sseg SEGMENT STACK ;início seg. pilha
  byte 4000h DUP(?) ;dimensiona pilha
sseg ENDS ;fim seg. pilha
dseg SEGMENT PUBLIC ;início seg. dados
  byte 4000h DUP(?) ;temporários
  byte ?      ;classe_variavel char x em 16384h
  byte 'a'     ;classe_constante char e2 em 16385h
  sword 1       ;classe_constante inteiro e em 16386h
  sword 34       ;classe_constante inteiro e3 em 16388h
  byte 'b'     ;classe_constante char e1 em 16390h
  sword ?     ;classe_variavel inteiro x1 em 16391h
  sword ?     ;classe_variavel inteiro eita em 16393h
  sword 100 DUP(?)      ;classe_variavel vet inteiro n1 em 16395h
  sword 50       ;classe_variavel inteiro n em 16595h
  sword 10       ;classe_variavel inteiro n7 em 16597h
  sword 45 DUP(?)      ;classe_variavel vet inteiro teste em 16599h
  sword 24 DUP(?)      ;classe_variavel vet inteiro beicola em 16689h
  sword 0       ;classe_variavel inteiro maxiter em 16737h
  sword ?     ;classe_variavel inteiro n2 em 16739h
  sword 100 DUP(?)      ;classe_variavel vet inteiro nome em 16741h
  byte 'x'     ;classe_variavel char n4 em 16941h
  byte ?      ;classe_variavel char a em 16942h
  byte ?      ;classe_variavel char mm em 16943h
  byte ?      ;classe_variavel char nn em 16944h
  byte '3'     ;classe_variavel char ab em 16945h
  byte ?      ;classe_variavel char mm2 em 16946h
  byte 'n'     ;classe_variavel char nn2 em 16947h
  byte ?      ;classe_variavel char ab2 em 16948h
  sword 23       ;classe_variavel inteiro mm3 em 16949h
  sword ?     ;classe_variavel inteiro nn3 em 16951h
  sword 3       ;classe_variavel inteiro ab3 em 16953h
  sword ?     ;classe_variavel inteiro mm4 em 16955h
  sword 55       ;classe_variavel inteiro nn4 em 16957h
  sword ?     ;classe_variavel inteiro ab4 em 16959h
  byte 256 DUP(?)       ;classe_variavel vet char vet em 16961h
  sword 33 DUP(?)      ;classe_variavel vet inteiro vet2 em 17217h
  sword ?     ;classe_variavel inteiro veeet em 17283h
  byte ?      ;classe_variavel char slaus em 17285h
  byte 2047 DUP(?)       ;classe_variavel vet char nome2 em 17286h
  byte 110 DUP(?)       ;classe_variavel vet char nome31 em 19333h
  byte 'j'     ;classe_variavel char nomezin em 19443h
  sword 2047 DUP(?)      ;classe_variavel vet inteiro nome433 em 19444h
  sword 20 DUP(?)      ;classe_variavel vet inteiro integer32 em 23538h
  byte 2047 DUP(?)       ;classe_variavel vet char nome24 em 23578h
  byte 20 DUP(?)       ;classe_variavel vet char nome3 em 25625h
  sword 31333       ;classe_variavel inteiro fed em 25645h
  byte 'c'     ;classe_constante char n5 em 25647h
  byte 0x0a     ;classe_constante char n8 em 25648h
  byte 'f'     ;classe_constante char oi em 25649h
  sword 10       ;classe_variavel inteiro n9 em 25650h
  sword 3       ;classe_constante inteiro p em 25652h
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
