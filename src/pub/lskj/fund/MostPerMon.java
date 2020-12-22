package pub.lskj.fund;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * 一个月里面如何操作基金达到最高收益
 * 创建时间 2020/12/22 23:12
 *
 * @author dragon
 * @version 1.0
 */
public class MostPerMon {
    /**
     * 每次买入最小资本100元
     */
    private final int PLUS_MIN = 100;

    /**
     * 资本的增加数据
     */
    private List<Plus> plusData = new LinkedList<>();

    /**
     * 追加资本
     */
    static class Plus {
        /**
         * 当天的基金数据
         */
        FundData fundData;
        /**
         * 投入的资本
         */
        int money;
    }

    /**
     * 本金
     */
    private int totalCapital;
    /**
     * 目标收益百分比（单位%）
     */
    private float targetPercentage;

    /**
     * 基金的交易日数据
     */
    static class FundData {
        /**
         * 交易日
         */
        int index = 0;
        /**
         * 当天净值
         */
        float value = 1F;
        /**
         * 涨跌幅，跌为负、涨为正
         */
        float rate = 0F;
    }

    /**
     * 当月的基金数据
     */
    private List<FundData> monthFundData = new LinkedList<>();

    public MostPerMon(int totalCapital, float targetPercentage) {
        this.totalCapital = totalCapital;
        this.targetPercentage = targetPercentage;
    }

    public MostPerMon(int totalCapital, float targetPercentage, List<FundData> monthFundData) {
        this(totalCapital, targetPercentage);
        setMonthFundData(monthFundData);
    }

    /**
     * 设置当月的基金数据
     *
     * @param monthFundData 当月的基金数据
     */
    public void setMonthFundData(List<FundData> monthFundData) {
        this.monthFundData = monthFundData;
    }

    /**
     * 打印最佳投资策略
     *
     * @param plusMaxTimes 资本最多分多少次追加
     */
    public void print(int plusMaxTimes) {
        calculate(plusMaxTimes);
    }

    /**
     * 计算策略
     *
     * @param plusMaxTimes 资本最多分多少次追加
     */
    private void calculate(int plusMaxTimes) {
        List<FundData> fundDataToPlus = findTopNegativeFundData(plusMaxTimes);
    }

    /**
     * 找出跌幅最高的几个交易日
     *
     * @return 跌幅最高的几个交易日
     */
    private List<FundData> findTopNegativeFundData(int top) {
        List<FundData> topNegatives = new LinkedList<>();
        List<FundData> monthFundData = this.monthFundData;
        for (int i = 0; i < top; i++) {
            float maxNegativeRate = 0;
            FundData find = null;
            for (FundData data : monthFundData) {
                // 选择跌幅最高的一个交易日
                if (data.rate < 0 && maxNegativeRate > data.rate) {
                    find = data;
                    maxNegativeRate = data.rate;
                }
            }
            if (find != null) {
                // 找到一个就要从总的交易日数据中去除该交易日数据
                monthFundData.remove(find);
                // 添加该交易日
                topNegatives.add(find);
            }
        }
        // 根据基金的交易日升序排序
        topNegatives.sort(new Comparator<FundData>() {
            @Override
            public int compare(FundData o1, FundData o2) {
                return o1.index - o2.index;
            }
        });
        return topNegatives;
    }
}
