package com.example.wallpaper.Activities;

import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.wallpaper.R;
import com.example.wallpaper.Utils.functions;
import com.example.wallpaper.api.ApiService;
import com.example.wallpaper.api.Client;
import com.example.wallpaper.databinding.ActivityFullScreenBinding;
import com.example.wallpaper.model.RealmController;
import com.example.wallpaper.model.photo;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.io.IOException;

public class FullScreenActivity extends AppCompatActivity {
    ActivityFullScreenBinding binding;
    TextView username;
    CircleImageView avatar;
    ImageView fullscreenPhoto;
    FloatingActionMenu fabMenu;
    FloatingActionButton fav;
    FloatingActionButton wallpaper;
    private Bitmap photoBitmap;
    photo p;
    String id;
    private RealmController realmController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityFullScreenBinding.inflate(getLayoutInflater());
        View root=binding.getRoot();
        setContentView(root);
        Intent intent=getIntent();
        id=intent.getStringExtra("photoId");
        username=binding.activityFullscreenPhotoUsername;
        avatar=binding.activityFullscreenPhotoUserAvatar;
        fullscreenPhoto=binding.activityFullscreenPhotoPhoto;
        fabMenu=binding.activityFullscreenPhotoFabMenu;
        fav=binding.activityFullscreenPhotoFabFavorite;
        wallpaper=binding.activityFullscreenPhotoFabSetWallpaper;
        p = new photo();
        realmController = new RealmController();
        if(realmController.isPhotoSaved(id)){
            fav.setImageDrawable(getResources().getDrawable(R.drawable.fav_filled));
        }

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getPhoto(id);
        wallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(photoBitmap != null){
                    if(functions.setWallpaper(FullScreenActivity.this, photoBitmap)){
                        Toast.makeText(FullScreenActivity.this, "Set Wallpaper successfully", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(FullScreenActivity.this, "Set Wallpaper fail", Toast.LENGTH_LONG).show();
                    }
                }
                fabMenu.close(true);
            }

        });

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(realmController.isPhotoSaved(p.getId())){
                    realmController.deletePhoto(p);
                    fav.setImageDrawable(getResources().getDrawable(R.drawable.fav_border));
                    Toast.makeText(FullScreenActivity.this, "Removed from Favorites", Toast.LENGTH_SHORT).show();
                }else{
                    realmController.savePhoto(p);
                    fav.setImageDrawable(getResources().getDrawable(R.drawable.fav_filled));
                    Toast.makeText(FullScreenActivity.this, "Added to favorites", Toast.LENGTH_SHORT).show();
                }
                fabMenu.close(true);
            }
        });

    }

    public void getPhoto(String id)
    {
        Log.i("photo",""+id);
        ApiService apiService= Client.getClient().create(ApiService.class);
        apiService.getPhoto(id).enqueue(new Callback<photo>() {
            @Override
            public void onResponse(Call<photo> call, Response<photo> response) {

                if(response.isSuccessful()){
                    Log.i("FullScreenActivity", "success");
                    p= response.body();

                    updateUI(p);
                }else{
                    try {
                        Log.i("FSA response failure:", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<photo> call, Throwable t) {
                Log.i("FSA connection failure: ", t.getMessage());
            }
        });
    }
    private void updateUI(final photo ph){
        // Make sure that, if we have some errors here, our application will not crash
        Log.i("update",""+ph.getId());
        try{
            username.setText(ph.getUser().getUsername());
            Glide.with(FullScreenActivity.this)
                    .load(ph.getUser().getProfileImage().getSmall())
                    .into(avatar);

            Glide
                    .with(FullScreenActivity.this)
                    .asBitmap()
                    .load(ph.getUrl().getRegular())
                    .centerCrop()
                    .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            fullscreenPhoto.setImageBitmap(resource);
                            photoBitmap = resource;
                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}