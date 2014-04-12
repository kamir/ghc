package org.etosha.hdgs.impala;

import app.SimpleGraphExporter;
import org.etosha.hdgs.hive.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.gephi.io.database.drivers.SQLDriver;
import org.gephi.io.database.drivers.SQLUtils;
import org.openide.util.Exceptions;

/**
 *
 * @author Mirko Kämpf
 */
public class ImpalaJDBCDriver implements SQLDriver {
    
    private static final String IMPALAD_HOST = SimpleGraphExporter.HIVE_SERVER2_IP;
    private static final String IMPALAD_JDBC_PORT = "21050";
    private static final String CONNECTION_URL = "jdbc:hive2://" + IMPALAD_HOST + ':' + IMPALAD_JDBC_PORT + "/;auth=noSasl";
    private static final String JDBC_DRIVER_NAME = "org.apache.hive.jdbc.HiveDriver";
 
    static Connection con = null;
            
    public ImpalaJDBCDriver() {
      System.out.print("> Load the Impala-Hive JDBC Driver ... ");
      try {
            Class.forName(JDBC_DRIVER_NAME);

            con = DriverManager.getConnection(CONNECTION_URL);
            
            System.out.println("done.");
      } 
      catch (ClassNotFoundException e) {
            e.printStackTrace();
      } 
      catch (SQLException ex) {  
            Exceptions.printStackTrace(ex);
      }  
    }
    
    public Connection getConnection(String connectionUrl, String username, String passwd) throws SQLException {
        System.out.println("> Impala Hive JDBC connection requested ... ");
        System.out.println("> " + CONNECTION_URL );
        return con;
    }

    @Override
    public String getPrefix() {
        return "hive2";
    }

    @Override
    public String toString() {
        return "Impala-Hive:JDBC";
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
