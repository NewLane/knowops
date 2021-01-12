##--------------------------------------------------------------
##
##  realMethods Confidential
##  
##  2021 realMethods, Inc.
##  All Rights Reserved.
##  
##  This file is subject to the terms and conditions defined in
##  file 'license.txt', which is part of this source code package.
##   
##  Contributors :
##        realMethods Inc - General Release
##--------------------------------------------------------------
FROM tomcat:9.0-jdk8

RUN apt-get update --fix-missing && \
apt-get install -y dos2unix && \
apt-get install -y nano

ARG tomcatRoot=/usr/local/tomcat

COPY catalina.properties ${tomcatRoot}/conf
COPY server.xml ${tomcatRoot}/conf
COPY /target/*.war ${tomcatRoot}/webapps

RUN dos2unix ${tomcatRoot}/conf/catalina.properties
RUN dos2unix ${tomcatRoot}/conf/server.xml

# make the app war the root war so all default requests are directed to it
RUN mv ${tomcatRoot}/webapps/realmethods-1.2.war ${tomcatRoot}/webapps/ROOT.war
# RUN mv ${tomcatRoot}/webapps/ROOT ${tomcatRoot}/webapps/ROOT_OLD

# run tomcat
# CMD ["/usr/local/tomcat/bin/catalina.sh", "run"]
