package LiveQuality;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import ru.yandex.clickhouse.ClickHouseConnection;
import ru.yandex.clickhouse.ClickHouseDriver;
import ru.yandex.clickhouse.ClickHouseStatement;
import ru.yandex.clickhouse.settings.ClickHouseProperties;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * 实践是检验真理的唯一标准
 */
public class flinkToClickhouse extends RichSinkFunction<ProcessLog> {
    private ClickHouseStatement statement = null;
    @Override
    public void open(Configuration parameters) throws Exception {
        String url = "jdbc:clickhouse://192.168.40.12:8123/learn";
        ClickHouseDriver clickHouseDriver = new ClickHouseDriver();
        ClickHouseProperties clickHouseProperties = new ClickHouseProperties();
        clickHouseProperties.setClientName("default");
        clickHouseProperties.setBufferSize(1024 * 100);
        ClickHouseConnection connect = clickHouseDriver.connect(url, clickHouseProperties);
        statement = clickHouseDriver.connect(url, clickHouseProperties).createStatement();
    }

    @Override
    public void invoke(ProcessLog value, Context context) throws Exception {
        String sql = "INSERT INTO liveQuality (projectKey, cdnName, cdnIp, cdnProvince, cdnCity, clientIp, clientProvince, clientCity, dt, OSType, appId, userId, ISP, eventKey, status, stallTime, stallCount, stallTimePer100, stallCountPer100, firstFrameBlocked, localDns, tcpConnect, localDnsParse, firstPackage, firstVideo, playerRender, firstFrameView,ts) VALUES ('" +
                value.getProjectKey() + "', '" +
                value.getCdnName() + "', '" +
                value.getCdnIp() + "', '" +
                value.getCdnProvince() + "', '" +
                value.getCdnCity() + "', '" +
                value.getClientIp() + "', '" +
                value.getClientProvince() + "', '" +
                value.getClientCity() + "', '" +
                value.getDt() + "', '" +
                value.getOSType() + "', '" +
                value.getAppId() + "', '" +
                value.getUserId() + "', '" +
                value.getISP() + "', '" +
                value.getEventKey() + "', " +
                value.getStatus() + ", '" +
                value.getStallTime() + "', '" +
                value.getStallCount() + "', " +
                value.getStallTimePer100() + ", " +
                value.getStallCountPer100() + ", '" +
                value.getFirstFrameBlocked() + "', '" +
                value.getLocalDns() + "', " +
                value.getTcpConnect() + ", " +
                value.getLocalDnsParse() + ", " +
                value.getFirstPackage() + ", " +
                value.getFirstVideo() + ", " +
                value.getPlayerRender() + ", " +
                value.getFirstFrameView()+ ", " +
                value.getTs() + ");";
        boolean execute = statement.execute(sql);
    }
}

