package oop.ex5.filters;

import java.io.File;

/**
 * filter that negates the filter it composes
 */
public class NotFilter implements Filter {
    private Filter filter;

    /**
     * class constructor
     * @param someFilter filter that's created by FilterFactory
     */
    public NotFilter(Filter someFilter){
        this.filter = someFilter;
    }

    /**
     * method that negates the filters it composes, if the filter would've returned true it will return false
     * @param file the file we want to be filtered
     * @return true or false
     */
    public boolean filter(File file) {
        return !this.filter.filter(file);
    }
}
