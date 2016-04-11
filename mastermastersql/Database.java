package mastermastersql;


import java.sql.*;

public class Database 
{
	private DBConnection connect;
        private ResultSet result;

    public Database(String url, String username, String password)
    {
        connect = new DBConnection(url, username, password);
	connect.getConnection();
    }

    public String getServer(){
        return connect.getIp();
    }
    public Object execQuery(String q)
    {
        ResultSet rs;
        try{
            int updateResult;
            PreparedStatement statement;
            statement = connect.getConnection().prepareStatement(q);
            statement.setQueryTimeout(20);
            
//            if(q.contains("UPDATE"))
//            {
//                updateResult = statement.executeUpdate();
//                return updateResult;
//            }
//            else {
                System.out.println("Query execution sucessfull."); 
                rs = statement.executeQuery();
                this.result = rs;  
                
                return result ;
//            }
            
            
           
            
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
}