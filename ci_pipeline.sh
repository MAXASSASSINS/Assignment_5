cd ~/assignments

sudo rm -rf assignment_5

sudo cp -r "/mnt/c/Users/mohd.c.shadab/OneDrive - Accenture/Documents/Desktop/Docker and K8s/assignment_5" ~/assignments

cd assignment_5

docker build -t my-producer:v2 -f Dockerfile.producer .
docker build -t my-consumer:v2 -f Dockerfile.consumer .
docker build -t my-postgres:v2 -f Dockerfile.postgres .

docker save my-consumer:v2 > ~/my-consumer.tar
microk8s ctr image import ~/my-consumer.tar

docker save my-producer:v2 > ~/my-producer.tar
microk8s ctr image import ~/my-producer.tar

docker save my-postgres:v2 > ~/my-postgres.tar
microk8s ctr image import ~/my-postgres.tar