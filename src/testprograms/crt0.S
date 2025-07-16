    .section .text
    .globl _start
    .align 2

_start:
    # Set up the stack pointer (x2)
    # Assuming your stack top is at symbol _stack_top (defined in link.ld)
    la x2, _stack_top

    # Call main()
    call main
    ebreak
