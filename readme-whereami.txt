s21 - error
- commented that plugin

OIDC error- not configuring it now
- comment
-  io.quarkus.runtime.configuration.ConfigurationException: 'quarkus.oidc.auth-server-url' property must be configured



ADD REDIS CLIENT
mvn quarkus:add-extension -Dextensions="redis-client"
quarkus.redis.hosts=redis://10:6378


- Knative eventing - FAILED

mvn quarkus:add-extension -Dextensions="openshift"
#
mvn clean package -Dquarkus.container-image.build=true
mvn package -Dquarkus.container-image.push=true

docker run -it -p 8080:8080 myorg/api-quarkus-rest:1.0.0-SNAPSHOT

oc login -u prem --server=https://api:6443

oc delete -f kn-api-deploy.yaml
oc apply -f kn-api-deploy.yaml
kn service list
kn service describe kn-api-quarkus-rest
oc logs deployment/kn-api-quarkus-rest-v1-deployment

