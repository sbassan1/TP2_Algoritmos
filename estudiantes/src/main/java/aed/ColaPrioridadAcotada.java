package aed;
public class ColaPrioridadAcotada<T extends Comparable<T>> {
    T[] elems;
    int indice;

    //Escribo el Inv. Rep. refiriendome a tipos de implementacion y no a observadores del TAD
    //Asi lo puedo usar en asserts
    //Plantear como duda el viernes
    /* Inv. Rep/ (c': ColaPrioridadAcotada<T>) {
        elems.length > 0 && 0 <= indice <= elems.length
    } */

    ColaPrioridadAcotada(int capacidad) {
        elems = (T[]) new Comparable[capacidad];
        indice = 0;
    }

    public boolean vacia() {
        return indice == 0;
    }

    public boolean llena() {
        return indice == elems.length;
    }

    public void encolar(T e) throws Exception {
        if (llena()) {
            throw new Exception("Cola de Prioridad Llena.");
        } else {
            elems[indice] = e;
            int actual = indice;
            indice++;
            while(actual !=0 && elems[actual].compareTo(elems[(actual - 1)/2]) > 0) {
                swap((actual - 1)/2, actual);
                actual = (actual - 1)/2;
            }
        }
        
    }

    public T desencolar() throws Exception {
        if (vacia()) {
            throw new Exception("Cola de Prioridad Vacia.");
        } else {
            T elemento = elems[0];
            elems[0] = elems[indice-1];
            indice--;
            int actual = 0;
            
            while(existeHijoMayor(actual)) {
                
                //caso 1: actual tiene unicamente un hijo izquierdo.
                if (indice == actual*2 + 1 && elems[actual].compareTo(elems[actual*2 + 1]) < 0) {
                    swap(actual, actual*2 + 1);
                    actual = actual*2 + 1;

                //caso 2: actual tiene dos hijos.    
                } else if (indice >= actual*2 + 2) {
                    int hijoMayor = hijoMayor(actual);
                    swap(actual, hijoMayor);
                    actual = hijoMayor;
                }
                
            }
            
            return elemento;
        }
    }

    private void swap(int nodo1, int nodo2) {
        //requiere: nodo 1 y nodo 2 <= indice
        T temp = elems[nodo1];
        elems[nodo1] = elems[nodo2];
        elems[nodo2] = temp;
    }

    private int hijoMayor(int actual) {
        //requiere: actual tiene dos hijos.
        int hijoMayor;
        if (elems[actual*2+1].compareTo(elems[actual*2+2]) > 0) {
            hijoMayor = actual*2+1;
        } else {
            hijoMayor = actual*2+2;
        }
        return hijoMayor;
    }

    private boolean existeHijoMayor(int actual) {
        return  (indice-1 == actual*2 + 1 && elems[actual].compareTo(elems[actual*2 + 1]) < 0) ||
                (indice-1 >= actual*2 + 2 && (elems[actual].compareTo(elems[actual*2 + 1]) < 0 || elems[actual].compareTo(elems[actual*2 + 2]) < 0));
    }

    //algoritmo de Floyd

    //main para testear

}
