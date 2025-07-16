	.file	"fib.c"
	.option nopic
	.attribute arch, "rv32i2p1"
	.attribute unaligned_access, 0
	.attribute stack_align, 16
	.text
	.align	2
	.globl	main
	.type	main, @function
main:
	addi	sp,sp,-32
	sw	s0,28(sp)
	addi	s0,sp,32
	sw	zero,-28(s0)
	sb	zero,-17(s0)
	li	a5,1
	sb	a5,-18(s0)
	lw	a5,-28(s0)
	lbu	a4,-17(s0)
	sb	a4,0(a5)
	lw	a5,-28(s0)
	addi	a5,a5,1
	lbu	a4,-18(s0)
	sb	a4,0(a5)
	li	a5,2
	sw	a5,-24(s0)
	j	.L2
.L3:
	lbu	a5,-17(s0)
	mv	a4,a5
	lbu	a5,-18(s0)
	add	a5,a4,a5
	sb	a5,-29(s0)
	lw	a5,-24(s0)
	lw	a4,-28(s0)
	add	a5,a4,a5
	lbu	a4,-29(s0)
	sb	a4,0(a5)
	lbu	a5,-18(s0)
	sb	a5,-17(s0)
	lbu	a5,-29(s0)
	sb	a5,-18(s0)
	lw	a5,-24(s0)
	addi	a5,a5,1
	sw	a5,-24(s0)
.L2:
	lw	a4,-24(s0)
	li	a5,11
	ble	a4,a5,.L3
	nop
	nop
	lw	s0,28(sp)
	addi	sp,sp,32
	jr	ra
	.size	main, .-main
	.ident	"GCC: (12.2.0-14+11+b1) 12.2.0"
