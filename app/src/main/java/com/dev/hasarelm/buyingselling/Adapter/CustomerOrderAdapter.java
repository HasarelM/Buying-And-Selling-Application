package com.dev.hasarelm.buyingselling.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.hasarelm.buyingselling.Model.orders;
import com.dev.hasarelm.buyingselling.R;
import com.dev.hasarelm.buyingselling.interfaces.customerOrderClickListner;

import java.util.ArrayList;

public class CustomerOrderAdapter extends RecyclerView.Adapter<CustomerOrderAdapter.CustomerOrderViewHolder>{

    private ArrayList<orders> mOrdersArrayList;
    private Activity activity;
    private customerOrderClickListner orderClickListner;

    public CustomerOrderAdapter(Activity activity,ArrayList<orders> orders,customerOrderClickListner<orders> orderClickListner){
        this.activity  =activity;
        this.mOrdersArrayList = orders;
        this.orderClickListner = orderClickListner;
    }

    @NonNull
    @Override
    public CustomerOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_order_list, parent, false);
        return new CustomerOrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerOrderViewHolder holder, int position) {

        orders or = mOrdersArrayList.get(position);
        holder.mTvRefNo.setText("CSO/REF/00000"+or.getId());
        holder.mTvCustomerName.setText(or.getDescription_1());
        holder.mTvMobileNo.setText(or.getDescription_3());
        holder.mTvDate.setText(or.getDescription_2());
    }

    @Override
    public int getItemCount() {
        return mOrdersArrayList.size();
    }

    public class CustomerOrderViewHolder extends RecyclerView.ViewHolder {

        TextView mTvDate,mTvMobileNo,mTvCustomerName,mTvRefNo;

        public CustomerOrderViewHolder(@NonNull View itemView) {
            super(itemView);

            mTvRefNo = itemView.findViewById(R.id.customer_order_layout_refNo);
            mTvCustomerName = itemView.findViewById(R.id.customer_order_layout_customer_name);
            mTvDate = itemView.findViewById(R.id.customer_order_layout_date);
            mTvMobileNo = itemView.findViewById(R.id.customer_order_layout_mobile_no);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final int position = CustomerOrderViewHolder.this.getAdapterPosition();

                    orderClickListner.customerOrderClick(position,mOrdersArrayList.get(position));
                }
            });

        }
    }
}
