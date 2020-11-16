package com.example.wallpaper.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.wallpaper.Adapters.PhotoAdapter;
import com.example.wallpaper.R;
import com.example.wallpaper.api.ApiService;
import com.example.wallpaper.api.Client;
import com.example.wallpaper.databinding.ActivityCollectionBinding;
import com.example.wallpaper.model.photo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CollectionActivity extends AppCompatActivity {
ActivityCollectionBinding binding;
    private static PhotoAdapter adapter;
    private static RecyclerView recyclerView;
    public static List<photo> photos;
    static ProgressBar progressBar;
    Toolbar toolbar;
int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCollectionBinding.inflate(getLayoutInflater());
        View root=binding.getRoot();
        setContentView(root);
        photos=new ArrayList<>();
        toolbar=binding.Maintoolbar;
        toolbar.setTitle(getIntent().getStringExtra("name"));
        recyclerView=binding.recycle;
        progressBar=binding.progress;
        adapter= new PhotoAdapter(this,photos);
        if(this.getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE)
            recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        else
            recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        recyclerView.setAdapter(adapter);
        showProgress(true);
        id=getIntent().getIntExtra("id",-1);
        ApiService service= Client.getClient().create(ApiService.class);
        service.getPhotosOfCollection(id).enqueue(new Callback<List<photo>>() {
            @Override
            public void onResponse(Call<List<photo>> call, Response<List<photo>> response) {
                if(response.isSuccessful()){
                    photos.addAll((Collection<? extends photo>) response.body());
                    adapter.notifyDataSetChanged();

                }
                else
                    Log.i("photo response",""+response.message());
                showProgress(false);
            }

            @Override
            public void onFailure(Call<List<photo>> call, Throwable t) {
                Log.i("photo response",""+t.getMessage());
                showProgress(true);
            }
        });

    }
    public static void showProgress(boolean isLoading)
    {
        if(isLoading)
        {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
        else
        {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}