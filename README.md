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
- Implement remaining instructions
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
| `sub`      | 	Subtract                 | `sub rd, rs1, rs2`| `rd = rs1 - rs2`              | arithmetic  |
| `mul`      | 	Multiply                 | `mul rd, rs1, rs2`| `rd = (rs1 * rs2)[31:0]`              | arithmetic  |
| `div`      | 	Divide                 | `div rd, rs1, rs2`| `rd = rs1 / rs2`              | divide  |
| `sll`      | Shift Left Logical                | `sll rd, rs1, rs2`| `rd = rs1 << rs2`              | shift  |
| `beq`      | Branch Equal                | `beq rs1, rs2, imm`| `if(rs1 == rs2) pc += imm`              | branch  |
| `bne`      | Branch Not Equal                | `bne rs1, rs2, imm`| `if(rs1 ≠ rs2) pc += imm`              | branch  |
| `and`      | Bitwise And                | `and rd, rs1, rs2`| `rd = rs1 & rs2`              | logical  |
| `andi`      | Bitwise And Immediate                | `andi rd, rs1, imm`| `rd = rs1 & imm`              | logical  |
| `or`      | Bitwise Or                | `or rd, rs1, rs2`| `rd = rs1 \| rs2`              | logical  |
| `ori`      | Bitwise Or Immediate                | `ori rd, rs1, imm`| `rd = rs1 \| imm`              | logical  |


### References
1. [RISC-V Assembler Cheat Sheet](https://projectf.io)  
2. [RISC-V Technical Specifications](https://riscv.org/technical/specifications/)
3. [RISC-V Instruction Encoder/Decoder](https://luplab.gitlab.io/rvcodecjs/)
