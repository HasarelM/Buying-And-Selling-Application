package com.dev.hasarelm.buyingselling.Activity.Customer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.hasarelm.buyingselling.Adapter.NotificationAdapter;
import com.dev.hasarelm.buyingselling.Common.Endpoints;
import com.dev.hasarelm.buyingselling.Common.RetrofitClient;
import com.dev.hasarelm.buyingselling.Common.SharedPreferencesClass;
import com.dev.hasarelm.buyingselling.Model.NotificationModel;
import com.dev.hasarelm.buyingselling.Model.notifications;
import com.dev.hasarelm.buyingselling.R;
import com.dev.hasarelm.buyingselling.interfaces.notificationClickLisner;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dev.hasarelm.buyingselling.Common.BaseUrl.VLF_BASE_URL;

public class CustomerNotificationFragment extends Fragment implements notificationClickLisner<notifications> {

    private RecyclerView mNotificationRecyclerView;
    private NotificationModel mNotificationModel;
    private ArrayList<notifications> mNotificationsArrayList;
    private NotificationAdapter mNotificationAdapter;
    private Activity activity;
    private TextView mTvNotification;

    public static SharedPreferences localSP;
    private String userID = "";
    private int id ;
    private int mClickID;
    private String message;
    View rootView;

    public CustomerNotificationFragment() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.seller_notification_fragment, container, false);
        mNotificationRecyclerView = rootView.findViewById(R.id.rv_notification);
        mTvNotification = rootView.findViewById(R.id.notification_tv);

        try {

            localSP = getContext().getSharedPreferences(SharedPreferencesClass.SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);
            userID = localSP.getString("User_ID", "");
            id = Integer.parseInt(userID);

        } catch (Exception g) {
        }

        getAllNotification(id);
        return rootView;
    }

    private void getAllNotification(int id) {
        final ProgressDialog myPd_ring=ProgressDialog.show(getContext(), "Please wait", "", true);
        Endpoints endpoints = RetrofitClient.getLoginClient().create(Endpoints.class);
        Call<NotificationModel> call = endpoints.getNotification(VLF_BASE_URL+"notifications?",id);
        call.enqueue(new Callback<NotificationModel>() {
            @Override
            public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {

                try {
                    if (response.code()==200){
                        myPd_ring.dismiss();
                        String mdg = "";
                        mNotificationModel = response.body();
                        mNotificationsArrayList = mNotificationModel.getNotifications();
                        mdg = mNotificationModel.getMessage().toString().trim();

                        if (mNotificationsArrayList.size()>0){

                            setupRecyclerView(mNotificationsArrayList);
                            //  myPd_ring.dismiss();
                        }else {

                            mNotificationRecyclerView.setVisibility(View.GONE);
                            mTvNotification.setVisibility(View.VISIBLE);
                        }
                    }

                }catch (Exception g){}
            }

            @Override
            public void onFailure(Call<NotificationModel> call, Throwable t) {

            }
        });
    }

    private void setupRecyclerView(ArrayList<notifications> mNotificationsArrayList) {

        mNotificationAdapter = new NotificationAdapter(activity, mNotificationsArrayList, this);
        mNotificationRecyclerView.setHasFixedSize(true);
        mNotificationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mNotificationRecyclerView.setAdapter(mNotificationAdapter);
    }

    @Override
    public void notificationClick(int position, notifications data) {

        mClickID = data.getId();

        updateViewNotification(mClickID);
    }

    private void updateViewNotification(int mClickID) {

        try {
            Endpoints endPoints = RetrofitClient.getLoginClient().create(Endpoints.class);
            Call<NotificationModel> call = endPoints.updateClickNotification(VLF_BASE_URL+"notification?",mClickID);
            call.enqueue(new Callback<NotificationModel>() {
                @Override
                public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {

                    if (response.code()==200){

                        mNotificationModel = response.body();
                        message = mNotificationModel.getMessage();
                    }
                }

                @Override
                public void onFailure(Call<NotificationModel> call, Throwable t) {

                }
            });

        }catch (Exception h){}
    }
}
