package aed;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ColaPrioridadAcotadaTests {

    @Test
    void constructor_cola_vacia() {
        ColaPrioridadAcotada<Integer> cola = new ColaPrioridadAcotada<Integer>(5);

        assertEquals(true, cola.vacia());
    }

    @Test
    void constructor_desde_arreglo() {
        Integer[] arreglo = {1,50,2,-5,8};
        ColaPrioridadAcotada<Integer> cola = new ColaPrioridadAcotada<Integer>(5, arreglo);

        assertEquals(true, cola.llena());
    }


    @Test
    void encolar_y_desencolar_uno() throws Exception{
        ColaPrioridadAcotada<Integer> cola = new ColaPrioridadAcotada<Integer>(5);

        cola.encolar(50);

        assertEquals(false, cola.vacia());
        assertEquals(50, cola.desencolar());
    }

    @Test
    void desencolar_con_unico_hijo() throws Exception{
        ColaPrioridadAcotada<Integer> cola = new ColaPrioridadAcotada<Integer>(4);

        cola.encolar(50);
        cola.encolar(35);
        cola.encolar(20);
        cola.encolar(10);

        assertEquals(false, cola.vacia());
        assertEquals(true, cola.llena());
        assertEquals(50, cola.desencolar());
        assertEquals(35, cola.desencolar());
        assertEquals(20, cola.desencolar());
        assertEquals(10, cola.desencolar());
        assertEquals(true, cola.vacia());
    }
    
    @Test
    void encolar_y_desencolar_muchos() throws Exception {
        ColaPrioridadAcotada<Integer> cola = new ColaPrioridadAcotada<Integer>(1000);

        for(int i=0;i<1000;i++) {
            cola.encolar(i);
        }
        
        assertEquals(true, cola.llena());
        
        for (int i=999;i>-1;i--) {
            assertEquals(i, cola.desencolar());
        }
        
        assertEquals(true, cola.vacia());
    }

    int NCLAVES = 10000;
    @Test
    void construir_por_array_y_desencolar() throws Exception {
        
        Integer[] arreglo = new Integer[NCLAVES];
        for(int i=0;i<NCLAVES;i++) {
            arreglo[i] = i;
        }

        ColaPrioridadAcotada<Integer> cola = new ColaPrioridadAcotada<Integer>(NCLAVES+1, arreglo);
        assertEquals(false, cola.llena());
        cola.encolar(NCLAVES);
        assertEquals(true, cola.llena());
        
        for (int i=NCLAVES;i>-1;i--) {
            assertEquals(i, cola.desencolar());
        }
        
        assertEquals(true, cola.vacia());
    }

}
