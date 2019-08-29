package com.example.listapostapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetalleActivity extends AppCompatActivity {

    private TextView textViewTituloDetalle, textViewContenidoDetalle;
    private ImageView imageViewFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        Bundle extras = getIntent().getExtras();
        int postId = extras.getInt("KEY_ID");

        findViewsById();

        cargarPost(postId);

    }

    private void findViewsById(){
        textViewTituloDetalle = findViewById(R.id.textViewTituloDetalle);
        textViewContenidoDetalle = findViewById(R.id.textViewContenidoDetalle);
        // imageViewFoto = findViewById(R.id.imageViewFoto);

    }

    private void cargarPost(int id){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService postService = retrofit.create(ApiService.class);
        Call<Post> call = postService.getPostById(id);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Post post = (Post) response.body();
                textViewTituloDetalle.setText(post.getTitle());
                textViewContenidoDetalle.setText(post.getBody());
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
            }
        });
    }

}
