FROM adoptopenjdk/openjdk11:ubi
VOLUME /tmp
COPY target/containers.jar /containers.jar
EXPOSE 8080
CMD ["java", "-jar", "/containers.jar"]