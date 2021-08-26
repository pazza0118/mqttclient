package com.example.mqtt_client2_v5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.eclipse.paho.mqttv5.client.IMqttToken;
import org.eclipse.paho.mqttv5.client.MqttAsyncClient;
import org.eclipse.paho.mqttv5.client.MqttCallback;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.client.MqttDisconnectResponse;
import org.eclipse.paho.mqttv5.client.persist.MemoryPersistence;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set topic, message, quality of service, broker, and clientID
        String topicPub     = "/test1";
        String topicSub1    = "/Vehicle/AmbientAirTemperature";
        String topicSub2    = "/Vehicle/Cabin/HVAC/AmbientAirTemperature";
        String content      = "Message from MqttPublishSample2";
        int qos             = 2;
        String broker       = "tcp://192.168.0.102:1883";
        String clientId     = "JavaSample2";
        MemoryPersistence persistence = new MemoryPersistence();

        try {

            // instance of connection option created, set as default
            MqttConnectionOptions connOpts = new MqttConnectionOptions();
            // When connecting to broker, tells it to clean the session state when the client disconnects
            connOpts.setCleanStart(false);

            // instance of client created with the broker and clientId, with persistence option
            MqttAsyncClient sampleClient = new MqttAsyncClient(broker, clientId, persistence);
            System.out.println("Connecting to broker: " + broker);

            // Connect: Client instance (already with broker and clientId) attempts to connect to broker using the default connect option
            IMqttToken token = sampleClient.connect(connOpts);
            token.waitForCompletion();
            System.out.println("Connected");
            System.out.println("Publishing message: "+content);

            // Publish: instance of message created with content (which is already defined as "Message from MqttPublishSample"), and set qos for message
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            token = sampleClient.publish(topicPub, message);
            token.waitForCompletion();

            // Subscribe
            System.out.println("Subscribing to topic: " + topicSub1);
            sampleClient.subscribe(topicSub1, 0);
            System.out.println("Subscribed to topic: " + topicSub1);

            System.out.println("Subscribing to topic: " + topicSub2);
            sampleClient.subscribe(topicSub2, 0);
            System.out.println("Subscribed to topic: " + topicSub2);

            sampleClient.setCallback(new MqttCallback() {
                @Override
                public void disconnected(MqttDisconnectResponse disconnectResponse) {

                }

                @Override
                public void mqttErrorOccurred(MqttException exception) {

                }

                @Override
                public void messageArrived(String topicSub, MqttMessage message) throws Exception {
                    System.out.println("Message Arrived!: " + topicSub + ": " + new String(message.getPayload()));
                }

                @Override
                public void deliveryComplete(IMqttToken token) {

                }

                @Override
                public void connectComplete(boolean reconnect, String serverURI) {

                }

                @Override
                public void authPacketArrived(int reasonCode, MqttProperties properties) {

                }
            });

//            // prints out the following before closing the instance
//            System.out.println("Disconnected");
//            System.out.println("Close client.");
//            // closes client stance and exit the system
//            sampleClient.close();
//            // Terminates JVM
//            System.exit(0);
        }
        // to catch bugs
        catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
    }
}