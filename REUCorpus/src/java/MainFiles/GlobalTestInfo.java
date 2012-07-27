/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MainFiles;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author josealvarado
 */
public class GlobalTestInfo {

    /*
     * Global variables that store the parsed input data
     */
    private ArrayList<String> globalUrls = new ArrayList<String>();
    private ArrayList<ArrayList> globalTreeNodeNames = new ArrayList<ArrayList>();
    private ArrayList<ArrayList> globalParsers = new ArrayList<ArrayList>();
    private int globalSize = 0;
    /*
     * Contains the selected local schema for each online database, size must
     * match globalSchema
     */
    ArrayList<ArrayList<String>> printKeys = new ArrayList<ArrayList<String>>();
    /*
     * Contains the selected global schema used to build the database, size must
     * match printKeys
     */
    ArrayList<String> globalSchema = new ArrayList<String>();

    /*
     * Default Constructor
     */
    public GlobalTestInfo() {
    }

    /*
     * Receives unparsed inputs from Hello.java and parses them into global parsed inputes
     */
    public void parseAllData(String unparsedGlobalWebsites, String unparsedGlobalTreeNodeNames, String unparsedGlobalParsers, String unparsedPrintKeys, String unparsedGlobalSchema) {

        String[] tempUrls = unparsedGlobalWebsites.split("END");
        for (int i = 0; i < tempUrls.length; i++) {
            globalUrls.add(tempUrls[i].trim());
        }
        globalSize = globalUrls.size();
        
        String[] tempTreeNodeNames = unparsedGlobalTreeNodeNames.split("END");
        for (int i = 0; i < tempTreeNodeNames.length; i++) {
            String[] temp = tempTreeNodeNames[i].split("EL");
            ArrayList temp2 = new ArrayList();
            for (int j = 0; j < temp.length; j++) {
                temp2.add(temp[j].trim());
            }
            globalTreeNodeNames.add(temp2);
        }

        String[] tempParsing = unparsedGlobalParsers.split("END");
        for (int i = 0; i < tempParsing.length; i++) {
            String[] temp = tempParsing[i].split("EL");
            ArrayList temp2 = new ArrayList();
            for (int j = 0; j < temp.length; j++) {
                temp2.add(temp[j].trim());
            }
            globalParsers.add(temp2);
        }

        String[] tempPrintKeys = unparsedPrintKeys.split("END");
        for (int i = 0; i < tempPrintKeys.length; i++) {
            String[] temp = tempPrintKeys[i].split("EL");
            ArrayList temp2 = new ArrayList();
            for (int j = 0; j < temp.length; j++) {
                temp2.add(temp[j].trim());
            }
            printKeys.add(temp2);
        }
        
        String[] tempGlobalSchema = unparsedGlobalSchema.split("EL");
        for (int j = 0; j < tempGlobalSchema.length; j++) {
            globalSchema.add(tempGlobalSchema[j].trim());
        }
    }

    /*
     * Builds html table Requires global schema, and the corresponding schema
     * from each online database
     */
    public void printWebsite(OnlineDatabases oD, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        pw.println("<HTML><HEAD><TITLE>Search Results</TITLE></HEAD><BODY>");
        pw.println("<br>");

        if (!oD.isEmpty()) {
            pw.print("<table border=1px>");

            for (int i = 0; i < globalSchema.size(); i++) {
                pw.print("<th>" + globalSchema.get(i) + "</th>");
            }

            pw.print("</tr>");
            int i = 0;
            for (int index = 0; index < oD.size(); index++) {
                OnlineDatabase temp = oD.get(index);
                ArrayList<String> tempKeys = printKeys.get(index);
                System.out.println("" + temp.size());
                for (int j = 0; j < temp.size(); j++) {
                    if ((i++) % 2 == 0) {
                        pw.print("<tr BGCOLOR ='#fdf5e6'>");
                    } else {
                        pw.print("<tr BGCOLOR ='#c0c0c0'>");
                    }

                    for (int k = 0; k < tempKeys.size(); k++) {
                        pw.println("<td>" + temp.get(j).getValue(tempKeys.get(k)) + "</td>");
                    }
                    pw.println("</tr>");
                }

            }
            pw.print("</table>");

            pw.println("</BODY></HTML>");
            pw.close();

        } else {
            pw.println("Your queries do not return any results");
            pw.println("</BODY>");
            pw.println("</HTML>");

        }
    }

    // <editor-fold defaultstate="collapsed" desc="Code that configures a default query">
    /*
     * Sets a default query for three online databases Also sets up a default
     * global schema
     */
    public void setupDefaultQuery() {
        setupZHS();
        setupDHS();
        setupHHS();
        globalSchema.add("ADDRESS");
        globalSchema.add("PRICE");
        globalSchema.add("BEDS");
        globalSchema.add("BATHS");
        globalSchema.add("DESCRIPTION");
        globalSchema.add("IRRE");
    }

    /*
     * Default variables for Zillow, Direct, and Home
     */
    private String DHSQuery, HHSQuery, ZHSQuery;
    private ArrayList<String> DHSClassNames, HHSClassNames, ZHSClassNames;
    private ArrayList<String> DHSParser, HHSParser, ZHSParser;

