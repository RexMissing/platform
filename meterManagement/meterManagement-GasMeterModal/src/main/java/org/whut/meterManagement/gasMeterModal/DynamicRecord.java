package org.whut.meterManagement.gasMeterModal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chenfu on 2016/12/27.
 */
/// <summary>
/// 表具动态数据
/// </summary>
public class DynamicRecord {

    public class STEP {
        private int bRead;
        private int eRead;
        private double price;

        public int getbRead() {
            return bRead;
        }

        public void setbRead(int bRead) {
            this.bRead = bRead;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int geteRead() {
            return eRead;
        }

        public void seteRead(int eRead) {
            this.eRead = eRead;
        }

        public STEP(int bRead, int eRead, double price) {
            this.bRead = bRead;
            this.eRead = eRead;
            this.price = price;
        }

    }

    public class StageBegin {
        private int bRead;
        private double bMoney;
        private String bDate;

        public int getbRead() {
            return bRead;
        }

        public void setbRead(int bRead) {
            this.bRead = bRead;
        }

        public double getbMoney() {
            return bMoney;
        }

        public void setbMoney(double bMoney) {
            this.bMoney = bMoney;
        }

        public String getbDate() {
            return bDate;
        }

        public void setbDate(String bDate) {
            this.bDate = bDate;
        }

        public StageBegin(int bRead, double bMoney, String bDate) {
            this.bRead = bRead;
            this.bMoney = bMoney;
            this.bDate = bDate;
        }
    }

    double _price;
    int _index;
    private List<STEP> useStep;
    private List<StageBegin> stageStep;

    public List<StageBegin> getStageStep() {
        return stageStep;
    }

    public void setStageStep(List<StageBegin> stageStep) {
        this.stageStep = stageStep;
    }

    public List<STEP> getUseStep() {
        return useStep;
    }

    public void setUseStep(List<STEP> useStep) {
        this.useStep = useStep;
    }

    public DynamicRecord() {
        _price = 0.00d;
        useStep = new ArrayList<STEP>();
        stageStep = new ArrayList<StageBegin>();
    }

    public void record(int R, double P) {
        if (P != _price) {
            useStep.add(new STEP(R, R, P));
            _price = P;
            _index = useStep.size() - 1;
        } else {
            useStep.get(_index).seteRead(R);
        }
    }

    public void stage(int R, double M, Date D) {
        // TODO 第三个参数 D.toString() 有待修改
        //StageStep.add(new StageBegin(R, M, D.toString()));
    }
}
