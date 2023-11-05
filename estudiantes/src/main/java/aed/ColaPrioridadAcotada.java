package aed;
public class ColaPrioridadAcotada<T extends Comparable<T>> {
    T[] elems;
    int indice;

    ColaPrioridadAcotada(int capacidad) {
        elems = (T[]) new Object[capacidad];
        indice = 0;
    }

    public boolean colaVacia() {
        return indice == 0;
    }

    public void encolar(T e) throws Exception {
        if (indice == elems.length) {
            throw new Exception("Cola de Prioridad Llena.");
        } else {
            elems[indice] = e;
            int actual = indice;
            while(actual !=0 && elems[actual].compareTo(elems[(int) Math.floor((actual - 2)/2)]) > 0) {
                T temp = elems[(int) Math.floor((actual - 2)/2)];
                elems[(int) Math.floor((actual - 2)/2)] = elems[actual];
                elems[actual] = temp;
                actual = (int) Math.floor((actual - 2)/2);
            }
            indice = indice++;
        }
    }

    public T desencolar() {
        //completar
    }

    //algoritmo de Floyd

    //main para testear

}
