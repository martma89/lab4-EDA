import
import edu.princeton.cs.algs4.StopwatchCPU;

public class Experiment {
    public static void executePurchase(InventoryIndex index,InventoryOperation op){
        if(!index.contains(op.getKey())){

        }
    }
    public static void executeQuery (InventoryIndex index,InventoryOperation operation){
    }
    public static void executeLend (InventoryIndex index,InventoryOperation operation){
    }
    public static void executeReceive(InventoryIndex index,InventoryOperation operation){
    }
    public static void executeDispose (InventoryIndex index,InventoryOperation operation){
    }
    public static void main (String[] args) {
        StopwatchCPU timer = new StopwatchCPU() ;
        BSTInventoryIndex index = new BSTInventoryIndex() ;
        for ( InventoryOperation op : operations ) {
            if (op.getType()==OperationType.PURCHASE ) {
                executePurchase(index,op) ;
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
