# Kubernetes Workshop



## Preparation


#### Install Docker:

[How to install Docker on Windows](https://www.docker.com/products/docker-desktop)
#### Install local Kubernetes Cluster



**Option 1 (recommended):** with Docker-for-Windows

![Kubernetes Setup](https://raw.githubusercontent.com/bdruesedow/kubernetes-workshop/master/lecture/images/kubernetes.PNG)

**Option 2:** use Minikube: [How to install minikube](https://kubernetes.io/docs/tasks/tools/install-minikube/)

##### Ressource settings:

![Docker Settings Advanced](https://raw.githubusercontent.com/bdruesedow/kubernetes-workshop/master/lecture/images/ressources.PNG)

![Docker Settings Shared Drives](https://raw.githubusercontent.com/bdruesedow/kubernetes-workshop/master/lecture/images/drives.PNG)

![Docker Settings Daemon](https://raw.githubusercontent.com/bdruesedow/kubernetes-workshop/master/lecture/images/daemon.PNG)

When asked, grant access to your filesystem:

![Docker grant Access](https://raw.githubusercontent.com/bdruesedow/kubernetes-workshop/master/lecture/images/access.PNG)


#### Install `kubectl`: [How to install kubectl on Windows](https://kubernetes.io/de/docs/tasks/tools/install-kubectl/#installation-auf-windows-mit-chocolatey-oder-scoop)

#### Install Kubernetes Helm: [How to Setup Kubernetes Helm](https://helm.sh/docs/intro/install/)

## Exercise

### Deployment with Docker

1. Build greeting-service and helloworld-service with gradlew.bat
2. Write Dockerfile for helloworld-service
3. Build Dockerimages for greeting-service and helloworld-service

    ```
    docker build -t <service-name>:<tag> -f <path-to-dockerfile> <path-to-folder>
    ```
For example:
    ```
    docker build -t greeting-service:latest -f docker/dockerfile-app .
    ```

4. Create Docker network to ensure communication between the services:

    ```
    docker network create <network-name>
    docker network ls
    ```

5. Deploy MySQL database:

    ```
    docker run --name mysqldb --net <network-name> -e MYSQL_ROOT_PASSWORD=s1ch3r -d mysql:5.7.14
    ```

6. Deploy greeting-service:

    ```
    docker run --name greeting-service --net <network-name> -d -p 8080:8080 greeting-service:latest
    docker logs -f greeting-service
    ```

7. Deploy helloworld-service:

    ```
    docker run --name helloworld-service --net <network-name> -d -p 8081:8080 helloworld-service:latest
    docker logs -f helloworld-service
    ```

8. Test the services:

    ```
    curl localhost:8080/greeting?name=World
    curl localhost:8080/getGreetings

    curl localhost:8081/hello
    ```

8. Stop and delete container:

    ```
    docker stop greeting-service mysqldb
    docker rm greeting-service mysqldb
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

## Kubernetes Tutorials:

Interactive Kubernetes Tutorial: [Kubernetes.io Basics](https://kubernetes.io/de/docs/tutorials/kubernetes-basics/)

**Solve parts 1, 2 & 3**  (30 minutes)

![Kubernetes.io Tutorials 1, 2, 3](https://raw.githubusercontent.com/bdruesedow/kubernetes-workshop/master/lecture/images/k8s_tutorials.PNG)


## Deployment to Kubernetes with Helm:

1. Release Helm Chart:

    ```
    cd helm/demo
    helm install <release-name> <path-to-chart>
    ```

2. Look into the cluster:

    Run `k9s.exe`

    or use `kubectl` commands:

    ```
    kuebctl get deployments
    kubectl get pods
    kubectl get services

    kubectl logs -f <pod>
    kubectl describe <ressource>
    ```

3. Create Helm templates for `helloworld-service`
    * Create new directory under `templates/`
    * Create a deployment.yaml, and service.yaml for the service.
    * Update `values.yaml` to work with your new templates.
    * Validate the helm chart with `helm lint`
    * Rollout a release (`helm upgrade <release-name> <path-to-chart>`)
    * Forward the ports of your services and check the outputs of:
        * `localhost:<forwarded-port-of-greeting-service>/greeting?name=World`
        * `localhost:<forwarded-port-of-greeting-service>/getGreetings`
        * `localhost:<forwarded-port-of-helloworld-service>/hello`


## CI-Pipeline with Jenkins:

* Deploy Jenkins-CI Server and a local Docker-Registry:

    ```
    cd environment
    ```

    ```
    docker-compose up -d
    ```

* Check if it works:

    Jenkins: `localhost:8090`

    Registry: `localhost:5000/v2/\_catalog`

* Setup Jenkins:
    * Open `localhost:8090` in a browser
    * Look for the admin passwort with `docker logs -f jenkins` or with
        ```
        docker exec -ti jenkins bash
        cat /var/jenkins_home/secrets/initialAdminPassword
        ```
    * After account setup, choose "*Install suggested plugins*"
    ![Jenkins Welcome Screen](https://raw.githubusercontent.com/bdruesedow/kubernetes-workshop/master/lecture/images/jenkins.PNG)
    * Create Username/Password Credentials based on your GitHub Login
    ![Jenkins Credentials](https://raw.githubusercontent.com/bdruesedow/kubernetes-workshop/master/lecture/images/jenkins_credentials.PNG)

* Create a new Multibranch Pipeline
    * Add Branch Sources -> GitHub
    * Enter Repository URL (https://github.com/bdruesedow/kubernetes-workshop.git)
    * Jenkinsfile path is `greeting-service/Jenkinsfile`
* Scan Multibranch Pipeline

## Docker Cleanup

* Remove unused containers: `docker container prune`
* Remove unused networks: `docker network prune`
* Remove unused images: `docker image prune`

## Troubleshoot:

![Jenkins Credentials](https://raw.githubusercontent.com/bdruesedow/kubernetes-workshop/master/lecture/images/registry_error.PNG)

Docker daemon has no permission to pull from an insecure registry, that is not configured in `daemon.json`. To fix this, add the registry as insecure regsitry.

With docker desktop:

![Docker Settings Daemon](https://raw.githubusercontent.com/bdruesedow/kubernetes-workshop/master/lecture/images/daemon.PNG)

or directly the `daemon.json` file:

```               
{                                           
 "registry-mirrors": [],                   
 "insecure-registries": ["<your-ip>:5002"],
 "debug": true,                            
 "experimental": false                     
}                                           
```
