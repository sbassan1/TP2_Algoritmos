package aed;
public class SistemaCNE {

// ATRIBUTOS PRIVADOS /////////////////////////////////////////////////////////////////////////////////////////

    private String[] nombresPartidos; // ordenados a base de los idPartidos
    private int[] votosPresidenciales; // ordenados a base de los idPartidos
    private String[] nombresDistristos; // ordenados a base de los idDistritos
    private int[] diputadosPorDistrito; // ordenados a base de los idDistritos
    private int[] ultimasMesasDistritos; // ordenados a base de los idDistritos
    private int[][] votosDiputadosPorDistrito; // en la ultima posicion de cada fila guarda el total de votos de ese distrito.
    private int[][] bancasDiputadosPorDistrito; // ordenados a base de los idDistritos
    private boolean[] bancasCalculadas; // bancasCalculadas[idDistrito] == true <==> se ejecuto resultadosDiputados() despues de la ultima ejecucion de registrarMesa().
    private float votosPrimero; //votos del partido que esta primero en votaciones
    private float votosSegundo; //votos del partido que esta segundo en votaciones
    private int primero; // idPartido del partido que está en primer lugar
    private int votosTotalesPresidente; // suma total de votos presidenciales
    private ColaPrioridadAcotada[] dHondt; // almacena heaps dHondt para cada distrito

    /*

    ### Notas respecto de la implementacion del TAD SistemaCNE:
        - Asumimos como requiere adicional de nuevoSistema() que |nombresPartidos| >= 3 (siguiendo a TP1).
        - Asumimos como requiere adicional de hayBallotage() que al menos una mesa con al menos un voto ha sido registrada.
        - Asumimos como requiere adicional de resultadosDiputados() que al menos un partido supera el umbral (siguiendo a TP1).

    ### Nota respecto del Invariante de Representacion:
        -   En este archivo .java incluimos el invariante escrito mayormente en lenguaje formal, pero para 
            facilitar su legibilidad y justificar algunas elecciones subimos también un .pdf con el invariante
            comentado en lenguaje natural.
        -   Como los predicados auxiliares tienen nombres de clarativos, decidimos no incluir sus definiciones en este archivo,
            pero las incluimos en el .pdf adjunto.
        -   Para referirnos a la longitud de un array, utilizamos |array| en lugar de array.length.            
    
    Inv. Rep.(c: SistemaCNE){

        // 1.- Pedimos que las longitudes de los arrays sean consistentes entre sí.

        |s.nombreDistritos| = |s.diputadosPorDistrito| = |s.ultimasMesasDistritos| = |s.votosDiputadosPorDistrito| = |s.bancasDiputadosPorDistrito| = |s.bancasCalculadas| = |s.dHondt| > 0 &&
        |s.nombresPartidos| = |s.votosPresidenciales| >= 3 &&
        (forall i:int :: 0 <= i < |s.votosDiputadosPorDistrito|  ==>L |s.votosDiputadosPorDistrito[i]| ==  |s.nombresPartidos| + 1) &&
        (forall i:int :: 0 <= i < |s.bancasDiputadosPorDistrito|  ==>L |s.bancasDiputadosPorDistrito[i]| ==  |s.nombresPartidos| - 1) &&L

        // 2.- Chequeamos algunas propiedades heredadas de los parametros del constructor de SistemaCNE.

        sinRepetidos(s.nombresPartidos) && s.nombresPartidos[|s.nombresPartidos-1|] == “Blanco” &&
        sinRepetidos(s.nombresDistritos) && 
        s.ultimasMesasDistritos[0] > 0 && estrictamenteCreciente(s.ultimasMesasDistritos)

        // 3.- Chequeamos la consistencia entre variables que representan la votación presidencial:


        (forall i: int :: 0 <= i < |s.votosPresidenciales| ==>L s.votosPresidenciales[i] >= 0) &&
        (s.votosTotalesPresidente == sumatoria(s.votosPresidenciales, 0, -1) &&
        esMaximo(s.primero, s.votosPresidenciales) && s.votosPrimero == s.votosPresidenciales[primero] &&
        (exists i:int :: 0 <= i < |s.votosPresidenciales| &&L esSegundo(i, s.votosPresidenciales) && s.votosPresidenciales[i] = votosSegundo) &&

        // 4.- Chequeamos la consistencia entre variables que representan la votación de diputados:

        (forall i,j:int :: 0 <= i < |s.votosDiputadosPorDistrito| && 0 <= j < |s.votosDiputadosPorDistrito[i]|  ==>L s.votosDiputadosPorDistrito[i][j] >= 0) ) && 
        (forall i,j:int :: 0 <= i < |s.bancasDiputadosPorDistrito| && 0 <= j < |s.bancasDiputadosPorDistrito[i]|  ==>L s.bancasDiputadosPorDistrito[i][j] >= 0) ) &&
        (forall i:int :: 0 <= i < |s.diputadosPorDistrito| ==>L s.diputadosPorDistrito[i] >= 0) ) &&

        (forall i:int :: 0 <= i < |s.votosDiputadosPorDistrito| ==>L s.votosDiputadosPorDistrito[i][ultimaPosicion] = sumatoria(s.VotosDiputadosPorDistrito[i], 0,-2)) &&

        forall d:int :: 0 <= d < |s.nombresDistritos| ==>L (

            ( (s.bancasCalculadas[d] = true && s.dHondt(d) != null &&L dHondtEnEstadoFinal(s.dHondt[d], d)  &&
            asignacionCorrectaDeBancas(s, d) )    || 
            
            ( (s.bancasCalculadas[d] = false &&  s.dHondt(d) != null &&L dHondtEnEstadoInicial(s.dHondt[d], d) && 
            (sumatoria(s.bancasDiputadosPorDistrito[d], 0,-1) = 0 || sumatoria(s.bancasDiputadosPorDistrito[d], 0,-1) = s.diputadosPorDistrito[d]) ) ||
            
            (   s.bancasCalculadas[d] = false && s.dHondt(d) == null && ( forall i:int :: 0 <= j < |s.votosDiputadosPorDistrito[d]| 
             ==>L (s.votosDiputadosPorDistrito[d][i] = 0 && s.bancasDiputadosPorDistrito[d][i] = 0))  ) 
                
        }
 */

// CLASES /////////////////////////////////////////////////////////////////////////////////////////

