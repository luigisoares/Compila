sseg SEGMENT STACK ;início seg. pilha
  byte 4000h DUP(?) ;dimensiona pilha
sseg ENDS ;fim seg. pilha
dseg SEGMENT PUBLIC ;início seg. dados
  byte 4000h DUP(?) ;temporários
  sword ?     ;classe_variavel inteiro a em 16384
  sword 10 DUP(?)      ;classe_variavel vet inteiro b em 16386
dseg ENDS ;fim seg. dados
cseg SEGMENT PUBLIC ;início seg. código
  ASSUME CS:cseg, DS:dseg
strt:
  mov AX, dseg
  mov ds, AX
mov AX, 0 ; movi para AX um VALORCONST
mov DS:[0], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, DS:[16386];   Endereco inicial do vetor
mov BX, DS:[0];   Endereco da expressao
add BX,BX;   Inteiros ocupam 2 bytes
add AX, BX;  Posicao inicial do vetor + posicao desejada
mov DS:[2], AX;  FINAL
mov AX, 1 ; movi para AX um VALORCONST
mov DS:[2], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, DS:[2] ; peguei o end do exp talvez2 << end do simboloA
mov DS:[16386], AX; salvando o valor no endereco correto
mov AX, 1 ; movi para AX um VALORCONST
mov DS:[4], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, DS:[16386];   Endereco inicial do vetor
mov BX, DS:[4];   Endereco da expressao
add BX,BX;   Inteiros ocupam 2 bytes
add AX, BX;  Posicao inicial do vetor + posicao desejada
mov DS:[6], AX;  FINAL
mov AX, 2 ; movi para AX um VALORCONST
mov DS:[6], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, DS:[6] ; peguei o end do exp talvez6 << end do simboloA
mov DS:[16386], AX; salvando o valor no endereco correto
mov AX, 2 ; movi para AX um VALORCONST
mov DS:[8], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, DS:[16386];   Endereco inicial do vetor
mov BX, DS:[8];   Endereco da expressao
add BX,BX;   Inteiros ocupam 2 bytes
add AX, BX;  Posicao inicial do vetor + posicao desejada
mov DS:[10], AX;  FINAL
mov AX, 3 ; movi para AX um VALORCONST
mov DS:[10], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, DS:[10] ; peguei o end do exp talvez10 << end do simboloA
mov DS:[16386], AX; salvando o valor no endereco correto
mov AX, 3 ; movi para AX um VALORCONST
mov DS:[12], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, DS:[16386];   Endereco inicial do vetor
mov BX, DS:[12];   Endereco da expressao
add BX,BX;   Inteiros ocupam 2 bytes
add AX, BX;  Posicao inicial do vetor + posicao desejada
mov DS:[14], AX;  FINAL
mov AX, 6 ; movi para AX um VALORCONST
mov DS:[14], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, DS:[14] ; peguei o end do exp talvez14 << end do simboloA
mov DS:[16386], AX; salvando o valor no endereco correto
mov AX, 0 ; movi para AX um VALORCONST
mov DS:[16], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, DS:[16386];   Endereco inicial do vetor
mov BX, DS:[16];   Endereco da expressao
add BX,BX;   Inteiros ocupam 2 bytes
add AX, BX;  Posicao inicial do vetor + posicao desejada
mov DS:[18], AX;  FINAL
mov AX, 1 ; movi para AX um VALORCONST
mov DS:[18], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, DS:[16386];   Endereco inicial do vetor
mov BX, DS:[18];   Endereco da expressao
add BX,BX;   Inteiros ocupam 2 bytes
add AX, BX;  Posicao inicial do vetor + posicao desejada
mov DS:[20], AX;  FINAL
mov AX, DS:[18]
mov BX, DS:[20]
add AX, BX ; add de AX e BX
mov DS:[20], AX ; 
mov AX, DS:[20] ; peguei o end do exp talvez16386 << end do simboloA
mov DS:[16384], AX; salvando o valor no endereco correto
mov ax, DS:[16384]
mov di, 20 ;end. string temp.
mov cx, 0 ;contador
cmp ax,0 ;verifica sinal
jge R0 ;salta se numero positivo
mov bl, 2Dh ;senao, escreve sinal 
mov ds:[di], bl
add di, 1 ;incrementa indice
neg ax ;toma modulo do numero
R0:
mov bx, 10 ;divisor
R1:
add cx, 1 ;incrementa contador
mov dx, 0 ;estende 32bits p/ div.
idiv bx ;divide DXAX por BX
push dx ;empilha valor do resto
cmp ax, 0 ;verifica se quoc.  0
jne R1 ;se nao  0, continua
R2:
pop dx ;desempilha valor
add dx, 30h ;transforma em caractere
mov ds:[di],dl ;escreve caractere
add di, 1 ;incrementa base
add cx, -1 ;decrementa contador
cmp cx, 0 ;verifica pilha vazia
jne R2 ;se nao pilha vazia, loop
mov dl, 024h ;fim de string
mov ds:[di], dl ;grava '$'
mov dx, 20
mov ah, 09h
int 21h
mov ah, 4Ch
int 21h
cseg ENDS ;fim seg. código
END strt ;fim programa
