package me.tsengem.parstagram;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.tsengem.parstagram.model.Post;
import com.bumptech.glide.Glide;
import com.parse.Parse;
import com.parse.ParseFile;
import com.parse.ParseUser;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private ArrayList<Post> mPosts;
    Context context;

    public PostAdapter() {
        mPosts = new ArrayList<Post>();
    }

    public void add(Post p) {
        mPosts.add(p);
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

        if (mPosts.size() == 0) {
            return;
        }

        Post post = mPosts.get(position);

        ParseUser user = post.getUser();

        holder.tvCaption.setText(post.getDescription());

        holder.tvUsername.setText(user.getUsername());
        holder.tvCreatedAt.setText(post.getCreatedAt().toString());

        Glide.with(context)
                .load(post.getImage().getUrl())
                .into(holder.ivPost);
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        mPosts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        mPosts.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView ivPost;
        public ImageView ivProfileImage;
        public TextView tvCaption;
        public TextView tvUsername;
        public TextView tvCreatedAt;

        public ViewHolder(View itemView) {
            super(itemView);

            // perform findViewById lookups
            ivPost = itemView.findViewById(R.id.ivPost);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvCaption = itemView.findViewById(R.id.tvCaption);
            tvUsername = itemView.findViewById(R.id.tv_username);
            tvCreatedAt = itemView.findViewById(R.id.tv_createdAt);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // get item position
            int position = getAdapterPosition();

            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position
                Post post = mPosts.get(position);

                // create intent for the new activity
                Intent intent = new Intent(context, PostDetailActivity.class);

                // serialize the movie using parceler
                intent.putExtra("postObjectId", post.getObjectId());

                // show the activity
                context.startActivity(intent);
            }
        }
    }
}
