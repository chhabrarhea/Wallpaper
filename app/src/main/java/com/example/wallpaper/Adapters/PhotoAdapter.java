package com.example.wallpaper.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.content.Intent;
import com.bumptech.glide.Glide;
import com.example.wallpaper.Activities.FullScreenActivity;
import com.example.wallpaper.R;
import com.example.wallpaper.Utils.SquareImage;
import com.example.wallpaper.databinding.PhotosItemBinding;
import com.example.wallpaper.model.photo;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

    public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.viewHolder>   {
    private final String TAG = PhotoAdapter.class.getSimpleName();
    private List<photo> photos;
    private Context context;
    public PhotoAdapter(Context context, List<photo> photos){
        this.photos = photos;
        this.context = context;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item= LayoutInflater.from(parent.getContext()).inflate(R.layout.photos_item,parent,false);
        return new viewHolder(item);
    }
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        photo photo = photos.get(position);
        holder.username.setText(photo.getUser().getUsername());
        Glide
                .with(context)
                .load(photo.getUrl().getRegular())
                .into(holder.image);
        Glide
                .with(context)
                .load(photo.getUser().getProfileImage().getSmall())
                .into(holder.dp);
    }
    @Override
    public int getItemCount() {
        return photos.size();
    }
    public class viewHolder extends RecyclerView.ViewHolder{
        PhotosItemBinding binding;
        TextView username;
        SquareImage image;
        CircleImageView dp;
        FrameLayout frame;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=PhotosItemBinding.bind(itemView);
            username=binding.photoItemUserName;
            image=binding.photoItemPhoto;
            dp=binding.photoItemUserAvatar;
            frame=binding.photoItemFrame;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos=getAdapterPosition();
                    String photoId = photos.get(pos).getId();
                    Intent intent = new Intent(context, FullScreenActivity.class);
                    intent.putExtra("photoId", photoId);
                    Log.i("photo",""+photoId);
                    context.startActivity(intent);
                }
            });
            binding.bind(itemView);
        }
    }
}
