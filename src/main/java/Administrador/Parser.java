package Administrador;

import java.util.List;

public class Parser {

    private final List<Token> tokens;
    private Token and = new Token(TipoToken.AND, "and"  );
    private Token Class = new Token(TipoToken.CLASS, "class"  );
    private Token Else = new Token(TipoToken.ELSE, "also"  );
    private Token False = new Token(TipoToken.FALSE, "false"  );
    private Token For = new Token(TipoToken.FOR, "to"  );
    private Token fun = new Token(TipoToken.FUN, "fun"  );
    private Token If = new Token(TipoToken.IF, "if");
    private Token Null = new Token(TipoToken.NULL, "null"  );
    private Token or = new Token(TipoToken.OR, "or"  );
    private Token print = new Token(TipoToken.PRINT, "print"  );
    private Token Return = new Token(TipoToken.RETURN, "return"  );
    private Token Super = new Token(TipoToken.SUPER, "super"  );
    private Token This = new Token(TipoToken.THIS, "this"  );
    private Token True = new Token(TipoToken.TRUE, "true"  );
    private Token var = new Token(TipoToken.VAR, "var"  );
    private Token While = new Token(TipoToken.WHILE, "while"  );


    // Símbolos
    private Token par1 = new Token(TipoToken.PAR1, "("  );
    private Token par2 = new Token(TipoToken.PAR2, ")"  );
    private Token llave1 = new Token(TipoToken.LLAVE1, "{"  );
    private Token llave2 = new Token(TipoToken.LLAVE2, "}"  );
    private Token coma = new Token(TipoToken.COMA, ","  );
    private Token punto = new Token(TipoToken.PUNTO, "."  );
    private Token pcoma = new Token(TipoToken.PCOMA, ";"  );
    private Token menos = new Token(TipoToken.MENOS, "-"  );
    private Token mas = new Token(TipoToken.MAS, "+"  );
    private Token por = new Token(TipoToken.POR, "*"  );
    private Token diag = new Token(TipoToken.DIAG, "/"  );
    private Token neg = new Token(TipoToken.NEG, "!"  );
    private Token comp = new Token(TipoToken.COMP, "!="  );
    private Token igual1 = new Token(TipoToken.IGUAL1, "="  );
    private Token igual2 = new Token(TipoToken.IGUAL2, "=="  );
    private Token menor = new Token(TipoToken.MENOR, "<"  );
    private Token menori = new Token(TipoToken.MENORI, "<="  );
    private Token mayor = new Token(TipoToken.MAYOR, ">"  );
    private Token mayori = new Token(TipoToken.MAYORI, ">="  );

