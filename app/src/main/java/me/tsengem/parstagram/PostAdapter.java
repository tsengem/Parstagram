package me.tsengem.parstagram;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import me.tsengem.parstagram.model.Post;
import com.bumptech.glide.Glide;
import com.parse.ParseException;
import com.parse.ParseFile;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> mPosts;
    Context context;

    public PostAdapter(List<Post> posts) {
        mPosts = posts;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.activity_item_post, parent, false);
        ViewHolder viewHolder = new ViewHolder(postView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = mPosts.get(position);

        ParseFile file = post.getImage();

        holder.tvCaption.setText(post.getDescription());
        //holder.ivProfileImage.setImageBitmap(file.getData());

        Glide.with(context)
                .load(post.getImage().getUrl())
                .into(holder.ivPost);
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivPost;
        public ImageView ivProfileImage;
        public TextView tvCaption;

        public ViewHolder(View itemView) {
            super(itemView);

            // perform findViewById lookups
            ivPost = itemView.findViewById(R.id.ivPost);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvCaption = itemView.findViewById(R.id.tvCaption);
        }
    }
}
