package com.dev.hasarelm.buyingselling.Activity.Customer;

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

import com.dev.hasarelm.buyingselling.Adapter.CustomerAddAdapter;
import com.dev.hasarelm.buyingselling.Adapter.CustomerOrderViewAdapter;
import com.dev.hasarelm.buyingselling.Common.Endpoints;
import com.dev.hasarelm.buyingselling.Common.RetrofitClient;
import com.dev.hasarelm.buyingselling.Common.SharedPreferencesClass;
import com.dev.hasarelm.buyingselling.Model.OrderList;
import com.dev.hasarelm.buyingselling.Model.createOrder;
import com.dev.hasarelm.buyingselling.Model.orderCreate;
import com.dev.hasarelm.buyingselling.Model.orders;
import com.dev.hasarelm.buyingselling.R;
import com.dev.hasarelm.buyingselling.interfaces.orderItemClickListner;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dev.hasarelm.buyingselling.Common.BaseUrl.VLF_BASE_URL;

public class CustomerHistoryFragment  extends Fragment implements orderItemClickListner<orderCreate> {

    private RecyclerView mRvOrderList;
    View rootView;

    private OrderList mCreateOrder;
    private ArrayList<orders> mOrderCreates;
    private int userID;
    public static SharedPreferences localSp;
    private String id;
    private CustomerOrderViewAdapter mCustomerOrderViewAdapter;
    private Activity activity;

    public CustomerHistoryFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.customer_history_fragment, container, false);
        mRvOrderList = rootView.findViewById(R.id.order_history_fragment_rv_order_list);
        try {
            localSp = getContext().getSharedPreferences(SharedPreferencesClass.SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);
            id = localSp.getString("User_ID", "");
            userID = Integer.parseInt(id);

        } catch (Exception g) {
        }

        getUserOrderList(userID);
        return rootView;
    }

    private void getUserOrderList(int userID) {
        try {
            final ProgressDialog myPd_ring = ProgressDialog.show(getContext(), "Please wait", "", true);
            Endpoints endpoints = RetrofitClient.getLoginClient().create(Endpoints.class);
            Call<OrderList> call = endpoints.getAllOrders(VLF_BASE_URL+"orders?customer_id=",userID);
            call.enqueue(new Callback<OrderList>() {
                @Override
                public void onResponse(Call<OrderList> call, Response<OrderList> response) {

                    if (response.code()==200){

                        mCreateOrder = response.body();
                        mOrderCreates = mCreateOrder.getOrders();

                        if (mOrderCreates.size()>0){

                            setUpRecycleView(mOrderCreates);

                            myPd_ring.dismiss();
                        }

                        myPd_ring.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<OrderList> call, Throwable t) {

                }
            });
        }catch (Exception g){}

    }

    private void setUpRecycleView(ArrayList<orders> mOrderCreates) {

        mCustomerOrderViewAdapter = new CustomerOrderViewAdapter(activity, mOrderCreates, this);
        mRvOrderList.setHasFixedSize(true);
        mRvOrderList.setLayoutManager(new LinearLayoutManager(activity));
        mRvOrderList.setAdapter(mCustomerOrderViewAdapter);
    }

    @Override
    public void orderItemClick(int position, orderCreate data) {

    }
}
