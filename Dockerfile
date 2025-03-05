# Use Tomcat 10 with Java 17
FROM tomcat:10.1.16-jdk17


# Define Tomcat home
ENV CATALINA_HOME /usr/local/tomcat
WORKDIR $CATALINA_HOME

# Copy WAR file to Tomcat webapps
COPY target/Event-Registration-System-1.0-SNAPSHOT.war $CATALINA_HOME/webapps/Event-Registration-System.war

# Expose port 8080
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]
