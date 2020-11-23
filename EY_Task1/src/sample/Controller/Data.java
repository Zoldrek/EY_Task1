package sample.Controller;
//класс, представляющий строки файла
public class Data {
    private int intnum;
    private double doublenum;
    private String date;
    private String rus;
    private String eng;


    public int getIntnum() {
        return intnum;
    }

    public void setIntnum(int intnum) {
        this.intnum = intnum;
    }

    public double getDoublenum() {
        return doublenum;
    }

    public void setDoublenum(double doublenum) {
        this.doublenum = doublenum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRus() {
        return rus;
    }

    public void setRus(String rus) {
        this.rus = rus;
    }

    public String getEng() {
        return eng;
    }

    public void setEng(String eng) {
        this.eng = eng;
    }

    public Data(String date, String eng, String rus, int intnum, double doublenum) {
        this.date=date;
        this.intnum=intnum;
        this.doublenum=doublenum;
        this.rus=rus;
        this.eng=eng;
    }
}
