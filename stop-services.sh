#!/bin/sh

export KUBECONFIG=~/Documents/odservices-kubeconfig.yaml

kubectl scale --replicas=0 deployments/customer
kubectl scale --replicas=0 deployments/fraud
kubectl scale --replicas=0 deployments/notification
# TODO: Add zipkin and rabbitmq to this list as well