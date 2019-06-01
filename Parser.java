import java.io.BufferedReader;

public class Parser {
   AnalisadorLexico lexico;
   TabelaSimbolo tabela;
   Simbolo s, simboloParaAnalise;
   BufferedReader arquivo;

   Parser(BufferedReader arquivo) {
      try {
         this.arquivo = arquivo;
         tabela = new TabelaSimbolo();
         lexico = new AnalisadorLexico(tabela);
         s = lexico.analisarLexema(lexico.devolve, arquivo);
         if (s == null) { // comentario
            s = lexico.analisarLexema(lexico.devolve, arquivo);
         }
      } catch (Exception e) {
         checkEOF();
         System.out.print(e.getMessage());
      }
   }

   void casaToken(byte token) {
      try {
         if (s != null) {
            if (s.getToken() == token) {
               simboloParaAnalise = s;
               s = lexico.analisarLexema(lexico.devolve, arquivo);
            } else {
               if (lexico.ehEOF) {
                  System.err.println((lexico.linha + 1) + ":Fim de Arquivo nao esperado.");
                  System.exit(0);
               } else {
                  tokenInesperado();
               }
            }
         } else {
            checkEOF();
         }
      } catch (Exception e) {
         checkEOF();
         System.err.println("casaT" + e.toString());
      }
   }
   
   //S 		-> {D}+{C}+
   void S() {
      try 
      {
         if (s != null) 
         {
            do
            {
               checkEOF();
               D();
            }while(ehDeclaracao());
            do
            {
               checkEOF();
               C();
            }while(ehComando());
         }            
      } 
      catch (Exception e) 
      {
         checkEOF();
         System.err.println(e.toString());
      }
   }

   //D 		-> VAR {(integer | char) id [D'] ';'}+ | CONST id( = CONSTV' | '['num']' = '"' string '"') ';'
   void D() {
      boolean condicao;
      try {
         checkEOF();
         if (s.getToken() == tabela.VAR) {
            casaToken(tabela.VAR);
            if (s.getToken() == tabela.INTEGER) {
               casaToken(tabela.INTEGER);
               condicao = acaoSemantica9();
            
            } else {
               casaToken(tabela.CHAR);
               condicao = acaoSemantica10();
            }         
            casaToken(tabela.ID);
            acaoSemantica1(simboloParaAnalise);
            acaoSemantica50(simboloParaAnalise,condicao);
            D1(simboloParaAnalise);
            casaToken(tabela.PV);
         } else if(s.getToken() == tabela.CONST){
            casaToken(tabela.CONST);
            casaToken(tabela.ID);
            acaoSemantica2(simboloParaAnalise);
            if(s.getToken() == tabela.ATT){
               if(s.getToken() == tabela.ATT){
                  casaToken(tabela.ATT);
                  CONSTV();
               }  
            } else{
               casaToken(tabela.ACOL);
               casaToken(tabela.VALORCONST); //@TODO NUM
               casaToken(tabela.FCOL);
               casaToken(tabela.ATT);
               casaToken(tabela.ASPAS);
               casaToken(tabela.VALORCONST); //@TODO STRING
               casaToken(tabela.ASPAS);
            }    
            casaToken(tabela.PV);  
         } 
         else if (s.getToken() == tabela.INTEGER || s.getToken() == tabela.CHAR){
            if (s.getToken() == tabela.INTEGER) {
               casaToken(tabela.INTEGER);
               condicao = acaoSemantica9();
            } else {
               casaToken(tabela.CHAR);
               condicao = acaoSemantica10();
            }         
            casaToken(tabela.ID);
            acaoSemantica1(simboloParaAnalise);
            acaoSemantica50(simboloParaAnalise, condicao);
            D1(simboloParaAnalise);
            casaToken(tabela.PV);
         }
      } catch (Exception e) {
         checkEOF();
         System.err.println(e.toString());
      }
   }

