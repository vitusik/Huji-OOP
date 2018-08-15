package oop.ex5.orders;

import java.io.File;

/**
 * a Comparator that negates the order that it's initialized with
 */
public class ReverseOrder implements Order {
    Order order;

    /**
     * class constructor
     * @param order any Order that is created by OrderFactory
     */
    public ReverseOrder(Order order){
        this.order = order;
    }

    public int compare(File file1,File file2){
        return this.order.compare(file1,file2) *(-1);
    }
}
