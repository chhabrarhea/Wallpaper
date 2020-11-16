package com.example.wallpaper.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.content.Intent;
import com.bumptech.glide.Glide;
import com.example.wallpaper.Activities.CollectionActivity;
import com.example.wallpaper.R;
import com.example.wallpaper.Utils.SquareImage;
import com.example.wallpaper.databinding.CollectionsItemBinding;
import com.example.wallpaper.model.Collection;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class CollectionAdapter extends BaseAdapter {
    private List<Collection> collections;
    private Context context;
    public CollectionAdapter(Context context, List<Collection> collections){
        this.collections = collections;
        this.context = context;
    }

    @Override
    public int getCount() {
        return collections.size();
    }

    @Override
    public Object getItem(int i) {
        return collections.get(i);
    }

    @Override
    public long getItemId(int i) {
        return collections.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.collections_item, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        final Collection collection = (Collection) collections.get(i);
        CollectionsItemBinding binding=CollectionsItemBinding.bind(view);
        if(collection.getTitle() != null){
            Log.d("Title", collection.getTitle());
            holder.title.setText(collection.getTitle());

        }
        holder.collectionPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, CollectionActivity.class);
                intent.putExtra("id",collection.getId());
                intent.putExtra("name",collection.getTitle());
                context.startActivity(intent);
            }
        });
        holder.totalPhotos.setText(String.valueOf(collection.getTotalPhotos()) + " photos");
        Glide
                .with(context)
                .load(collection.getCoverPhoto().getUrl().getRegular())
                .into(holder.collectionPhoto);
        return view;

    }
    static class ViewHolder{
        SquareImage collectionPhoto;
        TextView title;
        TextView totalPhotos;
        public ViewHolder(View view) {
            CollectionsItemBinding binding=CollectionsItemBinding.bind(view);
            collectionPhoto=binding.itemCollectionPhoto;
            title=binding.itemCollectionTitle;
            totalPhotos=binding.itemCollectionTotalPhotos;
        }
    }
}
