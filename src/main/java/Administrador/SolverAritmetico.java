package Administrador;

import java.util.Stack;

public class SolverAritmetico {

    private final Nodo nodo;
    private final TablaSimbolos tablaSimbolos;

    public SolverAritmetico(Nodo nodo, TablaSimbolos tablaSimbolos) {
        this.nodo = nodo;
        this.tablaSimbolos = tablaSimbolos;
    }

    public Object resolver() {
        return resolver(nodo);
    }

    private Object resolver(Nodo n) {
        // No tiene hijos, es un operando
        if (n.getHijos() == null || n.getHijos().isEmpty()) {
            if (n.getValue().tipo == TipoToken.NUMERO || n.getValue().tipo == TipoToken.CADENA) {
                return n.getValue().literal;
            } else if (n.getValue().tipo == TipoToken.IDENTIFICADOR) {
                // Ver la tabla de símbolos
                String varNombre = n.getValue().lexema;
                if (tablaSimbolos.existeIdentificador(varNombre)) {
                    return tablaSimbolos.obtener(varNombre);
                } else {
                    throw new RuntimeException("Error: La variable '" + varNombre + "' no existe.");
                }
            } else if (n.getValue().tipo == TipoToken.TRUE) {
                return true;
            } else if (n.getValue().tipo == TipoToken.FALSE) {
                return false;
            }
        } else {  // Operación binaria, esperamos al menos dos hijos
            if (n.getHijos().size() < 2) {
                throw new RuntimeException("Error: se esperaban al menos dos hijos para el nodo.");
            }

            Nodo izq = n.getHijos().get(0);
            Nodo der = n.getHijos().get(1);

            Object resultadoIzquierdo = resolver(izq);
            Object resultadoDerecho = resolver(der);

            // Obtener aridad y precedencia del operador actual
            int aridad = n.getValue().aridad();
            int precedencia = n.getValue().obtenerPrecedencia();

            // Verificar la aridad y precedencia
            if (aridad == 2 && precedencia > 0) {
                // Operador binario
                if (resultadoIzquierdo instanceof Double && resultadoDerecho instanceof Double) {
                    switch (n.getValue().tipo) {
                        case MAS:
                            return ((Double) resultadoIzquierdo + (Double) resultadoDerecho);
                        case MENOS:
                            return ((Double) resultadoIzquierdo - (Double) resultadoDerecho);
                        case POR:
                            return ((Double) resultadoIzquierdo * (Double) resultadoDerecho);
                        case DIAG:
                            return ((Double) resultadoIzquierdo / (Double) resultadoDerecho);
                        case MAYOR:
                            return ((Double) resultadoIzquierdo > (Double) resultadoDerecho);
                        case MAYORI:
                            return ((Double) resultadoIzquierdo >= (Double) resultadoDerecho);
                        case MENOR:
                            return ((Double) resultadoIzquierdo < (Double) resultadoDerecho);
                        case MENORI:
                            return ((Double) resultadoIzquierdo <= (Double) resultadoDerecho);
                        case IGUAL2:
                            return resultadoIzquierdo.equals(resultadoDerecho);
                        case COMP:
                            return !resultadoIzquierdo.equals(resultadoDerecho);
                    }
                } else if (resultadoIzquierdo instanceof String && resultadoDerecho instanceof String) {
                    if (n.getValue().tipo == TipoToken.MAS) {
                        // Ejecutar la concatenación
                        return resultadoIzquierdo.toString() + resultadoDerecho.toString();
                    } else if (n.getValue().tipo == TipoToken.MAYOR || n.getValue().tipo == TipoToken.MAYORI
                            || n.getValue().tipo == TipoToken.MENOR || n.getValue().tipo == TipoToken.MENORI
                            || n.getValue().tipo == TipoToken.IGUAL2 || n.getValue().tipo == TipoToken.COMP) {
                        throw new RuntimeException("Error: Operaciones relacionales no válidas con operandos no numéricos.");
                    }
                } else if (resultadoIzquierdo instanceof Boolean && resultadoDerecho instanceof Boolean) {
                    if (n.getValue().tipo == TipoToken.AND) {
                        return ((Boolean) resultadoIzquierdo && (Boolean) resultadoDerecho);
                    } else if (n.getValue().tipo == TipoToken.OR) {
                        return ((Boolean) resultadoIzquierdo || (Boolean) resultadoDerecho);
                    } else if (n.getValue().tipo == TipoToken.MAYOR || n.getValue().tipo == TipoToken.MAYORI
                            || n.getValue().tipo == TipoToken.MENOR || n.getValue().tipo == TipoToken.MENORI
                            || n.getValue().tipo == TipoToken.IGUAL2 || n.getValue().tipo == TipoToken.COMP) {
                        throw new RuntimeException("Error: Operaciones relacionales no válidas con operandos booleanos.");
                    }
                } else {
                    // Error por diferencia de tipos
                    throw new RuntimeException("Error: Diferencia de tipos en la operación aritmética, concatenación o lógica.");
                }
            } else if (aridad == 1 && precedencia > 0) {
                // Operador unario
                if (resultadoIzquierdo instanceof Double) {
                    switch (n.getValue().tipo) {
                        case NEG:
                            return -((Double) resultadoIzquierdo);
                    }
                } else if (resultadoIzquierdo instanceof Boolean) {
                    if (n.getValue().tipo == TipoToken.COMP) {
                        return !((Boolean) resultadoIzquierdo);
                    } else {
                        throw new RuntimeException("Error: Operación unaria no válida con operandos no booleanos.");
                    }
                } else {
                    // Error por diferencia de tipos
                    throw new RuntimeException("Error: Diferencia de tipos en la operación aritmética o lógica unaria.");
                }
            } else {
                // Error en la aridad
                throw new RuntimeException("Error: Aridad inválida para el operador '" + n.getValue().lexema + "'.");
            }
        }

        return null; // Agrega esta línea para evitar el error
    }
}
