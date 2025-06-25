package core.commands;

import core.cpu.CpuCore;

public class JumpAndLinkRegisterCommand implements InstructionCommand {
    @Override
    public void execute(CpuCore cpu, int opcode, int rd, int funct3,
                        int rs1, int rs2, int funct7, int imm, int hexInstruction) {

        int pc = cpu.getProgramCounter();
        int pcInBytes = pc * 4;

        // Sign-extend 12-bit immediate
        if ((imm & 0x800) != 0) {
            imm |= 0xFFFFF000;
        }

        int target = (cpu.getRegister(rs1) + imm) & ~1; // Clear bit 0 (LSB)
        cpu.setRegister(rd, pcInBytes + 4);             // Save return address
        cpu.setProgramCounter(target / 4);              // Convert byte address to instruction index
    }
}