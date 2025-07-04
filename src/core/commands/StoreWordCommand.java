package core.commands;

import core.cpu.CpuCore;
import core.cpu.Memory;

public class StoreWordCommand implements InstructionCommand {
    @Override
    public void execute(CpuCore cpu, int opcode, int rd, int funct3, int rs1, int rs2, int funct7, int imm, int hexInstruction) {

        int valueToStore = cpu.getRegister(rd);

        // Store instructions use S type immediates, which are encoded in the instruction
        // Masks bits for imm[11:5] bits 31-25 and shift into spot
        int top7bits= (hexInstruction & 0xFE000000)>>>14;
        // masks bits for imm[4:0] bits 11-7 and shift into spot
        int bottom5bits = (hexInstruction & 0x7C0) >>>7;
        // Add them together using or
        imm=top7bits | bottom5bits;
        // System.out.println(imm);
        cpu.memory.writeWord(cpu.getRegister(rs1) + imm, cpu.getRegister(rs2));
    }
}