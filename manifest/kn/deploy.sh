kubectl apply -f kn-hello.yml
#Wait for Knative Service to be Ready
kubectl wait ksvc hello --all --timeout=-1s --for=condition=Ready
#ee the URL where your Knative Service is hosted,
kn service list

#Get the URL of the new Service
SERVICE_URL=$(kubectl get ksvc hello -o jsonpath='{.status.url}')
echo $SERVICE_URL
#Test the App
curl $SERVICE_URL

#Check the knative pods that scaled from zero
kubectl get pod -l serving.knative.dev/service=hello

#Try the service url on your browser (command works on linux and macos)
open $SERVICE_URL

#Watch the pods and see how they scale down to zero after http traffic stops to the url
kubectl get pod -l serving.knative.dev/service=hello -w