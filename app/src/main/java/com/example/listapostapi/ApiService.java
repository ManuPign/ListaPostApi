package com.example.listapostapi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("/posts")
    Call<List<Post>> getPost();

    @GET("/posts/{id}")
    Call<Post> getPostById(@Path("id") int postId);
}
