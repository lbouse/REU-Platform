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

public class DirectHouseSearch implements HouseSearch {

    List<HouseInfo> directHouses = new ArrayList<HouseInfo>();

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


//                    System.out.println("DH1"+node.toPlainTextString());
//                    System.out.println("DH2"+node.toString());
//                    System.out.println("DH3"+node.getText());
//                    System.out.println("DH4"+node.toHtml());
//                    System.out.println("DH5"+node);
                    
//                    System.out.println("1" + tableRowTag.toPlainTextString());
                    
//                    System.out.println("-2" + tableRowTag.childAt(3).toPlainTextString());
//                    System.out.println("-1" + tableRowTag.childAt(3).getFirstChild().toPlainTextString());
//                    System.out.println("0" + tableRowTag.childAt(3).getFirstChild().getNextSibling().toPlainTextString());
//                    System.out.println("??????????????????????????????????????????????????????????????????????????????????????????????");
//                    System.out.println("1" + tableRowTag.childAt(5).toPlainTextString());
//                    System.out.println("2" + tableRowTag.childAt(5).getFirstChild().toPlainTextString());
//                    System.out.println("3" + tableRowTag.childAt(5).getFirstChild().getNextSibling().toPlainTextString());
//                    System.out.println("4" + tableRowTag.childAt(5).getFirstChild().getNextSibling().getNextSibling().toPlainTextString());
//                    System.out.println("5" + tableRowTag.childAt(5).getFirstChild().getNextSibling().getNextSibling().getNextSibling().toPlainTextString());
//                    System.out.println("6" + tableRowTag.childAt(5).getLastChild().toPlainTextString());
//                    System.out.println("7" + tableRowTag.childAt(5).getLastChild().getPreviousSibling().toPlainTextString());
//                    System.out.println("8" + tableRowTag.childAt(5).getLastChild().getPreviousSibling().getPreviousSibling().toPlainTextString());

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

    public void searchHouses(HashMap<String, String> searchKeys) {
        QueryStringFormatter formatter = new QueryStringFormatter("http://www.directhomes.com/searchResults.cfm");

        try {
            formatter.addQuery("city", "");
            formatter.addQuery("state", "");

            if (searchKeys.get(HouseSearch.ZIP_CODE) != null) {
                formatter.addQuery("zip", (String) searchKeys.get(HouseSearch.ZIP_CODE));
            }

            if (searchKeys.get(HouseSearch.MIN_BED) != null) {
                formatter.addQuery("beds", (String) searchKeys.get(HouseSearch.MIN_BED));
            } else {
                formatter.addQuery("beds", "");
            }

            if (searchKeys.get(HouseSearch.MIN_BATH) != null) {
                formatter.addQuery("baths", (String) searchKeys.get(HouseSearch.MIN_BATH));
            } else {
                formatter.addQuery("baths", "");
            }

            if (searchKeys.get(HouseSearch.MAX_PRICE) != null) {
                formatter.addQuery("Max_price", (String) searchKeys.get(HouseSearch.MAX_PRICE));
            }

            if (searchKeys.get(HouseSearch.MIN_PRICE) != null) {
                formatter.addQuery("Min_price", (String) searchKeys.get(HouseSearch.MIN_PRICE));
            }

            formatter.addQuery("propertyType", "Real-Estate");

            String finalQueryString = "http://www.directhomes.com/searchResults.cfm" + formatter.getQueryString();

            System.out.printf("%s", finalQueryString);

//            processMyNodes(finalQueryString);
            
            Parser parser = new Parser(finalQueryString);

//            int count = 0;
//            System.out.println("DIRECTHOUSESEARCH");
            for (NodeIterator i = parser.elements(); i.hasMoreNodes();) {
                processMyNodes(i.nextNode());
//                System.out.println("COUNT COUNT COUNT = " + count);
//                count++;
            }
            
//            processMyNodes(finalQueryString);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void processMyNodes(String finalQueryString) throws ParserException {
        try {
            Parser parser = new Parser(finalQueryString);
            NodeFilter addressFilter = new HasAttributeFilter("class", "mainInfo");
            NodeList addressList = parser.parse(addressFilter);
            addressList = addressList.extractAllNodesThatMatch(addressFilter);

            Parser priceParser = new Parser(finalQueryString);
            NodeFilter priceFilter = new HasAttributeFilter("class", "subInfo");
            NodeList priceList = priceParser.parse(priceFilter);
            priceList = priceList.extractAllNodesThatMatch(priceFilter);

            System.out.println("");
            System.out.println("Processing Direct Nodes");
            Scanner dummy;
            for (int i = 0; i < addressList.size(); i++) {
//                System.out.println(""+i+" = " + addressList.elementAt(i).toPlainTextString());
                Scanner scan = new Scanner(addressList.elementAt(i).toPlainTextString());
                ArrayList list = new ArrayList();
                while(scan.hasNextLine()){
                    String str = scan.nextLine();
//                    System.out.println(" * " + str);
                    if(str.trim().length()>0){
                        dummy = new Scanner(str.trim());
                        str = "";
                        while(dummy.hasNext())
                            str += dummy.next()+" ";
                        list.add(str.trim());
                    }
                        
                }
                for(int z = 0; z < list.size(); z++){
                    System.out.println(" 1 " + list.get(z));
                }
                
//                System.out.println(""+i+" = " + priceList.elementAt(i).toPlainTextString());   ^[ \t]+|[ \t]+$
                Scanner scan2 = new Scanner(priceList.elementAt(i).toPlainTextString());
                ArrayList list2 = new ArrayList();
                while(scan2.hasNextLine()){
                    String str = scan2.nextLine();
//                    System.out.println(" ** " + scan2.next());
                    if(str.trim().length()>0){
                        dummy = new Scanner(str.trim());
                        str = "";
                        while(dummy.hasNext())
                            str += dummy.next()+" ";
                        list2.add(str.trim());
                    }
                }
                for(int z = 0; z < list2.size(); z++){
                    System.out.println(" 2 " + list2.get(z));
                }
            }
            
        } catch (ParserException e) {
            e.printStackTrace();
        }

    }
    
    public List<HouseInfo> getHouses() {
        return directHouses;
    }

    public static void main(String[] args) {
        DirectHouseSearch directSearch = new DirectHouseSearch();
        HashMap<String, String> searchKeys = new HashMap();
        directSearch.searchHouses(searchKeys);
    }
}
