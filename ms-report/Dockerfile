FROM java:8-jre
MAINTAINER Anasov Sergey <sanasov@yandex.ru>

ADD ./build/libs/gateway-*.jar /app.jar
CMD java $JAVA_MEM $JAVA_EXT -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=4000,suspend=n -jar /app.jar

EXPOSE 4000