package consumer;

import consumer.ConsumerA.ConsumerA3;
import consumer.builder.ConsumerBuilders;
import org.apache.rocketmq.client.consumer.MQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
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

/**
 * @author houchunxin
 *
 *消息过滤器的简单使用
 */
public class FilterConsumer {

    private static final InternalLogger logger = Slf4jLoggerFactory.getLogger(ConsumerA3.class);
    public static void main(String[] args) throws MQClientException {

        String consumerGroup = "FilterConsumerGroup";
        String instanceName = "FilterConsumer1";
        String clientIP = RemotingUtil.getLocalAddress();
        String subscribeTopic = "filter";
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

        MQPushConsumer consumer = ConsumerBuilders.buildConsumer(consumerGroup, instanceName, clientIP, subscribeTopic, tags, namesrvAddr, messageListener);

        //consumer.subscribe(subscribeTopic, MessageSelector.byTag("tag1 || tag2 || tag3"));
        /*
            Numeric comparison, like >, >=, <, <=, BETWEEN, =;
            Character comparison, like =, <>, IN;
            IS NULL or IS NOT NULL;
            Logical AND, OR, NOT;
            Constant types are:

            Numeric, like 123, 3.1415;
            Character, like ‘abc’, must be made with single quotes;
            NULL, special constant;
            Boolean, TRUE or FALSE;
         */
        consumer.subscribe(subscribeTopic, MessageSelector.bySql("i >= 0 and i <= 5"));
        consumer.start();

    }

}
