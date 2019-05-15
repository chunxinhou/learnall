package producer;

import bean.User;
import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import producer.builder.ProducerBuilders;

/**
 * @author houchunxin
 * 用户不关心消息是否发送成功
 */
public class OnewayProducer {
    public static void main(String[] args) throws Exception {

        String nameServer = "localhost:9876";
        String producerGroup = "OnewayProducer";
        String topic = "TopicTest";
        String tags = "TagA";
        DefaultMQProducer producer = ProducerBuilders.buildDefaultMQProducer(nameServer, producerGroup);


        for (int i = 0; true; i++) {
            Thread.sleep(2000);

            Integer key = i;
            User user =  new User(i,"龙妈",40,"女","风暴降生丹尼莉丝、不焚者、弥林女王、安达尔人，罗伊那人和先民的女王、七国君王、疆域守护者、多斯拉克大草原的卡丽熙、打碎镣铐者、 龙之母丹妮莉丝·塔格利亚");
            //Message msg = new Message(topic, tags , ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            Message msg = new Message(topic, tags, key.toString(), JSON.toJSONString(user).getBytes(RemotingHelper.DEFAULT_CHARSET));

            producer.sendOneway(msg);
            //producer.sendOneway(msg,new MessageQueue(topic,"brokerName",1));
//            producer.sendOneway(msg, new MessageQueueSelector() {
//                @Override
//                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
//                    int i1 = (Integer)arg % mqs.size();
//                    return mqs.get(i1);
//                }
//            },key);

        }

        //producer.shutdown();
    }

}
