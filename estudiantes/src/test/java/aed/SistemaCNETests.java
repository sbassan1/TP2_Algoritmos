package aed;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

class SistemaCNETests {

    public SistemaCNE inicializarSistemaCNE(){
        String[] nombresDistritos = {"Buenos Aires", "Entre Ríos", "Córdoba", "Santa Fe", 
                                    "Mendoza", "San Juan", "Tucumán", "Misiones", "Corrientes", 
                                    "Santiago del Estero", "Salta", "Chaco"};
        int[] diputadosPorDistrito = {70, 9, 18, 19, 10, 6, 9, 7, 7, 7, 7, 7};
        String[] nombresPartidos = {"El Aprendizaje Automático Avanza", "Juntos por los TADs", 
                                    "Unión por los Objetos", "Frente por la Liberación de los Tipos", 
                                    "Hacemos por los Árboles", "Blanco"};
        int[] ultimasMesasDistritos = {101, 123, 155, 202, 227, 248, 259, 278, 295, 317, 333, 350};
        return new SistemaCNE(nombresDistritos, diputadosPorDistrito, nombresPartidos, ultimasMesasDistritos);
    }

    public SistemaCNE inicializarSistemaCNE(int cantDistritos, int cantPartidos, int mesasPorDistrito) {
        String[] nombresDistritos = new String[cantDistritos];
        int[] diputadosPorDistrito = new int[cantDistritos];
        String[] nombresPartidos = new String[cantPartidos+1];
        int[] ultimasMesasDistritos = new int[cantDistritos];
        for (int i = 0; i < cantDistritos; i++) {
            nombresDistritos[i] =  "Distrito " + Integer.toString(i);
            diputadosPorDistrito[i] = (int) 1e6;
            ultimasMesasDistritos[i] = (i+1) * mesasPorDistrito;
        }
        for (int i = 0; i < cantPartidos; i++) {
            nombresPartidos[i] = "Partido " + Integer.toString(i);
        }
        nombresPartidos[cantPartidos] = "Blanco";
        return new SistemaCNE(nombresDistritos, diputadosPorDistrito, nombresPartidos, ultimasMesasDistritos);
    }

    public void registrarVotosDeMesa(SistemaCNE sistema, int idMesa, int[][] votos){
        SistemaCNE.VotosPartido[] actaMesa = new SistemaCNE.VotosPartido[votos.length];
        for (int i = 0; i < votos.length; i++) {
            actaMesa[i] = sistema.new VotosPartido(votos[i][0], votos[i][1]);
        }
        sistema.registrarMesa(idMesa, actaMesa);
    }

    @Test
    void crearSistemaCNE() {
        SistemaCNE sistema = inicializarSistemaCNE();
        assertNotNull(sistema);
    }

    @Test
    void obtenerNombresPartidos(){
        SistemaCNE sistema = inicializarSistemaCNE();
        assertEquals("El Aprendizaje Automático Avanza", sistema.nombrePartido(0));
        assertEquals("Juntos por los TADs", sistema.nombrePartido(1));
        assertEquals("Unión por los Objetos", sistema.nombrePartido(2));
        assertEquals("Frente por la Liberación de los Tipos", sistema.nombrePartido(3));
        assertEquals("Hacemos por los Árboles", sistema.nombrePartido(4));
        assertEquals("Blanco", sistema.nombrePartido(5));
    }

    @Test
    void obtenerNombresDistritos(){
        SistemaCNE sistema = inicializarSistemaCNE();
        assertEquals("Buenos Aires", sistema.nombreDistrito(0));
        assertEquals("Entre Ríos", sistema.nombreDistrito(1));
        assertEquals("Córdoba", sistema.nombreDistrito(2));
        assertEquals("Santa Fe", sistema.nombreDistrito(3));
        assertEquals("Mendoza", sistema.nombreDistrito(4));
        assertEquals("San Juan", sistema.nombreDistrito(5));
        assertEquals("Tucumán", sistema.nombreDistrito(6));
        assertEquals("Misiones", sistema.nombreDistrito(7));
        assertEquals("Corrientes", sistema.nombreDistrito(8));
        assertEquals("Santiago del Estero", sistema.nombreDistrito(9));
        assertEquals("Salta", sistema.nombreDistrito(10));
        assertEquals("Chaco", sistema.nombreDistrito(11));
    }

