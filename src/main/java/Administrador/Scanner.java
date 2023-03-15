
package Administrador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scanner {

    private final String source;

    private final List<Token> tokens = new ArrayList<>();

    private int linea = 1;

    private static final Map<String, TipoToken> palabrasReservadas;
    static {
        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("and", TipoToken.AND);
        palabrasReservadas.put("class", TipoToken.CLASS);
        palabrasReservadas.put("also", TipoToken.ALSO);
        palabrasReservadas.put("false", TipoToken.FALSE );
        palabrasReservadas.put("to", TipoToken.TO);
        palabrasReservadas.put("fun", TipoToken.FUN); //definir funciones
        palabrasReservadas.put("if", TipoToken.IF);
        palabrasReservadas.put("null", TipoToken.NULL);
        palabrasReservadas.put("or", TipoToken.OR);
        palabrasReservadas.put("print", TipoToken.PRINT);
        palabrasReservadas.put("return", TipoToken.RETURN);
        palabrasReservadas.put("super", TipoToken.SUPER);
        palabrasReservadas.put("this", TipoToken.THIS);
        palabrasReservadas.put("true", TipoToken.TRUE);
        palabrasReservadas.put("var", TipoToken.VAR); //definir variables
        palabrasReservadas.put("while", TipoToken.WHILE);
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

    /*private static final Map<Integer, TipoToken> numeros;
    static{
        numeros = new HashMap<>();
        numeros.put(0, TipoToken.CERO);
        numeros.put(1, TipoToken.UNO);
        numeros.put(2, TipoToken.DOS);
        numeros.put(3, TipoToken.TRES);
        numeros.put(4, TipoToken.CUATRO);
        numeros.put(5, TipoToken.CINCO);
        numeros.put(6, TipoToken.SEIS);
        numeros.put(7, TipoToken.SIETE);
        numeros.put(8, TipoToken.OCHO);
        numeros.put(9, TipoToken.NUEVE);
    }*/

    Scanner(String source){
        this.source = source;

        
    }

    List<Token> scanTokens(){
        //Aquí va el corazón del scanner.
        int j=source.length();
        // while (source.charAt(linea)!= j ) {
        //     if("a"== source.substring(linea-1)){
           
        //         if("and"==source.substring(linea-1, linea+2)){
        //             tokens.add(new Token(TipoToken.AND, "and",palabrasReservadas, linea));
        //             linea=linea+3;
        //         }
        //         if("also"==source.substring(linea-1, linea+3)){
        //             tokens.add(new Token(TipoToken.ALSO, "also",palabrasReservadas, linea));
        //             linea=linea+4;
        //         } 
        //     }
        //     if ("class"== source.substring(linea-1,linea+4)) {
        //         tokens.add(new Token(TipoToken.CLASS, "class", palabrasReservadas, linea));
        //         linea=linea+5;
        //     }
            
        //     if("f"== source.substring(linea-1))
        //         if ("false"== source.substring(linea-1, linea+4)) {
        //             tokens.add(new Token(TipoToken.FALSE, "class", palabrasReservadas, linea));
        //             linea=linea+5;
        //         }else if("fun"== source.substring(linea-1, linea+2)){
        //             tokens.add(new Token(TipoToken.FUN, "class", palabrasReservadas, linea));
        //             linea=linea+3;
        //         }
             
                   
        //     if ("(" == source.substring(linea-1)) {
        //         tokens.add(new Token(TipoToken.PAR1, "(",simbolos, linea++));    
        //     }
        //     if (")" == source.substring(linea-1)) {
        //         tokens.add(new Token(TipoToken.PAR2, ")",simbolos, linea++));    
        //     }
        //     if("{"==source.substring(linea-1)){
        //         tokens.add(new Token(TipoToken.LLAVE1, "{",simbolos, linea++));
        //     }
        //     if("}"==source.substring(linea-1)){
        //         tokens.add(new Token(TipoToken.LLAVE2, "}",simbolos, linea++));
        //     }
        //     if(","==source.substring(linea-1)){
        //         tokens.add(new Token(TipoToken.COMA, ",",simbolos, linea++));
        //     }        
        //     if("."==source.substring(linea-1)){
        //         tokens.add(new Token(TipoToken.PUNTO, ".",simbolos, linea++));
        //     }
        //     if("}"==source.substring(linea-1)){
                 tokens.add(new Token(TipoToken.LLAVE2, "}",simbolos, linea++));
        //     }
            
            
            
        
        
        
        // }
        

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
