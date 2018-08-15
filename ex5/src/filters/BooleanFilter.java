package oop.ex5.filters;

/**
 * a super class for all the filters that receives yes\no modifiers
 */
public abstract class BooleanFilter implements Filter {
    private static final String  YES = "YES", NO = "NO";
    protected boolean yesNo;

    /**
     * class constructor
     * @param string if it is "YES" it will be converted to boolean true, if it is "NO" it will be converted
     *               to boolean false, otherwise an exception will be thrown
     * @throws FilterException will be thrown when the string isn't "YES" or "NO"
     */
    public BooleanFilter(String string) throws FilterException{
        if(string.equals(YES)){
            this.yesNo = true;
        }
        else if(string.equals(NO)){
            this.yesNo = false;
        }
        else{
            throw new FilterException();
        }

    }
}
