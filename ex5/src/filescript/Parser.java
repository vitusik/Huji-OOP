package oop.ex5.filescript;


import oop.ex5.filters.*;
import oop.ex5.orders.*;

import java.io.*;
import java.util.*;

/**
 * the class that receives a command file, and a directory
 * and then creates section list and can filter the directory and arrange it using a desired order
 */
public class Parser {
    final String FILTER = "FILTER", ORDER = "ORDER";
    private String commandsFile, sourceDir;
    private static FilterFactory filterFactory = new FilterFactory();
    private static OrderFactory orderFactory = new OrderFactory();

    /**
     * class constructor
     * @param commandsFile string representation of the absolute path to the command file
     * @param sourceDir string representation of the absolute path to the source directory
     */
    public Parser(String commandsFile, String sourceDir){
        this.commandsFile = commandsFile;
        this.sourceDir = sourceDir;
    }

    /**
     * creates the filter part in a given section
     * @param section the section we want to add a filter to it
     * @param string the string representation of the filter
     * @param lineNum the line number of the string, needed in case of type 1 error
     */
    private void filterParse(Section section,String string,int lineNum){
        Filter filter;
        try{
            filter = filterFactory.createFilter(string);
            section.setFilter(filter);
            }
        catch (FilterException e){
            filter = filterFactory.createFilter();
            section.setFilter(filter);
            section.addBadLine(lineNum);
        }
    }

    /**
     * creates the order part in a given section
     * @param section the section we want to add a filter to it
     * @param string the string representation of the order
     * @param lineNum the line number of the string, needed in case of type 1 error
     */
    private void orderParse(Section section,String string,int lineNum){
        Order order;
        try{
            order = orderFactory.createOrder(string);
            section.setOrder(order);
        }
        catch (OrderException e){
            order = orderFactory.createOrder();
            section.setOrder(order);
            section.addBadLine(lineNum);
        }
    }

    /**
     * method that creates a list of sections
     * @return ArrayList of Sections
     * @throws Type2ErrorException in case the command file is in the wrong format
     */
    public ArrayList<Section> parseFile() throws Type2ErrorException{
        ArrayList<Section> sectionsList = new ArrayList<Section>();
        try {
            File commandFile = new File(this.commandsFile);
            FileReader fileReader = new FileReader(commandFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            LineNumberReader lineNumberReader = new LineNumberReader(bufferedReader);
            String line = lineNumberReader.readLine();
            while(line != null){
                Section parsedSection = new Section();
                if(line.equals(FILTER)){
                    line = lineNumberReader.readLine();
                    filterParse(parsedSection,line,lineNumberReader.getLineNumber());
                }
                else{
                    lineNumberReader.close();
                    throw new Type2ErrorException();
                }
                line = lineNumberReader.readLine();
                if(line.equals(ORDER)){
                    line = lineNumberReader.readLine();
                    orderParse(parsedSection,line,lineNumberReader.getLineNumber());
                }
                else {
                    lineNumberReader.close();
                    throw new Type2ErrorException();
                }
                sectionsList.add(parsedSection);
            }
            lineNumberReader.close();
        }
        catch (FileNotFoundException e){
        }
        catch (IOException e){
        }

        return sectionsList;
    }

    /**
     * method that prints out all the warning in each section and then all the matched file in the correct
     * order per section
     * @param sections list of the sections created by parseFile
     */
    public void printScript(ArrayList<Section> sections){
        File[] files = new File(this.sourceDir).listFiles();
        final String type1Error = "warning in line ";
        for(Section section : sections){
            ArrayList<File> fileArrayList = new ArrayList<File>();
            for(File file : files){
                if(!section.getArrayOfErrorLines().isEmpty()){
                    for(int i : section.getArrayOfErrorLines()){
                        System.out.println(type1Error+i);
                    }
                }
                if(section.getFilter().filter(file)){
                    fileArrayList.add(file);
                }
            }
            Collections.sort(fileArrayList,section.getOrder());
            for(File file: fileArrayList){
                System.out.println(file.getName());
            }
        }
    }
}
