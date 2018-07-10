package me.tsengem.parstagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

import me.tsengem.parstagram.model.Post;

public class ParseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);

        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("EmmaAppId")
                .clientKey("seattle-pop")
                .server("http://tsengem-fbu-instagram.herokuapp.com/parse")
                .build();

        Parse.initialize(configuration);
    }
}
