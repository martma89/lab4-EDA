public class InventoryOperation {
    private OperationType type = new OperationType;
    private int key;
    private int quantity;
    private InventoryItem item = new InventoryItem;
    public InventoryOperation (OperationType type, int key, int quantity, InventoryItem item) {
        this.type = type;
        this.key = key;
        this.quantity = quantity;
        this.item = item;
    }






}
