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

public class HomeHouseSearch implements HouseSearch {

    List<HouseInfo> realtorHouses = new ArrayList<HouseInfo>();

    public void processMyNodes(String finalQueryString) throws ParserException {
        try {
            Parser parser = new Parser(finalQueryString);
            NodeFilter addressFilter = new HasAttributeFilter("class", "address");
            NodeList addressList = parser.parse(addressFilter);
            addressList = addressList.extractAllNodesThatMatch(addressFilter);

            Parser priceParser = new Parser(finalQueryString);
            NodeFilter priceFilter = new HasAttributeFilter("class", "price");
            NodeList priceList = priceParser.parse(priceFilter);
            priceList = priceList.extractAllNodesThatMatch(priceFilter);

            Parser bedParser = new Parser(finalQueryString);
            NodeFilter bedFilter = new HasAttributeFilter("class", "beds");
            NodeList bedList = bedParser.parse(bedFilter);
            bedList = bedList.extractAllNodesThatMatch(bedFilter);

            Parser bathParser = new Parser(finalQueryString);
            NodeFilter bathFilter = new HasAttributeFilter("class", "baths");
            NodeList bathList = bathParser.parse(bathFilter);
            bathList = bathList.extractAllNodesThatMatch(bathFilter);

            Parser descriptParser = new Parser(finalQueryString);
            NodeFilter descriptFilter = new HasAttributeFilter("class", "descriptionText");
            NodeList descriptList = descriptParser.parse(descriptFilter);
            descriptList = descriptList.extractAllNodesThatMatch(descriptFilter);

            Parser typeParser = new Parser(finalQueryString);
            NodeFilter typeFilter = new HasAttributeFilter("class", "attributes");
            NodeList typeList = typeParser.parse(typeFilter);
            typeList = typeList.extractAllNodesThatMatch(typeFilter);
            
//            System.out.println("addressSize " + addressList.size());
//            System.out.println(" " + priceList.size());
//            System.out.println(" " + bedList.size());
//            System.out.println(" " + bathList.size());
//            System.out.println(" " + descriptList.size());
            
            for (int i = 0; i < addressList.size(); i++) {
                HouseInfo finderHouse = new HouseInfo("http://www.homefinder.com");
                finderHouse.setAddress(addressList.elementAt(i).toPlainTextString());
                finderHouse.setPrice(priceList.elementAt(i).toPlainTextString().trim());
                if ((i < bedList.size()) && (i < bathList.size())) {
                    finderHouse.setBedBath(bedList.elementAt(i).toPlainTextString().trim() + "\n" + bathList.elementAt(i).toPlainTextString().trim());
                }
                if (i < descriptList.size() - 1) {
                    finderHouse.setDescription(descriptList.elementAt(i).toPlainTextString().trim());
//                    System.out.println("5 " + descriptList.elementAt(i).toPlainTextString());
                }
                
//                System.out.println("1 " + addressList.elementAt(i).toPlainTextString());
//                System.out.println("2 " + priceList.elementAt(i).toPlainTextString());
//                System.out.println("3 " + bedList.elementAt(i).toPlainTextString());
//                System.out.println("4 " + bathList.elementAt(i).toPlainTextString());
                
                
                realtorHouses.add(finderHouse);
            }
            
            
            
        } catch (ParserException e) {
            e.printStackTrace();
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

                System.out.println("HERE 2");
                
                if (tag.getAttribute("class") != null && tag.getAttribute("class").equalsIgnoreCase("resultsBands ")) {
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

                    // <editor-fold defaultstate="collapsed" desc="Extra code">
                    
                     System.out.println("HHS2"+node.toPlainTextString());
//                    System.out.println("DH2"+node.toString());
//                    System.out.println("DH3"+node.getText());
//                    System.out.println("DH4"+node.toHtml());
//                    System.out.println("DH5"+node);
                    
                    System.out.println("2" + tableRowTag.toPlainTextString());
                    
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

//                    realtorHouses.add(directHouse);// </editor-fold>
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
            String queryEngine = "http://www.homefinder.com/zip-code/";
            String queryFinalString = queryEngine;

            if (searchKeys.get(HouseSearch.ZIP_CODE) != null) {
                queryFinalString = queryEngine + (String) searchKeys.get(HouseSearch.ZIP_CODE) + "/";
            }

            if (searchKeys.get(HouseSearch.MIN_PRICE) != null) {
                queryFinalString = queryFinalString + "min_price_" + (String) searchKeys.get(HouseSearch.MIN_PRICE) + "/";
            }

            if (searchKeys.get(HouseSearch.MAX_PRICE) != null) {
                queryFinalString = queryFinalString + "max_price_" + (String) searchKeys.get(HouseSearch.MAX_PRICE) + "/";
            }

            if (!searchKeys.get(HouseSearch.MIN_BED).equalsIgnoreCase("")) {
                queryFinalString = queryFinalString + "min_bed_" + (String) searchKeys.get(HouseSearch.MIN_BED) + "/";
            }

            if (!searchKeys.get(HouseSearch.MIN_BATH).equalsIgnoreCase("")) {
                queryFinalString = queryFinalString + "min_bath_" + (String) searchKeys.get(HouseSearch.MIN_BATH) + "/";
            }

            System.out.printf("%s\n", queryFinalString);
            
//            //extra
//            System.out.println("HOMEHOUSESEARCH");
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
        return realtorHouses;
    }

    public static void main(String[] args) {
        try {
            HomeHouseSearch testParser = new HomeHouseSearch();
            HashMap<String, String> searchKeys = new HashMap();
            testParser.searchHouses(searchKeys);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
