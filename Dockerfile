FROM gradle:8-jdk17 AS build
WORKDIR /app/

COPY build.gradle settings.gradle ./
COPY backend/build.gradle ./backend/

COPY backend/src ./backend/src

RUN gradle :backend:war --no-daemon


FROM quay.io/wildfly/wildfly:26.1.2.Final-jdk17

COPY --from=build /app/backend/build/libs/backend.war /opt/jboss/wildfly/standalone/deployments/WebLab4.war
COPY standalone.xml /opt/jboss/wildfly/standalone/configuration/standalone.xml
COPY postgresql-42.7.2.jar /opt/jboss/wildfly/standalone/deployments/