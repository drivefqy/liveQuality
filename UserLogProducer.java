package LiveQuality;

import com.alibaba.fastjson.JSON;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 实践是检验真理的唯一标准
 * 测试版本,数据直接到flink
 */
public class UserLogProducer extends RichSourceFunction {

    private static Random rand = new Random(System.currentTimeMillis()/10000);
    private static List list = new ArrayList<String >();
    private static List list1 = new ArrayList<String >();
    private static String [] cdnIps  =  null;
    static {
        for (int i = 0; i < 10000; i++) {
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
        return (String) list1.get(rand.nextInt(list1.size()-1));
    }

    @Override
    public void run(SourceContext sourceContext) throws Exception {
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
        String [] liveTime = {"2023-09-22 10:00:00","2023-09-22 12:00:00","2023-09-22 14:00:00","2023-09-22 18:00:00","2023-09-22 20:00:00"};
        Random random = new Random();
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
                    String.format("%.03f",random.nextDouble() * 50000) ,
                    String.format("%.03f",random.nextDouble() * 50) ,
                    osTypes[random.nextInt(osTypes.length)],
                    getUserId(),
                    "zeroValue",
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
            sourceContext.collect(json);
            Thread.sleep(50);
        }
    }

    @Override
    public void cancel() {

    }
}
