package LiveQuality;

import java.time.LocalDate;

/**
 * 实践是检验真理的唯一标准
 */
public class ProcessLog {
    private  String projectKey ;
    private  String cdnName ;
    private  String cdnIp ;
    private  String cdnProvince ="默认省份=>北京市";
    private  String cdnCity ="默认省份=>北京市" ;
    private  String clientIp ;
    private  String clientProvince ="默认省份=>北京市";
    private  String clientCity ="默认省份=>北京市";
    private  String dt;

    private  String OSType;
    private  String appId;
    private  String userId;
    private  String ISP;

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    private  String eventKey;
    private  long ts;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private  int status = 0;

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    private  String stallTime;
    private  String stallCount;
    private  double stallTimePer100;
    private  double stallCountPer100;

    private  String firstFrameBlocked  ;
    private  String localDns ;

    private  long tcpConnect ;
    private  long localDnsParse;
    private  long firstPackage ;
    private  long firstVideo ;
    private  long playerRender;
    private  long firstFrameView ;

    public String getProjectKey() {
        return projectKey;
    }

    public ProcessLog() {

    }

    public void setProjectKey(String projectKey) {
        this.projectKey = projectKey;
    }

    public String getCdnName() {
        return cdnName;
    }

    public void setCdnName(String cdnName) {
        this.cdnName = cdnName;
    }

    public String getCdnIp() {
        return cdnIp;
    }

    public void setCdnIp(String cdnIp) {
        this.cdnIp = cdnIp;
    }

    public String getCdnProvince() {
        return cdnProvince;
    }

    public void setCdnProvince(String cdnProvince) {
        this.cdnProvince = cdnProvince;
    }

    public String getCdnCity() {
        return cdnCity;
    }

    public void setCdnCity(String cdnCity) {
        this.cdnCity = cdnCity;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getClientProvince() {
        return clientProvince;
    }

    public void setClientProvince(String clientProvince) {
        this.clientProvince = clientProvince;
    }

    public String getClientCity() {
        return clientCity;
    }

    public void setClientCity(String clientCity) {
        this.clientCity = clientCity;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getOSType() {
        return OSType;
    }

    public void setOSType(String OSType) {
        this.OSType = OSType;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getISP() {
        return ISP;
    }

    public void setISP(String ISP) {
        this.ISP = ISP;
    }

    public String getStallTime() {
        return stallTime;
    }

    public void setStallTime(String stallTime) {
        this.stallTime = stallTime;
    }

    public String getStallCount() {
        return stallCount;
    }

    public void setStallCount(String stallCount) {
        this.stallCount = stallCount;
    }

    public double getStallTimePer100() {
        return stallTimePer100;
    }

    public void setStallTimePer100(double stallTimePer100) {
        this.stallTimePer100 = stallTimePer100;
    }

    public double getStallCountPer100() {
        return stallCountPer100;
    }

    public void setStallCountPer100(double stallCountPer100) {
        this.stallCountPer100 = stallCountPer100;
    }

    public String getFirstFrameBlocked() {
        return firstFrameBlocked;
    }

    public void setFirstFrameBlocked(String firstFrameBlocked) {
        this.firstFrameBlocked = firstFrameBlocked;
    }

    public String getLocalDns() {
        return localDns;
    }

    public void setLocalDns(String localDns) {
        this.localDns = localDns;
    }

    public long getTcpConnect() {
        return tcpConnect;
    }

    public void setTcpConnect(long tcpConnect) {
        this.tcpConnect = tcpConnect;
    }

    public long getLocalDnsParse() {
        return localDnsParse;
    }

    public void setLocalDnsParse(long localDnsParse) {
        this.localDnsParse = localDnsParse;
    }

    public long getFirstPackage() {
        return firstPackage;
    }

    public void setFirstPackage(long firstPackage) {
        this.firstPackage = firstPackage;
    }

    public long getFirstVideo() {
        return firstVideo;
    }

    public void setFirstVideo(long firstVideo) {
        this.firstVideo = firstVideo;
    }

    public long getPlayerRender() {
        return playerRender;
    }

    public void setPlayerRender(long playerRender) {
        this.playerRender = playerRender;
    }

    public long getFirstFrameView() {
        return firstFrameView;
    }

    public void setFirstFrameView(long firstFrameView) {
        this.firstFrameView = firstFrameView;
    }

    public ProcessLog(String projectKey, String cdnName, String cdnIp, String cdnProvince, String cdnCity, String clientIp, String clientProvince, String clientCity, String dt, String OSType, String appId, String userId, String ISP, String stallTime, String stallCount, double stallTimePer100, double stallCountPer100, String firstFrameBlocked, String localDns, long tcpConnect, long localDnsParse, long firstPackage, long firstVideo, long playerRender, long firstFrameView) {
        this.projectKey = projectKey;
        this.cdnName = cdnName;
        this.cdnIp = cdnIp;
        this.cdnProvince = cdnProvince;
        this.cdnCity = cdnCity;
        this.clientIp = clientIp;
        this.clientProvince = clientProvince;
        this.clientCity = clientCity;
        this.dt = dt;
        this.OSType = OSType;
        this.appId = appId;
        this.userId = userId;
        this.ISP = ISP;
        this.stallTime = stallTime;
        this.stallCount = stallCount;
        this.stallTimePer100 = stallTimePer100;
        this.stallCountPer100 = stallCountPer100;
        this.firstFrameBlocked = firstFrameBlocked;
        this.localDns = localDns;
        this.tcpConnect = tcpConnect;
        this.localDnsParse = localDnsParse;
        this.firstPackage = firstPackage;
        this.firstVideo = firstVideo;
        this.playerRender = playerRender;
        this.firstFrameView = firstFrameView;
    }
}
