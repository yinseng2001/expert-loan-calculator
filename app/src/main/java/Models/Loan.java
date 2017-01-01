package Models;

/**
 * Created by yinseng on 12/29/16.
 */

public class Loan {
    private int month_no;
    private double principle;
    private double interest;
    private double payment;
    private double total;

    public Loan(int month_no, double principle, double interest, double payment, double total) {
        this.month_no = month_no;
        this.principle = principle;
        this.interest = interest;
        this.payment = payment;
        this.total = total;
    }


    public int getMonth_no() {
        return month_no;
    }

    public void setMonth_no(int month_no) {
        this.month_no = month_no;
    }

    public double getPrinciple() {
        return principle;
    }

    public void setPrinciple(double principle) {
        this.principle = principle;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
