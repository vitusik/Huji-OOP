package oop.ex5.filescript;

import java.util.*;

/**
 * the program manager, creates a parser and using it parse a file
 */
public class MyFileScript {
    final static String ERROR = "ERROR";
    public static void main(String[] args){
        if(args.length == 2){
            Parser parser = new Parser(args[0],args[1]);
            try{
                ArrayList<Section> sectionArrayList = parser.parseFile();
                parser.printScript(sectionArrayList);
            }
            catch (Type2ErrorException e){
                System.out.println(ERROR);
            }
        }
    }
}
