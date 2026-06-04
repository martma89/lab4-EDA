import edu.princeton.cs.algs4.StdRandom;
import java.util.ArrayList;

public class DataGenerator {
    public static InventoryItem generateItem(int id){
        String name = "Componente "+id;
        String[] categories = {
                "Sensor",
                "Motor",
                "Microcontrolador",
                "Cable",
                "Bateria",
                "Herramienta",
                "Modulo",
                "Kit"
        };
        String[] locations = {
                "Estante_A",
                "Estante_B",
                "Caja_1",
                "Caja_2",
                "Laboratorio",
                "Bodega"
        };
        // [0, 8[
        String category = categories[StdRandom.uniformInt(0,categories.length)];
        // [0, 6[
        String location = locations[StdRandom.uniformInt(0,locations.length)];
        int stockTotal = StdRandom.uniform(1,21); // [1,21[
        int stockAvailable = stockTotal;
        int stockOnLoan = 0;
        InventoryItem generado = new InventoryItem(id,name,category,location,stockTotal,stockAvailable,stockOnLoan);
        return generado;
    }
     /*
      * m: Cantidad total de operaciones a generar
      * keyUniverse: Rango de claves posibles. [1, keyUniverse]
      * seed: Semilla para reproducibilidad
      */
    public static ArrayList<InventoryItem> generateOperations(int m,int keyUniverse, long seed){
        StdRandom.setSeed(seed);
        // Distribución: Purchase 0.35, Query 0.3, Lend 0.15, Receive 0.1, Dispose 0.1
        // Purchase, Lend, Receive: StdRandom.uniformInt(1,6);
        // Query, Dispose: 0;
        // Purchase si no esta debe crear nuevo (...)
    }
}
