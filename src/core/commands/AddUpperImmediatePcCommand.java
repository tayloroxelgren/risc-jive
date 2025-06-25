package core.commands;

import core.cpu.CpuCore;


public class AddUpperImmediatePcCommand implements InstructionCommand {
    @Override
    public void execute(CpuCore cpu, int opcode, int rd, int funct3, int rs1,int rs2,int funct7,int imm,int hexInstruction) {
        int imm20 = hexInstruction >>> 12; // Extract upper 20 bits
        int offset = imm20 << 12;
        cpu.setRegister(rd, cpu.getProgramCounter() * 4 + offset); // Multiplying by 4 to equal it to one instruction
    }
}