package core.commands;

import core.cpu.CpuCore;
import core.cpu.Memory;

public class StoreByteCommand implements InstructionCommand {
    @Override
    public void execute(CpuCore cpu, int opcode, int rd, int funct3, int rs1, int rs2, int funct7, int imm, int hexInstruction) {
        // Store instructions use S type immediates, which are encoded in the instruction
        // Masks bits for imm[11:5] bits 31-25 and shift into spot
        int top7bits= (hexInstruction & 0xFE000000)>>>20;
        // masks bits for imm[4:0] bits 11-7 and shift into spot
        int bottom5bits = (hexInstruction & 0x7C0) >>>7;
        // Add them together using or
        imm=top7bits | bottom5bits;
        int address = cpu.getRegister(rs1) + imm;
        byte value  = (byte)(cpu.getRegister(rs2) & 0xFF);
        cpu.memory.writeByte(address, value);
    }
}