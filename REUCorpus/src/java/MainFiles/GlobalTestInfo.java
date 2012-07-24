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

    String DHSQuery, HHSQuery, ZHSQuery;
    ArrayList DHSClassNames, HHSClassNames, ZHSClassNames;
    ArrayList DHSParser, HHSParser, ZHSParser;
    String DHSQuery2, HHSQuery2, ZHSQuery2;
    ArrayList DHSClassNames2, HHSClassNames2, ZHSClassNames2;
    ArrayList DHSParser2, HHSParser2, ZHSParser2;
    
    //Use only on HTML page
    String websites = "http://www.directhomes.com/searchResults.cfm?city=&state=&zip=97317&beds=1&baths=1&Max_price=200000&Min_price=%20500&propertyType=Real-Estate END http://www.homefinder.com/zip-code/97317/min_bed_1/min_bath_1/min_price_500/max_price_200000/ END http://www.zillow.com/homes/97318_rb/#/homes/for_sale/Salem-OR-97317/399688_rid/1-_beds/1-_baths/500-200000_price/";
    String tree_node_names = "mainInfo END address EL price EL beds EL baths EL descriptionText END adr EL price EL prop-cola EL prop-colb";
    String parser = "#PATTERN mainInfo EL [ $adress ] EL [ $description ] END #PATTERN address EL [ $address ] EL #PATTERN price EL [ $price ] EL #PATTERN beds EL [ $beds ] EL #PATTERN baths EL [ $baths ] EL #PATTERN descriptionText EL [ $description ] END #PATTERN adr EL [ $address ] EL #PATTERN price EL [ $price ] EL #PATTERN prop-cola EL [ $beds \"Baths:\"-$baths \"Sqft:\"-$sqft \"Lot\"-$lot ] EL #PATTERN prop-colb EL [ $irrelevant ]";
    String t = "";
    
    ArrayList<ArrayList> printKeys = new ArrayList<ArrayList>();
    ArrayList GlobalSchema = new ArrayList();
    
    boolean debug = true;

    public GlobalTestInfo() {
        setupZHS();
        setupDHS();
        setupHHS();
        ArrayList temp = new ArrayList();
        temp.add("adr");
        printKeys.add(temp);
        ArrayList temp2 = new ArrayList();
        temp2.add("adress");
        printKeys.add(temp2);
        ArrayList temp3 = new ArrayList();
        temp3.add("address");
        printKeys.add(temp3);
        GlobalSchema.add("ADDRESS");
    }

    public void setupZHS() {
        ZHSQuery = "http://www.zillow.com/homes/97318_rb/#/homes/for_sale/Salem-OR-97317/399688_rid/1-_beds/1-_baths/500-200000_price/";
        ZHSClassNames = new ArrayList();
        ZHSClassNames.add("adr");
        ZHSClassNames.add("price");
        ZHSClassNames.add("prop-cola");
        ZHSClassNames.add("prop-colb");
        ZHSParser = new ArrayList();
        ZHSParser.add("#PATTERN adr");
        ZHSParser.add("[ $adr ]");
        ZHSParser.add("#PATTERN price");
        ZHSParser.add("[ $price ]");
        ZHSParser.add("#PATTERN prop-cola");
        ZHSParser.add("[ $beds \"Baths:\"-$baths \"Sqft:\"-$sqft \"Lot\"-$lot ]");
        ZHSParser.add("#PATTERN prop-colb");
        ZHSParser.add("[ $irrelevant ]");
    }
    
    
    public void setupDHS() {
        DHSQuery = "http://www.directhomes.com/searchResults.cfm?city=&state=&zip=97317&beds=1&baths=1&Max_price=200000&Min_price=%20500&propertyType=Real-Estate";
        DHSClassNames = new ArrayList();
        DHSClassNames.add("mainInfo");
//         DHSClassNames.add("subInfo");
        DHSParser = new ArrayList();
        DHSParser.add("#PATTERN mainInfo");
        DHSParser.add("[ $adress ]");
        DHSParser.add("[ $description ]");
    }

    public void setupHHS() {
        HHSQuery = "http://www.homefinder.com/zip-code/97317/min_bed_1/min_bath_1/min_price_500/max_price_200000/";
        HHSClassNames = new ArrayList();
        HHSClassNames.add("address");
        HHSClassNames.add("price");
        HHSClassNames.add("beds");
        HHSClassNames.add("baths");
        HHSClassNames.add("descriptionText");
        HHSParser = new ArrayList();
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
    ArrayList<String> urls = new ArrayList<String>();
    ArrayList<ArrayList> trees = new ArrayList<ArrayList>();
    ArrayList<ArrayList> parsers = new ArrayList<ArrayList>();

    public void parseAllData(String u, String t, String p) {

        String[] URLs = u.split("END");
        for (int i = 0; i < URLs.length; i++) {
            urls.add(URLs[i].trim());
            if (debug) {
                System.out.println("url " + i + " = " + urls.get(i));
            }
        }

        String[] treeNodeNames = t.split("END");
        for (int i = 0; i < treeNodeNames.length; i++) {
            String[] temp = treeNodeNames[i].split("EL");
            ArrayList temp2 = new ArrayList();
            for (int j = 0; j < temp.length; j++) {
                temp2.add(temp[j].trim());
            }
            trees.add(temp2);
            if (debug) {
                System.out.println("trees " + i + " = " + toStringArrayList(trees.get(i)));
            }
        }

        String[] parsing = p.split("END");
        for (int i = 0; i < parsing.length; i++) {
            String[] temp = parsing[i].split("EL");
            ArrayList temp2 = new ArrayList();
            for (int j = 0; j < temp.length; j++) {
                temp2.add(temp[j].trim());
            }
            parsers.add(temp2);
            if (debug) {
                System.out.println("parsers " + i + " = " + toStringArrayList(parsers.get(i)));
            }
        }

    }

    public String getUrls(int num) {
        return urls.get(num);
    }

    public ArrayList getTrees(int num) {
        return trees.get(num);
    }

    public ArrayList getParsers(int num) {
        return parsers.get(num);
    }

    public String toStringArrayList(ArrayList list) {
        String returnMe = "";
        for (int i = 0; i < list.size(); i++) {
            returnMe += list.get(i) + " ";
        }
        return returnMe.trim();
    }

    /*
     * To display OnlineDatabases you need a global schema, 
     * and the corresponding words for each OnlineDatabase
     */
    public void printWebsite(OnlineDatabases oD, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        pw.println("<HTML><HEAD><TITLE>Search Results</TITLE></HEAD><BODY>");
        pw.println("<br>");

        if (!oD.isEmpty()) {
            pw.print("<table border=1px>");
            
            for(int i = 0; i < GlobalSchema.size(); i++){
                pw.print("<th>" + GlobalSchema.get(i) + "</th>");
            }
            
            pw.print("</tr>");
            int i = 0;
            for (int index = 0; index < oD.size(); index++) {
                OnlineDatabase temp = oD.get(index);
                ArrayList<String> tempKeys = printKeys.get(index);
 
                if(debug){
                    System.out.println("Name = " + temp.getName());
                    System.out.println("tempKeys size  = " + tempKeys.size());
                    System.out.println("stored dataKeys "+temp.getSchema());
                    System.out.println("matched key = " + tempKeys.get(0));
                }
                
                for(int j = 0; j < temp.size(); j++){
                    if ((i++) % 2 == 0) {
                        pw.print("<tr BGCOLOR ='#fdf5e6'>");
                    } else {
                        pw.print("<tr BGCOLOR ='#c0c0c0'>");
                    }
                    
                    for(int k = 0; k < tempKeys.size(); k++){
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
}
