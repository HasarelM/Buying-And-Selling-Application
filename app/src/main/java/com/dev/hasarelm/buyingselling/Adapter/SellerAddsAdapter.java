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
import com.dev.hasarelm.buyingselling.interfaces.addDeleteListner;
import com.dev.hasarelm.buyingselling.interfaces.addLongClickListner;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class SellerAddsAdapter extends RecyclerView.Adapter<SellerAddsAdapter.SellerViewHolder>{

    private ArrayList<advertisements> mAdvertisementsArrayList;
    private Activity activity;
    private addDeleteListner <advertisements> addDeleteListner;
    private addLongClickListner <advertisements> addLongClickListner;

    public SellerAddsAdapter(Activity activity,ArrayList<advertisements> advertisements,addDeleteListner<advertisements> addDeleteListner,
                             addLongClickListner<advertisements> addLongClickListner){

        this.mAdvertisementsArrayList = advertisements;
        this.activity = activity;
        this.addDeleteListner = addDeleteListner;
        this.addLongClickListner = addLongClickListner;
    }

    @NonNull
    @Override
    public SellerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.seller_add, parent, false);
        return new SellerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SellerViewHolder holder, int position) {

        try {
            advertisements ad = mAdvertisementsArrayList.get(position);

            holder.mTvCategory.setText(ad.getCategory().toString().trim());
            holder.mTvTitle.setText(ad.getTitle().toString().trim());


            StringTokenizer tokens = new StringTokenizer(ad.getCreated_at(), "T");
            String first = tokens.nextToken();
            String second = tokens.nextToken();
            StringTokenizer token = new StringTokenizer(second, "T");
            String third = token.nextToken();
            StringTokenizer toke = new StringTokenizer(third, ".");
            String val = toke.nextToken();

            holder.mTvDate.setText(first);

            try {
                Picasso.get().load(ad.getSub_description().toString().trim())
                        .error(R.drawable.ic_baseline_account_balance_24)
                        .into(holder.mIvImage);

            }catch (Exception ww)
            {
            }

        }catch (Exception g){}
    }

    @Override
    public int getItemCount() {
        return mAdvertisementsArrayList.size();
    }

    public class SellerViewHolder extends RecyclerView.ViewHolder {

        TextView mTvTitle,mTvDate,mTvCategory;
        ImageView mIvImage;

        public SellerViewHolder(@NonNull View itemView) {
            super(itemView);

            mTvCategory = itemView.findViewById(R.id.seller_add_category);
            mTvTitle = itemView.findViewById(R.id.seller_add_title);
            mTvDate = itemView.findViewById(R.id.seller_add_to_date);
            mIvImage = itemView.findViewById(R.id.seller_iv_imageview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final int position = SellerViewHolder.this.getAdapterPosition();

                    addDeleteListner.addDeleteClick(position, mAdvertisementsArrayList.get(position));
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    final int position = SellerViewHolder.this.getAdapterPosition();

                    addLongClickListner.addLongClick(position,mAdvertisementsArrayList.get(position));

                    return true;
                }
            });
        }
    }
}
