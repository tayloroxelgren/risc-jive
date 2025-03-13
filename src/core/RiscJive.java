package core;

import core.cpu.CpuCore;
import core.commands.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RiscJive{ 
    // Invoker


    private List<Integer> instructions;
    private final CpuCore cpu;
    private Map<Integer, InstructionCommand> commandRegistry;
    private List<InstructionCommand> decodedInstructions;

    public RiscJive(){
        this.cpu=new CpuCore();
        this.instructions=new ArrayList<>();
        this.commandRegistry = new HashMap<>();
        this.decodedInstructions=new ArrayList<>();
        registerCommands();
    }

    private void registerCommands() {
        // hashing using op, funct3,funct7
        commandRegistry.put(InstructionHasher.getHashForInstruction(0x00500293), new AddiCommand()); // ADDI
        commandRegistry.put(InstructionHasher.getHashForInstruction(0x005302b3), new AddCommand()); //ADD
        commandRegistry.put(InstructionHasher.getHashForInstruction(0x006292b3), new SllCommand());  // SLL
        commandRegistry.put(InstructionHasher.getHashForInstruction(0x406282b3), new SubCommand()); // SUB
        commandRegistry.put(InstructionHasher.getHashForInstruction(0x00728163), new BranchEqualCommand()); // BEQ
        commandRegistry.put(InstructionHasher.getHashForInstruction(0x00729163), new BranchNotEqualCommand()); // BNE
        commandRegistry.put(InstructionHasher.getHashForInstruction(0x027282b3), new MulCommand()); // MUL
    }

    public void loadSampleProgram() {
        instructions.clear();

        // addi x5, x0, 5
        instructions.add(0x00500293); // 0x00500093: ADDI x1, x0, 5
        
        // addi x6, x0, 1
        instructions.add(0x00100313); //
        
        // sll x5, x5, x6
        instructions.add(0x006292b3);

        // Adds 10000 to x5
        for(int i=0;i<10000;i++){
            // addi x5, x5, 1
            instructions.add(0x00128293);
        }

        // addi x7, x0, 2000
        instructions.add(0x7d000393); //

        // sub x5, x5, x6
        instructions.add(0x406282b3);

        // bne x5, x7, -2
        // due to offset 2 being in half-words, this jumps back by 1 instruction
        // This should ensure that x5==2000
        instructions.add(0xfe729fe3);

        //add x5, x7, x5
        instructions.add(0x005382b3);

        instructions.add(0x027282b3);
    }



    public void decodeAndPrintInstruction(int instruction) {
        int opcode = instruction & 0x7F; // bits 0–6
        int rd = (instruction >> 7) & 0x1F; // bits 7–11
        int funct3 = (instruction >> 12) & 0x7; // bits 12–14
        int rs1 = (instruction >> 15) & 0x1F; // bits 15–19
        int rs2 = (instruction >> 20) & 0x1F; // bits 20–24
        int funct7 = (instruction >> 25) & 0x7F; // bits 25–31

        System.out.printf("Instruction: 0x%08X\n", instruction);
        System.out.printf("Opcode: 0x%02X\n", opcode);
        System.out.printf("rd: x%d\n", rd);
        System.out.printf("funct3: 0x%01X\n", funct3);
        System.out.printf("rs1: x%d\n", rs1);
        System.out.printf("rs2: x%d\n", rs2);
        System.out.printf("funct7: 0x%02X\n", funct7);

    }

    private InstructionCommand decodeInstruction(int instruction) {
        int opcode = instruction & 0x7F; // bits 0–6
        int rd = (instruction >> 7) & 0x1F; // bits 7–11
        int funct3 = (instruction >> 12) & 0x7; // bits 12–14
        int rs1 = (instruction >> 15) & 0x1F; // bits 15–19
        int rs2 = (instruction >> 20) & 0x1F; // bits 20–24
        int funct7 = (instruction >> 25) & 0x7F; // bits 25–31
        int imm = instruction >> 20; // For I-type immediates

        // Generate the same hash key used in command registration
        int hashKey = InstructionHasher.getHashForInstruction(instruction);

        InstructionCommand command = commandRegistry.get(hashKey);
        
        return command;
    }


    public void decodeInstructions() {
        for(Integer ins:this.instructions){
            this.decodedInstructions.add(decodeInstruction(ins));
        }
    }

    // sends commands to receiver along with original hex value so that parameters can be received
    public void sendCommands(List<InstructionCommand> cmds,List<Integer> instructions){
        this.cpu.setProgram(cmds,instructions);
    }

    public void run(){
        cpu.runEmulator();
    }

    public static void main(String[] args) {
        RiscJive emulator = new RiscJive();
        emulator.loadSampleProgram();
        emulator.decodeInstructions();
        emulator.sendCommands(emulator.decodedInstructions,emulator.instructions);
        emulator.run();
    }

}