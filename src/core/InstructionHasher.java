package core;

public class InstructionHasher {

    /**
     * Computes the hash key for a 32-bit encoded RISC-V instruction from the RV64I base set.
     * In RV64I, the base instruction encoding is identical to RV32I.
     * For I-type (opcode 0x13) and branch instructions (opcode 0x63), only opcode and funct3 are used.
     * For other instructions, the hash key is computed using opcode, funct3, and funct7.
     *
     * @param instruction a 32-bit RISC-V instruction
     * @return the computed hash key for lookup in the command registry
     */
    public static int getHashForInstruction(int instruction) {
        int opcode = instruction & 0x7F;             // bits 0–6
        int funct3 = (instruction >> 12) & 0x7;        // bits 12–14
        int funct7 = (instruction >> 25) & 0x7F;       // bits 25–31

        int hashKey;
        // For I-type (opcode 0x13) and branch instructions (opcode 0x63),
        // only use opcode and funct3.
        if (opcode == 0x13 || opcode == 0x63) {
            hashKey = opcode | (funct3 << 7);
        } else {
            hashKey = opcode | (funct3 << 7) | (funct7 << 10);
        }
        return hashKey;
    }
}
