package LiveQuality;

import com.mysql.jdbc.Driver;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * 实践是检验真理的唯一标准
 * mysql数据到flink
 */
public class Mysql extends RichSourceFunction {

    @Override
    public void run(SourceContext sourceContext) throws Exception {
        Driver driver = new Driver();
        String url = "jdbc:mysql://localhost:3307/mysql8?serverTimezone=UTC";
        Properties properties = new Properties();
        properties.put("user","root");
        properties.put("password","mysql");
        Connection connect = driver.connect(url, properties);
        ResultSet resultSet = connect.prepareStatement("select * from ip_map").executeQuery();
        while (resultSet.next()) {
            int A = resultSet.getInt(2);
            String province = resultSet.getString(3);
            int B = resultSet.getInt(4);
            String city = resultSet.getString(5);
            sourceContext.collect(new IpMap(A,province,B,city));
        }
    }

    @Override
    public void cancel() {

    }
}