    @Test
    void obtenerDiputadosEnDisputa(){
        SistemaCNE sistema = inicializarSistemaCNE();
        assertEquals(70, sistema.diputadosEnDisputa(0));
        assertEquals(9, sistema.diputadosEnDisputa(1));
        assertEquals(18, sistema.diputadosEnDisputa(2));
        assertEquals(19, sistema.diputadosEnDisputa(3));
        assertEquals(10, sistema.diputadosEnDisputa(4));
        assertEquals(6, sistema.diputadosEnDisputa(5));
        assertEquals(9, sistema.diputadosEnDisputa(6));
        assertEquals(7, sistema.diputadosEnDisputa(7));
        assertEquals(7, sistema.diputadosEnDisputa(8));
        assertEquals(7, sistema.diputadosEnDisputa(9));
        assertEquals(7, sistema.diputadosEnDisputa(10));
        assertEquals(7, sistema.diputadosEnDisputa(11));
    }

    @Test
    void obtenerDistritosDeMesas(){
        SistemaCNE sistema = inicializarSistemaCNE();
        assertEquals("Buenos Aires", sistema.distritoDeMesa(5));
        assertEquals("Buenos Aires", sistema.distritoDeMesa(57));
        assertEquals("Buenos Aires", sistema.distritoDeMesa(88));
        assertEquals("Buenos Aires", sistema.distritoDeMesa(99));
        assertEquals("Entre Ríos", sistema.distritoDeMesa(103));
        assertEquals("Entre Ríos", sistema.distritoDeMesa(115));
        assertEquals("Entre Ríos", sistema.distritoDeMesa(121));
        assertEquals("Córdoba", sistema.distritoDeMesa(127));
        assertEquals("Córdoba", sistema.distritoDeMesa(135));
        assertEquals("Córdoba", sistema.distritoDeMesa(147));
        assertEquals("Córdoba", sistema.distritoDeMesa(153));
        assertEquals("Santa Fe", sistema.distritoDeMesa(167));
        assertEquals("Santa Fe", sistema.distritoDeMesa(177));
        assertEquals("Santa Fe", sistema.distritoDeMesa(189));
        assertEquals("Santa Fe", sistema.distritoDeMesa(199));
        assertEquals("Mendoza", sistema.distritoDeMesa(205));
        assertEquals("Mendoza", sistema.distritoDeMesa(214));
        assertEquals("Mendoza", sistema.distritoDeMesa(225));
        assertEquals("San Juan", sistema.distritoDeMesa(229));
        assertEquals("San Juan", sistema.distritoDeMesa(235));
        assertEquals("San Juan", sistema.distritoDeMesa(246));
        assertEquals("Tucumán", sistema.distritoDeMesa(249));
        assertEquals("Tucumán", sistema.distritoDeMesa(255));
        assertEquals("Tucumán", sistema.distritoDeMesa(258));
        assertEquals("Misiones", sistema.distritoDeMesa(264));
        assertEquals("Misiones", sistema.distritoDeMesa(275));
        assertEquals("Corrientes", sistema.distritoDeMesa(278));
        assertEquals("Corrientes", sistema.distritoDeMesa(284));
        assertEquals("Corrientes", sistema.distritoDeMesa(292));
        assertEquals("Corrientes", sistema.distritoDeMesa(294));
        assertEquals("Santiago del Estero", sistema.distritoDeMesa(305));
        assertEquals("Santiago del Estero", sistema.distritoDeMesa(312));
        assertEquals("Salta", sistema.distritoDeMesa(319));
        assertEquals("Salta", sistema.distritoDeMesa(324));
        assertEquals("Salta", sistema.distritoDeMesa(331));
        assertEquals("Chaco", sistema.distritoDeMesa(333));
        assertEquals("Chaco", sistema.distritoDeMesa(345));
    }

