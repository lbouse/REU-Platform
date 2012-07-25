/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MainFiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeIterator;
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
    boolean debug = true;

    public GeneralSearch() {
    }

    public GeneralSearch(String finalQueryString, ArrayList classNames, ArrayList parseInfo) {
        this.finalQueryString = finalQueryString;
        this.classNames = classNames;
        this.parseInfo = parseInfo;
        this.onlineDatabase.setName(finalQueryString);
        if (debug) {
            System.out.println("finalQueryString  = " + finalQueryString);
            System.out.println("classNames = " + toStringArrayList(classNames));
            System.out.println("parseInfo = " + toStringArrayList(parseInfo));
        }
    }

    public void processMyNodes(Node node) {
        if (node instanceof TagNode) {
            TagNode tag = (TagNode) node;

            if (node instanceof TableTag) {
                TableTag tableTag = (TableTag) node;
            }
            if (node instanceof TableRow) {
                TableRow tableRowTag = (TableRow) node;

                if (tag.getAttribute("class") != null && tag.getAttribute("class").equalsIgnoreCase("resultsListing")) {
                    HouseInfo directHouse = new HouseInfo("http://www.directhomes.com");

                    Node thirdChildNode = tableRowTag.childAt(3).getFirstChild().getNextSibling();
                    Node addressNode = thirdChildNode.getFirstChild().getNextSibling();
                    directHouse.setAddress(addressNode.toPlainTextString().trim());

                    Node descriptionNode = tableRowTag.childAt(3).getLastChild();
                    directHouse.setDescription(descriptionNode.toPlainTextString());

                    Node priceNode = tableRowTag.childAt(5).getFirstChild().getNextSibling();
                    directHouse.setPrice(priceNode.toPlainTextString().trim());

                    Node bedBathNode = tableRowTag.childAt(5).getLastChild().getPreviousSibling().getPreviousSibling();
                    directHouse.setBedBath(bedBathNode.toPlainTextString().trim());

                    directHouses.add(directHouse);
                }

            }

            NodeList nl = tag.getChildren();
            if (nl != null) {
                try {
                    for (NodeIterator i = nl.elements(); i.hasMoreNodes();) {
                        processMyNodes(i.nextNode());
                    }
                } catch (ParserException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void processClass(String className) throws ParserException {
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

    public void processMyNodes() throws ParserException {
        try {
            for (int i = 0; i < classNames.size(); i++) {
                processClass("" + classNames.get(i));
                if (debug) {
                    System.out.println("Processed " + classNames.get(i));
                    System.out.println("Sizes per className = " + listOfData.get(i).getDataSizes());
                }

            }
        } catch (ParserException e) {
            e.printStackTrace();
        }
    }

    public void printGlobalData() {
        if (debug) {
            System.out.println("Pringting Global Data");
            System.out.println("Global Data Size " + onlineDatabase.size());
        }
        for (int i = 0; i < onlineDatabase.size(); i++) {
            System.out.println("Entry " + i);
            System.out.println("" + onlineDatabase.get(i));
        }
    }

    public ArrayList parseSpecialCommands(DataClass dataClass, ArrayList<String> parseData) {
//        System.out.println(""+dataClass);
        ArrayList parsingTheParser = new ArrayList();
        ArrayList<Integer> sizes = dataClass.getDataSizes();
//        System.out.println(""+sizes);
        for (int i = 0; i < parseData.size(); i++) {
            String[] elements = parseData.get(i).split(" ");
            if (elements[0].equals("IF")) {
                if ((elements[1]).equals("LINES")) {
                    //currently only supports >, so element[2] always equals 
                    int lines = Integer.parseInt("" + elements[3]);
                    //check whether condition is true before proceedeing

                    int total = 0;
                    for (int j = 0; j < sizes.size(); j++) {
//                        total += sizes.get(j);
                        if (sizes.get(j) > lines) {                              //Not Done, find out the number of variables and divide by that
                            if ((elements[4]).equals("COMBINE")) {
                                //Let's start with only combining two
                                int first = Integer.parseInt("" + elements[5]) - 1;
                                int second = Integer.parseInt("" + elements[6]) - 1 ;
                                //now Loop through dataClass and combine Data
                               
//                                System.out.println("total = " + total + " first = " + first + " second = " + second);
                                dataClass.set(total + first, ""+dataClass.get(total + first) + " " + dataClass.get(total + second));
                                dataClass.set(total + second, "DELETE LINE");
                                total += sizes.get(j);
                                sizes.set(j, sizes.get(j) - 1);
                            }
                        } else{
                            total += sizes.get(j);
                        } 
//                        total += sizes.get(j);
                    }
                }

            } else {
                break;
            }
            DataClass real = new DataClass();
            for(int j = 0; j < dataClass.size(); j++){
                if(!dataClass.get(j).equals("DELETE LINE")){
                    real.add(dataClass.get(j));
                }
            }
            dataClass.replace(real);
            //
        // Go through dataclass and remove all DELETE LINEs
        //
        }
//        System.out.println(""+dataClass);
        for(int i = 0; i < parseData.size(); i++){
            if(parseData.get(i).charAt(0) != '['){
                parseData.remove(i);
                i--;
            }
        }
        System.out.println(""+parseData);
        return parsingTheParser;
    }

    public void parseNodes() {
        if (debug) {
            System.out.println("Parsing Nodes");
        }
        for (int i = 0; i < listOfData.size(); i++) {
            DataClass dataClass = (DataClass) listOfData.get(i);
            ArrayList<String> parseInfo2 = getMatchingParseData(dataClass.getName());
            System.out.println("Mathcing parse info for " + dataClass.getName() + " : " + parseInfo2);

            parseSpecialCommands(dataClass, parseInfo2);
            applyParserOn(dataClass, parseInfo2);
        }
    }

    public void applyParserOn(DataClass dataClass, ArrayList parseInfo2) {
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
//                System.out.println("str = " + str + " count1 = "+ count1 + " count2 = " + count2);
                list.add(str.substring(count1 + 1, count1 + count2));
            }
        }
        String[] returnMe = new String[list.size()];
        for (int j = 0; j < list.size(); j++) {
            returnMe[j] = (String) (list.get(j));
        }
        return returnMe;
    }

    public String[] parseAgain(String dataString, String[] params) {
        ArrayList temp = new ArrayList();
        String ds = dataString;
        for (int i = 0; i < params.length; i++) {
            String piece = params[i];
            if (piece.charAt(0) == '\"') {
                String pieceInQuotes = pieceInQuotes(piece);
//                System.out.println("pieceInQuotes = " + pieceInQuotes);
                String[] nut = ds.split(pieceInQuotes, 2);
//                System.out.println("nut = "+toStringArray(nut));
                temp.add(nut[0]);
                ds = pieceInQuotes + nut[1];
            }
        }
        temp.add(ds);
        String[] returnMe = new String[temp.size()];
        for (int j = 0; j < temp.size(); j++) {
            returnMe[j] = (String) (temp.get(j));
        }
        return returnMe;
    }

    public String pieceInQuotes(String string) {                         //gets the positions of all the quotes
        ArrayList list = new ArrayList();
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == '\"') {
                list.add(i);
            }
        }
        return string.substring(Integer.parseInt("" + list.get(0)) + 1, Integer.parseInt("" + list.get(1)));
    }

    public String toStringArray(String[] params) {
        String returnMe = "";
        for (int i = 0; i < params.length; i++) {
            returnMe += " " + i + " = [ " + params[i] + " ]";
        }
        return returnMe;
    }

    public String toStringArrayList(ArrayList list) {
        String returnMe = "";
        for (int i = 0; i < list.size(); i++) {
            returnMe += list.get(i) + " ";
        }
        return returnMe.trim();
    }

    public void displayNodes() {
        if (debug) {
            System.out.println("Displaying Nodes");
        }
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
}
