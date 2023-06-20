/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrador;


public class Token {
    final TipoToken tipo;
    final String lexema; 
    final Object literal;
    final int linea;

    public Token(TipoToken tipo, String lexema, Object literal, int linea) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.literal = literal;
        this.linea = linea;
    }
    public Token(TipoToken tipo, String lexema) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.literal = null;
        this.linea = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Token)) {
            return false;
        }

        if(this.tipo == ((Token)o).tipo){
            return true;
        }

        return false;
    }
    public boolean esOperando(){
        switch (this.tipo){
            case IDENTIFICADOR:
            case NUMERO:
                return true;
            default:
                return false;
        }
    }

    public boolean esOperador(){
        switch (this.tipo){
            case MAS:
            case MENOS:
            case POR:
            case DIAG:
            case IGUAL1:
            case MAYOR:
            case MAYORI:
            case MENOR:
            case MENORI:
                return true;
            default:
                return false;
        }
    }

    public boolean esPalabraReservada(){
        switch (this.tipo){
            case VAR:
            case IF:
            case PRINT:
            case ELSE:
            case FOR:
            case WHILE:
                return true;
            default:
                return false;
        }
    }

    public boolean esEstructuraDeControl(){
        switch (this.tipo){
            case IF:
            case ELSE:
                return true;
            default:
                return false;
        }
    }

    public boolean precedenciaMayorIgual(Token t){
        return this.obtenerPrecedencia() >= t.obtenerPrecedencia();
    }

    private int obtenerPrecedencia(){
        switch (this.tipo){
            case POR:
            case DIAG:
                return 3;
            case MAS:
            case MENOS:
                return 2;
            case IGUAL1:
                return 1;
            case MAYOR:
            case MAYORI:
                return 1;
        }

        return 0;
    }

    public int aridad(){
        switch (this.tipo) {
            case POR:
            case DIAG:
            case MAS:
            case MENOS:
            case IGUAL1:
            case MAYOR:
            case MAYORI:
                return 2;
        }
        return 0;
    }



    public String toString(){
        return tipo + " " + lexema + " " + literal;
    } 
}
