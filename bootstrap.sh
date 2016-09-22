cd git-modules/access-impala/bin/

bash init-external-libs.sh

cd ../../../

cd git-modules/gephi-plugins/gephi-hadoop-connector/bin

bash init-external-libs.sh

cd ../../../..

cd git-modules
cd gephi-plugins

mvn clean compile package -DskipTests

mvn org.gephi:gephi-maven-plugin:run


