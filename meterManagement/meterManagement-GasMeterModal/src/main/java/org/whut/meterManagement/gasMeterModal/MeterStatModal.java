package org.whut.meterManagement.gasMeterModal;

import java.util.Date;

/**
 * Created by chenfu on 2016/12/27.
 */
/// <summary>
/// 表具状态信息
/// </summary>
public class MeterStatModal {
    /// <summary>
    /// 表具读数
    /// </summary>
    private int MeterRead;

    /// <summary>
    /// 剩余金额
    /// </summary>
    private double Money;

    /// <summary>
    /// 表具时间
    /// </summary>
    private Date MeterTime;

    /// <summary>
    /// 当前周期起始读数
    /// </summary>
    private int CycleBeginRead;

    public int getMeterRead() {
        return MeterRead;
    }

    public void setMeterRead(int meterRead) {
        MeterRead = meterRead;
    }

    public double getMoney() {
        return Money;
    }

    public void setMoney(double money) {
        Money = money;
    }

    public int getCycleBeginRead() {
        return CycleBeginRead;
    }

    public void setCycleBeginRead(int cycleBeginRead) {
        CycleBeginRead = cycleBeginRead;
    }

    public Date getMeterTime() {
        return MeterTime;
    }

    public void setMeterTime(Date meterTime) {
        MeterTime = meterTime;
    }
}
