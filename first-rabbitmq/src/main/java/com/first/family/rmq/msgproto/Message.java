// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: message.proto

package com.first.family.rmq.msgproto;

/**
 * <pre>
 * Route Message definition
 * </pre>
 *
 * Protobuf type {@code amqp.Message}
 */
public final class Message extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:amqp.Message)
    MessageOrBuilder {
private static final long serialVersionUID = 0L;
  // Use Message.newBuilder() to construct.
  private Message(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private Message() {
    apiVersion_ = "";
    message_ = com.google.protobuf.ByteString.EMPTY;
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new Message();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.first.family.rmq.msgproto.AMQPMessageProto.internal_static_amqp_Message_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.first.family.rmq.msgproto.AMQPMessageProto.internal_static_amqp_Message_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.first.family.rmq.msgproto.Message.class, com.first.family.rmq.msgproto.Message.Builder.class);
  }

  public static final int APIVERSION_FIELD_NUMBER = 1;
  @SuppressWarnings("serial")
  private volatile java.lang.Object apiVersion_ = "";
  /**
   * <pre>
   * message format version, current is 'v1'
   * </pre>
   *
   * <code>string apiVersion = 1;</code>
   * @return The apiVersion.
   */
  @java.lang.Override
  public java.lang.String getApiVersion() {
    java.lang.Object ref = apiVersion_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      apiVersion_ = s;
      return s;
    }
  }
  /**
   * <pre>
   * message format version, current is 'v1'
   * </pre>
   *
   * <code>string apiVersion = 1;</code>
   * @return The bytes for apiVersion.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getApiVersionBytes() {
    java.lang.Object ref = apiVersion_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      apiVersion_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int QOS_FIELD_NUMBER = 2;
  private int qos_ = 0;
  /**
   * <pre>
   * message qos 0
   * </pre>
   *
   * <code>int32 qos = 2;</code>
   * @return The qos.
   */
  @java.lang.Override
  public int getQos() {
    return qos_;
  }

  public static final int METADATA_FIELD_NUMBER = 3;
  private com.first.family.rmq.msgproto.Metadata metadata_;
  /**
   * <pre>
   * metadata for the message
   * </pre>
   *
   * <code>.model.Metadata metadata = 3;</code>
   * @return Whether the metadata field is set.
   */
  @java.lang.Override
  public boolean hasMetadata() {
    return metadata_ != null;
  }
  /**
   * <pre>
   * metadata for the message
   * </pre>
   *
   * <code>.model.Metadata metadata = 3;</code>
   * @return The metadata.
   */
  @java.lang.Override
  public com.first.family.rmq.msgproto.Metadata getMetadata() {
    return metadata_ == null ? com.first.family.rmq.msgproto.Metadata.getDefaultInstance() : metadata_;
  }
  /**
   * <pre>
   * metadata for the message
   * </pre>
   *
   * <code>.model.Metadata metadata = 3;</code>
   */
  @java.lang.Override
  public com.first.family.rmq.msgproto.MetadataOrBuilder getMetadataOrBuilder() {
    return metadata_ == null ? com.first.family.rmq.msgproto.Metadata.getDefaultInstance() : metadata_;
  }

  public static final int MESSAGE_FIELD_NUMBER = 4;
  private com.google.protobuf.ByteString message_ = com.google.protobuf.ByteString.EMPTY;
  /**
   * <pre>
   * user specified message
   * </pre>
   *
   * <code>bytes message = 4;</code>
   * @return The message.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getMessage() {
    return message_;
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(apiVersion_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, apiVersion_);
    }
    if (qos_ != 0) {
      output.writeInt32(2, qos_);
    }
    if (metadata_ != null) {
      output.writeMessage(3, getMetadata());
    }
    if (!message_.isEmpty()) {
      output.writeBytes(4, message_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(apiVersion_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, apiVersion_);
    }
    if (qos_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(2, qos_);
    }
    if (metadata_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(3, getMetadata());
    }
    if (!message_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(4, message_);
    }
    size += getUnknownFields().getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof com.first.family.rmq.msgproto.Message)) {
      return super.equals(obj);
    }
    com.first.family.rmq.msgproto.Message other = (com.first.family.rmq.msgproto.Message) obj;

    if (!getApiVersion()
        .equals(other.getApiVersion())) return false;
    if (getQos()
        != other.getQos()) return false;
    if (hasMetadata() != other.hasMetadata()) return false;
    if (hasMetadata()) {
      if (!getMetadata()
          .equals(other.getMetadata())) return false;
    }
    if (!getMessage()
        .equals(other.getMessage())) return false;
    if (!getUnknownFields().equals(other.getUnknownFields())) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + APIVERSION_FIELD_NUMBER;
    hash = (53 * hash) + getApiVersion().hashCode();
    hash = (37 * hash) + QOS_FIELD_NUMBER;
    hash = (53 * hash) + getQos();
    if (hasMetadata()) {
      hash = (37 * hash) + METADATA_FIELD_NUMBER;
      hash = (53 * hash) + getMetadata().hashCode();
    }
    hash = (37 * hash) + MESSAGE_FIELD_NUMBER;
    hash = (53 * hash) + getMessage().hashCode();
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.first.family.rmq.msgproto.Message parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.first.family.rmq.msgproto.Message parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.first.family.rmq.msgproto.Message parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.first.family.rmq.msgproto.Message parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.first.family.rmq.msgproto.Message parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.first.family.rmq.msgproto.Message parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.first.family.rmq.msgproto.Message parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.first.family.rmq.msgproto.Message parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.first.family.rmq.msgproto.Message parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.first.family.rmq.msgproto.Message parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.first.family.rmq.msgproto.Message parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.first.family.rmq.msgproto.Message parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.first.family.rmq.msgproto.Message prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * <pre>
   * Route Message definition
   * </pre>
   *
   * Protobuf type {@code amqp.Message}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:amqp.Message)
      com.first.family.rmq.msgproto.MessageOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.first.family.rmq.msgproto.AMQPMessageProto.internal_static_amqp_Message_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.first.family.rmq.msgproto.AMQPMessageProto.internal_static_amqp_Message_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.first.family.rmq.msgproto.Message.class, com.first.family.rmq.msgproto.Message.Builder.class);
    }

    // Construct using com.first.family.rmq.msgproto.Message.newBuilder()
    private Builder() {

    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);

    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      apiVersion_ = "";
      qos_ = 0;
      metadata_ = null;
      if (metadataBuilder_ != null) {
        metadataBuilder_.dispose();
        metadataBuilder_ = null;
      }
      message_ = com.google.protobuf.ByteString.EMPTY;
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.first.family.rmq.msgproto.AMQPMessageProto.internal_static_amqp_Message_descriptor;
    }

    @java.lang.Override
    public com.first.family.rmq.msgproto.Message getDefaultInstanceForType() {
      return com.first.family.rmq.msgproto.Message.getDefaultInstance();
    }

    @java.lang.Override
    public com.first.family.rmq.msgproto.Message build() {
      com.first.family.rmq.msgproto.Message result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.first.family.rmq.msgproto.Message buildPartial() {
      com.first.family.rmq.msgproto.Message result = new com.first.family.rmq.msgproto.Message(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(com.first.family.rmq.msgproto.Message result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.apiVersion_ = apiVersion_;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.qos_ = qos_;
      }
      if (((from_bitField0_ & 0x00000004) != 0)) {
        result.metadata_ = metadataBuilder_ == null
            ? metadata_
            : metadataBuilder_.build();
      }
      if (((from_bitField0_ & 0x00000008) != 0)) {
        result.message_ = message_;
      }
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.first.family.rmq.msgproto.Message) {
        return mergeFrom((com.first.family.rmq.msgproto.Message)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.first.family.rmq.msgproto.Message other) {
      if (other == com.first.family.rmq.msgproto.Message.getDefaultInstance()) return this;
      if (!other.getApiVersion().isEmpty()) {
        apiVersion_ = other.apiVersion_;
        bitField0_ |= 0x00000001;
        onChanged();
      }
      if (other.getQos() != 0) {
        setQos(other.getQos());
      }
      if (other.hasMetadata()) {
        mergeMetadata(other.getMetadata());
      }
      if (other.getMessage() != com.google.protobuf.ByteString.EMPTY) {
        setMessage(other.getMessage());
      }
      this.mergeUnknownFields(other.getUnknownFields());
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 10: {
              apiVersion_ = input.readStringRequireUtf8();
              bitField0_ |= 0x00000001;
              break;
            } // case 10
            case 16: {
              qos_ = input.readInt32();
              bitField0_ |= 0x00000002;
              break;
            } // case 16
            case 26: {
              input.readMessage(
                  getMetadataFieldBuilder().getBuilder(),
                  extensionRegistry);
              bitField0_ |= 0x00000004;
              break;
            } // case 26
            case 34: {
              message_ = input.readBytes();
              bitField0_ |= 0x00000008;
              break;
            } // case 34
            default: {
              if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                done = true; // was an endgroup tag
              }
              break;
            } // default:
          } // switch (tag)
        } // while (!done)
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.unwrapIOException();
      } finally {
        onChanged();
      } // finally
      return this;
    }
    private int bitField0_;

    private java.lang.Object apiVersion_ = "";
    /**
     * <pre>
     * message format version, current is 'v1'
     * </pre>
     *
     * <code>string apiVersion = 1;</code>
     * @return The apiVersion.
     */
    public java.lang.String getApiVersion() {
      java.lang.Object ref = apiVersion_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        apiVersion_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     * message format version, current is 'v1'
     * </pre>
     *
     * <code>string apiVersion = 1;</code>
     * @return The bytes for apiVersion.
     */
    public com.google.protobuf.ByteString
        getApiVersionBytes() {
      java.lang.Object ref = apiVersion_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        apiVersion_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     * message format version, current is 'v1'
     * </pre>
     *
     * <code>string apiVersion = 1;</code>
     * @param value The apiVersion to set.
     * @return This builder for chaining.
     */
    public Builder setApiVersion(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      apiVersion_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * message format version, current is 'v1'
     * </pre>
     *
     * <code>string apiVersion = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearApiVersion() {
      apiVersion_ = getDefaultInstance().getApiVersion();
      bitField0_ = (bitField0_ & ~0x00000001);
      onChanged();
      return this;
    }
    /**
     * <pre>
     * message format version, current is 'v1'
     * </pre>
     *
     * <code>string apiVersion = 1;</code>
     * @param value The bytes for apiVersion to set.
     * @return This builder for chaining.
     */
    public Builder setApiVersionBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      apiVersion_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }

    private int qos_ ;
    /**
     * <pre>
     * message qos 0
     * </pre>
     *
     * <code>int32 qos = 2;</code>
     * @return The qos.
     */
    @java.lang.Override
    public int getQos() {
      return qos_;
    }
    /**
     * <pre>
     * message qos 0
     * </pre>
     *
     * <code>int32 qos = 2;</code>
     * @param value The qos to set.
     * @return This builder for chaining.
     */
    public Builder setQos(int value) {
      
      qos_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * message qos 0
     * </pre>
     *
     * <code>int32 qos = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearQos() {
      bitField0_ = (bitField0_ & ~0x00000002);
      qos_ = 0;
      onChanged();
      return this;
    }

    private com.first.family.rmq.msgproto.Metadata metadata_;
    private com.google.protobuf.SingleFieldBuilderV3<
        com.first.family.rmq.msgproto.Metadata, com.first.family.rmq.msgproto.Metadata.Builder, com.first.family.rmq.msgproto.MetadataOrBuilder> metadataBuilder_;
    /**
     * <pre>
     * metadata for the message
     * </pre>
     *
     * <code>.model.Metadata metadata = 3;</code>
     * @return Whether the metadata field is set.
     */
    public boolean hasMetadata() {
      return ((bitField0_ & 0x00000004) != 0);
    }
    /**
     * <pre>
     * metadata for the message
     * </pre>
     *
     * <code>.model.Metadata metadata = 3;</code>
     * @return The metadata.
     */
    public com.first.family.rmq.msgproto.Metadata getMetadata() {
      if (metadataBuilder_ == null) {
        return metadata_ == null ? com.first.family.rmq.msgproto.Metadata.getDefaultInstance() : metadata_;
      } else {
        return metadataBuilder_.getMessage();
      }
    }
    /**
     * <pre>
     * metadata for the message
     * </pre>
     *
     * <code>.model.Metadata metadata = 3;</code>
     */
    public Builder setMetadata(com.first.family.rmq.msgproto.Metadata value) {
      if (metadataBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        metadata_ = value;
      } else {
        metadataBuilder_.setMessage(value);
      }
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * metadata for the message
     * </pre>
     *
     * <code>.model.Metadata metadata = 3;</code>
     */
    public Builder setMetadata(
        com.first.family.rmq.msgproto.Metadata.Builder builderForValue) {
      if (metadataBuilder_ == null) {
        metadata_ = builderForValue.build();
      } else {
        metadataBuilder_.setMessage(builderForValue.build());
      }
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * metadata for the message
     * </pre>
     *
     * <code>.model.Metadata metadata = 3;</code>
     */
    public Builder mergeMetadata(com.first.family.rmq.msgproto.Metadata value) {
      if (metadataBuilder_ == null) {
        if (((bitField0_ & 0x00000004) != 0) &&
          metadata_ != null &&
          metadata_ != com.first.family.rmq.msgproto.Metadata.getDefaultInstance()) {
          getMetadataBuilder().mergeFrom(value);
        } else {
          metadata_ = value;
        }
      } else {
        metadataBuilder_.mergeFrom(value);
      }
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * metadata for the message
     * </pre>
     *
     * <code>.model.Metadata metadata = 3;</code>
     */
    public Builder clearMetadata() {
      bitField0_ = (bitField0_ & ~0x00000004);
      metadata_ = null;
      if (metadataBuilder_ != null) {
        metadataBuilder_.dispose();
        metadataBuilder_ = null;
      }
      onChanged();
      return this;
    }
    /**
     * <pre>
     * metadata for the message
     * </pre>
     *
     * <code>.model.Metadata metadata = 3;</code>
     */
    public com.first.family.rmq.msgproto.Metadata.Builder getMetadataBuilder() {
      bitField0_ |= 0x00000004;
      onChanged();
      return getMetadataFieldBuilder().getBuilder();
    }
    /**
     * <pre>
     * metadata for the message
     * </pre>
     *
     * <code>.model.Metadata metadata = 3;</code>
     */
    public com.first.family.rmq.msgproto.MetadataOrBuilder getMetadataOrBuilder() {
      if (metadataBuilder_ != null) {
        return metadataBuilder_.getMessageOrBuilder();
      } else {
        return metadata_ == null ?
            com.first.family.rmq.msgproto.Metadata.getDefaultInstance() : metadata_;
      }
    }
    /**
     * <pre>
     * metadata for the message
     * </pre>
     *
     * <code>.model.Metadata metadata = 3;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        com.first.family.rmq.msgproto.Metadata, com.first.family.rmq.msgproto.Metadata.Builder, com.first.family.rmq.msgproto.MetadataOrBuilder> 
        getMetadataFieldBuilder() {
      if (metadataBuilder_ == null) {
        metadataBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            com.first.family.rmq.msgproto.Metadata, com.first.family.rmq.msgproto.Metadata.Builder, com.first.family.rmq.msgproto.MetadataOrBuilder>(
                getMetadata(),
                getParentForChildren(),
                isClean());
        metadata_ = null;
      }
      return metadataBuilder_;
    }

    private com.google.protobuf.ByteString message_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <pre>
     * user specified message
     * </pre>
     *
     * <code>bytes message = 4;</code>
     * @return The message.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getMessage() {
      return message_;
    }
    /**
     * <pre>
     * user specified message
     * </pre>
     *
     * <code>bytes message = 4;</code>
     * @param value The message to set.
     * @return This builder for chaining.
     */
    public Builder setMessage(com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      message_ = value;
      bitField0_ |= 0x00000008;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * user specified message
     * </pre>
     *
     * <code>bytes message = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearMessage() {
      bitField0_ = (bitField0_ & ~0x00000008);
      message_ = getDefaultInstance().getMessage();
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:amqp.Message)
  }

  // @@protoc_insertion_point(class_scope:amqp.Message)
  private static final com.first.family.rmq.msgproto.Message DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.first.family.rmq.msgproto.Message();
  }

  public static com.first.family.rmq.msgproto.Message getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<Message>
      PARSER = new com.google.protobuf.AbstractParser<Message>() {
    @java.lang.Override
    public Message parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      Builder builder = newBuilder();
      try {
        builder.mergeFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(builder.buildPartial());
      } catch (com.google.protobuf.UninitializedMessageException e) {
        throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(e)
            .setUnfinishedMessage(builder.buildPartial());
      }
      return builder.buildPartial();
    }
  };

  public static com.google.protobuf.Parser<Message> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<Message> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.first.family.rmq.msgproto.Message getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
