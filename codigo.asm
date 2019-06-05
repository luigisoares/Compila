sseg SEGMENT STACK ;in�cio seg. pilha
  byte 4000h DUP(?) ;dimensiona pilha
sseg ENDS ;fim seg. pilha
dseg SEGMENT PUBLIC ;in�cio seg. dados
  byte 4000h DUP(?) ;tempor�rios
  sword 1       ;classe_variavel inteiro a em 16384
  sword 0       ;classe_variavel inteiro b em 16386
  sword ?     ;classe_variavel inteiro c em 16388
dseg ENDS ;fim seg. dados
cseg SEGMENT PUBLIC ;in�cio seg. c�digo
  ASSUME CS:cseg, DS:dseg
strt:
  mov AX, dseg
  mov ds, AX
mov AX, 1 ; movi para AX um VALORCONST
mov DS:[0], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, DS:[0]; Atribuicao de valor para o FOR
mov DS:[16388], AX; Atribuicao de valor para o FOR
mov AX, 3 ; movi para AX um VALORCONST
mov DS:[2], AX ;MOVI PARA END o CONTEUDO DE AX
R0:
mov AX, DS:[16388]; Atribuicao para comparacao do FOR
mov BX, 3; Atribuição para comparcao do FOR
cmp AX,BX
jg R1
mov AX, DS:[16384]
mov bx, DS:[16386]
cmp AX, BX
jl R2
mov AX, 0
jmp R3
R2:
mov AX, 1
R3:
mov DS:[4], AX
mov BX, DS:[16384];
cmp BX, 0; Compara expressao do if
je R4
mov ax, DS:[16384]
mov di, 4 ;end. string temp.
mov cx, 0 ;contador
cmp ax,0 ;verifica sinal
jge R6 ;salta se numero positivo
mov bl, 2Dh ;senao, escreve sinal 
mov ds:[di], bl
add di, 1 ;incrementa indice
neg ax ;toma modulo do numero
R6:
mov bx, 10 ;divisor
R7:
add cx, 1 ;incrementa contador
mov dx, 0 ;estende 32bits p/ div.
idiv bx ;divide DXAX por BX
push dx ;empilha valor do resto
cmp ax, 0 ;verifica se quoc.  0
jne R7 ;se nao  0, continua
R8:
pop dx ;desempilha valor
add dx, 30h ;transforma em caractere
mov ds:[di],dl ;escreve caractere
add di, 1 ;incrementa base
add cx, -1 ;decrementa contador
cmp cx, 0 ;verifica pilha vazia
jne R8 ;se nao pilha vazia, loop
mov dl, 024h ;fim de string
mov ds:[di], dl ;grava '$'
mov dx, 4
mov ah, 09h
int 21h
jmp R5
R4:
mov ax, DS:[16386]
mov di, 4 ;end. string temp.
mov cx, 0 ;contador
cmp ax,0 ;verifica sinal
jge R9 ;salta se numero positivo
mov bl, 2Dh ;senao, escreve sinal 
mov ds:[di], bl
add di, 1 ;incrementa indice
neg ax ;toma modulo do numero
R9:
mov bx, 10 ;divisor
R10:
add cx, 1 ;incrementa contador
mov dx, 0 ;estende 32bits p/ div.
idiv bx ;divide DXAX por BX
push dx ;empilha valor do resto
cmp ax, 0 ;verifica se quoc.  0
jne R10 ;se nao  0, continua
R11:
pop dx ;desempilha valor
add dx, 30h ;transforma em caractere
mov ds:[di],dl ;escreve caractere
add di, 1 ;incrementa base
add cx, -1 ;decrementa contador
cmp cx, 0 ;verifica pilha vazia
jne R11 ;se nao pilha vazia, loop
mov dl, 024h ;fim de string
mov ds:[di], dl ;grava '$'
mov dx, 4
mov ah, 09h
int 21h
R5:
mov AX, DS:[16388]; Contador ++ do for
add AX, 1; Contador ++ do for
mov DS:[16388], AX; Contador ++ do for
jmp R0
R1:
mov ah, 4Ch
int 21h
cseg ENDS ;fim seg. c�digo
END strt ;fim programa
