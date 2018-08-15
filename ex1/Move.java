
public class Move {
    private int row,leftBound,rightBound;


    public Move(int inRow, int inLeft, int inRight){
        this.row = inRow;
        this.leftBound = inLeft;
        this.rightBound = inRight;
    }


    /*
    method that returns the row of a given Move object
     */
    public int getRow(){
        return this.row;
    }

    /*
    method that returns the left bound of a given Move object
     */
    public int getLeftBound(){
        return this.leftBound;
    }

    /*
    method that returns the right bound of a given Move object
     */
    public int getRightBound(){
        return this.rightBound;
    }


    /*
    method that returns the string representation of a Move object in the next
     way : "Row:LeftBound-RightBound"
     */

    public java.lang.String toString(){
        return this.getRow() + ":" + this.getLeftBound() + "-"
                + this.getRightBound();
    }


}
