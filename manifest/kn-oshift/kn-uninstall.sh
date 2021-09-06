#Deletetheknative-servingcustomresource
oc delete knativeservings.operator.knative.dev knative-serving -n knative-serving
#Afterthecommandhascompletedandallpodshavebeenremovedfromtheknative-serving namespace, delete the namespace
oc delete namespace knative-serving

#Delete the knative-eventing custom resource
oc delete knativeeventings.operator.knative.dev knative-eventing -n knative-eventing
#Afterthecommandhascompletedandallpodshavebeenremovedfromtheknative-eventing namespace, delete the namespace:
oc delete namespace knative-eventing


# To delete the remaining OpenShift Serverless CRDs, ente
oc get crd -oname | grep 'knative.dev' | xargs oc delete

