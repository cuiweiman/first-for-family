package com.first.family.rmq.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.first.family.rmq.msgproto.Message;
import com.first.family.rmq.msgproto.Metadata;
import com.first.family.rmq.utli.JsonUtil;
import com.google.protobuf.ByteString;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @description:
 * @author: cuiweiman
 * @date: 2024/4/26 14:00
 */
@Component
public class RabbitProducer {


    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.route-key}")
    private String routeKey;

    private final RabbitTemplate rabbitTemplate;

    Map<String, Object> map = new ConcurrentSkipListMap<>();

    public RabbitProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        map.put("reqId", "12223215-e067-4422-90c3-610538ef9f64");
        map.put("method", "thing.event.post");
        map.put("version", "1.0");
        map.put("timestamp", 1648455840001L);
        map.put("data_type", "DEVICE_ONLINE_STATUS");
        map.put("data", "{\"DEVICE_ONLINE_STATUS\":{\"time\":\"2022-03-25 12:52:35\",\"dev_id\":\"16460354276399781646035427639978\",\"status\":0,\"upload_mode\":1}}");
        map.put("instanceId", "w2bi2098hecw4rkc");
        map.put("productKey", "hualunfeihu");
    }

    public void publish() {
        try {
            Metadata.Builder mb = Metadata.newBuilder();
            mb.setCreateTime(System.currentTimeMillis());
            Message.Builder builder = Message.newBuilder();
            builder.setMetadata(mb.build());
            builder.setMessage(ByteString.copyFrom(JsonUtil.toJson(map), StandardCharsets.UTF_8));
            Message message = builder.build();

            rabbitTemplate.convertAndSend(exchange, routeKey, message.toByteArray(), new MessagePostProcessor() {
                        @Override
                        public org.springframework.amqp.core.Message postProcessMessage(org.springframework.amqp.core.Message message) throws AmqpException {
                            message.getMessageProperties().setHeader("topic", "/thing/productKey/deviceId/event/post");
                            return message;
                        }
                    }
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
