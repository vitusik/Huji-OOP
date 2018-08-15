package oop.ex5.filters;

import java.io.File;
/**
 * filter that checks if a suffix is the end of a file name
 */
public class SuffixFilter implements Filter {
    private String suffix;
    /**
     * class constructor
     * @param string the suffix
     */
    public SuffixFilter(String string){
        this.suffix = string;
    }
    /**
     * method that checks if the suffix that is stored in the filter is the end of the file's name
     * @param file the file we want to be filtered
     * @return true or false
     */
    public boolean filter(File file){
        String filename = file.getName();
        if(filename.endsWith(this.suffix)){
            return true;
        }
        return false;
    }
}


