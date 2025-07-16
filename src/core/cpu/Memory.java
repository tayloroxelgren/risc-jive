package core.cpu;

public class Memory {
    private byte[] memory;

    private static final int DEFAULT_MEMORY_SIZE = 100*4;

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
        for (int i = 0; i < memory.length; i += 4) {
            System.out.printf("Memory[0x%03X]: ", i);
            for (int j = 0; j < 4; j++) {
                System.out.printf("%02X ", memory[i + j] & 0xFF);
            }
            System.out.print("Decimal: " + this.readWord(i));
            
            // New line after every 4th word (every 16 bytes)
            if ((i + 4) % 8 == 0) {
                System.out.println();
            } else {
                System.out.print("  ");
            }
        }
    }
}