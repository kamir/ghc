ghc
===

gephi-hadoop-connector

Gephi () is my tool of choice for graph visualization. To load data from Hadoop into Gephi,
the Gephi-Hadoop-Connector uses the JDBC-Interface, which is provided by Impala and Hive, to 
load edge- and node-lists.

One important feature in Gephi is: it supports time dependent analysis and visualisation of networks.
To build such a timeline, an individual query can be defined for each single time frame of each individual layer.
If data is already partitioned by time the whole procedure is really efficient. 

How to handle all this metadata of a time dependent multilayer graph? 
Therefore we use the Etosha-Metastore, which will be released soon.

