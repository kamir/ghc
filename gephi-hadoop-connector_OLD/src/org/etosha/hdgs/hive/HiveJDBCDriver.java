package org.etosha.hdgs.hive;

import app.SimpleGraphExporter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.gephi.io.database.drivers.SQLDriver;
import org.gephi.io.database.drivers.SQLUtils;

/**
 *
 * @author Mathieu Bastian
 */
public class HiveJDBCDriver implements SQLDriver {

    private static String driverName = "org.apache.hadoop.hive.jdbc.HiveDriver";

    private static String conStr2 = "jdbc:hive://"+SimpleGraphExporter.HIVE_SERVER_IP+":10000/default";
    
    Connection con = null;
            
    public HiveJDBCDriver() {
      System.out.print("> Load the Hive JDBC Driver ... ");
      try {
          Class.forName(driverName);
          System.out.println("done.");
      } 
      catch (ClassNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }  
    }
    
    public Connection getConnection(String connectionUrl, String username, String passwd) throws SQLException {
        System.out.println("> Hive JDBC connection requested ... ");
        System.out.println("> " + connectionUrl );
  
        if (con == null ) con = DriverManager.getConnection( conStr2, "", "");
        return con;
    }

    @Override
    public String getPrefix() {
        return "hive";
    }

    @Override
    public String toString() {
        return "Apache Hive:JDBC";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof HiveJDBCDriver) {
            return ((HiveJDBCDriver) obj).getPrefix().equals(getPrefix());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return getPrefix().hashCode();
    }

   
    public boolean canUpdateFields() {
        return false;
    }
}
