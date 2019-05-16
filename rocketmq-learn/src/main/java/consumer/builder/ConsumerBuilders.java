package consumer.builder;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

public class ConsumerBuilders {

    public static MQPushConsumer buildConsumer(String consumerGroup, String instanceName, String clientIP, String subscribeTopic, String tags, String namesrvAddr, MessageListenerConcurrently messageListener) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();

        consumer.setConsumerGroup(consumerGroup);
        consumer.setInstanceName(instanceName);
        consumer.setClientIP(clientIP);
        consumer.subscribe(subscribeTopic, tags);

        consumer.setMaxReconsumeTimes(3);
        consumer.setMessageModel(MessageModel.CLUSTERING);
        consumer.setNamesrvAddr(namesrvAddr);

        consumer.setHeartbeatBrokerInterval(1000 * 30);
        consumer.setPollNameServerInterval(1000 * 30);
        consumer.setPersistConsumerOffsetInterval(1000 * 5);
        consumer.setPostSubscriptionWhenPull(false);
        consumer.setConsumeThreadMax(3);
        consumer.setConsumeThreadMin(1);
        //consumer.setAdjustThreadPoolNumsThreshold();
        consumer.setConsumeConcurrentlyMaxSpan(2000);
        consumer.setConsumeTimeout(15);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        //consumer.setConsumeTimestamp("20190516095401");
        consumer.registerMessageListener(messageListener);
        return consumer;
    }
}
