/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reu_platform;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author josealvarado
 */
public class Login extends JFrame implements ActionListener {

    JButton SUBMIT;
    JPanel panel;
    JLabel label1, label2, label3;
    final JTextField text1, text2, text3;

    Login() {
        label1 = new JLabel();
        label1.setText("Username:");
        text1 = new JTextField(15);

        label2 = new JLabel();
        label2.setText("Password:");
        text2 = new JPasswordField(15);

        label3 = new JLabel();
        label3.setText("Server URI");
        text3 = new JTextField(15);
        text3.setText("CorpusChristiServer");
        
        SUBMIT = new JButton("Connect");

        panel = new JPanel(new GridLayout(0, 2));
        panel.add(label1);
        panel.add(text1);
        panel.add(label2);
        panel.add(text2);
        panel.add(label3);
        panel.add(text3);
        panel.add(SUBMIT);
        add(panel, BorderLayout.CENTER);
        SUBMIT.addActionListener(this);
        setTitle("LOGIN FORM");
        setBounds(50, 50, 300,150);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        String value1 = text1.getText();
        String value2 = text2.getText();
        
        if (value1.equals("Corpus") && value2.equals("Christi")) {
            setVisible(false);
        } else {
            System.out.println("enter the valid username and password");
            JOptionPane.showMessageDialog(this, "Incorrect login or password",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}