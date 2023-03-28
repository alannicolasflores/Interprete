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
        
        identificadores.put("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPKRSTUVWXYZ", TipoToken.IDENTIFICADOR);
        identificadores.put("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPKRSTUVWXYZ", TipoToken.CADENA);
        identificadores.put("0123456789", TipoToken.NUMERO);
        
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
        if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
            linea++;
        }else
        if (source.charAt(linea-1) == 'a' && source.charAt(linea) == 'n' && source.charAt(linea+1) == 'd') {
            tokens.add(new Token(TipoToken.AND, "and", null, linea));
            linea = linea + 3;
            
        }else
        if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
            linea++;
        }else
        if (source.charAt(linea-1) == 'a' && source.charAt(linea) == 'l' && source.charAt(linea+1) == 's' && source.charAt(linea+2) == 'o') {
            tokens.add(new Token(TipoToken.ALSO, "also", null, linea));
            linea = linea + 4;
        }else
        if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
            linea++;
        }else
        if (source.charAt(linea-1) == 'c' && source.charAt(linea) == 'l' && source.charAt(linea+1) == 'a' && source.charAt(linea+2) == 's' && source.charAt(linea+3) == 's') {
             tokens.add(new Token(TipoToken.CLASS, "class",null, linea));
             linea=linea+5;
        }else
        if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
            linea++;
        }else 
        if (source.charAt(linea-1) == 'f' && source.charAt(linea) == 'a' && source.charAt(linea+1) == 'l' && source.charAt(linea+2) == 's' && source.charAt(linea+3) == 'e') {
            tokens.add(new Token(TipoToken.FALSE, "false", null, linea));
            linea = linea + 5;
        } else
        if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
            linea++;
        }else 
        if (source.charAt(linea-1) == 'f' && source.charAt(linea) == 'u' && source.charAt(linea+1) == 'n') {
            tokens.add(new Token(TipoToken.FUN, "fun", null, linea));
            linea = linea + 3;
        } else
        if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
            linea++;
        }else 
        if (source.charAt(linea-1) == 'i' && source.charAt(linea) == 'f') {
            tokens.add(new Token(TipoToken.IF, "if", null, linea));
            linea = linea + 2;
        }else
        if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
            linea++;
        }else
        if (source.charAt(linea-1) == 'n' && source.charAt(linea) == 'u' && source.charAt(linea+1) == 'l' && source.charAt(linea+2) == 'l') {
            tokens.add(new Token(TipoToken.NULL, "null", null, linea));
            linea = linea + 4;
        }
        else 
        if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
            linea++;
        }else
        if (source.charAt(linea-1) == 'o' && source.charAt(linea) == 'r') {
            tokens.add(new Token(TipoToken.OR, "or", null, linea));
            linea = linea + 2;
        } else
        if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
            linea++;
        }else
         if (source.charAt(linea-1) == 'p' && source.charAt(linea) == 'r' && source.charAt(linea+1) == 'i' && source.charAt(linea+2) == 'n' && source.charAt(linea+3) == 't') {
            tokens.add(new Token(TipoToken.PRINT, "print", null, linea));
            linea = linea + 5;
        } else
        if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
            linea++;
        }
        else if (source.charAt(linea-1) == 'r' && source.charAt(linea) == 'e' && source.charAt(linea+1) == 't' && source.charAt(linea+2) == 'u' && source.charAt(linea+3) == 'r' && source.charAt(linea+4) == 'n') {
            tokens.add(new Token(TipoToken.RETURN, "return", null, linea));
            linea = linea + 6;
        } else
        if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
            linea++;
        }
        else if (source.charAt(linea-1) == 's' && source.charAt(linea) == 'u' && source.charAt(linea+1) == 'p' && source.charAt(linea+2) == 'e' && source.charAt(linea+3) == 'r') {
            tokens.add(new Token(TipoToken.SUPER, "super", null, linea));
            linea = linea + 5;
        } else
        if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
            linea++;
        }
        else if (source.charAt(linea-1) == 't' && source.charAt(linea) == 'o') {
            tokens.add(new Token(TipoToken.TO, "to", null, linea));
            linea = linea + 2;
        } else
        if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
            linea++;
        }
        else if (source.charAt(linea-1) == 't' && source.charAt(linea) == 'r' && source.charAt(linea+1) == 'u' && source.charAt(linea+2) == 'e') {
            tokens.add(new Token(TipoToken.TRUE, "true", null, linea));
            linea = linea + 4;
        } else
        if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
            linea++;
        }
        else if (source.charAt(linea-1) == 'v' && source.charAt(linea) == 'a' && source.charAt(linea+1) == 'r') {
            tokens.add(new Token(TipoToken.VAR, "var", null, linea));
            linea = linea + 3;
        }
        else 
        if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
            linea++;
        }else
        if (source.charAt(linea-1) == 'w' && source.charAt(linea) == 'h' && source.charAt(linea+1) == 'i' && source.charAt(linea+2) == 'l' && source.charAt(linea+3) == 'e') {
            tokens.add(new Token(TipoToken.WHILE, "while",null, linea));
            linea = linea + 5;
        } else
        if (linea-1 < source.length() && source.charAt(linea-1) == ' ') {
            linea++;
        }else
        if (source.charAt(linea-1) == '!') {
                if (source.charAt(linea-1) == '!' && source.charAt(linea)=='=') {
                    tokens.add(new Token(TipoToken.COMP, "!=", null, linea));
                    linea=linea+2;
                }else
                tokens.add(new Token(TipoToken.NEG, "!", null, linea++));
            }        
            else
 
            if (source.charAt(linea-1) == '(') {
                tokens.add(new Token(TipoToken.PAR1, "(",null, linea));    
                linea++;
            }else
 
            if (source.charAt(linea-1) == ')') {
                tokens.add(new Token(TipoToken.PAR2, ")", null, linea++));    
            } else
 
            if (source.charAt(linea-1) == '{') {
                tokens.add(new Token(TipoToken.LLAVE1, "{", null, linea++));
            } else
 
            if (source.charAt(linea-1) == '}') {
                tokens.add(new Token(TipoToken.LLAVE2, "}", null, linea++));
            } else
 
            if (source.charAt(linea-1) == ',') {
                tokens.add(new Token(TipoToken.COMA, ",", null, linea++));
            } else
        
            if (source.charAt(linea-1) == '.') {
                tokens.add(new Token(TipoToken.PUNTO, ".", null, linea++));
            } else
 
            if (source.charAt(linea-1) == ';') {
                tokens.add(new Token(TipoToken.PCOMA, ";", null, linea++));
            } else

            if (source.charAt(linea-1) == '-') {
                tokens.add(new Token(TipoToken.MENOS, "-", null, linea++));
            } else
 
            if (source.charAt(linea-1) == '+') {
                tokens.add(new Token(TipoToken.MAS, "+", null, linea++));
            } else
 
            if (source.charAt(linea-1) == '*') {
                tokens.add(new Token(TipoToken.POR, "*", null, linea++));
            } else
 
            if (source.charAt(linea-1) == '/') {
                if(source.charAt(linea) == '/'){
                    linea=linea+2;
                    
                    while((source.charAt(linea) == '/') && (source.charAt(linea+1) == 'n')){
                            linea++;
                    }
                    linea=linea+4;
                }else if(source.charAt(linea) == '*'){
                    linea=linea+2;
                    System.out.println(linea);
                    while(!(source.charAt(linea) == '*') && !(source.charAt(linea+1) == '/')){
                            linea++;
                            System.out.println(linea);
                    }
                    
                }else 
                
                tokens.add(new Token(TipoToken.DIAG, "/", null, linea++));
            } else
 
            if (source.charAt(linea-1) == '!') {
                if (source.charAt(linea-1) == '!' && source.charAt(linea)=='=') {
                    tokens.add(new Token(TipoToken.COMP, "!=", null, linea));
                    linea=linea+2;
                } else
                tokens.add(new Token(TipoToken.NEG, "!", null, linea++));
            } else
 
            if (source.charAt(linea-1) == '=') {
                //tokens.add(new Token(TipoToken.IGUAL1, "=", "simbolos", linea++));
                if (source.charAt(linea-1)== '=' && source.charAt(linea)=='=') {
                    tokens.add(new Token(TipoToken.IGUAL2, "==", null, linea));
                    linea=linea+2;
                }
                else
                tokens.add(new Token(TipoToken.IGUAL1, "=", null, linea++));
             } else
  
            if (source.charAt(linea-1) == '<') {
                if (source.charAt(linea-1) == '<' && source.charAt(linea)=='=') {
                    tokens.add(new Token(TipoToken.MENORI, "<=", null, linea));
                    linea=linea+2;
                } else
                tokens.add(new Token(TipoToken.MENOR, "<", null, linea++));
            } else
 
            if (source.charAt(linea-1) == '>') {
                if (source.charAt(linea-1) == '>' && source.charAt(linea)=='=') {
                    tokens.add(new Token(TipoToken.MAYORI, ">=", null, linea));
                    linea=linea+2;
                } else
                tokens.add(new Token(TipoToken.MAYOR, ">", null, linea++));
            }else
            if (source.charAt(linea-1) == '"') {
                String aux = "";
                while (source.charAt(linea) != '"') {
                    linea++;
                    aux = aux.concat(String.valueOf(source.charAt(linea-1)));
                }
                tokens.add(new Token(TipoToken.CADENA, aux, aux, linea-1)); 
            }
            else 
            if(Character.isLetter(source.charAt(linea-1)) || Character.isDigit(source.charAt(linea-1))){    
                String aux = "";
                while((linea-1 < source.length()) && Character.isLetter(source.charAt(linea-1))) {
                    aux = aux.concat(String.valueOf(source.charAt(linea-1)));
                    if (linea == source.length()) { // Verifica si el número es el último carácter de la cadena de origen
                        linea++;
                        tokens.add(new Token(TipoToken.IDENTIFICADOR, aux, aux, linea++));
                        break;
                    } else
                    if(linea  < source.length() && source.charAt(linea) == ' ') {
                        linea++;
                        tokens.add(new Token(TipoToken.IDENTIFICADOR, aux, null, linea++));
                        break;
                    }
               
                 linea++;
                }
                while((linea-1 < source.length()) && Character.isDigit(source.charAt(linea-1))) {
                    aux = aux.concat(String.valueOf(source.charAt(linea-1)));
                    if (linea == source.length()) { // Verifica si el número es el último carácter de la cadena de origen
                        linea++;
                        tokens.add(new Token(TipoToken.NUMERO, aux, aux, linea++));
                        break;
                    } else if (!Character.isDigit(source.charAt(linea))) { // Verifica si el siguiente carácter no es un número
                        linea++;
                        tokens.add(new Token(TipoToken.NUMERO, aux, aux, linea++));
                        break;
                    }

                 linea++;
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