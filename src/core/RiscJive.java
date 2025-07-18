package core;

import core.cpu.CpuCore;
import core.commands.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.FileInputStream;
import java.io.IOException;

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
        commandRegistry.put(InstructionHasher.getHashForInstruction(0x00129293), new SlliCommand());  // SLLI
        commandRegistry.put(InstructionHasher.getHashForInstruction(0x0062d2b3), new SrlCommand());  // SRL
        commandRegistry.put(InstructionHasher.getHashForInstruction(0x0012d293), new SrliCommand());  // SRLI
        commandRegistry.put(InstructionHasher.getHashForInstruction(0x4062d2b3), new SraCommand());  // SRA
        commandRegistry.put(InstructionHasher.getHashForInstruction(0x0012d293), new SraiCommand());  // SRAI
        commandRegistry.put(InstructionHasher.getHashForInstruction(0x406282b3), new SubCommand()); // SUB
        commandRegistry.put(InstructionHasher.getHashForInstruction(0x00728163), new BranchEqualCommand()); // BEQ
        commandRegistry.put(InstructionHasher.getHashForInstruction(0x00729163), new BranchNotEqualCommand()); // BNE
        commandRegistry.put(InstructionHasher.getHashForInstruction(0xfe72cfe3), new BranchLessThanCommand()); // BLT
        commandRegistry.put(InstructionHasher.getHashForInstruction(0xfe526ee3), new BranchLessThanUCommand()); // BLTU
        commandRegistry.put(InstructionHasher.getHashForInstruction(0xfe525ee3), new BranchGreaterThanCommand()); // BGE
        commandRegistry.put(InstructionHasher.getHashForInstruction(0xfe42fee3), new BranchGreaterThanUCommand()); // BGEU
        commandRegistry.put(InstructionHasher.getHashForInstruction(0x027282b3), new MulCommand()); // MUL
        commandRegistry.put(InstructionHasher.getHashForInstruction(0x0272c333), new DivCommand()); // DIV
        commandRegistry.put(InstructionHasher.getHashForInstruction(0x00037293), new AndiCommand()); // ANDI
        commandRegistry.put(InstructionHasher.getHashForInstruction(0x0062f3b3), new AndCommand()); // AND
        commandRegistry.put(InstructionHasher.getHashForInstruction(0x0062e3b3), new OrCommand()); // OR
        commandRegistry.put(InstructionHasher.getHashForInstruction(0x0022e393), new OriCommand()); // ORI
        commandRegistry.put(InstructionHasher.getHashForInstruction(0x0063c3b3), new XorCommand()); // XOR
        commandRegistry.put(InstructionHasher.getHashForInstruction(0x0023c393), new XoriCommand()); // XORI
        commandRegistry.put(InstructionHasher.getHashForInstruction(0x0072b2b3), new SltuCommand()); // SLTU
        commandRegistry.put(InstructionHasher.getHashForInstruction(0x0092b293), new SltiuCommand()); // SLTIU
        commandRegistry.put(InstructionHasher.getHashForInstruction(0x007322b3), new SltCommand()); // SLT
        commandRegistry.put(InstructionHasher.getHashForInstruction(0x00332293), new SltiCommand()); // SLTI
        commandRegistry.put(InstructionHasher.getHashForInstruction(0x123452b7), new LoadUpperImmediateCommand()); // LUI
        commandRegistry.put(InstructionHasher.getHashForInstruction(0x00000297), new AddUpperImmediatePcCommand()); // AUIPC
        commandRegistry.put(InstructionHasher.getHashForInstruction(0x008000ef), new JumpAndLinkCommand()); // JAL
        commandRegistry.put(InstructionHasher.getHashForInstruction(0x00000167), new JumpAndLinkRegisterCommand()); // JALR

        // Memory commands
        commandRegistry.put(InstructionHasher.getHashForInstruction(0x00502023), new StoreWordCommand()); // SW
        commandRegistry.put(InstructionHasher.getHashForInstruction(0x002010a3), new StoreHalfWordCommand()); // SH
        commandRegistry.put(InstructionHasher.getHashForInstruction(0x002000a3), new StoreByteCommand()); // SB

        commandRegistry.put(InstructionHasher.getHashForInstruction(0x0000a383), new LoadWordCommand()); // LW
        commandRegistry.put(InstructionHasher.getHashForInstruction(0x00101103), new LoadHalfWordCommand()); // LH
        commandRegistry.put(InstructionHasher.getHashForInstruction(0x00005103), new LoadHalfWordUnsignedCommand()); // LHU
        commandRegistry.put(InstructionHasher.getHashForInstruction(0x00100103), new LoadByteCommand()); // LB
        commandRegistry.put(InstructionHasher.getHashForInstruction(0x00004103), new LoadByteUnsignedCommand()); // LBU

        // Ebreak
        commandRegistry.put(InstructionHasher.getHashForInstruction(0x00100073), new EbreakCommand()); // EBREAK

    }

    public void loadSampleProgram() {
        instructions.clear();


    }

    public void loadBinary(String path) throws IOException {
        instructions.clear();
        try (FileInputStream in = new FileInputStream(path)) {
            byte[] buf = new byte[4];
            while (in.read(buf) == 4) {
                int insn = (buf[3] & 0xFF) << 24 |
                        (buf[2] & 0xFF) << 16 |
                        (buf[1] & 0xFF) << 8  |
                        (buf[0] & 0xFF);
                instructions.add(insn);
            }
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

    public static void main(String[] args) throws IOException{
        RiscJive emulator = new RiscJive();
        emulator.loadBinary("testprograms/fib.bin");
        emulator.decodeInstructions();
        emulator.sendCommands(emulator.decodedInstructions,emulator.instructions);
        emulator.run();
    }

}