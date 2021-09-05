

# These domain mappig files are not available online.
#NOT WORKING

#export KNATIVE_VERSION="0.24.0"
#wget -P ${KNATIVE_VERSION}/ https://github.com/knative/serving/releases/download/v$KNATIVE_VERSION/serving-domainmapping-crds.yaml
#wget -P ${KNATIVE_VERSION}/ https://github.com/knative/serving/releases/download/v$KNATIVE_VERSION/serving-domainmapping.yaml
#kubectl apply -f ${KNATIVE_VERSION}/serving-domainmapping-crds.yaml
#kubectl wait --for=condition=Established --all crd
#kubectl apply -f ${KNATIVE_VERSION}/serving-domainmapping.yaml


#Enable broker domain for DomainMapping
kubectl apply -f  kn-domainclaim-for-ke-broker.yml

# Expose broker externally using DomainMapping CRD on broker-ingress.knative-eventing.127.0.0.1.nip.io
kubectl -n knative-eventing apply -f kn-expose-domainmapping-of-ke-broker.yml

export NAMESPACE=default
curl -s -v  "http://broker-ingress.knative-eventing.127.0.0.1.nip.io/$NAMESPACE/example-broker" \
  -X POST \
  -H "Ce-Id: say-hello" \
  -H "Ce-Specversion: 1.0" \
  -H "Ce-Type: greeting" \
  -H "Ce-Source: not-sendoff" \
  -H "Content-Type: application/json" \
  -d '{"msg":"Hello Knative!"}'