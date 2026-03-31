ENTRY(_start)

MEMORY{
  RAM (RWX) : ORIGIN =0X0, LENGTH = 1024
}

SECTIONS {
  /* Code starts at 0x0 */
  . = ORIGIN(RAM);
  _stack_top = ORIGIN(RAM) + LENGTH(RAM); 

  .text : {
    KEEP(*(.text*))
  } > RAM

  .data : {
    *(.data*)
  } > RAM

  .bss : {
    *(.bss*)
    *(COMMON)
  } > RAM

  /DISCARD/ : {
    *(.comment*)
    *(.note*)
  }
}

