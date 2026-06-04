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

    public OperationType getType() {
        return type;
    }
    public int getKey() {
        return key;
    }
    public int getQuantity() {
        return quantity;
    }
    public InventoryItem getItem() {
        return item;
    }
}
