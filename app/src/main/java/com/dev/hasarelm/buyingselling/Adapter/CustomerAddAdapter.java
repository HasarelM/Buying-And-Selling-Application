package com.dev.hasarelm.buyingselling.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.hasarelm.buyingselling.Model.advertisements;
import com.dev.hasarelm.buyingselling.R;
import com.dev.hasarelm.buyingselling.interfaces.addViewListner;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomerAddAdapter extends RecyclerView.Adapter<CustomerAddAdapter.CustomerViewHolder> {

    private ArrayList<advertisements> mAdvertisementsArrayList;
    private Activity activity;
    private addViewListner<advertisements> mAddViewListner;

    public CustomerAddAdapter(Activity activity,ArrayList<advertisements> advertisements,addViewListner<advertisements>addViewListner){

        this.mAdvertisementsArrayList = advertisements;
        this.activity = activity;
        this.mAddViewListner = addViewListner;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.seller_add_list, parent, false);
        return new CustomerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {

        try {
            advertisements ad = mAdvertisementsArrayList.get(position);

            holder.mTvTitle.setText(ad.getTitle().toString().trim());
            holder.mTvDate.setText(ad.getDate().toString().trim());
            holder.mTvFromTime.setText(ad.getFrom_time().toString().trim());
            holder.mTvToTime.setText(ad.getTo_time().toString().trim());

            try {
                Picasso.get().load(ad.getSub_description().toString().trim())
                        .error(R.drawable.ic_baseline_account_balance_24)
                        .into(holder.mIvLoad);

            }catch (Exception ww)
            {
            }

        }catch (Exception g){}

    }

    @Override
    public int getItemCount() {
        return mAdvertisementsArrayList.size();
    }

    public class CustomerViewHolder extends RecyclerView.ViewHolder {

        TextView mTvTitle,mTvFromTime,mTvToTime,mTvDate;
        ImageView mIvLoad;
        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);

            mTvTitle = itemView.findViewById(R.id.add_title);
            mTvDate = itemView.findViewById(R.id.add_date);
            mTvFromTime = itemView.findViewById(R.id.add_to_from_time);
            mTvToTime = itemView.findViewById(R.id.add_to_time);
            mIvLoad = itemView.findViewById(R.id.seller_add_list_iv_imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final int position = CustomerViewHolder.this.getAdapterPosition();
                    mAddViewListner.addViewClick(position,mAdvertisementsArrayList.get(position));
                    String ff;
                }
            });

        }
    }
}
