package Administrador;

import java.util.List;

public class Parser {

    private final List<Token> tokens;
    private Token AND = new Token(TipoToken.AND, "and", null, 0);
    private Token CLASS = new Token(TipoToken.CLASS, "class", null, 0);
    private Token ALSO = new Token(TipoToken.ALSO, "also", null, 0);
    private Token FALSE = new Token(TipoToken.FALSE, "false", null, 0);
    private Token TO = new Token(TipoToken.TO, "to", null, 0);
    private Token FUN = new Token(TipoToken.FUN, "fun", null, 0);
    private Token IF = new Token(TipoToken.IF, "if", null, 0);
    private Token NULL = new Token(TipoToken.NULL, "null", null, 0);
    private Token OR = new Token(TipoToken.OR, "or", null, 0);
    private Token PRINT = new Token(TipoToken.PRINT, "print", null, 0);
    private Token RETURN = new Token(TipoToken.RETURN, "return", null, 0);
    private Token SUPER = new Token(TipoToken.SUPER, "super", null, 0);
    private Token THIS = new Token(TipoToken.THIS, "this", null, 0);
    private Token TRUE = new Token(TipoToken.TRUE, "true", null, 0);
    private Token VAR = new Token(TipoToken.VAR, "var", null, 0);
    private Token WHILE = new Token(TipoToken.WHILE, "while", null, 0);


    // Símbolos
    private Token PAR1 = new Token(TipoToken.PAR1, "(", null, 0);
    private Token PAR2 = new Token(TipoToken.PAR2, ")", null, 0);
    private Token LLAVE1 = new Token(TipoToken.LLAVE1, "{", null, 0);
    private Token LLAVE2 = new Token(TipoToken.LLAVE2, "}", null, 0);
    private Token COMA = new Token(TipoToken.COMA, ",", null, 0);
    private Token PUNTO = new Token(TipoToken.PUNTO, ".", null, 0);
    private Token PCOMA = new Token(TipoToken.PCOMA, ";", null, 0);
    private Token MENOS = new Token(TipoToken.MENOS, "-", null, 0);
    private Token MAS = new Token(TipoToken.MAS, "+", null, 0);
    private Token POR = new Token(TipoToken.POR, "*", null, 0);
    private Token DIAG = new Token(TipoToken.DIAG, "/", null, 0);
    private Token NEG = new Token(TipoToken.NEG, "!", null, 0);
    private Token COMP = new Token(TipoToken.COMP, "!=", null, 0);
    private Token IGUAL1 = new Token(TipoToken.IGUAL1, "=", null, 0);
    private Token IGUAL2 = new Token(TipoToken.IGUAL2, "==", null, 0);
    private Token MENOR = new Token(TipoToken.MENOR, "<", null, 0);
    private Token MENORI = new Token(TipoToken.MENORI, "<=", null, 0);
    private Token MAYOR = new Token(TipoToken.MAYOR, ">", null, 0);
    private Token MAYORI = new Token(TipoToken.MAYORI, ">=", null, 0);

    // Números
    private Token NUMERO = new Token(TipoToken.NUMERO, "", null, 0);
    private Token CADENA = new Token(TipoToken.CADENA, "", null, 0);
    private Token IDENTIFICADOR = new Token(TipoToken.IDENTIFICADOR, "", null, 0);
    private final Token finCadena = new Token(TipoToken.EOF, "", null, 0);

    private int i = 0;
    private boolean hayErrores = false;

    private Token preanalisis;

    public Parser(List<Token> tokens){
        this.tokens = tokens;
    }

    public void parse(){
        i = 0;
        preanalisis = tokens.get(i);
        PROGRAM();
        if(!hayErrores && !preanalisis.equals(finCadena)){
            System.out.println("Error en la posición " + preanalisis.posicion + ". No se esperaba el token " + preanalisis.tipo);
        }
        else if(!hayErrores && preanalisis.equals(finCadena)){
            System.out.println("Consulta válida");
        }

        /*if(!preanalisis.equals(finCadena)){
            System.out.println("Error en la posición " + preanalisis.posicion + ". No se esperaba el token " + preanalisis.tipo);
        }else if(!hayErrores){
            System.out.println("Consulta válida");
        }*/
    }
    void PROGRAM(){
        if(preanalisis.equals(DECLARATION)){
        DECLARATION();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba la palabra reservada SELECT.");
        }
    }
    void DECLARATION(){

    }

    void coincidir(Token t){
        if(hayErrores) return;

        if(preanalisis.tipo == t.tipo){
            i++;
            preanalisis = tokens.get(i);
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba un  " + t.tipo);

        }
    }

}
