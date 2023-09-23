package LiveQuality;

import com.alibaba.fastjson.JSON;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.*;


/**
 * 实践是检验真理的唯一标准
 * 日志到kafka不使用flink版本
 */
public class LogToKafka_1  {
    public static void main(String[] args) throws InterruptedException {
        String[] projectKeys = {"直播间1", "直播间2", "直播间3","直播间4","直播间5","直播间6"};
        String[] cdnNames = {
                "Akamai Technologies",
                "Cloudflare",
                "Fastly",
                "Amazon CloudFront",
                "Microsoft Azure CDN",
                "Google Cloud CDN",
                "Alibaba Cloud CDN",
                "Tencent Cloud CDN",
                "ChinaCache",
                "Wangsu Science & Technology"
        };
        String[] isps = {"中国电信", "中国移动", "中国联通"};
        String[] eventKeys = {"playing", "play_stop","first_frame_view"};
        String[] osTypes = {"iOS", "Android","Windows","HarmonyOS","IOS","ios","android","windows","Linux","linux","harmonyos","mac","Mac"};
        Random random = new Random();
        String [] liveTime = {"2023-09-22 10:00:00","2023-09-22 12:00:00","2023-09-22 14:00:00","2023-09-22 18:00:00","2023-09-22 20:00:00"};
        //===================================================
        Properties con = new Properties();
        con.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.40.101:9092");
        con.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        con.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        con.put(ProducerConfig.ACKS_CONFIG,"1");
        con.put(ProducerConfig.BATCH_SIZE_CONFIG,102400);  //最大拉去数量
        con.put(ProducerConfig.LINGER_MS_CONFIG,10);  //间隔多久拉去
        con.put(ProducerConfig.BUFFER_MEMORY_CONFIG,1024*1024*32);
        con.put(ProducerConfig.SEND_BUFFER_CONFIG,1024*1024*10);
        con.put(ProducerConfig.PARTITIONER_CLASS_CONFIG,MyPartitioner.class);
        KafkaProducer producer = new KafkaProducer(con);

        while (true){
            String json = JSON.toJSONString(new UserLog(
                    projectKeys[random.nextInt(projectKeys.length)],
                    cdnNames[random.nextInt(cdnNames.length)],
                    getClientIp(),
                    cdnIps[random.nextInt(cdnIps.length)],
                    getDeviceId(),
                    isps[random.nextInt(isps.length)],
                    eventKeys[random.nextInt(eventKeys.length)],
                    getAppId(),
                    random.nextDouble() * 50000 + "",
                    random.nextDouble() * 50 + "",
                    osTypes[random.nextInt(osTypes.length)],
                    getUserId(),
                    random.nextBoolean() ? "true" : "false",
                    getLocalDns(),
                    random.nextInt(1000),
                    random.nextInt(3000),
                    random.nextInt(5000),
                    random.nextInt(7000),
                    random.nextInt(10000),
                    System.currentTimeMillis() / 1000,
                    random.nextInt(5000),
                    random.nextInt(2),
                    liveTime[rand.nextInt(liveTime.length)]
            ));
            producer.send(new ProducerRecord(Common.topic,json));
            Thread.sleep(200);
        }
    }
    private static Random rand = new Random(System.currentTimeMillis()/10000);
    private static List list = new ArrayList<String >();
    private static List list1 = new ArrayList<String >();
    private static String [] cdnIps  =  null;
    static {
        for (int i = 0; i < 100; i++) {
            list.add(UUID.randomUUID().toString());
        }
        list1.add("8.8.8.8");
        list1.add("8.8.4.4");
        list1.add("1.1.1.1");
        list1.add("1.0.0.1");
        list1.add("208.67.222.222");
        list1.add("208.67.220.220");
        list1.add("9.9.9.9");
        list1.add("149.112.112.112");
        list1.add("114.114.114.114");
        cdnIps = new String[]{
                "23.0.0.0",
                "23.0.0.8",
                "104.64.0.0",
                "104.64.0.10",
                "184.84.0.0",
                "184.84.0.14",
                "173.245.48.20",
                "103.21.244.22",
                "151.101.0.16",
                "13.32.0.15",
                "34.192.0.10",
                "10.0.0.1",
                "10.0.0.2",
                "10.0.0.3",
                "10.0.0.4",
        };
    }
    public static String getClientIp(){
        String ip = null;
        do {
            ip = (rand.nextInt(255)) + ":" +
                    (rand.nextInt(255) ) + ":" +
                    (rand.nextInt(255) ) + ":" +
                    (rand.nextInt(255) );
        }
        while (Arrays.asList(cdnIps).contains(ip));
        return ip;
    }
    public static String getDeviceId(){
        return (Math.random()+"").substring(3);
    }
    public static String getAppId(){
        String [] appVersion = {"3.0.0","3.1.0","3.2.0","3.5.0","4.0.0","8.0.0","8.1.x","8.2.x"};
        return appVersion[rand.nextInt(appVersion.length)];
    }
    public static String getUserId(){
        return (String) list.get(rand.nextInt(list.size()-1));
    }
    public static String getLocalDns(){
        return (String) list1.get(list1.size()-1);
    }

}

