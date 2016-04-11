/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mastermastersql;

import java.util.ArrayList;

/**
 *
 * @author Kingston
 */
public class Controller
{
    private Driver mp;
    private Database db;
    private MyPanel mainPanel;
    private String currentNode;
    private String currentMaster;
    private String centralIP;
    private String palawanIP;
    private String marinduqueIP;

    
    public void connectToServer(String url, String username, String password)
    {
       
        try{
             db = new Database(url,username,password);
        }
        catch(Exception e){
            System.out.printf("db error");
        }
    }

    public void setMP(Driver mp)
    {
        this.mp = mp;
    }
   
    public void setMainPanel(MyPanel myPanel)
    {
        this.mainPanel = myPanel;
    }

    public Database getDb()
    {
        return db;
    }

    public void setCurrentNode(String currentNode)
    {
        this.currentNode = currentNode;
    }
    
    public String getCurrentNode()
    {
        return this.currentNode;
    }

    public void setCurrentMaster(String currentMaster)
    {
        this.currentMaster = currentMaster;
    }
    
    public String getCurrentMaster()
    {
        return this.getCurrentMaster();
    }

    public String getCentralIP()
    {
        return centralIP;
    }

    public void setCentralIP(String centralIP)
    {
        this.centralIP = centralIP;
    }

    public String getPalawanIP()
    {
        return palawanIP;
    }

    public void setPalawanIP(String palawanIP)
    {
        this.palawanIP = palawanIP;
    }

    public String getMarinduqueIP()
    {
        return marinduqueIP;
    }

    public void setMarinduqueIP(String marinduqueIP)
    {
        this.marinduqueIP = marinduqueIP;
    }
    
   
}
