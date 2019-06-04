sseg SEGMENT STACK ;início seg. pilha
  byte 4000h DUP(?) ;dimensiona pilha
sseg ENDS ;fim seg. pilha
dseg SEGMENT PUBLIC ;início seg. dados
  byte 4000h DUP(?) ;temporários
  byte ?      ;classe_variavel char x em 4000h
  byte 5h DUP("abcd$")     ;classe_constante string e2 em 4001h
  sword 1       ;classe_constante inteiro e em 4008h
  sword 34       ;classe_constante inteiro e3 em 4010h
  byte 'b'     ;classe_constante char e1 em 4012h
  sword ?     ;classe_variavel inteiro x1 em 4013h
  sword ?     ;classe_variavel inteiro eita em 4015h
  sword 100 DUP(?)      ;classe_variavel vet inteiro n1 em 4017h
  sword 50       ;classe_variavel inteiro n em 4217h
  sword 10       ;classe_variavel inteiro n7 em 4219h
  sword 45 DUP(?)      ;classe_variavel vet inteiro teste em 4221h
  sword 24 DUP(?)      ;classe_variavel vet inteiro beicola em 4311h
  sword 0       ;classe_variavel inteiro maxiter em 4359h
  sword ?     ;classe_variavel inteiro n2 em 4361h
  sword 100 DUP(?)      ;classe_variavel vet inteiro nome em 4363h
  byte 'x'     ;classe_variavel char n4 em 4563h
  byte ?      ;classe_variavel char a em 4564h
  byte ?      ;classe_variavel char mm em 4565h
  byte ?      ;classe_variavel char nn em 4566h
  byte '3'     ;classe_variavel char ab em 4567h
  byte ?      ;classe_variavel char mm2 em 4568h
  byte 'n'     ;classe_variavel char nn2 em 4569h
  byte ?      ;classe_variavel char ab2 em 4570h
  sword 23       ;classe_variavel inteiro mm3 em 4571h
  sword ?     ;classe_variavel inteiro nn3 em 4573h
  sword 3       ;classe_variavel inteiro ab3 em 4575h
  sword ?     ;classe_variavel inteiro mm4 em 4577h
  sword 55       ;classe_variavel inteiro nn4 em 4579h
  sword ?     ;classe_variavel inteiro ab4 em 4581h
  byte 256 DUP(?)       ;classe_variavel vet char vet em 4583h
  sword 33 DUP(?)      ;classe_variavel vet inteiro vet2 em 4839h
  sword ?     ;classe_variavel inteiro veeet em 4905h
  byte ?      ;classe_variavel char slaus em 4907h
  byte 2047 DUP(?)       ;classe_variavel vet char nome2 em 4908h
  byte 110 DUP(?)       ;classe_variavel vet char nome31 em 6955h
  byte 'j'     ;classe_variavel char nomezin em 7065h
  sword 2047 DUP(?)      ;classe_variavel vet inteiro nome433 em 7066h
  sword 20 DUP(?)      ;classe_variavel vet inteiro integer32 em 11160h
  byte 2047 DUP(?)       ;classe_variavel vet char nome24 em 11200h
  byte 20 DUP(?)       ;classe_variavel vet char nome3 em 13247h
  sword 31333       ;classe_variavel inteiro fed em 13267h
  byte 3h DUP("bc$")     ;classe_constante string n5 em 13269h
  byte 17h DUP("federico$")     ;classe_constante string n8 em 13274h
  byte 'f'     ;classe_constante char oi em 13285h
  sword 9       ;classe_variavel inteiro n9 em 13286h
  byte 255     ;classe_variavel char x23 em 13288h
  sword 3       ;classe_constante inteiro p em 13289h
dseg ENDS ;fim seg. dados
cseg SEGMENT PUBLIC ;início seg. código
  ASSUME CS:cseg, DS:dseg
strt:
  mov AX, dseg
  mov ds, AX
mov AX, 3 ; movi para AX um VALORCONST
mov DS:[2], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, 98 ; movi para AX um VALORCONST
mov DS:[3], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, 255 ; movi para AX um VALORCONST
mov DS:[4], AX ;MOVI PARA END o CONTEUDO DE AX
dseg SEGMENT PUBLIC
byte "abc$"; constante string
dseg ENDS
mov AX, 3 ; movi para AX um VALORCONST
mov DS:[12], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, 3 ; movi para AX um VALORCONST
mov DS:[14], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, DS:[10] ;3 em 10
neg AX
add AX,1
mov DS:[14], AX
mov ah, 4Ch
int 21h
cseg ENDS ;fim seg. código
END strt ;fim programa
