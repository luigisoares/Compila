sseg SEGMENT STACK ;início seg. pilha
  byte 4000h DUP(?) ;dimensiona pilha
sseg ENDS ;fim seg. pilha
dseg SEGMENT PUBLIC ;início seg. dados
  byte 4000h DUP(?) ;temporários
  sword 23       ;classe_variavel inteiro mm3 em 16384
  sword ?     ;classe_variavel inteiro nn3 em 16386
  sword 3       ;classe_variavel inteiro ab3 em 16388
  byte 10 DUP(?)       ;classe_variavel vet char a em 16390
dseg ENDS ;fim seg. dados
cseg SEGMENT PUBLIC ;início seg. código
  ASSUME CS:cseg, DS:dseg
strt:
  mov AX, dseg
  mov ds, AX
mov AX, 3 ; movi para AX um VALORCONST
mov DS:[0], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, 2 ; movi para AX um VALORCONST
mov DS:[2], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, 5 ; movi para AX um VALORCONST
mov DS:[4], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, DS:[4] ;
neg AX
mov DS:[4], AX
mov AX, DS:[4]
mov BX, DS:[4]
imul BX ; multiplicacao
mov DS:[6], ax
mov AX, DS:[4]
mov BX, DS:[6]
add AX, BX ; add de AX e BX
mov DS:[6], AX ; 
mov AX, DS:[6] ; peguei o end do exp talvez0 << end do simboloA
mov DS:[16384], AX; salvando o valor no endereco correto
dseg SEGMENT PUBLIC
byte "n$"; constante string
dseg ENDS
mov ah, 4Ch
int 21h
cseg ENDS ;fim seg. código
END strt ;fim programa
