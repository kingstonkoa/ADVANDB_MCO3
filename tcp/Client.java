/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mastermastersql.Controller;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import mastermastersql.MyPanel;
import mastermastersql.MyPanel2;
/**
 *
 * @author LUCKY
 */
public class Client {

    BufferedReader in;
    PrintWriter out;
    JFrame frame = new JFrame("Chatter");
    JTextField textField = new JTextField(40);
    JTextArea messageArea = new JTextArea(8, 40);
    private final MyPanel2 panel2;
    private final Controller c;
    private String server;
    private String file;
    private String position;

    /**
     * Constructs the client by laying out the GUI and registering a
     * listener with the textfield so that pressing Return in the
     * listener sends the textfield contents to the server.  Note
     * however that the textfield is initially NOT editable, and
     * only becomes editable AFTER the client receives the NAMEACCEPTED
     * message from the server.
     */
    public Client(MyPanel2 panel2, Controller c) {
        this.panel2 = panel2;
        this.c = c;
        // Layout GUI
        textField.setEditable(false);
        messageArea.setEditable(false);
        frame.getContentPane().add(textField, "North");
        frame.getContentPane().add(new JScrollPane(messageArea), "Center");
        frame.pack();

        // Add Listeners
        textField.addActionListener(new ActionListener() {
            /**
             * Responds to pressing the enter key in the textfield by sending
             * the contents of the text field to the server.    Then clear
             * the text area in preparation for the next message.
             */
            public void actionPerformed(ActionEvent e) {
                out.println(textField.getText());
                textField.setText("");
            }
        });
    }

    /**
     * Prompt for and return the address of the server.
     */
    private String getServerAddress() {
        return JOptionPane.showInputDialog(
            frame,
            "Enter IP Address of the Server:",
            "Welcome to the Chatter",
            JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Prompt for and return the desired screen name.
     */
    private String getName() {
        return c.getCurrentNode();
    }

    /**
     * Connects to the server then enters the processing loop.
     */
    public void run() throws IOException {

        // Make connection and initialize streams
        String serverAddress = getServerAddress();
        Socket socket = new Socket(serverAddress, 3003);
        in = new BufferedReader(new InputStreamReader(
            socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        // Process all messages from server, according to the protocol.
        while (true) {
            String line = in.readLine();
            if (line.startsWith("SUBMITNAME")) {
                out.println(getName());
            } else if (line.startsWith("NAMEACCEPTED")) {
                textField.setEditable(true);
            } else if (line.startsWith("MESSAGE")) {
                panel2.appendLog(line.substring(8) + "\n");
                String str = line.substring(8) + "\n";
                String[] parts = str.split(":", 2);
                //messageArea.append(line.substring(8) + "\n");
                System.out.println(parts[0]);
                if(!parts[0].equals("Central Office") && c.getCurrentNode().equals("Central Office")) // a Palawan or Marinduque message, and you are central
                {
                    //TODO CHANGE master to which ever
                    switch(parts[0])
                    {
                        case "Palawan Branch" : if(c.getCurrentMaster().equals("Palawan Branch"))
                                                break;
                                                c.connectToServer(c.getPalawanIP(), "replicator", "pass");
                                                getMasterStatus();
                                                c.connectToServer("localhost", "root", "");
                                                String query = "STOP SLAVE;";
                                                c.executeQuery(query);
                                                query = "CHANGE MASTER TO MASTER_HOST = '" + server +  "', "
                                                + "MASTER_USER = 'replicator', "
                                                + "MASTER_PASSWORD = 'pass',"
                                                + " MASTER_LOG_FILE = '" + file + "',"
                                                + " MASTER_LOG_POS = " + position +  ";";
                                                c.executeQuery(query);
                                                query = "START SLAVE;";
                                                c.executeQuery(query);
                                                c.setCurrentMaster("Palawan Branch");
                                                sendOut("Now Listening to Palawan");
                                                System.out.println("listening to Palawan");
                                                break;
                        case "Marinduque Branch" : 
                                                if(c.getCurrentMaster().equals("Marinduque Branch"))
                                                break;
                                                c.connectToServer(c.getMarinduqueIP(), "replicator", "pass");
                                                getMasterStatus();
                                                c.connectToServer("localhost", "root", "");
                                                String query2 = "STOP SLAVE;";
                                                c.executeQuery(query2);
                                                query2 = "CHANGE MASTER TO MASTER_HOST = '" + server +  "', "
                                                + "MASTER_USER = 'replicator', "
                                                + "MASTER_PASSWORD = 'pass',"
                                                + " MASTER_LOG_FILE = '" + file + "',"
                                                + " MASTER_LOG_POS = " + position +  ";";
                                                c.executeQuery(query2);
                                                query2 = "START SLAVE;";
                                                c.executeQuery(query2);
                                                c.setCurrentMaster("Marinduque Branch");
                                                sendOut("Now Listening to Marinduque");
                                                System.out.println("listening to Marinduque");
                                                break;
                                
                    }
                }
            }
        }
    }
    
    public void sendOut(String message)
    {
    out.println(message);
    }
    
    public void getMasterStatus()
    {
        
        String query = "show master status;";
        Object o = c.getDb().execQuery(query);
        ResultSet rs = (ResultSet) o;
        System.out.println("Successfull master status execution\n");
        try
        {
           rs.next();
           this.file = rs.getString(1);
           //taResults.append("File: " + file + "\n");
           //db
           this.position = rs.getString(2);
           //taResults.append("Position: " + pos +"\n");
           this.server = c.getDb().getServer();
        } catch (SQLException ex)
        {
            Logger.getLogger(MyPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Runs the client as an application with a closeable frame.
     */
//    public static void main(String[] args) throws Exception {
//        Client client = new Client();
//        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        client.frame.setVisible(true);
//        client.run();
//    }
}
