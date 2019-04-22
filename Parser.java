import java.io.BufferedReader;

public class Parser {
   AnalisadorLexico lexico;
   TabelaSimbolo tabela;
   Simbolo s;
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
               s = lexico.analisarLexema(lexico.devolve, arquivo);
            } else {
               if (lexico.ehEOF) {
                  System.err.println((lexico.linha + 1) + ":Fim de Arquivo nao esperado.");
                  System.exit(0);
               } else {
                  tokenInesperado();
               }
            }
         }
      } catch (Exception e) {
         checkEOF();
         System.err.println("casaT" + e.toString());
      }
   }

   void S() {
      try {
         if (s != null) {
            do {
               checkEOF();
               D();
            } while (ehDeclaracao());
            do {
               checkEOF();
               C();
            } while (ehComando());
         }
      } catch (Exception e) {
         checkEOF();
         System.err.println(e.toString());
      }
   }

   void D() {
      try {
         checkEOF();
         if (s.getToken() == tabela.VAR) {
            casaToken(tabela.VAR);
            if (s.getToken() == tabela.INTEGER) {
               casaToken(tabela.INTEGER);
            } else {
               casaToken(tabela.CHAR);
            }
         
            casaToken(tabela.ID);
            D1();
            casaToken(tabela.PV);
         } else {
            if(s.getToken() == tabela.CONST || s.getToken() == tabela.INTEGER || s.getToken() == tabela.CHAR){
               if(s.getToken() == tabela.CONST){
                  casaToken(tabela.CONST);
                  casaToken(tabela.ID);
                  casaToken(tabela.ATT);
                  CONSTV();
               } else {
                  if(s.getToken() == tabela.INTEGER){
                     casaToken(tabela.INTEGER);
                  } else if(s.getToken() == tabela.CHAR){
                     casaToken(tabela.CHAR);
                  }
                  casaToken(tabela.ID);
                  D1();
               }
               
               casaToken(tabela.PV);
            }
         }
      } catch (Exception e) {
         checkEOF();
         System.err.println(e.toString());
      }
   }

   void D1() {
      try {
         checkEOF();
         if (s.getToken() == tabela.VIR) {
            casaToken(tabela.VIR);
            casaToken(tabela.ID);
            if (s.getToken() == tabela.ACOL) {
               casaToken(tabela.ACOL);
               casaToken(tabela.VALORCONST);
               casaToken(tabela.FCOL);
            }
            if (s.getToken() == tabela.VIR) {
               while (s.getToken() != tabela.PV) {
                  casaToken(tabela.VIR);
                  casaToken(tabela.ID);
                  if (s.getToken() == tabela.ACOL) {
                     casaToken(tabela.ACOL);
                     casaToken(tabela.VALORCONST);
                     casaToken(tabela.FCOL);
                  }
               }
            }
         } else if (s.getToken() == tabela.ATT) {
            casaToken(tabela.ATT);
            if (s.getToken() == tabela.ADD) {
               casaToken(tabela.ADD);
            } else if (s.getToken() == tabela.SUB) {
               casaToken(tabela.SUB);
            }
         
            casaToken(tabela.VALORCONST);
         } else if (s.getToken() == tabela.ACOL) {
            casaToken(tabela.ACOL);
            casaToken(tabela.VALORCONST);
            casaToken(tabela.FCOL);
            D1();
         }
      
      } catch (Exception e) {
         checkEOF();
         System.err.println(e.toString());
      }
   }

   void CONSTV() {
      try {
         checkEOF();
         if (s.getToken() == tabela.VALORCONST) { // @TODO Como pegar o 0 ?
            casaToken(tabela.VALORCONST);
         	// casaToken(tabela.X); // @TODO Como pegar o X ?
         	// casaToken(tabela.HEXA); // @TODO Como pegar os hexa ?
         	// casaToken(tabela.HEXA); // @TODO Como pegar os hexa ?
         } else if (s.getToken() == tabela.CHAR) {
            casaToken(tabela.CHAR);
         } else if (s.getToken() == tabela.ASPAS) {
            casaToken(tabela.ASPAS);
            while (s.getToken() != tabela.ASPAS) {
            
            }
            casaToken(tabela.ASPAS);
         }
      } catch (Exception e) {
         checkEOF();
         System.err.println(e.toString());
      }
   }

   void C() {
      try {
         checkEOF();
         if (s.getToken() == tabela.ID) {
            casaToken(tabela.ID);
            C1();
            casaToken(tabela.PV);
         } else if (s.getToken() == tabela.FOR) {
            casaToken(tabela.FOR);
            casaToken(tabela.ID);
            casaToken(tabela.ATT);
            // casaToken(tabela.VALORCONST); // @TODOVITAO AQUI DEVERIA SER E()
            E();
            casaToken(tabela.TO);
            if(s.getToken() == tabela.ID) {
               casaToken(tabela.ID);
            } else {
               E();
               // casaToken(tabela.VALORCONST); // @TODOVITAO AQUI DEVERIA SER E()
            }
            if (s.getToken() == tabela.STEP) {
               casaToken(tabela.STEP);
               if (s.getToken() == tabela.VALORCONST) {
                  casaToken(tabela.VALORCONST); // @TODO Como pegar o num ?
               }
            }
            casaToken(tabela.DO);
            C2();
         } else if (s.getToken() == tabela.IF) {
            casaToken(tabela.IF);
            E();
            casaToken(tabela.THEN);
            C3();
            //casaToken(tabela.PV);
         } else if (s.getToken() == tabela.PV) {
            casaToken(tabela.PV);
         } else if (s.getToken() == tabela.READLN) {
            casaToken(tabela.READLN);
            casaToken(tabela.APAR);
            casaToken(tabela.ID);
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

   void C1() {
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

   void C2() {
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

   void C3() {
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
            if (s.getToken() == tabela.ELSE) {
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

   void E2() {
      try {
         checkEOF();
      
         F();
         while (s.getToken() == tabela.MUL || s.getToken() == tabela.DIV || s.getToken() == tabela.MOD
         		|| s.getToken() == tabela.AND) {
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
         System.err.println(lexico.linha + ":Fim de arquivo nao esperado.");
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

}