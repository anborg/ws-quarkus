Quarkus app Develop

```asciidoc
mvn compile quarkus:dev
#UI http://localhost:8080/q/dev/.

```


```
#kn-cli
brew install knative/client/kn
```




### Knative

```asciidoc

minikube config set cpus 4
minikube config set memory 8192
minikube update-check
minikube config set kubernetes-version v1.21.1
minikube delete
minikube start
#You need to do this to be able to use the EXTERNAL-IP for kourier Load Balancer service.
minikube tunnel

```


```asciidoc

# knative in minikube -  custom resources

kubectl apply -f serving-crds-v0.25.0.yaml

# knative in minikube -  core components of Knative Serving

#https://github.com/knative/serving/releases/download/v0.25.0/
kubectl apply -f serving-core-v0.25.0.yaml

#https://knative.dev/docs/admin/install/serving/install-serving-with-yaml/

```



Install Kourier: ISTIO DID NOT WORK - resource intensive for minikube

Kourier

```shell
#Install the Knative Kourier controlle

kubectl apply -f kourier-v0.25.0.yaml

#Configure Knative Serving to use Kourier by default 

kubectl patch configmap/config-network \
  --namespace knative-serving \
  --type merge \
  --patch '{"data":{"ingress.class":"kourier.ingress.networking.knative.dev"}}'

# Fetch the External IP address or CNAME by running t

kubectl --namespace kourier-system get service kourier

#Verify installation - Monitor the Knative components until all of the components show a STATUS of Running or Completed
kubectl get pods -n knative-serving

```

Configure dns
```asciidoc
#You can configure DNS to prevent the need to run curl commands with a host header.


```

curl -v -H "Host: kourier-internal.kourier-system.svc.cluster.local" http://10.107.225.253:30439


==========
Insastall knative : https://github.com/csantanapr/knative-minikube

export KNATIVE_VERSION="0.24.0"

# Install Knative Serving in namespace knative-serving

```
#wget -P ./${KNATIVE_VERSION} https://github.com/knative/serving/releases/download/v$KNATIVE_VERSION/serving-crds.yaml
#wget -P ./${KNATIVE_VERSION} https://github.com/knative/serving/releases/download/v$KNATIVE_VERSION/serving-core.yaml

kubectl apply -f ${KNATIVE_VERSION}/serving-crds.yaml
kubectl wait --for=condition=Established --all crd

kubectl apply -f ${KNATIVE_VERSION}/serving-core.yaml
kubectl wait pod --timeout=-1s --for=condition=Ready -l '!job-name' -n knative-serving > /dev/null

```
Install Kourier

```asciidoc
export KNATIVE_NET_KOURIER_VERSION="0.24.0"
#wget -P ./${KNATIVE_NET_KOURIER_VERSION} https://github.com/knative/net-kourier/releases/download/v$KNATIVE_NET_KOURIER_VERSION/kourier.yaml


#Install Knative Layer kourier in namespace kourier-system

kubectl apply -f ${KNATIVE_NET_KOURIER_VERSION}/kourier.yaml
kubectl wait pod --timeout=-1s --for=condition=Ready -l '!job-name' -n kourier-system
kubectl wait pod --timeout=-1s --for=condition=Ready -l '!job-name' -n knative-serving
#Set the environment variable EXTERNAL_IP to External IP Address of the Worker Node, you might need to run this command multiple times until service is ready.

EXTERNAL_IP=$(kubectl -n kourier-system get service kourier -o jsonpath='{.status.loadBalancer.ingress[0].ip}')
echo EXTERNAL_IP=$EXTERNAL_IP

#Set the environment variable KNATIVE_DOMAIN as the DNS domain using nip.io

KNATIVE_DOMAIN="$EXTERNAL_IP.nip.io"
echo KNATIVE_DOMAIN=$KNATIVE_DOMAIN

#Double-check DNS is resolving

dig $KNATIVE_DOMAIN

#Configure DNS for Knative Serving

kubectl patch configmap -n knative-serving config-domain -p "{\"data\": {\"$KNATIVE_DOMAIN\": \"\"}}"

#Configure Knative to use Kourier

kubectl patch configmap/config-network \
--namespace knative-serving \
--type merge \
--patch '{"data":{"ingress.class":"kourier.ingress.networking.knative.dev"}}'

# Verify that Knative is Installed properly all pods should be in Running state and our kourier-ingress service configured.

kubectl get pods -n knative-serving
kubectl get pods -n kourier-system
kubectl get svc  -n kourier-system

```
