FROM tomcat:jdk15-openjdk

COPY target/aom.war /usr/local/tomcat/webapps/ao.war
