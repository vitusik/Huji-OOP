package oop.ex5.orders;


import java.io.File;

/**
 * a Comparator that compares file based on their absolute path
 */
public class AbsOrder implements Order {
    public int compare(File file1,File file2){
        String filePath1 = file1.getAbsolutePath();
        String filePath2 = file2.getAbsolutePath();
        return filePath1.compareTo(filePath2);
    }
}
