package com.example.firebaseproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapterRecycle extends RecyclerView.Adapter<ImageAdapterRecycle.CustomViewHolder> {
    private Context context;
    private List<UploadImage> uploadImageList;

    public ImageAdapterRecycle(Context context, List<UploadImage> uploadImageList) {
        this.context = context;
        this.uploadImageList = uploadImageList;
    }

    public ImageAdapterRecycle() {
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.imagecard_view,parent,false);

        return new CustomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.imageName.setText(uploadImageList.get(position).getImageName());
        Picasso.get().load(uploadImageList.get(position).getImageUrl()).into(holder.imageView_recycle);

    }

    @Override
    public int getItemCount() {
        return uploadImageList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
      private   ImageView imageView_recycle;
      private   TextView imageName;
        public CustomViewHolder(@NonNull View itemView) {

            super(itemView);
         imageView_recycle =   itemView.findViewById(R.id.imageView_recycle);
          imageName =  itemView.findViewById(R.id.image_name);
        }
    }
}
