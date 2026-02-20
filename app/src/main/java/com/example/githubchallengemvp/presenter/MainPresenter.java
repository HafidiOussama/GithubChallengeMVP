package com.example.githubchallengemvp.presenter;

import com.example.githubchallengemvp.data.model.ApiResponse;
import com.example.githubchallengemvp.data.model.Repository;
import com.example.githubchallengemvp.data.repository.GithubRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;
    private GithubRepository repository;

    public MainPresenter(
            MainContract.View view,
            GithubRepository repository
    ) {

        this.view = view;
        this.repository = repository;
    }

    @Override
    public void loadRepositories(int page) {

        view.showLoading();

        repository.getRepositories(page,
                new Callback<ApiResponse>() {

                    @Override
                    public void onResponse(
                            Call<ApiResponse> call,
                            Response<ApiResponse> response) {

                        view.hideLoading();

                        if (response.isSuccessful()) {

                            List<Repository> list =
                                    response.body().getItems();

                            view.showRepositories(list);
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<ApiResponse> call,
                            Throwable t) {

                        view.hideLoading();
                        view.showError(t.getMessage());
                    }
                });
    }
}
