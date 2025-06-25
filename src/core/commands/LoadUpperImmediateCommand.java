package core.commands;

import core.cpu.CpuCore;


public class LoadUpperImmediateCommand implements InstructionCommand {
    @Override
    public void execute(CpuCore cpu, int opcode, int rd, int funct3, int rs1,int rs2,int funct7,int imm,int hexInstruction) {
        int imm20 = hexInstruction >>> 12; // Extract bits 31–12
        cpu.setRegister(rd, imm20 << 12);
    }
}