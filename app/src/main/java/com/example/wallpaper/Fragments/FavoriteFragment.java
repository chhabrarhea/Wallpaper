package com.example.wallpaper.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.wallpaper.Adapters.PhotoAdapter;
import com.example.wallpaper.R;
import com.example.wallpaper.databinding.FragmentFavoriteBinding;
import com.example.wallpaper.model.RealmController;
import com.example.wallpaper.model.photo;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {
List<photo> favPhotos;
PhotoAdapter favAdapter;
RecyclerView recyclerView;
FragmentFavoriteBinding binding;
    public FavoriteFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        binding=FragmentFavoriteBinding.inflate(getLayoutInflater());
        View root=binding.getRoot();
        recyclerView=binding.fragmentFavoriteRecyclerview;
        favPhotos=new ArrayList<>();
        favAdapter=new PhotoAdapter(getActivity(),favPhotos);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(favAdapter);
        getFavPhotos();
        return root;
    }

    public void getFavPhotos(){
        RealmController realmController=new RealmController();
        favPhotos.addAll(realmController.allPhotos());
        favAdapter.notifyDataSetChanged();
        if(favPhotos.size()==0)
        {
            Toast.makeText(getContext(), "No Favorites Found!:(", Toast.LENGTH_LONG).show();
            recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding=null;
    }
}