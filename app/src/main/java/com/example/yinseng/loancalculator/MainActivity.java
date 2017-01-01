package com.example.yinseng.loancalculator;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

import Adapters.ListLoanAdapter;
import Adapters.LoanAdapter;
import Interface.OnClickListener;
import Models.Loan;
import Models.Result;
import Models.ResultSeperate;
import Utils.Finance;

public class MainActivity extends AppCompatActivity implements
        TextWatcher,
        OnClickListener,
        View.OnClickListener {
    private RecyclerView mRecyclerView;


    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList loan_list;
    private ArrayList result_seperate_list;


    private EditText et_pv;
    private EditText et_nper;
    private EditText et_rate;
    private EditText et_amount;
    private Button btn_submit;
    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        et_pv = (EditText) findViewById(R.id.et_pv);
        et_nper = (EditText) findViewById(R.id.et_periods);
        et_rate = (EditText) findViewById(R.id.et_rate);

        et_pv.addTextChangedListener(this);
        et_nper.addTextChangedListener(this);
        et_rate.addTextChangedListener(this);


        loan_list = new ArrayList<Object>();
        result_seperate_list = new ArrayList<Object>();

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.listview);


        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ListLoanAdapter(this, loan_list, this);
        mRecyclerView.setAdapter(mAdapter);


//        double pv = 20000;
//        double r = 0.12;
//        int nper = 12 *7;
//
//        calculate(pv,r,nper);


    }


    private void calculate(double pv, double r, int nper) {

        double interest = 0, principle = 0, payment = 0;
        double total = pv;
        double total_interest = 0;
        double total_principle = 0;

        nper = nper * 12;

        loan_list.clear();
        result_seperate_list.clear();

        for (int i = 0; i < nper; i++) {

            int per = i + 1;

            interest = Finance.ipmt(r / 12, per, nper, pv);
//            Log.e("loan", interest + "");
            principle = Finance.ppmt(r / 12, per, nper, pv);
//            Log.e("loan", principle + "");
            payment = Finance.pmt(r / 12, nper, pv);
//            Log.e("loan", payment + "");


            interest = Math.abs(Double.parseDouble(new DecimalFormat("0.00").format(interest)));
            principle =  Math.abs(Double.parseDouble(new DecimalFormat("0.00").format(principle)));
            payment =  Math.abs(Double.parseDouble(new DecimalFormat("0.00").format(payment)));

            total -= principle;
            total_principle  += principle;
            total_interest += interest;

            total_principle = Double.parseDouble(new DecimalFormat("0.00").format(total_principle));


            Log.e("loan","interest="+total_interest);

            loan_list.add(new Loan(per, principle, interest, payment, total_principle));
            result_seperate_list.add(new ResultSeperate(total_principle,total_interest,total));

        }
        double total_principle_interest = payment * nper;

        loan_list.add(new Result(total_principle_interest,  pv-total_principle_interest, ( pv-total_principle_interest) / nper));

        mAdapter.notifyDataSetChanged();
    }

    // nper month format
    private void calculate(double pv, double r, int nper,int cont,Result result) {
        Log.d("loan","calculate");
        double interest = 0, principle = 0, payment = 0;
        double total = pv;
        double total_interest = 0;
        double total_principle = 0;

        for (int i = 0; i < nper; i++) {

            int per = i + 1;

            interest = Finance.ipmt(r / 12, per, nper, pv);
            principle = Finance.ppmt(r / 12, per, nper, pv);
            payment = Finance.pmt(r / 12, nper, pv);

            interest = Math.abs(Double.parseDouble(new DecimalFormat("0.00").format(interest)));
            principle =  Math.abs(Double.parseDouble(new DecimalFormat("0.00").format(principle)));
            payment =  Math.abs(Double.parseDouble(new DecimalFormat("0.00").format(payment)));

            total -= principle;
            total_principle  += principle;
            total_interest += interest;

            total_principle = Double.parseDouble(new DecimalFormat("0.00").format(total_principle));

//            Log.e("loan","interest="+total_interest);

            loan_list.add(new Loan(per+cont, principle, interest, payment, total_principle));

        }
        loan_list.add(result);

        double total_principle_interest = payment *nper ;

        loan_list.add(new Result(total_principle_interest + result.getPrinciple_restore() + result.getInterest_restore(),  total_interest + result.getInterest_restore(),  ( total_interest+result.getInterest_restore()) / (nper+cont)));

        Log.d("loan",result.getPrinciple_restore() +"-"+ result.getInterest());
    }


    private boolean validate() {
        boolean b = false;

        if (!et_pv.getText().toString().equals("") && !et_nper.getText().toString().equals("") && !et_rate.getText().toString().equals("")) {
            b = true;
        }

        return b;
    }


    private void removeListItemFrom(int from,int end){
        loan_list.subList(from,end).clear();
    }


    @Override
    public void OnClick(Object object) {
        Loan loan = (Loan) object;
        openDialog(loan);


        Log.e("laon", loan.getTotal() + "");
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.btn_submit){
            double amount = Double.parseDouble(et_amount.getText().toString()) ;
//            Log.e("loan",et_amount.getText().toString());
            Loan loan = (Loan) view.getTag();
            ResultSeperate resultSeperate = (ResultSeperate)result_seperate_list.get(loan.getMonth_no()-1);
            resultSeperate.setAmount(amount);
            loan_list.add(loan.getMonth_no(),resultSeperate);
            Result result = (Result)loan_list.get(loan_list.size()-1);
            result.setPrinciple_restore(resultSeperate.getPrinciple_paid());
            result.setInterest_restore(resultSeperate.getInterest_paid());
            // remove /reset new list
            removeListItemFrom(loan.getMonth_no()+1,loan_list.size());

            // calculate continue
            double pv = Double.parseDouble(et_pv.getText().toString());
            double r = Double.parseDouble(et_rate.getText().toString()); // per year
            int nper = Integer.parseInt(et_nper.getText().toString());   // per year

            calculate(resultSeperate.getRemain_present_value() - amount, r / 100, nper *12 -loan.getMonth_no(),loan.getMonth_no(),result);

            mAdapter.notifyDataSetChanged();
            dialog.dismiss();

            Log.e("loan",resultSeperate.getInterest_paid() + "");

        }

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (validate()) {
            double pv = Double.parseDouble(et_pv.getText().toString());
            double r = Double.parseDouble(et_rate.getText().toString()); // per year
            int nper = Integer.parseInt(et_nper.getText().toString());   // per year

            calculate(pv, r / 100, nper);
        }


        Log.e("laon", charSequence.toString() + validate());
    }

    private void openDialog(Loan loan) {
        dialog = new Dialog(this, R.style.mydialogstyle);
        dialog.setContentView(R.layout.dialog_add_principle);
//        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        et_amount = (EditText) dialog.findViewById(R.id.et_amount);
        btn_submit = (Button) dialog.findViewById(R.id.btn_submit);
        btn_submit.setTag(loan);

        btn_submit.setOnClickListener(this);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
