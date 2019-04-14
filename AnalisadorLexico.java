import java.io.BufferedReader;
import java.util.Arrays;
import java.util.List;

public class AnalisadorLexico {
    TabelaSimbolo simbolos = new TabelaSimbolo();
    String lexema = "";
    public boolean devolve = false;
    Simbolo simb;
    boolean id = false, constante = false;
    char c;
    public static int linha = 1;
    public boolean ehComentario = false;
    public boolean ehEOF = false;
    public static List validos = Arrays.asList(new Character[] { '@' }); // @TODO popular os validos

    Simbolo analisarLexema(boolean devolucao, BufferedReader arquivo) throws Exception {
        int satual = 0;
        int sfinal = 1;
        lexema = "";

        while (satual != sfinal) {
            c = (char) arquivo.read();
            switch (satual) {
            case 0:
                // Quebra de linha no arquivo
                if (c == '\n' || c == 11) { // @TODO entender o char 11
                    linha++;
                } else if (c == '+' || c == '-' || c == '*' || c == '%' || c == '(' || c == ')' || c == '[' || c == ']'
                        || c == '{' || c == '}' || c == ';') {
                    // Lê os tokens que possuem somente 1 caractere e vao para o estado final
                    // Nada é devolvido
                    lexema += c;
                    satual = sfinal;
                    devolve = false;
                } else if (c == '>') {
                    // Possui 2 variacoes: '>' e '>=', vai para o proximo estado para decidir qual o
                    // token
                    lexema += c;
                    satual = 2;
                } else if (c == '<') {
                    // Possui 3 variacoes: '<', '<>' e '<=', vai para o proximo estado para decidir
                    // qual o token
                    lexema += c;
                    satual = 3;
                } else if (c == '=') {
                    // Possui 2 variacoes: '=' e '==', vai para o proximo estado para decidir qual o
                    // token
                    lexema += c;
                    satual = 4;
                } else if (c == '\'') {
                    // Constante do tipo 'constante'
                    lexema += c;
                    satual = 11;
                } else if (c == '"') {
                    // Constante do tipo "constante"
                    lexema += c;
                    satual = 12;
                } else if (isLetra(c) || c == '.' || c == '_') {
                    // Criacao de um id ou token que possui lexema constituido por letras, '.' ou
                    // '_'
                    lexema += c;
                    satual = 5;
                } else if (isDigito(c)) {
                    if (c == '0') {
                        // Constante do tipo 0x(hexa)(hexa)
                        lexema += c;
                        satual = 7;
                    } else {
                        // Numero nao começado por 0
                        lexema += c;
                    }
                }
            case 2:
                if (c == '=') {
                    lexema += c;
                    satual = sfinal;
                    devolve = false;
                } else {
                    satual = sfinal;
                    devolve = true;
                    devolucao = true;
                }
            case 3:
                if (c == '=') {
                    lexema += c;
                    satual = sfinal;
                    devolve = false;
                } else if (c == '>') {
                    lexema += c;
                    satual = sfinal;
                    devolve = false;
                } {
                satual = sfinal;
                this.devolve = true;
                devolucao = true;
            }
            case 4:
                if (c == '=') {
                    lexema += c;
                    satual = sfinal;
                    devolve = false;
                } else {
                    satual = sfinal;
                    devolve = true;
                    devolucao = true;
                }
            case 5:
                // Continua no mesmo estado caso encontre '.' ou '_'
                if (c == '.' || c == '_') {
                    lexema += c;
                } else if (isLetra(c) || isDigito(c)) {
                    // Vai para o estado 6 caso seja digito ou letra (minimo um digito ou letra no
                    // id)
                    lexema += c;
                    satual = 6;
                } else if (!isLetra(c) && !isDigito(c) && c != '.' && c != '_') {
                    satual = sfinal;
                    devolucao = true;
                    devolve = true;
                }
            case 6:
                if (isDigito(c) || isLetra(c) || c == '.' || c == '_') {
                    lexema += c;
                } else {
                    satual = sfinal;
                    devolucao = true;
                    devolve = true;
                }
            case 7:
                if (c == 'x' || c == 'X') {
                    lexema += c;
                    satual = 8;
                } else if (isDigito(c)) {
                    lexema += c;
                    satual = 10;
                } else {
                    satual = sfinal;
                    devolve = true;
                    devolucao = true;
                }
            case 8:
                // @TODO Verificar se isso funciona (HEXADECIMAL)
                // Aceitar todas as letras, e tratar o resultado no analisador sintatico ?
                if (c >= '0' && c <= 'F') {
                    lexema += c;
                    satual = 9;
                }
            case 9:
                // @TODO Verificar se isso funciona (HEXADECIMAL)
                // Aceitar todas as letras, e tratar o resultado no analisador sintatico ?
                if (c >= '0' && c <= 'F') {
                    lexema += c;
                    satual = sfinal;
                    devolve = false;
                }
            case 10:
                if (isDigito(c)) {
                    lexema += c;
                } else {
                    satual = sfinal;
                    devolve = true;
                    devolucao = true;
                }
            case 11:
                if (isDigito(c) || isLetra(c)) {
                    lexema += c;
                } else if (c == '\'') {
                    lexema += c;
                    satual = sfinal;
                    devolve = false;
                }
            case 12:
                if (isDigito(c) || isLetra(c) || isValido(c)) {
                    lexema += c;
                    satual = 13;
                }
            case 13:
                if (isDigito(c) || isLetra(c) || isValido(c)) {
                    lexema += c;
                } else if (c == '"') {
                    lexema += c;
                    satual = sfinal;
                    devolve = false;
                }
            case 14:
                if (c != '*') {
                    satual = sfinal;
                    devolucao = true;
                    devolve = true;
                } else {
                    satual = 15;
                }
            case 15:
                if (c == '*')
                    satual = 16;
            case 16:
                if (c == '/')
                    satual = 0;
                else
                    satual = 15;
            }
        }

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
                                if (Character.digit(lexema.charAt(2), 16) >= 0
                                        && Character.digit(lexema.charAt(3), 16) >= 0) {
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

    public static void printError() {
        System.out.println("Erro na linha: " + linha + ". Lexema não reconhecido: " + lexema);
        System.exit(1);
    }
}