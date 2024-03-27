package so;

import so.cpu.CpuManager;
import so.memory.MemoryManager;
import so.memory.Strategy;
import so.schedule.Schedule;
import so.Process;

public class SystemOperation {

	private static MemoryManager mm;
	private static CpuManager cm;
	private static Schedule schedule;

	public static MemoryManager getMm() {
		return mm;
	}

	public static void setMm(MemoryManager mm) {
		SystemOperation.mm = mm;
	}

	public static CpuManager getCm() {
		return cm;
	}

	public static void setCm(CpuManager cm) {
		SystemOperation.cm = cm;
	}

	public static Schedule getSchedule() {
		return schedule;
	}

	public static void setSchedule(Schedule schedule) {
		SystemOperation.schedule = schedule;
	}

	public static void createProcess() {
		if (mm == null) {
			mm = new MemoryManager(Strategy.FIRST_FIT);
		}
		if (cm == null) {
			cm = new CpuManager();
		}
	}

	public static Process SystemCall(SystemCallType type, Process p) {
		if (type.equals(SystemCallType.WRITE_PROCESS)) {
			mm.writeProcess(p);
		} else if (type.equals(SystemCallType.DELETE_PROCESS)) {
			mm.deleteProcess(p, 0);
		} else if (type.equals(SystemCallType.CREATE_PROCESS)) {
			if (cm == null) {
				cm = new CpuManager();
			}
			if (mm == null) {
				mm = new MemoryManager(Strategy.FIRST_FIT);
			}
			return new Process(0);
		}
		return null;
	}
}
