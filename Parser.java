import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;

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
						System.err.println(lexico.linha + ":Fim de Arquivo não esperado.");
						System.exit(0);
					} else {
						System.err.println(lexico.linha + ":Token não esperado: " + s.getLexema());
						System.exit(0);
					}
				}
			}
		} catch (Exception e) {
			System.err.println("casaT" + e.toString());
		}
	}

	void S() {
		try {
			if (s != null) {
				D();
				C();
				if (!lexico.ehEOF) {
					System.err.println(lexico.linha + ":Token não esperado: " + s.getLexema());
					System.exit(0);
				}
			}
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	void D() {
		try {
			checkEOF();

			if(s.getToken() == tabela.VAR) {
				casaToken(tabela.VAR);
				if(s.getToken() == tabela.INTEGER) {
					casaToken(tabela.INTEGER);
				} else {
					casaToken(tabela.CHAR);
				}

				casaToken(tabela.ID);
				D1();
				casaToken(tabela.PV);
			} else {
				casaToken(tabela.CONST);
				casaToken(tabela.ID);
				casaToken(tabela.ATT);
				CONSTV();
				casaToken(tabela.PV);
			}

		} catch(Exception e) {
			System.err.println(e.toString());
		}
	}

	void D1() {
		try {
			checkEOF();

			if(s.getToken() == tabela.VIR) {
				casaToken(tabela.VIR);
				casaToken(tabela.ID);
				if(s.getToken() == tabela.ACOL) {
					casaToken(tabela.ACOL);
					casaToken(tabela.NUM); // @TODO Como pegar o numero
					casaToken(tabela.ACOL);
				}
				if(s.getToken() == tabela.VIR) {
					while(s.getToken() != tabela.PV) {
						casaToken(tabela.VIR);
						casaToken(tabela.ID);
						if(s.getToken() == tabela.ACOL) {
							casaToken(tabela.ACOL);
							casaToken(tabela.NUM); // @TODO Como pegar o numero
							casaToken(tabela.ACOL);
						}	
					}
				}
			} else if(s.getToken() == tabela.ATT) {
				casaToken(tabela.ATT);
				if(s.getToken() == tabela.ADD) {
					casaToken(tabela.ADD);
				} else if(s.getToken() == tabela.SUB) {
					casaToken(tabela.SUB);
				}

				casaToken(tabela.NUM); // @TODO como pegar o numero
			} else if(s.getToken() == tabela.ACOL) {
				casaToken(tabela.ACOL);
				casaToken(tabela.NUM); // @TODO Como pegar o numero
				casaToken(tabela.FCHAVE);
			}

		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	void CONSTV() {
		try {
			checkEOF();
			if(s.getToken() == tabela.ZERO) { // @TODO Como pegar o 0 ?
				casaToken(tabela.ZERO);
				casaToken(tabela.X);	// @TODO Como pegar o X ?
				casaToken(tabela.HEXA);	// @TODO Como pegar os hexa ?
				casaToken(tabela.HEXA);	// @TODO Como pegar os hexa ?
			} else if(s.getToken() == tabela.CHAR) {
				casaToken(tabela.CHAR);
			} else if (s.getToken() == tabela.ASPAS) {
				casaToken(tabela.ASPAS);
				while(s.getToken() != tabela.ASPAS) {
					// @TODO O que fazer enquanto ele não termina de ler a string ?
				}
				casaToken(tabela.ASPAS);
			}
		} catch(Exception e){
			System.err.println(e.toString());
		}
	}

	void C() {
		try {
			checkEOF();

			if(s.getToken() == tabela.ID) {
				casaToken(tabela.ID);
				C1();
				casaToken(tabela.PV);
			} else if(s.getToken() == tabela.FOR) {
				casaToken(tabela.FOR);
				casaToken(tabela.ID);
				casaToken(tabela.ATT);
				casaToken(tabela.NUM); // @TODO Como pegar o num ?
				casaToken(tabela.TO);
				casaToken(tabela.NUM); // @TODO Como pegar o num ?
				if(s.getToken() == tabela.STEP) {
					casaToken(tabela.STEP);
				}
				casaToken(tabela.NUM); // @TODO Como pegar o num ?
				C2();
			} else if(s.getToken() == tabela.IF) {
				casaToken(tabela.IF);
				E();
				casaToken(tabela.THEN);
				C();
				// @ TODO como pegar os vários comandos e depois o else ?
			} else if(s.getToken() == tabela.PV) {
				casaToken(tabela.PV);
			} else if(s.getToken() == tabela.READLN) {
				casaToken(tabela.READLN);
				casaToken(tabela.APAR);
				casaToken(tabela.ID);
				casaToken(tabela.FPAR);
			} else if(s.getToken() == tabela.WRITELN) {
				casaToken(tabela.WRITELN);
				casaToken(tabela.APAR);
				E();
				// @TODO Como pegar várias expressões ?
				casaToken(tabela.FPAR);
			} else if(s.getToken() == tabela.WRITE) {
				casaToken(tabela.WRITE);
				casaToken(tabela.APAR);
				E();
				// @TODO Como pegar várias expressões ?
				casaToken(tabela.FPAR);
			}

		} catch(Exception e) {
			System.err.println(e.toString());
		}
	}

	void checkEOF() {
		if (lexico.ehEOF) {
			System.err.println(lexico.linha + ":Fim de arquivo não esperado.");
			System.exit(0);
		}
	}

}