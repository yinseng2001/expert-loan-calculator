package Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yinseng.loancalculator.R;

import java.text.DecimalFormat;
import java.util.List;

import Interface.OnClickListener;
import Models.Loan;
import Models.Result;
import Models.ResultSeperate;

/**
 * Created by yinseng on 1/1/17.
 */


public class ListLoanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_TOTAL = 1;
    private final int VIEW_TYPE_Seperate = 2;

    private OnClickListener onClickListener;
    private List<Object> mDataset;
    private Context mContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolderItem extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView mMonth;
        TextView mPrinciple;
        TextView mInterest;
        TextView mPayment;
        TextView mTotal;


        public ViewHolderItem(View itemView) {
            super(itemView);

            mMonth = (TextView) itemView.findViewById(R.id.tv_month);
            mPrinciple = (TextView) itemView.findViewById(R.id.tv_principle);
            mInterest = (TextView) itemView.findViewById(R.id.tv_interest);
            mPayment = (TextView) itemView.findViewById(R.id.tv_payment);
            mTotal = (TextView) itemView.findViewById(R.id.tv_total);
        }

        public void bind(final Loan item,final OnClickListener listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnClick(item);
                }
            });
        }
    }

    public static class ViewHolderResult extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView mTotal;
        TextView mInterest;
        TextView mInterest_per_month;


        public ViewHolderResult(View itemView) {
            super(itemView);

            mTotal = (TextView) itemView.findViewById(R.id.tv_total);
            mInterest = (TextView) itemView.findViewById(R.id.tv_interest);
            mInterest_per_month = (TextView) itemView.findViewById(R.id.tv_interest_per_month);

        }
    }

    public static class ViewHolderResultSeperate extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView mPrinciplePaid;
        TextView mInterestPaid;
        TextView mPresentValueRemain;
        TextView mPresentValue;


        public ViewHolderResultSeperate(View itemView) {
            super(itemView);

            mPrinciplePaid = (TextView) itemView.findViewById(R.id.tv_principle_paid);
            mInterestPaid = (TextView) itemView.findViewById(R.id.tv_interest_paid);
            mPresentValueRemain = (TextView) itemView.findViewById(R.id.tv_remain_present_value);
            mPresentValue =(TextView) itemView.findViewById(R.id.tv_present_value);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ListLoanAdapter( Context context,List<Object> myDataset,OnClickListener listener) {
        mDataset = myDataset;
        mContext = context;
        onClickListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return mDataset.get(position) instanceof Loan ? VIEW_TYPE_ITEM : mDataset.get(position) instanceof Result?VIEW_TYPE_TOTAL :VIEW_TYPE_Seperate;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_loan, parent, false);
            return new ViewHolderItem(view);
        } else if (viewType == VIEW_TYPE_TOTAL) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_loan_result, parent, false);
            return new ViewHolderResult(view);
        }else if(viewType == VIEW_TYPE_Seperate){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_loan_result_seperate, parent, false);
            return new ViewHolderResultSeperate(view);
        }

        return null;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(mDataset.get(position) instanceof Loan){
            Loan item =(Loan) mDataset.get(position);
            ViewHolderItem holder_item = (ViewHolderItem ) holder;
            holder_item.mMonth.setText(item.getMonth_no()+"");
            holder_item.mPrinciple.setText( new DecimalFormat("0.00").format(item.getPrinciple()) );
            holder_item.mInterest.setText( new DecimalFormat("0.00").format(item.getInterest()) );
            holder_item.mPayment.setText( new DecimalFormat("0.00").format(item.getPayment()));
            holder_item.mTotal.setText( new DecimalFormat("0.00").format(item.getTotal()) );

            holder_item.bind(item, onClickListener);

        }else if(mDataset.get(position) instanceof Result){
            Result item = (Result) mDataset.get(position);
            ViewHolderResult holder_item = (ViewHolderResult ) holder;
            holder_item.mTotal.setText(new DecimalFormat("0.00").format(item.getTotal()));
            holder_item.mInterest.setText( new DecimalFormat("0.00").format(item.getInterest()) );
            holder_item.mInterest_per_month.setText( new DecimalFormat("0.00").format(item.getInterest_per_month()) );

        }else if(mDataset.get(position) instanceof ResultSeperate){
            ResultSeperate item = (ResultSeperate) mDataset.get(position);
            ViewHolderResultSeperate holder_item = (ViewHolderResultSeperate ) holder;
            holder_item.mPrinciplePaid.setText(new DecimalFormat("0.00").format(item.getPrinciple_paid()));
            holder_item.mInterestPaid.setText( new DecimalFormat("0.00").format(item.getInterest_paid()) );
            holder_item.mPresentValueRemain.setText( new DecimalFormat("0.00").format(item.getRemain_present_value()) );
            holder_item.mPresentValue.setText(item.getPrentValueCalculation());

        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}

