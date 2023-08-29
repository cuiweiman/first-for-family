package com.first.family.mqtt;

import com.first.family.mqtt.config.MqttBrokerInfo;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * @description: 普通TCP连接
 * @author: cuiweiman
 * @date: 2023/7/18 17:48
 */
public class SimpleSubscribe {

    public static void main(String[] args) throws MqttException {
        MqttBrokerInfo mqttBrokerInfo = new MqttBrokerInfo();
        MqttConnectOptions connectOptions = getConnectOptions(mqttBrokerInfo);
        MqttClient mqttClient = getMqttClient(mqttBrokerInfo);

        String[] topics = {"gc/media/+", "thing/#"};
        int[] qos = {0, 0};

        mqttClient.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                System.out.println("connectionLost: " + cause.getMessage());
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                System.out.println("==========================================");
                System.out.println("topic: " + topic);
                System.out.println("Qos: " + message.getQos());
                System.out.println("message content: " + new String(message.getPayload()));
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                System.out.println("deliveryComplete---------" + token.isComplete());
            }
        });

        mqttClient.connect(connectOptions);
        mqttClient.subscribe(topics, qos);
    }

    public static MqttConnectOptions getConnectOptions(MqttBrokerInfo mqttBrokerInfo) {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(mqttBrokerInfo.getUsername());
        options.setPassword(mqttBrokerInfo.getPassword().toCharArray());
        options.setConnectionTimeout(60);
        options.setKeepAliveInterval(60);
        return options;
    }

    public static MqttClient getMqttClient(MqttBrokerInfo mqttBrokerInfo) throws MqttException {
        return new MqttClient(mqttBrokerInfo.getTcp(), mqttBrokerInfo.getSubClientId(), new MemoryPersistence());
    }

}
