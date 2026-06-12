import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StopwatchCPU;
import java.util.ArrayList;

public class Experiment {
    public static public static Contador contador;
    public static void executePurchase(InventoryIndex index,InventoryOperation op){
        if(!index.contains(op.getKey())){
            index.put(op.getKey(),op.getItem());
        }
        else {
            InventoryItem item = index.get(op.getKey());
            item.addStock(op.getKey(),op.getQuantity());
            index.put(op.getKey(),item);
        }
        contador.purchase_total++;
    }
    public static void executeQuery (InventoryIndex index,InventoryOperation operation){
        if(index.contains(operation.getKey())){
            InventoryItem item = index.get(operation.getKey());
            contador.query_successful++;
        }
        else contador.query_failed++;

        contador.query_total++;
    }
    public static void executeLend (InventoryIndex index,InventoryOperation operation){
        InventoryItem item = index.get(operation.getKey());
        if(item!=null && item.getStockAvailable()>=operation.getQuantity()){
            item.lend(operation.getKey(),operation.getQuantity());
            index.put(operation.getKey(),item);
            contador.lend_successful++;
        }
        else contador.lend_failed++;

        contador.lend_total++;
    }
    public static void executeReceive(InventoryIndex index,InventoryOperation operation){
        InventoryItem item = index.get(operation.getKey());
        if(item!=null && item.getStockOnLoan()>=operation.getQuantity()){
            item.receive(operation.getKey(),operation.getQuantity());
            index.put(operation.getKey(),item);
            contador.receive_successful++;
        }
        else contador.receive_failed++;

        contador.receive_total++;
    }
    public static void executeDispose (InventoryIndex index,InventoryOperation operation){
        InventoryItem item = index.get(operation.getKey());
        if(item!=null){
            index.delete(operation.getKey());
        }
        contador.dispose_total++;
    }
    public static double medir(ArrayList<InventoryOperation> operations, InventoryIndex index){
        StopwatchCPU timer = new StopwatchCPU();

        for ( InventoryOperation op : operations ) {
            if (op.getType()==OperationType.PURCHASE) {
                executePurchase(index,op);
            } else if (op.getType() == OperationType.QUERY){
                executeQuery(index,op);
            } else if(op.getType() == OperationType.LEND){
                executeLend(index,op);
            } else if (op.getType() == OperationType.RECEIVE){
                executeReceive(index,op);
            } else if (op.getType() == OperationType.DISPOSE){
                executeDispose(index,op);
            }
        }
        double elapsed = timer.elapsedTime();

        return elapsed;
    }
    public static void validate(InventoryIndex bst, InventoryIndex RBbst, int m) {
        if (bst.size() != RBbst.size()) {
            System.err.println("Error de validación: Los tamaños finales no coinciden.");
        }

        // Verifica todos los items del inventario en vez de solo 100, asegurando correctitud total
        for (Integer key : bst.keys()) {
            InventoryItem item = bst.get(key);

            // Reglas de inventario (stock no negativo y sumas consistentes)
            if (item.getStockAvailable() < 0 || item.getStockOnLoan() < 0 || item.getStockTotal() < 0) {
                System.err.println("Error de validación: Stock negativo detectado en key " + key);
            }
            if (item.getStockAvailable() + item.getStockOnLoan() != item.getStockTotal()) {
                System.err.println("Error de validación: Inconsistencia matemática de stock en key " + key);
            }

            // Comparación de get() entre ambas estructuras
            InventoryItem itemRB = RBbst.get(key);
            if (itemRB == null || item.getId() != itemRB.getId()){
                System.err.println("Error de validación: Diferencia de resultados entre BST y RedBlackBST en key " + key);
            }
        }
        int opsTotalesEjecutadas = contador.purchase_total + contador.query_total + contador.lend_total + contador.receive_total + contador.dispose_total;
        if (opsTotalesEjecutadas != m){
            System.err.println("Error de validación: Se ejecutaron " + opsTotalesEjecutadas + " en vez de "+m+" operaciones.");
        }
    }
    public static void main (String[] args) {
        int[] size = {12, 13, 14, 15, 16, 17, 18, 19};
        for (int i=0;i<size.length;i++){
            Out csv = new Out();
            csv.println("instancia,estructura,m,purchase_total,query_total,lend_total,receive_total,dispose_total,query_successful,query_failed,lend_successful,lend_failed,receive_successful,receive_failed,final_size,final_height,elapsed_seconds");
            int t = size[i];
            int m = (int)Math.pow(2,t);
            int keyUniverse = 4*m;
            for(int instancia=0;instancia<30;instancia++){
                long seed = m + instancia;
                StdRandom.setSeed(seed);
                DataGenerator generator = new DataGenerator();
                ArrayList<InventoryOperation>operations = generator.generateOperations(m,keyUniverse,seed);
                BSTInventoryIndex BST = new BSTInventoryIndex();
                RedBlackBSTInventoryIndex RedBlackBST = new RedBlackBSTInventoryIndex();
                // Inicio Medición 1
                double elapsed_seconds = medir(operations, BST);
                String estructura = "BST";
                int final_size = BST.size();
                int final_height = BST.height();
                csv.println(instancia+ "," +estructura+ "," + m + "," +contador.purchase_total+ "," +
                        contador.query_total+ "," +contador.lend_total+ "," +contador.receive_total+ "," +
                        contador.dispose_total+ "," +contador.query_successful+ "," +contador.query_failed+ "," +
                        contador.lend_successful+ "," +contador.lend_failed+ "," +contador.receive_successful+ "," +
                        contador.receive_failed+ "," +final_size+ "," +final_height+ "," +elapsed_seconds);

                // Inicio Medición 2
                elapsed_seconds = medir(operations, RedBlackBST);
                estructura = "RedBlackBST";
                final_size = RedBlackBST.size();
                final_height = RedBlackBST.height();
                csv.println(instancia+ "," +estructura+ "," + m + "," +contador.purchase_total+ "," +
                        contador.query_total+ "," +contador.lend_total+ "," +contador.receive_total+ "," +
                        contador.dispose_total+ "," +contador.query_successful+ "," +contador.query_failed+ "," +
                        contador.lend_successful+ "," +contador.lend_failed+ "," +contador.receive_successful+ "," +
                        contador.receive_failed+ "," +final_size+ "," +final_height+ "," +elapsed_seconds);

                validate(BST, RedBlackBST, m);
            }
            csv.close();
        }
    }
}
