package com.first.family.grpc.api.message;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.56.0)",
    comments = "Source: Hello.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class HelloServiceGrpc {

  private HelloServiceGrpc() {}

  public static final String SERVICE_NAME = "HelloService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.first.family.grpc.api.message.HelloProto.HelloRequest,
      com.first.family.grpc.api.message.HelloProto.HelloResponse> getHelloMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "hello",
      requestType = com.first.family.grpc.api.message.HelloProto.HelloRequest.class,
      responseType = com.first.family.grpc.api.message.HelloProto.HelloResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.first.family.grpc.api.message.HelloProto.HelloRequest,
      com.first.family.grpc.api.message.HelloProto.HelloResponse> getHelloMethod() {
    io.grpc.MethodDescriptor<com.first.family.grpc.api.message.HelloProto.HelloRequest, com.first.family.grpc.api.message.HelloProto.HelloResponse> getHelloMethod;
    if ((getHelloMethod = HelloServiceGrpc.getHelloMethod) == null) {
      synchronized (HelloServiceGrpc.class) {
        if ((getHelloMethod = HelloServiceGrpc.getHelloMethod) == null) {
          HelloServiceGrpc.getHelloMethod = getHelloMethod =
              io.grpc.MethodDescriptor.<com.first.family.grpc.api.message.HelloProto.HelloRequest, com.first.family.grpc.api.message.HelloProto.HelloResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "hello"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.first.family.grpc.api.message.HelloProto.HelloRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.first.family.grpc.api.message.HelloProto.HelloResponse.getDefaultInstance()))
              .setSchemaDescriptor(new HelloServiceMethodDescriptorSupplier("hello"))
              .build();
        }
      }
    }
    return getHelloMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.first.family.grpc.api.message.HelloProto.UserRequest,
      com.first.family.grpc.api.message.HelloProto.UserResponse> getUserServiceMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "userService",
      requestType = com.first.family.grpc.api.message.HelloProto.UserRequest.class,
      responseType = com.first.family.grpc.api.message.HelloProto.UserResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.first.family.grpc.api.message.HelloProto.UserRequest,
      com.first.family.grpc.api.message.HelloProto.UserResponse> getUserServiceMethod() {
    io.grpc.MethodDescriptor<com.first.family.grpc.api.message.HelloProto.UserRequest, com.first.family.grpc.api.message.HelloProto.UserResponse> getUserServiceMethod;
    if ((getUserServiceMethod = HelloServiceGrpc.getUserServiceMethod) == null) {
      synchronized (HelloServiceGrpc.class) {
        if ((getUserServiceMethod = HelloServiceGrpc.getUserServiceMethod) == null) {
          HelloServiceGrpc.getUserServiceMethod = getUserServiceMethod =
              io.grpc.MethodDescriptor.<com.first.family.grpc.api.message.HelloProto.UserRequest, com.first.family.grpc.api.message.HelloProto.UserResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "userService"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.first.family.grpc.api.message.HelloProto.UserRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.first.family.grpc.api.message.HelloProto.UserResponse.getDefaultInstance()))
              .setSchemaDescriptor(new HelloServiceMethodDescriptorSupplier("userService"))
              .build();
        }
      }
    }
    return getUserServiceMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.first.family.grpc.api.message.HelloProto.HelloRequest,
      com.first.family.grpc.api.message.HelloProto.HelloResponse> getStreamServiceMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "streamService",
      requestType = com.first.family.grpc.api.message.HelloProto.HelloRequest.class,
      responseType = com.first.family.grpc.api.message.HelloProto.HelloResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<com.first.family.grpc.api.message.HelloProto.HelloRequest,
      com.first.family.grpc.api.message.HelloProto.HelloResponse> getStreamServiceMethod() {
    io.grpc.MethodDescriptor<com.first.family.grpc.api.message.HelloProto.HelloRequest, com.first.family.grpc.api.message.HelloProto.HelloResponse> getStreamServiceMethod;
    if ((getStreamServiceMethod = HelloServiceGrpc.getStreamServiceMethod) == null) {
      synchronized (HelloServiceGrpc.class) {
        if ((getStreamServiceMethod = HelloServiceGrpc.getStreamServiceMethod) == null) {
          HelloServiceGrpc.getStreamServiceMethod = getStreamServiceMethod =
              io.grpc.MethodDescriptor.<com.first.family.grpc.api.message.HelloProto.HelloRequest, com.first.family.grpc.api.message.HelloProto.HelloResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "streamService"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.first.family.grpc.api.message.HelloProto.HelloRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.first.family.grpc.api.message.HelloProto.HelloResponse.getDefaultInstance()))
              .setSchemaDescriptor(new HelloServiceMethodDescriptorSupplier("streamService"))
              .build();
        }
      }
    }
    return getStreamServiceMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.first.family.grpc.api.message.HelloProto.HelloRequest,
      com.first.family.grpc.api.message.HelloProto.HelloResponse> getStreamClientMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "streamClient",
      requestType = com.first.family.grpc.api.message.HelloProto.HelloRequest.class,
      responseType = com.first.family.grpc.api.message.HelloProto.HelloResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<com.first.family.grpc.api.message.HelloProto.HelloRequest,
      com.first.family.grpc.api.message.HelloProto.HelloResponse> getStreamClientMethod() {
    io.grpc.MethodDescriptor<com.first.family.grpc.api.message.HelloProto.HelloRequest, com.first.family.grpc.api.message.HelloProto.HelloResponse> getStreamClientMethod;
    if ((getStreamClientMethod = HelloServiceGrpc.getStreamClientMethod) == null) {
      synchronized (HelloServiceGrpc.class) {
        if ((getStreamClientMethod = HelloServiceGrpc.getStreamClientMethod) == null) {
          HelloServiceGrpc.getStreamClientMethod = getStreamClientMethod =
              io.grpc.MethodDescriptor.<com.first.family.grpc.api.message.HelloProto.HelloRequest, com.first.family.grpc.api.message.HelloProto.HelloResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "streamClient"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.first.family.grpc.api.message.HelloProto.HelloRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.first.family.grpc.api.message.HelloProto.HelloResponse.getDefaultInstance()))
              .setSchemaDescriptor(new HelloServiceMethodDescriptorSupplier("streamClient"))
              .build();
        }
      }
    }
    return getStreamClientMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.first.family.grpc.api.message.HelloProto.HelloRequest,
      com.first.family.grpc.api.message.HelloProto.HelloResponse> getStreamAllMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "streamAll",
      requestType = com.first.family.grpc.api.message.HelloProto.HelloRequest.class,
      responseType = com.first.family.grpc.api.message.HelloProto.HelloResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<com.first.family.grpc.api.message.HelloProto.HelloRequest,
      com.first.family.grpc.api.message.HelloProto.HelloResponse> getStreamAllMethod() {
    io.grpc.MethodDescriptor<com.first.family.grpc.api.message.HelloProto.HelloRequest, com.first.family.grpc.api.message.HelloProto.HelloResponse> getStreamAllMethod;
    if ((getStreamAllMethod = HelloServiceGrpc.getStreamAllMethod) == null) {
      synchronized (HelloServiceGrpc.class) {
        if ((getStreamAllMethod = HelloServiceGrpc.getStreamAllMethod) == null) {
          HelloServiceGrpc.getStreamAllMethod = getStreamAllMethod =
              io.grpc.MethodDescriptor.<com.first.family.grpc.api.message.HelloProto.HelloRequest, com.first.family.grpc.api.message.HelloProto.HelloResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "streamAll"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.first.family.grpc.api.message.HelloProto.HelloRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.first.family.grpc.api.message.HelloProto.HelloResponse.getDefaultInstance()))
              .setSchemaDescriptor(new HelloServiceMethodDescriptorSupplier("streamAll"))
              .build();
        }
      }
    }
    return getStreamAllMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static HelloServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HelloServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<HelloServiceStub>() {
        @java.lang.Override
        public HelloServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new HelloServiceStub(channel, callOptions);
        }
      };
    return HelloServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static HelloServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HelloServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<HelloServiceBlockingStub>() {
        @java.lang.Override
        public HelloServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new HelloServiceBlockingStub(channel, callOptions);
        }
      };
    return HelloServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static HelloServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HelloServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<HelloServiceFutureStub>() {
        @java.lang.Override
        public HelloServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new HelloServiceFutureStub(channel, callOptions);
        }
      };
    return HelloServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     * <pre>
     *  普通 Unary RPC
     * </pre>
     */
    default void hello(com.first.family.grpc.api.message.HelloProto.HelloRequest request,
        io.grpc.stub.StreamObserver<com.first.family.grpc.api.message.HelloProto.HelloResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getHelloMethod(), responseObserver);
    }

    /**
     */
    default void userService(com.first.family.grpc.api.message.HelloProto.UserRequest request,
        io.grpc.stub.StreamObserver<com.first.family.grpc.api.message.HelloProto.UserResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUserServiceMethod(), responseObserver);
    }

    /**
     * <pre>
     *  服务端流式RPC
     * </pre>
     */
    default void streamService(com.first.family.grpc.api.message.HelloProto.HelloRequest request,
        io.grpc.stub.StreamObserver<com.first.family.grpc.api.message.HelloProto.HelloResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getStreamServiceMethod(), responseObserver);
    }

    /**
     * <pre>
     *  客户端流式RPC
     * </pre>
     */
    default io.grpc.stub.StreamObserver<com.first.family.grpc.api.message.HelloProto.HelloRequest> streamClient(
        io.grpc.stub.StreamObserver<com.first.family.grpc.api.message.HelloProto.HelloResponse> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getStreamClientMethod(), responseObserver);
    }

    /**
     * <pre>
     *  双向流式RPC
     * </pre>
     */
    default io.grpc.stub.StreamObserver<com.first.family.grpc.api.message.HelloProto.HelloRequest> streamAll(
        io.grpc.stub.StreamObserver<com.first.family.grpc.api.message.HelloProto.HelloResponse> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getStreamAllMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service HelloService.
   */
  public static abstract class HelloServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return HelloServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service HelloService.
   */
  public static final class HelloServiceStub
      extends io.grpc.stub.AbstractAsyncStub<HelloServiceStub> {
    private HelloServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HelloServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HelloServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *  普通 Unary RPC
     * </pre>
     */
    public void hello(com.first.family.grpc.api.message.HelloProto.HelloRequest request,
        io.grpc.stub.StreamObserver<com.first.family.grpc.api.message.HelloProto.HelloResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getHelloMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void userService(com.first.family.grpc.api.message.HelloProto.UserRequest request,
        io.grpc.stub.StreamObserver<com.first.family.grpc.api.message.HelloProto.UserResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUserServiceMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *  服务端流式RPC
     * </pre>
     */
    public void streamService(com.first.family.grpc.api.message.HelloProto.HelloRequest request,
        io.grpc.stub.StreamObserver<com.first.family.grpc.api.message.HelloProto.HelloResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getStreamServiceMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *  客户端流式RPC
     * </pre>
     */
    public io.grpc.stub.StreamObserver<com.first.family.grpc.api.message.HelloProto.HelloRequest> streamClient(
        io.grpc.stub.StreamObserver<com.first.family.grpc.api.message.HelloProto.HelloResponse> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncClientStreamingCall(
          getChannel().newCall(getStreamClientMethod(), getCallOptions()), responseObserver);
    }

    /**
     * <pre>
     *  双向流式RPC
     * </pre>
     */
    public io.grpc.stub.StreamObserver<com.first.family.grpc.api.message.HelloProto.HelloRequest> streamAll(
        io.grpc.stub.StreamObserver<com.first.family.grpc.api.message.HelloProto.HelloResponse> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getStreamAllMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service HelloService.
   */
  public static final class HelloServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<HelloServiceBlockingStub> {
    private HelloServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HelloServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HelloServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *  普通 Unary RPC
     * </pre>
     */
    public com.first.family.grpc.api.message.HelloProto.HelloResponse hello(com.first.family.grpc.api.message.HelloProto.HelloRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getHelloMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.first.family.grpc.api.message.HelloProto.UserResponse userService(com.first.family.grpc.api.message.HelloProto.UserRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUserServiceMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     *  服务端流式RPC
     * </pre>
     */
    public java.util.Iterator<com.first.family.grpc.api.message.HelloProto.HelloResponse> streamService(
        com.first.family.grpc.api.message.HelloProto.HelloRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getStreamServiceMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service HelloService.
   */
  public static final class HelloServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<HelloServiceFutureStub> {
    private HelloServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HelloServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HelloServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *  普通 Unary RPC
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.first.family.grpc.api.message.HelloProto.HelloResponse> hello(
        com.first.family.grpc.api.message.HelloProto.HelloRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getHelloMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.first.family.grpc.api.message.HelloProto.UserResponse> userService(
        com.first.family.grpc.api.message.HelloProto.UserRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUserServiceMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_HELLO = 0;
  private static final int METHODID_USER_SERVICE = 1;
  private static final int METHODID_STREAM_SERVICE = 2;
  private static final int METHODID_STREAM_CLIENT = 3;
  private static final int METHODID_STREAM_ALL = 4;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_HELLO:
          serviceImpl.hello((com.first.family.grpc.api.message.HelloProto.HelloRequest) request,
              (io.grpc.stub.StreamObserver<com.first.family.grpc.api.message.HelloProto.HelloResponse>) responseObserver);
          break;
        case METHODID_USER_SERVICE:
          serviceImpl.userService((com.first.family.grpc.api.message.HelloProto.UserRequest) request,
              (io.grpc.stub.StreamObserver<com.first.family.grpc.api.message.HelloProto.UserResponse>) responseObserver);
          break;
        case METHODID_STREAM_SERVICE:
          serviceImpl.streamService((com.first.family.grpc.api.message.HelloProto.HelloRequest) request,
              (io.grpc.stub.StreamObserver<com.first.family.grpc.api.message.HelloProto.HelloResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_STREAM_CLIENT:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.streamClient(
              (io.grpc.stub.StreamObserver<com.first.family.grpc.api.message.HelloProto.HelloResponse>) responseObserver);
        case METHODID_STREAM_ALL:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.streamAll(
              (io.grpc.stub.StreamObserver<com.first.family.grpc.api.message.HelloProto.HelloResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getHelloMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.first.family.grpc.api.message.HelloProto.HelloRequest,
              com.first.family.grpc.api.message.HelloProto.HelloResponse>(
                service, METHODID_HELLO)))
        .addMethod(
          getUserServiceMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.first.family.grpc.api.message.HelloProto.UserRequest,
              com.first.family.grpc.api.message.HelloProto.UserResponse>(
                service, METHODID_USER_SERVICE)))
        .addMethod(
          getStreamServiceMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              com.first.family.grpc.api.message.HelloProto.HelloRequest,
              com.first.family.grpc.api.message.HelloProto.HelloResponse>(
                service, METHODID_STREAM_SERVICE)))
        .addMethod(
          getStreamClientMethod(),
          io.grpc.stub.ServerCalls.asyncClientStreamingCall(
            new MethodHandlers<
              com.first.family.grpc.api.message.HelloProto.HelloRequest,
              com.first.family.grpc.api.message.HelloProto.HelloResponse>(
                service, METHODID_STREAM_CLIENT)))
        .addMethod(
          getStreamAllMethod(),
          io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
            new MethodHandlers<
              com.first.family.grpc.api.message.HelloProto.HelloRequest,
              com.first.family.grpc.api.message.HelloProto.HelloResponse>(
                service, METHODID_STREAM_ALL)))
        .build();
  }

  private static abstract class HelloServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    HelloServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.first.family.grpc.api.message.HelloProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("HelloService");
    }
  }

  private static final class HelloServiceFileDescriptorSupplier
      extends HelloServiceBaseDescriptorSupplier {
    HelloServiceFileDescriptorSupplier() {}
  }

  private static final class HelloServiceMethodDescriptorSupplier
      extends HelloServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    HelloServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (HelloServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new HelloServiceFileDescriptorSupplier())
              .addMethod(getHelloMethod())
              .addMethod(getUserServiceMethod())
              .addMethod(getStreamServiceMethod())
              .addMethod(getStreamClientMethod())
              .addMethod(getStreamAllMethod())
              .build();
        }
      }
    }
    return result;
  }
}