    // Números
    private Token numero = new Token(TipoToken.NUMERO, ""  );
    private Token cadena = new Token(TipoToken.CADENA, ""  );
    private Token identificador = new Token(TipoToken.IDENTIFICADOR, ""  );
    private final Token finCadena = new Token(TipoToken.EOF, ""  );

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
            System.out.println("Error en la linea" + preanalisis.linea + ". No se esperaba el token " + preanalisis.tipo);
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
            DECLARATION();
    }
    void DECLARATION() { //rev
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
        } else if (preanalisis.equals(llave1)||preanalisis.equals(While)||preanalisis.equals(Return)||preanalisis.equals(print)||preanalisis.equals(If)||preanalisis.equals(For)||preanalisis.equals(neg)||preanalisis.equals(menos)||preanalisis.equals(True)||preanalisis.equals(False)||preanalisis.equals(Null)||preanalisis.equals(This)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(Super)||preanalisis.equals(par1)){
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
            coincidir(pcoma);
        }else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada var identificador o ;");
        }
    }
    void VAR_INIT(){//rev
        if(hayErrores) return;
        if(preanalisis.equals(igual1)){
            coincidir(igual1);
            EXPRESSION();
        }
    }
    void STATEMENT(){//rev
        if(hayErrores) return;
        if (preanalisis.equals(neg)||preanalisis.equals(menos)||preanalisis.equals(True)||preanalisis.equals(False)||preanalisis.equals(Null)||preanalisis.equals(This)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(Super)||preanalisis.equals(par1)){
            EXPRESSION();
        }else if (preanalisis.equals(For)){
            FOR_STMT();
        }else if (preanalisis.equals(If)){
            IF_STMT();
        }else if (preanalisis.equals(print)){
            PRINT_STMT();
        }else if (preanalisis.equals(While)){
            WHILE_STMT();
        }else if (preanalisis.equals(Return)){
            RETURN_STMT();
        }else if (preanalisis.equals(llave1)){
            BLOCK();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada ! - true false null this numero cadena identificador super ( for if print return while ).");
        }
    }
    void EXPR_STMT(){ //rev
        if(hayErrores) return;
        if (preanalisis.equals(neg)||preanalisis.equals(menos)||preanalisis.equals(True)||preanalisis.equals(False)||preanalisis.equals(Null)||preanalisis.equals(This)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(Super)||preanalisis.equals(par1)){
            EXPRESSION();
            coincidir(pcoma);
        }else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada ! - true false null this numero cadena identificador super ( .");
        }
    }

    void  FOR_STMT(){//rev
        if(hayErrores) return;
        if (preanalisis.equals(For)){
            coincidir(For);
            coincidir(par1);
            FOR_STMT_1();FOR_STMT_2();FOR_STMT_3();
            coincidir(par2);
            STATEMENT();
        }else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada for.");
        }
    }
    void  FOR_STMT_1(){//rev
        if(hayErrores) return;
        if(preanalisis.equals(var)){
            VAR_DECL();
        }
        else if (preanalisis.equals(neg)||preanalisis.equals(menos)||preanalisis.equals(True)||preanalisis.equals(False)||preanalisis.equals(Null)||preanalisis.equals(This)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(Super)||preanalisis.equals(par1)||preanalisis.equals(pcoma)){
            
            EXPR_STMT();
        }else if(preanalisis.equals(pcoma)){
            coincidir(pcoma);
        }
            else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada var ! - true false null this numero cadena identificador super ( ;.");
        }
    }
    void  FOR_STMT_2(){//rev
        if(hayErrores) return;
        if (preanalisis.equals(neg)||preanalisis.equals(menos)||preanalisis.equals(True)||preanalisis.equals(False)||preanalisis.equals(Null)||preanalisis.equals(This)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(Super)||preanalisis.equals(par1)||preanalisis.equals(pcoma)){
            EXPRESSION();
        }else if(preanalisis.equals(pcoma)){
            coincidir(pcoma);
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada ! - true false null this numero cadena identificador super ( ; .");
        }

    }
    void  FOR_STMT_3(){//rev
        if(hayErrores) return;
        if (preanalisis.equals(neg)||preanalisis.equals(menos)||preanalisis.equals(True)||preanalisis.equals(False)||preanalisis.equals(Null)||preanalisis.equals(This)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(Super)||preanalisis.equals(par1)){
            EXPRESSION();
        }
    }
    void  IF_STMT (){//rev
        if(hayErrores) return;
        if(preanalisis.equals(If)){
            coincidir(If);
            coincidir(par1); EXPRESSION(); coincidir(par2);
            STATEMENT();
            ELSE_STATEMENT();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada if.");
        }

    }
    void  ELSE_STATEMENT (){//rev
        if(hayErrores) return;
        if(preanalisis.equals(Else)){
            coincidir(Else);
            STATEMENT();
        }

    }
    void PRINT_STMT(){//rev
        if(hayErrores) return;
        if(preanalisis.equals(print)){
            coincidir(print);
            EXPRESSION();
            coincidir(pcoma);
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada print.");
        }
    }
    void RETURN_STMT(){//rev
        if(hayErrores) return;
        if(preanalisis.equals(Return)){
            coincidir(Return);
            RETURN_EXP_OPC();
            coincidir(pcoma);
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada return.");
        }
    }
    void RETURN_EXP_OPC(){//rev
        if(hayErrores) return;
        if (preanalisis.equals(neg)||preanalisis.equals(menos)||preanalisis.equals(True)||preanalisis.equals(False)||preanalisis.equals(Null)||preanalisis.equals(This)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(Super)||preanalisis.equals(par1)){
            EXPRESSION();
        }
    }
    void WHILE_STMT(){//rev
        if(hayErrores) return;
        if(preanalisis.equals(While)){
            coincidir(While);
            coincidir(par1); EXPRESSION(); coincidir(par2);
            STATEMENT();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada While.");
        }
    }
    void BLOCK(){//rev
        if(hayErrores) return;
        if(preanalisis.equals(llave1)){
            coincidir(llave1);
            BLOCK_DECL();
            coincidir(llave2);
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada ( ).");
        }
    }
    void BLOCK_DECL(){//rev
        if(hayErrores) return;
        if (preanalisis.equals(neg)||preanalisis.equals(menos)||preanalisis.equals(True)||preanalisis.equals(False)||preanalisis.equals(Null)||preanalisis.equals(This)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(Super)||preanalisis.equals(par1)||preanalisis.equals(For)||preanalisis.equals(If)||preanalisis.equals(print)||preanalisis.equals(Return)||preanalisis.equals(While)||preanalisis.equals(llave1)||preanalisis.equals(Class)||preanalisis.equals(fun)||preanalisis.equals(var)){
            DECLARATION(); BLOCK_DECL();
        }
    }
    void EXPRESSION(){//rev
        if(hayErrores) return;
        if (preanalisis.equals(neg)||preanalisis.equals(menos)||preanalisis.equals(True)||preanalisis.equals(False)||preanalisis.equals(Null)||preanalisis.equals(This)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(Super)||preanalisis.equals(par1)){
         ASSIGNMENT();
        }else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada ! - true false null this numero cadena identificador super ( .");
        }
    }
    void ASSIGNMENT(){//rev
        if(hayErrores) return;
        if (preanalisis.equals(neg)||preanalisis.equals(menos)||preanalisis.equals(True)||preanalisis.equals(False)||preanalisis.equals(Null)||preanalisis.equals(This)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(Super)||preanalisis.equals(par1)){
            LOGIC_OR();
            ASSIGMENT_OPC();
        }else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada ! - true false null this numero cadena identificador super ( .");
        }

    }
    void  ASSIGMENT_OPC(){//rev
        if(hayErrores) return;
        if (preanalisis.equals(igual1)){
            coincidir(igual1);
            EXPRESSION();
        }
    }

    void  LOGIC_OR(){//rev
        if(hayErrores) return;
        if (preanalisis.equals(neg)||preanalisis.equals(menos)||preanalisis.equals(True)||preanalisis.equals(False)||preanalisis.equals(Null)||preanalisis.equals(This)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(Super)||preanalisis.equals(par1)){
            LOGIC_AND(); LOGIC_OR_2();
        }else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada ! - true false null this numero cadena identificador super ( .");
        }
    }
    void  LOGIC_OR_2(){//rev
        if(hayErrores) return;
        if (preanalisis.equals(or)){
            coincidir(or);
            LOGIC_AND();
            LOGIC_OR_2();
        }
    }
    void  LOGIC_AND(){//rev
        if(hayErrores) return;
        if (preanalisis.equals(neg)||preanalisis.equals(menos)||preanalisis.equals(True)||preanalisis.equals(False)||preanalisis.equals(Null)||preanalisis.equals(This)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(Super)||preanalisis.equals(par1)){
            EQUALITY();
            LOGIC_AND_2();
        }else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada ! - true false null this numero cadena identificador super ( .");
        }
    }
    void LOGIC_AND_2(){//rev
        if(hayErrores) return;
        if (preanalisis.equals(and)){
            coincidir(and);
            EQUALITY();
            LOGIC_AND_2();
        }
    }
    void EQUALITY(){//rev
        if(hayErrores) return;
        if (preanalisis.equals(neg)||preanalisis.equals(menos)||preanalisis.equals(True)||preanalisis.equals(False)||preanalisis.equals(Null)||preanalisis.equals(This)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(Super)||preanalisis.equals(par1)){
            COMPARISON();
            EQUALITY_2();
        }else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada ! - true false null this numero cadena identificador super ( .");
        }
    }
    void EQUALITY_2(){//rev
        if(hayErrores) return;
        if (preanalisis.equals(comp)){
            coincidir(comp);
            COMPARISON(); EQUALITY_2();
        }else if (preanalisis.equals(igual2)){
            coincidir(igual2);
            COMPARISON(); EQUALITY_2();
        }
    }
    void COMPARISON(){//rev
        if(hayErrores) return;
        if (preanalisis.equals(neg)||preanalisis.equals(menos)||preanalisis.equals(True)||preanalisis.equals(False)||preanalisis.equals(Null)||preanalisis.equals(This)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(Super)||preanalisis.equals(par1)){
            TERM();
            COMPARISON_2();
        }else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada ! - true false null this numero cadena identificador super ( .");
        }
    }
    void COMPARISON_2(){//rev
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
    void TERM(){//rev
        if(hayErrores) return;
        if (preanalisis.equals(neg)||preanalisis.equals(menos)||preanalisis.equals(True)||preanalisis.equals(False)||preanalisis.equals(Null)||preanalisis.equals(This)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(Super)||preanalisis.equals(par1)){
            FACTOR(); TERM_2();
        }else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada ! - true false null this numero cadena identificador super ( .");
        }
    }
    void TERM_2(){//rev
        if(hayErrores) return;
        if (preanalisis.equals(menos)){
            coincidir(menos);
            FACTOR(); TERM_2();
        } else if (preanalisis.equals(mas)) {
            coincidir(mas);
            FACTOR(); TERM_2();
        }
    }
    void FACTOR(){//rev
        if(hayErrores) return;
        if (preanalisis.equals(neg)||preanalisis.equals(menos)||preanalisis.equals(True)||preanalisis.equals(False)||preanalisis.equals(Null)||preanalisis.equals(This)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(Super)||preanalisis.equals(par1)){
            UNARY();
            FACTOR_2();
        }else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada ! - true false null this numero cadena identificador super ( .");
        }

    }
    void FUNCTIONS(){

    }
    void FUNCTION(){

    }
    void CALL() {//rev
        if(hayErrores) return;
        if (preanalisis.equals(True)||preanalisis.equals(False)||preanalisis.equals(Null)||preanalisis.equals(This)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(Super)||preanalisis.equals(par1)){
            PRIMARY();
            CALL_2();
        }else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada true false null this numero cadena identificador super ( .");
        }
    }
    void CALL_2() {//rev
        if(hayErrores) return;
        if (preanalisis.equals(par1)){
            coincidir(par1); ARGUMENTS_OPC(); coincidir(par2); CALL_2();
        }else if (preanalisis.equals(punto)){
            coincidir(punto); coincidir(identificador); CALL_2();
        }
    }
    void CALL_OPC() {//rev
        if(hayErrores) return;
        if (preanalisis.equals(True)||preanalisis.equals(False)||preanalisis.equals(Null)||preanalisis.equals(This)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(Super)||preanalisis.equals(par1)){
            CALL(); coincidir(punto);
        }
    }
    void PRIMARY() {//rev
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
            coincidir(Super); coincidir(punto); coincidir(identificador);
        }else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada true false null this numero cadena identificador super ( .");
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