FROM tomcat
COPY /target/CMSS_Projet-0.0.3-Beta.war /usr/local/tomcat/webapps
CMD ["catalina.sh", "run"]
