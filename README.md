# Basic RISC-V emulator



An **early and basic implementation** of a RISC-V CPU emulator that allows the execution of simple assembly instructions directly or via hexadecimal encoding. The project is still in very early development and currently supports a *few* arithmetic and shift operations.


## Compile instruction
```
javac core/cpu/CpuCore.java core/commands/*.java core/*.java
```

## Run
```
java core.RiscJive
```

## To-Do
- Implement remaining RV32I instructions
- Be able to convert hex codes to assembly and back
- Allow execution of assembly rather than just hex codes
- Create stack memory
- Basic debugger

---

### Current Instructions


| Instruction | Description                   | Use                | Result                        | type      |
|-------------|-------------------------------|--------------------|-------------------------------|-------------|
| `addi`      | Add Immediate                 | `addi rd, rs1, imm`| `rd = rs1 + imm`              | arithmetic  |
| `add`      | Add                 | `add rd, rs1, rs2`| `rd = rs1 + rs2`              | arithmetic  |
| `lui`      | 	Load Upper Immediate              | `lui rd, imm`| `rd = imm << 12`              | arithmetic  |
| `sub`      | 	Subtract                 | `sub rd, rs1, rs2`| `rd = rs1 - rs2`              | arithmetic  |
| `mul`      | 	Multiply                 | `mul rd, rs1, rs2`| `rd = (rs1 * rs2)[31:0]`              | multiply  |
| `div`      | 	Divide                 | `div rd, rs1, rs2`| `rd = rs1 / rs2`              | divide  |
| `sll`      | Shift Left Logical                | `sll rd, rs1, rs2`| `rd = rs1 << rs2`              | shift  |
| `slli`      | Shift Left Logical Immediate               | `sll rd, rs1, rs2`| `rd = rs1 << imm`              | shift  |
| `srl`      | Shift Right Logical                | `srl rd, rs1, rs2`| `rd = rs1 >> rs2`              | shift  |
| `srli`      | Shift Right Logical Immediate                | `srli rd, rs1, rs2`| `rd = rs1 >> imm`              | shift  |
| `sra`      | Shift Right Arithmetic                 | `sra rd, rs1, rs2`| `rd = rs1 >>> rs2`              | shift  |
| `srai`      | Shift Right Arithmetic  Immediate                | `srai rd, rs1, rs2`| `rd = rs1 >>> imm`              | shift  |
| `beq`      | Branch Equal                | `beq rs1, rs2, imm`| `if(rs1 == rs2) pc += imm`              | branch  |
| `bne`      | Branch Not Equal                | `bne rs1, rs2, imm`| `if(rs1 ≠ rs2) pc += imm`              | branch  |
| `blt`      | Branch Less Than              | `blt rs1, rs2, imm`| `if(rs1 < rs2) pc += imm`              | branch  | 
| `bltu`      | Branch Less Than unsigned            | `blt rs1, rs2, imm`| `if(rs1 < rs2) pc += imm`              | branch  | 
| `bge`      | Branch Greater Than              | `bgt rs1, rs2, imm`| `if(rs1 >= rs2) pc += imm`              | branch  | 
| `bgeu`      | Branch Greater Than unsigned             | `bgt rs1, rs2, imm`| `if(rs1 >= rs2) pc += imm`              | branch  | 
| `and`      | Bitwise And                | `and rd, rs1, rs2`| `rd = rs1 & rs2`              | logical  |
| `andi`      | Bitwise And Immediate                | `andi rd, rs1, imm`| `rd = rs1 & imm`              | logical  |
| `or`      | Bitwise Or                | `or rd, rs1, rs2`| `rd = rs1 \| rs2`              | logical  |
| `ori`      | Bitwise Or Immediate                | `ori rd, rs1, imm`| `rd = rs1 \| imm`              | logical  |
| `xor`      | Bitwise Xor                | `xor rd, rs1, rs2`| `rd = rs1 ^ rs2`              | logical  |
| `xori`      | Bitwise Xor Immediate                | `xori rd, rs1, imm`| `rd = rs1 ^ imm`              | logical  |
| `sltu`      | Set Less Than Unsigned        | `sltu rd, rs1, rs2`| `rd = (rs1 < rs2)`              | set  |
| `sltiu`      | Set Less Than Immediate Unsigned        | `sltu rd, rs1, imm`| `rd = (rs1 < imm)`              | set  |
| `slt`      | Set Less Than        | `sltu rd, rs1, rs2`| `rd = (rs1 < rs2)`              | set  |
| `slti`      | Set Less Than Immediate        | `sltu rd, rs1, imm`| `rd = (rs1 < imm)`              | set  |


### References
1. [RISC-V Assembler Cheat Sheet](https://projectf.io)  
2. [RISC-V Technical Specifications](https://riscv.org/technical/specifications/)
3. [RISC-V Instruction Encoder/Decoder](https://luplab.gitlab.io/rvcodecjs/)
