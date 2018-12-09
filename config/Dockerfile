FROM java:8-jre
MAINTAINER Anasov Sergey <sanasov@yandex.ru>

ADD ./build/libs/config-*.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/config-*.jar"]

EXPOSE 8888