    /*
     * Sets default values for Zillow
     */
    private void setupZHS() {
        ZHSQuery = "http://www.zillow.com/homes/97318_rb/#/homes/for_sale/Salem-OR-97317/399688_rid/1-_beds/1-_baths/500-200000_price/";
        ZHSClassNames = new ArrayList<String>();
        ZHSClassNames.add("adr");
        ZHSClassNames.add("price");
        ZHSClassNames.add("prop-cola");
        ZHSClassNames.add("prop-colb");
        ZHSParser = new ArrayList<String>();
        ZHSParser.add("#PATTERN adr");
        ZHSParser.add("[ $adr ]");
        ZHSParser.add("#PATTERN price");
        ZHSParser.add("[ $price ]");
        ZHSParser.add("#PATTERN prop-cola");
        ZHSParser.add("[ $beds \"Baths:\"-$baths \"Sqft:\"-$sqft \"Lot:\"-$lot ]");
        ZHSParser.add("#PATTERN prop-colb");
        ZHSParser.add("IF LINES > 1 COMBINE 1 2");
        ZHSParser.add("IF LINES > 1 COMBINE 1 2");
        ZHSParser.add("IF LINES > 1 COMBINE 1 2");
        ZHSParser.add("[ $irrelevant ]");
        ArrayList<String> selectedSchema = new ArrayList<String>();
        selectedSchema.add("adr");
        selectedSchema.add("price");
        selectedSchema.add("beds");
        selectedSchema.add("baths");
        selectedSchema.add("description");
        selectedSchema.add("irrelevant");
        printKeys.add(selectedSchema);
    }

    /*
     * Sets default values for Direct
     */
    private void setupDHS() {
        DHSQuery = "http://www.directhomes.com/searchResults.cfm?city=&state=&zip=97317&beds=1&baths=1&Max_price=200000&Min_price=%20500&propertyType=Real-Estate";
        DHSClassNames = new ArrayList<String>();
        DHSClassNames.add("mainInfo");
        DHSClassNames.add("subInfo");
        DHSParser = new ArrayList<String>();
        DHSParser.add("#PATTERN mainInfo");
        DHSParser.add("[ $adress ]");
        DHSParser.add("[ $description ]");
        DHSParser.add("#PATTERN subInfo");
        DHSParser.add("IF LINES > 4 COMBINE 3 4");
        DHSParser.add("IF LINES > 4 COMBINE 3 4");
        DHSParser.add("[ $price ]");
        DHSParser.add("[ $beds ]");
        DHSParser.add("[ $baths ]");
        DHSParser.add("[ $irrelevant ]");
        ArrayList<String> selectedSchema = new ArrayList<String>();
        selectedSchema.add("adress");
        selectedSchema.add("price");
        selectedSchema.add("beds");
        selectedSchema.add("baths");
        selectedSchema.add("description");
        selectedSchema.add("irrelevant");
        printKeys.add(selectedSchema);
    }

    /*
     * Sets default values for Home
     */
    private void setupHHS() {
        HHSQuery = "http://www.homefinder.com/zip-code/97317/min_bed_1/min_bath_1/min_price_500/max_price_200000/";
        HHSClassNames = new ArrayList<String>();
        HHSClassNames.add("address");
        HHSClassNames.add("price");
        HHSClassNames.add("beds");
        HHSClassNames.add("baths");
        HHSClassNames.add("descriptionText");
        HHSParser = new ArrayList<String>();
        HHSParser.add("#PATTERN address");
        HHSParser.add("[ $address ]");
        HHSParser.add("#PATTERN price");
        HHSParser.add("[ $price ]");
        HHSParser.add("#PATTERN beds");
        HHSParser.add("[ $beds ]");
        HHSParser.add("#PATTERN baths");
        HHSParser.add("[ $baths ]");
        HHSParser.add("#PATTERN descriptionText");
        HHSParser.add("[ $description ]");
        ArrayList<String> selectedSchema = new ArrayList<String>();
        selectedSchema.add("address");
        selectedSchema.add("price");
        selectedSchema.add("beds");
        selectedSchema.add("baths");
        selectedSchema.add("description");
        selectedSchema.add("irrelevant");
        printKeys.add(selectedSchema);
    }

    public String getDHSQuery() {
        return DHSQuery;
    }

    public String getHHSQuery() {
        return HHSQuery;
    }

    public String getZHSQuery() {
        return ZHSQuery;
    }

    public ArrayList getDHSClassNames() {
        return DHSClassNames;
    }

    public ArrayList getHHSClassNames() {
        return HHSClassNames;
    }

    public ArrayList getZHSClassNames() {
        return ZHSClassNames;
    }

    public ArrayList getDHSParser() {
        return DHSParser;
    }

    public ArrayList getHHSParser() {
        return HHSParser;
    }

    public ArrayList getZHSParser() {
        return ZHSParser;
    }

    // </editor-fold>
    public String toStringArrayList(ArrayList list) {
        String returnMe = "";
        for (int i = 0; i < list.size(); i++) {
            returnMe += list.get(i) + " ";
        }
        return returnMe.trim();
    }

    // <editor-fold defaultstate="collapsed" desc="Global get functions">
    public String getGlobalUrls(int num) {
        return globalUrls.get(num);
    }

    public ArrayList getGlobalTreeNodeNames(int num) {
        return globalTreeNodeNames.get(num);
    }

    public ArrayList getGlobalParsers(int num) {
        return globalParsers.get(num);
    }

    public int getGlobalSize() {
        return globalSize;
    }
    // </editor-fold>
}