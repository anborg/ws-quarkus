#./mvnw package

docker build -f src/main/docker/Dockerfile.jvm -t ws-quarkus-rest:1.0.0-SNAPSHOT .
