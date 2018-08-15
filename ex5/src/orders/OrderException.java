package oop.ex5.orders;

/**
 * exception that is thrown when the name of the order doesn't match one of the given ones
 */
public class OrderException extends Exception {
    private static final long serialVersionUID = 1L;

    public OrderException(){
        super();
    }
}
