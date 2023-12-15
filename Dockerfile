FROM tomcat:9.0.83-jdk21

COPY /target/spring-1.war /usr/local/tomcat/webapps/