package core.commands;

import core.cpu.CpuCore;
import core.cpu.Memory;

public class LoadHalfWordUnsignedCommand implements InstructionCommand {
    @Override
    public void execute(CpuCore cpu, int opcode, int rd, int funct3, int rs1, int rs2, int funct7, int imm, int hexInstruction) {
        System.out.println("hit");
        int uhalfword = cpu.memory.readHalfWord(cpu.getRegister(rs1) + imm);
        
        cpu.setRegister(rd, uhalfword & 0xFFFF);
    }
}