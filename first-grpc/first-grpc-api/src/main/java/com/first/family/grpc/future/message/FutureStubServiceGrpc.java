package com.first.family.grpc.future.message;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.56.0)",
    comments = "Source: FutureStubDemo.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class FutureStubServiceGrpc {

  private FutureStubServiceGrpc() {}

  public static final String SERVICE_NAME = "FutureStubService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.first.family.grpc.future.message.FutureStubDemoProto.FutureStubReq,
      com.first.family.grpc.future.message.FutureStubDemoProto.FutureStubResp> getFutureStubTestMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "futureStubTest",
      requestType = com.first.family.grpc.future.message.FutureStubDemoProto.FutureStubReq.class,
      responseType = com.first.family.grpc.future.message.FutureStubDemoProto.FutureStubResp.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.first.family.grpc.future.message.FutureStubDemoProto.FutureStubReq,
      com.first.family.grpc.future.message.FutureStubDemoProto.FutureStubResp> getFutureStubTestMethod() {
    io.grpc.MethodDescriptor<com.first.family.grpc.future.message.FutureStubDemoProto.FutureStubReq, com.first.family.grpc.future.message.FutureStubDemoProto.FutureStubResp> getFutureStubTestMethod;
    if ((getFutureStubTestMethod = FutureStubServiceGrpc.getFutureStubTestMethod) == null) {
      synchronized (FutureStubServiceGrpc.class) {
        if ((getFutureStubTestMethod = FutureStubServiceGrpc.getFutureStubTestMethod) == null) {
          FutureStubServiceGrpc.getFutureStubTestMethod = getFutureStubTestMethod =
              io.grpc.MethodDescriptor.<com.first.family.grpc.future.message.FutureStubDemoProto.FutureStubReq, com.first.family.grpc.future.message.FutureStubDemoProto.FutureStubResp>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "futureStubTest"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.first.family.grpc.future.message.FutureStubDemoProto.FutureStubReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.first.family.grpc.future.message.FutureStubDemoProto.FutureStubResp.getDefaultInstance()))
              .setSchemaDescriptor(new FutureStubServiceMethodDescriptorSupplier("futureStubTest"))
              .build();
        }
      }
    }
    return getFutureStubTestMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static FutureStubServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FutureStubServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FutureStubServiceStub>() {
        @java.lang.Override
        public FutureStubServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FutureStubServiceStub(channel, callOptions);
        }
      };
    return FutureStubServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static FutureStubServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FutureStubServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FutureStubServiceBlockingStub>() {
        @java.lang.Override
        public FutureStubServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FutureStubServiceBlockingStub(channel, callOptions);
        }
      };
    return FutureStubServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static FutureStubServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FutureStubServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FutureStubServiceFutureStub>() {
        @java.lang.Override
        public FutureStubServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FutureStubServiceFutureStub(channel, callOptions);
        }
      };
    return FutureStubServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void futureStubTest(com.first.family.grpc.future.message.FutureStubDemoProto.FutureStubReq request,
        io.grpc.stub.StreamObserver<com.first.family.grpc.future.message.FutureStubDemoProto.FutureStubResp> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFutureStubTestMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service FutureStubService.
   */
  public static abstract class FutureStubServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return FutureStubServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service FutureStubService.
   */
  public static final class FutureStubServiceStub
      extends io.grpc.stub.AbstractAsyncStub<FutureStubServiceStub> {
    private FutureStubServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FutureStubServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FutureStubServiceStub(channel, callOptions);
    }

    /**
     */
    public void futureStubTest(com.first.family.grpc.future.message.FutureStubDemoProto.FutureStubReq request,
        io.grpc.stub.StreamObserver<com.first.family.grpc.future.message.FutureStubDemoProto.FutureStubResp> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getFutureStubTestMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service FutureStubService.
   */
  public static final class FutureStubServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<FutureStubServiceBlockingStub> {
    private FutureStubServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FutureStubServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FutureStubServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.first.family.grpc.future.message.FutureStubDemoProto.FutureStubResp futureStubTest(com.first.family.grpc.future.message.FutureStubDemoProto.FutureStubReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFutureStubTestMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service FutureStubService.
   */
  public static final class FutureStubServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<FutureStubServiceFutureStub> {
    private FutureStubServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FutureStubServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FutureStubServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.first.family.grpc.future.message.FutureStubDemoProto.FutureStubResp> futureStubTest(
        com.first.family.grpc.future.message.FutureStubDemoProto.FutureStubReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getFutureStubTestMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_FUTURE_STUB_TEST = 0;

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
        case METHODID_FUTURE_STUB_TEST:
          serviceImpl.futureStubTest((com.first.family.grpc.future.message.FutureStubDemoProto.FutureStubReq) request,
              (io.grpc.stub.StreamObserver<com.first.family.grpc.future.message.FutureStubDemoProto.FutureStubResp>) responseObserver);
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
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getFutureStubTestMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.first.family.grpc.future.message.FutureStubDemoProto.FutureStubReq,
              com.first.family.grpc.future.message.FutureStubDemoProto.FutureStubResp>(
                service, METHODID_FUTURE_STUB_TEST)))
        .build();
  }

  private static abstract class FutureStubServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    FutureStubServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.first.family.grpc.future.message.FutureStubDemoProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("FutureStubService");
    }
  }

  private static final class FutureStubServiceFileDescriptorSupplier
      extends FutureStubServiceBaseDescriptorSupplier {
    FutureStubServiceFileDescriptorSupplier() {}
  }

  private static final class FutureStubServiceMethodDescriptorSupplier
      extends FutureStubServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    FutureStubServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (FutureStubServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new FutureStubServiceFileDescriptorSupplier())
              .addMethod(getFutureStubTestMethod())
              .build();
        }
      }
    }
    return result;
  }
}
