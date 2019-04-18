import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import java.util.Scanner;
import java.io.*;

public class AnalisadorLexico {
   TabelaSimbolo simbolos = new TabelaSimbolo();
   String lexema = "";
   public boolean devolve = false;
   Simbolo simb;
   boolean id = false, constante = false;
   char c;
   char c1;
   public static int linha = 0;
   public boolean ehComentario = false;
   public boolean ehEOF = false;
   public static List validos = Arrays.asList(new Character[] { '@' }); // @TODO popular os validos

   Simbolo analisarLexema(boolean devolucao, BufferedReader arquivo, BufferedReader arquivo2) throws Exception {
      //BufferedReader arquivo2 = null;
      int stateI = 0;
      int stateF = 1;
      lexema = "";
      // int pos = 0;
      // c = (char)arquivo.read();
      // while(c != '\n' || c != '\r'){
      while (stateI != stateF) {
         switch (stateI) {
         case 0:
            if (devolucao == false) {
               c = (char) arquivo.read();
               c1 = (char) arquivo2.read();
            }
            devolucao = false;
            // Quebra de linha no arquivo
            if (c == '\n' || c == 11) { // @TODO entender o char 11
               linha++;
            } else if (c == '+' || c == '-' || c == '*' || c == '%' || c == '(' || c == ')' || c == '[' || c == ']'
                  || c == '{' || c == '}' || c == ';' || c == ',' || c == '=') {
               // LÃƒÂª os tokens que possuem somente 1 caractere e vao para o estado final
               // Nada ÃƒÂ© devolvido
               lexema += c;
               stateI = stateF;
               devolve = false;
            } else if (c == 32 || c == 11 || c == 8 || c == 13 || c == 9) {
               // lendo "lixo" espaÃƒÂ§o em branco, enter tabs vertical e horizontal
               stateI = 0;
            } else if (c == '/') {
               lexema += c;
               stateI = 14;
            } else if (c == '>') {
               // Possui 2 variacoes: '>' e '>=', vai para o proximo estado para decidir qual o
               // token
               lexema += c;
               stateI = 2;
            } else if (c == '<') {
               // Possui 3 variacoes: '<', '<>' e '<=', vai para o proximo estado para decidir
               // qual o token
               lexema += c;
               stateI = 3;
            }
            /*
             * else if (c == '=') { // Possui 2 variacoes: '=' e '==', vai para o proximo
             * estado para decidir qual o // token lexema += c; stateI = 4; }
             */
            else if (c == '\'') {
               // Constante do tipo 'constante'
               lexema += c;
               stateI = 11;
            } else if (c == '"' || c == '\u00E2' || c == '\u20AC' || c == '\u0153') {
               // Constante do tipo "constante"
               lexema += c;
               stateI = 12;
            } else if (isLetra(c) || c == '.' || c == '_') {
               // Criacao de um id ou token que possui lexema constituido por letras, '.' ou
               // '_'
               lexema += c;
               stateI = 5;
            } else if (isDigito(c)) {
               if (c == '0') {
                  // Constante do tipo 0x(hexa)(hexa)
                  lexema += c;
                  stateI = 7;
               } else {
                  // Numero nao comeÃƒÂ§ado por 0
                  lexema += c;
                  stateI = 10;
               }
            } else if (c == 65535) {
               stateI = stateF;
               lexema += c;
               ehEOF = true;
               devolve = false;
               arquivo.close();
               arquivo2.close();
            } else {
               System.err.println(linha + ":Caractere invalido");
               System.exit(0);
            }
            break;
         case 2:
            c = (char) arquivo.read();
            c1 = (char) arquivo2.read();
            if (c == '=') {
               lexema += c;
               stateI = stateF;
               devolve = false;
            } else {
               stateI = stateF;
               devolve = true;
               devolucao = true;
            }
            break;
         case 3:
            c = (char) arquivo.read();
            c1 = (char) arquivo2.read();
            if (c == '=') {
               lexema += c;
               stateI = stateF;
               devolve = false;
            } else if (c == '>') {
               lexema += c;
               stateI = stateF;
               devolve = false;
            } else {
               stateI = stateF;
               this.devolve = true;
               devolucao = true;
            }
            break;
         case 4:
            /*
             * c = (char)arquivo.read(); c1 = (char)arquivo2.read(); if (c == '=') { lexema
             * += c; stateI = stateF; devolve = false; } else { stateI = stateF; devolve =
             * true; devolucao = true; }
             */
            break;
         case 5:
            c = (char) arquivo.read();
            c1 = (char) arquivo2.read();
            // Continua no mesmo estado caso encontre '.' ou '_'
            if (c == '.' || c == '_') {
               lexema += c;
            } else if (isLetra(c) || isDigito(c)) {
               // Vai para o estado 6 caso seja digito ou letra (minimo um digito ou letra no
               // id)
               lexema += c;
               stateI = 6;
            } else if (c != '.' && c != '_') {
               stateI = 0;
               lexema = "";
            }
            break;
         case 6:
            c = (char) arquivo.read();
            c1 = (char) arquivo2.read();
            if (isDigito(c) || isLetra(c) || c == '.' || c == '_') {
               lexema += c;
            } else {
               stateI = stateF;
               devolucao = true;
               devolve = true;
            }
            break;
         case 7:
            c = (char) arquivo.read();
            c1 = (char) arquivo2.read();
            if (c == 'x' || c == 'X') {
               lexema += c;
               stateI = 8;
            } else if (isDigito(c)) {
               lexema += c;
               stateI = 10;
            } else {
               stateI = stateF;
               devolve = true;
               devolucao = true;
            }
            break;
         case 8:
            c = (char) arquivo.read();
            c1 = (char) arquivo2.read();
            // @TODO Aceitar todas as letras, e tratar o resultado no analisador sintatico ?
            if (Character.digit(c, 16) >= 0) {
               lexema += c;
               stateI = 9;
            }
            break;
         case 9:
            c = (char) arquivo.read();
            c1 = (char) arquivo2.read();
            // @TODO Aceitar todas as letras, e tratar o resultado no analisador sintatico ?
            if (Character.digit(c, 16) >= 0) {
               lexema += c;
               stateI = stateF;
               devolve = false;
            }
            break;
         case 10:
            c = (char) arquivo.read();
            c1 = (char) arquivo2.read();
            if (isDigito(c)) {
               lexema += c;
            } else {
               stateI = stateF;
               devolve = true;
               devolucao = true;
            }
            break;
         case 11:
            c = (char) arquivo.read();
            c1 = (char) arquivo2.read();
            if (isDigito(c) || isLetra(c)) {
               lexema += c;
            } else if (c == '\'') {
               lexema += c;
               stateI = stateF;
               devolve = false;
            }
            break;
         case 12:
            c = (char) arquivo.read();
            c1 = (char) arquivo2.read();
            if (isDigito(c) || isLetra(c) || isValido(c)) {
               lexema += c;
               stateI = 13;
            }
            break;
         case 13:
            c = (char) arquivo.read();
            c1 = (char) arquivo2.read();
            if (isDigito(c) || isLetra(c) || isValido(c)) {
               lexema += c;
            } else if (c == '"' || c == '\u00E2' || c == '\u20AC' || c == '\u0153') {
               lexema += c;
               stateI = stateF;
               devolve = false;
            }
            break;
         case 14:
            c = (char) arquivo.read();
            c1 = (char) arquivo2.read();
            if (c != '*') {
               stateI = stateF;
               devolucao = true;
               devolve = true;
            } else {
               stateI = 15;
            }
            break;
         case 15:
            c = (char) arquivo.read();
            c1 = (char) arquivo2.read();
            if (c == '*')
               stateI = 16;
            break;
         case 16:
            c = (char) arquivo.read();
            c1 = (char) arquivo2.read();
            if (c == '/') {
               stateI = 0;
               lexema = "";
            } else
               stateI = 15;
            break;
         }
      }
      simb = null;
      if (!ehEOF) {
         // Seleciona o simbolo da tabela de simbolos caso ele ja esteja na tabela
         if (simbolos.tabela.get(lexema.toLowerCase()) != null) {
            simb = simbolos.tabela.get(lexema.toLowerCase());
         } else if (isLetra(lexema.charAt(0)) || lexema.charAt(0) == '.' || lexema.charAt(0) == '_') {
            // Insere um novo ID na tabela de simbolos
            simb = simbolos.inserirID(lexema);
         } else if (isDigito(lexema.charAt(0))) {

            if (lexema.charAt(0) == '0') {
               if (lexema.length() == 1) {
                  simb = simbolos.inserirConst(lexema, "tipo_inteiro");
               } else {
                  // Constante hexadecimal
                  if (lexema.length() > 2 && (lexema.charAt(1) == 'X' || lexema.charAt(2) == 'x')) {
                     // Constantes hexa sao do tipo 0xFF -> 4 caracteres
                     if (lexema.length() == 4) {
                        // Verifica se os 2 ultimos digitos sao hexadecimais
                        if (Character.digit(lexema.charAt(2), 16) >= 0 && Character.digit(lexema.charAt(3), 16) >= 0) {
                           simb = simbolos.inserirConst(lexema, "tipo_inteiro");
                        } else {
                           printError();
                        }
                     }
                  } else {
                     // Verifica se possui algum caracter nao numerico
                     for (int i = 0; i < lexema.length(); i++) {
                        if (!isDigito(lexema.charAt(i))) {
                           printError();
                        }
                     }

                     simb = simbolos.inserirConst(lexema, "tipo_inteiro");
                  }

               }
            } else {
               // Verifica se possui algum caracter nao numerico
               for (int i = 0; i < lexema.length(); i++) {
                  if (!isDigito(lexema.charAt(i))) {
                     printError();
                  }
               }

               simb = simbolos.inserirConst(lexema, "tipo_inteiro");
            }
         } else if (lexema.charAt(0) == '\'' && lexema.charAt(lexema.length() - 1) == '\'') {
            simb = simbolos.inserirConst(lexema, "tipo_string");
         } else if (lexema.charAt(0) == '"' && lexema.charAt(lexema.length() - 1) == '"') {
            simb = simbolos.inserirConst(lexema, "tipo_string");
         } else {
            printError();
         }
      }
      /*
       * arrSimb[pos] = simb; pos++; //c = (char)arquivo.read(); stateI = 0; stateF =
       * 1; lexema = ""; }
       */

      return simb;
   }

