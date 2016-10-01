package todo.bootcamp.com.tosndos.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import de.greenrobot.event.EventBus;
import todo.bootcamp.com.tosndos.R;
import todo.bootcamp.com.tosndos.adapter.TodoItemAdapter;
import todo.bootcamp.com.tosndos.model.ToDoModel;
import todo.bootcamp.com.tosndos.utils.DatabaseUtils;

public class ToDoActivityFragment extends Fragment{

    public static final String TAG = ToDoActivity.class.getName();
    private RecyclerView recyclerView;
    private List<ToDoModel> toDoFeedContent;
    private TodoItemAdapter toDoFeedAdapter;

    public ToDoActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_to_do, container, false);

        Context context = rootView.getContext();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.list);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        return rootView;
    }

    public void onEvent(String event){
        Log.d(TAG, "Data changed event");
        toDoFeedContent = DatabaseUtils.getAllTodoFeedContent();
        toDoFeedAdapter = new TodoItemAdapter(getActivity(), toDoFeedContent);
        recyclerView.setAdapter(toDoFeedAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        toDoFeedContent = DatabaseUtils.getAllTodoFeedContent();
        toDoFeedAdapter = new TodoItemAdapter(getActivity(), toDoFeedContent);
        recyclerView.setAdapter(toDoFeedAdapter);
    }

}
