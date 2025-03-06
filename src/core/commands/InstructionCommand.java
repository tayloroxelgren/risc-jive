package core.commands;

import core.cpu.CpuCore;

public interface InstructionCommand {
    void execute(CpuCore cpu, int rd, int rs1, int rs2, int imm);
}