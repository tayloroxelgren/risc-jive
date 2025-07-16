package core;

public class InstructionHasher {

    public static int getHashForInstruction(int instr) {
        int opcode = instr & 0x7F;
        int funct3 = (instr >> 12) & 0x7;
        int funct7 = (instr >> 25) & 0x7F;
        
        // U-type, J-type: opcode
        if (opcode == 0x37 || opcode == 0x17 || opcode == 0x6F) {
            return opcode;
        }
        
        // I-type, S-type, B-type: opcode + funct3
        if (opcode == 0x67 || opcode == 0x03 || opcode == 0x0F || 
            opcode == 0x63 || opcode == 0x23) {
            return opcode | (funct3 << 7);
        }
        
        // I-type with shifts: opcode + funct3 + funct7
        if (opcode == 0x13) {
            if (funct3 == 0x1 || funct3 == 0x5) {
                return opcode | (funct3 << 7) | (funct7 << 10);
            }
            return opcode | (funct3 << 7);
        }
        
        // I-type ECALL/EBREAK: opcode + imm
        if (opcode == 0x73) {
            int imm = (instr >> 20) & 0xFFF;
            return opcode | (imm << 7);
        }
        
        // R-type: opcode + funct3 + funct7
        if (opcode == 0x33) {
            return opcode | (funct3 << 7) | (funct7 << 10);
        }
        
        return opcode | (funct3 << 7) | (funct7 << 10);
    }

    
}
