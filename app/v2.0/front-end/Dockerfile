FROM image-registry.openshift-image-registry.svc:5000/openshift/ubi8-openjdk-8:1.3

USER root
RUN mkdir /app

COPY target/*.jar /app/front-end.jar

USER 1001
EXPOSE 8080/tcp
ENTRYPOINT ["java", "-jar", "/app/front-end.jar"]
