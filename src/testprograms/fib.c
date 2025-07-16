void main() {
    // Write directly to memory starting at address 0x00
    volatile unsigned int* mem = (unsigned int*)0x200;

    unsigned int a = 0;
    unsigned int b = 1;
    unsigned int next;

    mem[0] = a;
    mem[1] = b;

    for (int i = 2; i < 12; i++) {
        next = a + b;
        mem[i] = next;
        a = b;
        b = next;
    }

}
