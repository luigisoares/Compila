import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GeracaoMemoria{
   public static List<String> linhasCF;
   public BufferedWriter arquivo;
      
   public static int contador = 0;
	public static int contTemp = 0;
	
   public GeracaoMemoria() throws Exception{
		linhasCF = new ArrayList<>();
		arquivo = new BufferedWriter(new FileWriter("C:/Users/Luigi S/Desktop/Puc/CC7/Compila/Compiladores/codigo.asm"));
	}
    
	public void restetTemp(){
		contTemp = 0;
	}
	
	public int alocarTemp(){
		int tmp = contador;
		contador += 4000;
		return tmp;
	}
	
	public int alocarLogico(){
		int tmp = contador;
		contador++;
		return tmp;
	}
   
   public int alocarChar(){
		int tmp = contador;
		contador++;
		return tmp;
	}
   
   public int alocarChar(int tam){
		int tmp = contador;
		contador += tam;
		return tmp;
	}
	
	public int alocarInteiro(){
		int tmp = contador;
		contador += 2;
		return tmp;
	}
   
   public int alocarInteiro(int tam){
		int tmp = contador;
		contador += (2*tam);
		return tmp;
	}
	
	public int alocarString(){
		int tmp = contador;
		contador += 256;
		return tmp;
	}
	
	public int alocarString(int tam){
		int tmp = contador;
		contador += tam;
		return tmp;
	}
	
	public int novoTemp(){
		return contTemp;
	}
	
	public int alocarTempLogico(){
		int tmp = contTemp;
		contTemp++;
		return tmp;
	}
	
	public int alocarTempChar(){
		int tmp = contTemp;
		contTemp++;
		return tmp;
	}
   
	public int alocarTempInteiro(){
		int tmp = contTemp;
		contTemp += 2;
		return tmp;
	}
	
	public int alocarTempString(){
		int tmp = contTemp;
		contTemp += 256;
		return tmp;
	}
   
      public void criarArquivoASM() throws IOException{
		for(String linha : linhasCF){
			arquivo.write(linha);
			arquivo.newLine();
		}
		arquivo.close();
	}
}