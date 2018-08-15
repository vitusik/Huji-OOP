package oop.ex5.filters;


import java.io.File;

/**
 * base interface for all of the filters
 */
public interface Filter {
    /**
     * method that receives a file and uses the filter's criteria to decide if the file passes it or not
     * @param file the file we want to be filtered
     * @return true if the files passed the filter's criteria
     */
    public boolean filter(File file);
}
