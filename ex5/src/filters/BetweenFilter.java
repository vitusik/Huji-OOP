package oop.ex5.filters;

import java.io.File;

/**
 * this filter checks if a given file's size is between two values
 */
public class BetweenFilter implements Filter {
    private static final String SEPARATOR = "#";
    private double lowerBoundary;
    private double upperBoundary;

    /**
     * the constructor receives string of the type "lower_boundary#upper_boundary"
     * @param string string representation of the boundaries for the filter
     * @throws FilterException will be thrown in case the boundaries aren't numbers, or the boundaries are
     * illegal meaning the boundaries are negative or the lower boundary if higher than the upper boundary
     */
    public BetweenFilter(String string) throws FilterException{
        String[] parts = string.split(SEPARATOR);
        try{
            this.lowerBoundary = Double.parseDouble(parts[0]);
            this.upperBoundary = Double.parseDouble(parts[1]);
            if(this.lowerBoundary < 0 || this.upperBoundary < 0 || this.lowerBoundary > this.upperBoundary){
                throw new FilterException();
            }
        }
        catch (NumberFormatException e){
            throw new FilterException();
        }
    }

    /**
     * method that receives a file and returns true if its size is between the filter's boundaries
     * @param file the file we want to filter
     * @return true in case the file's size is between the filter's boundaries
     */
    public boolean filter(File file){
        long fileSize = file.length();
        if(this.lowerBoundary < fileSize && fileSize < upperBoundary){
            return true;
        }
        return false;
    }
}
