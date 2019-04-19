import java.util.HashMap;
public class TabelaSimbolo {
   public HashMap<String, Simbolo> tabela = new HashMap<>();
   public static int index = -1;
   
   public final byte IF = 0;
   public final byte INTEGER = 1;
   public final byte CHAR = 2; 
   public final byte WRITELN = 3;
   public final byte WRITE = 4;
   public final byte READLN = 5;
   public final byte FOR = 6;
   public final byte ELSE = 7;
   public final byte AND = 8;
   public final byte OR = 9;
   public final byte NOT = 10;
   public final byte ACHAVE = 11; // {
   public final byte FCHAVE = 12; // }
   public final byte ACOL = 13; // [
   public final byte FCOL = 14; // ]
   public final byte APAR = 15; // (
   public final byte FPAR = 16; // )
   public final byte DO = 17;
   public final byte THEN = 18;
   public final byte PV = 19;
   public final byte ATT = 20;
   public final byte TO = 21;
   public final byte STEP = 22;
   public final byte CONST = 23;
   public final byte VAR = 24;
   public final byte MAIOR = 25;
   public final byte MENOR = 26;
   public final byte MAIORIG = 27;
   public final byte MENORIG = 28;
   public final byte DIFF = 29;
   public final byte VIR = 30;
   public final byte ADD = 31;
   public final byte SUB = 32;
   public final byte DIV = 33;
   public final byte MUL = 34;
   public final byte MOD = 35;
   public final byte TRUE = 36;
   public final byte FALSE = 37;
   public final byte BOOLEAN = 38;
   public final byte ASPAS = 39;
   public final byte APOST = 40;
	
   public final byte ID = 41;
   public final byte VALORCONST = 42; 
	
   public TabelaSimbolo() {
      tabela.put("if", new Simbolo(IF,"if", ++index));
      tabela.put("integer", new Simbolo(INTEGER,"integer", ++index));
      tabela.put("char", new Simbolo(CHAR,"char", ++index));
      tabela.put("writeln", new Simbolo(WRITELN,"writeln", ++index));
      tabela.put("write", new Simbolo(WRITE,"write", ++index));
      tabela.put("readln", new Simbolo(READLN,"readln", ++index));
      tabela.put("for", new Simbolo(FOR,"for", ++index));
      tabela.put("else", new Simbolo(ELSE,"else", ++index));
      tabela.put("and", new Simbolo(AND,"and", ++index));
      tabela.put("or", new Simbolo(OR,"or", ++index));
      tabela.put("not", new Simbolo(NOT,"not", ++index));
      tabela.put("{", new Simbolo(ACHAVE,"{", ++index));
      tabela.put("}", new Simbolo(FCHAVE,"}", ++index));
      tabela.put("[", new Simbolo(ACOL,"[", ++index));
      tabela.put("]", new Simbolo(FCOL,"]", ++index));
      tabela.put("(", new Simbolo(APAR,"(", ++index));
      tabela.put(")", new Simbolo(FPAR,")", ++index));
      tabela.put("do", new Simbolo(DO,"do", ++index));
      tabela.put("then", new Simbolo(THEN,"then", ++index));
      tabela.put(";", new Simbolo(PV,";", ++index));
      tabela.put("=", new Simbolo(ATT,"=", ++index));
      tabela.put("to", new Simbolo(TO,"to", ++index));
      tabela.put("step", new Simbolo(STEP,"step", ++index));
      tabela.put("const", new Simbolo(CONST,"const", ++index));
      tabela.put("var", new Simbolo(VAR,"var", ++index));
      tabela.put(">", new Simbolo(MAIOR,">", ++index));
      tabela.put("<", new Simbolo(MENOR,"<", ++index));
      tabela.put(">=", new Simbolo(MAIORIG,">=", ++index));
      tabela.put("<=", new Simbolo(MENORIG,"<=", ++index));
      tabela.put("<>", new Simbolo(DIFF,"<>", ++index));
      //tabela.put("==", new Simbolo(IGUALIGUAL,"==", ++index));
      tabela.put(",", new Simbolo(VIR,",", ++index));
      tabela.put("+", new Simbolo(ADD,"+", ++index));
      tabela.put("-", new Simbolo(SUB,"-", ++index));
      tabela.put("/", new Simbolo(DIV,"/", ++index));
      tabela.put("*", new Simbolo(MUL,"*", ++index));
      tabela.put("%", new Simbolo(MOD,"%", ++index));
      tabela.put("boolean", new Simbolo(BOOLEAN,"boolean", ++index));   
      tabela.put("'", new Simbolo(APOST,"'", ++index));   
      tabela.put("\"", new Simbolo(ASPAS,"\"", ++index));   
   }
	
   public String pesquisa(String lexema){
      lexema = lexema.toLowerCase();
      Simbolo aux = tabela.get(lexema);
      return ((aux == null) ? "NULL" : ""+aux.getEnd());
   }
	
   public Simbolo buscaSimbolo(String lexema){
      lexema = lexema.toLowerCase();
      Simbolo aux = tabela.get(lexema);
      return ((aux == null) ? null : tabela.get(lexema));
      
   }
	
   public Simbolo inserirID(String lexema){
      lexema = lexema.toLowerCase();
      Simbolo simbolo = new Simbolo(ID,lexema, ++index);
      tabela.put(lexema, simbolo);
      return tabela.get(lexema);
   }
	
   public Simbolo inserirConst(String lexema, String tipo){
      lexema = lexema.toLowerCase();
      Simbolo simbolo = new Simbolo(VALORCONST, lexema, tipo, ++index);
      tabela.put(lexema, simbolo);
      return tabela.get(lexema);
   }   
  
   /*public static void main(String[] args){
      TabelaSimbolo tbl = new TabelaSimbolo();
      System.out.println(tbl.pesquisa("if"));
      System.out.println(tbl.pesquisa("FI"));
      System.out.println(tbl.pesquisa("iF"));
      System.out.println(tbl.buscaSimbolo("and").getLexema());
      System.out.println(tbl.pesquisa("And"));
      System.out.println(tbl.buscaSimbolo("for"));
      System.out.println(tbl.buscaSimbolo("<"));
      tbl.inserirID("teste");
      System.out.println(tbl.pesquisa("boolean"));
      System.out.println(tbl.pesquisa("teste"));
      
   }*/
}
