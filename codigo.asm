sseg SEGMENT STACK ;início seg. pilha
  byte 4000h DUP(?) ;dimensiona pilha
sseg ENDS ;fim seg. pilha
dseg SEGMENT PUBLIC ;início seg. dados
  byte 4000h DUP(?) ;temporários
  byte 20 DUP(?)       ;classe_variavel vet char luigi em 16384
  byte 95     ;classe_variavel char tanguinha em 16404
  sword 20 DUP(?)      ;classe_variavel vet inteiro salaminho em 16405
  byte 'a'     ;classe_variavel char a em 16445
  sword 1       ;classe_variavel inteiro n em 16446
  sword ?     ;classe_variavel inteiro h em 16448h
  sword 4       ;classe_variavel inteiro j em 16450
  byte 20 DUP(?)       ;classe_variavel vet char teste em 16452
  sword 1       ;classe_constante inteiro b em 16472
mov AX, 3 ; movi para AX um VALORCONST
mov DS:[2], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, DS:[2] ;
neg AX
mov DS:[2], AX
  sword 3       ;classe_constante inteiro c em 16474
mov AX, 5 ; movi para AX um VALORCONST
mov DS:[4], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, DS:[4] ;
neg AX
mov DS:[4], AX
  sword 5       ;classe_constante inteiro d em 16476
  byte 'c'     ;classe_constante char e em 16478
dseg ENDS ;fim seg. dados
cseg SEGMENT PUBLIC ;início seg. código
  ASSUME CS:cseg, DS:dseg
strt:
  mov AX, dseg
  mov ds, AX