   //D' 		-> [= CONSTV]{,id[ = CONSTV | '['num']']} | '['num']'{,id[ = CONSTV | '['num']']}
   void D1(Simbolo id) {
      Simbolo temp;
      try {
         checkEOF();
         if(s.getToken() == tabela.ATT || s.getToken() == tabela.ACOL || s.getToken() == tabela.VIR){
            if(s.getToken() == tabela.ATT){
               casaToken(tabela.ATT);
               temp = CONSTV();
               acaoSemantica42(id, temp);
               if (s.getToken() == tabela.VIR) {
                  while (s.getToken() != tabela.PV) {
                     casaToken(tabela.VIR);
                     casaToken(tabela.ID);
                     acaoSemantica1(simboloParaAnalise);
                     acaoSemantica51(id,simboloParaAnalise);
                     if (s.getToken() == tabela.ACOL || s.getToken() == tabela.ATT) {
                        if (s.getToken() == tabela.ACOL){
                           casaToken(tabela.ACOL);
                           casaToken(tabela.VALORCONST);
                           casaToken(tabela.FCOL);
                        } else {
                           casaToken(tabela.ATT);
                           temp = CONSTV();
                           acaoSemantica42(id, temp);
                        }
                     }
                  }
               }
            } else if (s.getToken() == tabela.VIR) {
               while (s.getToken() != tabela.PV) {
                  casaToken(tabela.VIR);
                  casaToken(tabela.ID);
                  acaoSemantica1(simboloParaAnalise);
                  acaoSemantica51(id,simboloParaAnalise);
                  if (s.getToken() == tabela.ACOL || s.getToken() == tabela.ATT) {
                     if (s.getToken() == tabela.ACOL){
                        casaToken(tabela.ACOL);
                        casaToken(tabela.VALORCONST);
                        casaToken(tabela.FCOL);
                     } else {
                        casaToken(tabela.ATT);
                        temp = CONSTV();
                        acaoSemantica42(id, temp);
                     }
                  }
               }
            } else if(s.getToken() == tabela.ACOL){
               casaToken(tabela.ACOL);
               casaToken(tabela.VALORCONST);
               casaToken(tabela.FCOL);
               if (s.getToken() == tabela.VIR) {
                  while (s.getToken() != tabela.PV) {
                     casaToken(tabela.VIR);
                     casaToken(tabela.ID);
                     acaoSemantica1(simboloParaAnalise);
                     acaoSemantica51(id,simboloParaAnalise);
                     if (s.getToken() == tabela.ACOL || s.getToken() == tabela.ATT) {
                        if (s.getToken() == tabela.ACOL){
                           casaToken(tabela.ACOL);
                           casaToken(tabela.VALORCONST);
                           casaToken(tabela.FCOL);
                        } else {
                           casaToken(tabela.ATT);
                           temp = CONSTV();
                           acaoSemantica42(id, temp);
                        }
                     }
                  }
               }
            }
         }
      } catch (Exception e) {
         checkEOF();
         System.err.println(e.toString());
      }
   }

   //CONSTV 	-> 0x(hexa)(hexa) | char | E
   Simbolo CONSTV() {
      Simbolo constvSimbolo = new Simbolo();
      try {
         checkEOF();
         if(s.getToken() == tabela.VALORCONST) {
            casaToken(tabela.VALORCONST);
            constvSimbolo.setTipo(simboloParaAnalise.getTipo());
         } else{
            constvSimbolo = E(); //acaoSemantica43
         }
      } catch (Exception e) {
         checkEOF();
         System.err.println(e.toString());
      }
      return constvSimbolo;
   }

   //CONSTV' -> 0x(hexa)(hexa) | char | [-] num
   void CONSTV1() {
      try {
         checkEOF();
         if (s.getToken() == tabela.VALORCONST) { // @TODO Como pegar o 0 ?
            casaToken(tabela.VALORCONST); //HEXA
         	// casaToken(tabela.X); // @TODO Como pegar o X ?
         	// casaToken(tabela.HEXA); // @TODO Como pegar os hexa ?
         	// casaToken(tabela.HEXA); // @TODO Como pegar os hexa ?
         } else if (s.getToken() == tabela.CHAR) {
            casaToken(tabela.CHAR);
         } else if (s.getToken() == tabela.SUB || s.getToken() == tabela.VALORCONST) {
            if (s.getToken() == tabela.SUB) {
               casaToken(tabela.SUB);
            }   
            casaToken(tabela.VALORCONST);
         }
      } catch (Exception e) {
         checkEOF();
         System.err.println(e.toString());
      }
   }

