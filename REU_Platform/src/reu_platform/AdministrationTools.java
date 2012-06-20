/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reu_platform;

import javax.swing.JDesktopPane;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
 
import java.awt.event.*;
import java.awt.*;

/**
 *
 * @author josealvarado
 */
public class AdministrationTools extends JFrame
                               implements ActionListener {
    JDesktopPane desktop;
    Dimension screenSize;
 
    public AdministrationTools() {
        super("Administration Tools");
 
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                  screenSize.width  - inset*2,
                  screenSize.height - inset*2);
 
        //Set up the GUI.
        desktop = new JDesktopPane(); //a specialized layered pane
        createOtherFrame(500/3, 500-60); //create first "window"
        setContentPane(desktop);
        setJMenuBar(createMenuBar());
 
        //Make dragging a little faster but perhaps uglier.
        desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
        setSize(500, 500);
        setVisible(true);
    }
 
    protected JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
 
        //Set up the lone menu.
        JMenu menu = new JMenu("Document");
        menu.setMnemonic(KeyEvent.VK_D);
        menuBar.add(menu);
 
        //Set up the first menu item.
        JMenuItem menuItem = new JMenuItem("New");
        menuItem.setMnemonic(KeyEvent.VK_N);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_N, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("new");
        menuItem.addActionListener(this);
        menu.add(menuItem);
 
        //Set up my own menu item for testing purposes
        menuItem = new JMenuItem("Tree");
        menuItem.setMnemonic(KeyEvent.VK_T);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_T, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("tree");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        //Set up the second menu item.
        menuItem = new JMenuItem("Quit");
        menuItem.setMnemonic(KeyEvent.VK_Q);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_Q, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("quit");
        menuItem.addActionListener(this);
        menu.add(menuItem);
 
        return menuBar;
    }
 
    //React to menu selections.
    public void actionPerformed(ActionEvent e) {
        if ("new".equals(e.getActionCommand())) { //new
            createFrame();
        } else if("tree".equals(e.getActionCommand())){
            createOtherFrame();
        }else { //quit
            quit();
        }
    }
 
    //Create a new internal frame.
    protected void createFrame() {
        MyInternalFrame frame = new MyInternalFrame(this);
        frame.setVisible(true); //necessary as of 1.3
        desktop.add(frame);
        try {
            frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {}
    }
 
    //Create a new internal frame.
    protected void createOtherFrame() {
        TreeDemo frame = new TreeDemo(this);
        frame.setVisible(true); //necessary as of 1.3
        desktop.add(frame);
        try {
            frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {}
    }
        //Create a new internal frame.
    protected void createOtherFrame(int sizeX, int sizeY) {
        TreeDemo frame = new TreeDemo(this);
        frame.setVisible(true); //necessary as of 1.3
        frame.setBounds(0, 0, sizeX, sizeY);
        desktop.add(frame);
        try {
            frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {}
    }
    
    //Quit the application.
    protected void quit() {
        System.exit(0);
    }
 
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);
 
        //Create and set up the window.
        AdministrationTools frame = new AdministrationTools();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Display the window.
        frame.setVisible(true);
    }
 
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
