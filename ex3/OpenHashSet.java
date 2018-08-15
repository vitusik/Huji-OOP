
public class OpenHashSet extends SimpleHashSet {

    private String [] hashTable;
    private int capacity, size;
    private float upperLoadFactor, lowerLoadFactor;
    private final int defaultCapacity = 16, defaultSize = 0;
    private final float defaultLowerLoadFactor = 0.25f, defaultUpperLoadFactor = 0.75f;
    private final String deleted = "deleted";
    private boolean hashing;

    /**
     * A default constructor
     */
    public OpenHashSet(){
        this.hashTable = new String[defaultCapacity];
        this.capacity = defaultCapacity;
        this.size = defaultSize;
        this.lowerLoadFactor = defaultLowerLoadFactor;
        this.upperLoadFactor = defaultUpperLoadFactor;
        this.hashing = false;
    }

    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
     * @param upperLoadFactor the desired upper load factor
     * @param lowerLoadFactor the desired lower load factor
     */
    public OpenHashSet(float upperLoadFactor, float lowerLoadFactor){
        this.hashTable = new String[defaultCapacity];
        this.capacity = defaultCapacity;
        this.size = defaultSize;
        this.lowerLoadFactor = lowerLoadFactor;
        this.upperLoadFactor = upperLoadFactor;
        this.hashing = false;
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one.
     * @param data list of strings to be added to the hash table
     */
    public OpenHashSet(java.lang.String[] data){
        this.hashTable = new String[defaultCapacity];
        this.lowerLoadFactor = defaultLowerLoadFactor;
        this.upperLoadFactor = defaultUpperLoadFactor;
        this.capacity = defaultCapacity;
        this.size = defaultSize;
        this.hashing = false;
        for(String i:data){
            this.add(i);
        }
    }
    /**
     * Add a specified element to the set if it's not already in it.
     * @param string New value to add to the set
     * @return False if string already exists in the set
     */
    public boolean add(String string){
        if(string == null) {
            return false;
        }
        int hash = string.hashCode(),index = 0;
        int i = 0;
        boolean found = false;
        while(!found){
            index = (hash + (i*i+i)/2)  & (this.capacity()-1);
            if(this.hashTable[index] == null || this.hashTable[index] == this.deleted){
                found = true;
            }
            else if(this.hashTable[index].equals(string)){
                return false;
            }
            i ++;
        }
        this.hashTable[index] = string;
        this.size +=1;
        this.checkForReHash(1);
        return true;

    }

    /**
     * Adds a string to the hash table, only used when rehashing
     * @param string new value in the set
     */
    private void reHashAdd(String string){
        if(string != null && string != this.deleted) {
            // if the string is Deleted there is no need to put it in the new hash table
            int hash = string.hashCode(),index = 0;
            int i = 0;
            boolean found = false;
            while(!found){
                index = (hash + (i*i+i)/2)  & (this.capacity()-1);
                if(this.hashTable[index] == null){
                    found = true;
                    this.hashTable[index] = string;
                }
                i ++;
            }
        }
    }
    /**
     * Look for a specified value in the set.
     * @param string Value to search for
     * @return True if string is found in the set
     */
    public boolean contains(String string){
        if(string == null) {
            return false;
        }
        int i = 0;
        int hash = string.hashCode(),index = 0;
        boolean found = false;
        while(!found){
            index = (hash + (i*i+i)/2)  & (this.capacity()-1);
            if(this.hashTable[index] == null){
                return false;
            }
            else if(this.hashTable[index].equals(string)){
                found = true;
            }
            i++;
        }
        return true;
    }
    /**
     * Remove the input element from the set.
     * @param string Value to delete
     * @return True if string is found and deleted
     */
    public boolean delete(String string){
        if(string == null) {
            return false;
        }
        int hash = string.hashCode(),index = 0;
        int i = 0;
        boolean found = false;
        while(!found){
            index = (hash + (i*i+i)/2)  & (this.capacity()-1);
            if(this.hashTable[index] == null){
                return false;
            }
            else if(this.hashTable[index].equals(string)){
                found = true;
            }
            i++;
        }
        this.hashTable[index] = this.deleted;
        this.size -=1;
        this.checkForReHash(-1);
        return true;
    }

    /**
     * Method that checks whether the HashTable needs to resized
     * @param a 1 means the check for rehash comes after a successful add method
     *          -1 mean the check for rehash comes after a successful delete method
     */
    private void checkForReHash(int a){
        if(!this.hashing) {
            float ratio = (float )this.size() / (float)this.capacity();
            if (ratio > this.upperLoadFactor && a == 1) {
                this.hashing = true;
                this.reHash(1);
            } else if (ratio < this.lowerLoadFactor && a == -1) {
                this.hashing = true;
                this.reHash(-1);
            }
        }
    }

    /**
     * Method that creates new hash table
     * @param a 1 means the hash table size need to multiplied by 2
     *          -1 means the hash table size need to divided by 2
     */
    private void reHash(int a){
        int arrayNewSize = this.capacity();
        if(a == 1){
            arrayNewSize = arrayNewSize * 2;
        }
        else if(a == -1){
            arrayNewSize = arrayNewSize / 2;
        }
        String[] newHashTable = new String[arrayNewSize];
        String [] oldCopy = this.hashTable.clone();
        this.capacity = arrayNewSize;
        this.hashTable = newHashTable;
        for(String i: oldCopy){
                this.reHashAdd(i);
        }
        this.hashing = false;
    }

    /**
     *
     * @return the current capacity of the hash table
     */
    public int capacity(){
        return this.capacity;
    }

    /**
     *
     * @return The number of elements currently in the set
     */
    public int size(){
        return this.size;
    }
}
