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
    private ArrayList classNames = new ArrayList(), parseInfo = new ArrayList(), listOfData = new ArrayList();
    private OnlineDatabase onlineDatabase = new OnlineDatabase();

    public GeneralSearch(){
    }
    
    public GeneralSearch(String finalQueryString, ArrayList classNames, ArrayList parseInfo) {
        this.finalQueryString = finalQueryString;
        this.classNames = classNames;
        this.parseInfo = parseInfo;
        this.onlineDatabase.name = this.finalQueryString;
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
            
            while (scan.hasNextLine()) {
                String str = scan.nextLine();
                if (str.trim().length() > 0) {
                    dummy = new Scanner(str.trim());
                    str = "";
                    while (dummy.hasNext()) {
                        str += dummy.next() + " ";
                    }
                    list.add(str.trim());
                }

            }
        }
        listOfData.add(list);
    }

    public void processMyNodes() throws ParserException {
        try {
            for(int i = 0; i <  classNames.size(); i++){
                processClass(""+classNames.get(i));
            }
        } catch (ParserException e) {
            e.printStackTrace();
        }
    }
    
    public void printGlobalData(){
        System.out.println("Global Data Size " + onlineDatabase.size());
        for(int i = 0; i < onlineDatabase.size(); i++){
            System.out.println("Entry " + i);
            System.out.println(""+onlineDatabase.get(i));
        }
    }
    
    public void parseNodes(){
        for(int i = 0; i < listOfData.size(); i++){
            DataClass dataClass = (DataClass)listOfData.get(i);
            ArrayList parseInfo2 = getMatchingParseData(dataClass.getName());
            System.out.println("Mathcing parse info for "+dataClass.getName()+" : " + parseInfo2);
            applyParserOn(dataClass, parseInfo2);
        }
    }
    
    public void applyParserOn(DataClass dataClass, ArrayList parseInfo2){
//        System.out.println(" parseInfo size = " + parseInfo2.size());
        if(parseInfo2.size()==1){                                               //one line of parsing data per dataClass
            String[] params = ((String)(parseInfo2.get(0))).split(" ");                     //parseInfo list into seperate components
//            System.out.println("parseInfo as array " + toStringArray(params));
            if(params[0].equals("[")){                                          //[   ] - implies a simple line command
                if(params.length==3){                                           //if this is true, a command looks like this [ $name ] - assign everything to one DataClass
                    String nameOfNewData = params[1];
                    nameOfNewData = nameOfNewData.substring(1);                 //gets rid of the $(money symbol) from the name
                    if(!nameOfNewData.equals("irrelevant")){
                        for(int i = 0; i < dataClass.size(); i++){
                            if(i >= onlineDatabase.size()){                                //if a Source Data doesn't exists create it
                                SourceData sD = new SourceData();
                                sD.save(nameOfNewData, ""+dataClass.get(i));
                                onlineDatabase.add(sD);
                            }else{                                                  //if a Source Data does exists use it
                                SourceData sD = (SourceData)(onlineDatabase.get(i));
                                sD.save(nameOfNewData, ""+dataClass.get(i));
                            }
                        }
                    }
                }
                else{                                                           //simple code with more than one parameter
                    for(int i = 0; i < dataClass.size(); i++){
                        String data = (String)(dataClass.get(i)); 
                        String[] pData = parseAgain(data, params);              //data that needs to be saved
                        String[] variableNames = getVariableNames(params);
                        for(int j = 0; j < pData.length; j++){
                            if(i >= onlineDatabase.size()){                                //if a Source Data doesn't exists create it
                                SourceData sD = new SourceData();
                                sD.save(variableNames[j], ""+pData[j]);
                                onlineDatabase.add(sD);
                            }else{                                                  //if a Source Data does exists use it
                                SourceData sD = (SourceData)(onlineDatabase.get(i));
                                sD.save(variableNames[j], ""+pData[j]);
                            }
                        }
                        
                    }
                }
            }
            else{                                                               // advanced command
                    
            }
        }
        else{                                                                   //more than one line of code
            
        }
        
    }
    
    public String[] getVariableNames(String[] params){
        ArrayList list = new ArrayList();
        for(int i = 0; i < params.length; i++){
            int count1 = 0; int count2 = 0;
            boolean check = false;
            if(params[i].contains("$")){
                String str = params[i];
                for(int k = 0; k < str.length(); k++){
                    if(str.charAt(k) == '$'){
                        count1 = k;
                        check = true;
                    }
                    if(check){
                        count2++;
                    }
                }
//                System.out.println("str = " + str + " count1 = "+ count1 + " count2 = " + count2);
                list.add(str.substring(count1+1, count1+count2));
            }
        }
        String[] returnMe = new String[list.size()];
        for(int j = 0; j < list.size(); j++){
            returnMe[j] = (String)(list.get(j));
        }
        return returnMe;
    }
    
    public String[] parseAgain(String dataString, String[] params){
        ArrayList temp = new ArrayList();
        String ds = dataString;
        for(int i = 0; i < params.length; i++){
            String piece = params[i];
            if(piece.charAt(0) == '\"'){
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
        for(int j = 0; j < temp.size(); j++){
            returnMe[j] = (String)(temp.get(j));
        }
        return returnMe;
    }
    
    public String pieceInQuotes(String string){                         //gets the positions of all the quotes
        ArrayList list = new ArrayList();
        for(int i = 0; i < string.length(); i++){
            if(string.charAt(i) == '\"'){
                list.add(i);
            }
        }
        return string.substring(Integer.parseInt(""+list.get(0))+1, Integer.parseInt(""+list.get(1)));
    }
    
    public String toStringArray(String[] params){
        String returnMe = "";
        for(int i = 0; i < params.length; i++){
            returnMe += " "+i+" = [ " + params[i] + " ]";
        }
        return returnMe;
    }
    
    public void displayNodes(){
        for(int i = 0; i < listOfData.size(); i++){
            System.out.println(""+listOfData.get(i));
        }
    }

    private ArrayList getMatchingParseData(String str){
        ArrayList list = new ArrayList();
        boolean record = false;
        for(int i = 0; i < parseInfo.size(); i++){
            String element = (String)(parseInfo.get(i));           
            Scanner scan = new Scanner(element);
            String potKey = scan.next();
             
            if(potKey.equals("#PATTERN")){
                if(element.equals("#PATTERN "+str)){
                    record = true;
                }
                else{
                    record = false;
                }
            }
            else{
                if(record){
                    list.add(element);
                }
            }
        }
        return list;
    }
    
    public List<HouseInfo> getHouses() {
        return directHouses;
    }
    
    public void setFinalQueryString(String finalQueryString){
        this.finalQueryString = finalQueryString;
    }
    
    public String getFinalQueryString(){
        return finalQueryString;
    }
    
    public void setClassNames(ArrayList classNames){
        this.classNames = classNames;
    }
    
    public ArrayList getClassNames(){
        return classNames;
    }
    
    public OnlineDatabase getOnlineDatabase(){
        return onlineDatabase;
    }
}
