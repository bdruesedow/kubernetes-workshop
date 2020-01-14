# Kubernetes Workshop



## Environment Setup


* Install Docker: [How to install Docker on Windows](https://www.docker.com/products/docker-desktop)
* Install local Kubernetes Cluster with Docker-for-Windows:

![Kubernetes Setup](https://raw.githubusercontent.com/bdruesedow/kubernetes-workshop/master/lecture/images/kubernetes.PNG)

or use Minikube: [How to install minikube](https://kubernetes.io/docs/tasks/tools/install-minikube/)

* Install `kubectl`: [How to install kubectl on Windows](https://kubernetes.io/de/docs/tasks/tools/install-kubectl/#installation-auf-windows-mit-chocolatey-oder-scoop)

* Install Kubernetes Helm: [How to Setup Kubernetes Helm](https://helm.sh/docs/intro/install/)


* Deploy Jenkins-CI Server and a local Docker-Registry:

```
cd environment
```

```
docker-compose up -d
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

## Kubernetes Tutorials

Interactive Kubernetes Tutorial: [Kubernetes.io Basics](https://kubernetes.io/de/docs/tutorials/kubernetes-basics/)



## Deployment to Kubernetes with Helm

1. Setup registry secret as Kubernetes secret:

```
kubectl create secret generic regcred \
    --from-file=.dockerconfigjson=<path/to/.docker/config.json> \
    --type=kubernetes.io/dockerconfigjson
```

2. Release Helm Chart

```
cd helm/demo
helm install <release-name> <path-to-chart>
```

3. Look into the cluster:

```
kuebctl get deployments
kubectl get pods
kubectl get services

kubectl logs -f <pod>
kubectl describe <ressource>
```

4. Create Helm templates for `helloworld-service`
    * Create new directory under `templates/`
    * Create a deployment.yaml, and service.yaml for the service.
    * Update `values.yaml` to work with your new templates.
    * Validate the helm chart with `helm lint`
    * Rollout a release (`helm upgrade <release-name> <path-to-chart>`)
    * Forward the ports of your services and check the outputs of:
        * `localhost:<forwarded-port-of-greeting-service>/greeting?name=World`
        * `localhost:<forwarded-port-of-helloworld-service>/hello`



## Cleanup

* Remove unused containers: `docker container prune`
* Remove unused networks: `docker network prune`
* Remove unused images: `docker image prune`
