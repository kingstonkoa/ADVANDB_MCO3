package mastermastersql;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection
{
	private String url, username, password,ip;
	private boolean connectionStatus = false;
	private Connection connection;
	private static DBConnection instance;

	public DBConnection(String url, String username, String password)
	{
		this.url = "jdbc:mysql://"+url+":3306";
                //"jdbc:mysql://localhost:3306"
                this.ip = url;
                this.username = username;
                this.password = password;

                
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
                        System.out.println(this.url);
			this.connection = DriverManager.getConnection(this.url, this.username, this.password);
			connectionStatus = true;
		} catch (Exception e)
		{
                     e.printStackTrace();
		}

	}

	public boolean getConnectionStatus()
	{
		return this.connectionStatus;
	}

	public Connection getConnection()
	{
		return this.connection;
	}

        public String getIp()
        {
            return ip;
        }
        
        

}
