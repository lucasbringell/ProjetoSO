package so;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Execute {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<String, Process> processes = new HashMap<>();

    public static void main(String[] args) {
        SystemOperation.createProcess(); // Inicializa o ambiente (Memória, CPU, etc.)

        boolean running = true;
        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1 - Criar Processo");
            System.out.println("2 - Escrever Processo na Memória");
            System.out.println("3 - Deletar Processo da Memória");
            System.out.println("4 - Listar Processos");
            System.out.println("5 - Sair");
            System.out.print("Escolha uma opção: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (choice) {
                case 1:
                    createProcess();
                    break;
                case 2:
                    writeProcess();
                    break;
                case 3:
                    deleteProcess();
                    break;
                case 4:
                    listProcesses();
                    break;
                case 5:
                    System.out.println("Saindo...");
                    running = false;
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void createProcess() {
        System.out.print("Digite o tamanho do processo: ");
        int size = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha
        Process p = new Process(size); // Aqui você precisa adaptar conforme seu construtor de Process
        p.setTimeToExecute(10); // Valor fictício
        p.setPriority(Priority.MEDIA); // Valor fictício
        processes.put(p.getId(), p);
        System.out.println("Processo criado com ID: " + p.getId());
    }

    private static void writeProcess() {
        System.out.print("Digite o ID do processo para escrever na memória: ");
        String id = scanner.next();
        Process p = processes.get(id);
        if (p != null) {
            try {
                SystemOperation.getMm().writeProcess(p);
                System.out.println("Processo escrito na memória.");
            } catch (OutOfMemoryError e) {
                System.out.println("Falha ao escrever processo na memória. Memória insuficiente.");
            }
        } else {
            System.out.println("Processo não encontrado.");
        }
    }

    private static void deleteProcess() {
        System.out.print("Digite o ID do processo para deletar da memória: ");
        String id = scanner.next();
        System.out.print("Quantos processos deseja deletar? ");
        int count = scanner.nextInt();

        if (processes.containsKey(id)) {
            Process p = processes.get(id);
            boolean success = SystemOperation.getMm().deleteProcess(p, count); // Alterado para passar count
            if (success) {
                System.out.println("Processos deletados da memória.");
            } else {
                System.out.println("Não foi possível deletar todos os processos solicitados.");
            }
        } else {
            System.out.println("Processo não encontrado.");
        }
    }

    private static void listProcesses() {
        SystemOperation.getMm().printStatusMemory(); // Alteração para usar o método printStatusMemory
    }
}
