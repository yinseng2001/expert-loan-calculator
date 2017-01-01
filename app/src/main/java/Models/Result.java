package Models;

/**
 * Created by yinseng on 1/1/17.
 */

public class Result {
    private double total;
    private double interest;
    private double principle_restore;
    private double interest_restore;
    private double interest_per_month;

    public Result(double total, double interest, double interest_per_month) {
        this.total = total;
        this.interest = interest;
        this.interest_per_month = interest_per_month;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public double getInterest_per_month() {
        return interest_per_month;
    }

    public void setInterest_per_month(double interest_per_month) {
        this.interest_per_month = interest_per_month;
    }

    public double getPrinciple_restore() {
        return principle_restore;
    }

    public void setPrinciple_restore(double principle_restore) {
        this.principle_restore = principle_restore;
    }

    public double getInterest_restore() {
        return interest_restore;
    }

    public void setInterest_restore(double interest_restore) {
        this.interest_restore = interest_restore;
    }
}
