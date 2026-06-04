import edu.princeton.cs.algs4.BST;

public class BSTInventoryIndex {
    private BST<Integer, InventoryItem> st;
    public void put(Integer key, InventoryItem value){
        st.put(key,value);
    }
    // Debe delegar sus operaciones en princeton
}
