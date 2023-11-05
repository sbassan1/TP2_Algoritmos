package aed;

import java.util.*;

public class ListaEnlazada<T> implements Secuencia<T> {
    private int size;
    private Nodo primero;
    private Nodo ultimo;

    private class Nodo {
        T valor;
        Nodo anterior;
        Nodo siguiente;

        Nodo(T v) {
            valor = v;
            anterior = null;
            siguiente = null;
        }
    }

    public ListaEnlazada() {
        size = 0;
        primero = null;
        ultimo = null;
    }

    public int longitud() {
        return size;
    }

    public void agregarAdelante(T elem) {
        Nodo nuevo = new Nodo(elem);
        if (primero == null) {
            primero = nuevo;
            ultimo = nuevo;
        } else {
            nuevo.siguiente = primero;
            nuevo.siguiente.anterior = nuevo;
            primero = nuevo;
        }
        size++;
    }

    public void agregarAtras(T elem) {
        Nodo nuevo = new Nodo(elem);
        if (ultimo == null) {
            ultimo = nuevo;
            primero = nuevo;
        } else {
            nuevo.anterior = ultimo;
            nuevo.anterior.siguiente = nuevo;
            ultimo = nuevo;
        }
        size++;
    }

    public T obtener(int i) {
        
        if (i == 0) {
            return primero.valor;
        } else {
            Nodo actual = primero;
            //el ciclo sale con actual en la posicion j+1 = i
            for (int j=0; j < i; j++) {
                actual = actual.siguiente;
            }
            return actual.valor;
        }
    }

    //puedo asumir que la lista no es vacia?
    public void eliminar(int i) {
        if (i == 0 && size == 1) {
            //borro el único elemento
            primero = null;
            ultimo = null;
        } else if (i == 0 && size > 1) {        
            //borro el primero, que tiene un siguiente
            primero = primero.siguiente;
            primero.anterior = null;
        } else if (i == size-1 && size > 1) {
            //borro el último, que tiene un anterior
            ultimo = ultimo.anterior;
            ultimo.siguiente = null;
        } else {
            //borro un nodo que tiene anterior y siguiente
            Nodo actual = primero;
            //el ciclo sale con actual en la posicion j+1 = i
            for (int j=0; j < i; j++) {
                actual = actual.siguiente;
            }
            //saco las referencias al actual
            actual.anterior.siguiente = actual.siguiente;
            actual.siguiente.anterior = actual.anterior;
        }
        size--;
    }

    public void modificarPosicion(int indice, T elem) {
        Nodo actual = primero;
        //el ciclo sale con actual en el nodo que quiero modificar
        for (int j=0; j < indice; j++) {
            actual = actual.siguiente;
        }
        actual.valor = elem;
    }

    public ListaEnlazada<T> copiar() {
        ListaEnlazada<T> nuevaLista = new ListaEnlazada<T>();
        for (int i=0; i < this.size; i++) {
            T valor = this.obtener(i);
            nuevaLista.agregarAtras(valor);
        }
        return nuevaLista;
    }

    public ListaEnlazada(ListaEnlazada<T> lista) {
        ListaEnlazada<T> construida = lista.copiar();
        this.size = construida.longitud();
        this.primero = construida.primero;
        this.ultimo = construida.ultimo;
    }
    
    @Override
    public String toString() {
        String cadena = "[";
        for (int i=0; i < this.size; i++) {
            T valor = this.obtener(i);
            cadena = cadena + valor + ", ";
        }
        cadena = cadena.substring(0,cadena.length()-2) + "]";
        return cadena;
    }

    // pensar el tema de los indices
    private class ListaIterador implements Iterador<T> {
    	// Completar atributos privados
        int indice;
        ListaIterador() {
            indice = 0;
        }

        public boolean haySiguiente() {
            return 0 <= indice && indice < size;
        }
        
        public boolean hayAnterior() {
	        return 1 <= indice && indice <= size;
        }

        public T siguiente() {
	        T valor = obtener(indice);
            indice++;
            return valor;
        }
        

        public T anterior() {
	    indice--;
	    T valor = obtener(indice);
            return valor;
        }
    }

    public Iterador<T> iterador() {
	    ListaIterador iterador = new ListaIterador();
        return iterador;
    }

}
