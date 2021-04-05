package com.dev.hasarelm.buyingselling.Activity.Customer;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dev.hasarelm.buyingselling.Activity.LoginActivity;
import com.dev.hasarelm.buyingselling.Common.Endpoints;
import com.dev.hasarelm.buyingselling.Common.RetrofitClient;
import com.dev.hasarelm.buyingselling.Common.SharedPreferencesClass;
import com.dev.hasarelm.buyingselling.Model.ProfileUpdates;
import com.dev.hasarelm.buyingselling.Model.UserDetails;
import com.dev.hasarelm.buyingselling.Model.profile;
import com.dev.hasarelm.buyingselling.Model.profileUpdate;
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

import static com.dev.hasarelm.buyingselling.Common.BaseUrl.VLF_BASE_URL;

public class CustomerProfileFragment extends Fragment {

    private TextView mTvLogOut, mTvEditProfile,mTvUserName,mTvUserMobile;
    private EditText mEtFname, mEtLastname, mEtAddress, mEtStreet, mEtCity, mEtEmail, mEtMobile;
    private Button mBtnUpdate;
    private ProfileUpdates mProfileUpdateModel;
    private Dialog dialog, dialog2;
    private UserDetails mUserDetails;
    private ArrayList<profile> mProfile;
    private String ID = "";
    private int userID = 0;
    private String message;
    private ArrayList<profileUpdate> mRegister;
    public static SharedPreferences localSP;
    String name,mobile,lname,address,street,city;
    View rootView;

    public CustomerProfileFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.customer_profile_fragment, container, false);

        try {
            localSP = getContext().getSharedPreferences(SharedPreferencesClass.SETTINGS, Context.MODE_PRIVATE+Context.MODE_PRIVATE);
            ID = localSP.getString("User_ID","");
            name = localSP.getString("C_Name","");
            mobile = localSP.getString("C_Mobile","");
            lname = localSP.getString("C_Lname","");
            address = localSP.getString("C_Address","");
            street = localSP.getString("C_Street","");
            city = localSP.getString("C_City","");

            userID = Integer.parseInt(ID);
        }catch (Exception f){}

        mEtFname = rootView.findViewById(R.id.activity_customer_Update_update_et_fName);
        mEtLastname = rootView.findViewById(R.id.activity_customer_Update_update_et_lName);
        mEtAddress = rootView.findViewById(R.id.activity_customer_Update_update_et_address);
        mEtStreet = rootView.findViewById(R.id.activity_customer_Update_update_et_street);
        mEtCity = rootView.findViewById(R.id.activity_customer_Update_update_et_city);
        mEtEmail = rootView.findViewById(R.id.activity_customer_Update_update_et_email);
        mEtMobile = rootView.findViewById(R.id.activity_customer_Update_update_et_mobile_no);
        mBtnUpdate = rootView.findViewById(R.id.activity_profile_Update_update_btn_updated);
        mTvUserName = rootView.findViewById(R.id.user_name);
        mTvUserMobile = rootView.findViewById(R.id.user_name_mobile);

        try {
            mTvUserName.setText(name+"");
            mTvUserMobile.setText(mobile+"");
            mEtFname.setText(name);
            mEtLastname.setText(lname);
            mEtAddress.setText(address);
            mEtCity.setText(city);
            mEtMobile.setText(mobile);
        }catch (Exception g){}


        mTvLogOut = rootView.findViewById(R.id.log_out_btn);
        mTvLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Do you want to logout!")
                        .setConfirmText("Yes")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                Intent intent = new Intent(getContext(), LoginActivity.class);
                                startActivity(intent);
                                SharedPreferencesClass.ClearSharedPreference(getContext(),"Customer_user_name");
                            }
                        })
                        .setCancelButton("No", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        });

        mTvEditProfile = rootView.findViewById(R.id.edit_profile);
        mTvEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        mBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fname = mEtFname.getText().toString().trim();
                String lname = mEtLastname.getText().toString().trim();
                String asssress = mEtAddress.getText().toString().trim();
                String street = mEtStreet.getText().toString().trim();
                String city = mEtCity.getText().toString().trim();
                String mobile = mEtMobile.getText().toString().trim();

                ArrayList<profile> profiles = new ArrayList<>();
                profile pf = new profile();

                pf.setId(userID);
                pf.setName(fname);
                pf.setLast_name(lname);
                pf.setAdd_line_1(asssress);
                pf.setAdd_line_2(city);
                pf.setAdd_line_3(street);
                pf.setPhone(mobile);
                profiles.add(pf);
                customerProfileUpdate(profiles);
            }
        });

        return rootView;
    }

    private void customerProfileUpdate(ArrayList<profile> profiles) {

        final ProgressDialog myPd_ring = ProgressDialog.show(getContext(), "Please wait", "", true);
        try {

            String Json_Body = new Gson().toJson(profiles);
            JSONArray jsonArray = new JSONArray(Json_Body);
            JSONObject jsonObject = new JSONObject();
            if (jsonArray.length() > 0) {
                jsonObject = jsonArray.getJSONObject(0);
            }
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
            Endpoints apiService = RetrofitClient.getLoginClient().create(Endpoints.class);
            Call<ProfileUpdates> call_customer = apiService.profileUpdate(VLF_BASE_URL + "profile/update?", body);
            call_customer.enqueue(new Callback<ProfileUpdates>() {
                @Override
                public void onResponse(Call<ProfileUpdates> call, Response<ProfileUpdates> response) {

                    try {
                        if (response.code() == 200) {
                            myPd_ring.dismiss();
                            message = response.body().getMessage();
                            mProfileUpdateModel = response.body();
                            mRegister = mProfileUpdateModel.getProfileUpdate();

                            String Name = "";
                            String LName = "";
                            String address = "";
                            String PhoneNo = "";

                            for (profileUpdate rs : mRegister) {

                                Name = rs.getName().toString().trim();
                                LName = rs.getLast_name().toString().trim();
                                PhoneNo = rs.getPhone().toString().trim();
                                address = rs.getAdd_line_1().toString().trim();
                            }

                            mTvUserName.setText(Name+"");
                            mTvUserMobile.setText(PhoneNo+"");
                            SharedPreferencesClass.setLocalSharedPreference(getContext(), "R_email", address);
                            SharedPreferencesClass.setLocalSharedPreference(getContext(), "R_name", Name);
                            SharedPreferencesClass.setLocalSharedPreference(getContext(), "R_lName", LName);
                            SharedPreferencesClass.setLocalSharedPreference(getContext(), "R_phone", PhoneNo);

                            new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Profile Updated")
                                    .setContentText("Your profile successfully updated!")
                                    .setConfirmText("OK")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismissWithAnimation();
                                        }
                                    })
                                    .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismissWithAnimation();
                                        }
                                    })
                                    .show();

                            dialog.dismiss();
                        }
                    }catch (Exception g){}

                }

                @Override
                public void onFailure(Call<ProfileUpdates> call, Throwable t) {
                    myPd_ring.dismiss();
                }
            });

        } catch (Exception gg) {
        }
    }


}
