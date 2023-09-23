package LiveQuality;

/**
 * 实践是检验真理的唯一标准
 */
public class UserLog {
    private  String projectKey ;
    private  String cdnName ;
    private  String clientIp ;
    private  String cdnIp ;
    private  String deviceId ;
    private  String ISP ;
    private  String eventKey ;
    private  String appId ;
    private  String stallTime;
    private  String stallCount;
    private  String osType;
    private  String userId ;
    private  String firstFrameBlocked  ;
    private  String localDns ;
    private  long localDnsParseTime ;
    private  long tcpConnect ;

    public long getLocalDnsParseTime() {
        return localDnsParseTime;
    }

    public void setLocalDnsParseTime(long localDnsParseTime) {
        this.localDnsParseTime = localDnsParseTime;
    }

    private  long firstPackage ;
    private  long firstVideo ;
    private  long playerRender;
    private  long timeStamp ;
    private  long firstFrameView ;
    private  int useful ;
    private  String time ;

    public String getProjectKey() {
        return projectKey;
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

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getCdnIp() {
        return cdnIp;
    }

    public void setCdnIp(String cdnIp) {
        this.cdnIp = cdnIp;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getISP() {
        return ISP;
    }

    public void setISP(String ISP) {
        this.ISP = ISP;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
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

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getFirstFrameView() {
        return firstFrameView;
    }

    public void setFirstFrameView(long firstFrameView) {
        this.firstFrameView = firstFrameView;
    }

    public int getUseful() {
        return useful;
    }

    public void setUseful(int useful) {
        this.useful = useful;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public UserLog(String projectKey, String cdnName, String clientIp, String cdnIp, String deviceId, String ISP, String eventKey, String appId, String stallTime, String stallCount, String osType, String userId, String firstFrameBlocked, String localDns,long localDnsParseTime,long tcpConnect, long firstPackage, long firstVideo, long playerRender, long timeStamp, long firstFrameView, int useful, String time) {
        this.projectKey = projectKey;
        this.cdnName = cdnName;
        this.clientIp = clientIp;
        this.cdnIp = cdnIp;
        this.deviceId = deviceId;
        this.ISP = ISP;
        this.eventKey = eventKey;
        this.appId = appId;
        this.stallTime = stallTime;
        this.stallCount = stallCount;
        this.osType = osType;
        this.userId = userId;
        this.firstFrameBlocked = firstFrameBlocked;
        this.localDns = localDns;
        this.localDnsParseTime = localDnsParseTime;
        this.tcpConnect = tcpConnect;
        this.firstPackage = firstPackage;
        this.firstVideo = firstVideo;
        this.playerRender = playerRender;
        this.timeStamp = timeStamp;
        this.firstFrameView = firstFrameView;
        this.useful = useful;
        this.time = time;
    }
}

