package aed;
public class SistemaCNE {
    private String[] nombresPartidos;
    private int[] votosPresidenciales;
    private String[] nombresDistristos;
    private int[] diputadosPorDistrito;
    private int[] ultimasMesasDistritos;
    private int[][] votosDiputadosPorDistrito;
    private int[][] bancasDiputadosPorDistrito;
    private ListaEnlazada mesasRegistradas;
    private ColaPrioridadAcotada<Nodo>[] dHondt;

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
        votosDiputadosPorDistrito = new int[nombresDistritos.length][nombresPartidos.length];
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
    public String distritoDeMesa(int idMesa) {
        int indiceInicio = 0;
        int indiceFinal = ultimasMesasDistritos.length-1;
        int medio;
        int i = 0;
        int longitud = ultimasMesasDistritos.length;

        while(indiceInicio<=indiceFinal && longitud != 1){
            medio = (indiceInicio+indiceFinal)/2;
            int rangoMenor = ultimasMesasDistritos[medio-1];
            int rangoMayor = ultimasMesasDistritos[medio];
            if (enRango(rangoMenor, rangoMayor, idMesa)){
                i = medio;
            }
            else if (idMesa < ultimasMesasDistritos[medio]){
                indiceFinal = medio - 1;
            } else {
                indiceInicio = medio + 1;
            }
        }
        return nombreDistrito(i);
    }

    public void registrarMesa(int idMesa, VotosPartido[] actaMesa) {
        //complejidad: O(P + log(D))
        
        //Buscar distrito mesa con distritoDeMesa() --log(D)
        //Recorrer actaMesa -P
            //Actualizar votos presidenciales (ir salvando el id del primero y el segundo?)
            //Actualizar votos diputados (teniendo en cuenta el distrito)
        //Actualizar variables para calcular el ballotage en O(1)
        //A partir del array de diputados, generar dHondt para ese distrito (-P Array2Heap)
            //usar constructor de ColaPrioridadAcotada(array)


        throw new UnsupportedOperationException("No implementada aun");
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

