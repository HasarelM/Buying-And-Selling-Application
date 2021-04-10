package com.dev.hasarelm.buyingselling.Activity.Seller;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.dev.hasarelm.buyingselling.Common.Endpoints;
import com.dev.hasarelm.buyingselling.Common.RetrofitClient;
import com.dev.hasarelm.buyingselling.Common.SharedPreferencesClass;
import com.dev.hasarelm.buyingselling.Model.AddModel;
import com.dev.hasarelm.buyingselling.Model.CategoryListModel;
import com.dev.hasarelm.buyingselling.Model.RouteModel;
import com.dev.hasarelm.buyingselling.Model.advertisementCreate;
import com.dev.hasarelm.buyingselling.Model.categories;
import com.dev.hasarelm.buyingselling.Model.images;
import com.dev.hasarelm.buyingselling.Model.routes;
import com.dev.hasarelm.buyingselling.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dev.hasarelm.buyingselling.Common.BaseUrl.VLF_BASE_URL;

public class SellerAddActivity extends AppCompatActivity implements View.OnClickListener {

    //Ui components
    private EditText mEtTitle, mEtDescription, mEtDate, mEtTime,mEtToTime;
    private SearchableSpinner mSpCategory, mSpRoutePlane;
    private Button mBtnCreate;
    private ImageView mTvBack,mIvMainImage,mIvSecondImage;

    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    private TimePickerDialog picker;
    private TimePickerDialog toPicker;
    private RouteModel mRouteModel;
    private ArrayList<routes> mRoutesArrayList;
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayList<String> arrayList2 = new ArrayList<>();
    private CategoryListModel mCategoryListModel;
    private ArrayList<categories>mCategoriesArrayList;
    private ArrayList<images> mImagesArrayList;
    private AddModel mAddModel;
    private ArrayList<advertisementCreate> mAdvertisementCreateArrayList;
    private String districtID;
    private int d_ID;
    public static SharedPreferences localSP;

