package com.example.as_c4_sp1_retrofit_task1;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonApi
{
    @GET("all")
    Call<List<Country>> getPosts();
}
