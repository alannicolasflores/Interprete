package Administrador;

import java.util.List;

public class Parser {

    private final List<Token> tokens;
    private Token and = new Token(TipoToken.AND, "and", null, 0);
    private Token Class = new Token(TipoToken.CLASS, "class", null, 0);
    private Token also = new Token(TipoToken.ALSO, "also", null, 0);
    private Token False = new Token(TipoToken.FALSE, "false", null, 0);
    private Token to = new Token(TipoToken.TO, "to", null, 0);
    private Token fun = new Token(TipoToken.FUN, "fun", null, 0);
    private Token If = new Token(TipoToken.IF, "if", null, 0);
    private Token Null = new Token(TipoToken.NULL, "null", null, 0);
    private Token or = new Token(TipoToken.OR, "or", null, 0);
    private Token print = new Token(TipoToken.PRINT, "print", null, 0);
    private Token Return = new Token(TipoToken.RETURN, "return", null, 0);
    private Token Super = new Token(TipoToken.SUPER, "super", null, 0);
    private Token This = new Token(TipoToken.THIS, "this", null, 0);
    private Token True = new Token(TipoToken.TRUE, "true", null, 0);
    private Token var = new Token(TipoToken.VAR, "var", null, 0);
    private Token While = new Token(TipoToken.WHILE, "while", null, 0);


    // Símbolos
    private Token par1 = new Token(TipoToken.PAR1, "(", null, 0);
    private Token par2 = new Token(TipoToken.PAR2, ")", null, 0);
    private Token llave1 = new Token(TipoToken.LLAVE1, "{", null, 0);
    private Token llave2 = new Token(TipoToken.LLAVE2, "}", null, 0);
    private Token coma = new Token(TipoToken.COMA, ",", null, 0);
    private Token punto = new Token(TipoToken.PUNTO, ".", null, 0);
    private Token pcoma = new Token(TipoToken.PCOMA, ";", null, 0);
    private Token menos = new Token(TipoToken.MENOS, "-", null, 0);
    private Token mas = new Token(TipoToken.MAS, "+", null, 0);
    private Token por = new Token(TipoToken.POR, "*", null, 0);
    private Token diag = new Token(TipoToken.DIAG, "/", null, 0);
    private Token neg = new Token(TipoToken.NEG, "!", null, 0);
    private Token comp = new Token(TipoToken.COMP, "!=", null, 0);
    private Token igual1 = new Token(TipoToken.IGUAL1, "=", null, 0);
    private Token igual2 = new Token(TipoToken.IGUAL2, "==", null, 0);
    private Token menor = new Token(TipoToken.MENOR, "<", null, 0);
    private Token menori = new Token(TipoToken.MENORI, "<=", null, 0);
    private Token mayor = new Token(TipoToken.MAYOR, ">", null, 0);
    private Token mayori = new Token(TipoToken.MAYORI, ">=", null, 0);

    // Números
    private Token numero = new Token(TipoToken.NUMERO, "", null, 0);
    private Token cadena = new Token(TipoToken.CADENA, "", null, 0);
    private Token identificador = new Token(TipoToken.IDENTIFICADOR, "", null, 0);
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
        if(hayErrores) return;
        if(preanalisis.equals(class)||preanalisis.equals(fun)||preanalisis.equals(var)||preanalisis.equals(false)||preanalisis.equals(null)||preanalisis.equals(this)||preanalisis.equals(number)){
            coincidir(identificador);
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba la palabra reservada SELECT.");
        }
    }
    void PROGRAM() {
        if(hayErrores) return;
        DECLARATION();
    }
    void DECLARATION() {
        if(hayErrores) return;
        if (preanalisis.equals(Class)) {// se analiza el primero
            CLASS_DECL();
            DECLARATION();
        } else if (preanalisis.equals(fun)) {
            FUN_DECL();
            DECLARATION();
        } else if (preanalisis.equals(var)) {
            VAR_DECL();
            coincidir(pcoma);
            DECLARATION();
        } else if (){
            // Regla épsilon
        }else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba la palabra reservada SELECT.");
        }
    }
    void CLASS_DECL(){
        if(hayErrores) return;
        if (preanalisis.equals(Class)){
           coincidir(Class);
           coincidir(identificador);
           CLASS_INHER();
           coincidir(llave1);
           FUNCTIONS();
           coincidir(llave2);
        }else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba la palabra reservada SELECT.");
        }
    }
    void CLASS_INHER(){
        if(hayErrores) return;
        if (preanalisis.equals(fun)) {
            coincidir(menor);
            coincidir(identificador);
        }else if {
            // Regla épsilon
        }else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba la palabra reservada SELECT.");
        }

    }
    void FUN_DECL(){
        if(hayErrores) return;
        if (preanalisis.equals(fun)){
            coincidir(fun);
            FUNCTION();
        }else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba la palabra reservada SELECT.");
        }
    }
    void VAR_DECL(){
        if(hayErrores) return;
        if (preanalisis.equals(var)){
            coincidir(fun);
            coincidir(identificador);
            VAR_INIT();
        }else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba la palabra reservada SELECT.");
        }
    }
    void VAR_INIT(){
        if(hayErrores) return;
        if (preanalisis.equals(igual1)){
            coincidir(igual1);
            EXPRESSION();
        }else if {
            // Regla épsilon
        }else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba la palabra reservada SELECT.");
        }
    }
    void FUNCTIONS(){

    }
    void FUNCTION(){

    }
    void STATEMENT(){

    }
    void EXPR_STMT(){

    }
    void  FOR_STMT(){

    }
    void  FOR_STMT_1(){

    }
    void  FOR_STMT_2(){

    }
    void  FOR_STMT_3(){

    }
    void  IF_STMT (){

    }
    void  ELSE_STATEMENT (){

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
