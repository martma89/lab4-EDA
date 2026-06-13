import edu.princeton.cs.algs4.StdRandom;
import java.util.ArrayList;
import java.util.HashSet;

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

        int stockTotal = StdRandom.uniformInt(1,21); // [1,21[
        int stockOnLoan = 0;

        // stockAvailable y stockTotal son iguales cuando stockOnLoan es 0.
        return new InventoryItem(id,name,category,location,stockTotal,stockTotal,stockOnLoan);
    }
     /*
      * m: Cantidad total de operaciones a generar
      * keyUniverse: Rango de claves posibles. [1, keyUniverse]
      * seed: Semilla para reproducibilidad
      */
    public ArrayList<InventoryOperation> generateOperations(int m, int keyUniverse, long seed){
        StdRandom.setSeed(seed);
        ArrayList<InventoryOperation> operations = new ArrayList<>();

        HashSet<Integer> existingKeys = new HashSet<>(); // Para guardar IDs

        for(int i = 0; i < m; i++){
            int key = StdRandom.uniformInt(1,keyUniverse+1); // [1, keyUniverse]
            double probabilidad = StdRandom.uniformDouble();

            OperationType type;

            // Casos base
            int quantity = 0;
            InventoryItem item = null;

            if (probabilidad < 0.35){
                type = OperationType.PURCHASE; // Purchase 0.35
                quantity = StdRandom.uniformInt(1,6); // [1,6[

                // Si no esta, se crea nuevo
                if(!existingKeys.contains(key)){
                    item = generateItem(key);
                    existingKeys.add(key); // Se registra el ID.
                }
            }
            else if (probabilidad < 0.65){ // Se suman % anteriores (0.35+0.3)
                type = OperationType.QUERY; // Query 0.3
            }
            else if (probabilidad < 0.80){ // 0.65+0.15
                type = OperationType.LEND; // Lend 0.15
                quantity = StdRandom.uniformInt(1,6); // [1,6[
            }
            else if (probabilidad < 0.90){ // 0.8+0.1
                type = OperationType.RECEIVE; // Receive 0.1
                quantity = StdRandom.uniformInt(1,6); // [1,6[
            }
            else {
                type = OperationType.DISPOSE; // Dispose 0.1
                existingKeys.remove(key);
            }
            operations.add(new InventoryOperation(type, key, quantity, item));
        }
        return operations;
    }
}
