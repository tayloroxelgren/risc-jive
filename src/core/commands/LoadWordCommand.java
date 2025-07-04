package core.commands;

import core.cpu.CpuCore;
import core.cpu.Memory;

public class LoadWordCommand implements InstructionCommand {
    @Override
    public void execute(CpuCore cpu, int opcode, int rd, int funct3, int rs1, int rs2, int funct7, int imm, int hexInstruction) {

        int valueToLoad = cpu.memory.readWord(cpu.getRegister(rs1) + imm);
        
        cpu.setRegister(rd, valueToLoad);
    }
}