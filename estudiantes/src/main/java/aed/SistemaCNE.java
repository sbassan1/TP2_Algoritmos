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
    private boolean[] bancasCalculadas; // da true si todas las bancas estan calculadas por Distrito, ordenados a base de los idDistritos
    private float votosPrimero; //votos del partido que esta primero en votaciones
    private float votosSegundo; //votos del partido que esta segundo en votaciones
    private int primero; // idPartido del partido que está en primer lugar
    private int votosTotalesPresidente; // suma total de votos presidenciales
    private ColaPrioridadAcotada[] dHondt; // arbol heap para hacer matriz de d'hont [][]

    /*
    Inv. Rep/(c: SistemaCNE){
        c.nombresPartidos.length > 0 &&
        c.nombresDistristos.length > 0 &&

        c.votosPresidenciales.length == c.nombresPartidos.length &&
        (forall i : int :: 0 <= i < c.votosPresidenciales.length ==>L c.votosPresidenciales[i] >= 0) &&
        
        c.diputadosPorDistrito.length == c.nombresDistristos.length &&
        (forall i : int :: 0 <= i < c.diputadosPorDistrito.length ==>L c.diputadosPorDistrito[i] >= 0) &&
        
        c.ultimasMesasDistritos.length == c.nombresDistristos.length &&
        (forall i : int :: 0 <= i < c.ultimasMesasDistritos.length - 1 ==>L c.ultimasMesasDistritos[i] > 0 && c.ultimasMesasDistritos[i] < c.ultimasMesasDistritos[i+1]) &&
        
        c.votosDiputadosPorDistrito.length == c.nombresDistristos.length &&
        (forall i:int :: 0 <= i < c.votosDiputadosPorDistrito.length  ==>L c.votosDiputadosPorDistrito[i].lenght ==  c.nombresPartidos.length + 1 &&
        votosDiputadosPorDistrito[i] > 0) &&

        c.bancasDiputadosPorDistrito.length == c.nombresDistristos.length &&
        (forall i:int :: 0 <= i < c.bancasDiputadosPorDistrito.length  ==>L c.bancasDiputadosPorDistrito[i].lenght ==  c.nombresPartidos.length - 1 && 
        c.bancasDiputadosPorDistrito[i] > 0) &&
        
        c.bancasCalculadas == c.nombresDistristos.length && c.votosTotalesPresidente >= 0 &&
        (c.votosTotalesPresidente == sum i : int :: 0 <= i < c.votosPresidenciales.lenght :: c.votosPresidenciales[i])) &&

        0 <= c.votosPrimero <= c.votosTotalesPresidente && 0 <= c.votosSegundo <= c.votosPrimero &&
        0 <= c.primero < c.nombresPartidos.length && 
        
        c.dHondt.length == c.nombresDistristos.length &&
        forall i : int :: 0 <= i < c.dHondt.lenght && c.dHondt[i] != null ==>L c.dHondt.indice == c.nombresDePartidos.length &&
        forall n : Nodo :: esNodoDe(n, c.dHondt[i]) ==>L (n.coeficiente == c.cantDeVotosDiputados[i][n.idPartido] || c.bancasCalculadas[i] == true )
    }

    pred esNodoDe(n: Nodo, dHondt: DHondt){
            // n es nodo de dHondt
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

    public SistemaCNE(String[] nombresDistritos, int[] diputadosPorDistrito, String[] nombresPartidos, int[] ultimasMesasDistritos) { // funcion contructora
        // Complejidad: O(n) Debido a que todas las complejidades se suman y queda la complejidad maxima
        this.nombresDistristos = nombresDistritos.clone();  // O(D). Usamos .clone() para simplificar el codigo, asumimos que la complejidad no puede ser peor a copiar el array con un ciclo = O(n). 
        this.diputadosPorDistrito = diputadosPorDistrito.clone(); // O(D)
        this.nombresPartidos = nombresPartidos.clone(); // O(P)
        this.ultimasMesasDistritos = ultimasMesasDistritos.clone(); // O(D)
        votosPresidenciales = new int[nombresPartidos.length]; // O(P) Debido a que Java crea e inicializa el array en O(n)
        votosDiputadosPorDistrito = new int[nombresDistristos.length][nombresPartidos.length + 1]; // O(D * P) Debido a que java inicializa una Matriz (Lista de listas) en O(n * m)
        bancasDiputadosPorDistrito = new int[nombresDistritos.length][nombresPartidos.length - 1]; // O(D * P)
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
        
        //Recorrer actaMesa, Complejidad O(P) Debido a que todas lsa operaciones de dentro del for son O(1) y se repite P veces, por ende O(P) * O(1) = O(P)
        for (int i = 0; i < actaMesa.length; i++){ //O(1) -> Guarda
            //Actualizar votos presidenciales (para el partido y totales)
            votosPresidenciales[i] += actaMesa[i].presidente; 
            votosTotalesPresidente += actaMesa[i].presidente; 
            //Actualizar votos diputados (para el partido y totales)
            votosDiputadosPorDistrito[indexDistrito][i] += actaMesa[i].diputados; 
            votosDiputadosPorDistrito[indexDistrito][votosDiputadosPorDistrito[indexDistrito].length-1] += actaMesa[i].diputados; 

            //Actualizar variables para calcular el ballotage en O(1), Debido a que las guardas y las operaciones toman O(1)
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
        
        //Prepara resultadosDiputados() 
        
        //1.- Crea array con coeficientes iniciales        
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
        if (!bancasCalculadas[idDistrito]) { // O(1)
            int i = 0; //Son las bancas asignadas, O(1)

            while (i < diputadosEnDisputa(idDistrito)){
                Nodo ganador = (Nodo) dHondt[idDistrito].maximo(); // O(1) Debido a que el MaxHeap saca Max en O(1)
                bancasDiputadosPorDistrito[idDistrito][ganador.idPartido]++; // O(1)
                ganador.coeficiente = votosDiputadosPorDistrito[idDistrito][ganador.idPartido]/(bancasDiputadosPorDistrito[idDistrito][ganador.idPartido] + 1); // O(1)
                dHondt[idDistrito].modificarMaximo(ganador); // O(log(n)) Debido a la complejidad demostrada en la clase ColaDePrioridadAcotada
                i++; 
            }
            bancasCalculadas[idDistrito] = true; // O(1)
        }
        
        return bancasDiputadosPorDistrito[idDistrito]; // O(1)
    }

    public boolean hayBallotage(){
        // Complejidad: O(1),
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

    private Boolean enRango(int min, int max, int elem){ // funcion generica para verificar si un numero esta entre un rango de numeros, Coplejidad: O(1)
        return (elem >= min && elem < max);    
        
    }

    private int BusquedaBinDistrito(int idMesa){ // Busca el distrito a base del idMesa, Complejidad O(log(D)) Debido a que la busqueda binaria para hallar el distrito tiene complejidad O(log(n))
        int indiceInicio = 0; // O(1)
        int indiceFinal = ultimasMesasDistritos.length-1; // O(1) Debido a que java implementa .length en O(1)
        int medio; // O(1)
        int i = 0; // O(1)
        int longitud = ultimasMesasDistritos.length; // O(1)

        while(indiceInicio<=indiceFinal && longitud != 1){ // O(log(n)) Debido a que el while se va a ejecutar log(n) veces como peor caso y dentro solo hace operaciones O(1)
            medio = (indiceInicio+indiceFinal)/2; // O(1)
            
            int rangoMenor = ultimasMesasDistritos[medio]; // O(1)
            int rangoMayor = ultimasMesasDistritos[medio + 1]; // O(1)
            if (enRango(rangoMenor, rangoMayor, idMesa)){ // O(1) Debido a que las guardas y las operaciones toman O(1)
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

    private boolean superaElUmbral(int idDistrito, int idPartido) { //Devuelve si un partido supera el umbral necesario para sumar bancas, Complejidad: O(1)
        return votosDiputadosPorDistrito[idDistrito][idPartido]*100/votosDiputadosPorDistrito[idDistrito][votosDiputadosPorDistrito[idDistrito].length-1] >= 3;
    }
}

