# 创建资源
kubectl apply -f server-service.yml

# 查看
kubectl get services -o wide
kubectl -A get deployment
kubectl get pods

# 删除 pod 和 service
kubectl delete pod,service server-service

# 添加实例个数
kubectl scale deployment/server-service --replicas=3


# 测试接口
curl http://127.0.0.1:18895/health
curl http://127.0.0.1:18895/ping
curl http://127.0.0.1:18895/discovery
curl http://127.0.0.1:18895/service/{serviceId}