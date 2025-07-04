package core;

public class InstructionHasher {

    /**
     * Computes the hash key for a 32-bit encoded RISC-V instruction from the RV32I base set.
     * For I-type (opcode 0x13) and branch instructions (opcode 0x63), only opcode and funct3 are used.
     * For other instructions, the hash key is computed using opcode, funct3, and funct7.
     *
     * @param instruction a 32-bit RISC-V instruction
     * @return the computed hash key for lookup in the command registry
     */
    // public static int getHashForInstruction(int instruction) {
    //     int opcode = instruction & 0x7F;             // bits 0–6
    //     int funct3 = (instruction >> 12) & 0x7;        // bits 12–14
    //     int funct7 = (instruction >> 25) & 0x7F;       // bits 25–31

    //     int hashKey;
    //     // For I-type (opcode 0x13) and branch instructions (opcode 0x63),
    //     // only use opcode and funct3.
    //     if (opcode == 0x13 || opcode == 0x63) {
    //         hashKey = opcode | (funct3 << 7);
    //     } else {
    //         hashKey = opcode | (funct3 << 7) | (funct7 << 10);
    //     }
    //     return hashKey;
    // }

    public static int getHashForInstruction(int instr) {
        int opcode = instr & 0x7F;          // bits  6..0

        // U-type (LUI/AUIPC) and J-type (JAL) — opcode alone is enough
        if (opcode == 0x37 /*LUI*/ || opcode == 0x17 /*AUIPC*/
                                || opcode == 0x6F /*JAL  */) {
            return opcode;
        }

        int funct3 = (instr >> 12) & 0x7;   // bits 14..12
        int funct7 = (instr >> 25) & 0x7F;  // bits 31..25

        // I-type (0x13) and B-type (0x63) → only opcode+funct3
        if (opcode == 0x13 || opcode == 0x63) {
            return opcode | (funct3 << 7);
        }

        // Everything else (R-type, S-type, etc.) → opcode+funct3+funct7
        return opcode | (funct3 << 7) | (funct7 << 10);
    }

    
}
