FROM java:8-jre
MAINTAINER Anasov Sergey <sanasov@yandex.ru>

ADD ./build/libs/discovery-*.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/discovery-*.jar"]

EXPOSE 8761