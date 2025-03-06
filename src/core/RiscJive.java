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
    private final Map<Integer, InstructionCommand> commandRegistry;
    private int programCounter;

    public RiscJive(){
        this.cpu=new CpuCore();
        this.instructions=new ArrayList<>();
        this.programCounter=0;
        this.commandRegistry = new HashMap<>();
        registerCommands();
    }

    private void registerCommands() {
        // hashing using op, funct3,funct7
        commandRegistry.put((0x13) | (0x0 << 7) | (0x00 << 10), new AddiCommand()); // ADDI
        commandRegistry.put((0x33) | (0x1 << 7) | (0x00 << 10), new SllCommand());  // SLL
        commandRegistry.put((0x33) | (0x0 << 7) | (0x20 << 10), new SubCommand()); // SUB
    }

    public void loadSampleProgram() {
        instructions.clear();
        
        // Psedu Assembly overview
        // addi x5, x0, 5
        // addi x6, x0, 1
        // sll x5, x5, x6
        // addi x5, x5, 1 X 10000
        // sub x5, x5, x6 X 69


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

        for(int i=0;i<69;i++){
            instructions.add(0x406282b3);
        }

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

    private void executeInstruction(int instruction) {
        int opcode = instruction & 0x7F; // bits 0–6
        int rd = (instruction >> 7) & 0x1F; // bits 7–11
        int funct3 = (instruction >> 12) & 0x7; // bits 12–14
        int rs1 = (instruction >> 15) & 0x1F; // bits 15–19
        int rs2 = (instruction >> 20) & 0x1F; // bits 20–24
        int funct7 = (instruction >> 25) & 0x7F; // bits 25–31
        int imm = instruction >> 20; // For I-type immediates

        // Generate the same hash key used in command registration
        int hashKey = (opcode) | (funct3 << 7) | (funct7 << 10);

        InstructionCommand command = commandRegistry.get(hashKey);
        
        if (command != null) {
            command.execute(cpu, rd, rs1, rs2, imm);
        } else {
            System.out.printf("Unknown instruction: 0x%08X\n", instruction);
        }
    }


    public void executeProgram() {
        while (programCounter < instructions.size()) {
            int instruction = instructions.get((int) programCounter);
            executeInstruction(instruction);
            programCounter++;
        }

        System.out.println("Emulation complete.");
        cpu.printRegisters();
    }

    public static void main(String[] args) {
        RiscJive emulator = new RiscJive();
        emulator.loadSampleProgram();
        emulator.executeProgram();
    }

}