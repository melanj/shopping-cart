FROM openjdk:11-jdk-alpine
EXPOSE 8080
ARG JAR_FILE=target/shopping-cart.jar
ADD ${JAR_FILE} shopping-cart.jar
COPY tests.sh /run-tests.sh
RUN ["chmod", "+x", "/run-tests.sh"]
ENTRYPOINT ["java","-jar","/shopping-cart.jar"]