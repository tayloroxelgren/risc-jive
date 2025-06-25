package core.commands;

import core.cpu.CpuCore;

public class BranchLessThanUCommand implements InstructionCommand {
    @Override
    public void execute(CpuCore cpu, int opcode, int rd, int funct3, int rs1, int rs2, int funct7, int imm, int hexInstruction) {
        if (Integer.compareUnsigned(cpu.getRegister(rs1), cpu.getRegister(rs2)) < 0) {
            int imm12 = (hexInstruction >> 31) & 0x1;
            int imm11 = (hexInstruction >> 7) & 0x1;
            int imm10to5 = (hexInstruction >> 25) & 0x3F;
            int imm4to1 = (hexInstruction >> 8) & 0xF;

            int offset = (imm12 << 12) | (imm11 << 11) | (imm10to5 << 5) | (imm4to1 << 1);
            if ((offset & 0x1000) != 0) offset |= 0xFFFFE000;

            // dividing offset by 4 because my instructions are not actual bytes
            cpu.setProgramCounter(cpu.getProgramCounter() + offset/4);
        }
    }
}
