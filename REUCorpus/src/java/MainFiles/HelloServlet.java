package MainFiles;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloServlet extends HttpServlet
{
   private static final long serialVersionUID = 1L;

   /*public HelloServlet()
   {
       super();
       System.out.println("TRACK");
   }*/

   /*public void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException
   {
      doPost(request, response);
   }*/

    @Override
   public void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException
   {
      String zipCode = request.getParameter("zipcode");
      System.out.println("Zip_Code: " + zipCode + "\n");

      /*HashMap<String, String> keys = new HashMap<String, String>();
      keys.put(HouseSearch.ZIP_CODE, zipCode);

      DirectHouseSearch directHouseSearch = new DirectHouseSearch();
      RealtorHouseSearch realtorHouseSearch = new RealtorHouseSearch();
      ZillowHouseSearch zillowHouseSearch = new ZillowHouseSearch();

      directHouseSearch.searchHouses(keys);
      realtorHouseSearch.searchHouses(keys);
      zillowHouseSearch.searchHouses(keys);

      List<HouseInfo> totalHouses = directHouseSearch.getHouses();
      //totalHouses.addAll(realtorHouseSearch.getHouses());
      totalHouses.addAll(zillowHouseSearch.getHouses());

      res.setContentType("text/Html");
      PrintWriter pw = res.getWriter();
      pw.println("<HTML><HEAD><TITLE>Search Results</TITLE></HEAD><BODY>");
      pw.println("<br>");
      
      pw.print("<table border=1px>");
      pw.print("<tr BGCOLOR='#4682b4>");
      pw.print("<th>House Address</th>");
      pw.print("<th>Price</th>");
      pw.print("<th>Monthly Payment</th>");
      pw.print("<th>Number of Beds and Baths</th>");
      pw.print("<th>Square Feet</th>");
      pw.print("<th>Description</th>");
      pw.print("</tr>");

      int i = 0;

      for(HouseInfo house : totalHouses)
      {
          if((i++)%2 == 0)
          {
            pw.print("<tr>");
          }
          else
          {
            pw.print("<tr BGCOLOR ='#dccdc'>");
          }

          pw.print("<td>" + house.getAddress() + "</td>");
          pw.print("<td>" + house.getPrice() + "</td>");
          pw.print("<td>" + house.getMonthlyPayment() + "</td>");
          pw.print("<td>" + house.getBedBath() + "</td>");
          pw.print("<td>" + house.getSquare() + "</td>");
          pw.print("<td>" + house.getDescription() + "</td>");
          pw.println("</tr>");
      }
      pw.print("</table>");

      pw.println("</BODY></HTML>");
      pw.close();*/
   }
}
