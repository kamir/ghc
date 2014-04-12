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
package org.etosha.hdgs.impala;

import app.SimpleGraphExporter;
import org.etosha.hdgs.hive.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import org.etosha.HadoopImporterToolAction;
import org.etosha.datamodel.MultiLayerNetwork;
import org.gephi.data.attributes.api.AttributeController;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.data.attributes.api.Estimator;
import org.gephi.data.attributes.type.DynamicInteger;
import org.gephi.data.attributes.type.Interval;
import org.gephi.graph.api.DirectedGraph;
import org.gephi.graph.api.Graph;
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
import org.gephi.io.processor.plugin.DynamicProcessor;
import org.gephi.layout.plugin.force.StepDisplacement;
import org.gephi.layout.plugin.force.yifanHu.YifanHuLayout;
import org.gephi.project.api.Project;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.openide.util.Lookup;

/**
 * This Connectoe loads edgelist and nodelist from hadoop clusters.
 * 
 * TODO: add GUI component to select / input the database and tablenames.
 * 
 * @author Mirko Kämpf
 * 
 */
public class ImpalaDynamicImportConnector {

    public static int currentPartitionId = 1;
    
    public static JTextPane jta = null;
    
    public static void main(String[] args) {
        ImpalaDynamicImportConnector tool = new ImpalaDynamicImportConnector();
        tool.script();
    }

    public void script() {
   
        //Init a project - and therefore a workspace
        ProjectController pc = HadoopImporterToolAction.pc;
        Project pro = pc.getCurrentProject();
        
        // we allwas load a new Workspace in an existing project ...
        Workspace workspace = pc.getCurrentWorkspace();
        
        //Get controllers and models
        ImportController importController = Lookup.getDefault().lookup(ImportController.class);
        GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getModel();
        AttributeModel attributeModel = Lookup.getDefault().lookup(AttributeController.class).getModel();

        //Import database
        EdgeListHiveDatabaseImpl db = new EdgeListHiveDatabaseImpl();
        
        db.setDBName(";auth=noSasl"); 
        db.setHost( SimpleGraphExporter.HIVE_SERVER_IP );
        db.setPort(21050);  
        
        db.setUsername("kamir");
        db.setPasswd("8cwrr");
        
        db.setSQLDriver(new ImpalaJDBCDriver());
        
        String tab = MultiLayerNetwork.getSelected().tableName;
        String nQ = MultiLayerNetwork.getSelected().nodelistQ;
        String eQ = MultiLayerNetwork.getSelected().edgelistQ + " " + MultiLayerNetwork.getSelected().partition_selector + currentPartitionId; ;
        
        db.setNodeQuery( nQ );
        db.setEdgeQuery( eQ );
        
        System.out.println( "EDGES\n" + eQ );
        System.out.println( "NODES\n" + nQ );
        
//        db.setNodeQuery("SELECT nodes.id AS id, nodes.label AS label, nodes.url FROM nodes");
//        db.setEdgeQuery("SELECT edges.source AS source, edges.target AS target, edges.label AS label, edges.weight AS weight FROM edges");
        
        int part = currentPartitionId;
        
        ImporterEdgeList edgeListImporter = new ImporterEdgeList();
        
        Container container = null;
        
        String url = SQLUtils.getUrl(db.getSQLDriver(), db.getHost(), db.getPort(), db.getDBName());
        
        System.out.println( url );
        
        //javax.swing.JOptionPane.showMessageDialog(null,"Go ... Impala! \n> " + url );
        log( "Go ... Impala! \n> " + url );

        container = importController.importDatabase(db, edgeListImporter);

        container.setAllowAutoNode( true );      //false = Don't create missing nodes
        
        if ( MultiLayerNetwork.getSelected().directed ) container.getLoader().setEdgeDefault(EdgeDefault.DIRECTED);   //Force DIRECTED
        else container.getLoader().setEdgeDefault(EdgeDefault.UNDIRECTED);   //Force UNDIRECTED

        //Append imported data to GraphAPI
        //importController.process(container, new DefaultProcessor(), workspace);
        
        //Initialize the DynamicProcessor - which will append the container to the workspace
        DynamicProcessor dynamicProcessor = new DynamicProcessor();
        dynamicProcessor.setDateMode(false);    //Set 'true' if you set real dates (ex: yyyy-mm-dd), it's double otherwise
        dynamicProcessor.setLabelmatching(true);   //Set 'true' if node matching is done on labels instead of ids

        //Set date for this file
        dynamicProcessor.setDate("" + part);

        //Process the container using the DynamicProcessor
        importController.process(container, dynamicProcessor, workspace);
        
        UndirectedGraph graph = graphModel.getUndirectedGraph();
        
        System.out.println("Nodes: " + graph.getNodeCount());
        System.out.println("Edges: " + graph.getEdgeCount());
        
        log("Partiton : " + part );
        log("Nodes    : " + graph.getNodeCount());
        log("Edges    : " + graph.getEdgeCount());
        
        
    }

    private void log(String string) {
        this.jta.setText( this.jta.getText() + "\n" + string );
    }
}
