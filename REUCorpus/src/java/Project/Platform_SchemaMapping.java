/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 *
 * @author Bouse
 */
public class Platform_SchemaMapping extends javax.swing.JInternalFrame implements MouseListener{
    //-------------------------------------------------------
    //Declare misc variables
    //-------------------------------------------------------
    private LinkedList<sourceNode> sourcePanels = new LinkedList<sourceNode>();
    private LinkedList<linkNode> linkList = new LinkedList<linkNode>();
    private int listA;
    private JPanel dbPanelA;
    private JPanel dbPanelB;
    private Color defaultGray = new Color(238, 238, 238);
    
    private String projName;
    private String srcType;
    private String dbType; //Only used if srcType == Database
    private String dbAType;
    private String dbAConnect[] = new String[5];
    private String dbBType;
    private String dbBConnect[] = new String[5];
    private String exlFileA;
    private String exlFileB;
    private JPanel exlPanelA;
    private JPanel exlPanelB;
    private LinkedList<fieldNode> exlFieldsA = new LinkedList<fieldNode>();
    private LinkedList<fieldNode> exlFieldsB = new LinkedList<fieldNode>();
    
    //These checks are used for drawing a line from one selection to another
    private fieldNode[] selectedNodes = {new fieldNode(), new fieldNode()};
    private int fieldCnt;
    private int outer = 0;
    private int inner = 0;    

    //Empty constructor. Since we don't want an empty project, forces user to create one.
    public Platform_SchemaMapping() {        
        // Force user to create a project, then scrap this one.
        createNewProject frame = new createNewProject();
        frame.setVisible(true);
        dispose();
    }  
    
