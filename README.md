Gephi-Hadoop-Connector :: Version 2
===================================

Gephi is my tool of choice for graph visualization. Hadoop stores all the data, especially the graph
data, which is processed in GraphX or Apache Giraph. Now we need an integration between the tools running on the
Workstation and the cluster. Loading a graph, just by a name in a well defined format to the workstation would be fine. Hadoop should filter the graph and before we load it, we should calculate some edge and node properties.

We can also load the graph data from Hadoop into Gephi instead of manualy handling all the files. It worked well in the beginning, but on the long run this will be no solution

I created the Gephi-Hadoop-Connector, which uses the JDBC-Interface provided by Impalaand Hive, to load edge- and node-lists. It works also with SQL databases.

One important feature in Gephi is: it supports time dependent analysis and visualisation of networks.
To build such a timeline, an individual query can be defined for each single time frame of each individual layer.
If data is already partitioned by time the whole procedure is really efficient. 

How to handle all this metadata of a time dependent multilayer graph? 
Therefore we use the Etosha-Graph-Metastore, which will be released soon.

<b>Gephi is built on top of the NetBeans Application Framework. So the connector was also built as a NetBeans project</b>.

First you need the "Gephi-Plugins-Bootstrap" project. Please clone it or check it out from here:

  https://github.com/kamir/gephi-plugins-bootcamp

Now you clone this project and you open it with NetBeans 7.4 or higher, and all dependencies for a CDH5 clusters are preconfigured.

