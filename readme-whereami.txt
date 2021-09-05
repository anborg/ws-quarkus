s21 - error
- commented that plugin

OIDC error- not configuring it now
- comment
-  io.quarkus.runtime.configuration.ConfigurationException: 'quarkus.oidc.auth-server-url' property must be configured



ADD REDIS CLIENT
mvn quarkus:add-extension -Dextensions="redis-client"
quarkus.redis.hosts=redis://10.16.0.140:6378


- Knative eventing - FAILED

mvn quarkus:add-extension -Dextensions="openshift"
#
mvn clean package -Dquarkus.container-image.build=true
mvn package -Dquarkus.container-image.push=true

oc login -u pnatar14 --server=https://api.sb104.caas.gcp.ford.com:6443

oc delete -f kn-app-deploy.yaml
oc apply -f kn-app-deploy.yaml
kn service list
kn service describe kn-caasdemo-quarkus-rest
oc logs deployment/kn-caasdemo-quarkus-rest-v1-deployment