    public class VotosPartido{
        private int presidente;
        private int diputados;
        VotosPartido(int presidente, int diputados){
            this.presidente = presidente; 
            this.diputados = diputados;
        }
        public int votosPresidente(){return presidente;}
        public int votosDiputados(){return diputados;}
    }

    private class Nodo implements Comparable<Nodo>{ // Esta clase la creamos nosotros, se utiliza para la D'hondt
        private int idPartido;
        private int coeficiente;
        
        /*
        Inv. Rep/(n: Nodo){
        0 <= n.idPartido < SistemaCNE.nombresPartidos.lenght &&
        (forall i : int :: 0 <= i < SistemaCNE.cantDeVotosDiputados.lenght ==>L
         n.coeficiente == SistemaCNE.cantDeVotosDiputados[i][n.idPartido] || SistemaCNE.bancasCalculadas[i] == true)
        */ 
        
        Nodo(int idDistrito, int idPartido){
            //Complejidad: O(1) Debido a que inicializa dos variables de operaciones O(1)
            this.idPartido = idPartido; //O(1)
            coeficiente = (superaElUmbral(idDistrito, idPartido)) ? votosDiputadosPorDistrito[idDistrito][idPartido] : 0 ; //O(1) 
        }
        
        public int compareTo(Nodo n) {
            //Complejidad: O(1) Debido a que las guardas y las operaciones toman O(1)
            if (coeficiente > n.coeficiente) {
              return 1;
            } else if (coeficiente < n.coeficiente) {
              return -1;
            } else {
              return 0;
            }
        }
    }

// FUNCIONES DEL ENUNCIADO /////////////////////////////////////////////////////////////////////////////////////////

    public SistemaCNE(String[] nombresDistritos, int[] diputadosPorDistrito, String[] nombresPartidos, int[] ultimasMesasDistritos) {
        // Complejidad: O(P*D) Debido a que todas las complejidades se suman y queda la complejidad maxima
        this.nombresDistristos = nombresDistritos.clone();  // O(D). Usamos .clone() para simplificar el codigo, asumimos que la complejidad no puede ser peor a copiar el array con un ciclo = O(n). 
        this.diputadosPorDistrito = diputadosPorDistrito.clone(); // O(D)
        this.nombresPartidos = nombresPartidos.clone(); // O(P)
        this.ultimasMesasDistritos = ultimasMesasDistritos.clone(); // O(D)
        votosPresidenciales = new int[nombresPartidos.length]; // O(P) Debido a que Java crea e inicializa el array en O(n)
        votosDiputadosPorDistrito = new int[nombresDistristos.length][nombresPartidos.length + 1]; // O(P * D) Debido a que java inicializa una Matriz (Lista de listas) en O(n * m)
        bancasDiputadosPorDistrito = new int[nombresDistritos.length][nombresPartidos.length - 1]; // O(P * D)
        bancasCalculadas = new boolean[nombresDistritos.length];  // O(D) 
        votosPrimero = 0; // O(1)
        votosSegundo = 0; // O(1)
        primero = 0; // O(1)
        dHondt = new ColaPrioridadAcotada[nombresDistritos.length]; // O(D) Debido a que no se crean las colas sino solo el array, inicializado en null
    }

