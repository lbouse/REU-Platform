/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.*;

/**
 *
 * @author josealvarado
 */
public class TestingLists2 {
    public static void main(String args[]) {
        String labels[] = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
    JFrame f = new JFrame("Selection Modes");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JList list1 = new JList(labels);
    JList list2 = new JList(labels);
    JList list3 = new JList(labels);
    JList list4 = new JList(labels);
    JList list5 = new JList(labels);
    JList list6 = new JList(labels);
    Container c = f.getContentPane();
    JScrollPane sp1 = new JScrollPane(list1);
    sp1.setColumnHeaderView(new JLabel("Single Selection"));
    JScrollPane sp2 = new JScrollPane(list2);
    sp2.setColumnHeaderView(new JLabel("Single Interval"));
    JScrollPane sp3 = new JScrollPane(list3);
    sp3.setColumnHeaderView(new JLabel("Multi Interval"));
    JScrollPane sp4 = new JScrollPane(list4);
    sp4.setColumnHeaderView(new JLabel("Single Selection"));
    JScrollPane sp5 = new JScrollPane(list5);
    sp5.setColumnHeaderView(new JLabel("Single Interval"));
    JScrollPane sp6 = new JScrollPane(list6);
    sp6.setColumnHeaderView(new JLabel("Multi Interval"));
    Box box = Box.createHorizontalBox();
    box.add(sp1);
    box.add(sp2);
    box.add(sp3);
    box.add(sp4);
    box.add(sp5);
    box.add(sp6);
    
    c.add(box, BorderLayout.CENTER);
    f.setSize(300, 200);
    f.setVisible(true);
  }
}


   