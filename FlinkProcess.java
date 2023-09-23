package LiveQuality;

import com.alibaba.fastjson.JSON;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.common.io.InputFormat;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.common.state.ReadOnlyBroadcastState;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.connector.jdbc.JdbcInputFormat;
import org.apache.flink.streaming.api.datastream.BroadcastStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.streaming.api.functions.co.BroadcastProcessFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.util.*;
import java.util.function.Consumer;

/**
 * 实践是检验真理的唯一标准
 */
public class FlinkProcess {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        conf.setInteger("rest.port",8080);
        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(conf);
        env.setParallelism(6);
        env.setRestartStrategy(RestartStrategies.fixedDelayRestart(100,1));
        //todo 从kafka得到数据
        //DataStreamSource log = env.addSource(new KafkaToFlink_1(),"source" ,TypeInformation.of(String.class));

        //todo 测试直接生成
        DataStreamSource log = env.addSource(new UserLogProducer(),"source" ,TypeInformation.of(String.class));

        DataStreamSource mysql = env.addSource(new Mysql(), "mysql", TypeInformation.of(IpMap.class));
        MapStateDescriptor<String, IpMap> mapStateDescriptor = new MapStateDescriptor<>("ipMap", String.class, IpMap.class);
        BroadcastStream ipMap = mysql.broadcast(mapStateDescriptor);
        SingleOutputStreamOperator ipPrase = log.connect(ipMap).process(new BroadcastProcessFunction<String, IpMap, ProcessLog>() {
            @Override
            public void processElement(String s, BroadcastProcessFunction<String, IpMap, ProcessLog>.ReadOnlyContext readOnlyContext, Collector<ProcessLog> collector) throws Exception {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                ProcessLog processLog = new ProcessLog();
                UserLog userLog = JSON.parseObject(s,UserLog.class);
                Instant instant = Instant.ofEpochMilli(userLog.getTimeStamp());
                LocalDateTime localDate = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
                processLog.setAppId(userLog.getAppId());
                processLog.setDt(userLog.getTime());
                processLog.setTs(userLog.getTimeStamp());
                processLog.setProjectKey(userLog.getProjectKey());
                processLog.setCdnIp(userLog.getCdnIp());
                processLog.setClientIp(userLog.getClientIp());
                processLog.setFirstFrameBlocked(userLog.getFirstFrameBlocked());
                processLog.setUserId(userLog.getUserId());
                processLog.setFirstFrameView(userLog.getFirstFrameView());
                processLog.setTcpConnect(userLog.getTcpConnect());
                processLog.setStallTime(userLog.getStallTime());
                processLog.setStallCount(userLog.getStallCount());
                processLog.setPlayerRender(userLog.getPlayerRender());
                processLog.setOSType(userLog.getOsType());
                processLog.setLocalDnsParse(userLog.getLocalDnsParseTime());
                processLog.setLocalDns(userLog.getLocalDns());
                processLog.setISP(userLog.getISP());
                processLog.setFirstVideo(userLog.getFirstVideo());
                processLog.setFirstPackage(userLog.getFirstPackage());
                processLog.setCdnName(userLog.getCdnName());
                processLog.setAppId(userLog.getAppId());
                processLog.setEventKey(userLog.getEventKey());
                String[] split = userLog.getClientIp().split(":");
                String AB = split[0] + ":" + split[1];
                if (readOnlyContext.getBroadcastState(mapStateDescriptor).contains(AB)) {
                    IpMap ipMap1 = readOnlyContext.getBroadcastState(mapStateDescriptor).get(userLog.getClientIp().substring(0, 7));
                    IpMap ipMap2 = readOnlyContext.getBroadcastState(mapStateDescriptor).get(userLog.getCdnIp().substring(0, 7));
                    try{
                    processLog.setClientProvince(ipMap1.getProvince());
                    processLog.setClientCity(ipMap1.getCity());
                    processLog.setCdnCity(ipMap2.getCity());
                    processLog.setCdnProvince(ipMap2.getProvince());
                    }catch (Exception e){

                    }
                }else {
                    Random random = new Random();
                    int i = random.nextInt(1000);
                    int zeroValue = 0;
                    String [] province = {"湖北省","湖南省","四川省","广东省"};
                    String [] city = {"成都市","深圳市","广州市","武汉市","长沙市","东莞市"};
                    Iterator<Map.Entry<String, IpMap>> iterator = readOnlyContext.getBroadcastState(mapStateDescriptor).immutableEntries().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<String, IpMap> next = iterator.next();
                        zeroValue ++;
                        if (zeroValue == i){
                            processLog.setClientProvince(next.getValue().getProvince());
                            processLog.setClientCity(next.getValue().getCity());
                            processLog.setCdnCity(city[random.nextInt(city.length)]);
                            processLog.setCdnProvince(province[random.nextInt(province.length)]);
                            break;
                        }
                    }
                }
                collector.collect(processLog);
            }

            @Override
            public void processBroadcastElement(IpMap ipMap, BroadcastProcessFunction<String, IpMap, ProcessLog>.Context context, Collector<ProcessLog> collector) throws Exception {
                if (!context.getBroadcastState(mapStateDescriptor).contains(ipMap.getA() + ":" + ipMap.getB())) {
                    context.getBroadcastState(mapStateDescriptor).put(ipMap.getA() +":"+ ipMap.getB(),ipMap);
                }
            }
        });
        SingleOutputStreamOperator stallIndex = ipPrase.process(new ProcessFunction<ProcessLog,ProcessLog>() {
            @Override
            public void processElement(ProcessLog processLog, ProcessFunction<ProcessLog, ProcessLog>.Context context, Collector<ProcessLog> collector) throws Exception {
                if ("playing".equals(processLog.getEventKey())) {
                    try {
                        double cnt = Double.parseDouble(processLog.getStallCount());
                        double time = Double.parseDouble(processLog.getStallTime());
                        processLog.setStallCountPer100(Double.parseDouble(String.format("%.03f",cnt * 100 / 60)));
                        processLog.setStallTimePer100(Double.parseDouble(String.format("%.03f",time * 100 / 60)));
                        collector.collect(processLog);
                    } catch (Exception e) {
                        System.out.println(" String => Double 类型转换异常 写入到侧流输出");
                        context.output(new OutputTag<ProcessLog>("castException",TypeInformation.of(ProcessLog.class)),processLog);
                    }
                }else {
                    collector.collect(processLog);
                }
            }
        });

        SingleOutputStreamOperator unifyDimValue = stallIndex.map(new MapFunction<ProcessLog, ProcessLog>() {

            @Override
            public ProcessLog map(ProcessLog processLog) throws Exception {
                processLog.setCdnName(processLog.getCdnName().toLowerCase(Locale.ROOT));
                processLog.setOSType(processLog.getOSType().toLowerCase());
                return processLog;
            }
        });

        SingleOutputStreamOperator firstFrame = unifyDimValue.map(new MapFunction<ProcessLog, ProcessLog>() {
            @Override
            public ProcessLog map(ProcessLog processLog) throws Exception {
                if ("first_frame_view".equals(processLog.getEventKey())) {
                    long localDnsParse = processLog.getLocalDnsParse();
                    long tcpConnect = processLog.getTcpConnect();
                    long firstPackage = processLog.getFirstPackage();
                    long firstVideo = processLog.getFirstVideo();
                    long playerRender = processLog.getPlayerRender();
                    if ((tcpConnect - localDnsParse) <= 0 || firstPackage - tcpConnect <= 0 || firstVideo - firstPackage <= 0 || playerRender - firstVideo <= 0) {
                        processLog.setFirstFrameView(0);
                        processLog.setFirstFrameBlocked("fail");
                    } else {
                        processLog.setFirstFrameView(1);
                        processLog.setFirstFrameBlocked("finish");
                    }
                }
                return processLog;
            }
        });

        SingleOutputStreamOperator liveStatus = firstFrame.map(new MapFunction<ProcessLog, ProcessLog>() {

            @Override
            public ProcessLog map(ProcessLog processLog) throws Exception {
                if ("play_stop".equals(processLog.getEventKey())) {
                    if (LocalDateTime.parse(processLog.getDt(),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).compareTo(
                            LocalDateTime.now()
                            ) <= 0) {
                        processLog.setStatus(1);
                    }
                }
                return processLog;
            }
        });

        liveStatus.addSink(new flinkToClickhouse());
//        liveStatus.map(new MapFunction<ProcessLog,String>() {
//            @Override
//            public String map(ProcessLog o) throws Exception {
//                return JSON.toJSONString(o);
//            }
//        }).print();
        env.execute();
    }
}

