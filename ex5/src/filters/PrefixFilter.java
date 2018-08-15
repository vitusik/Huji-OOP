package oop.ex5.filters;

import java.io.File;

/**
 * filter that checks if a prefix is the beginning of a file name
 */
public class PrefixFilter implements Filter {
    private String prefix;

    /**
     * class constructor
     * @param string the prefix
     */
    public PrefixFilter(String string){
        this.prefix = string;
    }

    /**
     * method that checks if the prefix that is stored in the filter is the beginning of the file's name
     * @param file the file we want to be filtered
     * @return true or false
     */
    public boolean filter(File file){
        String filename = file.getName();
        if(filename.startsWith(this.prefix)){
            return true;
        }
        return false;
    }
}


