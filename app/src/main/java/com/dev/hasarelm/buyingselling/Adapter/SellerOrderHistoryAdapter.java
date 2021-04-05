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
import com.dev.hasarelm.buyingselling.interfaces.orderHistoryClickLisnter;

import java.util.ArrayList;

public class SellerOrderHistoryAdapter extends RecyclerView.Adapter<SellerOrderHistoryAdapter.SellerOrderViewHolder> {

    private ArrayList<orders> mSellerOrderHistory;
    private Activity activity;
    private orderHistoryClickLisnter orderClickListner;

    public SellerOrderHistoryAdapter(ArrayList<orders> order, Activity activity, orderHistoryClickLisnter<orders> clickListner) {

        this.mSellerOrderHistory = order;
        this.activity = activity;
        this.orderClickListner = clickListner;
    }

    @NonNull
    @Override
    public SellerOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.seller_order_history, parent, false);

        return new SellerOrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SellerOrderViewHolder holder, int position) {

        orders or = mSellerOrderHistory.get(position);
        holder.mTvRefNo.setText("RF/CFE/00000"+or.getId()+"");
        holder.mTvCusName.setText(or.getDescription_1());
        holder.mTvContact.setText(or.getDescription_2());
        holder.mTvDate.setText(or.getDescription_3());

        if (or.getStatus().equals("3")){

            holder.mTvStatus.setText("Complete");
        }
    }

    @Override
    public int getItemCount() {
        return mSellerOrderHistory.size();
    }

    public class SellerOrderViewHolder extends RecyclerView.ViewHolder {

        TextView mTvCusName,mTvContact,mTvRefNo,mTvDate,mTvStatus;
        public SellerOrderViewHolder(@NonNull View itemView) {
            super(itemView);

            mTvCusName = itemView.findViewById(R.id.order_history_list_layout_customer_name);
            mTvContact = itemView.findViewById(R.id.order_history_list_layout_contact_no);
            mTvRefNo = itemView.findViewById(R.id.order_history_list_layout_refNo);
            mTvDate = itemView.findViewById(R.id.order_history_list_layout_order_date);
            mTvStatus = itemView.findViewById(R.id.order_history_list_layout_order_status);
        }
    }
}
