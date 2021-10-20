#https://medium.com/@pjbgf/moving-docker-images-from-one-container-registry-to-another-2f1f1631dc49
src_repo=den-org
src_img=ws-quarkus-rest
version=1.0.0-SNAPSHOT

org_registry=docker.io/myorg
gcp_registry=docker.io/myorg
img_target="${gcp_registry}/${src_img}:latest"
docker tag $src_repo/$src_img:$version $img_target
docker push $img_target
