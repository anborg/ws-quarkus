
# Build the native linux binary.
mvn package -Pnative -Dquarkus.native.container-build=true -Dquarkus.native.container-runtime=docker -DskipTests
#mvn package -Pnative -Dquarkus.native.container-build=true -Dquarkus.native.container-runtime=podman

# Build container from Dockerfile
docker build -f src/main/docker/Dockerfile.native -t docker.io/myorg/ws-quarkus-native .
