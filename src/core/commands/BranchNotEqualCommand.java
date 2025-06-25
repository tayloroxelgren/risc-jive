package core.commands;

import core.cpu.CpuCore;

public class BranchNotEqualCommand implements InstructionCommand {
    @Override
    public void execute(CpuCore cpu, int opcode, int rd, int funct3, int rs1,int rs2,int funct7,int imm,int hexInstruction) {
        if(cpu.getRegister(rs1)!=cpu.getRegister(rs2)){
            // Decode the branch offset directly from the hex instruction
            int imm12 = (hexInstruction >> 31) & 0x1;   // bit 31 -> imm[12]
            int imm11 = (hexInstruction >> 7) & 0x1;    // bit 7  -> imm[11]
            int imm10to5 = (hexInstruction >> 25) & 0x3F; // bits 30-25 -> imm[10:5]
            int imm4to1 = (hexInstruction >> 8) & 0xF;    // bits 11-8 -> imm[4:1]

            // Combine the bits to form the immediate needed for branch instructions
            int offset = (imm12 << 12) | (imm11 << 11) | (imm10to5 << 5) | (imm4to1 << 1);

            // making sure signing is working properly for negative jumps
            if ((offset & 0x1000) != 0) { // Check if the sign bit (bit 12) is set
                offset |= 0xFFFFE000; // Sign-extend to 32-bit signed int
            }
            // dividing offset by 2 because my instructions are not actual bits
            cpu.setProgramCounter(cpu.getProgramCounter()+offset/2);
        }
    }
}