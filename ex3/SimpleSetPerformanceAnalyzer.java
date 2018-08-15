import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;

public class SimpleSetPerformanceAnalyzer {
    public static void main(String[] args){
        //LinkedList<String> coll = new LinkedList<String>();
        //TreeSet<String> coll = new TreeSet<String>();
        //HashSet<String> coll = new HashSet<String>();
        //CollectionFacadeSet mySet = new CollectionFacadeSet(coll);
        //ChainedHashSet mySet = new ChainedHashSet();
        OpenHashSet mySet = new OpenHashSet();
        String[] array = Ex3Utils.file2array("src/data1.txt");
        //String[] array = Ex3Utils.file2array("src/data2.txt");
        long timeBefore = System.nanoTime();
        for(String i: array){
            mySet.add(i);

        }
        long timeDiff = (System.nanoTime() - timeBefore);
        long millies = timeDiff / 1000000;
        System.out.println("built in nano sec "+timeDiff);
        System.out.println("built in mili sec "+millies);

        timeBefore = System.nanoTime();
        mySet.contains("hi");
        timeDiff = (System.nanoTime() - timeBefore);
        millies = timeDiff / 1000000;
        System.out.println("found : 'hi' in nano sec "+timeDiff);
        System.out.println("found : 'hi' in mili sec "+millies);

        timeBefore = System.nanoTime();
        mySet.contains("-13170890158");
        timeDiff = (System.nanoTime() - timeBefore);
        millies = timeDiff / 1000000;
        System.out.println("found : '-13170890158' in nano sec "+timeDiff);
        System.out.println("found : '-13170890158' in mili sec "+millies);

        timeBefore = System.nanoTime();
        mySet.contains("23");
        timeDiff = (System.nanoTime() - timeBefore);
        millies = timeDiff / 1000000;
        System.out.println("found : '23' in nano sec "+timeDiff);
        System.out.println("found : '23' in mili sec "+millies);

    }
}
