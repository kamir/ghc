Gephi-Hadoop-Connector :: Version 5
===================================

Gephi is now available in version 0.9.1 with an optimized plugin mechanism. Since Gephi is my tool of choice for graph visualization and Hadoop stores all my data, especially the time series from which I generate the graph data, which is than  processed in GraphX (also using GraphFrames) I need a direct integration between Gephi and Hadoop. 

##Some Background
In general such an integration between analysis tools running on a workstation and the cluster which keeps the data is done on the file or file system level. This allows loading a graph from a remote location in a transparent way. Using APIs it possible to handle data on a more abstract level. A JDBC connector and an SQL query are used instead of a filename to get required node and edge data into Gephi.

The Gephi-Hadoop-Connector goes beyond that. First, you can use a file from HDFS to load edge and node data. Second, using a SQL query and the Impala connector allows you to filter huge graphs in Hadoop or even to merge multiple data sets on the fly, before you bring it into Gephi. Furthermore, the Apache Spark connector enables cluster side processing of graphs, which are to large for Gephi. 

This direct interaction between Gephi and Hadoop clusters simplifies the task of network analysis.

Initially I created the Gephi-Hadoop-Connector just using a JDBC-Interface to use Hive and Impala. Now, we integrate the Etosha metadata toolbox for linking data sets and graph and metadata exposure to external tools.

Another important feature in Gephi is the support of time dependent graphs. This means, a topological analysis and visualisation of networks over time is possible in one tool while all the huge datasets still sit in a scalable environment.
Build a timeline to present the topology of a complex systems becomes an easy task now. For each time interval, we can specify an individual query. In this way, even data from very different sources can be merged based on mappings included in the layer defining queries. This procedure becomes even more efficient, if data is already partitioned by time. Also the data slices, provided by Spark Streaming fit well into this framework.

One important question is still: How do I handle a multilayer time dependent graph? There is a lot of metadata required.<b>The Etosha data link engine is created for this purpose.</b> 

##Quickstart:
In order to test this tool (we are currently in a POC status) you should clone this project including the sub-modules.

```bash
  git clone https://github.com/kamir/ghc
  cd ghc
  git submodule update --init --recursive
```

Next, run the <i>bootstrap.sh</i> script in the main folder. It installs some jar files, not available in public repositories and prepares your system for building the tool. Now you can compile and package the project "gephi-plugins", and start the freshly build plugin using maven:

```bash
  cd git-modules
  cd gephi-plugins
  mvn clean compile package -DskipTests
  mvn org.gephi:gephi-maven-plugin:run
```

The <i>run.sh</i> script in the main folder makes this step easy.

Have fun with Gephi and all your huge graphs!

