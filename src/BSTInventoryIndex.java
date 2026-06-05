import edu.princeton.cs.algs4.BST;

public class BSTInventoryIndex implements InventoryIndex{
    private BST<Integer, InventoryItem> st;
    // revisar todo esto

    @Override
    public void put(Integer key, InventoryItem value){
        st.put(key,value);
    }

    @Override
    public InventoryItem get(Integer key) {
        return st.get(key);
    }

    @Override
    public void delete(Integer key) {

    }

    @Override
    public boolean contains(Integer key){
        return st.contains(key);
    }

    @Override
    public Iterable<Integer> keys() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public int height() {
        return 0;
    }


    // Debe delegar sus operaciones en princeton
}
