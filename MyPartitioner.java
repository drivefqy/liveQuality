package LiveQuality;

import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * 实践是检验真理的唯一标准
 * kafka 分区
 */
public class MyPartitioner implements Partitioner {
    @Override
    public int partition(String s, Object o, byte[] bytes, Object o1, byte[] bytes1, Cluster cluster) {
        try{
            return (((UserLog) JSON.parse(new String(bytes1))).getProjectKey().hashCode()) % 5;
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}

