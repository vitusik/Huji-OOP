package oop.ex5.filters;

import java.io.File;

public class WritableFilter extends BooleanFilter{
    /**
     * uses the BooleanFilter constructor
     * @param string "YES" for true, "NO" for false
     * @throws FilterException if the string in none of the above
     */
    public WritableFilter(String string) throws FilterException{
        super(string);
    }

    /**
     * method that returns true if the files writability is the same as the one that stored in the filter
     * @param file the file we want to be filtered
     * @return true or false
     */
    public boolean filter(File file){
        if(this.yesNo != file.canWrite()){
            return false;
        }
        return true;
    }
}
