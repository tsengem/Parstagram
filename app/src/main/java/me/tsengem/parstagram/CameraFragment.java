package me.tsengem.parstagram;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;

import me.tsengem.parstagram.model.Post;

import static me.tsengem.parstagram.HomeActivity.photoFile;

public class CameraFragment extends Fragment {

    ImageView ivPhoto;
    Button post_b;
    EditText tvCaption;

    ProgressBar pbLoading;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_camera_fragment, container, false);
        ivPhoto = v.findViewById(R.id.ivPhoto);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d("fragments", "post button was clicked!");
        tvCaption = view.findViewById(R.id.caption_et);
        pbLoading = view.findViewById(R.id.pbLoading);
        pbLoading.setVisibility(View.INVISIBLE);

        post_b = view.findViewById(R.id.b_post);
        post_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbLoading.setVisibility(View.VISIBLE);

                final Post newPost = new Post();
                newPost.setDescription(tvCaption.getText().toString());
                tvCaption.setText("");

                newPost.setUser(ParseUser.getCurrentUser());

                final ParseFile parseFile = new ParseFile(photoFile);

                parseFile.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            newPost.setImage(parseFile);

                            newPost.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {
                                        Log.d("CameraFragment", "Create post success!");
                                        pbLoading.setVisibility(View.INVISIBLE);
                                    } else {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }

                    }
                });
                Toast.makeText(v.getContext(), "Post saved", Toast.LENGTH_LONG).show();
            }
        });
    }
}
