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
import producer.util.ListSplitter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author houchunxin
 */
public class BatchProducer {


    public static void main(String[] args) throws Exception {

        String nameServer = "localhost:9876";
        String producerGroup = "BatchProducer";
        String topic = "TopicTest";
        String tags = "TagA";
        DefaultMQProducer producer = ProducerBuilders.buildDefaultMQProducer(nameServer, producerGroup);

        for (int i = 0; true; i++) {
            Thread.sleep(2000);

            Integer key = i;
            Integer age = 40;
            User user =  new User(i,"龙妈",age,"女","风暴降生丹尼莉丝、不焚者、弥林女王、安达尔人，罗伊那人和先民的女王、七国君王、疆域守护者、多斯拉克大草原的卡丽熙、打碎镣铐者、 龙之母丹妮莉丝·塔格利亚");
            Message msg = new Message(topic, tags, key.toString(), JSON.toJSONString(user).getBytes(RemotingHelper.DEFAULT_CHARSET));
            Message msg1 = new Message(topic, tags, key.toString(), JSON.toJSONString(user.setAge(age+1)).getBytes(RemotingHelper.DEFAULT_CHARSET));
            Message msg2 = new Message(topic, tags, key.toString(), JSON.toJSONString(user.setAge(age+1)).getBytes(RemotingHelper.DEFAULT_CHARSET));

            List<Message> messageList = new ArrayList<>();
            messageList.add(msg);
            messageList.add(msg1);
            messageList.add(msg2);
            //SendResult send = producer.send(messageList);

            ListSplitter splitter = new ListSplitter(messageList);
            while (splitter.hasNext()) {
                try {
                    List<Message>  listItem = splitter.next();
                    SendResult send =  producer.send(listItem);
                    System.out.printf("%s%n", send);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        //producer.shutdown();
    }

}
