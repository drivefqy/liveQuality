package LiveQuality;

import com.alibaba.fastjson.JSON;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.time.Duration;

/**
 * 实践是检验真理的唯一标准
 * kafka数据到flink flink版本
 */
public class KafkaToFlink {
    public static void main(String[] args) throws Exception {
        KafkaSource<String> kafkaSource = KafkaSource.<String>builder()
                .setBootstrapServers(Common.bootstrap_service1)
                .setTopics(Common.topic)
                .setValueOnlyDeserializer(new SimpleStringSchema())
                .setGroupId(Common.group_id)
                .build();
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        /*在 Flink 中，.withIdleness()、.withTimestampAssigner()、.withWatermarkAlignment() 和 .createTimestampAssigner() 是四个不同的方法，它们有各自的功能和作用：
        .withIdleness()
        这个方法是指定作业中空闲任务的行为。通过使用此方法，您可以配置对于空闲任务的处理方式，例如设置空闲任务的超时时间或禁用空闲任务检测。它与 Watermark Strategy 没有直接关联，主要用于优化资源利用和作业性能。
        .withTimestampAssigner()
        Flink 中的事件流通常需要指定事件的时间戳（timestamp），以便在事件时间处理中进行窗口操作。.withTimestampAssigner() 方法用于给数据流中的元素分配事件时间戳。您可以自定义 TimestampAssigner 接口的实现，并在该方法中进行配置，以根据元素的属性为每个元素分配事件时间戳。
        .withWatermarkAlignment()
        在 Flink 的事件时间处理中，Watermark 用于追踪事件流的进度，告知系统如何处理延迟的事件。.withWatermarkAlignment() 方法用于配置 Watermark 的对齐策略。对齐策略定义了在哪个阶段生成 Watermark，并在生成 Watermark 之后如何对齐它们。
        .createTimestampAssigner()
        .createTimestampAssigner() 是 Flink 的旧 API 中用于为数据流分配事件时间戳的方法。在新版本的 Flink 中，官方推荐使用.withTimestampAssigner() 方法代替。.createTimestampAssigner() 方法已被标记为过时，但仍然保留用于向后兼容。*/
        WatermarkStrategy<String> wms = WatermarkStrategy.<String>forBoundedOutOfOrderness(Duration.ofSeconds(1))
                .withTimestampAssigner(new SerializableTimestampAssigner<String>() {
                    @Override
                    public long extractTimestamp(String stringIntegerTuple2, long l) {
                        try {
                            return ((UserLog) JSON.parse(stringIntegerTuple2)).getTimeStamp();
                        }catch (Exception e){
                            return System.currentTimeMillis() / 1000;
                        }

                    }
                });
        DataStreamSource<String> source = env.fromSource(kafkaSource, wms, "liveQualityStream");
        env.enableCheckpointing(100000).setRestartStrategy(RestartStrategies.fixedDelayRestart(5,1000));
        CheckpointConfig checkpointConfig = env.getCheckpointConfig();
        checkpointConfig.setCheckpointStorage("file:///D:\\java project IDEA\\FlinkProject\\ck");
        checkpointConfig.setExternalizedCheckpointCleanup(CheckpointConfig.ExternalizedCheckpointCleanup.DELETE_ON_CANCELLATION);
        env.setParallelism(4);
        source.assignTimestampsAndWatermarks(wms).print();
        env.execute();
    }
}

