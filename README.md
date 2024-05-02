# Interprete
# ALAN NICOLAS FLORES MELO
- LinkedIn: [alan-flores-7393a0254](https://www.linkedin.com/in/alan-flores-7393a0254)
- Email: [alan.flores.m@outlook.com](mailto:alan.flores.m@outlook.com)
COMPILADORES 
## Equipo/Team
- Alan Nicolas Flores Melo
- González Meneses Yazmín Berenice
- 
# Intérprete de Compiladores

Este proyecto es un intérprete completo desarrollado para analizar y ejecutar código, aplicando los principios de los compiladores. Este intérprete incluye diversos componentes que trabajan juntos para descomponer, analizar y ejecutar el código fuente.

## Descripción del Proyecto
Este intérprete realiza un análisis completo del código fuente, desde el análisis léxico hasta la ejecución, pasando por el análisis sintáctico y semántico. Es capaz de interpretar instrucciones definidas en una gramática especificada, facilitando la comprensión y ejecución del código.

## Conocimientos Requeridos
Para trabajar en este proyecto se requieren conocimientos en:
- Programación orientada a objetos en Java.
- Estructuras de datos como árboles y tablas de símbolos.
- Principios de compilación, incluyendo análisis léxico, sintáctico y semántico.

## Componentes del Proyecto

### `Arbol.java`
Implementa la estructura de datos para el árbol de análisis sintáctico.

### `GeneradorAST.java`
Genera el Árbol de Sintaxis Abstracta (AST) a partir del análisis sintáctico.

### `GeneradorPostfija.java`
Convierte expresiones a su forma postfija para facilitar la evaluación.

### `Interprete.java`
Interpreta el código fuente, ejecutando las instrucciones del lenguaje.

### `Nodo.java`
Define los nodos utilizados en el `Arbol.java` para construir estructuras de datos complejas.

### `Parser.java`
Analiza el código fuente para construir el AST basado en la gramática definida.

### `Scanner.java`
Realiza el análisis léxico, identificando tokens en el código fuente.

### `SolverAritmetico.java`
Resuelve expresiones aritméticas utilizando el árbol postfijo.

### `TablaSimbolos.java`
Gestiona una tabla de símbolos para el seguimiento de variables y funciones.

### `TipoToken.java`
Define los tipos de tokens que el `Scanner.java` puede reconocer.

### `Token.java`
Representa los tokens individuales identificados durante el análisis léxico.

# Compiler Interpreter

This project is a complete interpreter developed to analyze and execute code, applying the principles of compilers. This interpreter includes various components that work together to decompose, analyze, and execute the source code.

## Project Description
This interpreter performs a complete analysis of the source code, from lexical analysis to execution, including syntactic and semantic analysis. It is capable of interpreting instructions defined in a specified grammar, facilitating the understanding and execution of code.

## Required Knowledge
Knowledge required for this project includes:
- Object-oriented programming in Java.
- Data structures such as trees and symbol tables.
- Compilation principles, including lexical, syntactic, and semantic analysis.

## Project Components

### `Arbol.java`
Implements the data structure for the syntax analysis tree.

### `GeneradorAST.java`
Generates the Abstract Syntax Tree (AST) from syntactic analysis.

### `GeneradorPostfija.java`
Converts expressions to their postfix form to facilitate evaluation.

### `Interprete.java`
Interprets the source code, executing the language instructions.

### `Nodo.java`
Defines the nodes used in `Arbol.java` to build complex data structures.

### `Parser.java`
Analyzes the source code to construct the AST based on the defined grammar.

### `Scanner.java`
Performs lexical analysis, identifying tokens in the source code.

### `SolverAritmetico.java`
Solves arithmetic expressions using the postfix tree.

### `TablaSimbolos.java`
Manages a symbol table for tracking variables and functions.

### `TipoToken.java`
Defines the types of tokens that `Scanner.java` can recognize.

### `Token.java`
Represents the individual tokens identified during lexical analysis.
