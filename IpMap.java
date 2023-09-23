package LiveQuality;

/**
 * 实践是检验真理的唯一标准
 */
public class IpMap {
    private int A;
    private String province;
    private int B;

    public IpMap(int a, String province, int b, String city) {
        A = a;
        this.province = province;
        B = b;
        this.city = city;
    }

    private String city;

    public int getA() {
        return A;
    }

    public void setA(int a) {
        A = a;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getB() {
        return B;
    }

    public void setB(int b) {
        B = b;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}

