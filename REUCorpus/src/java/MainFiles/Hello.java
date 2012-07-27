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

    /*
     * Stores all the parsed data from each online databases
     */
    public static OnlineDatabases oD = new OnlineDatabases();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParserException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            GlobalTestInfo gti = new GlobalTestInfo();

            /*
             * Gets websites, treeNodeNames, and parsers from the html page
             * after the submit has been pressed
             */
            String websites = request.getParameter("websites");
            System.out.println("INPUT WEBSITES = " + websites);
            String treeNodeNames = request.getParameter("treeNodeNames");
            System.out.println("INPUT TREENODENAMES = " + treeNodeNames);
            String parsers = request.getParameter("parsers");
            System.out.println("INPUT PARSERS = " + parsers);
            String printKeys = request.getParameter("printKeys");
            System.out.println("INPUT PRINTKEYS = " + printKeys);
            String globalSchema = request.getParameter("globalSchema");
            System.out.println("INPUT GLOBALSCHEMA = " + globalSchema);

            /*
             * Checks to see whether or not the user inputed something for all
             * three fields
             */
            if (!websites.trim().equals("") && !treeNodeNames.trim().equals("") && !parsers.trim().equals("") && !printKeys.trim().equals("") && !globalSchema.trim().equals("")) {
                gti.parseAllData(websites, treeNodeNames, parsers, printKeys, globalSchema);
                for (int i = 0; i < gti.getGlobalSize(); i++) {
                    GeneralSearch gs = new GeneralSearch(gti.getGlobalUrls(i), gti.getGlobalTreeNodeNames(i), gti.getGlobalParsers(i));
                    System.out.println("main " + i + " " + gs);
                    gs.processNodes();
//                    gs.displayNodes();
                    gs.parseNodes();
//                    gs.printGlobalData();
                    oD.add(gs.getOnlineDatabase());
                }
            } else {
                System.out.println("Nothing Inputed through html page");

                // <editor-fold defaultstate="collapsed" desc="Unhighlight this to test hard coded input, Note it only works if the user doesn't input anything">
                gti.setupDefaultQuery();

                GeneralSearch zhs = new GeneralSearch(gti.getZHSQuery(), gti.getZHSClassNames(), gti.getZHSParser());
                zhs.processNodes();
                zhs.parseNodes();
//                zhs.printGlobalData();
////                GeneralSearch dhs = new GeneralSearch(gti.getDHSQuery(), gti.getDHSClassNames(), gti.getDHSParser());
//                dhs.processNodes();
//                dhs.parseNodes();
//                dhs.printGlobalData();
//                GeneralSearch hhs = new GeneralSearch(gti.getHHSQuery(), gti.getHHSClassNames(), gti.getHHSParser());
//                hhs.processNodes();
//                hhs.parseNodes();
//                hhs.printGlobalData();


                oD.add(zhs.getOnlineDatabase());
//                oD.add(dhs.getOnlineDatabase());
//                oD.add(hhs.getOnlineDatabase());
                // </editor-fold>
            }

            /*
             * Sets the content type of the response being sent to the client In
             * this case it's an Html page
             */
            response.setContentType("text/Html");

            /*
             * Builds the Html page
             */
            gti.printWebsite(oD, response);

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
