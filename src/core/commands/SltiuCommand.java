package core.commands;

import core.cpu.CpuCore;


public class SltiuCommand implements InstructionCommand {
    @Override
    public void execute(CpuCore cpu, int opcode, int rd, int funct3, int rs1,int rs2,int funct7,int imm,int hexInstruction) {
        cpu.setRegister(rd, Integer.compareUnsigned(cpu.getRegister(rs1), imm) < 0 ? 1 : 0);
    }
}