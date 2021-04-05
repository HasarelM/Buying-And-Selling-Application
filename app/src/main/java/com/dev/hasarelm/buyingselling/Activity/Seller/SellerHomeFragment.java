package com.dev.hasarelm.buyingselling.Activity.Seller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.hasarelm.buyingselling.Adapter.CustomerOrderAdapter;
import com.dev.hasarelm.buyingselling.Common.Endpoints;
import com.dev.hasarelm.buyingselling.Common.RetrofitClient;
import com.dev.hasarelm.buyingselling.Common.SharedPreferencesClass;
import com.dev.hasarelm.buyingselling.Model.OrderList;
import com.dev.hasarelm.buyingselling.Model.OrderUpdates;
import com.dev.hasarelm.buyingselling.Model.orderUpdate;
import com.dev.hasarelm.buyingselling.Model.orders;
import com.dev.hasarelm.buyingselling.R;
import com.dev.hasarelm.buyingselling.interfaces.customerOrderClickListner;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dev.hasarelm.buyingselling.Activity.LoginActivity.GPS_Latitude;
import static com.dev.hasarelm.buyingselling.Activity.LoginActivity.GPS_Longitude;
import static com.dev.hasarelm.buyingselling.Common.BaseUrl.VLF_BASE_URL;

public class SellerHomeFragment extends Fragment implements customerOrderClickListner<orders> {

    private RecyclerView mRvCustomerAllList;
    private CustomerOrderAdapter mCustomerOrderAdapter;

    private Activity activity;
    public static SharedPreferences localSp;
    private OrderList mOrderList;
    private ArrayList<orders> mOrders;
    private OrderUpdates mOrderUpdates;
    private ArrayList<orderUpdate> mOrderUpdateArrayList;
    private String message;
    private String userID;
    private int id;
    private String longitude;
    private String latitude;

    View rootView;

    public SellerHomeFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.seller_home_fragment, container, false);
        mRvCustomerAllList = rootView.findViewById(R.id.seller_home_fragment_rv_all_list);

        try {

            localSp = getContext().getSharedPreferences(SharedPreferencesClass.SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);
            userID = localSp.getString("User_ID", "");
            id = Integer.parseInt(userID);

        } catch (Exception g) {
        }

        getCustomerOrderList();
        return rootView;
    }

    private void getCustomerOrderList() {
        final ProgressDialog myPd_ring = ProgressDialog.show(getContext(), "Please wait", "", true);
        try {
            Endpoints endpoints = RetrofitClient.getLoginClient().create(Endpoints.class);
            Call<OrderList> call = endpoints.getAll(VLF_BASE_URL + "orders",1);
            call.enqueue(new Callback<OrderList>() {
                @Override
                public void onResponse(Call<OrderList> call, Response<OrderList> response) {

                    if (response.code() == 200) {

                        myPd_ring.dismiss();
                        mOrderList = response.body();
                        mOrders = mOrderList.getOrders();

                        setUpRecycleView(mOrders);
                        myPd_ring.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<OrderList> call, Throwable t) {
                    myPd_ring.dismiss();
                }
            });
        } catch (Exception g) {
        }
    }

    private void setUpRecycleView(ArrayList<orders> mOrders) {
        mCustomerOrderAdapter = new CustomerOrderAdapter(activity, mOrders, this);
        mRvCustomerAllList.setHasFixedSize(true);
        mRvCustomerAllList.setLayoutManager(new LinearLayoutManager(activity));
        mRvCustomerAllList.setAdapter(mCustomerOrderAdapter);
    }

    @Override
    public void customerOrderClick(int position, orders data) {

        double lng = GPS_Longitude;
        double lat = GPS_Latitude;

        int orderID = data.getId();
        int status = 3;

        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Are you confirm this order to delivery")
                .setConfirmText("Yes!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();

                        updateStatus(orderID,status);

                        try {

                            longitude = data.getLongitude() + "";
                            latitude = data.getLatitude() + "";

                            String uri = String.format("http://maps.google.com/maps?daddr=" + latitude + "," + longitude);
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                            intent.setPackage("com.google.android.apps.maps");
                            startActivity(intent);

                        } catch (Exception h) {
                        }

                    }
                })
                .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();

    }

    private void updateStatus(int orderID, int status) {
        Endpoints endpoints = RetrofitClient.getLoginClient().create(Endpoints.class);
        Call<OrderUpdates> call = endpoints.orderStatusUpdate(VLF_BASE_URL+"order/update?",orderID,status);
        call.enqueue(new Callback<OrderUpdates>() {
            @Override
            public void onResponse(Call<OrderUpdates> call, Response<OrderUpdates> response) {

                try {
                    if (response.code()==200){

                        mOrderUpdates = response.body();
                        message = mOrderUpdates.getMessage().toString().trim();
                    }
                }catch (Exception g){}

            }

            @Override
            public void onFailure(Call<OrderUpdates> call, Throwable t) {

            }
        });
    }
}
