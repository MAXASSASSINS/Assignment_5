cd ~/assignments

sudo rm -rf assignment_5

sudo cp -r "/mnt/c/Users/mohd.c.shadab/OneDrive - Accenture/Documents/Desktop/Docker and K8s/assignment_5" ~/assignments

cd assignment_5

docker build -t my-producer:v3 -f Dockerfile.producer .
docker build -t my-consumer:v3 -f Dockerfile.consumer .
docker build -t my-postgres:v3 -f Dockerfile.postgres .

docker save my-consumer:v3 > ~/my-consumer.tar
microk8s ctr image import ~/my-consumer.tar

docker save my-producer:v3 > ~/my-producer.tar
microk8s ctr image import ~/my-producer.tar

docker save my-postgres:v3 > ~/my-postgres.tar
microk8s ctr image import ~/my-postgres.tar