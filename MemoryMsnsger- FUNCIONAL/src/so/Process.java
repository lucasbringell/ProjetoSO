package so;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import so.Process;

public class Process {

	private String id;
	private int sizeInMemory;
	private int timeToExecute;
	private Priority priority;

	private static int processCount = 0;

	// public Process(int sizeInMemory) {
	// Random rand = new Random();
	// this.id = "p" + ++processCount;

	public Process(int sizeInMemory) {
		Random rand = new Random();
		this.id = "p" + ++processCount;
		List<Integer> givenList = Arrays.asList(1, 2, 4, 5, 8, 10, 20, 50, 100);
		this.sizeInMemory = sizeInMemory;
		this.timeToExecute = timeToExecute;
		this.priority = priority;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getSizeInMemory() {
		return sizeInMemory;
	}

	public void setSizeInMemory(int sizeInMemory) {
		this.sizeInMemory = sizeInMemory;
	}

	public int getTimeToExecute() {
		return timeToExecute;
	}

	public void setTimeToExecute(int timeToExecute) {
		this.timeToExecute = timeToExecute;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

}
