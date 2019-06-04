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
    
   public void zerarTemp(){
      contTemp = 0;
   }
	
   public int alocarTemp(){
      int tmp = contador;
   	//contador += 4000;
      contador += 16384;
      return tmp;
   }
	
   public int alocarTipoLogico(){
      int tmp = contador;
      contador++;
      return tmp;
   }
   
   public int alocarTipoChar(){
      int tmp = contador;
      contador++;
      return tmp;
   }
   
   public int alocarTipoChar(int tam){
      int tmp = contador;
      contador += tam;
      return tmp;
   }
	
   public int alocarTipoInteiro(){
      int tmp = contador;
      contador += 2;
      return tmp;
   }
   
   public int alocarTipoInteiro(int tam){
      int tmp = contador;
      contador += (2*tam);
      return tmp;
   }
	
   public int alocarTipoString(){
      int tmp = contador;
      contador += 256;
      return tmp;
   }
	
   public int alocarTipoString(int tam){
      int tmp = contador;
      contador += tam;
      return tmp;
   }
	
   public int novoTemp(){
      return contTemp;
   }
	
   public int alocarTempTipoLogico(){
      int tmp = contTemp;
      contTemp++;
      return tmp;
   }
	
   public int alocarTempTipoChar(){
      int tmp = contTemp;
      contTemp++;
      return tmp;
   }
   
   public int alocarTempTipoInteiro(){
      int tmp = contTemp;
      contTemp += 2;
      return tmp;
   }
	
   public int alocarTempTipoString(){
      int tmp = contTemp;
      contTemp += 256;
      return tmp;
   }
   
   public int alocarTempTipoString(int tam){
      int tmp = contTemp;
      contTemp += tam;
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