    @Test
    void obtenerDistritosDeMesasBordes(){
        SistemaCNE sistema = inicializarSistemaCNE();
        assertEquals("Buenos Aires", sistema.distritoDeMesa(0));
        assertEquals("Buenos Aires", sistema.distritoDeMesa(100));
        assertEquals("Entre Ríos", sistema.distritoDeMesa(101));
        assertEquals("Entre Ríos", sistema.distritoDeMesa(122));
        assertEquals("Córdoba", sistema.distritoDeMesa(123));
        assertEquals("Córdoba", sistema.distritoDeMesa(154));
        assertEquals("Santa Fe", sistema.distritoDeMesa(155));
        assertEquals("Santa Fe", sistema.distritoDeMesa(201));
        assertEquals("Mendoza", sistema.distritoDeMesa(202));
        assertEquals("Mendoza", sistema.distritoDeMesa(226));
        assertEquals("San Juan", sistema.distritoDeMesa(227));
        assertEquals("San Juan", sistema.distritoDeMesa(247));
        assertEquals("Tucumán", sistema.distritoDeMesa(248));
        assertEquals("Tucumán", sistema.distritoDeMesa(258));
        assertEquals("Misiones", sistema.distritoDeMesa(259));
        assertEquals("Misiones", sistema.distritoDeMesa(277));
        assertEquals("Corrientes", sistema.distritoDeMesa(278));
        assertEquals("Corrientes", sistema.distritoDeMesa(294));
        assertEquals("Santiago del Estero", sistema.distritoDeMesa(295));
        assertEquals("Santiago del Estero", sistema.distritoDeMesa(316));
        assertEquals("Salta", sistema.distritoDeMesa(317));
        assertEquals("Salta", sistema.distritoDeMesa(332));
        assertEquals("Chaco", sistema.distritoDeMesa(333));
        assertEquals("Chaco", sistema.distritoDeMesa(349));
    }

    @Test
    void obtenerVotosPresidencialesUnicaMesa(){
        SistemaCNE sistema = inicializarSistemaCNE();
        int[][] votos = {{14, 14}, {27, 27}, {11, 11}, {0, 0}, {7, 7}, {8, 8}};
        registrarVotosDeMesa(sistema, 0, votos);
        assertEquals(14, sistema.votosPresidenciales(0));
        assertEquals(27, sistema.votosPresidenciales(1));
        assertEquals(11, sistema.votosPresidenciales(2));
        assertEquals(0, sistema.votosPresidenciales(3));
        assertEquals(7, sistema.votosPresidenciales(4));
        assertEquals(8, sistema.votosPresidenciales(5));
    }
    
    @Test
    void obtenerVotosDiputadosUnicoDistrito(){
        SistemaCNE sistema = inicializarSistemaCNE();
        int[][] votosMesa1 = {{0, 14}, {0, 27}, {0, 11}, {0, 0}, {0, 7}, {0, 4}};
        registrarVotosDeMesa(sistema, 157, votosMesa1);

        int[][] votosMesa2 = {{0, 71}, {0, 0}, {0, 63}, {0, 4}, {0, 17}, {0, 8}};
        registrarVotosDeMesa(sistema, 199, votosMesa2);

        assertEquals(85, sistema.votosDiputados(0, 3));
        assertEquals(27, sistema.votosDiputados(1, 3));
        assertEquals(74, sistema.votosDiputados(2, 3));
        assertEquals(4, sistema.votosDiputados(3, 3));
        assertEquals(24, sistema.votosDiputados(4, 3));
        assertEquals(12, sistema.votosDiputados(5, 3));

        for (int i = 0; i < 12; i++) {
            if (i != 3) {
                assertEquals(0, sistema.votosDiputados(0, i));
                assertEquals(0, sistema.votosDiputados(1, i));
                assertEquals(0, sistema.votosDiputados(2, i));
                assertEquals(0, sistema.votosDiputados(3, i));
                assertEquals(0, sistema.votosDiputados(4, i));
                assertEquals(0, sistema.votosDiputados(5, i));
            }
        }
    }

