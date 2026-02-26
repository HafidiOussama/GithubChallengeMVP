package com.example.githubchallengemvp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.githubchallengemvp.data.model.Repository;
import com.example.githubchallengemvp.presenter.MainContract;
import com.example.githubchallengemvp.presenter.MainPresenter;
import com.example.githubchallengemvp.ui.adapter.RepositoryAdapter;
import com.example.githubchallengemvp.databinding.ActivityMainBinding;

import java.util.List;
import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private ActivityMainBinding binding; // ViewBinding
    private RepositoryAdapter adapter;

    @Inject
    MainPresenter presenter;

    private int currentPage = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private static final int PAGE_SIZE = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialiser ViewBinding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(layoutManager);

        adapter = new RepositoryAdapter();
        binding.recyclerView.setAdapter(adapter);

        // Inject presenter
        ((MyApplication) getApplication())
                .getAppComponent()
                .inject(this);

        presenter.attachView(this);

        // Dark/Light mode button
        updateButtonIcon();
        binding.btnDarkMode.setOnClickListener(v -> {


                int currentMode = getResources().getConfiguration().uiMode
                        & android.content.res.Configuration.UI_MODE_NIGHT_MASK;

                if (currentMode == android.content.res.Configuration.UI_MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }

                binding.btnDarkMode.postDelayed(this::updateButtonIcon, 100);

        });

        // Load first page
        loadPage(1);

        // Infinite scroll
        binding.recyclerView.addOnScrollListener(new androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(androidx.recyclerview.widget.RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy <= 0) return;

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!isLoading && !isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0) {

                        currentPage++;
                        adapter.addLoadingFooter(); // loader bas
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

    private void updateButtonIcon() {

        int currentMode = getResources().getConfiguration().uiMode
                & android.content.res.Configuration.UI_MODE_NIGHT_MASK;

        if (currentMode == android.content.res.Configuration.UI_MODE_NIGHT_YES) {

            // Si Dark Mode actif → afficher icône soleil (white mode)
            binding.btnDarkMode.setImageResource(R.drawable.ic_light_mode);

        } else {

            // Si White Mode actif → afficher icône lune (dark mode)
            binding.btnDarkMode.setImageResource(R.drawable.ic_dark_mode);
        }
    }

    // MVP View methods
    @Override
    public void showLoading() {
        if (currentPage == 1) {
            binding.progressBar.setVisibility(android.view.View.VISIBLE);
        }
    }

    @Override
    public void hideLoading() {
        binding.progressBar.setVisibility(android.view.View.GONE);
    }

    @Override
    public void showRepositories(List<Repository> repositories) {
        if (currentPage == 1) {
            adapter.setData(repositories);
        } else {
            adapter.removeLoadingFooter();
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