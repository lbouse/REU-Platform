/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MainFiles;

import java.util.ArrayList;

/**
 *
 * @author josealvarado
 */
public class GlobalTestInfo {
    
     String DHSQuery, HHSQuery, ZHSQuery;
     ArrayList DHSClassNames, HHSClassNames, ZHSClassNames;
     ArrayList DHSParser, HHSParser, ZHSParser;
     
     public GlobalTestInfo(){
         setupDHS();
         setupHHS();
         setupZHS();
     }
     
     public void setupDHS(){
         DHSQuery = "http://www.directhomes.com/searchResults.cfm?city=&state=&zip=97317&beds=1&baths=1&Max_price=200000&Min_price=%20500&propertyType=Real-Estate";
         DHSClassNames = new ArrayList();
         DHSClassNames.add("mainInfo");
         DHSClassNames.add("subInfo");
         DHSParser = new ArrayList();
     }
     public void setupHHS(){
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
     public void setupZHS(){
         ZHSQuery = "http://www.zillow.com/homes/97318_rb/#/homes/for_sale/Salem-OR-97317/399688_rid/1-_beds/1-_baths/500-200000_price/";
         ZHSClassNames = new ArrayList();
         ZHSClassNames.add("adr");
         ZHSClassNames.add("price");
         ZHSClassNames.add("prop-cola");
         ZHSClassNames.add("prop-colb");
         ZHSParser = new ArrayList();
         ZHSParser.add("#PATTERN adr");
         ZHSParser.add("[ $address ]");
         ZHSParser.add("#PATTERN price");
         ZHSParser.add("[ $price ]");
         ZHSParser.add("#PATTERN prop-cola");
         ZHSParser.add("[ $beds \"Baths:\"-$baths \"Sqft:\"-$sqft \"Lot\"-$lot ]");
         ZHSParser.add("#PATTERN prop-colb");
         ZHSParser.add("[ $irrelevant ]");
     }
     
     public String getDHSQuery(){
         return DHSQuery;
     }
     public String getHHSQuery(){
         return HHSQuery;
     }
     public String getZHSQuery(){
         return ZHSQuery;
     }
     
     public ArrayList getDHSClassNames(){
            return DHSClassNames;
     }
     public ArrayList getHHSClassNames(){
         return HHSClassNames;
     }
     public ArrayList getZHSClassNames(){
         return ZHSClassNames;
     }
     
     public ArrayList getDHSParser(){
         return DHSParser;
     }
     public ArrayList getHHSParser(){
         return HHSParser;
     }
     public ArrayList getZHSParser(){
         return ZHSParser;
     }
}
