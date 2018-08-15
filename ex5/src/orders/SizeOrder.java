package oop.ex5.orders;

import java.io.File;

/**
 * a Comparator that compares two files based on their size
 */
public class SizeOrder implements Order {
    public int compare(File file1,File file2){
        double size1 = file1.length();
        double size2 = file2.length();
        return Double.compare(size1,size2);
    }
}
