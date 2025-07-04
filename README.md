# Basic RISC-V emulator



**Risc-Jive** is a pure-Java RV32I emulator that implements the full RISC-V base integer instruction set (RV32I), including arithmetic, logic, branching, loads/stores, and control flow (JAL/JALR). It can execute inline hex-encoded instructions or flat bare-metal `.bin` files (no OS or CRT) loaded at address 0x0. A basic test program is included and instructions for how to build it.


## Compile Instruction
```
javac core/cpu/CpuCore.java core/commands/*.java core/*.java
```

## Run
```
java core.RiscJive
```
## Building Test Program
Navigate to the testprograms directory

**Assemble**
```
riscv64-unknown-elf-as -march=rv32i -mabi=ilp32 -o fib.o fib.S
```
**Link**
```
riscv64-unknown-elf-ld -m elf32lriscv -T link.ld --no-relax -o fib.elf fib.o
```
**Extract Binary**
```
riscv64-unknown-elf-objcopy --only-section .text -O binary fib.elf fib.bin
```
This will produce a flat `.bin` file that calculates and stores the first 12 Fibonacci numbers in memory (one byte per number).

## To-Do
- [x] Implement remaining RV32I instructions
- [x] Allow execution of assembly rather than just hex codes (.bin files)
- [x] Create memory
- [ ] Basic debugger

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
| `slli`      | Shift Left Logical Immediate               | `slli rd, rs1, rs2`| `rd = rs1 << imm`              | shift  |
| `srl`      | Shift Right Logical                | `srl rd, rs1, rs2`| `rd = rs1 >> rs2`              | shift  |
| `srli`      | Shift Right Logical Immediate                | `srli rd, rs1, rs2`| `rd = rs1 >> imm`              | shift  |
| `sra`      | Shift Right Arithmetic                 | `sra rd, rs1, rs2`| `rd = rs1 >>> rs2`              | shift  |
| `srai`      | Shift Right Arithmetic  Immediate                | `srai rd, rs1, rs2`| `rd = rs1 >>> imm`              | shift  |
| `beq`      | Branch Equal                | `beq rs1, rs2, imm`| `if(rs1 == rs2) pc += imm`              | branch  |
| `bne`      | Branch Not Equal                | `bne rs1, rs2, imm`| `if(rs1 ≠ rs2) pc += imm`              | branch  |
| `blt`      | Branch Less Than              | `blt rs1, rs2, imm`| `if(rs1 < rs2) pc += imm`              | branch  | 
| `bltu`      | Branch Less Than unsigned            | `bltu rs1, rs2, imm`| `if(rs1 < rs2) pc += imm`              | branch  | 
| `bge`      | Branch Greater Than              | `bge rs1, rs2, imm`| `if(rs1 >= rs2) pc += imm`              | branch  | 
| `bgeu`      | Branch Greater Than unsigned             | `bgeu rs1, rs2, imm`| `if(rs1 >= rs2) pc += imm`              | branch  | 
| `auipc`      | Add Upper Immediate to PC             | `auipc rd, imm`| `rd = pc + (imm << 12)`              | branch  | 
| `and`      | Bitwise And                | `and rd, rs1, rs2`| `rd = rs1 & rs2`              | logical  |
| `andi`      | Bitwise And Immediate                | `andi rd, rs1, imm`| `rd = rs1 & imm`              | logical  |
| `or`      | Bitwise Or                | `or rd, rs1, rs2`| `rd = rs1 \| rs2`              | logical  |
| `ori`      | Bitwise Or Immediate                | `ori rd, rs1, imm`| `rd = rs1 \| imm`              | logical  |
| `xor`      | Bitwise Xor                | `xor rd, rs1, rs2`| `rd = rs1 ^ rs2`              | logical  |
| `xori`      | Bitwise Xor Immediate                | `xori rd, rs1, imm`| `rd = rs1 ^ imm`              | logical  |
| `sltu`      | Set Less Than Unsigned        | `sltu rd, rs1, rs2`| `rd = (rs1 < rs2)`              | set  |
| `sltiu`      | Set Less Than Immediate Unsigned        | `sltiu rd, rs1, imm`| `rd = (rs1 < imm)`              | set  |
| `slt`      | Set Less Than        | `slt rd, rs1, rs2`| `rd = (rs1 < rs2)`              | set  |
| `slti`      | Set Less Than Immediate        | `slti rd, rs1, imm`| `rd = (rs1 < imm)`              | set  |
| `jal`      | Jump and Link        | `jal rd, imm`| `rd = pc+4; pc += imm`              | 	jump  |
| `jalr`      | Jump and Link Register        | `jalr rd, rs1, imm`| `rd = pc+4; pc = rs1+imm`              | 	jump  |
| `lw`      | Load Word                 | `lw rd, imm(rs1)`| `rd = mem[rs1+imm]`              | 	load  |
| `lh`      | Load Half                 | `lh rd, imm(rs1)`| `rd = mem[rs1+imm][0:15]`              | 	load  |
| `lhu`      | Load Half Unsigned                 | `lhu rd, imm(rs1)`| `rd = mem[rs1+imm][0:15]`              | 	load  |
| `lb`      | Load Byte                 | `lb rd, imm(rs1)`| `rd = mem[rs1+imm][0:7]`              | 	load  |
| `lbu`      | Load Byte Unsigned                 | `lbu rd, imm(rs1)`| `rd = mem[rs1+imm][0:7]`              | 	load  |
| `sw`      | Store Word                 | `sw rs2, imm(rs1)`| `mem[rs1+imm] = rs2`              | 	store  |
| `sh`      | Store Half                 | `sh rs2, imm(rs1)`| `mem[rs1+imm][0:15] = rs2`              | 	store  |
| `sb`      | Store Byte                 | `sb rs2, imm(rs1)`| `mem[rs1+imm][0:7] = rs2`              | 	store  |


### References
1. [RISC-V Assembler Cheat Sheet](https://projectf.io)  
2. [RISC-V Technical Specifications](https://riscv.org/technical/specifications/)
3. [RISC-V Instruction Encoder/Decoder](https://luplab.gitlab.io/rvcodecjs/)
