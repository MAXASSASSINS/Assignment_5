cd ~/assignments

sudo rm -rf assignment_5

sudo cp -r "/mnt/c/Users/mohd.c.shadab/OneDrive - Accenture/Documents/Desktop/Docker and K8s/assignment_5" ~/assignments

cd assignment_5


docker build -t my-producer:v1 -f Dockerfile.producer .
docker build -t my-consumer:v1 -f Dockerfile.consumer .
docker build -t my-file-reader-writer:v1 -f Dockerfile.fileReaderWriter .
docker build -t my-postgres:v1 -f Dockerfile.postgres .

docker save my-consumer:v1 > ~/my-consumer.tar
microk8s ctr image import ~/my-consumer.tar

docker save my-producer:v1 > ~/my-producer.tar
microk8s ctr image import ~/my-producer.tar

docker save my-file-reader-writer:v1 > ~/my-file-reader-writer.tar
microk8s ctr image import ~/my-file-reader-writer.tar

docker save my-postgres:v1 > ~/my-postgres.tar
microk8s ctr image import ~/my-postgres.tar

microk8s kubectl apply -f k8s

microk8s kubectl logs -f deployment/my-producer
microk8s kubectl logs -f deployment/my-consumer
microk8s kubectl logs -f deployment/my-postgres

#microk8s kubectl exec -it deployment/redis -- sh

microk8s kubectl exec -it redis-0 -- sh

# KAFKA using HELM
microk8s helm3 repo add bitnami https://charts.bitnami.com/bitnami
microk8s helm3 repo update

microk8s helm3 install kafka bitnami/kafka \
  --set replicaCount=1 \
  --set zookeeper.enabled=true \
  --set listeners.client.protocol=PLAINTEXT \
  --set auth.enabled=false \
  --set externalAccess.enabled=false

microk8s helm3 uninstall kafka

# microk8s default storage path for pv
cd /var/snap/microk8s/common/default-storage/

# run interactive termianl
microk8s kubectl exec -it deployment/file-reader-writer -- bash

# once inside the file reader container bash
java -cp app.jar org.example.MyFileReaderWriter


sudo -i -u postgres
psql
