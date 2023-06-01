package Administrador;

import java.util.List;

public class Parser {

    private final List<Token> tokens;
    private Token and = new Token(TipoToken.AND, "and", null, 0);
    private Token Class = new Token(TipoToken.CLASS, "class", null, 0);
    private Token Else = new Token(TipoToken.ELSE, "also", null, 0);
    private Token False = new Token(TipoToken.FALSE, "false", null, 0);
    private Token For = new Token(TipoToken.FOR, "to", null, 0);
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
            System.out.println("Error en la posición " + preanalisis.linea + ". No se esperaba el token " + preanalisis.tipo);
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
    void PROGRAM() {
        if(hayErrores) return;
        if (preanalisis.equals(Class)||preanalisis.equals(fun)||preanalisis.equals(var)||preanalisis.equals(neg)||preanalisis.equals(menos)||preanalisis.equals(True)||preanalisis.equals(False)||preanalisis.equals(Null)||preanalisis.equals(This)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(Super)||preanalisis.equals(par1)||preanalisis.equals(For)||preanalisis.equals(If)||preanalisis.equals(print)||preanalisis.equals(Return)||preanalisis.equals(While)||preanalisis.equals(llave1)){
            DECLARATION();
        }
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
        } else if (preanalisis.equals(neg)||preanalisis.equals(menos)||preanalisis.equals(True)||preanalisis.equals(False)||preanalisis.equals(Null)||preanalisis.equals(This)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(Super)||preanalisis.equals(par1)){
            STATEMENT();
            DECLARATION();
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
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada Class.");
        }
    }
    void CLASS_INHER(){
        if(hayErrores) return;
        if (preanalisis.equals(mayor)){
            coincidir(menor);
            coincidir(identificador);
        }
    }
    void FUN_DECL(){
        if(hayErrores) return;
        if (preanalisis.equals(fun)){
            coincidir(fun);
            FUNCTION();
        }else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada fun.");
        }
    }
    void VAR_DECL(){
        if(hayErrores) return;
        if (preanalisis.equals(var)){
            coincidir(var);
            coincidir(identificador);
            VAR_INIT();
        }else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada var.");
        }
    }
    void VAR_INIT(){
        if(hayErrores) return;
        if (preanalisis.equals(neg)||preanalisis.equals(menos)||preanalisis.equals(True)||preanalisis.equals(False)||preanalisis.equals(Null)||preanalisis.equals(This)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(Super)||preanalisis.equals(par1)){
            EXPRESSION();
        }
    }
    void STATEMENT(){
        if(hayErrores) return;
        if (preanalisis.equals(neg)||preanalisis.equals(menos)||preanalisis.equals(True)||preanalisis.equals(False)||preanalisis.equals(Null)||preanalisis.equals(This)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(Super)||preanalisis.equals(par1)){
            EXPRESSION();
        }else if (preanalisis.equals(For)){
            FOR_STMT();
        }else if (preanalisis.equals(If)){
            IF_STMT();
        }else if (preanalisis.equals(print)){
            PRINT_STMT();
        }else if (preanalisis.equals(Return)){
            RETURN_STMT();
        }else if (preanalisis.equals(While)){
            WHILE_STMT();
        }else if (preanalisis.equals(llave1)){
            BLOCK();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada SELECT.");
        }
    }
    void EXPR_STMT(){
        if(hayErrores) return;
        if (preanalisis.equals(neg)||preanalisis.equals(menos)||preanalisis.equals(True)||preanalisis.equals(False)||preanalisis.equals(Null)||preanalisis.equals(This)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(Super)||preanalisis.equals(par1)){
            EXPRESSION();
        }else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada SELECT.");
        }
    }

    void  FOR_STMT(){
        if(hayErrores) return;
        if (preanalisis.equals(For)){
            coincidir(For);
            coincidir(par1);
            FOR_STMT_1();FOR_STMT_2();FOR_STMT_3();
            coincidir(par2);
            STATEMENT();
        }else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada SELECT.");
        }
    }
    void  FOR_STMT_1(){//
        if(hayErrores) return;
        if(preanalisis.equals(var)){
            VAR_DECL();
        }
        else if (preanalisis.equals(neg)||preanalisis.equals(menos)||preanalisis.equals(True)||preanalisis.equals(False)||preanalisis.equals(Null)||preanalisis.equals(This)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(Super)||preanalisis.equals(par1)||preanalisis.equals(pcoma)){

            EXPR_STMT();
        }else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada SELECT.");
        }
    }
    void  FOR_STMT_2(){
        if(hayErrores) return;
        if (preanalisis.equals(neg)||preanalisis.equals(menos)||preanalisis.equals(True)||preanalisis.equals(False)||preanalisis.equals(Null)||preanalisis.equals(This)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(Super)||preanalisis.equals(par1)){
            EXPRESSION();
        }else if(preanalisis.equals(pcoma)){
            coincidir(pcoma);
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada SELECT.");
        }

    }
    void  FOR_STMT_3(){
        if(hayErrores) return;
        if (preanalisis.equals(neg)||preanalisis.equals(menos)||preanalisis.equals(True)||preanalisis.equals(False)||preanalisis.equals(Null)||preanalisis.equals(This)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(Super)||preanalisis.equals(par1)){
            EXPRESSION();
        }
    }
    void  IF_STMT (){
        if(hayErrores) return;
        if(preanalisis.equals(If)){
            coincidir(If);
            coincidir(par1); EXPRESSION(); coincidir(par2);
            STATEMENT();
            ELSE_STATEMENT();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada SELECT.");
        }

    }
    void  ELSE_STATEMENT (){
        if(hayErrores) return;
        if(preanalisis.equals(Else)){
            coincidir(Else);
            STATEMENT();
        }

    }
    void PRINT_STMT(){
        if(hayErrores) return;
        if(preanalisis.equals(print)){
            coincidir(print);
            EXPRESSION();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada SELECT.");
        }
    }
    void RETURN_STMT(){
        if(hayErrores) return;
        if(preanalisis.equals(Return)){
            coincidir(Return);
            RETURN_EXP_OPC();
            coincidir(pcoma);
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada SELECT.");
        }
    }
    void RETURN_EXP_OPC(){
        if(hayErrores) return;
        if (preanalisis.equals(neg)||preanalisis.equals(menos)||preanalisis.equals(True)||preanalisis.equals(False)||preanalisis.equals(Null)||preanalisis.equals(This)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(Super)||preanalisis.equals(par1)){
            EXPRESSION();
        }
    }
    void WHILE_STMT(){
        if(hayErrores) return;
        if(preanalisis.equals(While)){
            coincidir(While);
            coincidir(par1); EXPRESSION(); coincidir(par2);
            STATEMENT();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada SELECT.");
        }
    }
    void BLOCK(){
        if(hayErrores) return;
        if(preanalisis.equals(llave1)){
            coincidir(llave1);
            BLOCK_DECL();
            coincidir(llave2);
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada SELECT.");
        }
    }
    void BLOCK_DECL(){
        if(hayErrores) return;
        if (preanalisis.equals(neg)||preanalisis.equals(menos)||preanalisis.equals(True)||preanalisis.equals(False)||preanalisis.equals(Null)||preanalisis.equals(This)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(Super)||preanalisis.equals(par1)||preanalisis.equals(For)||preanalisis.equals(If)||preanalisis.equals(print)||preanalisis.equals(Return)||preanalisis.equals(While)||preanalisis.equals(llave1)||preanalisis.equals(Class)||preanalisis.equals(fun)||preanalisis.equals(var)){
            DECLARATION(); BLOCK_DECL();
        }
    }
    void EXPRESSION(){
        if(hayErrores) return;
        if (preanalisis.equals(neg)||preanalisis.equals(menos)||preanalisis.equals(True)||preanalisis.equals(False)||preanalisis.equals(Null)||preanalisis.equals(This)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(Super)||preanalisis.equals(par1)){
         ASSIGNMENT();
        }else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada SELECT.");
        }
    }
    void ASSIGNMENT(){
        if(hayErrores) return;
        if (preanalisis.equals(neg)||preanalisis.equals(menos)||preanalisis.equals(True)||preanalisis.equals(False)||preanalisis.equals(Null)||preanalisis.equals(This)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(Super)||preanalisis.equals(par1)){
            LOGIC_OR();
            ASSIGMENT_OPC();
        }else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada SELECT.");
        }

    }
    void  ASSIGMENT_OPC(){
        if(hayErrores) return;
        if (preanalisis.equals(igual1)){
            coincidir(igual1);
        }
    }

    void  LOGIC_OR(){
        if(hayErrores) return;
        if (preanalisis.equals(neg)||preanalisis.equals(menos)||preanalisis.equals(True)||preanalisis.equals(False)||preanalisis.equals(Null)||preanalisis.equals(This)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(Super)||preanalisis.equals(par1)){
            LOGIC_AND(); LOGIC_OR_2();
        }else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada SELECT.");
        }
    }
    void  LOGIC_OR_2(){
        if(hayErrores) return;
        if (preanalisis.equals(or)){
            coincidir(or);
            LOGIC_AND();
            LOGIC_OR_2();
        }
    }
    void  LOGIC_AND(){
        if(hayErrores) return;
        if (preanalisis.equals(neg)||preanalisis.equals(menos)||preanalisis.equals(True)||preanalisis.equals(False)||preanalisis.equals(Null)||preanalisis.equals(This)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(Super)||preanalisis.equals(par1)){
            EQUALITY();
            LOGIC_AND_2();
        }else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada SELECT.");
        }
    }
    void LOGIC_AND_2(){
        if(hayErrores) return;
        if (preanalisis.equals(and)){
            coincidir(and);
            EQUALITY();
            LOGIC_AND_2();
        }
    }
    void EQUALITY(){
        if(hayErrores) return;
        if (preanalisis.equals(neg)||preanalisis.equals(menos)||preanalisis.equals(True)||preanalisis.equals(False)||preanalisis.equals(Null)||preanalisis.equals(This)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(Super)||preanalisis.equals(par1)){
            COMPARISON();
            EQUALITY_2();
        }else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada SELECT.");
        }
    }
    void EQUALITY_2(){
        if(hayErrores) return;
        if (preanalisis.equals(comp)){
            coincidir(comp);
            COMPARISON(); EQUALITY_2();
        }else if (preanalisis.equals(igual1)){
            coincidir(igual1);
            COMPARISON(); EQUALITY_2();
        }
    }
    void COMPARISON(){
        if(hayErrores) return;
        if (preanalisis.equals(neg)||preanalisis.equals(menos)||preanalisis.equals(True)||preanalisis.equals(False)||preanalisis.equals(Null)||preanalisis.equals(This)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(Super)||preanalisis.equals(par1)){
            TERM();
            COMPARISON_2();
        }else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada SELECT.");
        }
    }
    void COMPARISON_2(){
        if(hayErrores) return;
        if (preanalisis.equals(mayor)){
            coincidir(mayor);
            TERM(); COMPARISON_2();
        }else if (preanalisis.equals(mayori)){
            coincidir(mayori);
            TERM(); COMPARISON_2();
        }else if (preanalisis.equals(menor)){
            coincidir(menor);
            TERM(); COMPARISON_2();
        }else if (preanalisis.equals(menori)){
            coincidir(menori);
            TERM(); COMPARISON_2();
        }
    }
    void TERM(){
        if(hayErrores) return;
        if (preanalisis.equals(neg)||preanalisis.equals(menos)||preanalisis.equals(True)||preanalisis.equals(False)||preanalisis.equals(Null)||preanalisis.equals(This)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(Super)||preanalisis.equals(par1)){
            FACTOR(); TERM_2();
        }else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada SELECT.");
        }
    }
    void TERM_2(){
        if(hayErrores) return;
        if (preanalisis.equals(menos)){
            coincidir(menos);
            FACTOR(); TERM_2();
        } else if (preanalisis.equals(mas)) {
            coincidir(mas);
            FACTOR(); TERM_2();
        }
    }
    void FACTOR(){
        if(hayErrores) return;
        if (preanalisis.equals(neg)||preanalisis.equals(menos)||preanalisis.equals(True)||preanalisis.equals(False)||preanalisis.equals(Null)||preanalisis.equals(This)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(Super)||preanalisis.equals(par1)){
            UNARY();
            FACTOR_2();
        }else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada SELECT.");
        }

    }
    void FACTOR_2(){
        if(hayErrores) return;
        if (preanalisis.equals(diag)){
            coincidir(diag);
            UNARY();
            FACTOR_2();
        }else if (preanalisis.equals(por)){
            coincidir(por);
            UNARY();
            FACTOR_2();
        }

    }
    void UNARY(){
        if(hayErrores) return;
        if (preanalisis.equals(neg)){
            coincidir(neg);
            UNARY();
        }else if (preanalisis.equals(menos)){
            coincidir(menos);
            UNARY();
            FACTOR_2();
        }else if (preanalisis.equals(True)||preanalisis.equals(False)||preanalisis.equals(Null)||preanalisis.equals(This)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(Super)||preanalisis.equals(par1)){
            coincidir(menos);
            UNARY();
            FACTOR_2();
        }else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada SELECT.");
        }

    }
    void CALL() {
        if(hayErrores) return;
        if (preanalisis.equals(True)||preanalisis.equals(False)||preanalisis.equals(Null)||preanalisis.equals(This)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(Super)||preanalisis.equals(par1)){
            PRIMARY();
            CALL_2();
        }else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada SELECT.");
        }
    }
    void CALL_2() {
        if(hayErrores) return;
        if (preanalisis.equals(par1)){
            coincidir(par1); ARGUMENTS_OPC(); coincidir(par2); CALL_2();
        }else if (preanalisis.equals(punto)){
            coincidir(punto); coincidir(identificador); CALL_2();
        }
    }
    void CALL_OPC() {
        if(hayErrores) return;
        if (preanalisis.equals(True)||preanalisis.equals(False)||preanalisis.equals(Null)||preanalisis.equals(This)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(Super)||preanalisis.equals(par1)){
            CALL();
        }
    }
    void PRIMARY() {
        if(hayErrores) return;
        if (preanalisis.equals(True)){
            coincidir(True);
        }else if (preanalisis.equals(False)){
            coincidir(False);
        }else if (preanalisis.equals(Null)){
            coincidir(Null);
        }else if (preanalisis.equals(This)){
            coincidir(This);
        }else if (preanalisis.equals(numero)){
            coincidir(numero);
        }else if (preanalisis.equals(cadena)){
            coincidir(cadena);
        }else if (preanalisis.equals(identificador)){
            coincidir(identificador);
        }else if (preanalisis.equals(par1)){
            coincidir(par1);
            EXPRESSION();
            coincidir(par2);
        }else if (preanalisis.equals(Super)){
            coincidir(Super);
        }else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada SELECT.");
        }
    }

    void FUNCTION(){
        if(hayErrores) return;
        if (preanalisis.equals(identificador)){
            coincidir(identificador); coincidir(par1); PARAMETERS_OPC(); coincidir(par2); BLOCK();
        }else if (preanalisis.equals(punto)){
            coincidir(punto); coincidir(identificador); CALL_2();
        }
    }
    void FUNCTIONS(){
        if(hayErrores) return;
        if (preanalisis.equals(identificador)){
            FUNCTION(); FUNCTIONS();
        }
    }
    void PARAMETERS_OPC(){
        if(hayErrores) return;
        if (preanalisis.equals(identificador)){
            PARAMETERS();
        }
    }
    void PARAMETERS(){
        if(hayErrores) return;
        if (preanalisis.equals(identificador)){
            coincidir(identificador); PARAMETERS_2();
        }
    }
    void PARAMETERS_2(){
        if(hayErrores) return;
        if (preanalisis.equals(coma)){
            coincidir(coma); coincidir(identificador); PARAMETERS_2();
        }
    }
    void ARGUMENTS_OPC(){
        if(hayErrores) return;
        if (preanalisis.equals(neg)||preanalisis.equals(menos)||preanalisis.equals(True)||preanalisis.equals(False)||preanalisis.equals(Null)||preanalisis.equals(This)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(Super)||preanalisis.equals(par1)){
            ARGUMENTS();
        }
    }
    void ARGUMENTS(){
        if(hayErrores) return;
        if (preanalisis.equals(neg)||preanalisis.equals(menos)||preanalisis.equals(True)||preanalisis.equals(False)||preanalisis.equals(Null)||preanalisis.equals(This)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(Super)||preanalisis.equals(par1)){
            EXPRESSION();ARGUMENTS_2();
        }else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada SELECT.");
        }
    }
    void ARGUMENTS_2(){
        if(hayErrores) return;
        if (preanalisis.equals(coma)){
            coincidir(coma); EXPRESSION(); ARGUMENTS_2();
        }
    }

    void coincidir(Token t){
        if(hayErrores) return;

        if(preanalisis.tipo == t.tipo){
            i++;
            preanalisis = tokens.get(i);
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba un  " + t.tipo);

        }
    }

}