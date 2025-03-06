package core.commands;

import core.cpu.CpuCore;

public class SllCommand implements InstructionCommand {
    @Override
    public void execute(CpuCore cpu, int rd, int rs1, int rs2, int imm) {
        cpu.setRegister(rd, cpu.getRegister(rs1) << cpu.getRegister(rs2));
    }
}