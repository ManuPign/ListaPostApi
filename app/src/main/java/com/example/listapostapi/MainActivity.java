package com.example.listapostapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private List<Post> listaPost;
    private PostAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Tutorial API con Retrofit");

        listaPost = new ArrayList<Post>();

        obtenerListaApi();

        adapter = new PostAdapter(listaPost);

        listView = findViewById(R.id.listViewPost);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);

    }

    private void obtenerListaApi(){
        // listaPost.add(new Post(0,1,"Titulo de Prueba","Contenido de Prueba"));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService postService = retrofit.create(ApiService.class);
        Call< List<Post> > call = postService.getPost();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                for(Post post :  response.body()) {
                    listaPost.add(post);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(this, "Seleccionó ID: "+listaPost.get(position).getId(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(MainActivity.this, DetalleActivity.class);
        intent.putExtra("KEY_ID", listaPost.get(position).getId());
        startActivity(intent);
    }
}
