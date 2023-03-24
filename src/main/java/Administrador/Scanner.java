
package Administrador;

import java.net.SocketPermission;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalLong;



public class Scanner {

    private final String source;

    private final List<Token> tokens = new ArrayList<>();
    
    private int linea = 1;

    private static final Map<String, TipoToken> palabrasReservadas;
    static {
        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("and", TipoToken.AND);//
        palabrasReservadas.put("class", TipoToken.CLASS);//
        palabrasReservadas.put("also", TipoToken.ALSO);//
        palabrasReservadas.put("false", TipoToken.FALSE );//
        palabrasReservadas.put("to", TipoToken.TO);
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
    static{
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
    static{
        identificadores = new HashMap<>();
        // private final String abc="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPKRSTUVWXYZ";
        
        // identificadores.put(abc, TipoToken.IDENTIFICADOR);
        // identificadores.put(abc, TipoToken.CADENA);
        identificadores.put("0", TipoToken.CERO);
        identificadores.put("1", TipoToken.UNO);
        identificadores.put("2", TipoToken.DOS);
        identificadores.put("3", TipoToken.TRES);
        identificadores.put("4", TipoToken.CUATRO);
        identificadores.put("5", TipoToken.CINCO);
        identificadores.put("6", TipoToken.SEIS);
        identificadores.put("7", TipoToken.SIETE);
        identificadores.put("8", TipoToken.OCHO);
        identificadores.put("9", TipoToken.NUEVE);
    }


    Scanner(String source){
        this.source = source;

        
    }

    List<Token> scanTokens(){
        //Aquí va el corazón del scanner.
         int i=source.length();
         System.out.println("tamaño");
         System.out.println(i);
         while (linea-1<i){
        if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
            linea++;
        }else
        if (source.charAt(linea-1) == 'a' && source.charAt(linea) == 'n' && source.charAt(linea+1) == 'd') {
            tokens.add(new Token(TipoToken.AND, "and", "Palabra reservada", linea));
            linea = linea + 3;
            
        }else
        if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
            linea++;
        }else
        if (source.charAt(linea-1) == 'a' && source.charAt(linea) == 'l' && source.charAt(linea+1) == 's' && source.charAt(linea+2) == 'o') {
            tokens.add(new Token(TipoToken.ALSO, "also", "Palabra reservada", linea));
            linea = linea + 4;
        }else
        if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
            linea++;
        }else
        if (source.charAt(linea-1) == 'c' && source.charAt(linea) == 'l' && source.charAt(linea+1) == 'a' && source.charAt(linea+2) == 's' && source.charAt(linea+3) == 's') {
             tokens.add(new Token(TipoToken.CLASS, "class","Palabra reservada", linea));
             linea=linea+5;
        }else
        if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
            linea++;
        }else 
        if (source.charAt(linea-1) == 'f' && source.charAt(linea) == 'a' && source.charAt(linea+1) == 'l' && source.charAt(linea+2) == 's' && source.charAt(linea+3) == 'e') {
            tokens.add(new Token(TipoToken.FALSE, "false", "Palabra reservada", linea));
            linea = linea + 5;
        } else
        if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
            linea++;
        }else 
        if (source.charAt(linea-1) == 'f' && source.charAt(linea) == 'u' && source.charAt(linea+1) == 'n') {
            tokens.add(new Token(TipoToken.FUN, "fun", "Palabra reservada", linea));
            linea = linea + 3;
        } else
        if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
            linea++;
        }else 
        if (source.charAt(linea-1) == 'i' && source.charAt(linea) == 'f') {
            tokens.add(new Token(TipoToken.IF, "if", "Palabra reservada", linea));
            linea = linea + 2;
        }else
        if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
            linea++;
        }else
        if (source.charAt(linea-1) == 'n' && source.charAt(linea) == 'u' && source.charAt(linea+1) == 'l' && source.charAt(linea+2) == 'l') {
            tokens.add(new Token(TipoToken.NULL, "null", "Palabra reservada", linea));
            linea = linea + 4;
        }
        else 
        if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
            linea++;
        }else
        if (source.charAt(linea-1) == 'o' && source.charAt(linea) == 'r') {
            tokens.add(new Token(TipoToken.OR, "or", "Palabra reservada", linea));
            linea = linea + 2;
        } else
        if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
            linea++;
        }else
         if (source.charAt(linea-1) == 'p' && source.charAt(linea) == 'r' && source.charAt(linea+1) == 'i' && source.charAt(linea+2) == 'n' && source.charAt(linea+3) == 't') {
            tokens.add(new Token(TipoToken.PRINT, "print", "Palabra reservada", linea));
            linea = linea + 5;
        } else
        if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
            linea++;
        }
        else if (source.charAt(linea-1) == 'r' && source.charAt(linea) == 'e' && source.charAt(linea+1) == 't' && source.charAt(linea+2) == 'u' && source.charAt(linea+3) == 'r' && source.charAt(linea+4) == 'n') {
            tokens.add(new Token(TipoToken.RETURN, "return", "Palabra reservada", linea));
            linea = linea + 6;
        } else
        if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
            linea++;
        }
        else if (source.charAt(linea-1) == 's' && source.charAt(linea) == 'u' && source.charAt(linea+1) == 'p' && source.charAt(linea+2) == 'e' && source.charAt(linea+3) == 'r') {
            tokens.add(new Token(TipoToken.SUPER, "super", "Palabra reservada", linea));
            linea = linea + 5;
        } else
        if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
            linea++;
        }
        else if (source.charAt(linea-1) == 't' && source.charAt(linea) == 'o') {
            tokens.add(new Token(TipoToken.TO, "to", "Palabra reservada", linea));
            linea = linea + 2;
        } else
        if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
            linea++;
        }
        else if (source.charAt(linea-1) == 't' && source.charAt(linea) == 'r' && source.charAt(linea+1) == 'u' && source.charAt(linea+2) == 'e') {
            tokens.add(new Token(TipoToken.TRUE, "true", "Palabra reservada", linea));
            linea = linea + 4;
        } else
        if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
            linea++;
        }
        else if (source.charAt(linea-1) == 'v' && source.charAt(linea) == 'a' && source.charAt(linea+1) == 'r') {
            tokens.add(new Token(TipoToken.VAR, "var", "Palabra reservada", linea));
            linea = linea + 3;
        }
        else 
        if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
            linea++;
        }else
        if (source.charAt(linea-1) == 'w' && source.charAt(linea) == 'h' && source.charAt(linea+1) == 'i' && source.charAt(linea+2) == 'l' && source.charAt(linea+3) == 'e') {
            tokens.add(new Token(TipoToken.WHILE, "while","Palabra reservada", linea));
            linea = linea + 5;
        } else
        if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
            linea++;
        }else
        if (source.charAt(linea-1) == '!') {
                if (source.charAt(linea-1) == '!' && source.charAt(linea)=='=') {
                    tokens.add(new Token(TipoToken.COMP, "!=", "simbolos", linea));
                    linea=linea+2;
                }else
                tokens.add(new Token(TipoToken.NEG, "!", "simbolos", linea++));
            }        
            else
            if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
                linea++;
            }else
            if (source.charAt(linea-1) == '(') {
                tokens.add(new Token(TipoToken.PAR1, "(","simbolos", linea));    
                linea++;
            }else
            if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
                linea++;
            }else
            if (source.charAt(linea-1) == ')') {
                tokens.add(new Token(TipoToken.PAR2, ")", "simbolos", linea++));    
            } else
            if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
                linea++;
            }else
            if (source.charAt(linea-1) == '{') {
                tokens.add(new Token(TipoToken.LLAVE1, "{", "simbolos", linea++));
            } else
            if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
                linea++;
            }else
            if (source.charAt(linea-1) == '}') {
                tokens.add(new Token(TipoToken.LLAVE2, "}", "simbolos", linea++));
            } else
            if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
                linea++;
            }else
            if (source.charAt(linea-1) == ',') {
                tokens.add(new Token(TipoToken.COMA, ",", "simbolos", linea++));
            } else
            if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
                linea++;
            }else       
            if (source.charAt(linea-1) == '.') {
                tokens.add(new Token(TipoToken.PUNTO, ".", "simbolos", linea++));
            } else
            if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
                linea++;
            }else
            if (source.charAt(linea-1) == ';') {
                tokens.add(new Token(TipoToken.PCOMA, ";", "simbolos", linea++));
            } else
            if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
                linea++;
            }else
            if (source.charAt(linea-1) == '-') {
                tokens.add(new Token(TipoToken.MENOS, "-", "simbolos", linea++));
            } else
            if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
                linea++;
            }else
            if (source.charAt(linea-1) == '+') {
                tokens.add(new Token(TipoToken.MAS, "+", "simbolos", linea++));
            } else
            if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
                linea++;
            }else
            if (source.charAt(linea-1) == '*') {
                tokens.add(new Token(TipoToken.POR, "*", "simbolos", linea++));
            } else
            if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
                linea++;
            }else
            if (source.charAt(linea-1) == '/') {
                tokens.add(new Token(TipoToken.DIAG, "/", "simbolos", linea++));
            } else
            if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
                linea++;
            }else
            if (source.charAt(linea-1) == '!') {
                if (source.charAt(linea-1) == '!' && source.charAt(linea)=='=') {
                    tokens.add(new Token(TipoToken.COMP, "!=", "simbolos", linea));
                    linea=linea+2;
                } else
                tokens.add(new Token(TipoToken.NEG, "!", "simbolos", linea++));
            } else
            if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
                linea++;
            }else
            if (source.charAt(linea-1) == '=') {
                //tokens.add(new Token(TipoToken.IGUAL1, "=", "simbolos", linea++));
                if (source.charAt(linea-1)== '=' && source.charAt(linea)=='=') {
                    tokens.add(new Token(TipoToken.IGUAL2, "==", "simbolos", linea));
                    linea=linea+2;
                }
                else
                tokens.add(new Token(TipoToken.IGUAL1, "=", "simbolos", linea++));
             } else
             if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
                linea++;
            }else
            if (source.charAt(linea-1) == '<') {
                if (source.charAt(linea-1) == '<' && source.charAt(linea)=='=') {
                    tokens.add(new Token(TipoToken.MENORI, "<=", "simbolos", linea));
                    linea=linea+2;
                } else
                tokens.add(new Token(TipoToken.MENOR, "<", "simbolos", linea++));
            } else
            if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
                linea++;
            }else
            if (source.charAt(linea-1) == '>') {
                if (source.charAt(linea-1) == '>' && source.charAt(linea)=='=') {
                    tokens.add(new Token(TipoToken.MAYORI, ">=", "simbolos", linea));
                    linea=linea+2;
                } else
                tokens.add(new Token(TipoToken.MAYOR, ">", "simbolos", linea++));
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

        System.out.println(linea);
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