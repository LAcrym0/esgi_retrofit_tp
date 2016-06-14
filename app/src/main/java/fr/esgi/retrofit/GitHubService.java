package fr.esgi.retrofit;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface GitHubService {

    String END_POINT = "https://api.github.com";
    String TOKEN = "b9a87dad4f4982ff84e5ef454d68de729e9e4e4d";

    @Headers("Authorization: token " + TOKEN)
    @GET("/users/{username}")
    Call<User> getUser(@Path("username") String username);

    @Headers("Authorization: token " + TOKEN)
    @GET("/users/{username}/repos")
    Call<ArrayList<Repo>> getUsersRepos(@Path("username") String username);


}
