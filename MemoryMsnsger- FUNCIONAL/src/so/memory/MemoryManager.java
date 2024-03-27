package so.memory;

import so.Process;

public class MemoryManager {
	private String[] physicMemory;
	private Strategy strategy;

	public MemoryManager(Strategy strategy) {
		this.strategy = strategy;
		this.physicMemory = new String[128];
	}

    public void write(Process p) {
        // Verificação para estouro de memória
        if (p.getSizeInMemory() > this.physicMemory.length) {
            throw new OutOfMemoryError("Processo excede a capacidade da memória física.");
        }

        if (this.strategy.equals(Strategy.FIRST_FIT)) {
            this.writeUsingFirstFit(p);
        } else if (this.strategy.equals(Strategy.BEST_FIT)) {
            this.writeUsingBestFit(p);
        } else if (this.strategy.equals(Strategy.WORST_FIT)) {
            this.writeUsingWorstFit(p);
        }
    }

	private void writeUsingFirstFit(Process p) {
		int actualSize = 0;
		for (int i = 0; i < physicMemory.length; i++) {
			if (physicMemory[i] == null) {
				actualSize++;
				if (actualSize >= p.getSizeInMemory()) {
					int start = i - actualSize + 1;
					for (int j = start; j < start + p.getSizeInMemory(); j++) {
						physicMemory[j] = p.getId();
					}
					printStatusMemory();
					return;
				}
			} else {
				actualSize = 0; // Reset actual size if the current slot is not empty
			}
		}
	}

	public void printStatusMemory() {
	    StringBuilder memoryLayout = new StringBuilder();
	    for (int i = 0; i < physicMemory.length; i++) {
	        memoryLayout.append(physicMemory[i] == null ? "." : physicMemory[i]).append(" ");
	    }
	    System.out.println(memoryLayout.toString().trim());
	}


	private void writeUsingBestFit(Process p) {
		int bestStartIndex = -1;
		int bestSize = Integer.MAX_VALUE; // Inicializa com o máximo para encontrar o "melhor" menor espaço
		int currentStartIndex = -1;
		int currentSize = 0;

		for (int i = 0; i < physicMemory.length; i++) {
			if (physicMemory[i] == null) {
				if (currentStartIndex == -1)
					currentStartIndex = i; // Começo de um novo espaço livre
				currentSize++;
			}
			if (physicMemory[i] != null || i == physicMemory.length - 1) { // Fim do espaço livre ou fim da memória
				if (currentSize >= p.getSizeInMemory() && currentSize < bestSize) {
					bestStartIndex = currentStartIndex;
					bestSize = currentSize;
				}
				currentStartIndex = -1; // Reset para o próximo espaço livre
				currentSize = 0;
			}
		}

		if (bestStartIndex != -1) { // Se encontrou um espaço adequado
			for (int i = bestStartIndex; i < bestStartIndex + p.getSizeInMemory(); i++) {
				physicMemory[i] = p.getId();
			}
			printStatusMemory();
		}
	}

	private void writeUsingWorstFit(Process p) {
		int worstStartIndex = -1;
		int worstSize = -1; // Inicializa com -1 para encontrar o maior espaço
		int currentStartIndex = -1;
		int currentSize = 0;

		for (int i = 0; i < physicMemory.length; i++) {
			if (physicMemory[i] == null) {
				if (currentStartIndex == -1)
					currentStartIndex = i; // Começo de um novo espaço livre
				currentSize++;
			}
			if (physicMemory[i] != null || i == physicMemory.length - 1) { // Fim do espaço livre ou fim da memória
				if (currentSize >= p.getSizeInMemory() && currentSize > worstSize) {
					worstStartIndex = currentStartIndex;
					worstSize = currentSize;
				}
				currentStartIndex = -1; // Reset para o próximo espaço livre
				currentSize = 0;
			}
		}

		if (worstStartIndex != -1) { // Se encontrou um espaço adequado
			for (int i = worstStartIndex; i < worstStartIndex + p.getSizeInMemory(); i++) {
				physicMemory[i] = p.getId();
			}
			printStatusMemory();
		}
	}

	public void writeProcess(Process p) {
	    this.write(p); // Simplesmente chama o método write adequado com base na estratégia
	}


	public boolean deleteProcess(Process p, int count) {
	    int deletedCount = 0;
	    for (int i = 0; i < physicMemory.length && deletedCount < count; i++) {
	        if (physicMemory[i] != null && physicMemory[i].equals(p.getId())) {
	            physicMemory[i] = null; // Limpa o processo da memória física
	            deletedCount++; // Incrementa o contador de deletados
	        }
	    }
	    return deletedCount == count; // Retorna true se a quantidade desejada foi deletada
	}


}
