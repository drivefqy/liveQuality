package LiveQuality;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Properties;

/**
 * 实践是检验真理的唯一标准
 * kafka 数据到 flink 自定义source版本
 */
public class KafkaToFlink_1 extends RichSourceFunction {
    KafkaConsumer<String, String> consumer = null;
    @Override
    public void open(Configuration parameters) throws Exception {
        Properties conf = new Properties();
        conf.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.40.101:9092");
        conf.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        conf.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        conf.put(ConsumerConfig.GROUP_ID_CONFIG,"1");
        conf.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG,1024*1024*100);  // 最大拉取字节数  100M
        conf.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG,500);   // 最大等待毫秒数  500ms
        conf.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG,1024);  // 最小拉取字节数     1 K
        conf.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,1000) ; //最大拉取消息条数  1000条
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(conf);
        ArrayList<String> list = new ArrayList<>();
        list.add("liveQualityTopic");
        consumer.subscribe(list);
    }

    @Override
    public void run(SourceContext sourceContext) throws Exception {
        while (true){
            consumer.poll(Duration.ofSeconds(5));   // 5秒拉去一次
            ConsumerRecords<String, String> str = consumer.poll(Duration.ofSeconds(5));// 5秒拉去一次

            for (ConsumerRecord<String, String> i : str) {
                sourceContext.collect(i.value());
            }
        }
    }

    @Override
    public void cancel() {

    }
}

