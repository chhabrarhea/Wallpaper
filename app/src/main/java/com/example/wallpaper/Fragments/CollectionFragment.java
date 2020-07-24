package com.example.wallpaper.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.wallpaper.Adapters.CollectionAdapter;
import com.example.wallpaper.R;
import com.example.wallpaper.Utils.functions;
import com.example.wallpaper.api.ApiService;
import com.example.wallpaper.api.Client;
import com.example.wallpaper.databinding.FragmentCollectionBinding;
import com.example.wallpaper.model.Collection;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class CollectionFragment extends Fragment {
GridView gridView;
ProgressBar progressBar;
CollectionAdapter adapter;
List<Collection> collections;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentCollectionBinding binding=FragmentCollectionBinding.inflate(getLayoutInflater());
        View root=binding.getRoot();
        gridView=binding.collectionsGridview;
        progressBar=binding.collectionsProgressbar;
        collections=new ArrayList<>();
        adapter=new CollectionAdapter(getContext(),collections);
        gridView.setAdapter(adapter);
        getCollection();
        showProgressBar(true);
        return root;
    }
    private void getCollection(){
        ApiService apiInterface = Client.getClient().create(ApiService.class);
        Call<List<Collection>> call = apiInterface.getCollections(functions.apiKey);
        call.enqueue(new Callback<List<Collection>>() {
            @Override
            public void onResponse(Call<List<Collection>> call, Response<List<Collection>> response) {
                if(response.isSuccessful()){
                    for(Collection collection: response.body()){
                        collections.add(collection);
                    }
                    adapter.notifyDataSetChanged();
                    Log.d(TAG, "size " + collections.size());
                }else{
                    Log.d(TAG, "fail " + response.message());
                }
                showProgressBar(false);
            }

            @Override
            public void onFailure(Call<List<Collection>> call, Throwable t) {
                Log.d(TAG, t.getMessage());
                showProgressBar(false);
            }
        });
    }
    private void showProgressBar(boolean isShow){
        if(isShow){
            progressBar.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.GONE);
        }else{
            progressBar.setVisibility(View.GONE);
            gridView.setVisibility(View.VISIBLE);
        }
    }


}