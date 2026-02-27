package com.example.githubchallengemvp.data.repository;

import com.example.githubchallengemvp.data.model.ApiResponse;
import com.example.githubchallengemvp.data.remote.ApiService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;



public class GithubRepository {

    private ApiService apiService;

    public GithubRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public void getRepositories(
            int page,
            Callback<ApiResponse> callback
    ) {

        // 1. Obtenir la date actuelle
        Calendar calendar = Calendar.getInstance();

        // 2. Soustraire 30 jours
        calendar.add(Calendar.DAY_OF_YEAR, -30);

        // 3. Convertir en format yyyy-MM-dd
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String date30DaysAgo = sdf.format(calendar.getTime());

        // 4. Appel Retrofit avec la date dynamique
        Call<ApiResponse> call =
                apiService.getRepositories(
                        "created:>" + date30DaysAgo,
                        "stars",
                        "desc",  
                        page,
                        30

                );

        // 5. Exécuter la requête
        call.enqueue(callback);


    }


}
