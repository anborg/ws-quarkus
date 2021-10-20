# Appteam-global, not APP specific. Could be "namespace" specific
#oc delete -f appteam/appteam01-image-repo-secret.yaml
#oc create -f appteam/appteam01-image-repo-secret.yaml
oc delete -f myteam-image-repo-secret.yaml
oc create -f myteam-image-repo-secret.yaml
