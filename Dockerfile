FROM openjdk:14
VOLUME /tmp
EXPOSE 9090
COPY bank-backend-devops-1.0.0.jar /bank-backend-devops-1.0.0.jar
ENTRYPOINT ["java","-jar","bank-backend-devops-1.0.0.jar"]