package core.commands;

import core.cpu.CpuCore;

public class JumpAndLinkCommand implements InstructionCommand {
    @Override
    public void execute(CpuCore cpu, int opcode, int rd, int funct3,
                        int rs1, int rs2, int funct7, int imm, int hexInstruction) {

        int pc = cpu.getProgramCounter();
        int pcInBytes = pc * 4;

        // Decode J-type immediate (21 bits)
        int imm20 = (hexInstruction >>> 31) & 0x1;
        int imm10to1 = (hexInstruction >>> 21) & 0x3FF;
        int imm11 = (hexInstruction >>> 20) & 0x1;
        int imm19to12 = (hexInstruction >>> 12) & 0xFF;

        int offset = (imm20 << 20) | (imm19to12 << 12) | (imm11 << 11) | (imm10to1 << 1);

        // Sign-extend 21-bit immediate
        if ((offset & (1 << 20)) != 0) {
            offset |= 0xFFE00000;
        }

        // Save return address (PC + 4) in rd
        cpu.setRegister(rd, pcInBytes + 4);

        // Jump: update PC (note: divide by 4 if PC is index-based)
        cpu.setProgramCounter(pc + offset / 4);
    }
}