/*
 * The Hive-JDBC client ist an example application which shows
 * how to load data from an Hadoop cluster via the 
 * Hive-JDBC interface.
 * 
 * The required JDBC driver: 
 *       org.apache.hadoop.hive.jdbc.HiveDriver 
 * 
 * So we have a dependency to Hive libs.
 * 
 * 
 */
package org.etosha.hdgs.hive.tools;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;
import java.util.Vector;

public class ExperimentalHiveTableInspector {
    /**
     * 
     * The HiveTableInspector loads the table description and 
     * creates for each table a page with the name:
     *   
     * "hivetable_clustername_tablename" which is in a categories:
     *      HiveTableDataset 
     *      DataSet
     * 
     * It also adds a property which defines the relation between the dataset 
     * and the cluster, it is stored in. 
     * 
     */
    
  private static String driverName = "org.apache.hadoop.hive.jdbc.HiveDriver";

  /**
 * @param args
 * @throws SQLException
   */
  public static void main(String[] args) throws SQLException {
      
    try {
      Class.forName(driverName);
    } 
    catch (ClassNotFoundException e) {
      e.printStackTrace();
      System.out.println( ">>> The Hive-JDBC driver is not available!" );
      System.exit(1);
    }
//    String host = "localhost";  
    String host = "172.16.14.181";  
    
    String connectionStr = "jdbc:hive://"+host+":10000";
    System.out.println( ">>> " + connectionStr );
    
    Connection con = DriverManager.getConnection( connectionStr, "", "");
    
    Statement stmt = con.createStatement();
   
    Vector<String> tables = new Vector<String>();
    
    // show tables
    String sql = "show tables";
    System.out.println(">>> Execute : " + sql);
    ResultSet res = stmt.executeQuery(sql);
    while (res.next()) {
        
        String tn = res.getString(1);
        System.out.println("\t[tab] " +  tn);
        tables.add(tn);
    }
    
    for( String tableName : tables ) {
        
        HiveTableDataSetPage page = new HiveTableDataSetPage();
        page.cluster = host;
        page.tablename = tableName;
        
        StringBuffer schema = new StringBuffer();

        // describe table
        sql = "describe " + tableName;
        System.out.println("\n>>>> Execute : " + sql);
        res = stmt.executeQuery(sql);
        while (res.next()) {
          System.out.println("\t[col]" + res.getString(1) + "\t" + res.getString(2) + "\t" + res.getString(3));
          schema.append( "  [col]" + res.getString(1) + " :: " + res.getString(2) + " :: " + res.getString(3) + "\n");
        }

        page.schema = schema.toString();
        
        System.out.println( page.getPageName() );
        System.out.println( page.getPageContent() );
        System.out.println();
    }

  }
}

class HiveTableDataSetPage {

    String cluster = "default";
    String tablename = "?";
    String schema = "?";
    
    public String getPageContent(){
        String s = "===Hive Table Description===\n";
        s = s + "[[Category:HiveTableDataset]]\n";
        s = s + "[[Category:DataSet]]\n";
        s = s + "[[Is available in cluster::"+cluster+"]]\n";
        s = s + schema;
        return s;
    }
    
    
    public String getPageName(){
        return "hivetable_" + cluster + "_" + tablename;
    }
}
