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
                    SolverAritmetico solver = new SolverAritmetico(n, tablaSimbolos);
                    Object res = solver.resolver();
                    System.out.println(res);
                    break;
                case VAR:
                    String nombreVariable = n.getHijos().get(0).getValue().lexema;
                    Nodo valorNodo = n.getHijos().get(1);
                    Object valorVariable = resolverValor(valorNodo);

                    if (tablaSimbolos.existeIdentificador(nombreVariable)) {
                        System.out.println("Error: La variable '" + nombreVariable + "' ya existe.");
                    } else {
                        tablaSimbolos.asignar(nombreVariable, valorVariable);
                    }
                    break;

                case IDENTIFICADOR:
                    String nombre = t.lexema;
                    if (tablaSimbolos.existeIdentificador(nombre)) {
                        // Obtener el valor actual de la variable
                        Object valor = tablaSimbolos.obtener(nombre);

                        if (valor instanceof String) {

                            // Verificar si el nodo tiene hijos para reevaluar su valor
                            if (n.getHijos() != null && n.getHijos().size() > 0) {
                                valorNodo = n.getHijos().get(0);
                                solver = new SolverAritmetico(valorNodo, tablaSimbolos);
                                Object nuevoValor = solver.resolver();
                                tablaSimbolos.asignar(nombre, nuevoValor);

                            }
                        } else {
                            System.out.println("Error: El identificador '" + nombre + "' no es una cadena.");
                        }
                    } else {
                        System.out.println("Error: La variable '" + nombre + "' no existe.");
                    }
                    break;

                case PRINT:
                    for (Nodo hijo : n.getHijos()) {
                        Token hijoToken = hijo.getValue();
                        if (hijoToken.tipo == TipoToken.CADENA) {
                            System.out.println(hijoToken.literal);
                        } else if (hijoToken.tipo == TipoToken.IDENTIFICADOR) {
                            String varNombre = hijoToken.lexema;
                            if (tablaSimbolos.existeIdentificador(varNombre)) {
                                Object varValor = tablaSimbolos.obtener(varNombre);
                                System.out.println(varValor);
                            } else {
                                System.out.println("Error: La variable '" + varNombre + "' no existe.");
                            }
                        } else {
                            // Resolver el valor del nodo
                            Object valor = resolverValor(hijo);
                            System.out.println(valor);
                        }
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

    private Object resolverValor(Nodo n) {
        SolverAritmetico solver = new SolverAritmetico(n, tablaSimbolos);
        return solver.resolver();
    }
}
