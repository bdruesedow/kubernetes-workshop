# Kubernetes Workshop



# Environment Setup

```
cd examples/environments
```

```
docker-compose up -d
```


```
kubectl create secret generic regcred \
    --from-file=.dockerconfigjson=<path/to/.docker/config.json> \
    --type=kubernetes.io/dockerconfigjson
```

# Deployment with Helm

```
cd examples/deployment-helm
```

```
helm install <release-name> /path/to/demo
```
