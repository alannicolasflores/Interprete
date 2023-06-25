package Administrador;
import java.util.ArrayList;
import java.util.List;
public class Arbol {
    private final Nodo raiz;
    private final TablaSimbolos tablaSimbolos;

    public Arbol(Nodo raiz, TablaSimbolos tablaSimbolos) {
        this.raiz = raiz;
        this.tablaSimbolos = tablaSimbolos;
    }

    public void recorrer() {
        for (Nodo n : raiz.getHijos()) {
            Token t = n.getValue();
            switch (t.tipo) {
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
                    String nombreVariable = n.getHijos().get(0).getValue().lexema;
                    Object valorVariable = null;  // Obtén el valor de la variable según tu lógica

                    if (tablaSimbolos.existeIdentificador(nombreVariable)) {
                        System.out.println("Error: La variable '" + nombreVariable + "' ya existe.");
                    } else {
                        tablaSimbolos.asignar(nombreVariable, valorVariable);
                    }
                    break;

                case IDENTIFICADOR:
                    String nombre = t.lexema;
                    if (tablaSimbolos.existeIdentificador(nombre)) {
                        Object valor = tablaSimbolos.obtener(nombre);
                        System.out.println("El valor de la variable '" + nombre + "' es: " + valor);
                    } else {
                        System.out.println("Error: La variable '" + nombre + "' no existe.");
                    }
                    break;

                case IF:
                    // Implementa la lógica para el nodo IF
                    break;

                case WHILE:
                    // Implementa la lógica para el nodo WHILE
                    break;

                case FOR:
                    // Implementa la lógica para el nodo FOR
                    break;

                default:
                    System.out.println("Token no reconocido: " + t.lexema);
                    break;
            }
        }
    }
}
