package com.ming.service;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.ming.client.IndexDataClient;
import com.ming.pojo.IndexData;
import com.ming.pojo.Profit;
import com.ming.pojo.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BackTestService {
    @Autowired
    IndexDataClient indexDataClient;

    public List<IndexData> listIndexData(String code){
        List<IndexData> result = indexDataClient.getIndexData(code);
        // 降序 排序
        Collections.reverse(result);
        return result;
    }


    /**
     * @param ma 均线
     * @param sellRate 设置的卖出点
     * @param buyRate 设置的买入点
     * @param serviceCharge
     * @param indexDatas
     * @return
     */
    public Map<String,Object> simulate(int ma, float sellRate, float buyRate, float serviceCharge, List<IndexData> indexDatas)  {


        // 交易数组
        List<Trade> trades = new ArrayList<>();


        // 利润数组
        List<Profit> profits =new ArrayList<>();
        // 初始现金
        float initCash = 1000;
        // 现金
        float cash = initCash;
        // 份额
        float share = 0;
        // 价值
        float value = 0;


        int winCount = 0;
        float totalWinRate = 0;
        float avgWinRate = 0;
        float totalLossRate = 0;
        int lossCount = 0;
        float avgLossRate = 0;


        // 初始值
        float init =0;

        // 获取初始值，让其初始值相同
        if(!indexDatas.isEmpty())
            init = indexDatas.get(0).getClosePoint();

        // 遍历数组
        for (int i = 0; i<indexDatas.size() ; i++) {
            // 取出第一个数据
            IndexData indexData = indexDatas.get(i);    // 550
            // 找到收盘价
            float closePoint = indexData.getClosePoint();   // 604
            // 20 日均线
            float avg = getMA(i,ma,indexDatas); // 581
            // 20 日最大值
            float max = getMax(i,ma,indexDatas);    // 606

            // 当日增长幅度
            float increase_rate = closePoint/avg;   // 604 / 581 = 1.2
            // 当日亏的幅度
            float decrease_rate = closePoint/max;   // 500 / 450 = 1.11

            // 如果当前没有数据
            if(avg!=0) {
                //如果购买超过的均线  buyRate =
                if(increase_rate > buyRate  ) {
                    //如果此时还没有买 如果份额等于0
                    if(0 == share) {
                        // 当前的现金，除 当前的收盘价 得到当前购买的份额
                        share = cash / closePoint;
                        // 买完以后，现金为0
                        cash = 0;

                        Trade trade = new Trade();
                        trade.setBuyDate(indexData.getDate());
                        trade.setBuyClosePoint(indexData.getClosePoint());
                        trade.setSellDate("n/a");
                        trade.setSellClosePoint(0);
                        trades.add(trade);
                    }
                }
                // 如果当日亏的幅度，小于卖点，卖出
                else if(decrease_rate<sellRate ) {
                    //如果没卖
                    if(0!= share){
                        cash = closePoint * share * (1-serviceCharge);
                        share = 0;

                        Trade trade =trades.get(trades.size()-1);
                        trade.setSellDate(indexData.getDate());
                        trade.setSellClosePoint(indexData.getClosePoint());

                        float rate = cash / initCash;
                        trade.setRate(rate);

                        if(trade.getSellClosePoint()-trade.getBuyClosePoint()>0) {
                            totalWinRate +=(trade.getSellClosePoint()-trade.getBuyClosePoint())/trade.getBuyClosePoint();
                            winCount++;
                        }

                        else {
                            totalLossRate +=(trade.getSellClosePoint()-trade.getBuyClosePoint())/trade.getBuyClosePoint();
                            lossCount ++;
                        }
                    }
                }
                //do nothing
                else{

                }
            }

            // 那么份额为 0
            if(share!=0) {
                value = closePoint * share;
            }
            // 在份额为 0 的时候，现金和价值相等
            else {
                value = cash;
            }
            // 收益率
            float rate = value/initCash;

            Profit profit = new Profit();
            profit.setDate(indexData.getDate());
            profit.setValue(rate*init);

            System.out.println("profit.value:" + profit.getValue());
            // 放入集合里
            profits.add(profit);
        }

        Map<String,Object> map = new HashMap<>();
        map.put("profits", profits);
        map.put("trades", trades);

        map.put("winCount", winCount);
        map.put("lossCount", lossCount);
        map.put("avgWinRate", avgWinRate);
        map.put("avgLossRate", avgLossRate);

        return map;
    }

    private static float getMax(int i, int day, List<IndexData> list) {
        int start = i-1-day;
        if(start<0)
            start = 0;
        int now = i-1;

        if(start<0)
            return 0;

        float max = 0;
        for (int j = start; j < now; j++) {
            IndexData bean =list.get(j);
            if(bean.getClosePoint()>max) {
                max = bean.getClosePoint();
            }
        }
        return max;
    }

    private static float getMA(int i, int ma, List<IndexData> list) {
        int start = i-1-ma;
        int now = i-1;

        if(start<0)
            return 0;

        float sum = 0;
        float avg = 0;
        for (int j = start; j < now; j++) {
            IndexData bean =list.get(j);
            sum += bean.getClosePoint();
        }
        avg = sum / (now - start);
        return avg;
    }


    /**
     * 获取当前多少年
     * @param allIndexDatas
     * @return
     */
    public float getYear(List<IndexData> allIndexDatas) {
        float years;
        String sDateStart = allIndexDatas.get(0).getDate();
        String sDateEnd = allIndexDatas.get(allIndexDatas.size()-1).getDate();

        Date dateStart = DateUtil.parse(sDateStart);
        Date dateEnd = DateUtil.parse(sDateEnd);

        long days = DateUtil.between(dateStart, dateEnd, DateUnit.DAY);
        years = days/365f;
        return years;
    }
}
