package aed;
public class SistemaCNE {

// ATRIBUTOS PRIVADOS /////////////////////////////////////////////////////////////////////////////////////////

    private String[] nombresPartidos; // ordenados a base de los idPartidos
    private int[] votosPresidenciales; // ordenados a base de los idPartidos
    private String[] nombresDistristos; // ordenados a base de los idDistritos
    private int[] diputadosPorDistrito; // ordenados a base de los idDistritos
    private int[] ultimasMesasDistritos; // ordenados a base de los idDistritos
    private int[][] votosDiputadosPorDistrito; //en la ultima posicion, guarda el total de votos de ese distrito.
    private int[][] bancasDiputadosPorDistrito; // ordenados a base de los idDistritos
    private boolean[] bancasCalculadas; // da true si todas las bancas estan calculadas por Distrito, ordenados a base de los idDistritos
    private float votosPrimero; //votos del partido que esta primero en votaciones
    private float votosSegundo; //votos del partido que esta segundo en votaciones
    private int primero; // idPartido del partido que está en primer lugar
    private int votosTotalesPresidente; // suma total de votos presidenciales
    //private ListaEnlazada mesasRegistradas; CONSULTAR CLASE
    private ColaPrioridadAcotada[] dHondt; // arbol heap para hacer matriz de h'hont [][]

