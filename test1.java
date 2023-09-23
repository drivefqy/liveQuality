package LiveQuality;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 实践是检验真理的唯一标准
 * 测试类
 */
public class test1 {
    public static void main(String[] args) throws UnknownHostException {
        System.out.println(LocalDateTime.parse("2022-11-11 11:11:11", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).compareTo(
                LocalDateTime.parse("2023-11-11 11:11:11", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));

    }
}

