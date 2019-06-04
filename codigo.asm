sseg SEGMENT STACK ;início seg. pilha
  byte 4000h DUP(?) ;dimensiona pilha
sseg ENDS ;fim seg. pilha
dseg SEGMENT PUBLIC ;início seg. dados
  byte 4000h DUP(?) ;temporários
  byte 20 DUP(?)       ;classe_variavel vet char luigi em 4000h
  byte 0x5f     ;classe_variavel char tanguinha em 4020h
  sword 20 DUP(?)      ;classe_variavel vet inteiro salaminho em 4021h
  byte 'a'     ;classe_variavel char a em 4061h
  sword 1       ;classe_variavel inteiro n em 4062h
  sword ?     ;classe_variavel inteiro h em 4064h
  sword 4       ;classe_variavel inteiro j em 4066h
  byte 20 DUP(?)       ;classe_variavel vet char teste em 4068h
  sword 1       ;classe_constante inteiro b em 4088h
  sword 3       ;classe_constante inteiro c em 4090h
  sword 5       ;classe_constante inteiro d em 4092h
  byte 'c'     ;classe_constante char e em 4094h
dseg ENDS ;fim seg. dados
cseg SEGMENT PUBLIC ;início seg. código
  ASSUME CS:cseg, DS:dseg
strt:
  mov ax, dseg
  mov ds, ax
mov ah, 4Ch
int 21h
cseg ENDS ;fim seg. código
END strt ;fim programa