    @Test
    void mesasEnDistintosDistritosSumanAlTotal(){
        SistemaCNE sistema = inicializarSistemaCNE();
        int[][] votosMesa1 = {{23, 18}, {15, 18}, {19, 40}, {11, 12}, {27, 7}, {17, 17}};
        registrarVotosDeMesa(sistema, 334, votosMesa1);

        assertEquals(18, sistema.votosDiputados(0, 11));
        assertEquals(18, sistema.votosDiputados(1, 11));
        assertEquals(40, sistema.votosDiputados(2, 11));
        assertEquals(12, sistema.votosDiputados(3, 11));
        assertEquals(7, sistema.votosDiputados(4, 11));
        assertEquals(17, sistema.votosDiputados(5, 11));

        int[][] votosMesa2 = {{59, 71}, {41, 35}, {23, 19}, {5, 4}, {19, 21}, {41, 38}};
        registrarVotosDeMesa(sistema, 279, votosMesa2);

        assertEquals(71, sistema.votosDiputados(0, 8));
        assertEquals(35, sistema.votosDiputados(1, 8));
        assertEquals(19, sistema.votosDiputados(2, 8));
        assertEquals(4, sistema.votosDiputados(3, 8));
        assertEquals(21, sistema.votosDiputados(4, 8));
        assertEquals(38, sistema.votosDiputados(5, 8));

        int[][] votosMesa3 = {{0, 0}, {0, 0}, {0, 0}, {0, 0}, {150, 150}, {0, 0}};
        registrarVotosDeMesa(sistema, 148, votosMesa3);

        assertEquals(0, sistema.votosDiputados(0, 2));
        assertEquals(0, sistema.votosDiputados(1, 2));
        assertEquals(0, sistema.votosDiputados(2, 2));
        assertEquals(0, sistema.votosDiputados(3, 2));
        assertEquals(150, sistema.votosDiputados(4, 2));
        assertEquals(0, sistema.votosDiputados(5, 2));

        for (int i = 0; i < 12; i++) {
            if (i != 2 && i != 8 && i != 11) {
                assertEquals(0, sistema.votosDiputados(0, i));
                assertEquals(0, sistema.votosDiputados(1, i));
                assertEquals(0, sistema.votosDiputados(2, i));
                assertEquals(0, sistema.votosDiputados(3, i));
                assertEquals(0, sistema.votosDiputados(4, i));
                assertEquals(0, sistema.votosDiputados(5, i));
            }
        }

        assertEquals(82, sistema.votosPresidenciales(0));
        assertEquals(56, sistema.votosPresidenciales(1));
        assertEquals(42, sistema.votosPresidenciales(2));
        assertEquals(16, sistema.votosPresidenciales(3));
        assertEquals(196, sistema.votosPresidenciales(4));
        assertEquals(58, sistema.votosPresidenciales(5));
    }

