import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.StopwatchCPU;
import java.util.ArrayList;

public class Experiment {
    public static class Contar {
        int purchase_total = 0, query_total = 0, lend_total = 0, receive_total = 0, dispose_total = 0;
        int query_successful = 0, query_failed = 0, lend_successful = 0, lend_failed = 0;
        int receive_successful = 0, receive_failed = 0;
    }
    public static Contar contar;

    public static void executePurchase(InventoryIndex index,InventoryOperation op){
        if(!index.contains(op.getKey())){
            index.put(op.getKey(),op.getItem());
        }
        else {
            InventoryItem item = index.get(op.getKey());
            item.addStock(op.getKey(), op.getQuantity());
            index.put(op.getKey(), item);
        }
        contar.purchase_total++;
    }
    public static void executeQuery (InventoryIndex index,InventoryOperation operation){
        if(index.contains(operation.getKey())){
            InventoryItem item = index.get(operation.getKey()); // Lectura.
            contar.query_successful++;
        }
        else contar.query_failed++;

        contar.query_total++;
    }
    public static void executeLend (InventoryIndex index,InventoryOperation operation){
        InventoryItem item = index.get(operation.getKey());
        if(item!=null && item.getStockAvailable()>=operation.getQuantity()){
            item.lend(operation.getKey(), operation.getQuantity());
            index.put(operation.getKey(), item); // Se actualiza el item.
            contar.lend_successful++;
        }
        else contar.lend_failed++;

        contar.lend_total++;
    }
    public static void executeReceive(InventoryIndex index,InventoryOperation operation){
        InventoryItem item = index.get(operation.getKey());
        if(item!=null && item.getStockOnLoan() >= operation.getQuantity()){
            item.receive(operation.getKey(), operation.getQuantity());
            index.put(operation.getKey(), item); // Se actualiza el item.
            contar.receive_successful++;
        }
        else contar.receive_failed++;

        contar.receive_total++;
    }
    public static void executeDispose (InventoryIndex index,InventoryOperation operation){
        if(index.contains(operation.getKey())){
            index.delete(operation.getKey());
        }
        contar.dispose_total++;
    }
    public static double medir(ArrayList<InventoryOperation> operations, InventoryIndex index){
        StopwatchCPU timer = new StopwatchCPU();

        for (InventoryOperation op : operations) {
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
        return timer.elapsedTime();
    }
    public static void validar(InventoryIndex bst,InventoryIndex redblackbst, int m){
        if(bst.size() != redblackbst.size()){
            System.err.println("Error: tamaño de los arreglos bst y redblackbst no coinciden");
        }
        // Se verifica cada item
        for (Integer key : bst.keys()){
            InventoryItem itemBST = bst.get(key);

            // Se aplican reglas de validación
            if (itemBST.getStockAvailable() < 0 || itemBST.getStockOnLoan() < 0 || itemBST.getStockTotal() < 0) {
                System.err.println("Error: Stock negativo en key: " + key);
            }
            if (itemBST.getStockAvailable() + itemBST.getStockOnLoan() != itemBST.getStockTotal()) {
                System.err.println("Error: Inconsistencia de stock en key: " + key);
            }
            // Comparar getters.
            InventoryItem itemRedBlackBST = redblackbst.get(key);
            if (itemRedBlackBST == null || itemBST.getId() != itemRedBlackBST.getId()) {
                System.err.println("Error: Resultados distintos en BST y RedBlackBST en key: " + key);
            }
        }
        int opsTotalesEjecutadas = contar.purchase_total + contar.query_total + contar.lend_total + contar.receive_total + contar.dispose_total;
        if (opsTotalesEjecutadas != m) {
            System.err.println("Error: se ejecutaron " + opsTotalesEjecutadas + "y no " + m + " operaciones");
        }
    }

    public static void main (String[] args) {
        // Tamaños
        int[] size = {12, 13, 14, 15, 16, 17, 18, 19};

        for (int t : size) {
            int m = (int) Math.pow(2, t);

            // Archivo CSV en carpeeta data/
            Out csv = new Out("data/inventory_experiment_" + m + ".csv");
            csv.println("instancia,estructura,m,purchase_total,query_total,lend_total,receive_total,dispose_total,query_successful,query_failed,lend_successful,lend_failed,receive_successful,receive_failed,final_size,final_height,elapsed_seconds");

            for (int instancia = 0; instancia < 30; instancia++) {
                long seed = m + instancia;
                int keyUniverse = 4 * m;

                DataGenerator generator = new DataGenerator();
                ArrayList<InventoryOperation> operations = generator.generateOperations(m, keyUniverse, seed);

                BSTInventoryIndex BST = new BSTInventoryIndex();
                RedBlackBSTInventoryIndex RedBlackBST = new RedBlackBSTInventoryIndex();

                // Inicio Medición 1
                contar = new Contar();
                double elapsed_seconds = medir(operations, BST);

                String estructura = "BST";
                int final_size = BST.size();
                int final_height = BST.height();

                csv.println(instancia + "," + estructura + "," + m + "," + contar.purchase_total + "," +
                        contar.query_total + "," + contar.lend_total + "," + contar.receive_total + "," +
                        contar.dispose_total + "," + contar.query_successful + "," + contar.query_failed + "," +
                        contar.lend_successful + "," + contar.lend_failed + "," + contar.receive_successful + "," +
                        contar.receive_failed + "," + final_size + "," + final_height + "," + elapsed_seconds);

                // Inicio Medición 2
                contar = new Contar(); // Se reinicia el contador
                elapsed_seconds = medir(operations, RedBlackBST);

                estructura = "RedBlackBST";
                final_size = RedBlackBST.size();
                final_height = RedBlackBST.height();

                csv.println(instancia + "," + estructura + "," + m + "," + contar.purchase_total + "," +
                        contar.query_total + "," + contar.lend_total + "," + contar.receive_total + "," +
                        contar.dispose_total + "," + contar.query_successful + "," + contar.query_failed + "," +
                        contar.lend_successful + "," + contar.lend_failed + "," + contar.receive_successful + "," +
                        contar.receive_failed + "," + final_size + "," + final_height + "," + elapsed_seconds);

                validar(BST, RedBlackBST, m);
            }
            csv.close();
        }
    }
}
