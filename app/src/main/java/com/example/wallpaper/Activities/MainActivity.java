package com.example.wallpaper.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.wallpaper.Fragments.CollectionFragment;
import com.example.wallpaper.Fragments.FavoriteFragment;
import com.example.wallpaper.Fragments.PhotosFragment;
import com.example.wallpaper.R;
import com.example.wallpaper.Utils.functions;
import com.example.wallpaper.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
DrawerLayout drawerLayout;
private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(R.layout.activity_main);
         drawerLayout=findViewById(R.id.drawer);

          Toolbar toolbar=findViewById(R.id.Maintoolbar);
          toolbar.setTitle("124");
        setSupportActionBar(toolbar);
         getSupportActionBar().setHomeButtonEnabled(true);
         ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        functions.changeFragment(MainActivity.this,new PhotosFragment());
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }


    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_fav:
                 functions.changeFragment(MainActivity.this,new FavoriteFragment());
                break;
            case R.id.nav_photos:
                 functions.changeFragment(MainActivity.this,new PhotosFragment());
                 break;
            case R.id.nav_gallery:
                functions.changeFragment(MainActivity.this,new CollectionFragment());
                break; }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
