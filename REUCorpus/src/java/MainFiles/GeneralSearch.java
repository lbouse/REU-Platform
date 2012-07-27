/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MainFiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/**
 *
 * @author josealvarado
 */
public class GeneralSearch {

    private List<HouseInfo> directHouses = new ArrayList<HouseInfo>();
    private String finalQueryString;
    private ArrayList classNames = new ArrayList(), parseInfo = new ArrayList();
    private ArrayList<DataClass> listOfData = new ArrayList<DataClass>();
    private OnlineDatabase onlineDatabase = new OnlineDatabase();
    private boolean debug = true;

    /*
     * Default Constructor
     */
    public GeneralSearch() {
    }

    public GeneralSearch(String finalQueryString, ArrayList classNames, ArrayList parseInfo) {
        this.finalQueryString = finalQueryString;
        this.classNames = classNames;
        this.parseInfo = parseInfo;
        this.onlineDatabase.setName(finalQueryString);
    }
    
    public String toString(){
        String returnMe = "";
        returnMe += "\nQ = " + finalQueryString;
        returnMe += "\nN = " + toStringArrayList(classNames);
        returnMe += "\nP = " + toStringArrayList(parseInfo);
        return returnMe;
    }

    /*
     * Gets information from online database
     */
    public void processNodes() throws ParserException {
        try {
            for (int i = 0; i < classNames.size(); i++) {
                processNodesHelper("" + classNames.get(i));
            }
        } catch (ParserException e) {
            e.printStackTrace();
        }
    }

    /*
     * Gets information for given className
     */
    public void processNodesHelper(String className) throws ParserException {
        Parser parser = new Parser(finalQueryString);
        NodeFilter addressFilter = new HasAttributeFilter("class", className);
        NodeList addressList = parser.parse(addressFilter);
        addressList = addressList.extractAllNodesThatMatch(addressFilter);

        Scanner dummy;
        DataClass list = new DataClass(className);
        for (int i = 0; i < addressList.size(); i++) {
            Scanner scan = new Scanner(addressList.elementAt(i).toPlainTextString());
            int counter = 0;
            while (scan.hasNextLine()) {
                String str = scan.nextLine();
                if (str.trim().length() > 0) {
                    dummy = new Scanner(str.trim());
                    str = "";
                    while (dummy.hasNext()) {
                        str += dummy.next() + " ";
                    }
                    list.add(str.trim());           //this adds one line
                    counter++;
                }

            }
            list.addNewSize(counter);
        }
        listOfData.add(list);
    }

    /*
     * Parses the information it processed
     */
    public void parseNodes() {
        for (int i = 0; i < listOfData.size(); i++) {
            DataClass dataClass = (DataClass) listOfData.get(i);
            ArrayList<String> parseInfo2 = getMatchingParseData(dataClass.getName());
            System.out.println("Mathcing parse info for " + dataClass.getName() + " : " + parseInfo2);

            parseSpecialCommands(dataClass, parseInfo2);
            System.out.println("After special " + i + " = " + parseInfo2);
            applyParserOn(dataClass, parseInfo2);
        }
    }

    /*
     * Takes care of any special commands
     */
    public void parseSpecialCommands(DataClass dataClass, ArrayList<String> parseData) {
        ArrayList<Integer> sizes = dataClass.getDataSizes();                                        //gets the sizes of each query
        for (int i = 0; i < parseData.size(); i++) {                                                //loops through parse commnads looking for special commands
            String[] elements = parseData.get(i).split(" ");
            if (elements[0].equals("IF")) {                                                         //currently it only supports IF
                if ((elements[1]).equals("LINES")) {                                                //currently it only supports LINES
                    //currently it only supports >, so element[2] is always >
                    int lines = Integer.parseInt("" + elements[3]);
                    int total = 0;                                                                  //keeps track of where you are on dataClass
                    for (int j = 0; j < sizes.size(); j++) {
                        if (sizes.get(j) > lines) {
                            if ((elements[4]).equals("COMBINE")) {                                  //currently it only supports COMBINE with 2 parameters
                                int first = Integer.parseInt("" + elements[5]) - 1;
                                int second = Integer.parseInt("" + elements[6]) - 1;
                                dataClass.set(total + first, "" + dataClass.get(total + first) + " " + dataClass.get(total + second));      //does the combining
                                dataClass.set(total + second, "DELETE LINE");                       //does not deltete the combined line, replaces it with DELETE LINE, and then it's deleted after looping through sizes
                                total += sizes.get(j);
                                sizes.set(j, sizes.get(j) - 1);                                     //makes sizes smaller
                            }
                        } else {
                            total += sizes.get(j);
                        }
                    }
                }

            } else {
                break;                                                                              //breaks immediatly after not finding a special command
            }
            dataClass.removeDELETELINE();
//            System.out.println("i = "+ i + " "+dataClass);
        }
        for (int i = 0; i < parseData.size(); i++) {                                                //removes special commands in parseData
            if (parseData.get(i).charAt(0) != '[') {
                parseData.remove(i);
                i--;
            }
        }
    }

