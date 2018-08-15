package oop.ex5.filters;


import java.io.File;

/**
 * filter that checks if file has the same name that the filter has stored
 */
public class FileFilter implements Filter {
    private String name;

    /**
     * class constructor
     * @param string the wanted file's name (including suffix)
     */
    public FileFilter(String string){
        this.name = string;
    }

    /**
     * method that returns true if the file has the same name as this.name
     * @param file the file we want to be filtered
     * @return true of false
     */
    public boolean filter(File file){
        String filename = file.getName();
        if(this.name.equals(filename)){
            return true;
        }
        return false;
    }
}