   public static boolean isLetra(char c) {
      boolean ehLetra = false;
      if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z')
         ehLetra = true;
      return ehLetra;
   }

   public static boolean isDigito(char c) {
      boolean ehDigito = false;
      if (c >= '0' && c <= '9')
         ehDigito = true;
      return ehDigito;
   }

   public static boolean isValido(char c) {
      return validos.indexOf(c) >= 0;
   }

   public void printError() {
      System.out.println("Erro na linha: " + linha + ". Lexema nao reconhecido: " + lexema);
      System.exit(1);
   }

   public static void main(String[] args) throws Exception {
      try (FileReader reader = new FileReader("exemplo1.l"); BufferedReader br = new BufferedReader(reader)) {
         AnalisadorLexico aL = new AnalisadorLexico();
         FileReader reader2 = new FileReader("exemplo1.l");
         BufferedReader br2 = new BufferedReader(reader2);
         Simbolo simbol = new Simbolo();
         while (br2.read() != 65535) {
            //simb = aL.analisarLexema(aL.devolve, br);
            simbol = aL.analisarLexema(aL.devolve, br, br2);
            if (simbol != null) {
               System.out.println(simbol.getToken()+" "+simbol.getLexema());
            }
         }
      } catch (IOException e) {
         System.err.format("IOException: %s%n", e);
      }
   }
}