    //SchemaMapping constructor for Database
    public Platform_SchemaMapping( String pName, String sourceType, String srcADBType,
            String srcBDBType, ArrayList tblA, ArrayList tblB, String[] dbAFields,
            String[] dbBFields) throws ClassNotFoundException, SQLException
    {
        initComponents();
        srcType = new String(sourceType);
        projName = new String(pName);
        
        if( srcType.equals(" Database") )
        {
            dbAType = new String(srcADBType);
            dbBType = new String(srcBDBType);
            
            matchingScrollPanel.setAlignmentY(TOP_ALIGNMENT);
            matchingPanel.setLayout( new java.awt.GridLayout(1,0) );
            
            dbPanelA = new JPanel();        
            dbPanelA.setLayout(new BoxLayout(dbPanelA, BoxLayout.PAGE_AXIS));
            JLabel tempA = new JLabel(dbAFields[2]);
            tempA.setFont(new java.awt.Font("Century Gothic", 0, 22));
            tempA.setForeground(new java.awt.Color(102, 102, 102));
            dbPanelA.add(tempA);

            dbPanelB = new JPanel(new BoxLayout(dbPanelB, BoxLayout.PAGE_AXIS));
            dbPanelB.setLayout(new BoxLayout(dbPanelB, BoxLayout.PAGE_AXIS));
            JLabel tempB = new JLabel(dbAFields[2]);
            tempB.setFont(new java.awt.Font("Century Gothic", 0, 22));
            tempB.setForeground(new java.awt.Color(102, 102, 102));
            dbPanelB.add(tempB);
        
            int i;
            for(i = 0; i < tblA.size(); i++)
            {
                sourcePanels.add( new sourceNode(dbAFields, String.valueOf(tblA.get(i)),
                        i, fieldCnt, srcType, dbAType) );
                dbPanelA.add( sourcePanels.getLast().sourcePanel );
                fieldCnt = sourcePanels.getLast().fieldCount + 1;
            }    
            
            listA = i - 1;
            for(int j = 0; j < tblB.size(); j++)
            {
                sourcePanels.add( new sourceNode(dbBFields, String.valueOf(tblB.get(j)),
                        j+i, fieldCnt, srcType, dbBType) );
                dbPanelB.add( sourcePanels.getLast().sourcePanel );
                fieldCnt = sourcePanels.getLast().fieldCount;
            }              
            
            matchingPanel.add(dbPanelA);
            matchingPanel.add(dbPanelB);
            matchingPanel.revalidate();            
            
            for(outer = 0; outer < sourcePanels.size(); outer++)
            {   for(inner = 0; inner < sourcePanels.get(outer).sourceFields.size(); inner++)
                { sourcePanels.get(outer).sourceFields.get(inner).addMouseListener(this); }
            }

            matchingScrollPanel.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener(){
                public void adjustmentValueChanged(AdjustmentEvent e) { matchingPanel.repaint(); }
            });

            matchingPanel.addMouseListener(this);            
        }
        else{ JOptionPane.showMessageDialog(null, "ERROR! Invalid source type.\n"
                + "Error source: SchemaMapping constructor for Databases"); }
    }/* End SchemaMapping constructor for DB */
    
    // SchemaMapping constructor for Excel
    public Platform_SchemaMapping(String pName, String sourceType, String xlFileA, String xlFileB, String orientation,
            boolean cellSchema)
    {
        initComponents();
        srcType = new String(sourceType);
        projName = new String(pName);        

        String srcAFields[];
        String srcBFields[];
            
        if( srcType.equals(" Excel") )
        {
            exlFileA = new String(xlFileA);
            exlFileB = new String(xlFileB);
            
            Sheet sheetA = getFile(exlFileA);
            Sheet sheetB = getFile(exlFileB);
            
            int rowsACnt = sheetA.getRows();
            int rowsBCnt = sheetB.getRows();
            int colsACnt = sheetA.getColumns();
            int colsBCnt = sheetB.getColumns();
            
            if( orientation.equals("horizontal") )
            {
                srcAFields = new String[colsACnt];
                srcBFields = new String[colsBCnt];
                
                if(cellSchema)
                {
                    for(int i = 0; i < colsACnt; i++)
                    { srcAFields[i] = new String( sheetA.getCell(i, 0).getContents() ); }
                    
                    for(int i = 0; i < colsBCnt; i++)
                    { srcBFields[i] = new String( sheetB.getCell(i, 0).getContents() ); }
                }
                else
                {
                    for(int i = 0; i < colsACnt; i++)
                    { srcAFields[i] = new String(getColumnName(i)); }

                    for(int i = 0; i < colsBCnt; i++)
                    { srcBFields[i] = new String(getColumnName(i)); }
                }
            }
            else if( orientation.equals("vertical") )
            {
                srcAFields = new String[rowsACnt];
                srcBFields = new String[rowsBCnt];
                
                if(cellSchema)
                {
                    for(int i = 0; i < rowsACnt; i++)
                    { srcAFields[i] = new String( sheetA.getCell(0, i).getContents() ); }
                    
                    for(int i = 0; i < rowsBCnt; i++)
                    { srcBFields[i] = new String( sheetB.getCell(0, i).getContents() ); }
                }
                else
                {
                    for(int i = 0; i < colsACnt; i++)
                    { srcAFields[i] = String.valueOf(i); }

                    for(int i = 0; i < colsBCnt; i++)
                    { srcBFields[i] = String.valueOf(i); }
                }
            } 
            else 
            { 
                srcAFields = new String[0];
                srcBFields = new String[0];            
                
                JOptionPane.showMessageDialog(null, "ERROR! Invalid orientation\n"
                    + "Error source: SchemaMapping constructor for Excel"); 
            }
            
            //Load the schema into the window for matching now 
            matchingPanel.setLayout( new java.awt.GridLayout(1,0) );
            
            dbPanelA = new JPanel();        
            dbPanelA.setLayout(new BoxLayout(dbPanelA, BoxLayout.PAGE_AXIS));

            dbPanelB = new JPanel(new BoxLayout(dbPanelB, BoxLayout.PAGE_AXIS));
            dbPanelB.setLayout(new BoxLayout(dbPanelB, BoxLayout.PAGE_AXIS));
        
            int i;
            for(i = 0; i < srcAFields.length; i++)
            {
                exlFieldsA.add( new fieldNode(srcAFields[i], 1, i) );
                exlFieldsA.getLast().setPreferredSize(new Dimension(dbPanelA.getSize().width - 10, 15));
                exlFieldsA.getLast().setPoint();
                exlFieldsA.getLast().addMouseListener(this);
                exlFieldsA.getLast().setAlignmentX(Component.LEFT_ALIGNMENT);
                dbPanelA.add( exlFieldsA.getLast() );
            }    
            
            for(int j = 0; j < srcBFields.length; j++)
            {
                exlFieldsB.add( new fieldNode(srcBFields[j], 2, i) );
                exlFieldsB.getLast().setPreferredSize(new Dimension(dbPanelB.getSize().width - 10, 15));
                exlFieldsB.getLast().setPoint();
                exlFieldsB.getLast().addMouseListener(this);
                exlFieldsB.getLast().setAlignmentX(Component.LEFT_ALIGNMENT);
                dbPanelB.add( exlFieldsB.getLast() );
                i++;
            }              
            
            matchingPanel.add(dbPanelA);
            matchingPanel.add(dbPanelB);
            matchingPanel.revalidate();

            matchingScrollPanel.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener(){
                public void adjustmentValueChanged(AdjustmentEvent e) { matchingPanel.repaint(); }
            });

            matchingPanel.addMouseListener(this);               
        }
        else{ JOptionPane.showMessageDialog(null, "ERROR! Invalid source type.\n"
                + "Error source: SchemaMapping constructor for Excel");
        }
    }//end SchemaMapping constructor for Excel    

