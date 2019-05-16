package consumer;

import consumer.builder.ConsumerBuilders;
import org.apache.rocketmq.client.consumer.MQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.logging.InternalLogger;
import org.apache.rocketmq.logging.Slf4jLoggerFactory;
import org.apache.rocketmq.remoting.common.RemotingUtil;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class ConsumerA2 {

    private static final InternalLogger logger = Slf4jLoggerFactory.getLogger(ConsumerA3.class);
    public static void main(String[] args) throws MQClientException {

        String consumerGroup = "ConsumerA";
        String instanceName = "ConsumerA2";
        String clientIP = RemotingUtil.getLocalAddress();
        String subscribeTopic = "TopicTest";
        String tags = "*";
        String namesrvAddr = "localhost:9876";

        MessageListenerConcurrently messageListener = new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt ext:msgs) {
                    try {
                        logger.info(new String(ext.getBody(),"UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        };

        MQPushConsumer consumer = ConsumerBuilders.buildConsumer(consumerGroup, instanceName, clientIP, subscribeTopic, tags, namesrvAddr, messageListener);
        consumer.start();


    }
}
