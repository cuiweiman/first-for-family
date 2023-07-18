FROM openjdk:17

RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo "Asia/Shanghai">/etc/timezone
ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en

RUN mkdir -p /home/family/first-k8s-client
COPY target/first-k8s-client-1.0-SNAPSHOT.jar /home/family/first-k8s-client/
USER root
WORKDIR /home/family/first-k8s-client

VOLUME ["/Users/cuiweiman/family/first-k8s-client"]

EXPOSE 8896
RUN bash -c "touch /home/family/first-k8s-client/first-k8s-client-1.0-SNAPSHOT.jar"

ENTRYPOINT ["sh", "-c", "java -jar ${JAVA_OPTS} /home/family/first-k8s-client/first-k8s-client-1.0-SNAPSHOT.jar ${0} ${@}"]

#容器启动命令
#docker run --name first_k8s_client -p:8896:8896 --restart=on-failure:3 \
# -e "JAVA_OPTS=-Xms2G -Xmx2G -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tmp/first-k8s-client.hprof -Dspring.profiles.active=default" \
# -d first_k8s_client:1.0
