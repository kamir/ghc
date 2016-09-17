/*
 * Simple data model to handle multiple layers of a network.
 *
 * A Workspace in Gephi allows only on Network per Workspace, this
 * requires some changes, if we want to handle multi-layer networks
 * in the future.
 * 
 * Therefore we store all data in Hadoop and the metastore is aware of the
 * Layer structure and relations between layers, which are loaded to Gephi
 * individually. 
 *
 * E.g. we load a static network, calculate the layout, remove all edges and
 * load new edge definitions to plot this graph with the same node locations. 
 * This assumes, that no new nodes are added but this allows a visual 
 * comparison of multiple layers, which can be stored in the metastore and 
 * with a graphic tool, one can staple the layers to illustrate multilayer 
 * networks, even if they have many layers.
 *
 */

package org.etosha.datamodel;

import org.etosha.datamodel.NetworkLayer;

/**
 *
 * @author kamir
 */
public class MultiLayerNetwork {

    static NetworkLayer defaultLayer = null;
    
    public static void initNetworks() {
        defaultLayer = new NetworkLayer();
    }
    
    public static void setDefaultLayer( NetworkLayer nl ) {
        defaultLayer = nl;
    };
    
    public static NetworkLayer getSelected() {
        if ( defaultLayer == null ) initNetworks();
        return defaultLayer;
    }
    
}
