public class InventoryItem {
    int id, stockTotal, stockAvailable, stockOnLoan;
    String name, category, location;
    InventoryItem(int id, String name, String category, String location, int stockTotal, int stockAvailable, int stockOnLoan) {
        this.id = id;
        this.name = name;
        this.stockTotal = stockTotal;
        this.stockAvailable = stockAvailable;
        this.stockOnLoan = stockOnLoan;
        this.category = category;
        this.location = location;
    }
    public int getId() {
        return id;
    }
    public int getStockTotal() {
        return stockTotal;
    }
    public int getStockAvailable() {
        return stockAvailable;
    }
    public int getStockOnLoan() {
        return stockOnLoan;
    }
    public String getCategory() {
        return category;
    }
    public String getLocation() {
        return location;
    }
    public void addStock(int ItemId, int quantity){
        // No stock negativo
        // Falta ItemId
        if(quantity>=0){
        stockTotal += quantity;
        stockAvailable += quantity;
        }
    }
    public boolean lend(int ItemId, int quantity){
        // No se puede prestar más de lo disponible
        // Falta ItemId
        if(stockAvailable>=quantity && quantity>=0) {
            stockOnLoan += quantity;
            stockAvailable -= quantity;
            return true;
        }
        return false;
    }
    public boolean receive(int ItemId, int quantity){
        // No se puede prestar más de lo prestado
        // Falta ItemId
        if(stockOnLoan>=quantity && quantity>=0) {
            stockOnLoan -= quantity;
            stockAvailable += quantity;
            return true;
        }
        return false;
    }
}
