package pub.lskj.fund;

import java.util.LinkedList;
import java.util.List;

/**
 * 创建时间 2020/12/22 23:11
 *
 * @author dragon
 * @version 1.0
 */
public class Main {
    public static void main(String[] args) {
        List<MostPerMon.FundData> monthData = initMonthFundData();
        MostPerMon mostPerMon = new MostPerMon(2000, 10, monthData);
        mostPerMon.print(3);
    }

    private static List<MostPerMon.FundData> initMonthFundData() {
        List<MostPerMon.FundData> monthData = new LinkedList<>();
        for (int i = 0; i < 20; i++) {
            MostPerMon.FundData data = new MostPerMon.FundData();
            data.index = i;
            if (i % 2 == 0) {
                data.rate = 1F;
            } else {
                data.rate = -0.5F;
            }
            monthData.add(data);
        }
        return monthData;
    }
}