    public String nombrePartido(int idPartido) {
        // Complejidad: O(1), Debido a que indexa en un array
        return nombresPartidos[idPartido];
    }

    public String nombreDistrito(int idDistrito) {
        // Complejidad: O(1)
        return nombresDistristos[idDistrito];
    }

    public int diputadosEnDisputa(int idDistrito) {
        // Complejidad: O(1)
        return diputadosPorDistrito[idDistrito];
    }

    public String distritoDeMesa(int idMesa) {
        // Complejidad: O(Log(D)), Debido a que la busqueda binaria para hallar el distrito tiene complejidad O(log(n)) y luego indexa en un array -> O(1). Por ende el total es O(log(D))
        return nombreDistrito(BusquedaBinDistrito(idMesa));
    }

    public void registrarMesa(int idMesa, VotosPartido[] actaMesa) {
        /* Complejidad: O(P + log(D)) 
        El tiempo de ejecucion aproximado es T(P,D) = log(D) + 4*c*P y esta funcion T(P,D) = O(P + log(D))
        */
        int indexDistrito = BusquedaBinDistrito(idMesa); //O(log(D))
        
        //Recorrer actaMesa: Complejidad O(P) Debido a que todas las operaciones de dentro del for son O(1) y se repite P veces.
        for (int i = 0; i < actaMesa.length; i++){ //O(1) -> Guarda
            //Actualizar votos presidenciales (para el partido y totales): O(1)
            votosPresidenciales[i] += actaMesa[i].presidente; 
            votosTotalesPresidente += actaMesa[i].presidente; 
            //Actualizar votos diputados (para el partido y totales): O(1)
            votosDiputadosPorDistrito[indexDistrito][i] += actaMesa[i].diputados; 
            votosDiputadosPorDistrito[indexDistrito][votosDiputadosPorDistrito[indexDistrito].length-1] += actaMesa[i].diputados; 

            //Actualizar variables para calcular el ballotage: O(1), Debido a que las guardas y las operaciones toman O(1)
            if (votosPresidenciales[i] >= votosPrimero && i == primero){ 
                votosPrimero = votosPresidenciales[i]; 
            } else if (votosPresidenciales[i] >= votosPrimero && i != primero){ 
                votosSegundo = votosPrimero; 
                primero = i; 
                votosPrimero = votosPresidenciales[i]; 
            } else if (votosPresidenciales[i] < votosPrimero && votosPresidenciales[i] > votosSegundo) { 
                votosSegundo = votosPresidenciales[i]; 
            }
        }   
        
        //Preparar resultadosDiputados() 
        
        //1.- Crear array con coeficientes iniciales        
        Nodo[] coeficientes = new Nodo[nombresPartidos.length-1]; // O(P)
        //Crea e inicializa los nodos. Complejidad O(P), Debido a que las operaciones dentro del for son O(1) y se repite P veces, por ende O(P) * O(1) = O(P)
        for (int i=0; i<coeficientes.length; i++) { // O(1) -> Guarda
            Nodo coeficiente = new Nodo(indexDistrito,i); // O(1)
            coeficientes[i] = coeficiente; // O(1)
        }

        //2.- Crea cola de prioridad con un constructor que heapifica el array anterior.
        ColaPrioridadAcotada<Nodo> dHondtDistrito = new ColaPrioridadAcotada<>(coeficientes.length, coeficientes); //O(P) porque la llamada al constructor = O(coeficientes.length) y coeficientes.length = P-1.
        dHondt[indexDistrito] = dHondtDistrito; // O(1)

        //3.- Marca la cola de prioridad como no utilizada.
        bancasCalculadas[indexDistrito] = false; // O(1)
    }

