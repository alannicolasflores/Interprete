/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrador;


public enum TipoToken {

    
    // Crear un tipoToken por palabra reservada
    // Crear un tipoToken: identificador, una cadena y numero
    // Crear un tipoToken por cada "Signo del lenguaje" (ver clase Scanner)


    // Palabras clave:
    AND, CLASS, ALSO,FALSE,TO,FUN,IF,NULL,OR,PRINT,RETURN,SUPER,THIS,TRUE,VAR,WHILE,
    Y,
    
    //Símbolos
    PAR1, PAR2, LLAVE1, LLAVE2, COMA, PUNTO, PCOMA, MENOS, MAS, POR, DIAG, NEG, COMP, IGUAL1, IGUAL2, MENOR, MENORI, MAYOR, MAYORI,
    
    //Numeros
    NUMERO,CADENA,IDENTIFICADOR,

    // Final de cadena
    EOF
}