   //C		-> id C' ';'| FOR id = E to E [step num] do C'' | if E then C''' | ';' | readln'('id')'';' | write'('E{,E}')'';' | writeln'('E{,E}')'';'
   void C() {
      try {
         checkEOF();
         if (s.getToken() == tabela.ID) {
            casaToken(tabela.ID);
            acaoSemantica3(simboloParaAnalise);
            A(simboloParaAnalise);
            casaToken(tabela.PV);
         } else if (s.getToken() == tabela.FOR) {
            casaToken(tabela.FOR);
            casaToken(tabela.ID);
            acaoSemantica3(simboloParaAnalise);
            casaToken(tabela.ATT);
            E();
            //casaToken(tabela.VALORCONST); // @TODOVITAO AQUI DEVERIA SER E()
            casaToken(tabela.TO);
            if(s.getToken() == tabela.ID) {
               casaToken(tabela.ID);
               acaoSemantica3(simboloParaAnalise);
            } else {
               E();
               //casaToken(tabela.VALORCONST); // @TODOVITAO AQUI DEVERIA SER E()
            }
            if (s.getToken() == tabela.STEP) {
               casaToken(tabela.STEP);
               casaToken(tabela.ID); // @TODO Como pegar o num ? 
               acaoSemantica3(simboloParaAnalise);
            }
            casaToken(tabela.DO);
            H();
         } else if (s.getToken() == tabela.IF) {
            casaToken(tabela.IF);
            E();
            casaToken(tabela.THEN);
            J();
            //casaToken(tabela.PV);
         } else if (s.getToken() == tabela.PV) {
            casaToken(tabela.PV);
         } else if (s.getToken() == tabela.READLN) {
            casaToken(tabela.READLN);
            casaToken(tabela.APAR);
            casaToken(tabela.ID);
            acaoSemantica3(simboloParaAnalise);
            casaToken(tabela.FPAR);
            casaToken(tabela.PV);
         } else if (s.getToken() == tabela.WRITELN) {
            casaToken(tabela.WRITELN);
            casaToken(tabela.APAR);
            E();
            while(s.getToken() == tabela.VIR) {
               casaToken(tabela.VIR);
               E();
            }
            casaToken(tabela.FPAR);
            casaToken(tabela.PV);
         } else if (s.getToken() == tabela.WRITE) {
            casaToken(tabela.WRITE);
            casaToken(tabela.APAR);
            E();
            while(s.getToken() == tabela.VIR) {
               casaToken(tabela.VIR);
               E();
            }
            casaToken(tabela.FPAR);
            casaToken(tabela.PV);
         } else {
            tokenInesperado();
         }
      
      } catch (Exception e) {
         checkEOF();
         System.err.println(e.toString());
      }
   }

   //A		-> = E | '['E']' = E
   void A(Simbolo id) {
      Simbolo simboloA = new Simbolo();
      Simbolo simboloA1 = new Simbolo();
      Simbolo simboloA2 = new Simbolo();
   
      try {
         checkEOF();
      
         if (s.getToken() == tabela.ATT) {
            casaToken(tabela.ATT);
            acaoSemantica5(id);
            simboloA = E(); //acaoSemantica49
         } else {
            casaToken(tabela.ACOL);
            simboloA1 = E();
            casaToken(tabela.FCOL);
            casaToken(tabela.ATT);
            simboloA2 = E(); //acaoSemantica49
         }
      } catch (Exception e) {
         checkEOF();
         System.err.println(e.toString());
      }
   }

   //H		-> C | '{' {C} '}'
   void H() {
      try {
         checkEOF();
      
         if (s.getToken() == tabela.ACHAVE) {
            casaToken(tabela.ACHAVE);
            while(ehComando()) {
               C();
            }	
            casaToken(tabela.FCHAVE);
         } else {
            C();
         }
      } catch (Exception e) {
         checkEOF();
         System.err.println(e.toString());
      }
   }

