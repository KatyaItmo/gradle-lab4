FROM gradle:8-jdk17 AS build
WORKDIR /app/

ARG REVISION=HEAD

RUN apt-get update && apt-get install -y git
RUN git clone https://github.com/KatyaItmo/gradle-lab4.git . \
    && git checkout $REVISION

RUN gradle :backend:war --no-daemon
RUN gradle :backend:test --no-daemon

FROM quay.io/wildfly/wildfly:26.1.2.Final-jdk17

COPY --from=build /app/backend/build/libs/backend.war /opt/jboss/wildfly/standalone/deployments/WebLab4.war
COPY standalone.xml /opt/jboss/wildfly/standalone/configuration/standalone.xml
COPY postgresql-42.7.2.jar /opt/jboss/wildfly/standalone/deployments/