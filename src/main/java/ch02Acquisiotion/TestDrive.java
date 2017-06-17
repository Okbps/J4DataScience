package ch02Acquisiotion;

/**
 * Created by Aspire on 10.06.2017.
 */
public class TestDrive {
    public static void main(String[] args) {
//        DBUnderstanding dbs = new DBUnderstanding();
//        dbs.connect();

        HttpAcquisition httpAcquisition = new HttpAcquisition();
        httpAcquisition.connect();
    }
}
