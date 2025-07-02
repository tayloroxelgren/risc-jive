public class Memory {
    private byte[] memory;

    // Default to Megabyte memory size
    private static final int DEFAULT_MEMORY_SIZE = 1024 * 1024; // 1 MB
    public Memory() {
        memory = new byte[DEFAULT_MEMORY_SIZE];
    }

    public byte readByte(int address) {
        return memory[address];
    }

    public void writeByte(int address, byte value) {
        memory[address] = value;
    }
}