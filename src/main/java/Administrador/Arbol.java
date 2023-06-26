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
                    if (n.getHijos() != null && n.getHijos().size() >= 2) {
                        String nombreVariable = n.getHijos().get(0).getValue().lexema;
                        Nodo valorNodo = n.getHijos().get(1);
                        Object valorVariable = new SolverAritmetico(valorNodo, tablaSimbolos).resolver();
                        if (tablaSimbolos.existeIdentificador(nombreVariable)) {
                            tablaSimbolos.asignar(nombreVariable, valorVariable);
                        } else {
                            tablaSimbolos.asignar(nombreVariable, valorVariable);
                        }
                    } else {
                        System.out.println("Error: La declaración de la variable está mal formada.");
                    }
                    break;
                case IDENTIFICADOR:
                    String nombre = t.lexema;
                    if (tablaSimbolos.existeIdentificador(nombre)) {
                        Object valor = tablaSimbolos.obtener(nombre);
                        if (valor instanceof String) {
                            if (n.getHijos() != null && !n.getHijos().isEmpty()) {
                                Nodo valorNodo = n.getHijos().get(0);
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
                    if (n.getHijos() != null && !n.getHijos().isEmpty()) {
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
                                solver = new SolverAritmetico(hijo, tablaSimbolos);
                                Object valor = solver.resolver();
                                System.out.println(valor);
                            }
                        }
                    } else {
                        System.out.println("Error: El comando PRINT no tiene ninguna expresión asociada.");
                    }
                    break;
                case IF:
                    if (n.getHijos() != null && n.getHijos().size() == 3) {
                        Nodo condicionNodo = n.getHijos().get(0);
                        Nodo bloqueVerdaderoNodo = n.getHijos().get(1);
                        Nodo bloqueFalsoNodo = n.getHijos().get(2);

                        SolverAritmetico condicionSolver = new SolverAritmetico(condicionNodo, tablaSimbolos);
                        Object condicion = condicionSolver.resolver();

                        if (condicion instanceof Boolean) {
                            boolean condicionBool = (Boolean) condicion;

                            if (condicionBool) {
                                // Ejecutar el bloque verdadero
                                Arbol bloqueVerdaderoArbol = new Arbol(bloqueVerdaderoNodo, tablaSimbolos);
                                bloqueVerdaderoArbol.recorrer();
                            } else {
                                // Ejecutar el bloque falso
                                Arbol bloqueFalsoArbol = new Arbol(bloqueFalsoNodo, tablaSimbolos);
                                bloqueFalsoArbol.recorrer();
                            }
                        } else {
                            System.out.println("Error: La condición del IF debe ser una expresión booleana.");
                        }
                    } else {
                        System.out.println("Error: Estructura incorrecta para el nodo IF.");
                    }
                    break;

                case ELSE:
                    if (n.getHijos() != null && n.getHijos().size() == 1) {
                        Nodo bloqueElseNodo = n.getHijos().get(0);

                        // Ejecutar el bloque del nodo ELSE
                        Arbol bloqueElseArbol = new Arbol(bloqueElseNodo, tablaSimbolos);
                        bloqueElseArbol.recorrer();
                    } else {
                        System.out.println("Error: Estructura incorrecta para el nodo ELSE.");
                    }
                    break;
                case WHILE:
                    if (n.getHijos() != null && n.getHijos().size() == 2) {
                        Nodo condicionNodo = n.getHijos().get(0);
                        Nodo bloqueWhileNodo = n.getHijos().get(1);

                        // Evaluar la condición del WHILE
                        SolverAritmetico solverCondicion = new SolverAritmetico(condicionNodo, tablaSimbolos);
                        boolean condicion = (boolean) solverCondicion.resolver();

                        // Ejecutar el bloque del WHILE mientras la condición sea verdadera
                        while (condicion) {
                            Arbol bloqueWhileArbol = new Arbol(bloqueWhileNodo, tablaSimbolos);
                            bloqueWhileArbol.recorrer();

                            // Volver a evaluar la condición del WHILE
                            condicion = (boolean) solverCondicion.resolver();
                        }
                    } else {
                        System.out.println("Error: Estructura incorrecta para el nodo WHILE.");
                    }
                    break;

                case FOR:
                    // Implementar la lógica para el nodo FOR
                    break;
                default:
                    System.out.println("Token no reconocido: " + t.lexema);
                    break;
            }
        }
    }
}
