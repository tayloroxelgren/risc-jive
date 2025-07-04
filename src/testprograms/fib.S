    /* fib12_bytes.S – compute first 12 Fibonacci numbers,
     * storing each as one byte at addresses 0x00, 0x01, …
     */

    .section .text
    .globl  _start
    .align  2

_start:
    /* x2 = F[0] = 0, x3 = F[1] = 1 */
    addi    x2, x0, 0       /* prev = 0 */
    addi    x3, x0, 1       /* curr = 1 */

    /* store F[0] at byte 0, F[1] at byte 1 */
    sb      x2, 0(x0)
    sb      x3, 1(x0)

    /* i = 2, N = 12 */
    addi    x5, x0, 2       /* loop index i */
    addi    x6, x0, 12      /* count N */

loop:
    /* next = prev + curr */
    add     x4, x2, x3

    /* address = i (base 0) */
    add     x7, x0, x5

    /* store byte F[i] at addr i */
    sb      x4, 0(x7)

    /* shift for next iter: prev = curr; curr = next */
    add     x2, x3, x0
    add     x3, x4, x0

    /* i++ */
    addi    x5, x5, 1

    /* if i < N, repeat */
    blt     x5, x6, loop
