package oop.ex5.filters;

/**
 * exception that is thrown whenever the name of the filter in the commands file isn't matching one of the
 * given filters, or when the values for the size filters are wrong or missing
 */
public class FilterException extends Exception {
    private static final long serialVersionUID = 1L;

    public FilterException(){
        super();
    }
}