   //J	-> C [else ('{' {C} '}' || C)] | '{' {C} '}' [else ('{' {C} '}' || C)]
   void J() {
      try {
         checkEOF();
      
         if (s.getToken() == tabela.ACHAVE) {
            casaToken(tabela.ACHAVE);
            do{
               C();
            }while(ehComando());
            casaToken(tabela.FCHAVE);
            if (s != null && s.getToken() == tabela.ELSE) { // caso o opcional seja no  EOF
               casaToken(tabela.ELSE);
               if(s.getToken() == tabela.ACHAVE){
                  casaToken(tabela.ACHAVE);
                  do{
                     C();
                  }while(ehComando());
                  casaToken(tabela.FCHAVE);
               }else {
                  C();
               }
            } 
         } else {
            C();
            if (s != null && s.getToken() == tabela.ELSE) {
               casaToken(tabela.ELSE);
               if(s.getToken() == tabela.ACHAVE){
                  casaToken(tabela.ACHAVE);
                  C();
                  casaToken(tabela.FCHAVE);
               }else {
                  C();
               }
            } 
         }
      } catch (Exception e) {
         checkEOF();
         System.err.println(e.toString());
      }
   }

   //E		-> E' {('<' | '>' | '<=' | '>=' | '<>' | '=') E'}
   Simbolo E() {
      Simbolo simboloE = new Simbolo();
      Simbolo simboloE2 = new Simbolo();
      boolean condicao;
      try {
         checkEOF();
         
         simboloE = E1(); // acaoSemantica7
         if (s.getToken() == tabela.MAIOR || s.getToken() == tabela.MENOR || s.getToken() == tabela.MAIORIG
         		|| s.getToken() == tabela.MENORIG || s.getToken() == tabela.DIFF || s.getToken() == tabela.ATT) {
            condicao = acaoSemantica9();
            if(s.getToken() == tabela.MAIOR){
               casaToken(tabela.MAIOR);              
               acaoSemantica8(simboloE);
            } else if(s.getToken() == tabela.MENOR){
               casaToken(tabela.MENOR);
               acaoSemantica8(simboloE);
            } else if(s.getToken() == tabela.MAIORIG){
               casaToken(tabela.MAIORIG);
               acaoSemantica8(simboloE);
            } else if(s.getToken() == tabela.MENORIG){
               casaToken(tabela.MENORIG);
               acaoSemantica8(simboloE);
            } else if(s.getToken() == tabela.DIFF){
               casaToken(tabela.DIFF);
               acaoSemantica8(simboloE);
            } else if(s.getToken() == tabela.ATT){
               casaToken(tabela.ATT);
               condicao = acaoSemantica10();
            }
               
            simboloE2 = E1();
            acaoSemantica11(simboloE,simboloE2);
            acaoSemantica12(simboloE,condicao);
            simboloE.setTipo("tipo_logico"); //acaosemantica47
         }
      
      } catch (Exception e) {
         checkEOF();
         System.err.println(e.toString());
      }
      
         
      return simboloE;
   }

   //E'		-> [+ | -] E'' {('+' | '-' | or ) E''}
   Simbolo E1() {
      Simbolo simboloE1 = new Simbolo();
      Simbolo simboloE1_2 = new Simbolo();
      boolean condicao;
      int operacao = 0; /* 1 para add , 2 para sub , 3 para or, 0 default */
      try {
         checkEOF();
         condicao = acaoSemantica9();
         if (s.getToken() == tabela.ADD) {
            casaToken(tabela.ADD);
            condicao = acaoSemantica10();
         } else if (s.getToken() == tabela.SUB) {
            casaToken(tabela.SUB);
            condicao = acaoSemantica10();
         }
         simboloE1 = E2(); // acaoSemantica14
         acaoSemantica13(simboloE1,condicao);
         while (s.getToken() == tabela.ADD || s.getToken() == tabela.SUB || s.getToken() == tabela.OR || s.getToken() == tabela.MUL) {
         //if (s.getToken() == tabela.ADD || s.getToken() == tabela.SUB || s.getToken() == tabela.OR) {
            if (s.getToken() == tabela.ADD) {
               casaToken(tabela.ADD);
               operacao=acaoSemantica15(simboloE1);
            } else if (s.getToken() == tabela.SUB) {
               casaToken(tabela.SUB);
               operacao=acaoSemantica16(simboloE1);
            } else {
               casaToken(tabela.OR);
               operacao=acaoSemantica17(simboloE1);
            }
            simboloE1_2 = E2();
            acaoSemantica18(simboloE1,simboloE1_2);
            acaoSemantica19(simboloE1_2,operacao);
         }
         
      } catch (Exception e) {
         checkEOF();
         System.err.println(e.toString());
      }
      
      return simboloE1;
      
   }

