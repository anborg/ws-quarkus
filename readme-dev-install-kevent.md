## Install Knative Eventing

### Select the version of Knative Eventing to install
export KNATIVE_EVENTING_VERSION="0.24.1"

### Install Knative Eventing in namespace knative-eventing

```
# wget -P ./${KNATIVE_EVENTING_VERSION} https://github.com/knative/eventing/releases/download/v$KNATIVE_EVENTING_VERSION/eventing-crds.yaml
# wget -P ./${KNATIVE_EVENTING_VERSION} https://github.com/knative/eventing/releases/download/v$KNATIVE_EVENTING_VERSION/eventing-core.yaml
# wget -P ./${KNATIVE_EVENTING_VERSION} https://github.com/knative/eventing/releases/download/v$KNATIVE_EVENTING_VERSION/in-memory-channel.yaml
# wget -P ./${KNATIVE_EVENTING_VERSION} https://github.com/knative/eventing/releases/download/v$KNATIVE_EVENTING_VERSION/mt-channel-broker.yaml

kubectl apply --filename ${KNATIVE_EVENTING_VERSION}/eventing-crds.yaml
kubectl wait --for=condition=Established --all crd

kubectl apply --filename ${KNATIVE_EVENTING_VERSION}/eventing-core.yaml

kubectl wait pod --timeout=-1s --for=condition=Ready -l '!job-name' -n knative-eventing

kubectl apply --filename ${KNATIVE_EVENTING_VERSION}/in-memory-channel.yaml

kubectl wait pod --timeout=-1s --for=condition=Ready -l '!job-name' -n knative-eventing

kubectl apply --filename ${KNATIVE_EVENTING_VERSION}/mt-channel-broker.yaml

kubectl wait pod --timeout=-1s --for=condition=Ready -l '!job-name' -n knative-eventing

```

## Deploy Knative Eventing Application

```shell
#Set the example Namspace
NAMESPACE=default


```