package com.dev.hasarelm.buyingselling.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.hasarelm.buyingselling.Model.orderCreate;
import com.dev.hasarelm.buyingselling.Model.orders;
import com.dev.hasarelm.buyingselling.R;
import com.dev.hasarelm.buyingselling.interfaces.orderItemClickListner;

import java.util.ArrayList;

public class CustomerOrderViewAdapter extends RecyclerView.Adapter<CustomerOrderViewAdapter.OrderViewHolder>{

    private ArrayList<orders> mOrderCreates;
    private Activity activity;
    private orderItemClickListner mOrderItemClickListner;

    public CustomerOrderViewAdapter(Activity activity,ArrayList<orders> orderCreates/*,orderItemClickListner<orderCreate> orderItemClickListner*/){

        this.mOrderCreates = orderCreates;
        this.activity = activity;
       /* this.mOrderItemClickListner = orderItemClickListner;*/
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_list_layout, parent, false);
        return new OrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {

        orders ord = mOrderCreates.get(position);
        holder.mTvRefNo.setText("CO/RDF/0000"+ord.getId()+"");
        holder.mTvOrderDetails.setText(ord.getDescription_3());
        holder.mTvDate.setText("2021-03-20");

        if (ord.getStatus().equals("1")){

            holder.mTvStatus.setText("Pending");
        }else {

            holder.mTvStatus.setText("Complete");
        }
    }

    @Override
    public int getItemCount() {
        return mOrderCreates.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView mTvRefNo,mTvOrderDetails,mTvDate,mTvStatus;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            mTvRefNo = itemView.findViewById(R.id.order_list_layout_refNo);
            mTvDate = itemView.findViewById(R.id.order_list_layout_order_date);
            mTvOrderDetails = itemView.findViewById(R.id.order_list_layout_order_details);
            mTvStatus = itemView.findViewById(R.id.order_list_layout_order_status);

           /* itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final int position = OrderViewHolder.this.getAdapterPosition();
                    mOrderItemClickListner.orderItemClick(position, mOrderCreates.get(position));
                }
            });*/
        }
    }
}
