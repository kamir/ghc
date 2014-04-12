ghc
===

gephi-hadoop-connector

Gephi is my tool of choice for graph visualization. And Hadoop stores all the data, especially the graph
data, which is processed in Giraph. 

I have to load data from Hadoop into Gephi all the time. Manual handling of all the files was not an option
on the long term. So created the Gephi-Hadoop-Connector, which uses the JDBC-Interface provided by Impala 
and Hive, to load edge- and node-lists.

One important feature in Gephi is: it supports time dependent analysis and visualisation of networks.
To build such a timeline, an individual query can be defined for each single time frame of each individual layer.
If data is already partitioned by time the whole procedure is really efficient. 

How to handle all this metadata of a time dependent multilayer graph? 
Therefore we use the Etosha-Graph-Metastore, which will be released soon.

