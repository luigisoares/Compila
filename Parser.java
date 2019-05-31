import java.io.BufferedReader;

public class Parser {
   AnalisadorLexico lexico;
   TabelaSimbolo tabela;
   Simbolo s, simboloParaAnalise;
   BufferedReader arquivo;

   Parser(BufferedReader arquivo) {
      try {
         this.arquivo = arquivo;
         lexico = new AnalisadorLexico();
         tabela = new TabelaSimbolo();
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
                  System.out.println(CONSTV().getTipo());
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
      try {
         checkEOF();
         if(s.getToken() == tabela.ATT || s.getToken() == tabela.ACOL || s.getToken() == tabela.VIR){
            if(s.getToken() == tabela.ATT){
               casaToken(tabela.ATT);
               CONSTV();
               if (s.getToken() == tabela.VIR) {
                  while (s.getToken() != tabela.PV) {
                     casaToken(tabela.VIR);
                     casaToken(tabela.ID);
                     acaoSemantica1(simboloParaAnalise);
                     if (s.getToken() == tabela.ACOL || s.getToken() == tabela.ATT) {
                        if (s.getToken() == tabela.ACOL){
                           casaToken(tabela.ACOL);
                           casaToken(tabela.VALORCONST);
                           casaToken(tabela.FCOL);
                        } else {
                           casaToken(tabela.ATT);
                           CONSTV();
                        }
                     }
                  }
               }
            } else if (s.getToken() == tabela.VIR) {
               while (s.getToken() != tabela.PV) {
                  casaToken(tabela.VIR);
                  casaToken(tabela.ID);
                  acaoSemantica1(simboloParaAnalise);
                  if (s.getToken() == tabela.ACOL || s.getToken() == tabela.ATT) {
                     if (s.getToken() == tabela.ACOL){
                        casaToken(tabela.ACOL);
                        casaToken(tabela.VALORCONST);
                        casaToken(tabela.FCOL);
                     } else {
                        casaToken(tabela.ATT);
                        CONSTV();
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
                     if (s.getToken() == tabela.ACOL || s.getToken() == tabela.ATT) {
                        if (s.getToken() == tabela.ACOL){
                           casaToken(tabela.ACOL);
                           casaToken(tabela.VALORCONST);
                           casaToken(tabela.FCOL);
                        } else {
                           casaToken(tabela.ATT);
                           CONSTV();
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
         if (s.getToken() == tabela.VALORCONST || s.getToken() == tabela.HEXA) { // @TODO Como pegar o 0 ?
            if(s.getToken() == tabela.VALORCONST) {
               casaToken(tabela.VALORCONST); //HEXA
            } else {
               System.out.println(s.getLexema());
               casaToken(tabela.HEXA); //HEXA
               constvSimbolo.setTipo("tipo_caracter");
            }
         } else if (s.getToken() == tabela.CHAR) {
            casaToken(tabela.CHAR);
         } else{
            E();
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
            A();
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
               casaToken(tabela.VALORCONST); // @TODO Como pegar o num ? 
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
   void A() {
      try {
         checkEOF();
      
         if (s.getToken() == tabela.ATT) {
            casaToken(tabela.ATT);
            E();
         } else {
            casaToken(tabela.ACOL);
            E();
            casaToken(tabela.FCOL);
            casaToken(tabela.ATT);
            E();
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
            if (s.getToken() == tabela.ELSE) {
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
   void E() {
      try {
         checkEOF();
      
         E1();
         if (s.getToken() == tabela.MAIOR || s.getToken() == tabela.MENOR || s.getToken() == tabela.MAIORIG
         		|| s.getToken() == tabela.MENORIG || s.getToken() == tabela.DIFF || s.getToken() == tabela.ATT) {
            if(s.getToken() == tabela.MAIOR){
               casaToken(tabela.MAIOR);
            } else if(s.getToken() == tabela.MENOR){
               casaToken(tabela.MENOR);
            } else if(s.getToken() == tabela.MAIORIG){
               casaToken(tabela.MAIORIG);
            } else if(s.getToken() == tabela.MENORIG){
               casaToken(tabela.MENORIG);
            } else if(s.getToken() == tabela.DIFF){
               casaToken(tabela.DIFF);
            } else if(s.getToken() == tabela.ATT){
               casaToken(tabela.ATT);
            }
               
            E1();
         }
      
      } catch (Exception e) {
         checkEOF();
         System.err.println(e.toString());
      }
   }

   //E'		-> [+ | -] E'' {('+' | '-' | or ) E''}
   void E1() {
      try {
         checkEOF();
      
         if (s.getToken() == tabela.ADD) {
            casaToken(tabela.ADD);
         } else if (s.getToken() == tabela.SUB) {
            casaToken(tabela.SUB);
         }
      
         E2();
         while (s.getToken() == tabela.ADD || s.getToken() == tabela.SUB || s.getToken() == tabela.OR || s.getToken() == tabela.MUL) {
         //if (s.getToken() == tabela.ADD || s.getToken() == tabela.SUB || s.getToken() == tabela.OR) {
            if (s.getToken() == tabela.ADD) {
               casaToken(tabela.ADD);
            } else if (s.getToken() == tabela.SUB) {
               casaToken(tabela.SUB);
            } else {
               casaToken(tabela.OR);
            }
            E2();
         }
      
      } catch (Exception e) {
         checkEOF();
         System.err.println(e.toString());
      }
   }

   //E''		-> F {('*' | '/' | '%' | and) F}
   void E2() {
      try {
         checkEOF();
      
         F();
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
            F();
         }
      
      } catch (Exception e) {
         checkEOF();
         System.err.println(e.toString());
      }
   }

   //F		-> '(' E ')' | not F | id ['[' E ']']| num
   void F() {
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
         } else {
            casaToken(tabela.ID);
            acaoSemantica3(simboloParaAnalise);
            if (s.getToken() == tabela.ACOL){
               casaToken(tabela.ACOL);
               casaToken(tabela.VALORCONST);
               casaToken(tabela.FCOL);
            }
         } 
      
      } catch (Exception e) {
         checkEOF();
         System.err.println(e.toString());
      }
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

   void acaoSemantica50(Simbolo simbolo, boolean condicao) {
      if(condicao) {
         simbolo.setTipo("tipo_caracter");
      } else {
         simbolo.setTipo("tipo_inteiro");
      }
   }

}