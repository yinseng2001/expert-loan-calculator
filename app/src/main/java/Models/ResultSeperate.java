package Models;

import java.text.DecimalFormat;

/**
 * Created by yinseng on 1/1/17.
 */

public class ResultSeperate {
    private double principle_paid;
    private double interest_paid;
    private double remain_present_value;
    private double amount;


    public ResultSeperate(double principle_paid, double interest_paid, double remain_present_value) {
        this.principle_paid = principle_paid;
        this.interest_paid = interest_paid;
        this.remain_present_value = remain_present_value;
    }

    public double getPrinciple_paid() {
        return principle_paid;
    }

    public void setPrinciple_paid(double principle_paid) {
        this.principle_paid = principle_paid;
    }

    public double getInterest_paid() {
        return interest_paid;
    }

    public void setInterest_paid(double interest_paid) {
        this.interest_paid = interest_paid;
    }

    public double getRemain_present_value() {
        return remain_present_value;
    }

    public void setRemain_present_value(double remain_present_value) {
        this.remain_present_value = remain_present_value;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }


    public String getPrentValueCalculation(){
        remain_present_value = Double.parseDouble( new DecimalFormat("0.00").format(remain_present_value));

        return remain_present_value + "-" + amount + "=" + (remain_present_value-amount);
    }
}