    @Test
    void obtenerResultadosDiputados(){
        SistemaCNE sistema = inicializarSistemaCNE();
        int[][] votosMesa1 = {{510, 580}, {240, 190}, {740, 690}, {110, 160}, {70, 50}, {170, 170}};
        registrarVotosDeMesa(sistema, 158, votosMesa1);

        int[][] votosMesa2 = {{510, 470}, {810, 950}, {150, 120}, {170, 210}, {60, 60}, {80, 100}};
        registrarVotosDeMesa(sistema, 175, votosMesa2);

        int[][] votosMesa3 = {{590, 630}, {170, 180}, {540, 460}, {290, 370}, {100, 50}, {20, 20}};
        registrarVotosDeMesa(sistema, 194, votosMesa3);

        int[][] votosMesa4 = {{640, 640}, {180, 170}, {510, 490}, {290, 380}, {100, 40}, {0, 0}};
        registrarVotosDeMesa(sistema, 195, votosMesa4);

        assertEquals(7, sistema.resultadosDiputados(3)[0]);
        assertEquals(4, sistema.resultadosDiputados(3)[1]);
        assertEquals(5, sistema.resultadosDiputados(3)[2]);
        assertEquals(3, sistema.resultadosDiputados(3)[3]);
        assertEquals(0, sistema.resultadosDiputados(3)[4]);
    }

    @Test
    void obtenerResultadosDiputadosVariosDistritos(){
        int[][] votosMesaCordoba = {{1577, 1577}, {2457, 2457}, {3417, 3417}, {947, 947}, {553, 553}, {114, 114}};
        int[][] votosMesaBuenosAires = {{7452, 7452}, {2356, 2356}, {4178, 4178}, {9853, 9853}, {5693, 5693}, {1510, 1510}};
        int[][] votosMesaCorrientes = {{6891, 6891}, {5448, 5448}, {3444, 3444}, {7630, 7630}, {1729, 1729}, {149, 149}};
        int[][] votosMesaSanJuan = {{1853, 1853}, {1458, 1458}, {5441, 5441}, {6379, 6379}, {3998, 3998}, {441, 441}};

        SistemaCNE sistema = inicializarSistemaCNE();
        registrarVotosDeMesa(sistema, 148, votosMesaCordoba);
        registrarVotosDeMesa(sistema, 99, votosMesaBuenosAires);
        registrarVotosDeMesa(sistema, 279, votosMesaCorrientes);
        registrarVotosDeMesa(sistema, 243, votosMesaSanJuan);

        assertEquals(3, sistema.resultadosDiputados(2)[0]);
        assertEquals(5, sistema.resultadosDiputados(2)[1]);
        assertEquals(7, sistema.resultadosDiputados(2)[2]);
        assertEquals(2, sistema.resultadosDiputados(2)[3]);
        assertEquals(1, sistema.resultadosDiputados(2)[4]);

        assertEquals(18, sistema.resultadosDiputados(0)[0]);
        assertEquals(5, sistema.resultadosDiputados(0)[1]);
        assertEquals(10, sistema.resultadosDiputados(0)[2]);
        assertEquals(24, sistema.resultadosDiputados(0)[3]);
        assertEquals(13, sistema.resultadosDiputados(0)[4]);

        assertEquals(2, sistema.resultadosDiputados(8)[0]);
        assertEquals(2, sistema.resultadosDiputados(8)[1]);
        assertEquals(1, sistema.resultadosDiputados(8)[2]);
        assertEquals(2, sistema.resultadosDiputados(8)[3]);
        assertEquals(0, sistema.resultadosDiputados(8)[4]);

        assertEquals(0, sistema.resultadosDiputados(5)[0]);
        assertEquals(0, sistema.resultadosDiputados(5)[1]);
        assertEquals(2, sistema.resultadosDiputados(5)[2]);
        assertEquals(3, sistema.resultadosDiputados(5)[3]);
        assertEquals(1, sistema.resultadosDiputados(5)[4]);
    }

