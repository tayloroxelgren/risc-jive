package core.cpu;

import core.commands.InstructionCommand;
import java.util.ArrayList;
import java.util.List;

public class CpuCore{
    // Receiver

    private final long[] registers = new long[32];
    private int programCounter;
    private List<InstructionCommand> decodedInstructions;
    private List<Integer> rawinstructions;




    public CpuCore(){
        this.programCounter=0;
        this.decodedInstructions = new ArrayList<>();
        this.rawinstructions = new ArrayList<>();
    }

    public void setProgram(List<InstructionCommand> sentCommands,List<Integer> instructions){
        this.decodedInstructions=sentCommands;
        this.rawinstructions=instructions;
    }



    private int[] paramsFromHex(Integer instruction){
        int opcode = instruction & 0x7F; // bits 0–6
        int rd = (instruction >> 7) & 0x1F; // bits 7–11
        int funct3 = (instruction >> 12) & 0x7; // bits 12–14
        int rs1 = (instruction >> 15) & 0x1F; // bits 15–19
        int rs2 = (instruction >> 20) & 0x1F; // bits 20–24
        int funct7 = (instruction >> 25) & 0x7F; // bits 25–31
        int imm = instruction >> 20; // For I-type immediates

        int[] params = {opcode,rd,funct3,rs1,rs2,funct7,imm};

        return params;
    }

    public int getProgramCounter(){
        return this.programCounter;
    }

    public void setProgramCounter(int x){
        this.programCounter=x;
    }


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

    public void runEmulator(){
        while(this.programCounter<decodedInstructions.size()){
            InstructionCommand currentCommand=decodedInstructions.get(this.programCounter);
            int[] params=paramsFromHex(this.rawinstructions.get(this.programCounter));
            int opcode = params[0];
            int rd = params[1];
            int funct3 = params[2];
            int rs1 = params[3];
            int rs2 = params[4];
            int funct7 = params[5];
            int imm = params[6];

            int oldProgramCounter = this.programCounter;

            currentCommand.execute(this,opcode,rd,funct3,rs1,rs2,funct7,imm,this.rawinstructions.get(this.programCounter));
            // Using this to account for branches
            if (this.programCounter == oldProgramCounter) {
                this.programCounter++;
            }
        }
        printRegisters();
    }

}