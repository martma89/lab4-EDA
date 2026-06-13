import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.StopwatchCPU;

import java.io.File;
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
            index.get(operation.getKey()); // Lectura.
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
            throw new IllegalStateException("Error: tamaño de los arreglos bst y redblackbst no coinciden");
        }

        int keyUniverse = 4 * m;

        // Se verifica 100 claves aleatorias
        for (int i = 0; i < 100; i++) {
            int randomKey = edu.princeton.cs.algs4.StdRandom.uniformInt(1, keyUniverse + 1);

            InventoryItem itemBST = bst.get(randomKey);
            InventoryItem itemRedBlackBST = redblackbst.get(randomKey);

            // Comparar getters.
            if ((itemBST == null) != (itemRedBlackBST == null)) {
                throw new IllegalStateException("Error: Resultados distintos de existencia en BST y RedBlackBST en key: " + randomKey);
            }
            else if (itemBST != null && itemBST.getId() != itemRedBlackBST.getId()) {
                throw new IllegalStateException("Error: Resultados distintos de ID en BST y RedBlackBST en key: " + randomKey);
            }
        }

        // Se aplican reglas de validación
        for (Integer key : bst.keys()){
            InventoryItem itemBST = bst.get(key);

            if (itemBST.getStockAvailable() < 0 || itemBST.getStockOnLoan() < 0 || itemBST.getStockTotal() < 0) {
                throw new IllegalStateException("Error: Stock negativo en key: " + key);
            }
            if (itemBST.getStockAvailable() + itemBST.getStockOnLoan() != itemBST.getStockTotal()) {
                throw new IllegalStateException("Error: Inconsistencia de stock en key: " + key);
            }
        }

        // Se validan operaciones
        int opsTotalesEjecutadas = contar.purchase_total + contar.query_total + contar.lend_total + contar.receive_total + contar.dispose_total;
        if (opsTotalesEjecutadas != m) {
            throw new IllegalStateException("Error: se ejecutaron " + opsTotalesEjecutadas + " y no " + m + " operaciones");
        }
    }
    public static void guardarCSV(Out csv, int instancia, String estructura, int m, int final_size, int final_height, double elapsed_seconds) {
        csv.println(instancia + "," + estructura + "," + m + "," + contar.purchase_total + "," +
                contar.query_total + "," + contar.lend_total + "," + contar.receive_total + "," +
                contar.dispose_total + "," + contar.query_successful + "," + contar.query_failed + "," +
                contar.lend_successful + "," + contar.lend_failed + "," + contar.receive_successful + "," +
                contar.receive_failed + "," + final_size + "," + final_height + "," + elapsed_seconds);
    }

    public static void main (String[] args) {
        // Tamaños
        int[] size = {12, 13, 14, 15, 16, 17, 18, 19};

        // Crea carpeta data si no existe;
        File carpeta = new File("data");
        if(!carpeta.exists()){
           if(!carpeta.mkdirs()){
               System.err.println("Error: No se pudo crear la carpeta.");
           }
        }

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
                double elapsed_BST = medir(operations, BST);

                guardarCSV(csv, instancia, "BST", m, BST.size(), BST.height(), elapsed_BST);

                // Inicio Medición 2
                contar = new Contar(); // Se reinicia el contador
                double elapsed_RedBlackBST = medir(operations, RedBlackBST);

                guardarCSV(csv, instancia, "RedBlackBST", m, RedBlackBST.size(), RedBlackBST.height(), elapsed_RedBlackBST);

                // Validación de datos
                validar(BST, RedBlackBST, m);
            }
            csv.close();
        }
    }
}
