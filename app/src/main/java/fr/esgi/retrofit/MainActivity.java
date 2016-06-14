package fr.esgi.retrofit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.username) TextView username;
    @BindView(R.id.button_valid) AppCompatButton btValid;
    @BindView(R.id.progress)
    ProgressBar progress;

    SharedPreferences sp;

    GitHubService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        sp = getApplicationContext().getSharedPreferences("github_app", Context.MODE_PRIVATE);
        username.setText(sp.getString(getString(R.string.username), ""));

        service = GithubWebService.get();
    }

    protected void loadUser(String name){
        service.getUser(name).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    User user = response.body();
                    Session.getInstance().setCurrentUser(user);
                    System.out.println(user.getName());
                    System.out.println(user.getLogin());
                    System.out.println(user.getAvatarUrl());
                    goToNextActivity(user);
                }else
                    System.out.println("/!\\ Error /!\\ : " + response.code());
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("retrofit", "failure" + t);
                progress.setVisibility(View.GONE);
            }
        });
    }

    @OnClick(R.id.button_valid)
    public void load(View view) {
        progress.setVisibility(View.VISIBLE);
        String name = username.getText().toString();
        saveUser(name);
        loadUser(name);
    }

    public void saveUser(String name){
        sp.edit().putString(getString(R.string.username), name).apply();
    }

    protected void goToNextActivity(User user){
        System.out.println("Passage2");
        Intent intent = new Intent(MainActivity.this, NavigationDrawerActivity.class);
        Bundle bundleUser = new Bundle();
        bundleUser.putParcelable(getString(R.string.user), user);
        intent.putExtra(getString(R.string.user), bundleUser);
        startActivity(intent);
    }
}