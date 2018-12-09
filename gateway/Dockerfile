FROM java:8-jre
MAINTAINER Anasov Sergey <sanasov@yandex.ru>

ADD ./build/libs/gateway-*.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/gateway-*.jar"]

EXPOSE 4000