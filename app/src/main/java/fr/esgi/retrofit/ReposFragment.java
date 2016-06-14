package fr.esgi.retrofit;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReposFragment extends Fragment {
    GitHubService service;
    @BindView(R.id.swipeRefreshRepos)
    SwipeRefreshLayout refreshRepos;
    @BindView(R.id.rv_repos)
    RecyclerView rvRepos;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_repos, container, false);

        ButterKnife.bind(this, rootView);
        this.initView();

        return rootView;
    }

    public static ReposFragment newInstance(User user) {
        ReposFragment myFragment = new ReposFragment();

        Bundle args = new Bundle();
        args.putParcelable("USER", user);
        myFragment.setArguments(args);

        return myFragment;
    }

    private void initView() {
        service = GithubWebService.get();
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rvRepos.setLayoutManager(llm);
        refreshRepos.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRepos();
            }
        });
        getRepos();

    }

    private void getRepos (){
        Bundle args = getArguments();
        User user = args.getParcelable("USER");
        if (user != null) {
            service.getUsersRepos(user.getLogin()).enqueue(new Callback<ArrayList<Repo>>() {
                @Override
                public void onResponse(Call<ArrayList<Repo>> call, Response<ArrayList<Repo>> response) {
                    if (response.code() == 200) {
                        ArrayList<Repo> repos = response.body();
                        rvRepos.setAdapter(new ReposAdapter(repos, getContext()));
                    }else
                        System.out.println("/!\\ Error /!\\ : " + response.code());
                    if (refreshRepos.isRefreshing())
                        refreshRepos.setRefreshing(false);
                }

                @Override
                public void onFailure(Call<ArrayList<Repo>> call, Throwable t) {
                    Log.e("retrofit", "failure" + t);
                    if (refreshRepos.isRefreshing())
                        refreshRepos.setRefreshing(false);
                }
            });
        }
    }

}
