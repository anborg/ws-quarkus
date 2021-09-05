export NAMESPACE=default
kubectl -n $NAMESPACE apply -f ke-broker.yml
kubectl -n $NAMESPACE get broker example-broker

#To deploy the hello-display consumer to your cluster,
kubectl -n $NAMESPACE delete -f ke-hello-display.yml
kubectl -n $NAMESPACE apply -f ke-hello-display.yml

#Create a trigger by entering
kubectl -n $NAMESPACE apply -f ke-hello-display-trigger.yml