dseg SEGMENT PUBLIC
byte "tails e sonic$"; constante string
dseg ENDS
mov AX, DS:[20] ; peguei o end do exp talvez4 << end do simboloA
mov DS:[16452], AX; salvando o valor no endereco correto
mov AX, 1 ; movi para AX um VALORCONST
mov DS:[22], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, 10 ; movi para AX um VALORCONST
mov DS:[24], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, 100 ; movi para AX um VALORCONST
mov DS:[25], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, DS:[16445]
mov bx, DS:[25]
cmp AX, BX
je R0
mov AX, 0
jmp R1
R0:
mov AX, 0FFh
R1:
mov DS:[25], AX
mov AX, 97 ; movi para AX um VALORCONST
mov DS:[26], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, 98 ; movi para AX um VALORCONST
mov DS:[27], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, DS:[26]
mov BX, DS:[27]
add AX, BX ; add de AX e BX
mov DS:[27], AX ; 
mov AX, DS:[27] ; peguei o end do exp talvez25 << end do simboloA
mov DS:[16445], AX; salvando o valor no endereco correto
mov AX, 1 ; movi para AX um VALORCONST
mov DS:[29], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, DS:[16446]
mov BX, DS:[29]
add AX, BX ; add de AX e BX
mov DS:[29], AX ; 
mov AX, DS:[29] ; peguei o end do exp talvez16446 << end do simboloA
mov DS:[16446], AX; salvando o valor no endereco correto
mov AX, 1 ; movi para AX um VALORCONST
mov DS:[31], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, DS:[16446]
mov bx, DS:[31]
cmp AX, BX
jg R2
mov AX, 0
jmp R3
R2:
mov AX, 0FFh
R3:
mov DS:[31], AX
mov AX, DS:[16446]
mov bx, DS:[16472]
cmp AX, BX
jl R4
mov AX, 0
jmp R5
R4:
mov AX, 0FFh
R5:
mov DS:[31], AX
mov AX, DS:[16472]
mov BX, DS:[16446]
or AX, BX ; or
mov DS:[31], AX ; 
mov AX, 97 ; movi para AX um VALORCONST
mov DS:[32], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, 98 ; movi para AX um VALORCONST
mov DS:[33], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, DS:[32]
mov BX, DS:[33]
add AX, BX ; add de AX e BX
mov DS:[33], AX ; 
mov AX, DS:[33] ; peguei o end do exp talvez31 << end do simboloA
mov DS:[16445], AX; salvando o valor no endereco correto
mov AX, 1 ; movi para AX um VALORCONST
mov DS:[35], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, DS:[16446]
mov bx, DS:[35]
cmp AX, BX
jg R6
mov AX, 0
jmp R7
R6:
mov AX, 0FFh
R7:
mov DS:[35], AX
mov AX, DS:[16446]
mov bx, DS:[16472]
cmp AX, BX
jge R8
mov AX, 0
jmp R9
R8:
mov AX, 0FFh
R9:
mov DS:[35], AX
mov AX, 97 ; movi para AX um VALORCONST
mov DS:[36], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, 98 ; movi para AX um VALORCONST
mov DS:[37], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, DS:[36]
mov BX, DS:[37]
add AX, BX ; add de AX e BX
mov DS:[37], AX ; 
mov AX, DS:[37] ; peguei o end do exp talvez35 << end do simboloA
mov DS:[16445], AX; salvando o valor no endereco correto
mov AX, 1 ; movi para AX um VALORCONST
mov DS:[39], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, DS:[16446]
mov bx, DS:[39]
cmp AX, BX
jle R10
mov AX, 0
jmp R11
R10:
mov AX, 0FFh
R11:
mov DS:[39], AX
mov AX, 97 ; movi para AX um VALORCONST
mov DS:[40], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, 98 ; movi para AX um VALORCONST
mov DS:[41], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, DS:[40]
mov BX, DS:[41]
add AX, BX ; add de AX e BX
mov DS:[41], AX ; 
mov AX, DS:[41] ; peguei o end do exp talvez39 << end do simboloA
mov DS:[16445], AX; salvando o valor no endereco correto
mov AX, 1 ; movi para AX um VALORCONST
mov DS:[43], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, DS:[16446]
mov bx, DS:[43]
cmp AX, BX
jge R12
mov AX, 0
jmp R13
R12:
mov AX, 0FFh
R13:
mov DS:[43], AX
mov AX, 97 ; movi para AX um VALORCONST
mov DS:[44], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, 98 ; movi para AX um VALORCONST
mov DS:[45], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, DS:[44]
mov BX, DS:[45]
add AX, BX ; add de AX e BX
mov DS:[45], AX ; 
mov AX, DS:[45] ; peguei o end do exp talvez43 << end do simboloA
mov DS:[16445], AX; salvando o valor no endereco correto
mov AX, 1 ; movi para AX um VALORCONST
mov DS:[47], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, 10 ; movi para AX um VALORCONST
mov DS:[49], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, 12 ; movi para AX um VALORCONST
mov DS:[51], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, DS:[16446]
mov bx, DS:[51]
cmp AX, BX
jne R14
mov AX, 0
jmp R15
R14:
mov AX, 0FFh
R15:
mov DS:[51], AX
mov AX, 2 ; movi para AX um VALORCONST
mov DS:[53], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, DS:[53] ; peguei o end do exp talvez16446 << end do simboloA
mov DS:[16446], AX; salvando o valor no endereco correto
mov AX, 1 ; movi para AX um VALORCONST
mov DS:[55], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, 109 ; movi para AX um VALORCONST
mov DS:[56], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, 2 ; movi para AX um VALORCONST
mov DS:[58], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, 65 ; movi para AX um VALORCONST
mov DS:[59], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, 3 ; movi para AX um VALORCONST
mov DS:[61], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, 67 ; movi para AX um VALORCONST
mov DS:[62], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, 4 ; movi para AX um VALORCONST
mov DS:[64], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, 79 ; movi para AX um VALORCONST
mov DS:[65], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, 5 ; movi para AX um VALORCONST
mov DS:[67], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, 110 ; movi para AX um VALORCONST
mov DS:[68], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, 6 ; movi para AX um VALORCONST
mov DS:[70], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, 104 ; movi para AX um VALORCONST
mov DS:[71], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, 7 ; movi para AX um VALORCONST
mov DS:[73], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, 65 ; movi para AX um VALORCONST
mov DS:[74], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, 8 ; movi para AX um VALORCONST
mov DS:[76], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, 46 ; movi para AX um VALORCONST
mov DS:[77], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, 1 ; movi para AX um VALORCONST
mov DS:[79], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, 5 ; movi para AX um VALORCONST
mov DS:[81], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, DS:[81] ;
neg AX
mov DS:[81], AX
mov AX, 2 ; movi para AX um VALORCONST
mov DS:[83], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, 4 ; movi para AX um VALORCONST
mov DS:[85], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, DS:[85] ;
neg AX
mov DS:[85], AX
mov AX, 3 ; movi para AX um VALORCONST
mov DS:[87], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, 5 ; movi para AX um VALORCONST
mov DS:[89], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, 3 ; movi para AX um VALORCONST
mov DS:[91], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, DS:[91]
mov BX, DS:[89]
add AX, BX ; add de AX e BX
mov DS:[91], AX ; 
mov AX, 4 ; movi para AX um VALORCONST
mov DS:[93], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, 4 ; movi para AX um VALORCONST
mov DS:[95], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, 2 ; movi para AX um VALORCONST
mov DS:[97], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, 5 ; movi para AX um VALORCONST
mov DS:[99], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, 4 ; movi para AX um VALORCONST
mov DS:[101], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, 2 ; movi para AX um VALORCONST
mov DS:[103], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, DS:[103] ;
neg AX
mov DS:[103], AX
mov AX, DS:[103] ;
neg AX
mov DS:[103], AX
mov AX, 6 ; movi para AX um VALORCONST
mov DS:[105], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, 10 ; movi para AX um VALORCONST
mov DS:[107], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, DS:[107] ;
neg AX
mov DS:[107], AX
mov AX, 100 ; movi para AX um VALORCONST
mov DS:[108], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, DS:[16445]
mov bx, DS:[108]
cmp AX, BX
jne R16
mov AX, 0
jmp R17
R16:
mov AX, 0FFh
R17:
mov DS:[108], AX
mov AX, DS:[16445] ;a em 16445
neg AX
add AX,1
mov DS:[108], AX
mov AX, 1 ; movi para AX um VALORCONST
mov DS:[110], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, 1 ; movi para AX um VALORCONST
mov DS:[112], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, 5 ; movi para AX um VALORCONST
mov DS:[114], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, DS:[114] ;
neg AX
mov DS:[114], AX
mov AX, 1 ; movi para AX um VALORCONST
mov DS:[116], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, 1 ; movi para AX um VALORCONST
mov DS:[118], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, 5 ; movi para AX um VALORCONST
mov DS:[120], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, DS:[120] ;
neg AX
mov DS:[120], AX
mov AX, 1 ; movi para AX um VALORCONST
mov DS:[122], AX ;MOVI PARA END o CONTEUDO DE AX
mov AX, 10 ; movi para AX um VALORCONST
mov DS:[124], AX ;MOVI PARA END o CONTEUDO DE AX
mov ah, 4Ch
int 21h
cseg ENDS ;fim seg. código
END strt ;fim programa
