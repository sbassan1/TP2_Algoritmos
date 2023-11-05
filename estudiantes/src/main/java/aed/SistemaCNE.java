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
    private ColaPrioridadAcotada dHondt;

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

        while(indiceInicio<=indiceFinal){
            medio = (indiceInicio+indiceFinal)/2;
            i = medio;
            int rangoMenor = ultimasMesasDistritos[i-1];
            int rangoMayor = ultimasMesasDistritos[i];
            if (enRango(rangoMenor, rangoMayor, idMesa)){
                return nombreDistrito(i);
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

