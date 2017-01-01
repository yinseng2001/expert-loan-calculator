package Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yinseng.loancalculator.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import Models.Loan;

/**
 * Created by yinseng on 12/29/16.
 */

public class LoanAdapter extends BaseAdapter {
    public ArrayList<Loan> loan_list;
    Activity activity;

    public LoanAdapter(Activity activity, ArrayList<Loan> loan_list) {
        super();
        this.activity = activity;
        this.loan_list = loan_list;
    }

    @Override
    public int getCount() {
        return loan_list.size();
    }

    @Override
    public Object getItem(int position) {
        return loan_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView mMonth;
        TextView mPrinciple;
        TextView mInterest;
        TextView mPayment;
        TextView mTotal;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            Log.e("last","null");
            convertView = inflater.inflate(R.layout.list_row_loan, null);
            holder = new ViewHolder();

            holder.mMonth = (TextView) convertView.findViewById(R.id.tv_month);
            holder.mPrinciple = (TextView) convertView.findViewById(R.id.tv_principle);
            holder.mInterest = (TextView) convertView.findViewById(R.id.tv_interest);
            holder.mPayment = (TextView) convertView.findViewById(R.id.tv_payment);
            holder.mTotal = (TextView) convertView.findViewById(R.id.tv_total);

            convertView.setTag(holder);
        } else {
            Log.e("last","this");
            holder = (ViewHolder) convertView.getTag();
        }

        Loan item = loan_list.get(position);
        holder.mMonth.setText(item.getMonth_no()+"");
        holder.mPrinciple.setText( new DecimalFormat("0.00").format(item.getPrinciple()) );
        holder.mInterest.setText( new DecimalFormat("0.00").format(item.getInterest()) );
        holder.mPayment.setText( new DecimalFormat("0.00").format(item.getPayment()));
        holder.mTotal.setText( new DecimalFormat("0.00").format(item.getTotal()) );

        Log.e("last",item.getMonth_no()+"");

        return convertView;
    }








}
