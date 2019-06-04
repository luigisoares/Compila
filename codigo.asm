sseg SEGMENT STACK ;início seg. pilha
  byte 4000h DUP(?) ;dimensiona pilha
sseg ENDS ;fim seg. pilha
dseg SEGMENT PUBLIC ;início seg. dados
  byte 4000h DUP(?) ;temporários
  byte ?      ;classe_variavel char x em 16384h
  byte 5h DUP("abcd$")     ;classe_constante string e2 em 16385h
  sword 1       ;classe_constante inteiro e em 16392h
  sword 34       ;classe_constante inteiro e3 em 16394h
  byte 'b'     ;classe_constante char e1 em 16396h
  sword ?     ;classe_variavel inteiro x1 em 16397h
  sword ?     ;classe_variavel inteiro eita em 16399h
  sword 100 DUP(?)      ;classe_variavel vet inteiro n1 em 16401h
  sword 50       ;classe_variavel inteiro n em 16601h
  sword 10       ;classe_variavel inteiro n7 em 16603h
  sword 45 DUP(?)      ;classe_variavel vet inteiro teste em 16605h
  sword 24 DUP(?)      ;classe_variavel vet inteiro beicola em 16695h
  sword 0       ;classe_variavel inteiro maxiter em 16743h
  sword ?     ;classe_variavel inteiro n2 em 16745h
  sword 100 DUP(?)      ;classe_variavel vet inteiro nome em 16747h
  byte 'x'     ;classe_variavel char n4 em 16947h
  byte ?      ;classe_variavel char a em 16948h
  byte ?      ;classe_variavel char mm em 16949h
  byte ?      ;classe_variavel char nn em 16950h
  byte '3'     ;classe_variavel char ab em 16951h
  byte ?      ;classe_variavel char mm2 em 16952h
  byte 'n'     ;classe_variavel char nn2 em 16953h
  byte ?      ;classe_variavel char ab2 em 16954h
  sword 23       ;classe_variavel inteiro mm3 em 16955h
  sword ?     ;classe_variavel inteiro nn3 em 16957h
  sword 3       ;classe_variavel inteiro ab3 em 16959h
  sword ?     ;classe_variavel inteiro mm4 em 16961h
  sword 55       ;classe_variavel inteiro nn4 em 16963h
  sword ?     ;classe_variavel inteiro ab4 em 16965h
  byte 256 DUP(?)       ;classe_variavel vet char vet em 16967h
  sword 33 DUP(?)      ;classe_variavel vet inteiro vet2 em 17223h
  sword ?     ;classe_variavel inteiro veeet em 17289h
  byte ?      ;classe_variavel char slaus em 17291h
  byte 2047 DUP(?)       ;classe_variavel vet char nome2 em 17292h
  byte 110 DUP(?)       ;classe_variavel vet char nome31 em 19339h
  byte 'j'     ;classe_variavel char nomezin em 19449h
  sword 2047 DUP(?)      ;classe_variavel vet inteiro nome433 em 19450h
  sword 20 DUP(?)      ;classe_variavel vet inteiro integer32 em 23544h
  byte 2047 DUP(?)       ;classe_variavel vet char nome24 em 23584h
  byte 20 DUP(?)       ;classe_variavel vet char nome3 em 25631h
  sword 31333       ;classe_variavel inteiro fed em 25651h
  byte 3h DUP("bc$")     ;classe_constante string n5 em 25653h
  byte 17h DUP("federico$")     ;classe_constante string n8 em 25658h
  byte 'f'     ;classe_constante char oi em 25669h
  sword 9       ;classe_variavel inteiro n9 em 25670h
  byte 255     ;classe_variavel char x23 em 25672h
  sword 3       ;classe_constante inteiro p em 25673h
dseg ENDS ;fim seg. dados
cseg SEGMENT PUBLIC ;início seg. código
  ASSUME CS:cseg, DS:dseg
strt:
  mov ax, dseg
  mov ds, ax
mov AX, DS:[80] ;
not AX
mov DS:[0], AX
mov ah, 4Ch
int 21h
cseg ENDS ;fim seg. código
END strt ;fim programa
