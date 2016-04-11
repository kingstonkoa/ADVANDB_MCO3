package mastermastersql;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.*;

public class MyPanel extends JPanel {
    private Controller c;
    private JLabel labelTitle;
    private JTextField tfUsername1;
    private JLabel labelUsername;
    private JLabel labelLogin;
    private JTextField tfPassword1;
    private JLabel labelPassword;
    private JLabel labelCreateUser;
    private JTextField tfUsername2;
    private JTextField tfPassword2;
    private JButton btnExecute1;
    private JButton btnExecute2;
    private JTextArea taShowMaster;
    private JButton btnExecute3;
    private JLabel labelShowMaster;
    private JButton btnConnectMaster;
    private JTextField taQuery;
    private JLabel labelQuery;
    private JTextArea taResults;
    private JLabel labelResults;
    private JButton btnExecute4;
    private JButton btnClear;
    private JLabel labelServer;
    private JLabel labelFile;
    private JLabel labelPosition;
    private JTextField tfServer;
    private JTextField tfFile;
    private JTextField tfPosition;
    private JButton btnRestartServer;
    private JLabel labelIP;
    private JTextField tfIP;
    private Database db;
    public static final String[] nodes = { "Central Office", "Palawan Branch", "Marinduque Branch" };

    public MyPanel(Controller c) {
        this.c = c;
        //construct components
        labelTitle = new JLabel ("MASTER MASTER SETUP");
        tfUsername1 = new JTextField (5);
        labelUsername = new JLabel ("username");
        labelLogin = new JLabel ("Login");
        tfPassword1 = new JTextField (5);
        labelPassword = new JLabel ("password");
        labelCreateUser = new JLabel ("Create User");
        tfUsername2 = new JTextField (5);
        tfPassword2 = new JTextField (5);
        btnExecute1 = new JButton ("Execute");
        btnExecute2 = new JButton ("Execute");
        taShowMaster = new JTextArea (5, 5);
        btnExecute3 = new JButton ("Execute");
        labelShowMaster = new JLabel ("Show Master Status");
        btnConnectMaster = new JButton ("CONNECT MASTERS");
        taQuery = new JTextField (5);
        labelQuery = new JLabel ("Query");
        taResults = new JTextArea (5, 5);
        taResults.setText("");
        labelResults = new JLabel ("Results");
        btnExecute4 = new JButton ("Execute");
        btnClear = new JButton ("Clear");
        labelServer = new JLabel ("Server");
        labelFile = new JLabel ("File");
        labelPosition = new JLabel ("Position");
        tfServer = new JTextField (5);
        tfFile = new JTextField (5);
        tfPosition = new JTextField (5);
        btnRestartServer = new JButton ("Restart Server");
        labelIP = new JLabel ("IP Address");
        tfIP = new JTextField (5);

        //set components properties
        labelServer.setToolTipText ("IP of Master");
        labelFile.setToolTipText ("from Show Master Status");
        labelPosition.setToolTipText ("from Show Master Status");

        //adjust size and set layout
        setPreferredSize (new Dimension (732, 429));
        setLayout (null);

        //add components
        add (labelTitle);
        add (tfUsername1);
        add (labelUsername);
        add (labelLogin);
        add (tfPassword1);
        add (labelPassword);
        add (labelCreateUser);
        add (tfUsername2);
        add (tfPassword2);
        add (btnExecute1);
        add (btnExecute2);
        add (taShowMaster);
        add (btnExecute3);
        add (labelShowMaster);
        add (btnConnectMaster);
        add (taQuery);
        add (labelQuery);
        add (taResults);
        add (labelResults);
        add (btnExecute4);
        add (btnClear);
        add (labelServer);
        add (labelFile);
        add (labelPosition);
        add (tfServer);
        add (tfFile);
        add (tfPosition);
        add (btnRestartServer);
        add (labelIP);
        add (tfIP);

        //set component bounds (only needed by Absolute Positioning)
        labelTitle.setBounds (325, 15, 150, 35);
        tfUsername1.setBounds (90, 130, 110, 25);
        labelUsername.setBounds (20, 130, 65, 25);
        labelLogin.setBounds (100, 65, 45, 30);
        tfPassword1.setBounds (90, 160, 110, 25);
        labelPassword.setBounds (20, 160, 65, 25);
        labelCreateUser.setBounds (225, 65, 100, 25);
        tfUsername2.setBounds (215, 105, 100, 25);
        tfPassword2.setBounds (215, 145, 100, 25);
        btnExecute1.setBounds (90, 190, 100, 25);
        btnExecute2.setBounds (220, 190, 100, 25);
        taShowMaster.setBounds (325, 105, 150, 75);
        btnExecute3.setBounds (345, 190, 100, 25);
        labelShowMaster.setBounds (330, 60, 125, 30);
        btnConnectMaster.setBounds (480, 210, 165, 35);
        taQuery.setBounds (65, 265, 155, 115);
        labelQuery.setBounds (120, 225, 45, 30);
        taResults.setBounds (235, 265, 335, 115);
        labelResults.setBounds (345, 225, 65, 30);
        btnExecute4.setBounds (90, 390, 100, 25);
        btnClear.setBounds (340, 390, 100, 25);
        labelServer.setBounds (485, 105, 60, 25);
        labelFile.setBounds (485, 135, 100, 25);
        labelPosition.setBounds (485, 165, 65, 25);
        tfServer.setBounds (555, 105, 120, 25);
        tfFile.setBounds (555, 135, 120, 25);
        tfPosition.setBounds (555, 165, 120, 25);
        btnRestartServer.setBounds (585, 295, 130, 50);
        labelIP.setBounds (20, 95, 75, 30);
        tfIP.setBounds (90, 100, 110, 25);
        
        btnExecute1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try{
                c.connectToServer(tfIP.getText(), tfUsername1.getText(), tfPassword1.getText());
                //c.connectToServer("192.168.8.104", "replicator", "pass");
                //db = new Database(tfIP.getText(), tfUsername1.getText(), tfPassword1.getText());
                db = c.getDb();
                taResults.append("successful in connection");
                }
                catch (Exception e){
                   taResults.append("error in connection");
                }
            }
        });
        
        btnExecute2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

            }
        });
        
        btnExecute3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

            }
        });
        
        btnExecute4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                    Object o = db.execQuery(taQuery.getText());
                    
                    try{
                        String s = (String) o;
                        taResults.append(s);
                    }
                    catch(Exception e){
                        
                        try{
                             int u = (Integer) o;
                            taResults.append(u + "rows updated");
                        }
                        catch(Exception exc){
                                ResultSet rs = (ResultSet) o;
                                taResults.append("Successfull execution\n");
                                try
                                {
                                   rs.next();
                                   String file = rs.getString(1);
                                   //taResults.append("File: " + file + "\n");
                                   //db
                                   String pos = rs.getString(2);
                                   //taResults.append("Position: " + pos +"\n");
                                   tfServer.setText(db.getServer());
                                   tfFile.setText(file);
                                   tfPosition.setText(pos);
                                } catch (SQLException ex)
                                {
                                    Logger.getLogger(MyPanel.class.getName()).log(Level.SEVERE, null, ex);
                                }
                        }
                        
                        
                    }
            }
        });
        
        btnConnectMaster.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JFrame frame = new JFrame("InputDialog Example #1");
                String currentMaster = (String) JOptionPane.showInputDialog(frame, 
                "Connecting to which master?",
                "Select a Master",
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                nodes, 
                nodes[0]);
                c.setCurrentMaster(currentMaster);
                    String query = "STOP SLAVE;";
//                            + " CHANGE MASTER TO MASTER_HOST='" + tfServer.getText() + "',"
//                            + " MASTER_USER = 'replicator',"
//                            + " MASTER_PASSWORD = 'pass',"
//                            + " MASTER_LOG_FILE = '" + tfFile.getText() + "',"
//                            + " MASTER_LOG_POS = " + tfPosition.getText() + ";"
//                            + " START SLAVE;"; 
                    System.out.println(query);
                    db.execQuery(query);
                    query = "CHANGE MASTER TO MASTER_HOST = '" + tfServer.getText() +  "', "
                            + "MASTER_USER = 'replicator', "
                            + "MASTER_PASSWORD = 'pass',"
                            + " MASTER_LOG_FILE = '" + tfFile.getText() + "',"
                            + " MASTER_LOG_POS = " + tfPosition.getText() +  ";";
                    System.out.println(query);
                    db.execQuery(query);
                     query = "START SLAVE;";
                    System.out.println(query);
                    db.execQuery(query);
                    
            }
        });
        
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                taResults.setText("");
            }
        });   
        
        btnRestartServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                taResults.setText("");
            }
        });
    }
    
}
