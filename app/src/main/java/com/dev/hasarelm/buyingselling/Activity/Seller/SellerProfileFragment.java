package com.dev.hasarelm.buyingselling.Activity.Seller;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.hasarelm.buyingselling.Activity.LoginActivity;
import com.dev.hasarelm.buyingselling.Adapter.CustomerAddAdapter;
import com.dev.hasarelm.buyingselling.Adapter.SellerAddsAdapter;
import com.dev.hasarelm.buyingselling.Common.Endpoints;
import com.dev.hasarelm.buyingselling.Common.RetrofitClient;
import com.dev.hasarelm.buyingselling.Common.SharedPreferencesClass;
import com.dev.hasarelm.buyingselling.Model.AllAdvertisementsModel;
import com.dev.hasarelm.buyingselling.Model.CustomerRegisterModel;
import com.dev.hasarelm.buyingselling.Model.DeleteSellerAdd;
import com.dev.hasarelm.buyingselling.Model.UserDetails;
import com.dev.hasarelm.buyingselling.Model.advertisementDelete;
import com.dev.hasarelm.buyingselling.Model.advertisements;
import com.dev.hasarelm.buyingselling.Model.profile;
import com.dev.hasarelm.buyingselling.Model.register;
import com.dev.hasarelm.buyingselling.R;
import com.dev.hasarelm.buyingselling.interfaces.addDeleteListner;
import com.dev.hasarelm.buyingselling.interfaces.addLongClickListner;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dev.hasarelm.buyingselling.Common.BaseUrl.VLF_BASE_URL;

public class SellerProfileFragment extends Fragment implements addDeleteListner<advertisements> , addLongClickListner<advertisements> {

    View rootView;
    public static SharedPreferences localSP;
    private ImageButton mBtnLogOut;
    private RatingBar mRatingBar;
    private TextView mTvProfileEdit,mTvLogOut;
    private RecyclerView mRvSellerAddList;

    private String message;
    private ArrayList<register> mRegister;
    private List<String> mSelectVehicleType = new ArrayList<String>();
    private CustomerRegisterModel mCustomerRegisterModel;
    private Dialog dialog,dialog2;
    private UserDetails mUserDetails;
    private ArrayList<profile>mProfile;
    private String ID = "";
    private int userID=0;
    private AllAdvertisementsModel mAdvertisementsModel;
    private ArrayList<advertisements> advertisements;
    private SellerAddsAdapter mSellerAddsAdapter;
    private Activity activity;
    private DeleteSellerAdd mDeleteSellerAdd;
    private ArrayList<advertisementDelete> mAdvertisementDelete;

    public SellerProfileFragment(){


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.seller_user_profile, container, false);

        try {
            localSP = getContext().getSharedPreferences(SharedPreferencesClass.SETTINGS, Context.MODE_PRIVATE+Context.MODE_PRIVATE);
            ID = localSP.getString("User_ID","");
            userID = Integer.parseInt(ID);
        }catch (Exception f){}


        mRvSellerAddList = rootView.findViewById(R.id.seller_add_rv_ist);

