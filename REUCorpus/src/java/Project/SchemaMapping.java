package Project;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Bouse
 */
public class SchemaMapping extends javax.swing.JFrame implements MouseListener{
    //-------------------------------------------------------
    //Declare misc variables
    //-------------------------------------------------------
    private LinkedList<sourceNode> sourcePanels = new LinkedList<sourceNode>();
    private LinkedList<linkNode> linkList = new LinkedList<linkNode>();
    private int listA;
    private JPanel dbPanelA;
    private JPanel dbPanelB;
    private Color defaultGray = new Color(238, 238, 238);
    
    private String srcType;
    private String dbType; //Only used if srcType == Database
    private String dbAType;
    private String dbBType;
    
    //These checks are used for drawing a line from one selection to another
    private fieldNode[] selectedNodes = {new fieldNode(), new fieldNode()};
    private int fieldCnt;
    private int outer = 0;
    private int inner = 0;
    
    /**
     * Creates new form NewJFrame
     */
    //Empty constructor. Since we don't want an empty project, forces user to create one.
    public SchemaMapping() {        
        // Force user to create a project, then scrap this one.
        createNewProject frame = new createNewProject();
        frame.setVisible(true);
        dispose();
    }    
    
    public SchemaMapping( String projName, String sourceType, String srcADBType,
            String srcBDBType, ArrayList tblA, ArrayList tblB, String[] dbAFields,
            String[] dbBFields) throws ClassNotFoundException, SQLException
    {
        initComponents();
        srcType = new String(sourceType);
        
        if( srcType.equals(" Database") )
        {
            dbAType = new String(srcADBType);
            dbBType = new String(srcBDBType);
            
            matchingScrollPanel.setAlignmentY(TOP_ALIGNMENT);
            matchingPanel.setLayout( new java.awt.GridLayout(1,0) );
            
            dbPanelA = new JPanel();        
            dbPanelA.setLayout(new BoxLayout(dbPanelA, BoxLayout.PAGE_AXIS));
            JLabel temp = new JLabel(dbAFields[2]);
            temp.setFont(new java.awt.Font("Century Gothic", 0, 22));
            temp.setForeground(new java.awt.Color(102, 102, 102));
            dbPanelA.add(temp);

            dbPanelB = new JPanel(new BoxLayout(dbPanelB, BoxLayout.PAGE_AXIS));
            dbPanelB.setLayout(new BoxLayout(dbPanelB, BoxLayout.PAGE_AXIS));
            temp.setText(dbBFields[2]);
            dbPanelB.add(temp);
        
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
        else{ JOptionPane.showMessageDialog(null, "ERROR! Invalid source type."); }
    }
   
    //---------------------------------------------------------------------------------------------------------
    //                                      GENERAL METHODS
    //---------------------------------------------------------------------------------------------------------
    
    public void mouseReleased(MouseEvent e)
    {
        if( e.getSource().getClass() == sourcePanels.getLast().sourceFields.getLast().getClass() )
        {
            for(int i = 0; i < sourcePanels.size(); i++)
            {   for(int j = 0; j < sourcePanels.get(i).sourceFields.size(); j++)
                { 
                    if( e.getSource() == sourcePanels.get(i).sourceFields.get(j))
                    { 
                        selectLabel( sourcePanels.get(i).sourceFields.get(j), i );
                    }
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
    
    public void selectLabel( fieldNode node, int cnt )
    {
        for(int i = 0; i < linkList.size(); i++)
        { linkList.get(i).setSelected(false); }
        
        Graphics g;
        g = matchingPanel.getGraphics();

            node.setSelectedStatus(true);
            
            if(!selectedNodes[0].getSelectedStatus())
            {   
                selectedNodes[0] = node;
                selectedNodes[0].setBackground( new Color(238, 221, 130) );
                selectedNodes[0].setOpaque(true);
                if(linkList.size() > 0){linkList.getLast().setSelected(false);}
            }
            //else if(!selectedNodes[1].getSelectedStatus())
            else
            {   
                selectedNodes[1] = node;
                selectedNodes[1].setBackground( new Color(238, 221, 130));
                selectedNodes[1].setOpaque(true);
            }
            
            //If there are two nodes selected, draw a line between them
            if(selectedNodes[0].getSelectedStatus() && selectedNodes[1].getSelectedStatus())
            {
                selectedNodes[0].setPoint();
                selectedNodes[1].setPoint();
                
                int src1 = selectedNodes[0].getFieldSource();
                int src2 = selectedNodes[1].getFieldSource();
                int src1X;
                int src2X;
                if( src1 > listA ){ src1X = dbPanelB.getX(); } else{ src1X = 50; }
                if( src2 > listA ){ src2X = dbPanelB.getX(); } else{ src2X = 50; }
                
                int x1 = selectedNodes[0].getPoint().x + src1X;
                int y1 = selectedNodes[0].getPoint().y + sourcePanels.get(src1).sourcePanel.getY();
                int x2 = selectedNodes[1].getPoint().x + src2X;
                int y2 = selectedNodes[1].getPoint().y + sourcePanels.get(src2).sourcePanel.getY();
                
                selectedNodes[0].setPoint(new Point(x1, y1));
                selectedNodes[1].setPoint(new Point(x2, y2));
                
                addMatch();
            }        

    }
    
    public void selectMatch(linkNode node)
    {
        for(int i = 0; i < linkList.size(); i++)
        { linkList.get(i).setSelected(false); }
        
        node.setSelected(true);
        Graphics g;
        g = matchingPanel.getGraphics();  
        node.nodeA.setBackground(new Color(238, 221, 130));
        node.nodeB.setBackground(new Color(238, 221, 130));
    }
    
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
        optionPanel = new javax.swing.JPanel();
        newProject = new javax.swing.JLabel();
        openProject = new javax.swing.JLabel();
        saveProject = new javax.swing.JLabel();
        addMatch = new javax.swing.JLabel();
        removeMatch = new javax.swing.JLabel();
        help = new javax.swing.JLabel();
        projectSettings = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        helpMenu = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SCHEMA MATCHING");

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

        optionPanel.setBackground(new java.awt.Color(255, 255, 255));
        optionPanel.setForeground(new java.awt.Color(51, 51, 51));

        newProject.setIcon(new javax.swing.ImageIcon("C:\\Users\\Bouse\\Documents\\NetBeansProjects\\REU-Platform\\REU_Platform\\src\\reu_platform\\images\\new_project.png")); // NOI18N
        newProject.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                newProjectMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                newProjectMouseExited(evt);
            }
        });

        openProject.setIcon(new javax.swing.ImageIcon("C:\\Users\\Bouse\\Documents\\NetBeansProjects\\REU-Platform\\REU_Platform\\src\\reu_platform\\images\\open_project.png")); // NOI18N

        saveProject.setIcon(new javax.swing.ImageIcon("C:\\Users\\Bouse\\Documents\\NetBeansProjects\\REU-Platform\\REU_Platform\\src\\reu_platform\\images\\save_project.png")); // NOI18N

        addMatch.setIcon(new javax.swing.ImageIcon("C:\\Users\\Bouse\\Documents\\NetBeansProjects\\REU-Platform\\REU_Platform\\src\\reu_platform\\images\\add_match.png")); // NOI18N
        addMatch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addMatchMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addMatchMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                addMatchMouseExited(evt);
            }
        });

        removeMatch.setIcon(new javax.swing.ImageIcon("C:\\Users\\Bouse\\Documents\\NetBeansProjects\\REU-Platform\\REU_Platform\\src\\reu_platform\\images\\remove_match.png")); // NOI18N
        removeMatch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                removeMatchMouseClicked(evt);
            }
        });

        help.setIcon(new javax.swing.ImageIcon("C:\\Users\\Bouse\\Documents\\NetBeansProjects\\REU-Platform\\REU_Platform\\src\\reu_platform\\images\\help.png")); // NOI18N

        projectSettings.setIcon(new javax.swing.ImageIcon("C:\\Users\\Bouse\\Documents\\NetBeansProjects\\REU-Platform\\REU_Platform\\src\\reu_platform\\images\\proj_settings.png")); // NOI18N

        javax.swing.GroupLayout optionPanelLayout = new javax.swing.GroupLayout(optionPanel);
        optionPanel.setLayout(optionPanelLayout);
        optionPanelLayout.setHorizontalGroup(
            optionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(optionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(newProject)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(openProject)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(saveProject)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addMatch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(removeMatch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(projectSettings)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(help)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        optionPanelLayout.setVerticalGroup(
            optionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(optionPanelLayout.createSequentialGroup()
                .addGroup(optionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(newProject)
                    .addComponent(openProject)
                    .addComponent(saveProject)
                    .addComponent(addMatch)
                    .addComponent(removeMatch)
                    .addComponent(help)
                    .addComponent(projectSettings))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(optionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(matchingScrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addComponent(matchingScrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(optionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jMenu1.setMnemonic('f');
        jMenu1.setText("File");
        menuBar.add(jMenu1);

        jMenu2.setMnemonic('e');
        jMenu2.setText("Edit");
        menuBar.add(jMenu2);

        helpMenu.setMnemonic('h');
        helpMenu.setLabel("Help");
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
               
    }
    
    private void addMatchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMatchMouseClicked
        if( selectedNodes[0].getSelectedStatus() && selectedNodes[1].getSelectedStatus() )
        {
            selectedNodes[0].setSelectedStatus(false);
            selectedNodes[1].setSelectedStatus(false);
            selectedNodes[0].setOpaque(false);
            selectedNodes[1].setOpaque(false);

            linkList.add( new linkNode(linkList.size()+1, 
                            selectedNodes[0].getID(), selectedNodes[1].getID()) );    
            linkList.getLast().setEndNodes(selectedNodes[0], selectedNodes[1]);
        }
        else{ JOptionPane.showMessageDialog(null, "There is less than two nodes currently selected."); }
        
       matchingPanel.repaint();
    }//GEN-LAST:event_addMatchMouseClicked

    private void newProjectMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newProjectMouseEntered
            Border border = LineBorder.createGrayLineBorder();
            newProject.setBorder(border);
    }//GEN-LAST:event_newProjectMouseEntered

    private void newProjectMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newProjectMouseExited
            Border border = javax.swing.BorderFactory.createEmptyBorder();
            newProject.setBorder(border);
    }//GEN-LAST:event_newProjectMouseExited

    private void addMatchMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMatchMouseEntered
            Border border = LineBorder.createGrayLineBorder();
            addMatch.setBorder(border);
    }//GEN-LAST:event_addMatchMouseEntered

    private void addMatchMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMatchMouseExited
            Border border = LineBorder.createGrayLineBorder();
            addMatch.setBorder(border);
    }//GEN-LAST:event_addMatchMouseExited

    private void removeMatchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_removeMatchMouseClicked
        boolean deleted = false;
        Graphics g;
        
        for(int i = 0; i < linkList.size(); i++)
        {
            if(linkList.get(i).isSelected())
            {
                int x1 = linkList.getLast().nodeA.getPoint().x;
                int y1 = linkList.getLast().nodeA.getPoint().y;
                int x2 = linkList.getLast().nodeB.getPoint().x;
                int y2 = linkList.getLast().nodeB.getPoint().y;
            
                g = matchingPanel.getGraphics();            
                g.setColor(defaultGray);
                g.drawLine(x1, y1, x2, y2);
                
                linkList.get(i).nodeA.setBackground(defaultGray);
                linkList.get(i).nodeB.setBackground(defaultGray); 
                
                linkList.remove(i);
                deleted = true;
            }
        }
        if( !deleted ){ JOptionPane.showMessageDialog(null, "No link selected!"); }
    }//GEN-LAST:event_removeMatchMouseClicked

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
//            
//            if( linkList.get(i).isSelected() )
//            {
//                linkList.get(i).nodeA.setBackground(new Color(238, 221, 130));
//                linkList.get(i).nodeB.setBackground(new Color(238, 221, 130));
//                g.setColor( new Color(238, 221, 130) );
//                
//                y1 += 1;
//                y2 += 1;
//                g.drawLine( x1, y1, x2, y2);
//                
//                y1 -= 2;
//                y2 -= 2;
//                g.drawLine(x1, y1, x2, y2);
//            }
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new SchemaMapping().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel addMatch;
    private javax.swing.JLabel help;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel matchingPanel;
    private javax.swing.JScrollPane matchingScrollPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JLabel newProject;
    private javax.swing.JLabel openProject;
    private javax.swing.JPanel optionPanel;
    private javax.swing.JLabel projectSettings;
    private javax.swing.JLabel removeMatch;
    private javax.swing.JLabel saveProject;
    // End of variables declaration//GEN-END:variables

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