    public int votosPresidenciales(int idPartido) {
        // Complejidad: O(1)
        return votosPresidenciales[idPartido];
    }

    public int votosDiputados(int idPartido, int idDistrito) {
        // Complejidad: O(1)
        return votosDiputadosPorDistrito[idDistrito][idPartido];
    }

    public int[] resultadosDiputados(int idDistrito){
        /* Complejidad: O(Dd * log(P))
         * La operacion mas costosa del ciclo es la llamada a modificarMaximo (L.217), que tiene una complejidad
         * de O(log(#nodos)). Como el heap que representa a la matriz dHondt tiene siempre P nodos, cada llamada a 
         * modificarMaximo tiene una complejidad de O(log(P)). 
         * Dado que el ciclo se ejecuta Dd veces, la complejidad resultante es O(Dd * log(P)).
         */
        if (!bancasCalculadas[idDistrito]) { // O(1)
            int i = 0; //Son las bancas asignadas, O(1)

            while (i < diputadosEnDisputa(idDistrito)){
                Nodo ganador = (Nodo) dHondt[idDistrito].maximo(); // O(1), justificado en clase ColaDePrioridadAcotada.
                bancasDiputadosPorDistrito[idDistrito][ganador.idPartido]++; // O(1)
                ganador.coeficiente = votosDiputadosPorDistrito[idDistrito][ganador.idPartido]/(bancasDiputadosPorDistrito[idDistrito][ganador.idPartido] + 1); // O(1)
                dHondt[idDistrito].modificarMaximo(ganador); // O(log(P))
                i++; 
            }
            bancasCalculadas[idDistrito] = true; // O(1)
        }
        
        return bancasDiputadosPorDistrito[idDistrito]; // O(1)
    }

    public boolean hayBallotage(){
        // Complejidad: O(1)
        float porcPrimero = (votosPrimero/votosTotalesPresidente)*100; //O(1) Debido a que es una operacion matematica
        float porcSegundo = (votosSegundo/votosTotalesPresidente)*100; //O(1)
        boolean res = true; //O(1) Debido a que inicializo una var de tipo primitivo
        if(porcPrimero >= 45){ //O(1) Debido a que las guardas y las operaciones toman O(1)
            res = false; 
        } else if (porcPrimero >= 40 && (porcPrimero - porcSegundo) > 10){ 
            res = false; 
        }
        return res; //O(1) 
    }

// FUNCIONES AUXILIARES /////////////////////////////////////////////////////////////////////////////////////////

    private Boolean enRango(int min, int max, int elem){ 
        // Verifica si un numero esta entre un rango de numeros, Complejidad: O(1)
        return (elem >= min && elem < max);    
        
    }

    private int BusquedaBinDistrito(int idMesa){ // Busca el distrito a base del idMesa.
        /* Complejidad:  O(log(D))
        * La complejidad esta dada por el ciclo (L260) que, en el peor caso, se ejecuta log2(D)+1 veces debido
        * a que el espacio inicial de busqueda dado por |ultimasMesasDistritos| se divide por 2 hasta quedar 
        * reducido a una secuencia de un unico elemento. 
        */ 
        
        int indiceInicio = 0; // O(1)
        int indiceFinal = ultimasMesasDistritos.length-1; // O(1)
        int medio; // O(1)
        int i = 0; // O(1)
        int longitud = ultimasMesasDistritos.length; // O(1)

        while(indiceInicio<=indiceFinal && longitud != 1){ // En el peor caso, log2(D) + 1 iteraciones.
            medio = (indiceInicio+indiceFinal)/2; // O(1)
            
            int rangoMenor = ultimasMesasDistritos[medio]; // O(1)
            int rangoMayor = ultimasMesasDistritos[medio + 1]; // O(1)
            if (enRango(rangoMenor, rangoMayor, idMesa)){ // Guardas y operaciones toman O(1)
                i = medio + 1; 
                break; 
            }
            else if (idMesa < ultimasMesasDistritos[medio]){ 
                indiceFinal = medio - 1; 
            } else {
                indiceInicio = medio + 1; 
            }
        }
        return i; // O(1)
    }

    private boolean superaElUmbral(int idDistrito, int idPartido) { 
        //Devuelve true si un partido supera el umbral necesario para sumar bancas, Complejidad: O(1)
        return votosDiputadosPorDistrito[idDistrito][idPartido]*100/votosDiputadosPorDistrito[idDistrito][votosDiputadosPorDistrito[idDistrito].length-1] >= 3;
    }
}

