FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /logs
VOLUME /mnt

ADD demoapi-rs.jar app.jar
RUN sh -c 'touch /app.jar'
ADD config.properties config.properties
RUN sh -c 'touch /config.properties'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
