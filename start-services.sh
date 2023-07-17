#!/bin/sh

kubectl apply -f k8s/services/customer
kubectl apply -f k8s/services/fraud
kubectl apply -f k8s/services/notification
