package com.example.firebaseproject;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapterRecycle extends RecyclerView.Adapter<ImageAdapterRecycle.CustomViewHolder> {
    private Context context;
    private List<UploadImage> uploadImageList;
    private OnItemRecycleImageListener onItemRecycleImageListener ;

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

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
      private   ImageView imageView_recycle;
      private   TextView imageName;
        public CustomViewHolder(@NonNull View itemView) {

            super(itemView);
         imageView_recycle =   itemView.findViewById(R.id.imageView_recycle);
          imageName =  itemView.findViewById(R.id.image_name);
          itemView.setOnClickListener(this);
          itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
         if(onItemRecycleImageListener!=null){
             int position = getAdapterPosition();
             if(position !=RecyclerView.NO_POSITION){
                 onItemRecycleImageListener.onItemClickImage(position);
             }
         }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle(" CHOOSE");
            menu.setHeaderIcon(R.drawable.menuopen);
            MenuItem viewImageWithInfo = menu.add(Menu.NONE,1,1," View Image Info");
            MenuItem deleteImage = menu.add(Menu.NONE,2,2," Delete Image ");
            MenuItem markImportant = menu.add(Menu.NONE,3,3," Mark for important view");


            viewImageWithInfo.setOnMenuItemClickListener(this);
            deleteImage.setOnMenuItemClickListener(this);
            markImportant.setOnMenuItemClickListener(this);

        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if(onItemRecycleImageListener!=null){
                int position = getAdapterPosition();
                if(position !=RecyclerView.NO_POSITION){
                    if(item.getItemId() == 1){
                             onItemRecycleImageListener.onViewImageItem(position);
                    }
                    if(item.getItemId() == 2){
                             onItemRecycleImageListener.onDeleteItem(position);
                    }
                    if(item.getItemId() == 3){
                            onItemRecycleImageListener.onMarkImportant(position);
                    }


                }
            }
            return false;
        }
    }


    public interface OnItemRecycleImageListener{
        void onItemClickImage(int position);
        void onViewImageItem(int position);
        void onDeleteItem (int position);
        void onMarkImportant(int position);
    }
    public void  itemRecycleClick(OnItemRecycleImageListener onItemRecycleImageListener){
        this.onItemRecycleImageListener = onItemRecycleImageListener;
    }






}

