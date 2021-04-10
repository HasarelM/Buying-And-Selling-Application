package com.dev.hasarelm.buyingselling.Activity.Customer;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.hasarelm.buyingselling.Adapter.CustomerAddAdapter;
import com.dev.hasarelm.buyingselling.Common.Endpoints;
import com.dev.hasarelm.buyingselling.Common.RetrofitClient;
import com.dev.hasarelm.buyingselling.Common.SharedPreferencesClass;
import com.dev.hasarelm.buyingselling.Model.AllAdvertisementsModel;
import com.dev.hasarelm.buyingselling.Model.CategoryListModel;
import com.dev.hasarelm.buyingselling.Model.RouteModel;
import com.dev.hasarelm.buyingselling.Model.advertisements;
import com.dev.hasarelm.buyingselling.Model.categories;
import com.dev.hasarelm.buyingselling.Model.images;
import com.dev.hasarelm.buyingselling.Model.routes;
import com.dev.hasarelm.buyingselling.R;
import com.dev.hasarelm.buyingselling.interfaces.addViewListner;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dev.hasarelm.buyingselling.Common.BaseUrl.VLF_BASE_URL;

public class CustomerHomeFragment extends Fragment implements View.OnClickListener, addViewListner<advertisements> {
    View rootView;
    private RecyclerView mRvSellerAddList;
    private Button mBtnFilter;
    private SearchableSpinner mSpCategory, mSpRoutePlane;

    private AllAdvertisementsModel mAdvertisementsModel;
    private ArrayList<advertisements> advertisements;
    private CustomerAddAdapter mCustomerAddAdapter;
    private Activity activity;
    private String districtID;
    private int d_ID;
    public static SharedPreferences localSP;
    public static SharedPreferences localSp;
    private LinearLayout linearLayout;
    private Button mBtnSearch;
    private RouteModel mRouteModel;
    private ArrayList<routes> mRoutesArrayList;
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayList<String> arrayList2 = new ArrayList<>();
    private CategoryListModel mCategoryListModel;
    private ArrayList<categories> mCategoriesArrayList;
    private ArrayList<images> mImagesArrayList;
    private int category_id;
    private int rout_id;
    private CheckBox mChkAll;
    private Dialog add_dialog;
    static int AddID;

