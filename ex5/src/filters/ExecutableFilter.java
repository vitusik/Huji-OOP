package oop.ex5.filters;

import java.io.File;

public class ExecutableFilter extends BooleanFilter{
    /**
     * uses the BooleanFilter constructor
     * @param string "YES" for true, "NO" for false
     * @throws FilterException if the string in none of the above
     */
    public ExecutableFilter(String string) throws FilterException{
        super(string);
    }

    /**
     * method that checks the file's executability and will return true if it matches the filter criteria
     * @param file the file we want to be filtered
     * @return true or false
     */
    public boolean filter(File file){
        if(this.yesNo != file.canExecute()){
            return false;
        }
        return true;
    }
}
