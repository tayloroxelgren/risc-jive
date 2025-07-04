package core.cpu;

public class Memory {
    private byte[] memory;

    private static final int DEFAULT_MEMORY_SIZE = 24;

    public Memory() {
        memory = new byte[DEFAULT_MEMORY_SIZE];
    }

    public int readWord(int address) {
        return ((memory[address] & 0xFF) << 0) |
               ((memory[address + 1] & 0xFF) << 8) |
               ((memory[address + 2] & 0xFF) << 16) |
               ((memory[address + 3] & 0xFF) << 24);
    }
    public short readHalfWord(int address) {
        int value =((memory[address] & 0xFF) | ((memory[address + 1] & 0xFF) << 8));

        return (short)value;
    }

    public byte readByte(int address) {
        int value=memory[address] & 0xFF;
        return (byte)value;
    }

    public void writeWord(int address, int value) {
        memory[address] = (byte) (value);
        memory[address + 1] = (byte) (value >> 8);
        memory[address + 2] = (byte) (value >> 16);
        memory[address + 3] = (byte) (value >> 24);
    }


    public void writeHalfWord(int address, int value) {
        memory[address] = (byte) (value);
        memory[address + 1] = (byte) (value >> 8);
    }


    public void writeByte(int address, int value) {
        memory[address] = (byte) value;
    }

    public void printMemory() {
        for (int i = 0; i < memory.length; i++) {
            System.out.println("Memory " + i + ": " + (memory[i] & 0xFF));
        }
    }
}