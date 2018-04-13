FROM openjdk
RUN apt-get update && apt-get install -y netcat-openbsd
COPY build/libs/uni-master-monolith-som.jar /opt/uni-master-monolith-som/lib/
COPY entrypoint.sh /opt/uni-master-monolith-som/bin/
RUN chmod 755 /opt/uni-master-monolith-som/bin/entrypoint.sh

EXPOSE 8080

ENTRYPOINT ["/opt/uni-master-monolith-som/bin/entrypoint.sh"]