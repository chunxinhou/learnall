package consumer;

import consumer.builder.ConsumerBuilders;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.remoting.common.RemotingUtil;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class TtraceConsumer {

    public static void main(String[] args) throws MQClientException {

        String consumerGroup = "TtraceConsumerGroup";
        String instanceName = "trace";
        String clientIP = RemotingUtil.getLocalAddress();
        String subscribeTopic = "Test";
        String tags = "*";
        String namesrvAddr = "localhost:9876";

        MessageListenerConcurrently messageListener = new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt ext:msgs) {
                    try {
                        System.out.println(new String(ext.getBody(),"UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        };
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup,true);

        consumer.setConsumerGroup(consumerGroup);
        consumer.setInstanceName(instanceName);
        consumer.setClientIP(clientIP);
        consumer.subscribe(subscribeTopic, tags);
        consumer.registerMessageListener(messageListener);
        consumer.setMaxReconsumeTimes(3);
        consumer.setMessageModel(MessageModel.CLUSTERING);
        consumer.setNamesrvAddr(namesrvAddr);



        consumer.start();

    }
}