   //E''		-> F {('*' | '/' | '%' | and) F}
   Simbolo E2() {
      Simbolo simboloE2 = new Simbolo();
      Simbolo simboloE2_1 = new Simbolo();
      try {
         checkEOF();
      
         simboloE2 = F(); // acaoSemantica20
         while (s.getToken() == tabela.MUL || s.getToken() == tabela.DIV || s.getToken() == tabela.MOD || s.getToken() == tabela.AND) {
         //if (s.getToken() == tabela.MUL || s.getToken() == tabela.DIV || s.getToken() == tabela.MOD || s.getToken() == tabela.AND) {
            if(s.getToken() == tabela.MUL){
               casaToken(tabela.MUL);
            }else if(s.getToken() == tabela.DIV){
               casaToken(tabela.DIV);
            }else if(s.getToken() == tabela.MOD){
               casaToken(tabela.MOD);
            }else{
               casaToken(tabela.AND);
            }
            simboloE2_1 = F();
         }
      
      } catch (Exception e) {
         checkEOF();
         System.err.println(e.toString());
      }
         
      return simboloE2;
      
   }

   //F		-> '(' E ')' | not F | id ['[' E ']']| num
   Simbolo F() {
      Simbolo simboloF = new Simbolo();
      Simbolo simboloF1 = new Simbolo();
      try {
      
         checkEOF();
      
         if (s.getToken() == tabela.APAR) {
            casaToken(tabela.APAR);
            E();
            casaToken(tabela.FPAR);
         } else if (s.getToken() == tabela.NOT) {
            casaToken(tabela.NOT);
            F();
         } else if (s.getToken() == tabela.VALORCONST) {
            casaToken(tabela.VALORCONST);
            // \/ acaoSemantica29
            simboloF = new Simbolo(simboloParaAnalise.getToken(),simboloParaAnalise.getLexema(),simboloParaAnalise.getEndereco(),simboloParaAnalise.getTipo(),"classe_variavel",0);
         } else {
            casaToken(tabela.ID);
            acaoSemantica3(simboloParaAnalise);
            // acaoSemantica30
            simboloF = lexico.simbolos.buscaSimbolo(simboloParaAnalise.getLexema());
            if (s.getToken() == tabela.ACOL){
               casaToken(tabela.ACOL);
               //casaToken(tabela.VALORCONST); @TODO VITAO
               E();
               casaToken(tabela.FCOL);
            }
         } 
      
      } catch (Exception e) {
         checkEOF();
         System.err.println(e.toString());
      }
         
      return simboloF;
      
   }

   void checkEOF() {
      if (lexico.ehEOF) {
         System.err.println((lexico.linha + 1) + ":Fim de arquivo nao esperado.");
         System.exit(0);
      }
   }

   void tokenInesperado() {
      System.err.println((lexico.linha + 1) + ":Token nao esperado: " + s.getLexema());
      System.exit(0);
   }

   boolean ehDeclaracao() {
      return (s != null && (s.getToken() == tabela.VAR || s.getToken() == tabela.CONST ||  s.getToken() == tabela.INTEGER ||  s.getToken() == tabela.CHAR));
   }

   boolean ehComando() {
      return (s != null && (s.getToken() == tabela.ID || s.getToken() == tabela.FOR || s.getToken() == tabela.IF
         	|| s.getToken() == tabela.PV || s.getToken() == tabela.READLN || s.getToken() == tabela.WRITELN
         	|| s.getToken() == tabela.WRITE));
   }

   void acaoSemantica1(Simbolo simbolo) {
      if(!simbolo.getClasse().equals("")) {
         System.out.println((lexico.linha + 1) + ":identificador ja declarado " + simbolo.getLexema());
         System.exit(0);
      }
   
      simbolo.setClasse("classe_variavel");
   }

   void acaoSemantica2(Simbolo simbolo) {
      if(!simbolo.getClasse().equals("")) {
         System.out.println((lexico.linha + 1) + ":identificador ja declarado " + simbolo.getLexema());
         System.exit(0);
      }
   
      simbolo.setClasse("classe_constante");
   }

