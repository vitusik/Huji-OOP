package oop.ex5.filescript;


import oop.ex5.filters.Filter;
import oop.ex5.orders.Order;

import java.util.ArrayList;

/**
 * class that is composed of a filter and a order, is used later on to filter the files, and then to put them
 * in an arrayList in the desired order
 */
public class Section {
    private Filter filter;
    private Order order;
    private ArrayList<Integer> arrayOfErrorLines;

    /**
     * default constructor
     */
    public Section(){
        this.arrayOfErrorLines = new ArrayList<Integer>();
    }

    /**
     * simple setter for order
     * @param someOrder Order of some kind
     */
    public void setOrder(Order someOrder){
        this.order = someOrder;
    }

    /**
     * simple getter for order
     * @return Order subclass
     */
    public Order getOrder(){
        return this.order;
    }

    /**
     * simple setter for filter
     * @param someFilter Filter of some kind
     */
    public void setFilter(Filter someFilter){
        this.filter = someFilter;
    }
    /**
     * simple getter for filter
     * @return Filter subclass
     */
    public Filter getFilter(){
        return this.filter;
    }

    /**
     * method that adds a number line in which type 1 error has occurred
     * @param value the number of the line
     */
    public void addBadLine(int value){
        this.arrayOfErrorLines.add(value);
    }

    /**
     * getter for the arrayList of the bad lines
     * @return ArrayList of integers
     */
    public ArrayList<Integer> getArrayOfErrorLines(){
        return this.arrayOfErrorLines;
    }


}
