FROM openjdk:8
ADD target/*.jar ecom-1.0.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","ecom-1.0.jar"]

# Checking Snyk Webhook Services
# Checking second time

