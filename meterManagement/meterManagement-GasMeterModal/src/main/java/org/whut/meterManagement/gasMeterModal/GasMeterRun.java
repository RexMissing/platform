package org.whut.meterManagement.gasMeterModal;


import org.whut.meterManagement.date.DateUtil;

import java.util.Date;

/**
 * Created by chenfu on 2016/12/27.
 */
/// <summary>
/// 模拟表具计量计算类
/// </summary>
public class GasMeterRun {

    /// <summary>
    /// 模拟表具正常计费过程
    /// </summary>
    /// <param name="oldMS">上一次表具正常状态数据</param>
    /// <param name="PRI">表具计费单价策略</param>
    /// <param name="meterread">当前表具读数</param>
    /// <param name="curTotal">当前周期量</param>
    /// <param name="preTotal">上个周期量</param>
    /// <param name="UpMoney">金额变更值</param>
    /// <param name="MeterUseRecord">表具计费情况返回</param>
    /// <returns>最新表具正常状态</returns>
    public static MeterStatModal standardRun(MeterStatModal oldMS, PriceModal PRI, int meterread, int curTotal, int preTotal, double upMoney, Date receiveTime, DynamicRecord meterUseRecord) {
        MeterStatModal newMS = new MeterStatModal();
        //meterUseRecord = new DynamicRecord();

        //根据阶梯周期长度及周期起始日期，计算最近的一个阶梯周期起始日
        Date curStartDate = PRI.getStartDate();
        Date nexStartDate = DateUtil.changeMonth(PRI.getStartDate(), PRI.getCycleLen());
        while (nexStartDate.getTime() < receiveTime.getTime()) {
            curStartDate = nexStartDate;
            nexStartDate = DateUtil.changeMonth(curStartDate, PRI.getCycleLen());
        }

        int curBeginRead = meterread - curTotal;                //当前周期起码
        double userMoney = 0.00d;                              //表具将要扣去的金额
        if (curBeginRead > oldMS.getMeterRead())                       //最后一次抄表在本周期前
        {
            int preBeginRead = meterread - curTotal - preTotal; //上周期起码
            if (preBeginRead > oldMS.getMeterRead())                   //最后一次抄表在上周期前
            {
                return null;
            } else                                                //最后一次抄表在上周期内
            {
                //模拟表具逐方计量费用扣减(上周期)
                for (int i = oldMS.getMeterRead() + 1; i <= curBeginRead; i++) {
                    double p = PRI.getPrice(i - preBeginRead);
                    userMoney += p;
                    meterUseRecord.record(i, p);
                }
                meterUseRecord.stage(curBeginRead, oldMS.getMoney() - userMoney, curStartDate);
                //模拟表具逐方计量费用扣减(本周期)
                for (int i = curBeginRead + 1; i <= meterread; i++) {
                    double p = PRI.getPrice(i - curBeginRead);
                    userMoney += p;
                    meterUseRecord.record(i, p);
                }
            }
        } else                                                    //最后一次抄表在本周期内
        {
            //模拟表具逐方计量费用扣减
            for (int i = oldMS.getMeterRead() + 1; i <= meterread; i++) {
                double p = PRI.getPrice(i - curBeginRead);
                userMoney += p;
                meterUseRecord.record(i, p);
            }
        }
        //新的表具状态
        newMS.setCycleBeginRead(curBeginRead);
        newMS.setMeterRead(meterread);
        newMS.setMoney(oldMS.getMoney() - userMoney + upMoney);
        return newMS;
    }

