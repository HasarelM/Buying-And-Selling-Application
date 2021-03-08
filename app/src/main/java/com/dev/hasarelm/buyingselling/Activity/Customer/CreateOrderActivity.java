package com.dev.hasarelm.buyingselling.Activity.Customer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.dev.hasarelm.buyingselling.Common.Endpoints;
import com.dev.hasarelm.buyingselling.Common.RetrofitClient;
import com.dev.hasarelm.buyingselling.Common.SharedPreferencesClass;
import com.dev.hasarelm.buyingselling.Model.createOrder;
import com.dev.hasarelm.buyingselling.Model.orderCreate;
import com.dev.hasarelm.buyingselling.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dev.hasarelm.buyingselling.Activity.Customer.CustomerHomeFragment.AddID;
import static com.dev.hasarelm.buyingselling.Common.BaseUrl.VLF_BASE_URL;

public class CreateOrderActivity extends AppCompatActivity {

    private EditText mEtSellerName, mEtSellerMobile, mEtDescription;
    private Button mBtnUploadOrder;

    private createOrder mCreateOrder;
    private ArrayList<orderCreate> mOrderCreates;
    public static SharedPreferences localSp;
    private int userID;
    private String ID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);

        initView();

        mBtnUploadOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadOrder();
            }
        });

        localSp = this.getSharedPreferences(SharedPreferencesClass.SETTINGS, Context.MODE_PRIVATE+Context.MODE_PRIVATE);
        ID = localSp.getString("User_ID","");
        userID = Integer.parseInt(ID);
    }

    private void initView() {

        mEtSellerName = findViewById(R.id.activity_create_et_seller_name);
        mEtSellerMobile = findViewById(R.id.activity_create_et_seller_mobile);
        mEtDescription = findViewById(R.id.activity_create_order_et_details);
        mBtnUploadOrder = findViewById(R.id.activity_create_btn_create_order);

    }

    private ArrayList<orderCreate> orderCreate() {

        String sellerName = mEtSellerName.getText().toString().trim();
        String sellerMobile = mEtSellerMobile.getText().toString().trim();
        String description = mEtDescription.getText().toString().trim();

        ArrayList<orderCreate> creates = new ArrayList<>();
        orderCreate or = new orderCreate();
        or.setCustomer_id(userID);
        or.setAdvertisement_id(AddID);
        or.setDescription_1(sellerName);
        or.setDescription_2(sellerMobile);
        or.setDescription_3(description);
        creates.add(or);

        return creates;
    }

    private void uploadOrder() {

        final ProgressDialog myPd_ring = ProgressDialog.show(this, "Please wait", "", true);

        try {
            String Json_Body = new Gson().toJson(orderCreate());
            JSONArray jsonArray = new JSONArray(Json_Body);
            JSONObject jsonObject = new JSONObject();
            if (jsonArray.length() > 0) {
                jsonObject = jsonArray.getJSONObject(0);
            }

            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
            Endpoints endpoints = RetrofitClient.getLoginClient().create(Endpoints.class);
            Call<createOrder> call = endpoints.createOrder(VLF_BASE_URL + "order?", body);
            call.enqueue(new Callback<createOrder>() {
                @Override
                public void onResponse(Call<createOrder> call, Response<createOrder> response) {

                    if (response.code() == 200) {

                        mCreateOrder = response.body();
                        mOrderCreates = mCreateOrder.getOrderCreate();

                        if (mOrderCreates.size()>0){

                            new SweetAlertDialog(CreateOrderActivity.this)
                                    .setTitleText("Your order created!")
                                    .show();
                        }

                        myPd_ring.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<createOrder> call, Throwable t) {

                    myPd_ring.dismiss();
                }
            });
        } catch (Exception g) {
        }

    }
}