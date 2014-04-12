/*
 *   A NetworkLayer is defined by a node and an edge list. For both we
 *   need an individual query to request data from a Hadoop Cluster or a
 *   MySQL database.
 *
 *   Later we support arbitrary data sources to load a layer.
 */

package org.etosha.datamodel;

import static org.etosha.hdgs.impala.ImpalaDynamicImportConnector.currentPartitionId;

/**
 *
 * @author kamir
 */
public class NetworkLayer {
    
    /**
     * 
     * The MultilayerFramework to which this layer belongs to is not known
     * at this level. The metastore cares about this data.
     * 
     */
    
    
    // identifier of the table, the layer is loaded from
    public String tableName = "edgelist_es_de_amoklauf_von_erfurt";

    public String nodelistQ = "SELECT " + tableName + ".source AS id, " + tableName + ".source AS label FROM " + tableName;

    public String edgelistQ = "SELECT " + tableName + ".source AS source, " + tableName + ".target AS target " + tableName + ".q0 AS weight FROM " + tableName;
    
    // partitioning is done via an optionla partition selector. A partition identifier
    // is added to the string which is added to the edgelist query, if partitioning is used.
    public String partition_selector = "WHERE month=";

    // correlation networks are not directed
    // static and dependency networks are directed
    public boolean directed = false;   
    
    
}
