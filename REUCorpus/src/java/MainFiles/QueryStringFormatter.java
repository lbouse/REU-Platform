package MainFiles;

import java.net.URLEncoder;

public class QueryStringFormatter {
   private String queryEngine;
   private StringBuilder query = new StringBuilder();

   public QueryStringFormatter(String queryEngine) {this.queryEngine = queryEngine;}

   public String getEngine() {return this.queryEngine;}

   public void addQuery(String queryString, String queryValue)	throws Exception {query.append(queryString+"="+URLEncoder.encode(queryValue,"UTF-8")+"&");}

   public String getQueryString() {return "?"+query.toString();}

   public static void main(String[] args) throws Exception {
      QueryStringFormatter formatter = new QueryStringFormatter("http://www.google.com.au/search");

      formatter.addQuery("newwindow","1");
      formatter.addQuery("q","Xingchen Chu & Rajkumar Buyya");

      System.out.println(formatter.getEngine()+

      formatter.getQueryString());
   }
}