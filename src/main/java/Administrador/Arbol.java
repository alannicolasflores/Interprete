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
            if (t != null) {
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
                    case IGUAL1:
                        // Reasignación de variables
                        if (n.getHijos() != null && n.getHijos().size() >= 2) {
                            String nombreVariable = n.getHijos().get(0).getValue().lexema;
                            Nodo valorNodo = n.getHijos().get(1);
                            if (tablaSimbolos.existeIdentificador(nombreVariable)) {
                                Object nuevoValor = new SolverAritmetico(valorNodo, tablaSimbolos).resolver();
                                tablaSimbolos.asignar(nombreVariable, nuevoValor);
                            } else {
                                throw new RuntimeException("Error: La variable '" + nombreVariable + "' no existe.");
                            }
                        } else {
                            System.out.println("Error: La reasignación de la variable está mal formada.");
                        }
                        break;
                    case VAR:
                        // Verifica si el nodo tiene al menos 2 hijos
                        if (n.getHijos() != null && n.getHijos().size() >= 2) {
                            String nombreVariable = n.getHijos().get(0).getValue().lexema;
                            Nodo valorNodo = n.getHijos().get(1);

                            // Resuelve el valor de la variable utilizando el SolverAritmetico
                                Object valorVariable = new SolverAritmetico(valorNodo, tablaSimbolos).resolver();
                                tablaSimbolos.asignar(nombreVariable, valorVariable);

                        } else {
                            System.out.println("Error: La declaración de la variable está mal formada.");
                        }
                        break;
                    case PRINT:
                        if (n.getHijos() != null && !n.getHijos().isEmpty()) {
                            for (Nodo hijo : n.getHijos()) {
                                evaluarNodo(hijo);
                            }
                        } else {
                            System.out.println("Error: El comando PRINT no tiene ninguna expresión asociada.");
                        }
                        break;
                    case IF:
                        if (n.getHijos() != null && n.getHijos().size() == 2) {
                            Nodo condicionNodo = n.getHijos().get(0);
                            Nodo bloqueVerdaderoNodo = n.getHijos().get(1);

                            SolverAritmetico condicionSolver = new SolverAritmetico(condicionNodo, tablaSimbolos);
                            Object condicion = condicionSolver.resolver();

                            if (condicion instanceof Boolean) {
                                boolean condicionBool = (Boolean) condicion;

                                if (condicionBool) {
                                    // Ejecutar el bloque verdadero
                                    Arbol bloqueVerdaderoArbol = new Arbol(bloqueVerdaderoNodo, tablaSimbolos);
                                    bloqueVerdaderoArbol.recorrer();
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
                        if (n.getHijos() != null && n.getHijos().size() == 4) {
                            Nodo inicializacionNodo = n.getHijos().get(0);
                            Nodo condicionNodo = n.getHijos().get(1);
                            Nodo incrementoNodo = n.getHijos().get(2);
                            Nodo bloqueForNodo = n.getHijos().get(3);

                            // Ejecutar la inicialización del FOR
                            Arbol inicializacionArbol = new Arbol(inicializacionNodo, tablaSimbolos);
                            inicializacionArbol.recorrer();

                            // Evaluar la condición del FOR
                            SolverAritmetico solverCondicion = new SolverAritmetico(condicionNodo, tablaSimbolos);
                            boolean condicion = (boolean) solverCondicion.resolver();

                            // Ejecutar el bloque del FOR mientras la condición sea verdadera
                            while (condicion) {
                                // Ejecutar el bloque del FOR
                                Arbol bloqueForArbol = new Arbol(bloqueForNodo, tablaSimbolos);
                                bloqueForArbol.recorrer();

                                // Ejecutar el incremento del FOR
                                Arbol incrementoArbol = new Arbol(incrementoNodo, tablaSimbolos);
                                incrementoArbol.recorrer();

                                // Volver a evaluar la condición del FOR
                                condicion = (boolean) solverCondicion.resolver();
                            }
                        } else {
                            System.out.println("Error: Estructura incorrecta para el nodo FOR.");
                        }
                        break;
                    default:
                        System.out.println("Token no reconocido: " + t.lexema);
                        break;
                }
            }
        }
    }

    private void evaluarNodo(Nodo nodo) {
        Token t = nodo.getValue();
        if (t != null) {
            switch (t.tipo) {
                case CADENA:
                    System.out.println(t.literal);
                    break;
                case IDENTIFICADOR:
                    String varNombre = t.lexema;
                    if (tablaSimbolos.existeIdentificador(varNombre)) {
                        Object varValor = tablaSimbolos.obtener(varNombre);
                        System.out.println(varValor);
                    } else {
                        System.out.println("Error: La variable '" + varNombre + "' no existe.");
                    }
                    break;
                default:
                    SolverAritmetico solver = new SolverAritmetico(nodo, tablaSimbolos);
                    Object valor = solver.resolver();
                    System.out.println(valor);
                    break;
            }
        }
    }


}
