# Base image
FROM maven:3.6.3-jdk-8
# Install Git and Maven (using package manager)

#RUN apt-get update && \

RUN  apt-get install -y tomcat 



# Clone the repository

WORKDIR /usr/local/tomcat/webapps/

RUN git clone https://github.com/tuxwow/addressbook-cicd-project.git



# Switch to project directory

WORKDIR /usr/local/tomcat/webapps/addressbook-cicd-project



# Build and test with Maven

RUN mvn compile

RUN mvn test

RUN mvn pmd:pmd

RUN mvn clean package



# Copy the WAR file (adjust path if needed)

ADD ./target/addressbook.war /usr/local/tomcat/webapps/



# Expose the port

EXPOSE 8084



# Start Tomcat

CMD ["catalina.sh", "run"]


