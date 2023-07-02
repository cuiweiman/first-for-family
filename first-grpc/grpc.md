# HelloGrpc

1. 编写 proto 文档 （如 Hello.proto 的 message），设计 gRPC 服务信息 （如 Hello.proto 的 service）
2. pom.xml 中引入 proto 编译的 扩展和插件：os-maven-plugin ；protobuf-maven-plugin，并配置 proto 文件的编译生成路径
3. HelloServiceGrpc.HelloServiceImplBase 接口即 需要实现 业务代码 的远程服务 API接口

# Stub代理方式

- BlockingStub: 客户端 同步阻塞式 发送RPC请求
- Stub: 客户端 异步监听式 发送RPC请求
- FutureStub: 同时支持同步 和 异步; 注意：**只能应用于 Unary RPC**

# gRPC的四种通信方式

## 简单RPC:

> 一元RPC Unary RPC

- 是开发中，主要使用的通信方式
- eg: Hello.proto + GrpcServer1 + GrpcClient1
- 客户端建立与服务端的http2连接后，客户端将发送一个request，并进入阻塞，直到服务端响应response
- 语法:
  ```protobuf
  service HelloService{
    rpc hello(HelloRequest) returns (HelloResponse){}
    rpc userService(UserRequest) returns (UserResponse){}
  }
  ``` 

## 服务端流式RPC

- Server Streaming RPC 服务端向客户端发送流。一次客户端请求，客户端进入阻塞，保持长连接，『多次』回传响应信息，直到结束。
- 场景: 触发实时数据推送——客户端发送数据请求，服务端根据时间，不停推送实时数据。
- 语法: 包括 客户端阻塞、客户端异步监听 两种方式
  ```protobuf
  /*服务端流式RPC*/
  service HelloService{
    rpc streamService(HelloRequest) returns (stream HelloResponse){}
  }
  ``` 
  ```java
  // 客户端 阻塞式 发送请求后，会进入业务阻塞，直到服务端 onComplete() 触发
  HelloServiceGrpc.HelloServiceBlockingStub helloService = HelloServiceGrpc.newBlockingStub(managedChannel);
  HelloProto.HelloRequest helloRequest = HelloProto.HelloRequest.newBuilder().setName("鲁智深").build();
  // 服务端流式响应，响应结果是 迭代器 类型
  Iterator<HelloProto.HelloResponse> iterator = helloService.streamService(helloRequest);
  while (iterator.hasNext()) {
      HelloProto.HelloResponse helloResponse = iterator.next();
      System.out.println("服务端流式响应::客户端接收结果\t" + helloResponse.getResult());
  }
  ```

  ```java
    // 客户端异步监听方式，不会进入阻塞
    CountDownLatch countDownLatch = new CountDownLatch(1);
    HelloServiceGrpc.HelloServiceStub serviceStub = HelloServiceGrpc.newStub(managedChannel);
    serviceStub.streamService(helloRequest, new StreamObserver<>() {
        @Override
        public void onNext(HelloProto.HelloResponse helloResponse) {
            // 服务端响应一个，客户端接收并处理一个
            System.out.println("服务端流式响应::异步监听\t" + helloResponse.getResult());
        }

        @Override
        public void onError(Throwable throwable) {
            System.out.println("服务端流式响应::异步监听错误\t" + throwable.getMessage());
        }

        @Override
        public void onCompleted() {
            // 服务端响应完成，长连接将关闭
            System.out.println("服务端流式响应::客户端接收响应结束");
            countDownLatch.countDown();
        }
    });

    System.out.println("异步执行确认");

    boolean await = countDownLatch.await(6, TimeUnit.SECONDS);
    if (await) {
        System.out.println("正常线程等待，执行结束");
    } else {
        System.out.println("线程等待超时，执行结束");
    }
  ```

## 客户端流式RPC

- Client Streaming RPC 客户端向服务端发送流请求，服务端一次响应
- 场景: 物联网IoT中的传感器、设备等，与服务端建立了连接后，需要不断上报数据到服务端。
- 语法: 重点在 服务端的处理
  ```protobuf
  /*服务端流式RPC*/
  service HelloService{
    rpc streamClient(stream HelloRequest) returns(HelloResponse){}
  }
  ``` 

  ```java
  // 服务端处理客户端消息
  @Override
  public StreamObserver<HelloProto.HelloRequest> streamClient(StreamObserver<HelloProto.HelloResponse> responseObserver) {
      // 异步 监听 并 接收 客户端的数据请求
      return new StreamObserver<HelloProto.HelloRequest>() {
          @Override
          public void onNext(HelloProto.HelloRequest helloRequest) {
              System.out.println("[监听客户端流式请求]\thelloRequest.name = " + helloRequest.getName());
          }

          @Override
          public void onError(Throwable throwable) {
              System.out.println("[监听客户端流式请求] 错误: " + throwable.getMessage());
          }

          @Override
          public void onCompleted() {
              System.out.println("[监听客户端流式请求] 完成");
          }
      };
  }
  ```

  ```java
  // 客户端流式发送逻辑
  ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 9000).usePlaintext().build();
  try {
    HelloServiceGrpc.HelloServiceStub helloServiceStub = HelloServiceGrpc.newStub(managedChannel);
    HelloProto.HelloRequest.Builder builder = HelloProto.HelloRequest.newBuilder();
    // 异步监听 服务端的响应信息
    StreamObserver<HelloProto.HelloRequest> streamClient = helloServiceStub.streamClient(new StreamObserver<>() {
        @Override
        public void onNext(HelloProto.HelloResponse helloResponse) {
            System.out.println("[监听服务端响应]\thelloResponse.result = " + helloResponse.getResult());
        }

        @Override
        public void onError(Throwable throwable) {
            System.out.println("[监听服务端响应] 错误 " + throwable.getMessage());
        }

        @Override
        public void onCompleted() {
            System.out.println("[监听服务端响应]\t 结束 ");
        }
    });

    // 发送客户端 流式请求
    for (int i = 0; i < 10; i++) {
        HelloProto.HelloRequest helloRequest = builder.setName("name-" + i).build();
        streamClient.onNext(helloRequest);
    }
    streamClient.onCompleted();
    managedChannel.awaitTermination(12, TimeUnit.SECONDS);
  } catch (Exception e) {
      throw new RuntimeException(e);
  } finally {
      managedChannel.shutdown();
  }
  ```

## 双向流RPC

- Bi-directional Streaming RPC 客户端可以请求多次，服务端可以响应多次，双向都可以多次发送流。
- 场景: 例如聊天窗口通道
- 语法：客户端和服务端类似于 服务端流式响应、客户端流式请求，此处只列出 proto 的配置
  ```protobuf
  
  ```

# grpc-spring-boot-starter

经测试，grpc-spring-boot-starter:2.14.0.RELEASE 目前最多支持到 SpringBoot 2.7.10 











