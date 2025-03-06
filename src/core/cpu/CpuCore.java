package core.cpu;

public class CpuCore{
    // Receiver

    private final long[] registers = new long[32];


    public long getRegister(int index){
        if(index==0){
            return 0;
        }
        return registers[index];
    }

    public void setRegister(int index,long value){
        if(index!=0){
            registers[index]=value;
        }
    }

    public void printRegisters() {
        for (int i = 0; i < registers.length; i++) {
            System.out.printf("x%d: %d\n", i, getRegister(i));
        }
    }

}