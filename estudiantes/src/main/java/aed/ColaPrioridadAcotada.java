package aed;
public class ColaPrioridadAcotada<T extends Comparable<T>> {

// ATRIBUTOS PRIVADOS /////////////////////////////////////////////////////////////////////////////////////////
    
    T[] elems;
    int indice;


// INVARIANTE DE REPRESENTACION ///////////////////////////////////////////////////////////////////////////////

    //Escrito en lenguaje formal.
    /*Inv. Rep/ (c: ColaPrioridadAcotada<T>) {
        c.elems.length > 0 && 0 <= c.indice <= c.elems.length && 
        forall i:int :: 0 <= i < c.indice ==>L c.elems[i] != null &&
        forall i,j:int :: 0 <= i,j < c.indice && (j = i*2 + 1 || j = i*2 + 2) ==>L c.elems[i] >= c.elems[j]
    }

    ### Comentario:

    Todo estado de una instancia de la clase cumple con las siguientes condiciones:

    - El tamaño de elems es mayor a 0 e índice es un entero posistivo menor o igual a la longitud de elems.
    - Sea i un entero en el intervalo [0, indice), elems[i] es no nulo.
    - Sean i y j enteros tales que: i y j están en el intervalo [0, indice) y que elems[i] representa al padre  
    de elems[j], elems[i] es mayor o igual que elems[j].
    
    */

// PROCEDIMIENTOS DEL TIPO DE DATOS //////////////////////////////////////////////////////////////////////////

    ColaPrioridadAcotada(int capacidad) {
        //complejidad: O(capacidad)
        elems = (T[]) new Comparable[capacidad]; // Java crea e inicializa el array en O(n)
        indice = 0;
    }

    //Construye un max-heap a partir de un array usando el algoritmo de Floyd.
    ColaPrioridadAcotada(int capacidad, T[] arreglo) {
        //requiere: capacidad >= arreglo.length && ninguna posicion del arreglo == null
        /*complejidad: O(capacidad)
        * El tiempo de ejecucion aproximado es T(capacidad,arreglo.length) = capacidad + 2*arreglo.length pero,
        * como capacidad >= arreglo.length (por requiere), T() < 3*capacidad ==> T = O(capacidad).
        */
        
        elems = (T[]) new Comparable[capacidad]; //O(capacidad)
        indice = 0;

        for (int i = 0; i < arreglo.length; i++) { //O(arreglo.length)
            elems[i] = arreglo[i];
            indice++;
        }

        // actual = ultimo nodo con hijos
        int actual = (indice-2)/2;

        /*Algoritmo de Floyd, con complejidad O(n).
         * Como las posiciones de elems en el intervalo [0, indice) representan los nodos del arbol,
         * y como el for(L47) asegura que indice == arreglo.length, se sigue que la complejidad 
         * del while(L59) es O(arreglo.length)
         * */

        while(actual>=0){
            bajar(actual);
            actual--;
        }
    }

    public boolean vacia() {
        //complejidad = O(1)
        return indice == 0;
    }

    public boolean llena() {
        //complejidad = O(1)
        return indice == elems.length;
    }

    public void encolar(T elem) { // throws Exception {
        /* complejidad: O(log(indice))
         * Sea n la cantidad de nodos de un arbol y k = floor(log2(n)) + 1 la altura de un arbol,
         * en el peor caso en que elem > elems[0], el while(L90) se ejecutara k-1 veces = floor(log2(n)). 
         */
        /* if (llena()) {
            throw new Exception("Cola de Prioridad Llena.");
        } else { */
            elems[indice] = elem;
            int actual = indice;
            indice++;
            while(actual !=0 && elems[actual].compareTo(elems[(actual - 1)/2]) > 0) {
                swap((actual - 1)/2, actual);
                actual = (actual - 1)/2;
            }
        // }
        
    }

    public T desencolar() { //throws Exception {
        /* complejidad: O(log(indice))
         * La operacion mas costosa es L107, con complejidad O(log(indice))
         */
        //if (vacia()) {
            //throw new Exception("Cola de Prioridad Vacia.");
        //} else {
            T elemento = elems[0];
            elems[0] = elems[indice-1];
            bajar(0);
            indice--;
            return elemento;
       // }
    }

    //Devuelve el máximo, sin desencolarlo. Como devuelve una referencia a la representacion interna de la 
    //clase, este metodo no preserva el encapsulamiento.
    //Una idea para solucionar el problema seria pedir que los objetos de tipo T tengan un metodo .clone() y 
    //hacer una copia antes de devolverlos.
    public T maximo() {
        //complejidad = O(1)
        return elems[0];
    }

    //Modifica el máximo y mantiene el invariante de representacion.
    public void modificarMaximo(T elem) {
        // complejidad: O(log(indice))
        elems[0] = elem;
        bajar(0);
    }

// METODOS AUXILIARES ///////////////////////////////////////////////////////////////////////////////////

    private void swap(int nodo1, int nodo2) {
        //requiere: nodo 1 y nodo 2 <= indice
        //complejidad: O(1)
        T temp = elems[nodo1];
        elems[nodo1] = elems[nodo2];
        elems[nodo2] = temp;
    }

    private void bajar(int nodo) {
        //requiere: 0 <= nodo < indice
        /*complejidad: O(log(indice))
         * Sea n la cantidad de nodos de un arbol y k = floor(log2(n)) + 1 la altura de un arbol,
         * en el peor caso en que nodo = 0, el while(L134) se ejecutara k-1 veces = floor(log2(n)).
         */
        while(existeHijoMayor(nodo)) {
                
                //caso 1: nodo tiene unicamente un hijo izquierdo.
                if (indice-1 == nodo*2 + 1) {
                    swap(nodo, nodo*2 + 1);
                    nodo = nodo*2 + 1;

                //caso 2: nodo tiene dos hijos.    
                } else if (indice-1 >= nodo*2 + 2) {
                    int hijoMayor = hijoMayor(nodo);
                    swap(nodo, hijoMayor);
                    nodo = hijoMayor;
                }
                
            }
    }

    private int hijoMayor(int actual) {
        //complejidad: O(1)
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
        //complejidad: O(1)
        return  (indice-1 == actual*2 + 1 && elems[actual].compareTo(elems[actual*2 + 1]) < 0) ||
                (indice-1 >= actual*2 + 2 && (elems[actual].compareTo(elems[actual*2 + 1]) < 0 || elems[actual].compareTo(elems[actual*2 + 2]) < 0));
    }
}
