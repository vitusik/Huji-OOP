package oop.ex5.filters;

/**
 * super class for greater than and smaller than filters
 */
public abstract class SizeFilter implements Filter{
    protected double threshold;

    /**
     * class constructor
     * @param string string representation of the filter's boundary
     * @throws FilterException in case the boundary is negative, or cannot be casted to double
     */
    public SizeFilter(String string) throws FilterException{
        try{
            this.threshold = Double.parseDouble(string);
            if(this.threshold < 0){
                throw new FilterException();
            }
        }
        catch (NumberFormatException e){
            throw new FilterException();
        }
    }
}
