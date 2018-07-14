package me.tsengem.parstagram;

import android.content.Context;
import android.graphics.Movie;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import me.tsengem.parstagram.model.Post;

public class PostDetailActivity extends AppCompatActivity {

    Post post;

    ImageView photo_iv;
    TextView caption_tv;
    TextView timestamp_tv;
    TextView user_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        photo_iv = findViewById(R.id.photo_iv);
        caption_tv = findViewById(R.id.caption_tv);
        timestamp_tv = findViewById(R.id.timestamp_tv);
        user_tv = findViewById(R.id.user_tv);

        final String objectId = getIntent().getExtras().getString("postObjectId");
        Log.d("DetailActivity", "objectId = " + objectId);

        final Post.Query postsQuery = new Post.Query();
        postsQuery.getTop().withUser();

        postsQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                for (int i = 0; i < objects.size(); i++) {
                    if (objects.get(i).getObjectId().equals(objectId)) {
                        post = objects.get(i);
                        caption_tv.setText(post.getDescription());
                        timestamp_tv.setText(getRelativeTimeAgo(post.getCreatedAt().toString()));
                        user_tv.setText(post.getUser().getUsername());

                        Glide.with(getApplicationContext())
                                .load(post.getImage().getUrl())
                                .into(photo_iv);
                    }
                }
            }
        });
    }

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }
}