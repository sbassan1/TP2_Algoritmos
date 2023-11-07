package aed;
public class SistemaCNE {
    private String[] nombresPartidos;
    private int[] votosPresidenciales;
    private String[] nombresDistristos;
    private int[] diputadosPorDistrito;
    private int[] ultimasMesasDistritos;
    private int[][] votosDiputadosPorDistrito;
    private int[][] bancasDiputadosPorDistrito;
    private int primero;
    private int segundo;
    //private ListaEnlazada mesasRegistradas;
    //private ColaPrioridadAcotada<Nodo>[] dHondt;

    public class VotosPartido{
        private int presidente;
        private int diputados;
        VotosPartido(int presidente, int diputados){this.presidente = presidente; this.diputados = diputados;}
        public int votosPresidente(){return presidente;}
        public int votosDiputados(){return diputados;}
    }

    public SistemaCNE(String[] nombresDistritos, int[] diputadosPorDistrito, String[] nombresPartidos, int[] ultimasMesasDistritos) {
        this.nombresDistristos = nombresDistritos;
        this.diputadosPorDistrito = diputadosPorDistrito;
        this.nombresPartidos = nombresPartidos;
        this.ultimasMesasDistritos = ultimasMesasDistritos;
        votosPresidenciales = new int[nombresPartidos.length];
        votosDiputadosPorDistrito = new int[nombresDistristos.length][nombresPartidos.length];
        this.primero = 0;
        this.segundo = 0;

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

    public Boolean enRango(int min, int max, int elem){
        return (elem >= min && elem < max);    
        
    }

    public int BusquedaBinDistrito(int idMesa){
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

    public String distritoDeMesa(int idMesa) {
        return nombreDistrito(BusquedaBinDistrito(idMesa));
    }

    public void registrarMesa(int idMesa, VotosPartido[] actaMesa) {
        //complejidad: O(P + log(D))
        
        //Buscar distrito mesa con BusquedaBinDistrito() --log(D)
        int indexDistrito = BusquedaBinDistrito(idMesa);
        
        //Recorrer actaMesa -P
        for (int i = 0; i <= actaMesa.length; i++){
            //Actualizar votos presidenciales 
            this.votosPresidenciales[i] += actaMesa[i].presidente;
            //Actualizar votos diputados (teniendo en cuenta el distrito)
            this.votosDiputadosPorDistrito[i][indexDistrito] += actaMesa[i].diputados;
            //Actualizar variables para calcular el ballotage en O(1)
            if (actaMesa[i].presidente > this.primero){//pensar logica de los if, creo que esta ok
                this.primero = actaMesa[i].presidente;
            } else if (actaMesa[i].presidente > this.segundo){
                this.segundo = actaMesa[i].presidente;
            }
        }   
        //A partir del array de diputados, generar dHondt para ese distrito (-P Array2Heap)
        //usar constructor de ColaPrioridadAcotada(array)
        
        
    }

    public int votosPresidenciales(int idPartido) {
        return votosPresidenciales[idPartido];
    }

    public int votosDiputados(int idPartido, int idDistrito) {
        return votosDiputadosPorDistrito[idPartido][idDistrito];
    }

    public int[] resultadosDiputados(int idDistrito){
        throw new UnsupportedOperationException("No implementada aun");
    }

    public boolean hayBallotage(){
        throw new UnsupportedOperationException("No implementada aun");
    }
}

