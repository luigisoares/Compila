import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Parser{
    AnalisadorLexico lexico;
	TabelaSimbolos tabela;
	Simbolo s;
	BufferedReader arquivo;

    Parser(BufferedReader arquivo){
		try{
			this.arquivo = arquivo;
			lexico = new AnalisadorLexico();
			tabela = new TabelaSimbolo();
			s = lexico.analisar(lexico.dev, arquivo);
			if(s == null){ // comentario
				s = lexico.analisar(lexico.dev, arquivo);
			}
		}catch(Exception e){System.out.print(e.getMessage());}
	}

    void casaToken(byte token){
		try{
			if(s != null){
				if(s.getToken() == token){
					s = lexico.analisar(lexico.dev, arquivo);
				}else{
					if(lexico.EOF){
						System.err.println(lexico.linha + ":Fim de Arquivo não esperado.");
						System.exit(0);
					}else{
						System.err.println(lexico.linha + ":Token não esperado: " + s.getLexema());
						System.exit(0);
					}	
				}
			}
		}catch(Exception e){
			System.err.println("casaT" + e.toString());
		}
	}

}