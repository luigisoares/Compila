public class Simbolo {
   private String lexema = "";
   private byte token;
	//private String classe = "";
   private String tipo = "";
   private int endereco;
   
   
   public Simbolo(byte token, String lexema, int endereco){
      this.lexema = lexema;
      this.token = token;
      this.endereco = endereco;
   }
	
   public Simbolo(byte token, String lexema, String tipo, int endereco){
      this.lexema = lexema;
      this.token = token;
      this.tipo = tipo;
      this.endereco = endereco;
   }

   public byte getToken(){
      return token;
   }
	
   public int getEnd(){
      return endereco;
   }
	
   public String getLexema(){
      return lexema;
   }

   public String getTipo() {
      return tipo;
   }

   public void setTipo(String tipo) {
      this.tipo = tipo;
   }

   public int getEndereco() {
      return endereco;
   }

   public void setEndereco(int endereco) {
      this.endereco = endereco;
   }

}