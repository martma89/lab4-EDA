
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StopwatchCPU;

public class Experiment {
    public static void executePurchase(InventoryIndex index,InventoryOperation op){
        if(!index.contains(op.getKey())){
            index.put(op.getKey(),op.getItem());
        }else{
            InventoryItem item = index.get(op.getKey());
            item.addStock(op.getKey(),op.getQuantity());
            index.put(op.getKey(),item);
        }
    }
    public static void executeQuery (InventoryIndex index,InventoryOperation operation){
        // segun deberia trabajarse en void.[nose como hacerlo xd]
        if(index.contains(operation.getKey())){

        }
    }
    //no estoy seguro si se hace con boolean para ver si se puede ejecutar o no [ver 4.2.1]
    public static boolean executeLend (InventoryIndex index,InventoryOperation operation){
        InventoryItem item = index.get(operation.getKey());
        if(item!=null && item.getStockAvailable()>=operation.getQuantity()){
            item.lend(operation.getKey(),operation.getQuantity());
            index.put(operation.getKey(),item);
            //se ejecuta
            return true;
        }
        //no se ejecuta
        return false;
    }
    // revisar el como corroborar que se completo la operacion puede ser utilizando booleano
    public static void executeReceive(InventoryIndex index,InventoryOperation operation){
        InventoryItem item = index.get(operation.getKey());
        if(item!=null && item.getStockOnLoan()>=operation.getQuantity()){
            item.receive(operation.getKey(),operation.getQuantity());
            index.put(operation.getKey(),item);
        }
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
                long m = (long)Math.pow(2,t);
                long seed = m + j;
                StdRandom.setSeed(seed);
                long keyUniverse = 4*m;
                generateOpera

            }
        }
        StopwatchCPU timer = new StopwatchCPU() ;
        BSTInventoryIndex index = new BSTInventoryIndex() ;
        for ( InventoryOperation op : operations ) {
            if (op.getType()==OperationType.PURCHASE ) {
                executePurchase(index,op);
                } else if (op.getType() == OperationType.QUERY ) {
                executeQuery( index,op) ;
                } else if(op.getType() == OperationType.LEND ) {
                executeLend(index,op) ;
                } else if (op.getType() == OperationType . RECEIVE ) {
                executeReceive(index,op) ;
                } else if (op.getType() == OperationType . DISPOSE ) {
                executeDispose(index,op) ;
                }
            }
        double elapsed = timer . elapsedTime () ;
    }
}
