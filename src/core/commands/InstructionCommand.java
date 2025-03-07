package core.commands;

import core.cpu.CpuCore;

public interface InstructionCommand {
    public void execute(CpuCore cpu, int opcode, int rd, int funct3, int rs1,int rs2,int funct7,int imm,int hexInstruction);
}