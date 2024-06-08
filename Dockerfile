# Base image
FROM maven:3.6.3-jdk-8

WORKDIR /usr/local/tomcat/webapps/

RUN git clone https://github.com/tuxwow/addressbook-cicd-project.git

WORKDIR /usr/local/tomcat/webapps/addressbook-cicd-project

RUN mvn compile

RUN mvn test

RUN mvn pmd:pmd

RUN mvn clean package

EXPOSE 8084


#CMD ["catalina.sh", "run"]


