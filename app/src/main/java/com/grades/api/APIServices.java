package com.grades.api;


import com.grades.model.Classes;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.mime.TypedInput;

public interface APIServices {





    @Headers({"Content-Type: application/json"})
    @GET("/m/api/MobileWebAPI.asmx/GetGradebookSummaryData")
    void getClasses(@Header("Cookie") String sessionIdAndToken,Callback<Classes> callback);


    @GET("/LoginParent.aspx")
    void login(Callback<String>callback);


    @GET("/m/api/MobileWebAPI.asmx/IsUserLoggedIn")
    void getUserLoggedIn(Callback<String>callback);


    @POST("/LoginParent.aspx")
    void loginApi(@Body TypedInput body, Callback<String>callback);

    @POST("/m/api/MobileWebAPI.asmx/GetGradebookDetailedSummaryData")
    void getGradebookDetailedSummaryData(@Body TypedInput body, Callback<String>callback);




}