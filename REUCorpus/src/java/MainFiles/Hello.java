package MainFiles;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.htmlparser.util.ParserException;

public class Hello extends HttpServlet {

    public static OnlineDatabases oD = new OnlineDatabases();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParserException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

            String zipCode = request.getParameter("zipcode");
            System.out.println("Zip_Code: " + zipCode);

            String minBed = request.getParameter("minbed");
            System.out.println("Min_BED: " + minBed);

            String minBath = request.getParameter("minbath");
            System.out.println("Min_BATH: " + minBath);

            String minPrice = request.getParameter("minprice");
            System.out.println("Min_PRICE: " + minPrice);

            String maxPrice = request.getParameter("maxprice");
            System.out.println("Max_PRICE: " + maxPrice);

            HashMap<String, String> keys = new HashMap<String, String>();
            keys.put(HouseSearch.ZIP_CODE, zipCode);
            keys.put(HouseSearch.MIN_BED, minBed);
            keys.put(HouseSearch.MIN_BATH, minBath);
            keys.put(HouseSearch.MIN_PRICE, minPrice);
            keys.put(HouseSearch.MAX_PRICE, maxPrice);

            System.out.println("");

            //Testing 
            GlobalTestInfo gti = new GlobalTestInfo();

//            String testing1 = request.getParameter("website");
//            System.out.println("TESTING TESTING testing1 = " + testing1);
//            String testing2 = request.getParameter("treeNodeNames");
//            System.out.println("TESTING TESTING testing2 = " + testing2);
//            String testing3 = request.getParameter("parser");
//            System.out.println("TESTING TESTING testing3 = " + testing3);
//            System.out.println("");
//            gti.parseAllData(testing1, testing2, testing3);

            GeneralSearch dhs = new GeneralSearch(gti.getDHSQuery(), gti.getDHSClassNames(), gti.getDHSParser());
//            GeneralSearch dhs = new GeneralSearch(gti.getUrls(0), gti.getTrees(0), gti.getParsers(0));
            dhs.processMyNodes();
//            dhs.displayNodes();
            dhs.parseNodes();
//            dhs.printGlobalData();
            GeneralSearch hhs = new GeneralSearch(gti.getHHSQuery(), gti.getHHSClassNames(), gti.getHHSParser());
//            GeneralSearch hhs = new GeneralSearch(gti.getUrls(1), gti.getTrees(1), gti.getParsers(1));
            hhs.processMyNodes();
//            hhs.displayNodes();
            hhs.parseNodes();
//            hhs.printGlobalData();
            GeneralSearch zhs = new GeneralSearch(gti.getZHSQuery(), gti.getZHSClassNames(), gti.getZHSParser());
//            GeneralSearch zhs = new GeneralSearch(gti.getUrls(2), gti.getTrees(2), gti.getParsers(2));
            zhs.processMyNodes();
            zhs.parseNodes();
//            zhs.displayNodes();
//            zhs.printGlobalData();

            oD.add(zhs.getOnlineDatabase());
            oD.add(dhs.getOnlineDatabase());
            oD.add(hhs.getOnlineDatabase());
            //  

            DirectHouseSearch directHouseSearch = new DirectHouseSearch();
            HomeHouseSearch realtorHouseSearch = new HomeHouseSearch();
            ZillowHouseSearch zillowHouseSearch = new ZillowHouseSearch();

            directHouseSearch.searchHouses(keys);
            realtorHouseSearch.searchHouses(keys);
            zillowHouseSearch.searchHouses(keys);

            List<HouseInfo> totalHouses = new ArrayList<HouseInfo>();
            List<HouseInfo> directHouses = directHouseSearch.getHouses();
            List<HouseInfo> zillowHouses = zillowHouseSearch.getHouses();
            List<HouseInfo> realtorHouses = realtorHouseSearch.getHouses();

            if (zillowHouses.size() > 0) {
                System.out.println("Zillow Houses Search has " + zillowHouses.size() + " results");
                for (int index = 0; index < zillowHouses.size(); index++) {
                    totalHouses.add(zillowHouses.get(index));
                }
            } else {
                System.out.println("Zillow House Search is empty");
            }

            if (directHouses.size() > 0) {
                System.out.println("Direct Houses Search has " + directHouses.size() + " results");
                for (int index = 0; index < directHouses.size(); index++) {
                    totalHouses.add(directHouses.get(index));
                }
            } else {
                System.out.println("Direct House Serach is empty");
            }

            if (realtorHouses.size() > 0) {
                System.out.println("Realtor Houses Search has " + realtorHouses.size() + " results");
                for (int index = 0; index < realtorHouses.size(); index++) {
                    totalHouses.add(realtorHouses.get(index));
                }
            } else {
                System.out.println("Realtor House Search is empty");
            }


//            System.out.printf("%d\n", directHouseSearch.getHouses().size());
//            System.out.printf("%d\n", zillowHouseSearch.getHouses().size());
//            System.out.printf("%d\n", realtorHouseSearch.getHouses().size());
            System.out.printf("%d\n", totalHouses.size());

            //This page generates the talbe
            response.setContentType("text/Html");

            if (!oD.isEmpty()) {
                gti.printWebsite(oD, response);
            } else {
                PrintWriter pw = response.getWriter();

                pw.println("<HTML><HEAD><TITLE>Search Results</TITLE></HEAD><BODY>");
                pw.println("<br>");

                if (!totalHouses.isEmpty()) {
                    pw.print("<table border=1px>");
                    pw.print("<th>House Address</th>");
                    pw.print("<th>Price</th>");
                    pw.print("<th>Monthly Payment</th>");
                    pw.print("<th>Number of Beds and Baths</th>");
                    pw.print("<th>Square Feet</th>");
                    pw.print("<th>Description</th>");
                    pw.print("</tr>");
                    int i = 0;
                    for (int index = 0; index < totalHouses.size(); index++) {
                        HouseInfo house = totalHouses.get(index);
                        if ((i++) % 2 == 0) {
                            pw.print("<tr BGCOLOR ='#fdf5e6'>");
                        } else {
                            pw.print("<tr BGCOLOR ='#c0c0c0'>");
                        }

                        pw.println("<td>" + house.getAddress() + "</td>");
                        pw.println("<td>" + house.getPrice() + "</td>");
                        pw.println("<td>" + house.getMonthlyPayment() + "</td>");
                        pw.println("<td>" + house.getBedBath() + "</td>");
                        pw.println("<td>" + house.getSquare() + "</td>");
                        pw.println("<td>" + house.getDescription() + "</td>");
                        pw.println("</tr>");
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



        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParserException ex) {
            Logger.getLogger(Hello.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParserException ex) {
            Logger.getLogger(Hello.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
