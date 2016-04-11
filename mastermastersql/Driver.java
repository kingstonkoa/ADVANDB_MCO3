/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mastermastersql;

import java.awt.Dimension;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import tcp.Client;

/**
 *
 * @author Kingston
 */
public class Driver extends JFrame
{
    private JFrame frame;
    private JPanel panel;
    JTabbedPane tp;
    public static final String[] nodes = { "Central Office", "Palawan Branch", "Marinduque Branch" };
    
    public Driver()
    {
        //this.frame = new JFrame("Master Master Setup");
        setTitle("MC03");
        tp = new JTabbedPane();
        getContentPane().add(tp);
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        
    }

    public void setPanel(String title, JPanel panel)
    {
        //getContentPane().removeAll();
        //frame.getContentPane().add (panel);
        tp.add(title,panel);
        pack();
        setVisible (true);
        
        revalidate();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException
    {
        // TODO code application logic here
       Driver mp = new Driver();
       Controller controller = new Controller();
       controller.setMP(mp);

       
       MyPanel masterPanel = new MyPanel(controller);
       mp.setPanel("Setup",masterPanel);
       
      MyPanel2 panel2 = new MyPanel2(controller);
      //panel2.setPreferredSize (new Dimension (750, 600));
      mp.setPanel("Query",panel2);
       
       controller.setMainPanel(masterPanel);
       
       Client client = new Client(panel2, controller);
       panel2.setClient(client);
       
       JFrame frame = new JFrame("InputDialog Example #1");

       String currentNode = (String) JOptionPane.showInputDialog(frame, 
        "What is node is this machine?",
        "Select a Node",
        JOptionPane.QUESTION_MESSAGE, 
        null, 
        nodes, 
        nodes[0]);
       
       frame = new JFrame("InputDialog Example #1");

    // prompt the user to enter their name
        String centralIP = JOptionPane.showInputDialog(frame, "IP for Central");
        String palawanIP = JOptionPane.showInputDialog(frame, "IP for Palawan");
        String marinduqueIP = JOptionPane.showInputDialog(frame, "IP for Marinduque");
       controller.setCurrentNode(currentNode);
       controller.setCentralIP(centralIP);
       controller.setPalawanIP(palawanIP);
       controller.setMarinduqueIP(marinduqueIP);
       client.run();
       
       
       
    }
    
}
