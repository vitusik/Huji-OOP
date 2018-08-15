package oop.ex5.filters;

/**
 * the class that creates all the different filters
 */
public class  FilterFactory {
    private final static String NOT = "NOT", GREATER_THAN = "greater_than", BETWEEN = "between",
            SMALLER_THAN = "smaller_than", FILE = "file", CONTAINS = "contains", PREFIX = "prefix", SUFFIX = "suffix",
            WRITABLE = "writable", EXECUTABLE = "executable", HIDDEN = "hidden", ALL = "all";
    private static final String SEPARATOR = "#";

    /**
     * filter constructor,used when the the factory has encountered FilterException
     * @return AllFilter
     */
    public static Filter createFilter(){
        return new AllFilter();
    }

    /**
     * default filter constructor
     * @param filterAndArgs string that consists of the filters name and possible arguments
     * @return a filter
     * @throws FilterException in case the filterAndArgs is wrong
     */
    public static Filter createFilter(String filterAndArgs) throws FilterException {
        boolean not = false;
        Filter returnedFilter;
        if (filterAndArgs.endsWith(NOT)) {
            not = true;
            // this way each filter constructor can assume that there are not NOT statement in the end
            // of the string
            filterAndArgs = filterAndArgs.substring(0, filterAndArgs.length() - NOT.length());
        }
        String[] partsOfFilterAndArgs = filterAndArgs.split(SEPARATOR);
        if (partsOfFilterAndArgs.length >= 2) {
            String filter = partsOfFilterAndArgs[0];
            String arguments = filterAndArgs.substring(filter.length());
            if (filter.equals(GREATER_THAN)) {
                returnedFilter = new GreaterThanFilter(arguments);
            } else if (filter.equals(BETWEEN)) {
                returnedFilter = new BetweenFilter(arguments);
            } else if (filter.equals(SMALLER_THAN)) {
                returnedFilter = new SmallerThanFilter(arguments);
            } else if (filter.equals(FILE)) {
                returnedFilter = new FileFilter(arguments);
            } else if (filter.equals(CONTAINS)) {
                returnedFilter = new ContainsFilter(arguments);
            } else if (filter.equals(PREFIX)) {
                returnedFilter = new PrefixFilter(arguments);
            } else if (filter.equals(SUFFIX)) {
                returnedFilter = new SuffixFilter(arguments);
            } else if (filter.equals(WRITABLE)) {
                returnedFilter = new WritableFilter(arguments);
            } else if (filter.equals(EXECUTABLE)) {
                returnedFilter = new ExecutableFilter(arguments);
            } else if (filter.equals(HIDDEN)) {
                returnedFilter = new HiddenFilter(arguments);
            } else if (filter.equals(ALL)) {
                returnedFilter = new AllFilter();
            } else throw new FilterException();
            if (not) {
                returnedFilter = new NotFilter(returnedFilter);
            }
            return returnedFilter;
        }
        // thrown when there only one argument in the string
        throw new FilterException();
    }
}



