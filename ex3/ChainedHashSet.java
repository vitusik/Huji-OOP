

public class ChainedHashSet extends SimpleHashSet{
    private LinkedListStrings [] hashTable;
    private int capacity, size;
    private float upperLoadFactor, lowerLoadFactor;
    private final int defaultCapacity = 16, defaultSize = 0;
    private final float defaultLowerLoadFactor = 0.25f, defaultUpperLoadFactor = 0.75f;
    private boolean hashing;
    /**
     * A default constructor
     */
    public ChainedHashSet(){
        this.hashTable = new LinkedListStrings[defaultCapacity];
        this.upperLoadFactor = defaultUpperLoadFactor;
        this.lowerLoadFactor = defaultLowerLoadFactor;
        this.capacity = defaultCapacity;
        this.size = defaultSize;
        this.hashing = false;
        for(int i = 0; i < this.hashTable.length; i++){
            this.hashTable[i] = new LinkedListStrings();
        }

    }
    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
     * @param upperLoadFactor the desired upper load factor
     * @param lowerLoadFactor the desired lower load factor
     */
    public ChainedHashSet(float upperLoadFactor, float lowerLoadFactor){
        this.hashTable = new LinkedListStrings[defaultCapacity];
        this.capacity = defaultCapacity;
        this.size = defaultSize;
        this.lowerLoadFactor = lowerLoadFactor;
        this.upperLoadFactor = upperLoadFactor;
        this.hashing = false;
        for(int i = 0; i < this.hashTable.length; i++){
            this.hashTable[i] = new LinkedListStrings();
        }
    }
    /**
     * Data constructor - builds the hash set by adding the elements one by one.
     * @param data list of strings to be added to the hash table
     */
    public ChainedHashSet(java.lang.String[] data){
        this.hashTable = new LinkedListStrings[defaultCapacity];
        this.lowerLoadFactor = defaultLowerLoadFactor;
        this.upperLoadFactor = defaultUpperLoadFactor;
        this.size = 0;
        this.capacity = defaultCapacity;
        this.hashing = false;
        for(int i = 0; i < this.hashTable.length; i++) {
            this.hashTable[i] = new LinkedListStrings();
        }
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
        if(!this.contains(string)){
            int index = (string.hashCode()) & (this.capacity() -1);
            this.hashTable[index].add(string);
            this.size += 1;
            this.checkForReHash(1);
            return true;
        }
        return false;
    }
    /**
     * Adds a string to the hash table, only used when rehashing
     * @param string new value in the set
     */
    private void reHashAdd(String string){
        int index = (string.hashCode()) & (this.capacity() -1);
        this.hashTable[index].add(string);
    }
    /**
     * Look for a specified value in the set.
     * @param string Value to search for
     * @return True if string is found in the set
     */
    public boolean contains(String string){
        if(string == null){
            return false;
        }
        int index;
        index = (string.hashCode()) & (this.capacity() -1);
        return this.hashTable[index].contains(string);
    }
    /**
     * Remove the input element from the set.
     * @param string Value to delete
     * @return True if string is found and deleted
     */
    public boolean delete(String string){
        if(this.contains(string)){
            int index = (string.hashCode()) & (this.capacity() -1);
            this.hashTable[index].remove(string);
            this.size -= 1;
            this.checkForReHash(-1);
            return true;
        }
        return false;
    }
    /**
     * Method that checks whether the HashTable needs to resized
     * @param a 1 means the check for rehash comes after a successful add method
     *          -1 mean the check for rehash comes after a successful delete method
     */
    private void checkForReHash(int a){
        if((!this.hashing)) {
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
        int newHashTableSize = this.capacity();
        if(a == 1){
            newHashTableSize = newHashTableSize * 2;
        }
        else if(a == -1){
            newHashTableSize = newHashTableSize / 2;
        }
        LinkedListStrings[] newHashTable = new LinkedListStrings[newHashTableSize];
        LinkedListStrings [] oldCopy = this.hashTable.clone();
        this.hashTable = newHashTable;
        for(int i = 0; i < this.hashTable.length; i++){
            this.hashTable[i] = new LinkedListStrings();
        }
        this.capacity = newHashTableSize;
        for(LinkedListStrings i: oldCopy){
            for(String string : i){
                this.reHashAdd(string);
            }
        }
        this.hashing = false;
    }


    /**
     *
     * @return The number of elements currently in the set
     */
    public int size(){
        return this.size;
    }
    /**
     *
     * @return the current capacity of the hash table
     */
    public int capacity(){
        return this.capacity;
    }
}
