package com.dev.hasarelm.buyingselling.Common;

import com.dev.hasarelm.buyingselling.Model.CustomerRegisterModel;
import com.dev.hasarelm.buyingselling.Model.DistrictsModel;
import com.dev.hasarelm.buyingselling.Model.UserLogin;
import com.dev.hasarelm.buyingselling.Model.VehicleTypeModel;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface Endpoints {

    //customer register method
    @Headers({"Content-Type: application/json"})
    @POST
    Call<CustomerRegisterModel> customerRegister(@Url String Url , @Body RequestBody cartList);

    //customer register method
    @Headers({"Content-Type: application/json"})
    @POST
    Call<UserLogin> userLogin(@Url String Url , @Body RequestBody cartList);

    //district get method
    @Headers({"Content-Type: application/json"})
    @GET
    Call<DistrictsModel> getDistrict(@Url String Url);

    //district get method
    @Headers({"Content-Type: application/json"})
    @GET
    Call<VehicleTypeModel> getVehicleType(@Url String Url);
}
