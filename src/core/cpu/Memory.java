package core.cpu;

public class Memory {
    private int[] memory;

    // Default to Megabyte memory size
    // private static final int DEFAULT_MEMORY_SIZE = 1024 * 1024; // 1 MB
    private static final int DEFAULT_MEMORY_SIZE = 10; // 1 MB
    public Memory() {
        memory = new int[DEFAULT_MEMORY_SIZE];
    }

    public int readWord(int address) {
        return memory[address];
    }

    public void writeWord(int address, int value) {
        memory[address] = value;
    }

    // function that prints out all bytes in memory
    public void printMemory() {
        for (int i = 0; i < memory.length; i++) {
            System.out.println("Memory " + i + ": " + memory[i]);
        }
    }
}