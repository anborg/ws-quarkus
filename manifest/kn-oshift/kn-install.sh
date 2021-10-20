
# Install kn-serving
oc apply -f kn-serving.yaml
#Toverifytheinstallationiscomplete
oc get knativeserving.operator.knative.dev/knative-serving -n knative-serving -- template='{{range .status.conditions}}{{printf "%s=%s\n" .type .status}}{{end}}'
#CheckthattheKnativeServingresourceshavebeencreate
oc get pods -n knative-serving

#Install kn-app-hello
oc apply -f kn-app1-hello.yaml
kn service describe --verbose kn-helloworld


#output yaml
kn service describe hello-kn-event -o yaml
#print url only
kn service describe hello-kn-event -o url







# Install jaggar for tracing TODO problem
oc apply -f kn-tracing-jaeger-inmem.yaml
#Getthehostnameofthejaegerroute
oc get route jaeger

# self signed certs?!
# see custom-certs.yml

#########  Install kn-eventing
oc apply -f kne-eventing.yaml
#Verifytheinstallationiscompletebyentering
oc get knativeeventing.operator.knative.dev/knative-eventing \
-n knative-eventing \
--template='{{range .status.conditions}}{{printf "%s=%s\n" .type .status}}{{end}}'
oc get pods -n knative-eventing

# ----- broker ---
# check if there is a broker
kn broker list
# create broker
kn broker create default
kn broker describe default
# event source
kn source list --type PingSource

#
oc apply -f kne-sink-ping-eventprinter.yaml
oc apply -f kne-source-ping.yaml
kn source list --type PingSource
kn source ping describe kne-test-ping-source

# --event watch ---
#By default, Knative services terminate their pods if no traffic is received within a 60 second period.
#Watch for new pods created:
watch oc get pods
#look at the logs of the created pod
oc logs $(oc get pod -o name | grep kne-sink-hello-v1) -c user-container


# InatLL kne- Kafka
oc apply -f kne-kafka.yaml



#------------ notes move out of install --------
#openshift command
oc get ksvc hello-kn-event
SERVICEURL=$(kn service describe hello-kn-event -o url)
curl $SERVICEURL

# with nttps you may wnat to use insecure
curl https://hello-kn-event-default.example.com --insecure

#To use a local cacert
curl https://event-delivery-default.example.com --cacert <file

# Interacting with a serverless application using HTTP2 and gRPC
oc -n knative-serving-ingress get svc kourier