   void acaoSemantica3(Simbolo simbolo) {
      if(simbolo.getClasse().equals("")) {
         System.out.println((lexico.linha + 1) + ":identificador nao declarado " + simbolo.getLexema());
         System.exit(0);
      }
   }

   boolean acaoSemantica9() {
      return false;
   }

   boolean acaoSemantica10() {
      return true;
   }

   // Implementada por passagem de parametros do metodo D1
   // void acaoSemantica40(Simbolo id, Simbolo D1) {
   //    D1.setTipo(id.getTipo());  
   // }
   
   // Implementada por passagem de parametros do metodo A
   // void acaoSemantica4(Simbolo id, Simbolo A) {
   //    D1.setTipo(id.getTipo());  
   // }


   void acaoSemantica42(Simbolo simbolo1, Simbolo simbolo2) {
      if(!simbolo1.getTipo().equals(simbolo2.getTipo())) {
         System.out.println((lexico.linha + 1) + ":tipos incompativeis");
         System.exit(0);
      }
   }

   void acaoSemantica50(Simbolo simbolo, boolean condicao) {
      if(condicao) {
         simbolo.setTipo("tipo_caracter");
      } else {
         simbolo.setTipo("tipo_inteiro");
      }
   }

   void acaoSemantica8(Simbolo simbolo){
      if(simbolo.getTipo() != "tipo_inteiro"){
         System.out.println((lexico.linha + 1) + ":tipos incompativeis");
         System.exit(0);
      }
   }
   
   void acaoSemantica5(Simbolo simbolo){
      if(simbolo.getClasse() == "classe_constante"){
         System.out.println((lexico.linha + 1) + ":classe de identificador incompativel "+simbolo.getLexema());
         System.exit(0);
      }
   }
   
   void acaoSemantica11(Simbolo exps1,Simbolo exps2){
      if(exps1.getTipo() != exps2.getTipo()){
         System.out.println((lexico.linha + 1) + ":tipos incompativeis");
         System.exit(0);
      }
   }
   
   void acaoSemantica12(Simbolo exps1,boolean condicao){
      if(exps1.getTipo() == "tipo_string" && condicao == false){
         System.out.println((lexico.linha + 1) + ":tipos incompativeis");
         System.exit(0);
      }
   }
   
   void acaoSemantica13(Simbolo expt1,boolean condicao){
      if(expt1.getTipo() != "tipo_inteiro" && condicao == true){
         System.out.println((lexico.linha + 1) + ":tipos incompativeis");
         System.exit(0);
      }
   }
   
   int acaoSemantica15(Simbolo expt1){
      if(expt1.getTipo() != "tipo_inteiro"){
         System.out.println((lexico.linha + 1) + ":tipos incompativeis");
         System.exit(0);
      } else {
         return 1;
      }
      return 0;
   }
   
   int acaoSemantica16(Simbolo expt1){
      if(expt1.getTipo() != "tipo_inteiro"){
         System.out.println((lexico.linha + 1) + ":tipos incompativeis");
         System.exit(0);
      }else {
         return 2;
      }
      return 0;
   }
   
   int acaoSemantica17(Simbolo expt1){
      if(expt1.getTipo() != "tipo_logico"){
         System.out.println((lexico.linha + 1) + ":tipos incompativeis");
         System.exit(0);
      }else {
         return 3;
      }
      return 0;
   }
   
   void acaoSemantica18(Simbolo expt1,Simbolo expt2){
      if(expt1.getTipo() != expt2.getTipo()){
         System.out.println((lexico.linha + 1) + ":tipos incompativeis");
         System.exit(0);
      }
   }
   
   void acaoSemantica19(Simbolo expt2,int operacao){
   /* 1 para add , 2 para sub , 3 para or, 0 default */
      if(expt2.getTipo() != "tipo_logico" && operacao == 3 ||
      expt2.getTipo() != "tipo_inteiro" && operacao == 2 ||
      expt2.getTipo() != "tipo_inteiro" && operacao == 1 ){
         System.out.println((lexico.linha + 1) + ":tipos incompativeis");
         System.exit(0);
      }
   }
   
   void acaoSemantica51(Simbolo pai, Simbolo filhoID ) {
      filhoID.setTipo(pai.getTipo());
   }

}