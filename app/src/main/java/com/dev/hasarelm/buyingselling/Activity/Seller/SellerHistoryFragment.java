package com.dev.hasarelm.buyingselling.Activity.Seller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.hasarelm.buyingselling.Adapter.SellerOrderHistoryAdapter;
import com.dev.hasarelm.buyingselling.Common.Endpoints;
import com.dev.hasarelm.buyingselling.Common.RetrofitClient;
import com.dev.hasarelm.buyingselling.Common.SharedPreferencesClass;
import com.dev.hasarelm.buyingselling.Model.OrderList;
import com.dev.hasarelm.buyingselling.Model.orders;
import com.dev.hasarelm.buyingselling.R;
import com.dev.hasarelm.buyingselling.interfaces.customerOrderClickListner;
import com.dev.hasarelm.buyingselling.interfaces.orderHistoryClickLisnter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dev.hasarelm.buyingselling.Common.BaseUrl.VLF_BASE_URL;

public class SellerHistoryFragment extends Fragment implements orderHistoryClickLisnter<orders> {

    private SellerOrderHistoryAdapter mOrderHistoryAdapter;
    private ArrayList<orders> mOrders;
    private OrderList mOrderList;
    private RecyclerView mRvHistoryList;
    public static SharedPreferences localSp;
    private String userID;
    private int seller_id;
    private Activity activity;
    View rootView;

    public SellerHistoryFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.seller_history_fragment, container, false);

        try {

            localSp = getContext().getSharedPreferences(SharedPreferencesClass.SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);
            userID = localSp.getString("User_ID", "");
            seller_id = Integer.parseInt(userID);

        } catch (Exception g) {
        }

        mRvHistoryList = rootView.findViewById(R.id.seller_order_history_fragment_rv_list);

        getSellerOrderHistoryList(seller_id);
        return rootView;
    }

    private void getSellerOrderHistoryList(int seller_id) {
        final ProgressDialog myPd_ring=ProgressDialog.show(getContext(), "Please wait", "", true);
        Endpoints endpoints = RetrofitClient.getLoginClient().create(Endpoints.class);
        Call<OrderList> call = endpoints.getOrderHistory(VLF_BASE_URL+"orders?seller_id=",seller_id,3);
        call.enqueue(new Callback<OrderList>() {
            @Override
            public void onResponse(Call<OrderList> call, Response<OrderList> response) {

                if (response.code()==200){

                    myPd_ring.dismiss();

                    mOrderList = response.body();
                    mOrders = mOrderList.getOrders();

                    setupRecyclerView(mOrders);
                }
            }

            @Override
            public void onFailure(Call<OrderList> call, Throwable t) {

            }
        });
    }

    private void setupRecyclerView(ArrayList<orders> mOrders) {

        mOrderHistoryAdapter = new SellerOrderHistoryAdapter(mOrders,activity, this);
        mRvHistoryList.setHasFixedSize(true);
        mRvHistoryList.setLayoutManager(new LinearLayoutManager(activity));
        mRvHistoryList.setAdapter(mOrderHistoryAdapter);
    }

    @Override
    public void orderHistoryClick(int position, orders data) {

    }
}
