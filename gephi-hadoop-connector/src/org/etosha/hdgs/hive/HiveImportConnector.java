/*
Copyright 2008-2010 Gephi
Authors : Mathieu Bastian <mathieu.bastian@gephi.org>
Website : http://www.gephi.org

This file is part of Gephi.

Gephi is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

Gephi is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with Gephi.  If not, see <http://www.gnu.org/licenses/>.
*/
package org.etosha.hdgs.hive;

import app.SimpleGraphExporter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.etosha.HadoopImporterToolAction;
import org.etosha.datamodel.MultiLayerNetwork;
import org.etosha.datamodel.NetworkLayer;
import org.gephi.data.attributes.api.AttributeController;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.Node;
import org.gephi.graph.api.UndirectedGraph;
import org.gephi.io.database.drivers.MySQLDriver;
import org.gephi.io.database.drivers.SQLUtils;
import org.gephi.io.importer.api.Container;
import org.gephi.io.importer.api.EdgeDefault;
import org.gephi.io.importer.api.ImportController;
 
import org.gephi.io.importer.plugin.database.ImporterEdgeList;
import org.gephi.io.processor.plugin.DefaultProcessor;
import org.gephi.layout.plugin.force.StepDisplacement;
import org.gephi.layout.plugin.force.yifanHu.YifanHuLayout;
import org.gephi.project.api.Project;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.openide.util.Lookup;

/**
 * This demo shows how to import data from a MySQL database. The database format
 * must be "Edge List", basically a table for nodes and a table for edges.
 * <p>
 * To be found by the importer, you need to have following columns:
 * <ul><li><b>Nodes:</b> ID and LABEL</li>
 * <li><b>Edges:</b> SOURCE, TARGET and WEIGHT</li></ul>
 * Any other column will be imported as attributes. Other recognized columns are
 * X, Y and SIZE for nodes and ID and LABEL for edges.
 * <p>
 * A possible toolkit use-case is a layout server. Therefore this demo layout
 * the network imported from the database, layout it and update X, Y columns to
 * the database.
 * 
 * @author Mathieu Bastian
 */
public class HiveImportConnector {
    
    
    
    /* for Impala :
    
// here is an example query based on one of the Hue Beeswax sample tables
    private static final String SQL_STATEMENT = "select * from employees";
// set the impalad host
    private static final String IMPALAD_HOST = "localhost";
// port 21050 is the default impalad JDBC port
    private static final String IMPALAD_JDBC_PORT = "21050";
    private static final String CONNECTION_URL = "jdbc:hive2://" + IMPALAD_HOST + ':' + IMPALAD_JDBC_PORT + "/;auth=noSasl";
    private static final String JDBC_DRIVER_NAME = "org.apache.hive.jdbc.HiveDriver";

    
    */
    
    public static void main(String[] args) {
        HiveImportConnector tool = new HiveImportConnector();
        tool.script();
    }

    public void script() {
        
        NetworkLayer nl = MultiLayerNetwork.getSelected();
        
        //Init a project - and therefore a workspace
        ProjectController pc = HadoopImporterToolAction.pc;
        Project pro = pc.getCurrentProject();
        Workspace workspace = pc.newWorkspace(pro);

        //Get controllers and models
        ImportController importController = Lookup.getDefault().lookup(ImportController.class);
        GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getModel();
        AttributeModel attributeModel = Lookup.getDefault().lookup(AttributeController.class).getModel();

        //Import database
        EdgeListHiveDatabaseImpl db = new EdgeListHiveDatabaseImpl();
        
        db.setDBName("default"); // !!!
        db.setHost( SimpleGraphExporter.HIVE_SERVER_IP );
        db.setPort(10000); // !!!
        
        db.setUsername("kamir");
        db.setPasswd("8cwrr");
        
        db.setSQLDriver(new HiveJDBCDriver());
        
        String tab = "edgelist_es_de_erfurt2";
        
        db.setNodeQuery("SELECT UNIQUE( " + tab + ".source ) AS id, " + tab + ".source AS label FROM " + tab);
        db.setEdgeQuery("SELECT " + tab + ".source AS source, " + tab + ".target AS target " + tab + ".q0 AS weight FROM " + tab);
        
        ImporterEdgeList edgeListImporter = new ImporterEdgeList();
        
        Container container = null;
        
        String url = SQLUtils.getUrl(db.getSQLDriver(), db.getHost(), db.getPort(), db.getDBName());
        
        System.out.println( url );
        
        javax.swing.JOptionPane.showMessageDialog(null,"Go ... Hive! \n> " + url );
        
                
        try {
            container = importController.importDatabase(db, edgeListImporter);

            container.setAllowAutoNode(false);      //Don't create missing nodes
            container.getLoader().setEdgeDefault(EdgeDefault.UNDIRECTED);   //Force UNDIRECTED

            //Append imported data to GraphAPI
            importController.process(container, new DefaultProcessor(), workspace);
        }
        catch (Exception ex ) {
            ex.printStackTrace();
        }
        finally {
            if ( container != null )
                System.out.println( container.getReport().getText() );
        }
        
        
        
        //See if graph is well imported
        UndirectedGraph graph = graphModel.getUndirectedGraph();
        System.out.println("Nodes: " + graph.getNodeCount());
        System.out.println("Edges: " + graph.getEdgeCount());

        //Layout - 100 Yifan Hu passes
//        YifanHuLayout layout = new YifanHuLayout(null, new StepDisplacement(1f));
//        layout.setGraphModel(graphModel);
//        layout.resetPropertiesValues();
//        layout.initAlgo();
//        for (int i = 0; i < 100 && layout.canAlgo(); i++) {
//            layout.goAlgo();
//        }
//        layout.endAlgo();


    }
}
