package LiveQuality;



import com.alibaba.fastjson.JSON;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.partitioner.FlinkKafkaPartitioner;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import java.util.Properties;

/**
 * 实践是检验真理的唯一标准
 * log数据到Kafka flink 版本
 */
public class LogToKafka {
    public static void main(String[] args) throws Exception {
        Properties con = new Properties();
        con.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        con.put(ProducerConfig.ACKS_CONFIG,"1");
        con.put(ProducerConfig.BATCH_SIZE_CONFIG,102400);  //最大拉去数量
        con.put(ProducerConfig.LINGER_MS_CONFIG,10);  //间隔多久拉去
        con.put(ProducerConfig.BUFFER_MEMORY_CONFIG,1024*1024*32);
        con.put(ProducerConfig.SEND_BUFFER_CONFIG,1024*1024*10);
        KafkaSink<String> kafkaSink = KafkaSink.<String>builder()
                .setBootstrapServers(Common.bootstrap_service1)
                .setKafkaProducerConfig(con)
                .setRecordSerializer(
                        KafkaRecordSerializationSchema
                                .builder()
                                .setTopic(Common.topic)
                                .setValueSerializationSchema(new SimpleStringSchema())
                                .setPartitioner(new FlinkKafkaPartitioner<String>() {
                                    @Override
                                    public int partition(String record, byte[] key, byte[] value, String targetTopic, int[] partitions) {
                                        try{
                                            return (((UserLog)JSON.parse(new String(value))).getProjectKey().hashCode()) % 5;
                                        }catch (Exception e){
                                            return 0;
                                        }
                                    }
                                })
                                .build()
                ).build();
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.enableCheckpointing(100000).setRestartStrategy(RestartStrategies.fixedDelayRestart(5,1000));
        CheckpointConfig checkpointConfig = env.getCheckpointConfig();
        checkpointConfig.setCheckpointStorage("file:///D:\\java project IDEA\\FlinkProject\\ck");
        checkpointConfig.setExternalizedCheckpointCleanup(CheckpointConfig.ExternalizedCheckpointCleanup.DELETE_ON_CANCELLATION);
        env.setParallelism(4);
        DataStreamSource<String> source = env.addSource(new UserLogProducer(), TypeInformation.of(String.class));
        source.sinkTo(kafkaSink);

        env.execute();
    }

}