    /*
    Inv. Rep/(c': SistemaCNE){ // La comence a la noche pero esta sin terminar --Santi
        nombresPartidos.length > 0 &&
        nombresDistristos.length > 0 &&
        votosPresidenciales.length == nombresPartidos.length &&
        diputadosPorDistrito.length == nombresDistristos.length &&
        ultimasMesasDistritos.length == nombresDistristos.length &&
        votosDiputadosPorDistrito.length == nombresDistristos.length &&
        bancasDiputadosPorDistrito.length == nombresDistristos.length &&
        bancasCalculadas == nombresDistristos.length && votosTotalesPresidente > 0 &&
        0 <= votosPrimero <= votosTotalesPresidente && 0 <= votosSegundo <= votosPrimero &&
        0 <= primero < nombresPartidos.length && dHondt.length

        forall i : int :: 0 <= i < |dHondt| ==>
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

    //NO OLVIDAR EL INVARIANTE DE REPRESENTACION Y LA COMPLEJIDAD DE ESTA CLASE
    private class Nodo implements Comparable<Nodo>{ // Esta clase la creamos nosotros, se utiliza para la D'hondt
        private int idPartido;
        private int coeficiente;
        
        Nodo(int idDistrito, int idPartido){
            this.idPartido = idPartido; 
            coeficiente = (superaElUmbral(idDistrito, idPartido)) ? votosDiputadosPorDistrito[idDistrito][idPartido] : 0 ;
        }
        
        public int compareTo(Nodo n) {
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
        this.nombresDistristos = nombresDistritos;
        this.diputadosPorDistrito = diputadosPorDistrito;
        this.nombresPartidos = nombresPartidos;
        this.ultimasMesasDistritos = ultimasMesasDistritos;
        votosPresidenciales = new int[nombresPartidos.length];
        votosDiputadosPorDistrito = new int[nombresDistristos.length][nombresPartidos.length + 1];
        bancasDiputadosPorDistrito = new int[nombresDistritos.length][nombresPartidos.length - 1];
        bancasCalculadas = new boolean[nombresDistritos.length];
        votosPrimero = 0;
        votosSegundo = 0;
        primero = 0;
        dHondt = new ColaPrioridadAcotada[nombresDistritos.length];
    }

    public String nombrePartido(int idPartido) {
        return nombresPartidos[idPartido];
    }

    public String nombreDistrito(int idDistrito) {
        return nombresDistristos[idDistrito];
    }

    public int diputadosEnDisputa(int idDistrito) {
        return diputadosPorDistrito[idDistrito];
    }

    public String distritoDeMesa(int idMesa) {
        return nombreDistrito(BusquedaBinDistrito(idMesa));
    }

    public void registrarMesa(int idMesa, VotosPartido[] actaMesa) {
        //complejidad: CALCULAR
        int indexDistrito = BusquedaBinDistrito(idMesa);
        
        //Recorrer actaMesa -P
        for (int i = 0; i < actaMesa.length; i++){
            //Actualizar votos presidenciales (para el partido y totales)
            votosPresidenciales[i] += actaMesa[i].presidente;
            votosTotalesPresidente += actaMesa[i].presidente;
            //Actualizar votos diputados (para el partido y totales)
            votosDiputadosPorDistrito[indexDistrito][i] += actaMesa[i].diputados;
            votosDiputadosPorDistrito[indexDistrito][votosDiputadosPorDistrito[indexDistrito].length-1] += actaMesa[i].diputados;

            //Actualizar variables para calcular el ballotage en O(1)
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
        
        //1.- Genera dHondt para el distrito.        
        Nodo[] coeficientes = new Nodo[nombresPartidos.length-1];
        for (int i=0; i<coeficientes.length; i++) {
            Nodo coeficiente = new Nodo(indexDistrito,i);
            coeficientes[i] = coeficiente;
        }
        ColaPrioridadAcotada<Nodo> dHondtDistrito = new ColaPrioridadAcotada<>(coeficientes.length, coeficientes);
        dHondt[indexDistrito] = dHondtDistrito;

        //2.- Marca la asignacion de bancas como no calculada.

        bancasCalculadas[indexDistrito] = false;

        //DUDA: Es necesario agregar mesa a mesas registradas?
    }

    public int votosPresidenciales(int idPartido) {
        return votosPresidenciales[idPartido];
    }

    public int votosDiputados(int idPartido, int idDistrito) {
        return votosDiputadosPorDistrito[idDistrito][idPartido];
    }

    public int[] resultadosDiputados(int idDistrito){ //throws Exception{
        if (!bancasCalculadas[idDistrito]) {
            int i = 0; //Son las bancas asignadas

            while (i < diputadosEnDisputa(idDistrito)){
                Nodo ganador = (Nodo) dHondt[idDistrito].maximo();
                bancasDiputadosPorDistrito[idDistrito][ganador.idPartido]++;
                ganador.coeficiente = votosDiputadosPorDistrito[idDistrito][ganador.idPartido]/(bancasDiputadosPorDistrito[idDistrito][ganador.idPartido] + 1);
                dHondt[idDistrito].modificarMaximo(ganador);
                i++; 
            }
            bancasCalculadas[idDistrito] = true;
        }
        
        return bancasDiputadosPorDistrito[idDistrito];
    }

    public boolean hayBallotage(){
        float porcPrimero = (votosPrimero/votosTotalesPresidente)*100;
        float porcSegundo = (votosSegundo/votosTotalesPresidente)*100;
        boolean res = true;
        if(porcPrimero >= 45){
            res = false;
        } else if (porcPrimero >= 40 && (porcPrimero - porcSegundo) > 10){
            res = false;
        }
        return res;
    }

// FUNCIONES AUXILIARES /////////////////////////////////////////////////////////////////////////////////////////

    private Boolean enRango(int min, int max, int elem){ // funcion generica para verificar si un numero esta entre un rango de numeros
        return (elem >= min && elem < max);    
        
    }

    private int BusquedaBinDistrito(int idMesa){ // busca el distrito a base del idMesa
        int indiceInicio = 0;
        int indiceFinal = ultimasMesasDistritos.length-1;
        int medio;
        int i = 0;
        int longitud = ultimasMesasDistritos.length;

        while(indiceInicio<=indiceFinal && longitud != 1){
            medio = (indiceInicio+indiceFinal)/2;
            
            int rangoMenor = ultimasMesasDistritos[medio];
            int rangoMayor = ultimasMesasDistritos[medio + 1];
            if (enRango(rangoMenor, rangoMayor, idMesa)){
                i = medio + 1;
                break;
            }
            else if (idMesa < ultimasMesasDistritos[medio]){
                indiceFinal = medio - 1;
            } else {
                indiceInicio = medio + 1;
            }
        }
        return i;
    }

    private boolean superaElUmbral(int idDistrito, int idPartido) { 
        return votosDiputadosPorDistrito[idDistrito][idPartido]*100/votosDiputadosPorDistrito[idDistrito][votosDiputadosPorDistrito[idDistrito].length-1] >= 3;
    }
}

