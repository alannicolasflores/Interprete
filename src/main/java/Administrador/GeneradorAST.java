package Administrador;

import java.util.List;
import java.util.Stack;

public class GeneradorAST {

    private final List<Token> postfija;  // Lista de tokens en notación postfija
    private final Stack<Nodo> pila;  // Pila de nodos para construir el árbol
    private final TablaSimbolos tablaSimbolos;  // Tabla de símbolos

    public GeneradorAST(List<Token> postfija, TablaSimbolos tablaSimbolos) {
        this.postfija = postfija;
        this.pila = new Stack<>();
        this.tablaSimbolos = tablaSimbolos;
    }

    public Arbol generarAST() {
        Stack<Nodo> pilaPadres = new Stack<>();  // Pila de nodos padres
        Nodo raiz = new Nodo(null);  // Nodo raíz del árbol
        pilaPadres.push(raiz);  // Se agrega la raíz a la pila de padres

        Nodo padre = raiz;

        for (Token t : postfija) {
            if (t.tipo == TipoToken.EOF) {
                break;
            }

            if (t.esPalabraReservada()) {
                Nodo n = new Nodo(t);

                padre = pilaPadres.peek();  // Se obtiene el padre actual de la pila
                padre.insertarSiguienteHijo(n);  // Se inserta el nodo como hijo del padre

                pilaPadres.push(n);  // Se agrega el nodo a la pila de padres
                padre = n;  // El nodo creado se convierte en el nuevo padre

            } else if (t.esOperando()) {
                Nodo n = new Nodo(t);  // Se crea un nuevo nodo con el token
                pila.push(n);  // Se agrega el nodo a la pila de nodos

            } else if (t.esOperador()) {
                int aridad = t.aridad();  // Se obtiene la aridad del operador
                Nodo n = new Nodo(t);  // Se crea un nuevo nodo con el token

                // Se agregan los nodos hijos necesarios de la pila como hijos del nuevo nodo
                for (int i = 1; i <= aridad; i++) {
                    Nodo nodoAux = pila.pop();
                    n.insertarHijo(nodoAux);
                }

                pila.push(n);  // Se agrega el nuevo nodo a la pila de nodos

            } else if (t.tipo == TipoToken.PCOMA) {
                if (pila.isEmpty()) {
                    /*
                     * Si la pila está vacía, significa que el token es un punto y coma
                     * que cierra una estructura de control.
                     */
                    pilaPadres.pop();  // Se remueve el padre actual de la pila
                    if (!pilaPadres.isEmpty()) {
                        padre = pilaPadres.peek();  // Se actualiza el padre actual solo si la pila de padres no está vacía
                    }
                } else {
                    Nodo n = pila.pop();  // Se obtiene el nodo de la pila

                    if (padre.getValue() != null && padre.getValue().tipo == TipoToken.VAR) {
                        /*
                         * En el caso del VAR, es necesario eliminar el token igual que
                         * pudiera aparecer en la raíz del nodo n.
                         */
                        if (n.getValue() != null && n.getValue().tipo == TipoToken.IGUAL1) {
                            padre.insertarHijos(n.getHijos());  // Se insertan los hijos en el padre
                        } else {
                            padre.insertarSiguienteHijo(n);  // Se inserta el nodo como siguiente hijo del padre
                        }
                        pilaPadres.pop();  // Se remueve el padre actual de la pila
                        if (!pilaPadres.isEmpty()) {
                            padre = pilaPadres.peek();  // Se actualiza el padre actual solo si la pila de padres no está vacía
                        }

                    } else if (padre.getValue() != null && (padre.getValue().tipo == TipoToken.PRINT || padre.getValue().tipo == TipoToken.IF || padre.getValue().tipo == TipoToken.ELSE || padre.getValue().tipo == TipoToken.WHILE)) {
                        // En el caso de los nodos PRINT, IF, ELSE y WHILE, se inserta el nodo como siguiente hijo del padre
                        padre.insertarSiguienteHijo(n);
                    }
                }
            }
        }

        // Suponiendo que en la pila solamente queda un nodo
        // Nodo nodoAux = pila.pop();
        Arbol programa = new Arbol(raiz, tablaSimbolos);  // Se crea el árbol con la raíz y la tabla de símbolos

        return programa;  // Se retorna el árbol generado
    }
}
