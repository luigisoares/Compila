import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;

public class Principal {
   static Parser p;
   static BufferedReader arquivo;
	
   static void lerCaminho(){
      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));  	
      String file = "";
      try{
         do{
         	// System.out.print("Digite o nome do arquivo: ");
         	// file = in.readLine();
            //file = "exemplo1.l";
            //file = "t9.l"; // HEXA
            //file = "t1.l"; // HEXA
            file = "t2.l"; // $ no meio da string
            //file = "t3.l"; // comentario sem fechar
            //file = "t4.l"; // ! no lugar do ]
            //file = "t5.l"; // esqueceu o ;
            //file = "t6.l"; // no +- printa o -
            //file = "t7.l"; // no step do for n�o tem valor pra saber os steps
            //file = "t8.l"; // EOF esqueceu de por } no final do for
         
            if(file.length() > 2){
               if(file.charAt(file.length()-2) != '.' && file.charAt(file.length() - 1) != 'l' && file.charAt(file.length() - 1) != 'L'){
                  System.out.print("Digite o nome do arquivo: ");
                  file = in.readLine();
               }
            }
         	
         }while(file.length() == 0);
      	
         arquivo = new BufferedReader(new FileReader(file));
      }catch (Exception e) {
         lerCaminho();
      }
   }
	
   public static void main(String[] args) throws Exception{
      try{
         lerCaminho();
         p = new Parser(arquivo);
         p.S();
         System.out.println("Finalizado - sem erros.");
      }catch (Exception e) {
         System.err.println("Erro: " + e.getMessage());
      	
      }
   }
}