    /// <summary>
    /// 模拟表具故障前计费过程
    /// </summary>
    /// <param name="oldMS">上一次表具正常状态数据</param>
    /// <param name="PRI">表具计费单价策略</param>
    /// <param name="meterread">当前表具读数</param>
    /// <param name="meterUseRecord">表具计费情况返回</param>
    /// <returns>最终表具状态</returns>
    public static MeterStatModal maintainRun(MeterStatModal oldMS, PriceModal PRI, int meterread, DynamicRecord meterUseRecord) {
        MeterStatModal meterStatModal = new MeterStatModal();
        //meterUseRecord = new DynamicRecord();
        //根据阶梯周期长度及周期起始日期，计算最近的一个阶梯周期起始日
        Date curStartDate = PRI.getStartDate();
        Date nexStartDate = DateUtil.changeMonth(PRI.getStartDate(), PRI.getCycleLen());
        while (nexStartDate.getTime() < new Date().getTime()) {
            curStartDate = nexStartDate;
            nexStartDate = DateUtil.changeMonth(curStartDate, PRI.getCycleLen());
        }

        double useMoney = 0.00d;
        if (oldMS.getMeterTime().getTime() > curStartDate.getTime())                 //最后一次抄表在当前周期内，表示未跨越周期
        {
            //模拟表具逐方计量费用扣减
            for (int i = oldMS.getMeterRead() + 1; i <= meterread; i++) {
                double p = PRI.getPrice(i - oldMS.getCycleBeginRead());
                useMoney += p;
                meterUseRecord.record(i, p);
            }
        } else                                               //最后一次抄表在当前周期以前，表示已经跨越周期
        {
            //假设从最后一次抄表时间至当前时间，表具均匀计量，以此为依据进行费用计算
            //1.得到oldMS表具状态所处周期起止日期
            Date start = PRI.getStartDate();
            //Date end = start.AddMonths(PRI.cycleLen).AddDays(-1);
            Date end = DateUtil.changeDayOfMonth(DateUtil.changeMonth(start, PRI.getCycleLen()), -1);
            while (true) {
                if (oldMS.getMeterTime().getTime() > start.getTime() && oldMS.getMeterTime().getTime() < end.getTime())
                    break;
//                start = end.AddDays(1);
//                end = start.AddMonths(PRI.cycleLen).AddDays(-1);
                start = DateUtil.changeDayOfMonth(end, 1);
                end = DateUtil.changeDayOfMonth(DateUtil.changeMonth(start, PRI.getCycleLen()), -1);
            }
            //2.根据平均原则，计算oldMS所在周期，表具接下来计量近似值
//            TimeSpan ts1 = end - oldMS.MeterTime.Date;
//            TimeSpan ts2 = Date.Now.Date - oldMS.MeterTime.Date;
//            double per = ts1.TotalDays / ts2.TotalDays;
            long ts1 = end.getTime() - oldMS.getMeterTime().getTime();
            long ts2 = new Date().getTime() - oldMS.getMeterTime().getTime();
            double per = DateUtil.getTotalDays(ts1) / DateUtil.getTotalDays(ts2);
            int bRead = oldMS.getMeterRead();
            int eRead = bRead + (int) ((meterread - oldMS.getMeterRead()) * per);
            //3.模拟表具逐方计量
            for (int i = bRead + 1; i <= eRead; i++) {
                double p = PRI.getPrice(i - oldMS.getCycleBeginRead());
                useMoney += p;
                meterUseRecord.record(i, p);
            }

            //4.进行后续循环计算
            while (true) {
//                start = end.AddDays(1);
//                end = start.AddMonths(PRI.cycleLen).AddDays(-1);
                start = DateUtil.changeDayOfMonth(end, 1);
                end = DateUtil.changeDayOfMonth(DateUtil.changeMonth(start, PRI.getCycleLen()), -1);
                if (end.getTime() < new Date().getTime()) {
                    bRead = eRead;
                    ts1 = end.getTime() - start.getTime();
                    per = DateUtil.getTotalDays(ts1) / DateUtil.getTotalDays(ts2);
                    eRead = bRead + (int) ((meterread - oldMS.getMeterRead()) * per);
                    meterUseRecord.stage(bRead, oldMS.getMoney() - useMoney, start);
                    for (int i = bRead + 1; i <= eRead; i++) {
                        double p = PRI.getPrice(i - bRead);
                        useMoney += p;
                        meterUseRecord.record(i, p);
                    }
                } else {
                    bRead = eRead;
                    eRead = meterread;
                    meterUseRecord.stage(bRead, oldMS.getMoney() - useMoney, start);
                    //模拟
                    for (int i = bRead + 1; i <= eRead; i++) {
                        double p = PRI.getPrice(i - bRead);
                        useMoney += p;
                        meterUseRecord.record(i, p);
                    }
                    break;//循环终止
                }
            }
        }
//        meterStatModal.MeterRead = meterread;
//        meterStatModal.Money = oldMS.Money - useMoney;
        meterStatModal.setMeterRead(meterread);
        meterStatModal.setMoney(oldMS.getMoney() - useMoney);
        return meterStatModal;
    }
}
