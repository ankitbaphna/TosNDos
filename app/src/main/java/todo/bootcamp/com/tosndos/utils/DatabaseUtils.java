package todo.bootcamp.com.tosndos.utils;

import com.activeandroid.query.Select;

import java.util.List;

import todo.bootcamp.com.tosndos.model.ToDoModel;

/**
 * Created by baphna on 9/23/2016.
 */

public class DatabaseUtils {

    public static List<ToDoModel> getAllTodoFeedContent(){
        return new Select().from(ToDoModel.class).orderBy("Id DESC").execute();
    }
}