    /*
     * Applies non special commands on dataClass, probably the most consufing 
     */
    public void applyParserOn(DataClass dataClass, ArrayList parseInfo2) {
        System.out.println("dataClass ? " + dataClass);
        for (int i = 0; i < dataClass.size(); i++) {                                //assumes very line is a simple [ ] line command
            String data = (String) (dataClass.get(i));                              //raw string, unparsed
            String[] params = ((String) (parseInfo2.get(i % parseInfo2.size()))).split(" ");                     //parseInfo list into seperate components, and it gets parseInfo for the line its in, handles more than one line of code
            String[] pData = parseAgain(data, params);                              //data that needs to be saved, split into its components
            String[] variableNames = getVariableNames(params);                      //name of the variables for each of those components
            for (int j = 0; j < pData.length; j++) {
                if (i / parseInfo2.size() >= onlineDatabase.size()) {                                     //if a Source Data doesn't exists create it
                    SourceData sD = new SourceData();
                    sD.save(variableNames[j], "" + pData[j]);
                    onlineDatabase.add(sD);
                } else {                                                              //if a Source Data does exists use it
                    SourceData sD = (SourceData) (onlineDatabase.get(i / parseInfo2.size()));
                    sD.save(variableNames[j], "" + pData[j]);
                }
            }
        }
    }

    /*
     * Gets variable names in command
     */
    public String[] getVariableNames(String[] params) {
        ArrayList list = new ArrayList();
        for (int i = 0; i < params.length; i++) {
            int count1 = 0;
            int count2 = 0;
            boolean check = false;
            if (params[i].contains("$")) {
                String str = params[i];
                for (int k = 0; k < str.length(); k++) {
                    if (str.charAt(k) == '$') {
                        count1 = k;
                        check = true;
                    }
                    if (check) {
                        count2++;
                    }
                }
                list.add(str.substring(count1 + 1, count1 + count2));
            }
        }
        String[] returnMe = new String[list.size()];
        for (int j = 0; j < list.size(); j++) {
            returnMe[j] = (String) (list.get(j));
        }
        return returnMe;
    }

    /*
     * parses dataString given the parameters, returning the information that needs to be saved
     */
    public String[] parseAgain(String dataString, String[] params) {
        ArrayList temp = new ArrayList();
        String ds = dataString;
        for (int i = 0; i < params.length; i++) {
            String piece = params[i];
            if (piece.charAt(0) == '\"') {
                String pieceInQuotes = pieceInQuotes(piece);
                String[] nut = ds.split(pieceInQuotes, 2);
                temp.add(nut[0]);
                System.out.println("NUT "+ toStringArray(nut));
                ds = pieceInQuotes + nut[1];                                        //makes sure to add the last piece
            }
            else if( piece.charAt(0) == '\\'){
                
            }
        }
        temp.add(ds);
        String[] returnMe = new String[temp.size()];
        for (int j = 0; j < temp.size(); j++) {
            returnMe[j] = (String) (temp.get(j));
        }
        return returnMe;
    }

    /*
     * return the piece in quotes
     */
    public String pieceInQuotes(String string) {                         //gets the positions of all the quotes
        ArrayList list = new ArrayList();
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == '\"') {
                list.add(i);
            }
        }
        return string.substring(Integer.parseInt("" + list.get(0)) + 1, Integer.parseInt("" + list.get(1)));
    }

    /*
     * prints global data in console
     */
    public void printGlobalData() {
        for (int i = 0; i < onlineDatabase.size(); i++) {
            System.out.println("Entry " + i);
            System.out.println("" + onlineDatabase.get(i));
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Useful toString functions">
    public String toStringArray(String[] params) {
        if (params.length == 0) {
            return null;
        }
        String returnMe = "";
        for (int i = 0; i < params.length; i++) {
            returnMe +=  " { "+params[i] + " } ";
        }
        return returnMe;
    }

    public String toStringArrayList(ArrayList list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        String returnMe = "";
        for (int i = 0; i < list.size(); i++) {
            returnMe += list.get(i) + " ";
        }
        return returnMe.trim();
    }

    // </editor-fold>
    
    public void displayNodes() {
        for (int i = 0; i < listOfData.size(); i++) {
            System.out.println("" + listOfData.get(i));
        }
    }
    
    private ArrayList<String> getMatchingParseData(String str) {
        ArrayList<String> list = new ArrayList();
        boolean record = false;
        for (int i = 0; i < parseInfo.size(); i++) {
            String element = (String) (parseInfo.get(i));
            Scanner scan = new Scanner(element);
            String potKey = scan.next();

            if (potKey.equals("#PATTERN")) {
                if (element.equals("#PATTERN " + str)) {
                    record = true;
                } else {
                    record = false;
                }
            } else {
                if (record) {
                    list.add(element);
                }
            }
        }
        return list;
    }

    // <editor-fold defaultstate="collapsed" desc="Get Functions">
    public List<HouseInfo> getHouses() {
        return directHouses;
    }

    public void setFinalQueryString(String finalQueryString) {
        this.finalQueryString = finalQueryString;
    }

    public String getFinalQueryString() {
        return finalQueryString;
    }

    public void setClassNames(ArrayList classNames) {
        this.classNames = classNames;
    }

    public ArrayList getClassNames() {
        return classNames;
    }

    public OnlineDatabase getOnlineDatabase() {
        return onlineDatabase;
    }
    // </editor-fold>
}