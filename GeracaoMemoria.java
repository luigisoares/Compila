import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GeracaoMemoria{
   public static List<String> linhasCF;
   public BufferedWriter arquivo;
   
   public GeracaoMemoria() throws Exception{
		linhasCF = new ArrayList<>();
		arquivo = new BufferedWriter(new FileWriter("C:/Users/Luigi S/Desktop/Puc/CC7/Compila/Compiladores/codigo.asm"));
	}
    
   public void criarArquivoASM() throws IOException{
		for(String linha : linhasCF){
			arquivo.write(linha);
			arquivo.newLine();
		}
		arquivo.close();
	}
}