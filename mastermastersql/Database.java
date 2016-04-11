package mastermastersql;


import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database 
{
	private DBConnection connect;
        private ResultSet result;
        private int tableLocked = 0;

    public Database(String url, String username, String password)
    {
        connect = new DBConnection(url, username, password);
	connect.getConnection();
    }

    public String getServer(){
        return connect.getIp();
    }
        
    public int getTableLocked() {
        return tableLocked;
    }
    
    //function to split the queries
    public ArrayList<Object> SplitQueries(String q){
        ArrayList<Object> resultList = new ArrayList<> ();
        String[] tempQueries = q.split(";");
        for(int i = 0; i < tempQueries.length; i++)
        {
            System.out.print(tempQueries[i]);
            resultList.add(execQuery(tempQueries[i]));
        }
        return resultList;
    }
    
    public Object execQuery(String q)
    {
        ResultSet rs;
        try{
            int updateResult;
            PreparedStatement statement;
            statement = connect.getConnection().prepareStatement(q);
            statement.setQueryTimeout(20);
            
            //String[] temp = q.split(" ");
            String tableName = "hpq_crop";
            
            //check if table is locked and then send message if it locked
            if(checkTable())
                return "Table " + tableName +" is currently locked. \n Please try again after a few seconds";
                
            else if(q.contains("UPDATE") || q.contains("update"))
            {
                //execute the write lock
                String prequery = "LOCK TABLE  " + tableName + " WRITE;";
                PreparedStatement LockStatement;
                LockStatement = connect.getConnection().prepareStatement(prequery);
                LockStatement.setQueryTimeout(20);
                LockStatement.execute();
                System.out.println("locked");
                
                tableLocked = 1; // global variable to be used to check if the table is locked or not
                //delay for 2 seconds
                try {
                    Thread.sleep(2000); //delay of 2 seconds
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                
                statement.execute();
                updateResult = statement.executeUpdate();
                System.out.println("inside middle");
                //execute the write unclock
                prequery = "UNLOCK TABLES;";
                PreparedStatement UnlockStatement;
                UnlockStatement = connect.getConnection().prepareStatement(prequery);
                UnlockStatement.setQueryTimeout(20);
                UnlockStatement.execute();
                
                tableLocked = 1;
                
                return updateResult;
                
            }
            else if (q.contains("USE") || q.contains("use"))
            {
                System.out.println("Query execution sucessfull."); 
                rs = statement.executeQuery();
                this.result = rs;  
                return "Query execution sucessfull.";
            }
            else if (q.contains("CHANGE") || q.contains("change"))
            {
                System.out.println("Query execution sucessfull."); 
                rs = statement.executeQuery();
                this.result = rs;  
                return "Query execution sucessfull.";
            }
            
            else if (q.contains("master") || q.contains("MASTER"))
            {
                System.out.println("Query execution sucessfull."); 
                rs = statement.executeQuery();
                this.result = rs;  
                return result;
            }
            else {
                
                //execute the read lock
                String prequery = "LOCK TABLE " + tableName + " READ;";
                PreparedStatement LockStatement;
                LockStatement = connect.getConnection().prepareStatement(prequery);
                LockStatement.setQueryTimeout(20);
                LockStatement.execute();
                
                tableLocked = 1; // global variable to be used to check if the table is locked or not
                //delay for 2 seconds
                try {
                    Thread.sleep(2000); //delay of 2 seconds
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                
                System.out.println("Query execution sucessfull."); 
                rs = statement.executeQuery();
                this.result = rs;  
                
                
                //execute the read unlock
                prequery = "UNLOCK TABLES;";
                PreparedStatement UnlockStatement;
                UnlockStatement = connect.getConnection().prepareStatement(prequery);
                UnlockStatement.setQueryTimeout(20);
                UnlockStatement.execute();
                
                tableLocked = 0;
                return result ;           }
            
            
           
            
            //int rowCount = 0;
            
//            while(rs.next()){
//                System.out.println("Data: " + rs.getString(1));
//                rowCount++;
//            } 
//            System.out.println("Row Count: " + rowCount);
//            System.out.println("Execution Time: " + q.getExecTime() + " ms");
            
          
                
            
            
        }
        catch(SQLException e) {
            System.out.println("Error in executing query");
            e.printStackTrace();
            return "Error in executing query";
        }
    }

    private boolean checkTable() {
        
        String query = "SHOW OPEN TABLES WHERE `Table` LIKE '%crop%' AND `Database` LIKE 'db_hpq_palawan' AND In_use > -1;";
        
            try {
                PreparedStatement statement;
                 statement = connect.getConnection().prepareStatement(query);
                statement.setQueryTimeout(20);
                
                ResultSet tablecheck = statement.executeQuery();
                
                tablecheck.next();
                if(tablecheck.getString(3).equalsIgnoreCase("1"))
                    return true;
                else return false;
                
            } catch (SQLException ex) {
                System.out.println("check table error");
            }
            return false;
        }
            
 }