    @Test
    void unoSuperaElUmbral(){
        int[][] votosMesa1 = {{84, 84}, {13, 13}, {15, 15}, {14, 14}, {0, 0}, {1510, 1510}};
        int[][] votosMesa2 = {{0, 0}, {19, 19}, {11, 11}, {5, 5}, {7, 7}, {1718, 1718}};
        int[][] votosMesa3 = {{46, 46}, {21, 21}, {34, 34}, {51, 51}, {3, 3}, {1914, 1914}};
        int[][] votosMesa4 = {{121, 121}, {39, 39}, {4, 4}, {19, 19}, {44, 44}, {2184, 2184}};

        SistemaCNE sistema = inicializarSistemaCNE();
        registrarVotosDeMesa(sistema, 249, votosMesa1);
        registrarVotosDeMesa(sistema, 250, votosMesa2);
        registrarVotosDeMesa(sistema, 251, votosMesa3);
        registrarVotosDeMesa(sistema, 252, votosMesa4);

        assertEquals(9, sistema.resultadosDiputados(6)[0]);
        assertEquals(0, sistema.resultadosDiputados(6)[1]);
        assertEquals(0, sistema.resultadosDiputados(6)[2]);
        assertEquals(0, sistema.resultadosDiputados(6)[3]);
        assertEquals(0, sistema.resultadosDiputados(6)[4]);
    }

    @Test
    void noHayBallotageConDifDe10(){
        int[][] votosMesa1 = {{2374, 2374}, {1048, 1048}, {584, 584}, {110, 110}, {234, 234}, {170, 170}};
        int[][] votosMesa2 = {{3125, 3125}, {647, 647}, {487, 487}, {191, 191}, {484, 484}, {410, 410}};
        int[][] votosMesa3 = {{841, 841}, {245, 245}, {1058, 1058}, {620, 620}, {678, 678}, {203, 203}};
        int[][] votosMesa4 = {{1024, 1024}, {335, 335}, {1248, 1248}, {857, 857}, {222, 222}, {669, 669}};
        
        SistemaCNE sistema = inicializarSistemaCNE();
        registrarVotosDeMesa(sistema, 99, votosMesa1);
        registrarVotosDeMesa(sistema, 111, votosMesa2);
        registrarVotosDeMesa(sistema, 131, votosMesa3);
        registrarVotosDeMesa(sistema, 157, votosMesa4);

        assertEquals(7364, sistema.votosPresidenciales(0));
        assertEquals(2275, sistema.votosPresidenciales(1));
        assertEquals(3377, sistema.votosPresidenciales(2));
        assertEquals(1778, sistema.votosPresidenciales(3));
        assertEquals(1618, sistema.votosPresidenciales(4));
        assertEquals(1452, sistema.votosPresidenciales(5));

        assertFalse(sistema.hayBallotage());
    }

    @Test
    void noHayBallotageConMasDe45(){
        int[][] votosMesa1 = {{5987, 5987}, {1048, 1048}, {3698, 3698}, {110, 110}, {234, 234}, {170, 170}};
        int[][] votosMesa2 = {{3125, 3125}, {647, 647}, {5789, 5789}, {191, 191}, {484, 484}, {410, 410}};
        int[][] votosMesa3 = {{4785, 4785}, {245, 245}, {8789, 8789}, {620, 620}, {678, 678}, {203, 203}};
        int[][] votosMesa4 = {{6478, 6478}, {335, 335}, {4354, 4354}, {857, 857}, {222, 222}, {669, 669}};

        SistemaCNE sistema = inicializarSistemaCNE();
        registrarVotosDeMesa(sistema, 298, votosMesa1);
        registrarVotosDeMesa(sistema, 223, votosMesa2);
        registrarVotosDeMesa(sistema, 244, votosMesa3);
        registrarVotosDeMesa(sistema, 335, votosMesa4);

        assertEquals(20375, sistema.votosPresidenciales(0));
        assertEquals(2275, sistema.votosPresidenciales(1));
        assertEquals(22630, sistema.votosPresidenciales(2));
        assertEquals(1778, sistema.votosPresidenciales(3));
        assertEquals(1618, sistema.votosPresidenciales(4));
        assertEquals(1452, sistema.votosPresidenciales(5));

        assertFalse(sistema.hayBallotage());
    }

