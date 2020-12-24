package pub.lskj.fund;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

/**
 * 创建时间 2020/12/22 23:11
 *
 * @author dragon
 * @version 1.0
 */
public class Main {
    static int index = 0;

    public static void main(String[] args) {
        // 招商医药
        List<MostPerMon.FundData> monthData = new LinkedList<>();
        monthData.addAll(getMonthFundData("161726-2020-11"));
        monthData.addAll(getMonthFundData("161726-2020-12"));
        // 招商白酒
//        List<MostPerMon.FundData> monthData = getMonthFundData("161725-2020-12");
        // 国泰食品
//        List<MostPerMon.FundData> monthData = getMonthFundData("160222-2020-12");
        // 国泰新能源
//        List<MostPerMon.FundData> monthData = getMonthFundData("160225-2020-12");
        MostPerMon mostPerMon = new MostPerMon(4000, 0.05F, monthData);
        mostPerMon.print(3);
    }

    private static List<MostPerMon.FundData> getMonthFundData(String fileName) {
        List<MostPerMon.FundData> monthData = new LinkedList<>();
        InputStream fundDataStream = Main.class.getResourceAsStream(fileName + ".json");
        String fundDataString = getContent(fundDataStream);
        JSONArray array = JSON.parseArray(fundDataString);
        for (int i = 0; i < array.size(); i++, index++) {
            JSONObject jo = ((JSONObject) array.get(i));
            MostPerMon.FundData fundData = new MostPerMon.FundData(index, jo.getFloatValue("value"), jo.getFloatValue("rate"));
            monthData.add(fundData);
        }
        return monthData;
    }

    public static String getContent(InputStream in) {
        String result = "";
        if (null != in) {
            StringBuffer content = null;
            BufferedReader r = new BufferedReader(new InputStreamReader(in));
            content = new StringBuffer();
            String line = "";
            while (true) {
                try {
                    if ((line = r.readLine()) == null) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                content.append(line);
            }
            result = content.toString();
        }
        return result.replace(" ", "");
    }
}
