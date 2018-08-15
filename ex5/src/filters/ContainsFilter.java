package oop.ex5.filters;

import java.io.File;

/**
 * filter that checks if a given string is contained in the name of the file
 */
public class ContainsFilter implements Filter{
    private String containedString;

    /**
     * class constructor
     * @param string the string that will be searched inside the file's name
     */
    public ContainsFilter(String string){
        this.containedString = string;
    }

    /**
     * method that receives a file and checks if the containedString in in the files name
     * @param file the file we want to filter
     * @return true if the string is contained inside the file's name
     */
    public boolean filter(File file){
        String filename = file.getName();
        if(filename.contains(containedString)){
            return true;
        }
        return false;
    }
}