        mTvLogOut = rootView.findViewById(R.id.log_out_btn_seller);
        mTvProfileEdit = rootView.findViewById(R.id.editProfile_tv);
        mTvLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                SharedPreferencesClass.ClearSharedPreference(getContext(),"seller_user_name");
            }
        });


        mTvProfileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //updateProfile();
            }
        });

       // getDonaterLevel();

        getSellerOwnAdds(userID);

        return rootView;
    }

    private void getSellerOwnAdds(int userID) {

        final ProgressDialog myPd_ring = ProgressDialog.show(getContext(), "Please wait", "", true);

        Endpoints endpoints = RetrofitClient.getLoginClient().create(Endpoints.class);
        Call<AllAdvertisementsModel> call = endpoints.getSellerAdds(VLF_BASE_URL+"advertisements?driver_id=",userID);
        call.enqueue(new Callback<AllAdvertisementsModel>() {
            @Override
            public void onResponse(Call<AllAdvertisementsModel> call, Response<AllAdvertisementsModel> response) {

                if (response.code() == 200) {

                    mAdvertisementsModel = response.body();
                    advertisements = mAdvertisementsModel.getAdvertisements();

                    if (advertisements.size()>0){

                        setupRecyclerView(advertisements);

                        myPd_ring.dismiss();
                    }
                }

            }

            @Override
            public void onFailure(Call<AllAdvertisementsModel> call, Throwable t) {

            }
        });
    }

    private void setupRecyclerView(ArrayList<advertisements> advertisements) {

        mSellerAddsAdapter = new SellerAddsAdapter(activity, advertisements,this,this);
        mRvSellerAddList.setHasFixedSize(true);
        mRvSellerAddList.setLayoutManager(new LinearLayoutManager(activity));
        mRvSellerAddList.setAdapter(mSellerAddsAdapter);
    }

    private void getDonaterLevel() {

        final ProgressDialog myPd_ring = ProgressDialog.show(getContext(), "Please wait", "", true);
        Endpoints endPoints = RetrofitClient.getLoginClient().create(Endpoints.class);
        Call<UserDetails> call = endPoints.getUserDetails(VLF_BASE_URL+"profile?",userID);
        call.enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {

                try {

                    if (response.code()==200){

                        mUserDetails = response.body();
                        mProfile = mUserDetails.getProfile();

                        for (profile pf : mProfile){

                           /* mTvUserName.setText("Mr."+pf.getName());

                            if (pf.getRate()==1){

                                mTvDonateLevel.setText("Level 3 Donater");
                                mRatingBar.setRating(Float.parseFloat("3.0"));
                                mTvDescription.setText("In this tutorial, we shows you two basic examples to send SMS message. ... 1, You will use Android Studio IDE to create an Android application and name it as");
                                myPd_ring.dismiss();
                            }*/
                        }
                    }

                }catch (Exception f){}
            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {

                myPd_ring.dismiss();
            }
        });
    }

   /* private void updateProfile() {

        try {
             dialog = new Dialog(getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.edit_profile_layout);

            EditText name = dialog.findViewById(R.id.activity_seller_register_update_et_fName);
            EditText lName = dialog.findViewById(R.id.activity_seller_register_update_et_lName);
            EditText email = dialog.findViewById(R.id.activity_seller_register_update_et_email);
            EditText mobile = dialog.findViewById(R.id.activity_seller_register_update_et_mobile_no);
            EditText vehicle = dialog.findViewById(R.id.activity_seller_register_update_et_vehicle_no);
            EditText password = dialog.findViewById(R.id.activity_seller_register_update_et_password);
            EditText c_password = dialog.findViewById(R.id.activity_seller_register_update_et_c_password);
            Button mBtnUpdate = dialog.findViewById(R.id.activity_seller_register_update_btn_register);

            String userID = localSP.getString("User_ID","");
            int ID = Integer.parseInt(userID);


            mBtnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String Email = name.getText().toString();
                    String first_name = lName.getText().toString();
                    String lat_name = email.getText().toString();
                    String phone = mobile.getText().toString();
                    String vehicle_no = vehicle.getText().toString();
                    String pass = password.getText().toString().trim();
                    String c_pass = c_password.getText().toString().trim();

                    ArrayList<profile> profiles = new ArrayList<>();

                    profile pf = new profile();
                    pf.setId(ID);
                    pf.setEmail(Email);
                    pf.setName(first_name);
                    pf.setLast_name(lat_name);
                    pf.setPhone(phone);
                    pf.setVehicle_no(vehicle_no);
                    pf.setPassword(pass);
                    pf.setC_password(c_pass);
                    profiles.add(pf);

                    updateUserProfileData(profiles);
                }
            });

            dialog.show();

        }catch (Exception h){}

    }

    private void updateUserProfileData(ArrayList<profile> profiles) {

        final ProgressDialog myPd_ring=ProgressDialog.show(getContext(), "Please wait", "", true);
        try {

            String Json_Body = new Gson().toJson(profiles);
            JSONArray jsonArray = new JSONArray(Json_Body);
            JSONObject jsonObject = new JSONObject();
            if (jsonArray.length()>0){
                jsonObject = jsonArray.getJSONObject(0);
            }
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),jsonObject.toString());
            Endpoints apiService = RetrofitClient.getLoginClient().create(Endpoints.class);
            Call<CustomerRegisterModel> call_customer = apiService.profileUpdate(VLF_BASE_URL + "profile/update?",body);
            call_customer.enqueue(new Callback<CustomerRegisterModel>() {
                @Override
                public void onResponse(Call<CustomerRegisterModel> call, Response<CustomerRegisterModel> response) {

                    if (response.code() == 200) {
                        myPd_ring.dismiss();
                        message = response.body().getMessage();
                        mCustomerRegisterModel = response.body();
                        mRegister = mCustomerRegisterModel.getRegister();

                        new SweetAlertDialog(getContext())
                                .setTitleText("Your Profile Updated!")
                                .show();

                        dialog.dismiss();

                        String Name="";
                        String LName="";
                        String Email ="";
                        String PhoneNo="";
                        String Vehicle="";

                        for (register rs : mRegister){

                            Name = rs.getName().toString().trim();
                            LName = rs.getLast_name().toString().trim();
                            Email = rs.getEmail().toString().trim();
                            PhoneNo = rs.getPhone().toString().trim();
                            Vehicle = rs.getVehicle_no().toString().trim();
                        }

                        SharedPreferencesClass.setLocalSharedPreference(getContext(),"R_email",Email);
                        SharedPreferencesClass.setLocalSharedPreference(getContext(),"R_name",Name);
                        SharedPreferencesClass.setLocalSharedPreference(getContext(),"R_lName",LName);
                        SharedPreferencesClass.setLocalSharedPreference(getContext(),"R_phone",PhoneNo);
                        SharedPreferencesClass.setLocalSharedPreference(getContext(),"R_vehicle_no",Vehicle);


                    }
                }

                @Override
                public void onFailure(Call<CustomerRegisterModel> call, Throwable t) {
                    myPd_ring.dismiss();
                }
            });

        } catch (Exception gg) {
        }
    }*/

    @Override
    public void addDeleteClick(int position, advertisements data) {

        int ID = data.getId();

        try {

            Dialog dialog2 = new Dialog(getContext());
            dialog2.setContentView(R.layout.add_view_layout);
            dialog2.setTitle("Title");

            //TextView mTvCategory,mTvTitle,mTvDescription,mTvRoutePlane,mTvDistrict,mTvDate,mTvFromDate,mTvToDate;

            TextView  mTvCategory = dialog2.findViewById(R.id.add_view_category_type);
            TextView mTvTitle = dialog2.findViewById(R.id.add_view_title);
            TextView mTvDescription = dialog2.findViewById(R.id.add_view_description);
            TextView mTvRoutePlane = dialog2.findViewById(R.id.add_view_route_plane);
            TextView mTvDistrict = dialog2.findViewById(R.id.ad_view_district);
            TextView mTvDate = dialog2.findViewById(R.id.add_view_visit_date);
            TextView mTvFromDate = dialog2.findViewById(R.id.add_view_from_time);
            TextView  mTvToDate = dialog2.findViewById(R.id.add_view_to_time);

            String cat = data.getCategory().toString().trim();
            String tit = data.getTitle().toString().trim();
            String des = data.getDescription().toString().trim();
            String rp =  data.getCategory().toString().trim();
            String f_time = data.getFrom_time().toString().trim();
            String l_time = data.getTo_time().toString().trim();

            mTvCategory.setText(cat+"");
            mTvTitle.setText(tit+"");
            mTvDescription.setText(des+"");
            mTvRoutePlane.setText(rp+"");
            mTvFromDate.setText(f_time+"");
            mTvToDate.setText(l_time+"");

            dialog2.show();

        }catch (Exception f){}

    }

    private void deleteSellerAdd(int id) {

        final ProgressDialog myPd_ring = ProgressDialog.show(getContext(), "Please wait", "", true);

        Endpoints endpoints = RetrofitClient.getLoginClient().create(Endpoints.class);
        Call<DeleteSellerAdd> call = endpoints.deleteSellerAdds(VLF_BASE_URL+"advertisement?id=",id);
        call.enqueue(new Callback<DeleteSellerAdd>() {
            @Override
            public void onResponse(Call<DeleteSellerAdd> call, Response<DeleteSellerAdd> response) {

                if (response.code()==200){

                    mDeleteSellerAdd = response.body();
                    mAdvertisementDelete = mDeleteSellerAdd.getAdvertisementDelete();

                        myPd_ring.dismiss();

                        new SweetAlertDialog(getContext())
                                .setTitleText("Delete your Add!")
                                .show();
                }
            }

            @Override
            public void onFailure(Call<DeleteSellerAdd> call, Throwable t) {

                myPd_ring.dismiss();
            }
        });

    }

    @Override
    public void addLongClick(int position, advertisements data) {

        int ID = data.getId();

        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("You won't be able to recover this file!")
                .setConfirmText("Delete!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();

                        deleteSellerAdd(ID);
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
}