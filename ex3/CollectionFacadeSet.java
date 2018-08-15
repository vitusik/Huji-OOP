/**
 * Wraps an underlying Collection and serves to both simplify its API and five it a common type with the
 * implemented SimpleHashSets
 */

public class CollectionFacadeSet implements SimpleSet {

    private java.util.Collection<java.lang.String> coll;

    /**
     * Creates a new facade wrapping the specified Collection
     * @param collection the Collection to wrap
     */
    public CollectionFacadeSet(java.util.Collection<java.lang.String> collection){
        this.coll = collection;
    }

    /**
     * Add a specified element to the set if it's not already in it.
     * @param string New value to add to the set
     * @return False if string already exists in the set
     */
    public boolean add(String string){
        if(this.coll.contains(string)){
            return false;
        }
        return this.coll.add(string);
    }

    /**
     * Look for a specified value in the set.
     * @param string Value to search for
     * @return True if string is found in the set
     */
    public boolean contains(String string){
        return this.coll.contains(string);
    }

    /**
     * Remove the input element from the set.
     * @param string Value to delete
     * @return True if string is found and deleted
     */
    public boolean delete(String string){
        return this.coll.remove(string);
    }

    /**
     *
     * @return The number of elements currently in the set
     */
    public int size(){
        return this.coll.size();
    }
}
