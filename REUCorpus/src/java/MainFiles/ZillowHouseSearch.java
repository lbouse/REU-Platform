package MainFiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.htmlparser.*;
import org.htmlparser.util.*;
import org.htmlparser.filters.*;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;

public class ZillowHouseSearch implements HouseSearch {

    List<HouseInfo> zillowHouses = new ArrayList<HouseInfo>();

    public void processMyNodes(String finalQueryString) {
        try {
            Parser addressParser = new Parser(finalQueryString);
            NodeFilter addressFilter = new HasAttributeFilter("class", "adr");
            NodeList addressList = addressParser.parse(addressFilter);
            addressList = addressList.extractAllNodesThatMatch(addressFilter);

            Parser priceParser = new Parser(finalQueryString);
            NodeFilter priceFilter = new HasAttributeFilter("class", "price");
            NodeList priceList = priceParser.parse(priceFilter);
            priceList = priceList.extractAllNodesThatMatch(priceFilter);

            Parser paymentParser = new Parser(finalQueryString);
            NodeFilter paymentFilter = new HasAttributeFilter("class", "major");
            NodeList paymentList = paymentParser.parse(paymentFilter);
            paymentList = paymentList.extractAllNodesThatMatch(paymentFilter);

            Parser bedBathParser = new Parser(finalQueryString);
            NodeFilter bedBathFilter = new HasAttributeFilter("class", "prop-cola");
            NodeList bedBathList = bedBathParser.parse(bedBathFilter);
            bedBathList = bedBathList.extractAllNodesThatMatch(bedBathFilter);

            Parser squareFeetParser = new Parser(finalQueryString);
            NodeFilter squareFeetFilter = new HasAttributeFilter("class", "prop-colb");
            NodeList squareFeetList = squareFeetParser.parse(squareFeetFilter);
            squareFeetList = squareFeetList.extractAllNodesThatMatch(squareFeetFilter);

//            Parser descriptionParser = new Parser(finalQueryString);
//            NodeFilter descriptionFilter = new HasAttributeFilter("class", "describtion");        //This tag exists but they're all empty
//            NodeList descriptionList = descriptionParser.parse(descriptionFilter);
//            descriptionList = descriptionList.extractAllNodesThatMatch(descriptionFilter);
            
            for (int i = 0; i < addressList.size(); i++) {
                HouseInfo zillowHouse = new HouseInfo("http://www.zillow.com");
                zillowHouse.setAddress(addressList.elementAt(i).toPlainTextString());
                zillowHouse.setPrice(priceList.elementAt(i).toPlainTextString());
                zillowHouse.setBedBath(bedBathList.elementAt(i).getFirstChild().toPlainTextString() + "\n"
                        + bedBathList.elementAt(i).getFirstChild().getNextSibling().getNextSibling().toPlainTextString().trim());

                if (i < paymentList.size() - 1) {
                    zillowHouse.setMonthlyPayment(paymentList.elementAt(i).toPlainTextString().trim());
                }
                zillowHouse.setSquare(squareFeetList.elementAt(i).getFirstChild().toPlainTextString());
//                System.out.println("square = " + squareFeetList.elementAt(i).toPlainTextString());                    //It really does look all jumbled up
//                zillowHouse.setDescription(descriptionList.elementAt(i).getFirstChild().toPlainTextString().trim());

                zillowHouses.add(zillowHouse);
            }
        } catch (ParserException pe) {
            pe.printStackTrace();
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

                System.out.println("HERE 3");
                
                if (tag.getAttribute("class") != null && tag.getAttribute("class").equalsIgnoreCase("property-info")) {
//                    HouseInfo directHouse = new HouseInfo("http://www.directhomes.com");
//
//                    Node thirdChildNode = tableRowTag.childAt(3).getFirstChild().getNextSibling();
//                    Node addressNode = thirdChildNode.getFirstChild().getNextSibling();
//                    directHouse.setAddress(addressNode.toPlainTextString().trim());
//
//                    Node descriptionNode = tableRowTag.childAt(3).getLastChild();
//                    directHouse.setDescription(descriptionNode.toPlainTextString());
//
//                    Node priceNode = tableRowTag.childAt(5).getFirstChild().getNextSibling();
//                    directHouse.setPrice(priceNode.toPlainTextString().trim());
//
//                    Node bedBathNode = tableRowTag.childAt(5).getLastChild().getPreviousSibling().getPreviousSibling();
//                    directHouse.setBedBath(bedBathNode.toPlainTextString().trim());

                    System.out.println("ZHS3"+node.toPlainTextString());
//                    System.out.println("DH2"+node.toString());
//                    System.out.println("DH3"+node.getText());
//                    System.out.println("DH4"+node.toHtml());
//                    System.out.println("DH5"+node);
                    
                    System.out.println("3" + tableRowTag.toPlainTextString());
                    
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

//                    realtorHouses.add(directHouse);
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
        try {
            String queryEngine = "http://www.zillow.com/homes/";
            String queryFinalString = queryEngine;

            if (searchKeys.get(HouseSearch.ZIP_CODE) != null) {
                queryFinalString = queryEngine + (String) searchKeys.get(HouseSearch.ZIP_CODE) + "_rb/";
            }

            if (!searchKeys.get(HouseSearch.MIN_PRICE).equalsIgnoreCase("") || !searchKeys.get(HouseSearch.MAX_PRICE).equalsIgnoreCase("")) {
                if (!searchKeys.get(HouseSearch.MIN_PRICE).equalsIgnoreCase("")) {
                    queryFinalString = queryFinalString + (String) searchKeys.get(HouseSearch.MIN_PRICE) + "-";

                    if (!searchKeys.get(HouseSearch.MAX_PRICE).equalsIgnoreCase("")) {
                        queryFinalString = queryFinalString + (String) searchKeys.get(HouseSearch.MAX_PRICE);
                    }
                } else if (searchKeys.get(HouseSearch.MIN_PRICE).equalsIgnoreCase("") && !searchKeys.get(HouseSearch.MAX_PRICE).equalsIgnoreCase("")) {
                    queryFinalString = queryFinalString + "0-" + (String) searchKeys.get(HouseSearch.MAX_PRICE);
                }
                queryFinalString = queryFinalString + "_price/";
            }

            if (!searchKeys.get(HouseSearch.MIN_BED).equalsIgnoreCase("")) {
                queryFinalString = queryFinalString + (String) searchKeys.get(HouseSearch.MIN_BED) + "-_beds/";
            }

            if (!searchKeys.get(HouseSearch.MIN_BATH).equalsIgnoreCase("")) {
                queryFinalString = queryFinalString + (String) searchKeys.get(HouseSearch.MIN_BATH) + "-_baths/";
            }

            System.out.printf("%s\n", queryFinalString);
            
//            //extra
//            System.out.println("ZillowHouseSearch");
//            Parser parser = new Parser(queryFinalString);
//            int count = 0;
//            for (NodeIterator i = parser.elements(); i.hasMoreNodes();) {
//                processMyNodes(i.nextNode());
//                System.out.println("COUNT COUNT COUNT = " + count);
//                count++;
//            }
            
            processMyNodes(queryFinalString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void processMyNodes2(Node node) {
                    System.out.println("HHS1"+node.toPlainTextString());
    }
     
    
    public List<HouseInfo> getHouses() {
        return zillowHouses;
    }

    public static void main(String[] args) {
        ZillowHouseSearch zillowSearch = new ZillowHouseSearch();
        HashMap<String, String> searchKeys = new HashMap<String, String>();
        zillowSearch.searchHouses(searchKeys);
    }
}
