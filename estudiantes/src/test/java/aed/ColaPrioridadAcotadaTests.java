package aed;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ColaPrioridadAcotadaTests {

    @Test
    void nueva_cola() {
        ColaPrioridadAcotada<Integer> cola = new ColaPrioridadAcotada<Integer>(5);

        assertEquals(true, cola.vacia());
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

}
