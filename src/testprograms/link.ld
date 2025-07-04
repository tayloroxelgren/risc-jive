ENTRY(_start)

SECTIONS
{
  /* Place .text at address 0x0 */
  . = 0x0;
  .text : {
    KEEP(*(.text*))
  }

  /* Discard everything else */
  /DISCARD/ : {
    *(.data*)
    *(.bss*)
    *(.comment*)
    *(.note*)
  }
}
