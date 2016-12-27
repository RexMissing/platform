package org.whut.meterManagement.gasMeterModal;

import java.sql.Timestamp;

/**
 * Created by chenfu on 2016/12/27.
 */
/// <summary>
/// 单价
/// </summary>
public class PriceModal {
    private int ID;
    private double p0;
    private double p1;
    private double p2;
    private double p3;
    private int a1;
    private int a2;
    private int a3;
    private int cycleLen;
    private Timestamp startDate;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public double getP0() {
        return p0;
    }

    public void setP0(double p0) {
        this.p0 = p0;
    }

    public double getP1() {
        return p1;
    }

    public void setP1(double p1) {
        this.p1 = p1;
    }

    public double getP2() {
        return p2;
    }

    public void setP2(double p2) {
        this.p2 = p2;
    }

    public double getP3() {
        return p3;
    }

    public void setP3(double p3) {
        this.p3 = p3;
    }

    public int getA1() {
        return a1;
    }

    public void setA1(int a1) {
        this.a1 = a1;
    }

    public int getA2() {
        return a2;
    }

    public void setA2(int a2) {
        this.a2 = a2;
    }

    public int getA3() {
        return a3;
    }

    public void setA3(int a3) {
        this.a3 = a3;
    }

    public int getCycleLen() {
        return cycleLen;
    }

    public void setCycleLen(int cycleLen) {
        this.cycleLen = cycleLen;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    /// <summary>
    /// 对单价进行整理，使其符合逻辑
    /// </summary>
    public void sort()
    {
        //首先对3个起始量进行合法性判断
        if (a1 == 0)
        {
            p1 = p0;
        }

        if (a2 == 0)
        {
            p2 = p0;
        }

        if (a3 == 0)
        {
            p3 = p0;
        }

        if (a2 < a1)
        {
            a2 = a1;
        }

        if (a3 < a2)
        {
            a3 = a2;
        }
    }

    public double getPrice(int IRead)
    {
        double RST;
        //需要对阶梯价格进行合理性判读
        if (p1 == 0 && p2 == 0 && p3 == 0)
        {
            return p0;
        }
        if (IRead <= a1)
        {
            RST = p0;
        }
        else
        {
            if (IRead <= a2)
            {
                RST = p1;
            }
            else
            {
                if (IRead <= a3)
                {
                    RST = p2;
                }
                else
                {
                    RST = p3;
                }
            }
        }
        return RST;
    }
}
