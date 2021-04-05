package com.dev.hasarelm.buyingselling.Common;

import com.dev.hasarelm.buyingselling.Model.AddModel;
import com.dev.hasarelm.buyingselling.Model.AllAdvertisementsModel;
import com.dev.hasarelm.buyingselling.Model.CategoryListModel;
import com.dev.hasarelm.buyingselling.Model.CustomerRegisterModel;
import com.dev.hasarelm.buyingselling.Model.DeleteSellerAdd;
import com.dev.hasarelm.buyingselling.Model.DistrictsModel;
import com.dev.hasarelm.buyingselling.Model.ForgetPasswordModel;
import com.dev.hasarelm.buyingselling.Model.NotificationModel;
import com.dev.hasarelm.buyingselling.Model.OrderList;
import com.dev.hasarelm.buyingselling.Model.OrderUpdates;
import com.dev.hasarelm.buyingselling.Model.ProfileUpdates;
import com.dev.hasarelm.buyingselling.Model.RouteModel;
import com.dev.hasarelm.buyingselling.Model.UserDetails;
import com.dev.hasarelm.buyingselling.Model.UserLogin;
import com.dev.hasarelm.buyingselling.Model.VehicleTypeModel;
import com.dev.hasarelm.buyingselling.Model.createOrder;
import com.dev.hasarelm.buyingselling.Model.orderCreate;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
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

    //forget password method
    @Headers({"Content-Type: application/json"})
    @POST
    Call<ForgetPasswordModel> forgetPassword(@Url String Url , @Query("email") String email);

    //route get method
    @Headers({"Content-Type: application/json"})
    @GET
    Call<RouteModel> getRouteList(@Url String Url, @Query("district_id") int district_id);

    //route get method
    @Headers({"Content-Type: application/json"})
    @GET
    Call<CategoryListModel> getCategory(@Url String Url);

    //route get method
    @Headers({"Content-Type: application/json"})
    @POST
    Call<AddModel> createAdd(@Url String Url,@Body RequestBody cartList);

    @Headers({"Content-Type: application/json"})
    @GET
    Call<AllAdvertisementsModel> getAllAddsList(@Url String Url);

    @Headers({"Content-Type: application/json"})
    @GET
    Call<AllAdvertisementsModel> getAddFilter(@Url String Url,@Query("route_id") int route_id,@Query("category_id")int category_id);

    @Headers({"Content-Type: application/json"})
    @PUT
    Call<ProfileUpdates> profileUpdate(@Url String Url , @Body RequestBody list);

    @Headers({"Content-Type: application/json"})
    @GET
    Call<UserDetails> getUserDetails(@Url String Url, @Query("id") int id);

    @Headers({"Content-Type: application/json"})
    @GET
    Call<AllAdvertisementsModel> getSellerAdds(@Url String Url,@Query("seller_id") int seller_id);

    @Headers({"Content-Type: application/json"})
    @DELETE
    Call<DeleteSellerAdd> deleteSellerAdds(@Url String Url, @Query("id") int id);

    //route get method
    @Headers({"Content-Type: application/json"})
    @POST
    Call<createOrder> createOrder(@Url String Url, @Body RequestBody cartList);

    @Headers({"Content-Type: application/json"})
    @GET
    Call<OrderList> getAllOrders(@Url String Url, @Query("customer_id") int customer_id,@Query("status") int status);

    @Headers({"Content-Type: application/json"})
    @GET
    Call<OrderList> getAll(@Url String Url,@Query("status") int status);

    @Headers({"Content-Type: application/json"})
    @PUT
    Call<OrderUpdates> orderStatusUpdate(@Url String Url , @Query("id") int id,@Query("status") int status);

    @Headers({"Content-Type: application/json"})
    @GET
    Call<OrderList> getOrderHistory(@Url String Url, @Query("seller_id") int seller_id,@Query("status") int status);

    @Headers({"Content-Type: application/json"})
    @GET
    Call<NotificationModel> getNotification(@Url String Url,@Query("user_id") int user_id);

    @Headers({"Content-Type: application/json"})
    @PUT
    Call<NotificationModel> updateClickNotification(@Url String Url, @Query("id") int id);
}
