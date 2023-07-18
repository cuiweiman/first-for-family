# 创建资源
kubectl apply -f client-service.yml

# 查看
kubectl get services -o wide
kubectl -A get deployment
kubectl get pods

# 删除 pod 和 service,deployment
kubectl delete deployment client-service
kubectl delete service client-service
kubectl delete pod server-service-xxxxxx-xxxxxxxx

# 添加实例个数
kubectl scale deployment/client-service --replicas=3

# 测试接口
curl http://127.0.0.1:18896/health
curl http://127.0.0.1:18896/callServer
