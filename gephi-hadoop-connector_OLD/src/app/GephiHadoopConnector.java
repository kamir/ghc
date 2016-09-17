package app;

import org.etosha.hdgs.hive.HiveImportConnector;
import org.etosha.hdgs.impala.ImpalaImportConnector;
import org.etosha.hdgs.mysql.MySQLImportConnector;

/**
 *
 * @author webex
 */
public class GephiHadoopConnector {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        MySQLImportConnector.main( args );
        
        HiveImportConnector.main( args );
        
        ImpalaImportConnector.main( args );

    }
    
}
