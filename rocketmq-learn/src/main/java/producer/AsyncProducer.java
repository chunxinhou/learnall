package producer;

import bean.User;
import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.logging.InternalLogger;
import org.apache.rocketmq.logging.Slf4jLoggerFactory;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import producer.builder.ProducerBuilders;

/**
 * 同步消息
 * @author houchunxin
 */
public class AsyncProducer {

    private static final InternalLogger logger = Slf4jLoggerFactory.getLogger(AsyncProducer.class);
    public static void main(String[] args) throws Exception {

        String nameServer = "localhost:9876";
        String producerGroup = "AsyncProducer";
        String topic = "TopicTest";
        String tags = "TagA";
        DefaultMQProducer producer = ProducerBuilders.buildDefaultMQProducer(nameServer, producerGroup);

        for (int i = 0; true; i++) {
            Thread.sleep(2000);

            Integer key = i;
            User user =  new User(i,"龙妈",40,"女","风暴降生丹尼莉丝、不焚者、弥林女王、安达尔人，罗伊那人和先民的女王、七国君王、疆域守护者、多斯拉克大草原的卡丽熙、打碎镣铐者、 龙之母丹妮莉丝·塔格利亚");
            //Message msg = new Message(topic, tags , ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            Message msg = new Message(topic, tags, key.toString(), JSON.toJSONString(user).getBytes(RemotingHelper.DEFAULT_CHARSET));
//            msg.setDelayTimeLevel(1);
            msg.putUserProperty("i",String.valueOf(i)); //设置消息额外属性

            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    logger.info("onSuccess:{0}-{1}",JSON.toJSONString(msg),JSON.toJSONString(sendResult));
                }

                @Override
                public void onException(Throwable e) {
                    logger.info("onException:{0}",JSON.toJSONString(msg),e);
                }
            });

        }

       //producer.shutdown();
    }


}
