package com.example.githubchallengemvp.data.remote;
import com.example.githubchallengemvp.data.model.ApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface ApiService {
// APPELS HTTP
    @GET("search/repositories")
    Call<ApiResponse> getRepositories(

            @Query("q") String query,

            @Query("sort") String sort,

            @Query("order") String order,

            @Query("page") int page,

            @Query("per_page") int perPage
    );

}
