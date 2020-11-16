package com.example.wallpaper.api;

import com.example.wallpaper.model.Collection;
import com.example.wallpaper.model.photo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("photos/")
    Call<List<photo>> getPhotos(@Query("page") int page,@Query("per_page") int integers);

    @GET("collections/{id}/photos")
    Call<List<photo>> getPhotosOfCollection(@Path("id") int id);

    @GET("collections/featured/")
    Call<List<Collection>> getCollections(@Query("client_id") String apiKey);

    @GET("photos/{id}")
    Call<photo> getPhoto(@Path("id") String id);


}
