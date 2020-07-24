package com.example.wallpaper.Fragments;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.wallpaper.Adapters.PhotoAdapter;
import com.example.wallpaper.R;
import com.example.wallpaper.Utils.functions;
import com.example.wallpaper.api.ApiService;
import com.example.wallpaper.api.Client;
import com.example.wallpaper.databinding.FragmentPhotosBinding;
import com.example.wallpaper.model.photo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class PhotosFragment extends Fragment   {
    public PhotosFragment() {}
    private static PhotoAdapter adapter;
    private static RecyclerView recyclerView;
    public static List<photo> photos;
    static ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentPhotosBinding binding=FragmentPhotosBinding.inflate(getLayoutInflater());
        View root=binding.getRoot();
        photos=new ArrayList<>();
        recyclerView=binding.photosRecyclerView;
        progressBar=binding.photosProgress;
        adapter= new PhotoAdapter(getContext(),photos);
        if(getActivity().getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE)
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        else
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
        recyclerView.setAdapter(adapter);
        showProgress(true);
        getPhotos();
        return root;

    }
    public static void getPhotos()
    {
     Retrofit retrofit = Client.getClient();
     ApiService apiService=retrofit.create(ApiService.class);
        Call <List<photo>> call=apiService.getPhotos();
        call.enqueue(new Callback<List<photo>>() {
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
}}