package Administrador;
import java.util.ArrayList;
import java.util.List;

public class Arbol {
    private final Nodo raiz;

    public Arbol(Nodo raiz){
        this.raiz = raiz;
    }

    public void recorrer(){
        for(Nodo n : raiz.getHijos()){
            Token t = n.getValue();
            switch (t.tipo){
                // Operadores aritméticos
                case MAS:
                case MENOS:
                case POR:
                case DIAG:
                case OR:
                case AND:
                case MAYOR:
                case MAYORI:
                case MENORI:
                case MENOR:
                case IGUAL2:
                case NEG:
                case COMP:

                    SolverAritmetico solver = new SolverAritmetico(n);
                    Object res = solver.resolver();
                    System.out.println(res);

                break;

                case VAR:
                    // Crear una variable. Usar tabla de simbolos
                    break;
                case IF:
                    break;
                case WHILE:
                    break;
                case FOR:
                    break;

            }
        }
    }

}

