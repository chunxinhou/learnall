package producer.builder;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;

public class ProducerBuilders {

    public static DefaultMQProducer buildDefaultMQProducer(String nameServer, String producerGroup) throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer();
        producer.setNamesrvAddr(nameServer);
        producer.setProducerGroup(producerGroup);

        producer.setRetryTimesWhenSendFailed(2);
        //producer.setRetryTimesWhenSendAsyncFailed(2);
        producer.setMaxMessageSize(1024 * 1024 * 4);
        producer.setDefaultTopicQueueNums(4);
        producer.setSendMsgTimeout(3000);
        producer.setCompressMsgBodyOverHowmuch(4096);
        producer.setRetryAnotherBrokerWhenNotStoreOK(false);
        producer.start();
        return producer;
    }
}
