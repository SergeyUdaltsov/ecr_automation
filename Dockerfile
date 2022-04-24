FROM adoptopenjdk/openjdk11:ubi
VOLUME /tmp
COPY target/containers.jar /containers.jar
CMD ["java", "-jar", "/containers.jar"]