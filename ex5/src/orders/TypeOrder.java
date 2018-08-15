package oop.ex5.orders;

import java.io.File;

/**
 * a Comparator that compare two files based on its type, the types are compared based on their name
 */
public class TypeOrder implements Order {
    public int compare(File file1,File file2){
        int lastIndexOfFile1 = file1.getName().lastIndexOf(".");
        String fileType1 = file1.getName().substring(lastIndexOfFile1 + 1);
        int lastIndexOfFile2 = file2.getName().lastIndexOf(".");
        String fileType2 = file2.getName().substring(lastIndexOfFile1 + 1);
        return fileType1.compareTo(fileType2);
    }
}
