package todo.bootcamp.com.tosndos.application;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

/**
 * Created by baphna on 9/23/2016.
 */

public class ToDoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}
