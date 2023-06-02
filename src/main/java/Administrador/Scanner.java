package Administrador;

import java.net.SocketPermission;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalLong;

//ALAN NICOLAS FLORES MELO
//GONZÁLEZ MENESES YAZMÍN BERENICE


public class Scanner {

    private  String source;

    private final List<Token> tokens = new ArrayList<>();

    private int linea = 1;

    private static final Map<String, TipoToken> palabrasReservadas;

    static {
        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("and", TipoToken.AND);//
        palabrasReservadas.put("class", TipoToken.CLASS);//
        palabrasReservadas.put("else", TipoToken.ELSE);//
        palabrasReservadas.put("false", TipoToken.FALSE);//
        palabrasReservadas.put("for", TipoToken.FOR);
        palabrasReservadas.put("fun", TipoToken.FUN); //definir funciones
        palabrasReservadas.put("if", TipoToken.IF);//
        palabrasReservadas.put("null", TipoToken.NULL);//
        palabrasReservadas.put("or", TipoToken.OR);//
        palabrasReservadas.put("print", TipoToken.PRINT);//
        palabrasReservadas.put("return", TipoToken.RETURN);//
        palabrasReservadas.put("super", TipoToken.SUPER);//
        palabrasReservadas.put("this", TipoToken.THIS);
        palabrasReservadas.put("true", TipoToken.TRUE);
        palabrasReservadas.put("var", TipoToken.VAR); //definir variables
        palabrasReservadas.put("while", TipoToken.WHILE);//
    }

    private static final Map<String, TipoToken> simbolos;

    static {
        simbolos = new HashMap<>();
        simbolos.put("(", TipoToken.PAR1);
        simbolos.put(")", TipoToken.PAR2);
        simbolos.put("{", TipoToken.LLAVE1);
        simbolos.put("}", TipoToken.LLAVE2);
        simbolos.put(",", TipoToken.COMA);
        simbolos.put(".", TipoToken.PUNTO);
        simbolos.put(";", TipoToken.PCOMA);
        simbolos.put("-", TipoToken.MENOS);
        simbolos.put("+", TipoToken.MAS);
        simbolos.put("*", TipoToken.POR);
        simbolos.put("/", TipoToken.DIAG);
        simbolos.put("!", TipoToken.NEG);
        simbolos.put("!=", TipoToken.COMP);
        simbolos.put("=", TipoToken.IGUAL1);
        simbolos.put("==", TipoToken.IGUAL2);
        simbolos.put("<", TipoToken.MENOR);
        simbolos.put("<=", TipoToken.MENORI);
        simbolos.put(">", TipoToken.MAYOR);
        simbolos.put(">=", TipoToken.MAYORI);

    }

    private static final Map<String, TipoToken> identificadores;

    static {
        identificadores = new HashMap<>();
        // private final String abc="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPKRSTUVWXYZ";

        identificadores.put("", TipoToken.IDENTIFICADOR);
        identificadores.put("", TipoToken.CADENA);
        identificadores.put("", TipoToken.NUMERO);

    }


    Scanner(String source) {
        this.source = source;


    }

