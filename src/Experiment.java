
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StopwatchCPU;

import java.util.ArrayList;

public class Experiment {
    private static int query_successful;
    public static int query_failed;
    private static int lend_successful;
    public static int lend_failed;
    public static int receive_successful;
    public static int receive_failed;
    public static void executePurchase(InventoryIndex index,InventoryOperation op){
        if(!index.contains(op.getKey())){
            index.put(op.getKey(),op.getItem());
        }else{
            InventoryItem item = index.get(op.getKey());
            item.addStock(op.getKey(),op.getQuantity());
            index.put(op.getKey(),item);
        }
    }
    //realizar este metodo ---- no entendi como hacerlo
    public static void executeQuery (InventoryIndex index,InventoryOperation operation){
        if(index.contains(operation.getKey())){
            InventoryItem item = index.get(operation.getKey());
        }
    }
    //no estoy seguro si se hace con boolean para ver si se puede ejecutar o no [ver 4.2.1]
    public static void executeLend (InventoryIndex index,InventoryOperation operation){
        InventoryItem item = index.get(operation.getKey());
        if(item!=null && item.getStockAvailable()>=operation.getQuantity()){
            item.lend(operation.getKey(),operation.getQuantity());
            index.put(operation.getKey(),item);
            lend_successful++;
        }
        lend_failed++;
    }
    // revisar el como corroborar que se completo la operacion puede ser utilizando booleano
    public static void executeReceive(InventoryIndex index,InventoryOperation operation){
        InventoryItem item = index.get(operation.getKey());
        if(item!=null && item.getStockOnLoan()>=operation.getQuantity()){
            item.receive(operation.getKey(),operation.getQuantity());
            index.put(operation.getKey(),item);
            receive_successful++;
        }
        receive_failed++;
    }
    public static void executeDispose (InventoryIndex index,InventoryOperation operation){
        InventoryItem item = index.get(operation.getKey());
        if(item!=null){
            index.delete(operation.getKey());
        }
        index.delete(operation.getKey());
    }
    //hacer main completo
    public static void main (String[] args) {
        int[] tamannos = {12, 13, 14, 15, 16, 17, 18, 19};
        for (int i=0;i<tamannos.length;i++){
            int t = tamannos[i];
            for(int j=0;j<30;j++){
                int m = (int)Math.pow(2,t);
                long seed = m + j;
                StdRandom.setSeed(seed);
                int keyUniverse = 4*m;
                DataGenerator generator = new DataGenerator();
                ArrayList<InventoryOperation>operations = generator.generateOperations(m,keyUniverse,seed);
                //primera medicion
                StopwatchCPU timer = new StopwatchCPU() ;
                BSTInventoryIndex index = new BSTInventoryIndex() ;
                for ( InventoryOperation op : operations ) {
                    if (op.getType()==OperationType.PURCHASE) {
                        executePurchase(index,op);
                    } else if (op.getType() == OperationType.QUERY){
                        executeQuery( index,op);
                    } else if(op.getType() == OperationType.LEND){
                        executeLend(index,op);
                    } else if (op.getType() == OperationType . RECEIVE){
                        executeReceive(index,op);
                    } else if (op.getType() == OperationType . DISPOSE){
                        executeDispose(index,op);
                    }
                }
                double elapsed = timer . elapsedTime () ;
                //inicio medicion 2
                StopwatchCPU timer2 = new StopwatchCPU() ;
                RedBlackBSTInventoryIndex index2 = new RedBlackBSTInventoryIndex();
                for (InventoryOperation op : operations){
                    if (op.getType()==OperationType.PURCHASE){
                        executePurchase(index2,op);
                    } else if (op.getType() == OperationType.QUERY){
                        executeQuery(index2,op);
                    } else if(op.getType() == OperationType.LEND ) {
                        executeLend(index2,op);
                    } else if (op.getType() == OperationType . RECEIVE ) {
                        executeReceive(index2,op);
                    } else if (op.getType() == OperationType . DISPOSE ) {
                        executeDispose(index2,op);
                    }
                }
                double elapsed2 = timer2.elapsedTime();

            }
        }

    }
}
