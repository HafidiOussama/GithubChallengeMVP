package com.example.githubchallengemvp;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.githubchallengemvp.R;
import com.example.githubchallengemvp.data.model.Repository;
//import com.example.githubchallengemvp.data.remote.ApiClient;
import com.example.githubchallengemvp.data.remote.ApiService;
import com.example.githubchallengemvp.data.repository.GithubRepository;
import com.example.githubchallengemvp.presenter.MainContract;
import com.example.githubchallengemvp.presenter.MainPresenter;
import com.example.githubchallengemvp.ui.adapter.RepositoryAdapter;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private RepositoryAdapter adapter;
//    private MainPresenter presenter;

    @Inject
    MainPresenter presenter;

    // Pagination variables
    private int currentPage = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private static final int PAGE_SIZE = 30;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        adapter = new RepositoryAdapter();
        recyclerView.setAdapter(adapter);

        /// ///
        ((MyApplication) getApplication())
                .getAppComponent()
                .inject(this);

        presenter.attachView(this);





//        // Init Retrofit
//        ApiService apiService =
//                ApiClient.getRetrofit()
//                        .create(ApiService.class);
//
//        GithubRepository repository =
//                new GithubRepository(apiService);
//
//        presenter =
//                new MainPresenter(this, repository);




        // Load first page
        loadPage(1);

        // Scroll listener
        recyclerView.addOnScrollListener(
                new RecyclerView.OnScrollListener() {

                    @Override
                    public void onScrolled(
                            RecyclerView recyclerView,
                            int dx,
                            int dy) {

                        super.onScrolled(recyclerView, dx, dy);

                        if (dy <= 0) return;

                        int visibleItemCount =
                                layoutManager.getChildCount();

                        int totalItemCount =
                                layoutManager.getItemCount();

                        int firstVisibleItemPosition =
                                layoutManager
                                        .findFirstVisibleItemPosition();

                        if (!isLoading && !isLastPage) {

                            if ((visibleItemCount
                                    + firstVisibleItemPosition)
                                    >= totalItemCount
                                    && firstVisibleItemPosition >= 0) {

                                currentPage++;
                                loadPage(currentPage);
                            }
                        }
                    }
                });
    }

    private void loadPage(int page) {

        if (isLoading || isLastPage) return;

        isLoading = true;
        presenter.loadRepositories(page);
    }

    // -------------------------
    // MVP View methods
    // -------------------------

    @Override
    public void showLoading() {

        if (currentPage == 1)
            progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {

        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showRepositories(List<Repository> repositories) {

        if (currentPage == 1) {
            adapter.setData(repositories);
        } else {
            adapter.addData(repositories);
        }

        if (repositories.size() < PAGE_SIZE) {
            isLastPage = true;
        }

        isLoading = false;
    }


    @Override
    public void showError(String message) {

        isLoading = false;
    }
}