    List<Token> scanTokens() {
        //Aquí va el corazón del scanner.
        //source=source.replaceAll("\r\n", "");


        int cont = 1;

        while (cont - 1 < source.length()) {
            char a = source.charAt(cont - 1);

            if (/*cont - 1 < source.length() &&*/ source.charAt(cont - 1) == ' ') {
                cont++;
            } else if (/*cont - 1 < source.length() - 1 &&*/ source.charAt(cont - 1) == '\t') {
                cont++;
            } else if (/*(cont - 1 < source.length() - 1 && */ source.charAt(cont - 1) == '\r' && source.charAt(cont) == '\n' /*)||(cont - 1 < source.length() - 1 && source.charAt(cont - 1) == '/' && source.charAt(cont) == 'n')*/) {
                cont+=2;
                linea++;
            }else

            if (source.charAt(cont - 1) == '(') {
                tokens.add(new Token(TipoToken.PAR1, "(", null, linea));
                cont++;
            }else

            if (source.charAt(cont-1) == ')') {
                tokens.add(new Token(TipoToken.PAR2, ")", null, linea));
                cont++;
            } else

            if (source.charAt(cont-1) == '{') {
                tokens.add(new Token(TipoToken.LLAVE1, "{", null, linea));
                cont++;
            } else

            if (source.charAt(cont-1) == '}') {
                tokens.add(new Token(TipoToken.LLAVE2, "}", null, linea));
                cont++;
            } else

            if (source.charAt(cont-1) == ',') {
                tokens.add(new Token(TipoToken.COMA, ",", null, linea));
                cont++;
            } else

            if (source.charAt(cont-1) == '.') {
                tokens.add(new Token(TipoToken.PUNTO, ".", null, linea));
                cont++;
            } else

            if (source.charAt(cont-1) == ';') {
                tokens.add(new Token(TipoToken.PCOMA, ";", null, linea));
                cont++;
            } else

            if (source.charAt(cont-1) == '-') {
                tokens.add(new Token(TipoToken.MENOS, "-", null, linea));
                cont++;
            } else

            if (source.charAt(cont-1) == '+') {
                tokens.add(new Token(TipoToken.MAS, "+", null, linea));
                cont++;
            } else

            if (source.charAt(cont-1) == '*') {
                tokens.add(new Token(TipoToken.POR, "*", null, linea));
                cont++;
            } else
            if (source.charAt(cont-1) == '/') {
                if(cont<source.length() &&source.charAt(cont) == '/'){
                    cont=cont+2;

                    while(cont<source.length() &&!(source.charAt(cont) == '/') && (source.charAt(cont+1) == 'n')){
                        cont++;
                    }
                    cont=cont+4;
                }else
                if(cont+1<source.length() &&source.charAt(cont) == '*'){
                    cont=cont+2;

                    while(cont+1<source.length() && !(source.charAt(cont) == '*') && !(source.charAt(cont+1) == '/')){
                        cont++;

                    }
                    cont++;
                }else

                    tokens.add(new Token(TipoToken.DIAG, "/", null, linea));
                cont++;
            } else

            if (source.charAt(cont-1) == '!') {
                if (cont < source.length() && source.charAt(cont)=='=') {
                    tokens.add(new Token(TipoToken.COMP, "!=", null, linea));
                    cont=cont+2;
                } else {
                    tokens.add(new Token(TipoToken.NEG, "!", null, linea));
                    cont++;
                }

            } else
            if (source.charAt(cont-1) == '=') {
                //tokens.add(new Token(TipoToken.IGUAL1, "=", "simbolos", linea++));

                if (cont < source.length() && source.charAt(cont)=='=') {
                    tokens.add(new Token(TipoToken.IGUAL2, "==", null, linea));
                    cont=cont+2;
                }else
                {
                    tokens.add(new Token(TipoToken.IGUAL1, "=", null, linea));
                    cont++;}
            } else

            if (source.charAt(cont-1) == '<') {
                if (cont < source.length() && source.charAt(cont)=='=') {
                    tokens.add(new Token(TipoToken.MENORI, "<=", null, linea));
                    cont=cont+2;
                } else
                    tokens.add(new Token(TipoToken.MENOR, "<", null, linea));
                cont++;
            } else

            if (source.charAt(cont-1) == '>') {
                if (cont < source.length() && source.charAt(cont)=='=') {
                    tokens.add(new Token(TipoToken.MAYORI, ">=", null, linea));
                    cont=cont+2;
                } else
                    tokens.add(new Token(TipoToken.MAYOR, ">", null, linea));
                cont++;
            }else
            if (source.charAt(cont - 1) == '"') {
                String aux = "";
                cont++;
                while (cont - 1 < source.length() && source.charAt(cont - 1) != '"') {
                    aux = aux.concat(String.valueOf(source.charAt(cont - 1)));
                    cont++;
                }

                if (cont - 1 >= source.length()) {
                    System.out.print("Error: no se cerraron las comillas ");
                } else {
                    tokens.add(new Token(TipoToken.CADENA, aux, aux, linea));
                    cont = cont + 2;
                }
            }
            else
            if(Character.isLetter(source.charAt(cont-1))){
                String aux = "";


                if (source.length() - cont>1 &&source.charAt(cont-1) == 'a' && source.charAt(cont) == 'n' && source.charAt(cont+1) == 'd') {
                    tokens.add(new Token(TipoToken.AND, "and", null, linea));
                    cont = cont + 3;

                }else

                if (source.length() - cont>2&&source.charAt(cont-1) == 'a' && source.charAt(cont) == 'l' && source.charAt(cont+1) == 's' && source.charAt(cont+2) == 'o') {
                    tokens.add(new Token(TipoToken.ELSE, "else", null, linea));
                    cont = cont + 4;
                }else

                if (source.length() - cont>3&&source.charAt(cont-1) == 'c' && source.charAt(cont) == 'l' && source.charAt(cont+1) == 'a' && source.charAt(cont+2) == 's' && source.charAt(cont+3) == 's') {
                    tokens.add(new Token(TipoToken.CLASS, "class",null, linea));
                    cont=cont+5;
                }else
                if (cont-1 < source.length() && source.charAt(cont-1) == ' ') {
                    cont++;
                }else
                if (source.length() - cont>3&&source.charAt(cont-1)  == 'f' && source.charAt(cont) == 'a' && source.charAt(cont+1) == 'l' && source.charAt(cont+2) == 's' && source.charAt(cont+3) == 'e') {
                    tokens.add(new Token(TipoToken.FALSE, "false", null, linea));
                    cont = cont + 5;
                } else
                if (cont-1 < source.length() && source.charAt(cont-1) == ' ') {
                    cont++;
                }else
                if (source.length() - cont>1&&source.charAt(cont-1) == 'f' && source.charAt(cont) == 'u' && source.charAt(cont+1) == 'n') {
                    tokens.add(new Token(TipoToken.FUN, "fun", null, linea));
                    cont = cont + 3;
                } else
                if (cont-1 < source.length() && source.charAt(cont-1) == ' ') {
                    cont++;
                }else
                if (source.length() - cont>0&&source.charAt(cont-1) == 'i' && source.charAt(cont) == 'f') {
                    tokens.add(new Token(TipoToken.IF, "if", null, linea));
                    cont = cont + 2;
                }else
                if (cont-1 < source.length() && source.charAt(cont-1) == ' ') {
                    cont++;
                }else
                if (source.length() - cont>2&&source.charAt(cont-1) == 'n' && source.charAt(cont) == 'u' && source.charAt(cont+1) == 'l' && source.charAt(cont+2) == 'l') {
                    tokens.add(new Token(TipoToken.NULL, "null", null, linea));
                    cont = cont + 4;
                }
                else
                if (cont-1 < source.length() && source.charAt(cont-1) == ' ') {
                    cont++;
                }else
                if (source.length() - cont>0&&source.charAt(cont-1) == 'o' && source.charAt(cont) == 'r') {
                    tokens.add(new Token(TipoToken.OR, "or", null, linea));
                    cont = cont + 2;
                } else
                if (cont-1 < source.length() && source.charAt(cont-1) == ' ') {
                    cont++;
                }else
                if (source.length() - cont>3&&source.charAt(cont-1) == 'p' && source.charAt(cont) == 'r' && source.charAt(cont+1) == 'i' && source.charAt(cont+2) == 'n' && source.charAt(cont+3) == 't') {
                    tokens.add(new Token(TipoToken.PRINT, "print", null, linea));
                    cont = cont + 5;
                } else
                if (cont-1 < source.length() && source.charAt(cont-1) == ' ') {
                    cont++;
                }
                else if (source.length() - cont>4&&source.charAt(cont-1) == 'r' && source.charAt(cont) == 'e' && source.charAt(cont+1) == 't' && source.charAt(cont+2) == 'u' && source.charAt(cont+3) == 'r' && source.charAt(cont+4) == 'n') {
                    tokens.add(new Token(TipoToken.RETURN, "return", null, linea));
                    cont = cont + 6;
                } else
                if (cont-1 < source.length() && source.charAt(cont-1) == ' ') {
                    cont++;
                }
                else if (source.length() - cont>3&&source.charAt(cont-1) == 's' && source.charAt(cont) == 'u' && source.charAt(cont+1) == 'p' && source.charAt(cont+2) == 'e' && source.charAt(cont+3) == 'r') {
                    tokens.add(new Token(TipoToken.SUPER, "super", null, linea));
                    cont = cont + 5;
                } else
                if (cont-1 < source.length() && source.charAt(cont-1) == ' ') {
                    cont++;
                }
                else if (source.length() - cont>0&&source.charAt(cont-1) == 'f' && source.charAt(cont) == 'o'&& source.charAt(cont+1) == 'r') {
                    tokens.add(new Token(TipoToken.FOR, "for", null, linea));
                    cont = cont + 3;
                } else
                if (cont-1 < source.length() && source.charAt(cont-1) == ' ') {
                    cont++;
                }
                else if (source.length() - cont>2&&source.charAt(cont-1) == 't' && source.charAt(cont) == 'r' && source.charAt(cont+1) == 'u' && source.charAt(cont+2) == 'e') {
                    tokens.add(new Token(TipoToken.TRUE, "true", null, linea));
                    cont = cont + 4;
                } else
                if (cont-1 < source.length() && source.charAt(cont-1) == ' ') {
                    cont++;
                }
                else if (source.length() - cont>1&&source.charAt(cont-1) == 'v' && source.charAt(cont) == 'a' && source.charAt(cont+1) == 'r') {
                    tokens.add(new Token(TipoToken.VAR, "var", null, linea));
                    cont = cont + 3;
                }
                else
                if (cont-1 < source.length() && source.charAt(cont-1) == ' ') {
                    cont++;
                }else
                if (source.length() - cont>3&&source.charAt(cont-1) == 'w' && source.charAt(cont) == 'h' && source.charAt(cont+1) == 'i' && source.charAt(cont+2) == 'l' && source.charAt(cont+3) == 'e') {
                    tokens.add(new Token(TipoToken.WHILE, "while",null, linea));
                    cont = cont + 5;
                } else

                    while((cont-1 < source.length()) && Character.isLetter(source.charAt(cont-1))) {
                        aux = aux.concat(String.valueOf(source.charAt(cont-1)));
                        if (cont == source.length()) { // Verifica si el número es el último carácter de la cadena de origen
                            cont++;
                            tokens.add(new Token(TipoToken.IDENTIFICADOR, aux, null, linea));
                            cont++;
                            break;
                        } else
                        if(cont  < source.length() && source.charAt(cont) == ' ') {
                            cont++;
                            tokens.add(new Token(TipoToken.IDENTIFICADOR, aux, null, linea));
                            cont++;
                            break;
                        }else if (!Character.isLetter(source.charAt(cont))) { // Verifica si el siguiente carácter no es un número

                            tokens.add(new Token(TipoToken.IDENTIFICADOR, aux, null, linea));
                            cont++;
                            break;
                        }
                        cont++;
                    }

            }else
            if ( Character.isDigit(source.charAt(cont-1)) && cont-1 < source.length()) {
                String aux="";
                while((cont-1 < source.length()) && Character.isDigit(source.charAt(cont-1))) {
                    aux = aux.concat(String.valueOf(source.charAt(cont-1)));
                    if (cont == source.length()) { // Verifica si el número es el último carácter de la cadena de origen

                        tokens.add(new Token(TipoToken.NUMERO, aux, aux, linea));
                        cont++;
                        break;
                    }
                    else if((cont  < source.length() && source.charAt(cont) == '.')|| (cont  < source.length() && source.charAt(cont) == ',')) {
                        cont++;
                        aux = aux.concat(String.valueOf(source.charAt(cont - 1)));
                    }
                    else if (!Character.isDigit(source.charAt(cont))) { // Verifica si el siguiente carácter no es un número

                        tokens.add(new Token(TipoToken.NUMERO, aux, aux, linea));
                        cont++;
                        break;
                    }

                    cont++;

                }

            }


        }
    /*
    Analizar el texto de entrada para extraer todos los tokens
    y al final agregar el token de fin de archivo
    */
        tokens.add(new Token(TipoToken.EOF, "", null, linea));
        //literal es una expresion regular
        //lexema es el string al cual es igual
        //linea: es un contador de palabras

        return tokens;
    }
}

/*
Signos o símbolos del lenguaje:
(
)
{
}
,
.
;
-
+
*
/
!
!=
=
==
<
<=
>
>=
// -> comentarios (no se genera token)
/* ... * / -> comentarios (no se genera token)
Identificador,
Cadena
Numero
Cada palabra reservada tiene su nombre de token
 */
