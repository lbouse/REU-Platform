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

public class Mapping extends HttpServlet {

    //public static PrintWriter out;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParserException {
        System.out.println("processRequest");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            //int stepNumber = Integer.valueOf( request.getParameter("step") );
            
             //Coming from step 1, going to step 2
//                String sourceType = request.getParameter("srcTypes");
//                String projName = request.getParameter("projName");
                String sourceType = "website";
                
                response.setContentType("text/Html");
                if( sourceType.equals("website") )
                { 
//                    buildStepTwoC(projName, sourceType, stepNumber, response); 
                    out.println("<html><head>");
                    out.println("<title>New Project - Web</title>");
                    out.println("<link rel='stylesheet' href='platformCSS.css' type='text/css' media='screen'/>");
                    out.println("<script type='text/javascript' src='js/jquery.tinyscrollbar.min.js'></script>");
                    out.println("</head></html><body>");
                    out.println("<div id='main'><div class='header'>New Project - Web</div><div class='content'>");
                    out.println("<form action='./Mapping' name='mapping' method='post'>");

                    out.println("<input type='text' name='webURL' value='www.example.com/search.php' /> "
                            + "&nbsp; <button type='button' name='addURL' value=' + ' onclick='addSrc()' />");

                    out.println("<br /><br />");
                    out.println("<div id='sourceList'></div>");

                    out.println("<input type='submit' name='submit' value='Next' />");
                    out.println("</form>");
                    out.println("</div><div class='footer'><a href='index.xhtml'>BACK TO INDEX</a></div>");
                    out.println("</body></html>");                    
                }

            /*
             * Sets the content type of the response being sent to the client In
             * this case it's an Html page
             */

        } finally {
            out.close();
        }
    }

    //Step two, source type "Database"
//    public void buildStepTwoA(final String projName, final String srcType, int stepNum) 
//            throws ClassNotFoundException, SQLException
//    {
//        mainPanel.add( Box.createRigidArea(new Dimension(0, 3)) );
//        headerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
//        mainPanel.add(headerPanel); 
//
//        final createNewProjectDB s = new createNewProjectDB(stepNum);
//        s.dbPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
//        mainPanel.add(s.dbPanel);
//
//        nextBtn.addActionListener(new java.awt.event.ActionListener(){
//            public void actionPerformed(ActionEvent e) {
//                srcAStr = new String[s.fieldLength];
//                srcBStr = new String[s.fieldLength];
//                if(s.checkAll()){
//                    for(int i = 0; i < s.sourceAFields.length; i++)
//                    { srcAStr[i] = new String(s.sourceAFields[i].getText()); }
//
//                    for(int i = 0; i < s.sourceBFields.length; i++)
//                    { srcBStr[i] = new String(s.sourceBFields[i].getText()); }
//
//                    sourceADBType = s.sourceADBType;
//                    sourceBDBType = s.sourceBDBType;
//
//                }else{ JOptionPane.showMessageDialog(null, "ERROR!\nField left blank."); }
//            } 
//        });
//
//        createButtonPanel.revalidate();
//        createButtonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
//        mainPanel.add(createButtonPanel);
//        mainPanel.revalidate();        
//    }/*end build StepTwoA*/
    
    //Select source websites
    public void buildStepTwoC(String projName, String sourceType, int stepNumber,
            HttpServletResponse response) throws IOException
    {
        System.out.println("buildStepTwoC");
        PrintWriter out = response.getWriter();
        out.println("<html><head>");
        out.println("<title>New Project - Web</title>");
        out.println("<link rel='stylesheet' href='platformCSS.css' type='text/css' media='screen'/>");
        out.println("<script type='text/javascript' src='js/jquery.tinyscrollbar.min.js'></script>");
        out.println("</head></html><body>");
        out.println("<div id='main'><div class='header'>New Project - Web</div><div class='content'>");
        out.println("<form action='Mapping' name='mapping' method='post'>");
        
        out.println("<input type='text' name='webURL' value='www.example.com/search.php' /> "
                + "&nbsp; <button type='button' name='addURL' value=' + ' onclick='addSrc()' />");
        
        out.println("<br /><br />");
        out.println("<div id='sourceList'></div>");
        
        out.println("<input type='submit' name='submit' value='Next' />");
        out.println("</form>");
        out.println("</div><div class='footer'><a href='index.xhtml'>BACK TO INDEX</a></div>");
        out.println("</body></html>");
    }/*end build stepTwoA*/
    
    
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
        System.out.println("doGet");
        
        try {
            processRequest(request, response);
        } catch (ParserException ex) {
            Logger.getLogger(Mapping.class.getName()).log(Level.SEVERE, null, ex);
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
        System.out.println("doPost");
        
        try {
            processRequest(request, response);
        } catch (ParserException ex) {
            Logger.getLogger(Mapping.class.getName()).log(Level.SEVERE, null, ex);
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