    @Test
    void hayBallotage(){
        int[][] votosMesa1 = {{5987, 5987}, {1048, 1048}, {3698, 3698}, {110, 110}, {234, 234}, {1780, 1780}};
        int[][] votosMesa2 = {{3125, 3125}, {647, 647}, {5789, 5789}, {191, 191}, {484, 484}, {4180, 4180}};
        int[][] votosMesa3 = {{4785, 4785}, {245, 245}, {8789, 8789}, {620, 620}, {678, 678}, {203, 203}};
        int[][] votosMesa4 = {{6478, 6478}, {335, 335}, {4354, 4354}, {857, 857}, {222, 222}, {669, 669}};

        SistemaCNE sistema = inicializarSistemaCNE();
        registrarVotosDeMesa(sistema, 124, votosMesa1);
        registrarVotosDeMesa(sistema, 228, votosMesa2);
        registrarVotosDeMesa(sistema, 244, votosMesa3);
        registrarVotosDeMesa(sistema, 152, votosMesa4);

        assertEquals(20375, sistema.votosPresidenciales(0));
        assertEquals(2275, sistema.votosPresidenciales(1));
        assertEquals(22630, sistema.votosPresidenciales(2));
        assertEquals(1778, sistema.votosPresidenciales(3));
        assertEquals(1618, sistema.votosPresidenciales(4));
        assertEquals(6832, sistema.votosPresidenciales(5));

        assertTrue(sistema.hayBallotage());
    }

    @Test
    @Timeout(2)
    void complejidadObtenerDistritosDeMesas() {
        int cantDistritos = (int) 1e5;
        int mesasPorDistrito = 10;
        int cantPartidos = (int) 1e3;
        SistemaCNE sistema = inicializarSistemaCNE(cantDistritos, cantPartidos, mesasPorDistrito);

        for (int idDistrito = 0; idDistrito < cantDistritos; idDistrito++) {
            for (int numMesa = 0; numMesa < mesasPorDistrito; numMesa++) {
                int idMesa = idDistrito * mesasPorDistrito + numMesa;
                assertEquals("Distrito " + Integer.toString(idDistrito), sistema.distritoDeMesa(idMesa));
            }
        }
    }

    @Test
    @Timeout(4)
    void complejidadRegistrarMesa() {
        int cantDistritos = (int) 10;
        int mesasPorDistrito = 10;
        int cantPartidos = (int) 1e6;
        SistemaCNE sistema = inicializarSistemaCNE(cantDistritos, cantPartidos, mesasPorDistrito);

        int[][] votos = new int[cantPartidos][2];
        for (int idPartido = 0; idPartido < cantPartidos; idPartido++) {
            votos[idPartido][0] = idPartido * 1000;
            votos[idPartido][1] = idPartido * 1000;
        }
        for (int idDistrito = 0; idDistrito < cantDistritos; idDistrito++) {
            registrarVotosDeMesa(sistema, idDistrito * mesasPorDistrito, votos);
        }
    }

    @Test
    @Timeout(3)
    void complejidadResultadosDiputados() {
        int cantDistritos = (int) 50;
        int mesasPorDistrito = 10;
        int cantPartidos = (int) 100;
        // Hay 1e6 diputados por cada distrito
        SistemaCNE sistema = inicializarSistemaCNE(cantDistritos, cantPartidos, mesasPorDistrito);

        int[][] votos = new int[cantPartidos][2];
        for (int idPartido = 0; idPartido < cantPartidos; idPartido++) {
            votos[idPartido][0] = idPartido * (int) 1e6 + 51;
            votos[idPartido][1] = idPartido * (int) 1e6 + 51;
        }
        for (int idDistrito = 0; idDistrito < cantDistritos; idDistrito++) {
            registrarVotosDeMesa(sistema, idDistrito * mesasPorDistrito, votos);
        }

        for (int idDistrito = 0; idDistrito < cantDistritos; idDistrito++){ 
            sistema.resultadosDiputados(idDistrito);
        }
    }
}
