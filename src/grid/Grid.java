package grid;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Grid
{
    private List<String>                i_gridColumns                   = new ArrayList<>();
    private List<Map<String, Object>>   i_gridList                      = new ArrayList<>();
    private StringBuilder               i_stringBuilder                 = new StringBuilder();
    private int                         i_iteratorPosition              = 0;
    private int                         i_gridPosition                  = 0;
    private static final String         i_NEW_LINE                      = "\n";
    private static final String         i_SEARCH_FULL                   = "full";
    private static final String         i_SEARCH_FULL_INSENSITIVE       = "full-insensitive";
    private static final String         i_SEARCH_PARTIAL                = "partial";
    private static final String         i_SEARCH_PARTIAL_INSENSITIVE    = "partial-insensitive";
    private static final String         i_COLUMN_ROW                    = "Row";
    private static final String         i_OUTPUT_PLUS                   = "+";
    private static final String         i_OUTPUT_PIPE                   = "|";
    private static final String         i_OUTPUT_SPACE                  = " ";
    private static final String         i_OUTPUT_SPACE_EMPTY            = "";
    private static final String         i_OUTPUT_DASH                   = "-";
    private static final String         i_OUTPUT_NO_RESULTS             = "Empty";
    private static final String         i_ERROR_MAX_ROWS                = "Cannot switch to next row\nRow position is currently set to last row.";
    private static final String         i_ERROR_MIN_ROWS                = "Cannot switch to previous row\nRow position is currently set to first row.";

    /**
     * Takes supplied Grid object and position and returns true or false if it exists
     * @param grid Grid
     * @param position int
     * @return boolean
     */
    public static boolean indexExists(Grid grid, int position)
    {
        if (grid.getTotalRowCount() > position)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Takes supplied Grid object and checks i_gridPosition to see if it exists
     * @param grid Grid
     * @return boolean
     */
    public static boolean indexExists(Grid grid)
    {
        return indexExists(grid, grid.getGridPosition());
    }

    /**
     * Takes key and value and stores it in row by number
     * @param rowNumber int
     * @param key String
     * @param value Object
     */
    public void append(int rowNumber, String key, Object value)
    {
        /*
         * Adds columns if they don't exist
         */
        addColumn(key);

        /*
         * Index exists; fetch map and append values
         */
        if (indexExists(this, rowNumber))
        {
            Map<String, Object> map = i_gridList.get(rowNumber);
            map.put(key, value);
        }

        /*
         * Index does not exist; create new map and store
         */
        else
        {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put(key, value);
            i_gridList.add(map);
        }
    }

    /**
     * Takes key and value and stores it in row number i_gridPosition
     * @param key String
     * @param value Object
     */
    public void append(String key, Object value)
    {
        append(getGridPosition(), key, value);
    }

    /**
     * Takes map and stores it in grid
     * @param map Map&lt;String, Object&gt;
     */
    public void append(Map<String, Object> map)
    {
        /*
         * Get map columns and load into object
         */
        List<String> keys = new ArrayList<String>(map.keySet());
        for (int i = 0; i < map.size(); i++) {
            Object key = keys.get(i);
            addColumn((String) key);
        }

        /*
         * Index exists; append map to existing
         */
        if (indexExists(this))
        {
            Map<String, Object> m = i_gridList.get(i_gridPosition);
            for (int i = 0; i < map.size(); i++) {
                Object key = keys.get(i);
                m.put((String) key, map.get(key));
            }
        }

        /*
         * Index does not exist; add map
         */
        else
        {
            i_gridList.add(map);
        }

    }

    /**
     * Increases grid's starting position by one so next append goes on a new row
     */
    public void newRow()
    {
        setGridPosition(getGridPosition() + 1);
    }

    /**
     * Returns number of rows in the grid
     * @return int
     */
    public int getTotalRowCount()
    {
        return i_gridList.size();
    }

    /**
     * Returns a Map from supplied row number
     * @param rowNumber int
     * @return Map&lt;String, Object&gt;
     */
    public Map<String, Object> getRowByNumber(int rowNumber)
    {
        return i_gridList.get(rowNumber);
    }

    /**
     * Returns a Map from the grid's current position
     * @return Map&lt;String, Object&gt;
     */
    public Map<String, Object> getGridCurrentRow()
    {
        return i_gridList.get(getGridPosition());
    }

    /**
     * Returns value from supplied row number and column
     * @param number int
     * @param column String
     * @return Object
     */
    public Object getValueByRowAndColumn(int number, String column)
    {
        return i_gridList.get(number).get(column);
    }

    /**
     * Adds supplied column to the grid if it does not exist
     * @param column String
     */
    public void addColumn(String column)
    {
        if (i_gridColumns.indexOf(column) < 0)
        {
            i_gridColumns.add(column);
        }
    }

    /**
     * Removes supplied column from both the grid and column lists
     * @param column String
     */
    public void removeColumn(String column)
    {
        i_gridColumns.remove(column);
        int index = -1;
        for (Map<String, Object> row : i_gridList) {
            index = i_gridList.indexOf(row);
            row.remove(column);

            /*
             * If Map is left empty, remove it
             */
            if (row.isEmpty())
            {
                i_gridList.remove(index);
            }
        }
    }

    /**
     * Return a List of column names
     * @return List&lt;String&gt;
     */
    public List<String> getColumns()
    {
        return i_gridColumns;
    }

    /**
     * Return the grid's current position
     * @return int
     */
    public int getGridPosition() {
        return i_gridPosition;
    }

    /**
     * Set grid's position
     * @param gridPosition int
     */
    private void setGridPosition(int gridPosition) {
        i_gridPosition = gridPosition;
    }

    /**
     * Sets grid's position to supplied row number
     * @param rowNumber int
     */
    public void setGridRow(int rowNumber)
    {
        setGridPosition(rowNumber);
    }

    /**
     * Set grid's position to the first row
     */
    public void setGridTop()
    {
        setGridPosition(0);
    }

    /**
     * Set grid's position to the last row
     */
    public void setGridBottom()
    {
        setGridPosition(i_gridList.size() - 1);
    }

    /**
     * Moves grid's position down one row
     */
    public void setGridRowNext()
    {
        if (getGridPosition() + 1 <= i_gridList.size())
        {
            setGridPosition(getGridPosition() + 1);
        }
        else
        {
            System.out.println(i_ERROR_MAX_ROWS);
        }
    }

    /**
     * Moves grid's position up one row
     */
    public void setGridRowPrev()
    {
        if (getGridPosition() - 1 >= 0)
        {
            setGridPosition(getGridPosition() - 1);
        }
        else
        {
            System.out.println(i_ERROR_MIN_ROWS);
        }
    }

    /**
     * Get iterator's position
     * @return int
     */
    public int getIteratorPosition()
    {
        return i_iteratorPosition;
    }

    /**
     * Returns true if there are still more rows to iterate
     * @return boolean
     */
    public boolean hasIteratorNext() {
        return i_iteratorPosition < i_gridList.size() && i_gridList.get(i_iteratorPosition) != null;
    }

    /**
     * Returns a Map of the current iterator row
     * @return Map&lt;String, Object&gt;
     */
    public Map<String, Object> getIteratorCurrentRow()
    {
        return i_gridList.get(i_iteratorPosition);

    }

    /**
     * Returns a Map of the next iterator row
     * @return Map&lt;String, Object&gt;
     */
    public Map<String, Object> getIteratorNext() {
        return i_gridList.get(i_iteratorPosition++);
    }

    /**
     * Returns a new Grid Object containing results from search
     * @param type String
     * @param key String
     * @param search String
     * @return Grid
     */
    private Grid getObjectSearch(String type, String key, String search)
    {
        Grid searchGrid = new Grid();
        for (int i = 0; i < i_gridList.size(); i++) {
            int index = i;
            Map<String, Object> map = i_gridList.get(index);
            String value = (String) map.get(key);

            if (type.equals(i_SEARCH_FULL))
            {
                if (search.equals(value))
                {
                    searchGrid.append(map);
                }
            }
            else if (type.equals(i_SEARCH_FULL_INSENSITIVE))
            {
                if (value.toUpperCase().equals(search.toUpperCase()))
                {
                    searchGrid.append(map);
                }
            }
            else if (type.equals(i_SEARCH_PARTIAL))
            {
                if (value.contains(search))
                {
                    searchGrid.append(map);
                }
            }
            else if (type.equals(i_SEARCH_PARTIAL_INSENSITIVE))
            {
                if (value.toUpperCase().contains(search.toUpperCase()))
                {
                    searchGrid.append(map);
                }
            }
            searchGrid.newRow();
        }
        return searchGrid;
    }

    /**
     * Search by full string search
     * @param key String
     * @param search String
     * @return Grid
     */
    public Grid getObjectSearchFull(String key, String search)
    {
        return getObjectSearch(i_SEARCH_FULL, key, search);
    }

    /**
     * Search by full case insensitive search
     * @param key String
     * @param search String
     * @return Grid
     */
    public Grid getObjectSearchFullCaseInsensitive(String key, String search)
    {
        return getObjectSearch(i_SEARCH_FULL_INSENSITIVE, key, search);
    }

    /**
     * Search by partial string search
     * @param key String
     * @param search String
     * @return Grid
     */
    public Grid getObjectSearchPartial(String key, String search)
    {
        return getObjectSearch(i_SEARCH_PARTIAL, key, search);
    }

    /**
     * Search by partial case insensitive search
     * @param key String
     * @param search String
     * @return Grid
     */
    public Grid getObjectSearchPartialCaseInsensitive(String key, String search)
    {
        return getObjectSearch(i_SEARCH_PARTIAL_INSENSITIVE, key, search);
    }

    /**
     * Returns a List of Maps containing results from search
     * @param type String
     * @param key String
     * @param search String
     * @return List&lt;Map&lt;String, Object&gt;&gt;
     */
    private List<Map<String, Object>> getListSearch(String type, String key, String search)
    {
        List<Map<String, Object>> searchGrid = new ArrayList<>();
        for (int i = 0; i < i_gridList.size(); i++) {
            int index = i;
            Map<String, Object> map = i_gridList.get(index);
            String value = (String) map.get(key);

            if (type.equals(i_SEARCH_FULL))
            {
                if (search.equals(value))
                {
                    searchGrid.add(map);
                }
            }
            else if (type.equals(i_SEARCH_FULL_INSENSITIVE))
            {
                if (value.toUpperCase().equals(search.toUpperCase()))
                {
                    searchGrid.add(map);
                }
            }
            else if (type.equals(i_SEARCH_PARTIAL))
            {
                if (value.contains(search))
                {
                    searchGrid.add(map);
                }
            }
            else if (type.equals(i_SEARCH_PARTIAL_INSENSITIVE))
            {
                if (value.toUpperCase().contains(search.toUpperCase()))
                {
                    searchGrid.add(map);
                }
            }
        }
        return searchGrid;
    }

    /**
     * Search by full string search
     * @param key String
     * @param search String
     * @return List&lt;Map&lt;String, Object&gt;&gt;
     */
    public List<Map<String, Object>> getListSearchFull(String key, String search)
    {
        return getListSearch(i_SEARCH_FULL, key, search);
    }

    /**
     * Search by full case insensitive search
     * @param key String
     * @param search String
     * @return List&lt;Map&lt;String, Object&gt;&gt;
     */
    public List<Map<String, Object>> getListSearchFullCaseInsensitive(String key, String search)
    {
        return getListSearch(i_SEARCH_FULL_INSENSITIVE, key, search);
    }

    /**
     * Search by partial string search
     * @param key String
     * @param search String
     * @return List&lt;Map&lt;String, Object&gt;&gt;
     */
    public List<Map<String, Object>> getListSearchPartial(String key, String search)
    {
        return getListSearch(i_SEARCH_PARTIAL, key, search);
    }

    /**
     * Search by partial case insensitive search
     * @param key String
     * @param search String
     * @return List&lt;Map&lt;String, Object&gt;&gt;
     */
    public List<Map<String, Object>> getListSearchPartialCaseInsensitive(String key, String search)
    {
        return getListSearch(i_SEARCH_PARTIAL_INSENSITIVE, key, search);
    }

    /**
     * Returns an iterator Map containing results from search
     * @param type String
     * @param key String
     * @param search String
     * @return Map&lt;String, Object&gt;
     */
    private Map<String, Object> getIteratorSearch(String type, String key, String search)
    {
        Map<String, Object> map = i_gridList.get(i_iteratorPosition);
        String value = (String) map.get(key);

        if (type.equals(i_SEARCH_FULL))
        {
            if (search.equals(value))
            {
                return map;
            }
            else
            {
                return new LinkedHashMap<>();
            }
        }
        else if (type.equals(i_SEARCH_FULL_INSENSITIVE))
        {
            if (value.toUpperCase().equals(search.toUpperCase()))
            {
                return map;
            }
            else
            {
                return new LinkedHashMap<>();
            }
        }
        else if (type.equals(i_SEARCH_PARTIAL))
        {
            if (value.contains(search))
            {
                return map;
            }
            else
            {
                return new LinkedHashMap<>();
            }
        }
        else if (type.equals(i_SEARCH_PARTIAL_INSENSITIVE))
        {
            if (value.toUpperCase().contains(search.toUpperCase()))
            {
                return map;
            }
            else
            {
                return new LinkedHashMap<>();
            }
        }
        else
        {
            return new LinkedHashMap<>();
        }
    }

    /**
     * Search by full string search
     * @param key String
     * @param search String
     * @return Map&lt;String, Object&gt;
     */
    public Map<String, Object> getIteratorSearchFull(String key, String search)
    {
        return getIteratorSearch(i_SEARCH_FULL, key, search);
    }

    /**
     * Search by full case insensitive search
     * @param key String
     * @param search String
     * @return Map&lt;String, Object&gt;
     */
    public Map<String, Object> getIteratorSearchFullCaseInsensitive(String key, String search)
    {
        return getIteratorSearch(i_SEARCH_FULL_INSENSITIVE, key, search);
    }

    /**
     * Search by partial string search
     * @param key String
     * @param search String
     * @return Map&lt;String, Object&gt;
     */
    public Map<String, Object> getIteratorSearchPartial(String key, String search)
    {
        return getIteratorSearch(i_SEARCH_PARTIAL, key, search);
    }

    /**
     * Search by partial case insensitive search
     * @param key String
     * @param search String
     * @return Map&lt;String, Object&gt;
     */
    public Map<String, Object> getIteratorSearchPartialCaseInsensitive(String key, String search)
    {
        return getIteratorSearch(i_SEARCH_PARTIAL_INSENSITIVE, key, search);
    }

    /**
     * Reset iterator's starting position
     */
    public void resetIteratorNext() {
        i_iteratorPosition = 0;
    }
    
    /**
     * Returns a List of data for supplied column
     * @param column String
     * @return List&lt;Object&gt;
     */
    public List<Object> getColumnData(String column)
    {
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < i_gridList.size(); i++) {
            Map<String, Object> map = i_gridList.get(i);
            Object value = map.get(column);
            list.add(value);
        }
        return list;
    }

    /**
     * Return a List of Lists for specified number of row
     * @param rowsToOutput int
     * @return List&lt;List&lt;Object&gt;&gt;
     */
    public List<List<Object>> getListOfLists(int rowsToOutput)
    {
        List<List<Object>>  gridListOfLists = new ArrayList<>();
        List<Object> columnList = new ArrayList<>();

        columnList.add(i_COLUMN_ROW);
        for (int i = 0; i < i_gridColumns.size(); i++)
        {
            String column   = i_gridColumns.get(i);
            columnList.add(column);
        }
        gridListOfLists.add(columnList);

        for (int i = 0; i < i_gridList.size(); i++)
        {

            int index = i;
            if (index < rowsToOutput)
            {
                Map<String, Object> row = i_gridList.get(index);
                List<Object> rowList = new ArrayList<>();
                rowList.add(index);
                for (int j = 0; j < i_gridColumns.size(); j++)
                {
                    String column   = i_gridColumns.get(j);
                    Object data     = row.get(column);
                    rowList.add(data);
                }
                gridListOfLists.add(rowList);
            }
        }
        return gridListOfLists;
    }

    /**
     * Return a List of Lists for each row
     * @return List&lt;List&lt;Object&gt;&gt;
     */
    public List<List<Object>> getListOfLists()
    {
        return getListOfLists(getTotalRowCount());
    }

    /**
     * Generate an array containing the largest sized row for each column
     * @param gridListOfLists List&lt;List&lt;Object&gt;&gt;
     * @return int&#91;&#93;
     */
    private int[] getSizeArray(List<List<Object>> gridListOfLists)
    {
        int[] sizes = new int[i_gridColumns.size()+1];
        for (List<Object> row : gridListOfLists) {
            for (int i = 0; i < row.size(); i++)
            {
                Object  item    = row.get(i);
                int     index   = i;
                if (item != null)
                {
                    int length  = item.toString().length();
                    if (sizes[index] < length)
                    {
                        sizes[index] = length;
                    }
                }
            }
        }
        return sizes;
    }

    /**
     * Builds formatted row dividers for toString output
     * @param sizes int&#91;&#93;
     */
    private void buildRowSeperator(int[] sizes)
    {
        i_stringBuilder.append(i_OUTPUT_PLUS);
        for (int i = 0; i < sizes.length; i++) {
            for (int j = 0; j < sizes[i]; j++) {
                i_stringBuilder.append(i_OUTPUT_DASH);
            }
            i_stringBuilder.append(i_OUTPUT_DASH + i_OUTPUT_DASH);
            if (i < sizes.length-1)
            {
                i_stringBuilder.append(i_OUTPUT_PLUS);
            }
        }
        i_stringBuilder.append(i_OUTPUT_PLUS);
        i_stringBuilder.append(i_NEW_LINE);
    }

    /**
     * Builds formatted output of each row for toString output
     * @param row List&lt;Object&gt;
     * @param sizes int&#91;&#93;
     */
    private void buildRow(List<Object> row, int[] sizes)
    {
        i_stringBuilder.append(i_OUTPUT_PIPE);
        for (int i = 0; i < row.size(); i++)
        {
            int index = i;
            Object item = row.get(index);
            int itemLength = 0;
            if (item != null)
            {
                itemLength = item.toString().length();
            }
            int difference;
            String spaces = i_OUTPUT_SPACE_EMPTY;
            if (itemLength < sizes[index])
            {
                difference = sizes[index] - itemLength;
                for (int j = 0; j < difference+1; j++) {
                    spaces += i_OUTPUT_SPACE;
                }
            }
            else
            {
                spaces = i_OUTPUT_SPACE;
            }
            if (item != null)
            {
                i_stringBuilder.append(spaces + item + i_OUTPUT_SPACE);
            }
            else
            {
                i_stringBuilder.append(spaces + i_OUTPUT_SPACE);
            }
            if (index < row.size()-1)
            {
                i_stringBuilder.append(i_OUTPUT_PIPE);
            }
        }
        i_stringBuilder.append(i_OUTPUT_PIPE);
        i_stringBuilder.append(i_NEW_LINE);
    }

    /**
     * Outputs formatted grid with supplied number of rows
     * @param rowsToOutput int
     * @return String
     */
    public String toString(int rowsToOutput)
    {
        if (i_gridColumns.size() == 0)
        {
            int length = i_OUTPUT_NO_RESULTS.length() + 2;
            i_stringBuilder.append(i_OUTPUT_PLUS);
            for (int i = 0; i < length; i++) {
                i_stringBuilder.append(i_OUTPUT_DASH);
            }
            i_stringBuilder.append(i_OUTPUT_PLUS);
            i_stringBuilder.append(i_NEW_LINE);
            
            i_stringBuilder.append(i_OUTPUT_PIPE + i_OUTPUT_SPACE + i_OUTPUT_NO_RESULTS + i_OUTPUT_SPACE + i_OUTPUT_PIPE);
            i_stringBuilder.append(i_NEW_LINE);
            
            i_stringBuilder.append(i_OUTPUT_PLUS);
            for (int i = 0; i < length; i++) {
                i_stringBuilder.append(i_OUTPUT_DASH);
            }
            i_stringBuilder.append(i_OUTPUT_PLUS);
        }
        else
        {
            List<List<Object>>  gridListOfLists = getListOfLists(rowsToOutput);
            int[] sizes = getSizeArray(gridListOfLists);

            buildRowSeperator(sizes);
            for (List<Object> row : gridListOfLists) {
                buildRow(row, sizes);
                buildRowSeperator(sizes);
            }
            i_stringBuilder.append(gridListOfLists.size() - 1 + " of " + i_gridList.size());
        }

        String export = i_stringBuilder.toString();
        // Clear
        i_stringBuilder.setLength(0);
        return export;

    }

    /**
     * Outputs formatted grid with a default of 100 rows
     */
    public String toString()
    {
        return toString(100);
    }
}
