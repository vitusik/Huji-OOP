package oop.ex5.filters;


import java.io.File;

public class SmallerThanFilter extends SizeFilter{
    private double threshold;
    /**
     * uses the constructor of SizeFilter
     * @param string string representation of the filter's boundary
     * @throws FilterException in case the boundary is negative, or cannot be casted to double
     */
    public SmallerThanFilter(String string) throws FilterException{
        super(string);
    }

    /**
     * method that checks if the file's size is smaller than the stored threshold, if it is then true will
     * be returned
     * @param file the file we want to be filtered
     * @return true or false
     */
    public boolean filter(File file){
        long fileSize = file.length();
        if(this.threshold > fileSize){
            return true;
        }
        return false;
    }
}