/*******************************************************************************
**                             METHODS: GENERAL                               **
*******************************************************************************/ 
        /** Copied from javax.swing.table.AbstractTableModel,
        * to name columns using spreadsheet conventions:
        *  A, B, C, . Z, AA, AB, etc.
        */
    public String getColumnName(int column) {
        String result = "";
        for (; column >= 0; column = column / 26 - 1) {
            result = (char)((char)(column%26)+'A') + result;
        }
        return result;
    }
    
    public void mouseReleased(MouseEvent e)
    {
        if( srcType.equals(" Database") )
        {
            if( e.getSource().getClass() == sourcePanels.getLast().sourceFields.getLast().getClass() )
            {
                for(int i = 0; i < sourcePanels.size(); i++)
                {   for(int j = 0; j < sourcePanels.get(i).sourceFields.size(); j++)
                    { 
                        if( e.getSource() == sourcePanels.get(i).sourceFields.get(j))
                        { selectLabel( sourcePanels.get(i).sourceFields.get(j), i ); }
                    }
                }
            }
            else
            {
                for(int i = 0; i < linkList.size(); i++)
                {   
                    if( linkList.get(i).isOnLine( e.getPoint() ) )
                    { 
                        selectMatch(linkList.get(i));
                    }
                }
            }
        }
        else if(srcType.equals(" Excel"))
        {
            if( e.getSource().getClass() == exlFieldsA.getLast().getClass() )
            {
                boolean found = false;
                
                for(int i = 0; !found && i < exlFieldsA.size(); i++)
                {
                    if(e.getSource() == exlFieldsA.get(i))
                    { selectLabel( exlFieldsA.get(i), i ); }
                }
                
                if( !found )
                {
                    for(int i = 0; !found && i < exlFieldsB.size(); i++)
                    {
                        if(e.getSource() == exlFieldsB.get(i))
                        { selectLabel( exlFieldsB.get(i), i ); }
                    }
                }
            }
            else
            {
                for(int i = 0; i < linkList.size(); i++)
                {   
                    if( linkList.get(i).isOnLine( e.getPoint() ) )
                    { 
                        selectMatch(linkList.get(i));
                    }
                }
            }            
        }
    }//end mouseReleased
    
    public void selectLabel( fieldNode node, int cnt )
    {
        for(int i = 0; i < linkList.size(); i++)
        { linkList.get(i).setSelected(false); }
        
        Graphics g;
        g = matchingPanel.getGraphics();
        node.setSelectedStatus(true);
        int src = node.getFieldSource();
        
        if(src < listA)//Source is on the left
        {
            selectedNodes[0] = node;
            selectedNodes[0].setBackground( new Color(238, 221, 130) );
            selectedNodes[0].setOpaque(true);
            if(linkList.size() > 0){linkList.getLast().setSelected(false);}
        }
        else //source is on the right
        {
            selectedNodes[1] = node;
            selectedNodes[1].setBackground( new Color(238, 221, 130) );
            selectedNodes[1].setOpaque(true);
            if(linkList.size() > 0){linkList.getLast().setSelected(false);}            
        }

        //If there are two nodes selected, draw a line between them
        if(selectedNodes[0].getSelectedStatus() && selectedNodes[1].getSelectedStatus())
        {
            selectedNodes[0].setPoint();
            selectedNodes[1].setPoint();

            int x1 = 0, x2 = 0, y1 = 0, y2 = 0;
            
            if( srcType.equals(" Database") )
            {
                int src1 = selectedNodes[0].getFieldSource();
                int src2 = selectedNodes[1].getFieldSource();
                int src1X;
                int src2X;
                if( src1 > listA ){ src1X = dbPanelB.getX(); } else{ src1X = 50; }
                if( src2 > listA ){ src2X = dbPanelB.getX(); } else{ src2X = 50; }

                x1 = selectedNodes[0].getPoint().x + src1X;
                x2 = selectedNodes[1].getPoint().x + src2X;
                y1 = selectedNodes[0].getPoint().y + sourcePanels.get(src1).sourcePanel.getY();
                y2 = selectedNodes[1].getPoint().y + sourcePanels.get(src2).sourcePanel.getY();          
            }
            else if( srcType.equals(" Excel") )
            {
                int src1 = selectedNodes[0].getID();
                int src2 = selectedNodes[1].getID();
                int src1X;
                int src2X;
                if( src1 == 1 ){ src1X = dbPanelA.getX(); } else{ src1X = 50; }
                if( src2 == 2 ){ src2X = dbPanelB.getX(); } else{ src2X = 50; }

                x1 = selectedNodes[0].getPoint().x + src1X;
                x2 = selectedNodes[1].getPoint().x + src2X;
                y1 =  selectedNodes[0].getPoint().y;
                y2 =  selectedNodes[1].getPoint().y;  
            }

            selectedNodes[0].setPoint(new Point(x1, y1));
            selectedNodes[1].setPoint(new Point(x2, y2));

            addMatch();
        }        

    }//end selectLabel
    
    public void selectMatch(linkNode node)
    {
        for(int i = 0; i < linkList.size(); i++)
        { linkList.get(i).setSelected(false); }
        
        node.setSelected(true);
        Graphics g;
        g = matchingPanel.getGraphics();  
        node.nodeA.setBackground(new Color(238, 221, 130));
        node.nodeB.setBackground(new Color(238, 221, 130));
    }//end selectMatch
    
    public Point offsetPoint(Point pnt, fieldNode parent)
    {
        int src1 = parent.getFieldSource();
        int src1X;
        if( src1 > listA ){ src1X = dbPanelB.getX(); } else{ src1X = 50; }
        return new Point(pnt.x + src1X, pnt.y + sourcePanels.get(src1).sourcePanel.getY());
    }
    
    
        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        matchingScrollPanel = new javax.swing.JScrollPane();
        matchingPanel = new javax.swing.JPanel();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);

        matchingScrollPanel.setBackground(new java.awt.Color(255, 255, 255));

        matchingPanel.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout matchingPanelLayout = new javax.swing.GroupLayout(matchingPanel);
        matchingPanel.setLayout(matchingPanelLayout);
        matchingPanelLayout.setHorizontalGroup(
            matchingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 935, Short.MAX_VALUE)
        );
        matchingPanelLayout.setVerticalGroup(
            matchingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 831, Short.MAX_VALUE)
        );

        matchingScrollPanel.setViewportView(matchingPanel);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(matchingScrollPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 946, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(matchingScrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 831, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel matchingPanel;
    private javax.swing.JScrollPane matchingScrollPanel;
    // End of variables declaration//GEN-END:variables

        private void addMatch()
    {
        if( selectedNodes[0].getSelectedStatus() && selectedNodes[1].getSelectedStatus() )
        {
            Graphics g;
            selectedNodes[0].setSelectedStatus(false);
            selectedNodes[1].setSelectedStatus(false);
            
            selectedNodes[0].fieldLinks.add(selectedNodes[1]);
            selectedNodes[1].fieldLinks.add(selectedNodes[0]); 
            
            linkList.add( new linkNode(linkList.size()+1, 
                            selectedNodes[0].getID(), selectedNodes[1].getID()) );    
            linkList.getLast().setEndNodes(selectedNodes[0], selectedNodes[1]);
            
            int x1 = linkList.getLast().nodeA.getPoint().x;
            int y1 = linkList.getLast().nodeA.getPoint().y;
            int x2 = linkList.getLast().nodeB.getPoint().x;
            int y2 = linkList.getLast().nodeB.getPoint().y;
            
            g = matchingPanel.getGraphics();            
            g.setColor( Color.BLACK );
            g.drawLine(x1, y1, x2, y2);
                
            selectedNodes[0].setBackground(Color.WHITE);
            selectedNodes[0].setOpaque(true);
            selectedNodes[1].setBackground(Color.WHITE);
            selectedNodes[1].setOpaque(true);
        }
        else{ JOptionPane.showMessageDialog(null, "There are less than two nodes currently selected."); }
    }//end addMatch
        
   //---------------------------------------------------------
   // The following methods were used from ReadExcelGUI.java
   //---------------------------------------------------------
      private Sheet readExcelSheet (File file) {
        // open excel file (workbook) for reading
        Workbook wbk;
        try {
            wbk = Workbook.getWorkbook(file);
        } catch (Exception ex) {
            JOptionPane message = new JOptionPane(
                    "Can't read excel file " + file.getPath(),
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
        if (wbk.getNumberOfSheets() <= 0) {
            JOptionPane message = new JOptionPane(
                    "Excel file doesn't have any sheets.",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
        // return first sheet
        return wbk.getSheet(0);
    }   
    
    public Sheet getFile(String fileName)
    {
        File file = new File(fileName);
        //Enable this later, for now we just want the filename. Don't load the data yet.
        // loading long excel tables may be time consuming, so use wait cursor
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        Sheet sheet = readExcelSheet(file);
        if (sheet == null) 
        { JOptionPane.showMessageDialog(null, "ERROR!\nExcel sheet is empty!"); }

        setCursor(Cursor.getDefaultCursor());
        return sheet;
    }
    
    public void paint(Graphics g)     //note paint method
    {   
        super.paint(g);
        for(int i = 0; i < linkList.size(); i++)
        {
            int x1 = linkList.get(i).nodeA.getPoint().x;
            int y1 = linkList.get(i).nodeA.getPoint().y;
            int x2 = linkList.get(i).nodeB.getPoint().x;
            int y2 = linkList.get(i).nodeB.getPoint().y;
                    
            g = matchingPanel.getGraphics();
            g.setColor(Color.BLACK);
            g.drawLine(x1, y1, x2, y2);
        }
    }
    
    
 //   @Override
    public void mouseClicked(MouseEvent e) {
    }

//    @Override
    public void mousePressed(MouseEvent e) {
    }

//    @Override
//    public void mouseReleased(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }

//    @Override
    public void mouseEntered(MouseEvent e) {
    }

//    @Override
    public void mouseExited(MouseEvent e) {
    } 

}