    public CustomerHomeFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.customer_home_fragment, container, false);

        try {

            localSP = getContext().getSharedPreferences(SharedPreferencesClass.SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);
            districtID = localSP.getString("district", "");
            d_ID = Integer.parseInt(districtID);

        } catch (Exception g) {
        }

        mBtnFilter = rootView.findViewById(R.id.customer_home_fragment_filter);
        mBtnFilter.setOnClickListener(this);
        mRvSellerAddList = rootView.findViewById(R.id.customer_home_fragment_rv_list);
        mBtnSearch = rootView.findViewById(R.id.filter_dialog_btn_search);
        linearLayout = rootView.findViewById(R.id.filter_layout_visible);
        mSpCategory = rootView.findViewById(R.id.filter_dialog_sw_category);
        mSpRoutePlane = rootView.findViewById(R.id.filter_dialog_sw_route);
        mChkAll = rootView.findViewById(R.id.chk_all_adds);
        linearLayout.setVisibility(View.GONE);

        allAdds();
        getCategoryTypes();
        getSellerRoute(d_ID);


        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                linearLayout.setVisibility(View.GONE);

                filterMethods(category_id, rout_id);
            }
        });

        mChkAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    allAdds();
                }
            }
        });
        return rootView;
    }

    private void filterMethods(int category_id, int rout_id) {

        final ProgressDialog myPd_ring = ProgressDialog.show(getContext(), "Please wait", "", true);

        Endpoints endpoints = RetrofitClient.getLoginClient().create(Endpoints.class);
        Call<AllAdvertisementsModel> call = endpoints.getAddFilter(VLF_BASE_URL + "advertisements?category_id=", rout_id,category_id );
        call.enqueue(new Callback<AllAdvertisementsModel>() {
            @Override
            public void onResponse(Call<AllAdvertisementsModel> call, Response<AllAdvertisementsModel> response) {

                if (response.code() == 200) {

                    myPd_ring.dismiss();

                    mAdvertisementsModel = response.body();
                    advertisements = mAdvertisementsModel.getAdvertisements();

                    if (advertisements.size() > 0) {

                        setupRecyclerView(advertisements);

                        myPd_ring.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<AllAdvertisementsModel> call, Throwable t) {

                myPd_ring.dismiss();
            }
        });
    }

    private void allAdds() {

        final ProgressDialog myPd_ring = ProgressDialog.show(getContext(), "Please wait", "", true);

        Endpoints endpoints = RetrofitClient.getLoginClient().create(Endpoints.class);
        Call<AllAdvertisementsModel> call = endpoints.getAllAddsList(VLF_BASE_URL + "advertisements");
        call.enqueue(new Callback<AllAdvertisementsModel>() {
            @Override
            public void onResponse(Call<AllAdvertisementsModel> call, Response<AllAdvertisementsModel> response) {

                if (response.code() == 200) {

                    mAdvertisementsModel = response.body();
                    advertisements = mAdvertisementsModel.getAdvertisements();

                    if (advertisements.size() > 0) {

                        setupRecyclerView(advertisements);

                        myPd_ring.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<AllAdvertisementsModel> call, Throwable t) {

                myPd_ring.dismiss();
            }
        });
    }

    private void getCategoryTypes() {

        final ProgressDialog myPd_ring = ProgressDialog.show(getContext(), "Please wait", "", true);
        try {
            Endpoints endpoints = RetrofitClient.getLoginClient().create(Endpoints.class);
            Call<CategoryListModel> call = endpoints.getCategory(VLF_BASE_URL + "categories");
            call.enqueue(new Callback<CategoryListModel>() {
                @Override
                public void onResponse(Call<CategoryListModel> call, Response<CategoryListModel> response) {

                    try {
                        if (response.code() == 200) {

                            int districtID = 0;
                            String name = "";

                            mCategoryListModel = response.body();
                            mCategoriesArrayList = mCategoryListModel.getCategories();

                            for (categories ct : mCategoriesArrayList) {

                                mImagesArrayList = ct.getImages();
                                name = ct.getName();
                                arrayList2.add(name);
                            }

                            arrayList2.add(0, "Select Category");
                            ArrayAdapter<String> dataAdapter_type = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrayList2);
                            dataAdapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            mSpCategory.setAdapter(dataAdapter_type);

                            mSpCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    category_id = position;

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                            myPd_ring.dismiss();
                        }
                    }catch (Exception g){}

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
        final ProgressDialog myPd_ring = ProgressDialog.show(getContext(), "Please wait", "", true);
        try {

            Endpoints endpoints = RetrofitClient.getLoginClient().create(Endpoints.class);
            Call<RouteModel> call = endpoints.getRouteList(VLF_BASE_URL + "routes?", districtID);
            call.enqueue(new Callback<RouteModel>() {
                @Override
                public void onResponse(Call<RouteModel> call, Response<RouteModel> response) {

                    try {
                        if (response.code() == 200) {

                            mRouteModel = response.body();
                            mRoutesArrayList = mRouteModel.getRoutes();
                            String routeName;

                            for (routes rt : mRoutesArrayList) {

                                routeName = rt.getRoute();
                                arrayList.add(routeName);
                            }

                            arrayList.add(0, "Select Route");
                            ArrayAdapter<String> dataAdapter_type = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrayList);
                            dataAdapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            mSpRoutePlane.setAdapter(dataAdapter_type);
                            myPd_ring.dismiss();

                            mSpRoutePlane.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    rout_id = position;
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        }
                    }catch (Exception g){}
                }

                @Override
                public void onFailure(Call<RouteModel> call, Throwable t) {
                    myPd_ring.dismiss();
                }
            });

        } catch (Exception g) {
        }

    }

    private void setupRecyclerView(ArrayList<advertisements> phoneCallsList) {
        mCustomerAddAdapter = new CustomerAddAdapter(activity, phoneCallsList, this);
        mRvSellerAddList.setHasFixedSize(true);
        mRvSellerAddList.setLayoutManager(new LinearLayoutManager(activity));
        mRvSellerAddList.setAdapter(mCustomerAddAdapter);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.customer_home_fragment_filter:
                linearLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.filter_dialog_sw_date:
            default:
                break;
        }
    }

    @Override
    public void addViewClick(int position, advertisements data) {

        try {

            AddID = data.getId();

            add_dialog = new Dialog(getContext());
            add_dialog.setContentView(R.layout.add_view);
            add_dialog.setTitle("Title");

            TextView mTvSellerName = add_dialog.findViewById(R.id.add_seller_view_name);
            TextView mTvSellerMobile = add_dialog.findViewById(R.id.add_seller_view_mobile);
            TextView mTvCategory = add_dialog.findViewById(R.id.add_seller_view_category);
            TextView mTvToTime = add_dialog.findViewById(R.id.add_seller_view_to_time);
            TextView mTvFromTime = add_dialog.findViewById(R.id.add_seller_view_from_time);
            TextView mTvDescription = add_dialog.findViewById(R.id.add_seller_view_description);
            TextView mTvTitle = add_dialog.findViewById(R.id.add_seller_view_title);
            TextView mTvDistrict = add_dialog.findViewById(R.id.add_seller_view_district);
            TextView mTvRoute = add_dialog.findViewById(R.id.add_seller_view_route);
            TextView mTvVisitDate = add_dialog.findViewById(R.id.add_seller_view_date);

            Button mBtnCreateOrder = add_dialog.findViewById(R.id.add_view_seller_btn_create);
            Button mBtnClose = add_dialog.findViewById(R.id.add_view_seller_btn_close);

            String seller_name = data.getSeller_first_name().toString().trim();
            String seller_phone = data.getSeller_phone().toString().trim();
            String CategoryType = data.getCategory().toString().trim();
            String from_time = data.getFrom_time().toString().trim();
            String to_time = data.getTo_time().toString().trim();
            String description = data.getDescription().toString().trim();
            String title = data.getTitle().toString().trim();
            String route = data.getRoute().toString().trim();
            String mDate = data.getDate().toString().trim();

            mTvSellerName.setText(seller_name+"");
            mTvSellerMobile.setText(seller_phone+"");
            mTvCategory.setText(CategoryType + "");
            mTvToTime.setText(to_time + "");
            mTvFromTime.setText(from_time + "");
            mTvDescription.setText(description + "");
            mTvTitle.setText(title + "");
            mTvRoute.setText(route+"");
            mTvVisitDate.setText(mDate+"");

            mBtnCreateOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getContext(),CreateOrderActivity.class);
                    startActivity(intent);

                }
            });

            mBtnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    add_dialog.dismiss();
                }
            });

            add_dialog.show();

        } catch (Exception g) {
        }
    }
}
