# Kubernetes Workshop



## Environment Setup

```
cd environment
```

```
docker-compose up -d
```

Deploys Jenkins and a local Docker-Registry:


```
kubectl create secret generic regcred \
    --from-file=.dockerconfigjson=<path/to/.docker/config.json> \
    --type=kubernetes.io/dockerconfigjson
```

## Deployment with Helm

```
cd helm
helm install <release-name> /path/to/demo
```


## Exercise

### Deployment with Docker

* Write Dockerfile for helloworld-service
* Build greeting-service and helloworld-service

```
docker build -t <service-name>:<tag> -f <path-to-dockerfile> <path-to-folder>
```

* Create Docker network:

```
docker network create <network-name>
docker network ls
```

* Deploy MySQL database:

```
docker run --name mysqldb --net <network-name> -e MYSQL_ROOT_PASSWORD=s1ch3r -d mysql:5.7.14
```

* Deploy greeting-service:

```
docker run --name greeting-service --net <network-name> -d -p 8080:8080 greeting-service:latest
docker logs -f greeting-service
```

* Test the application:

```
curl localhost:8080/greeting?name=World
curl localhost:8080/getGreetings
```

* Stop and delete container:

```
docker stop greeting-backend mysqldb
docker rm greeting-backend mysqldb
```

## Deployment with Compose:

```
cd compose
docker-compose up -d
```

* Test application:

```
curl localhost:8080/greeting?name=World
curl localhost:8080/getGreetings
```

* Stop container:

```
docker-compose down
```

## Deployment to Kubernetes with Helm

* Release Helm Chart

```
cd helm/demo
helm install <release-name> <path-to-chart>
```

* Look into the cluster:

```
kuebctl get deployments
kubectl get pods
kubectl get services

kubectl logs -f <pod>
kubectl describe <ressource>
```

## Cleanup

* Remove unused containers: `docker container prune`
* Remove unused networks: `docker network prune`
* Remove unused images: `docker image prune`
