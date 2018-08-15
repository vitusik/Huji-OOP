package oop.ex5.filters;

import java.io.File;

/**
 * this type of filter doesn't filter and any file it will receive it will accept it
 */
public class AllFilter implements Filter{
    /**
     * default constructor
     */
    public AllFilter(){}

    /**
     * method that receives a file and always returns true
     * @param file the file we want to filter
     * @return true, all filter doesn't filter the files
     */
    public boolean filter(File file){
        return true;
    }

}