    public static SharedPreferences localSp;
    private String userID;
    String imageOne = "";
    String imageTwo = "";
    int routeID=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_add);

        initView();
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        try {

            localSP = this.getSharedPreferences(SharedPreferencesClass.SETTINGS, Context.MODE_PRIVATE+Context.MODE_PRIVATE);
            districtID = localSP.getString("districtID","");
            d_ID = Integer.parseInt(districtID);

        }catch (Exception g){}

        getCategoryTypes();
        getSellerRoute(d_ID);

        try {

            localSp = this.getSharedPreferences(SharedPreferencesClass.SETTINGS, Context.MODE_PRIVATE+Context.MODE_PRIVATE);
            userID = localSp.getString("User_ID","");

        }catch (Exception hh){}

    }

    private void getCategoryTypes() {

        final ProgressDialog myPd_ring = ProgressDialog.show(this, "Please wait", "", true);
        try {
            Endpoints endpoints = RetrofitClient.getLoginClient().create(Endpoints.class);
            Call<CategoryListModel> call = endpoints.getCategory(VLF_BASE_URL + "categories");
            call.enqueue(new Callback<CategoryListModel>() {
                @Override
                public void onResponse(Call<CategoryListModel> call, Response<CategoryListModel> response) {

                    if (response.code() == 200) {

                         int districtID = 0;
                         String name="";

                         mCategoryListModel = response.body();
                         mCategoriesArrayList = mCategoryListModel.getCategories();

                         for (categories ct : mCategoriesArrayList){

                             mImagesArrayList = ct.getImages();
                             name = ct.getName();
                             arrayList2.add(name);
                         }

                        arrayList2.add(0, "Select Category");
                        ArrayAdapter<String> dataAdapter_type = new ArrayAdapter<String>(SellerAddActivity.this, android.R.layout.simple_spinner_item, arrayList2);
                        dataAdapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mSpCategory.setAdapter(dataAdapter_type);

                        mSpCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                if (position==1){

                                    imageOne = "http://easybuyapp.ga/images/Dairy1.jpeg";
                                    imageTwo = "http://easybuyapp.ga/images/Dairy2.jpeg";
                                }else if (position==2){

                                    imageOne = "http://easybuyapp.ga/images/Fish1.jpg";
                                    imageTwo =  "http://easybuyapp.ga/images/Fish2.jpg";
                                }else if (position==3){

                                    imageOne = "http://easybuyapp.ga/images/Vegitables1.jpg";
                                    imageTwo = "http://easybuyapp.ga/images/Vegitables1.jpg";
                                }else if (position==4){

                                    imageOne = "http://easybuyapp.ga/images/BakeryProducts1.jpg";
                                    imageTwo = "http://easybuyapp.ga/images/BakeryProducts2.jpg";
                                }else if (position==5){

                                    imageOne = "http://easybuyapp.ga/images/GroceryItems1.jpg";
                                    imageTwo = "http://easybuyapp.ga/images/GroceryItems2.jpg";
                                }else if (position==6){

                                    imageOne = "http://easybuyapp.ga/images/Services1.jpg";
                                    imageTwo = "http://easybuyapp.ga/images/Services2.jpg";
                                }

                                try {
                                    Picasso.get().load(imageOne)
                                            .error(R.drawable.ic_launcher_background)
                                            .into(mIvMainImage);

                                    Picasso.get().load(imageTwo)
                                            .error(R.drawable.ic_launcher_background)
                                            .into(mIvSecondImage);
                                }catch (Exception ww)
                                {

                                }

                                myPd_ring.dismiss();

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }

                }

                @Override
                public void onFailure(Call<CategoryListModel> call, Throwable t) {

                    myPd_ring.dismiss();
                }
            });

        } catch (Exception g) {
        }
    }

    private void getSellerRoute(int districtID) {
        final ProgressDialog myPd_ring = ProgressDialog.show(this, "Please wait", "", true);
        try {

            Endpoints endpoints = RetrofitClient.getLoginClient().create(Endpoints.class);
            Call<RouteModel> call = endpoints.getRouteList(VLF_BASE_URL + "routes?", districtID);
            call.enqueue(new Callback<RouteModel>() {
                @Override
                public void onResponse(Call<RouteModel> call, Response<RouteModel> response) {

                    if (response.code() == 200) {

                        mRouteModel = response.body();
                        mRoutesArrayList = mRouteModel.getRoutes();
                        String routeName;

                        for (routes rt : mRoutesArrayList) {

                            routeName = rt.getRoute();
                            arrayList.add(routeName);
                        }

                        arrayList.add(0, "Select Route");
                        ArrayAdapter<String> dataAdapter_type = new ArrayAdapter<String>(SellerAddActivity.this, android.R.layout.simple_spinner_item, arrayList);
                        dataAdapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mSpRoutePlane.setAdapter(dataAdapter_type);

                        mSpRoutePlane.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                routeID = position;

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        myPd_ring.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<RouteModel> call, Throwable t) {
                    myPd_ring.dismiss();
                }
            });

        } catch (Exception g) {
        }

    }

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, datePickerListener, year, month, day);
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            day = selectedDay;
            month = selectedMonth;
            year = selectedYear;

            int month = selectedMonth + 1;
            String formattedMonth = "" + month;
            String formattedDayOfMonth = "" + selectedDay;

            if(month < 10){

                formattedMonth = "0" + month;
            }
            if(selectedDay < 10){

                formattedDayOfMonth = "0" + selectedDay;
            }

            mEtDate.setText(selectedYear + "-" + (formattedMonth) + "-"
                    +formattedDayOfMonth );
        }
    };

    private void initView() {

        mEtTitle = findViewById(R.id.activity_seller_add_et_title);
        mEtDescription = findViewById(R.id.activity_seller_add_et_description);
        mEtDate = findViewById(R.id.activity_seller_add_et_date);
        mEtDate.setKeyListener(null);
        mEtTime = findViewById(R.id.activity_seller_add_et_time);
        mEtTime.setOnClickListener(this);
        mEtTime.setKeyListener(null);
        mSpCategory = findViewById(R.id.activity_seller_add_sp_category);
        mSpRoutePlane = findViewById(R.id.activity_seller_add_sp_route_plane);
        mBtnCreate = findViewById(R.id.activity_seller_add_btn_create);
        mBtnCreate.setOnClickListener(this);
        mEtDate.setOnClickListener(this);
        mEtToTime = findViewById(R.id.activity_seller_add_to_time);
        mEtToTime.setOnClickListener(this);
        mEtToTime.setKeyListener(null);
        mTvBack = findViewById(R.id.seller_add_add_arrow);
        mTvBack.setOnClickListener(this);
        mIvMainImage = findViewById(R.id.activity_seller_iv_loadImage);
        mIvSecondImage = findViewById(R.id.activity_seller_iv_loadImage2);
    }

    private void UsersetTime() {

        final Calendar cldr = Calendar.getInstance();
        int hour = cldr.get(Calendar.HOUR_OF_DAY);
        int minutes = cldr.get(Calendar.MINUTE);
        // time picker dialog
        picker = new TimePickerDialog(SellerAddActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {

                        String format_min = ""+ sMinute;
                        String format_hrs = ""+sHour;

                        if (sMinute<10){

                            format_min = "0"+minutes;
                           /*
                            if (format_min.startsWith("0")){

                            }*/
                        }

                        if (sHour<10)
                        {
                            format_hrs = "0"+sHour;
                        }

                        mEtTime.setText(format_hrs + ":" + format_min + ":00");
                    }
                }, hour, minutes, false);
        picker.show();
    }

    private void toTime(){

        final Calendar cldr = Calendar.getInstance();
        int hour = cldr.get(Calendar.HOUR_OF_DAY);
        int minutes = cldr.get(Calendar.MINUTE);
        // time picker dialog
        toPicker = new TimePickerDialog(SellerAddActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {

                        String format_min = ""+ sMinute;
                        String format_hrs = ""+sHour;

                        if (sMinute<10){

                            format_min = "0"+minutes;
                        }

                        if (sHour<10)
                        {
                            format_hrs = "0"+sHour;
                        }
                        mEtToTime.setText(format_hrs + ":" + format_min + ":00");
                    }
                }, hour, minutes, false);
        toPicker.show();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.activity_seller_add_btn_create:
                createAdd();
                break;
            case R.id.activity_seller_add_et_date:
                showDialog(0);
                break;
            case R.id.activity_seller_add_et_time:
                UsersetTime();
                break;
            case R.id.seller_add_add_arrow:
                Intent intent = new Intent(SellerAddActivity.this, SellerHomeActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.activity_seller_add_to_time:
                toTime();
                break;
            default:
                break;
        }
    }

    private ArrayList<advertisementCreate> addCreated(){

        int ID = Integer.parseInt(userID);

        ArrayList<advertisementCreate> advertisementCreates = new ArrayList<>();
        advertisementCreate create = new advertisementCreate();

        int categoryID = mSpCategory.getSelectedItemPosition();
        int routePlane = routeID;
        String title = mEtTitle.getText().toString().trim();
        String description = mEtDescription.getText().toString().trim();
        String date = mEtDate.getText().toString().trim();
        String toTime = mEtToTime.getText().toString().trim();
        String fromTime = mEtTime.getText().toString().trim();
        String imagene = imageOne;

        create.setSeller_id(ID);
        create.setCategory_id(categoryID);
        create.setRoute_id(routePlane);
        create.setTitle(title);
        create.setDescription(description);
        create.setDate(date);
        create.setTo_time(toTime);
        create.setFrom_time(fromTime);
        create.setSub_description(imagene);
        advertisementCreates.add(create);

        return  advertisementCreates;
    }

    private void createAdd() {

        if (validation()){

            final ProgressDialog myPd_ring=ProgressDialog.show(this, "Please wait", "", true);
            try {
                String Json_Body = new Gson().toJson(addCreated());
                JSONArray jsonArray = new JSONArray(Json_Body);
                JSONObject jsonObject = new JSONObject();
                if (jsonArray.length()>0){
                    jsonObject = jsonArray.getJSONObject(0);
                }

                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),jsonObject.toString());
                Endpoints endpoints = RetrofitClient.getLoginClient().create(Endpoints.class);
                Call<AddModel> call = endpoints.createAdd(VLF_BASE_URL+"advertisement",body);
                call.enqueue(new Callback<AddModel>() {
                    @Override
                    public void onResponse(Call<AddModel> call, Response<AddModel> response) {

                        if (response.code()==200){

                            String imageOne = "";
                            mAddModel = response.body();
                            mAdvertisementCreateArrayList = mAddModel.getAdvertisementCreate();

                            myPd_ring.dismiss();

                            new SweetAlertDialog(SellerAddActivity.this)
                                    .setTitleText("Your Addverstisment Succussfully Updated!")
                                    .show();
                        }

                    }

                    @Override
                    public void onFailure(Call<AddModel> call, Throwable t) {

                        myPd_ring.dismiss();
                    }
                });

            }catch (Exception g){

            }
        }
    }

    private boolean validation() {
        final String title = mEtTitle.getText().toString();
        final String description = mEtDescription.getText().toString();
        final String date = mEtDate.getText().toString();
        final String time = mEtTime.getText().toString();
        final String t_time = mEtToTime.getText().toString();

        if (title.length() == 0) {
            mEtTitle.requestFocus();
            mEtTitle.setError("Title cannot be blank");
            return false;
        } else if (description.length() == 0) {
            mEtDescription.requestFocus();
            mEtDescription.setError("Description cannot be blank");
            return false;
        } else if (date.length()==0) {
            mEtDate.requestFocus();
            mEtDate.setError("Date cannot be blank");
            return false;
        } else if (time.length() == 0) {
            mEtTime.requestFocus();
            mEtTime.setError("To time cannot be blank");
            return false;
        }else if (t_time.length() == 0) {
            mEtToTime.requestFocus();
            mEtToTime.setError("From time cannot be blank");
            return false;
        }  else {

        }
        return true;
    }


}