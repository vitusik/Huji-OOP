package oop.ex5.orders;

/**
 * the class that creates all the different orders
 */
public class OrderFactory {
    private static final String ABS = "abs", TYPE = "type", SIZE = "size", REVERSE = "#REVERSE";

    /**
     * order constructor,used when the the factory has encountered OrderException
     * @return AbsOrder
     */
    public static Order createOrder(){
        return new AbsOrder();
    }

    /**
     *
     * @param orderString the string representation of the order
     * @return an order
     * @throws OrderException thrown when the name of the order doesn't match one of the given ones
     */
    public static Order createOrder(String orderString) throws OrderException{
        boolean reverse = false;
        Order returnedOrder;
        if(orderString.endsWith(REVERSE)){
            reverse = true;
        }
        orderString = orderString.substring(0,orderString.length() - REVERSE.length());
        if(orderString.equals(ABS)){
            returnedOrder = new AbsOrder();
        }
        else if(orderString.equals(TYPE)){
            returnedOrder = new TypeOrder();
        }
        else if(orderString.equals(SIZE)){
            returnedOrder = new SizeOrder();
        }
        else{
            throw new OrderException();
        }
        if(reverse){
            returnedOrder = new ReverseOrder(returnedOrder);
        }
        return returnedOrder;
    }
}
