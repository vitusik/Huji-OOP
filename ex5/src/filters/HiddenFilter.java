package oop.ex5.filters;

import java.io.File;

/**
 * filter that checks if the file is hidden
 */
public class HiddenFilter extends BooleanFilter{
    /**
     * uses the BooleanFilter constructor
     * @param string "YES" for true, "NO" for false
     * @throws FilterException if the string in none of the above
     */
    public HiddenFilter(String string) throws FilterException{
        super(string);
    }

    /**
     * method that checks if the file status regarding its hidden status matches the filters status
     * @param file the file we want to be filtered
     * @return true or false
     */
    public boolean filter(File file){
        if(this.yesNo != file.isHidden()){
            return false;
        }
        return true;
    }
}
