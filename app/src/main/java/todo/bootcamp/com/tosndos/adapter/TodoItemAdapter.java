package todo.bootcamp.com.tosndos.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import de.greenrobot.event.EventBus;
import todo.bootcamp.com.tosndos.R;
import todo.bootcamp.com.tosndos.activity.ComposeActivity;
import todo.bootcamp.com.tosndos.model.ToDoModel;

/**
 * Created by baphna on 9/23/2016.
 */
public class TodoItemAdapter extends RecyclerView.Adapter<TodoItemAdapter.ViewHolder>{

    public static final String TAG = TodoItemAdapter.class.getSimpleName();
    private Context mContext;
    private final List<ToDoModel> toDoFeedContents;

    public TodoItemAdapter(Context mContext, List<ToDoModel> toDoFeedContents) {
        this.mContext = mContext;
        this.toDoFeedContents = toDoFeedContents;
    }

    @Override
    public TodoItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_feed, parent, false);
        mContext = parent.getContext();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TodoItemAdapter.ViewHolder holder, final int position) {
        holder.mItem = toDoFeedContents.get(position);
        holder.mTitleView.setText(toDoFeedContents.get(position).getTitle());
        holder.mDateTimeView.setText(toDoFeedContents.get(position).getEndDate().toString());
        holder.mSummaryView.setText(toDoFeedContents.get(position).getDetails());

        String tags = "Tags: " + toDoFeedContents.get(position).getTags();
        holder.mTagsView.setText(tags);

        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToDoModel item = ToDoModel.load(ToDoModel.class, holder.mItem.getId());
                final ToDoModel itemCopy = new ToDoModel(item.getTitle(), item.getEndDate(),
                        item.getTags(), item.getPriority(), item.isCompleted(), item.getDetails());
                item.delete();

                Snackbar.make(v, "Deleted", Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemCopy.save();
                        EventBus.getDefault().post("");
                    }
                }).show();
                EventBus.getDefault().post("");
            }
        });

        holder.mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent = new Intent(mContext, ComposeActivity.class);
                editIntent.putExtra("Id", holder.mItem.getId());
                mContext.startActivity(editIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return toDoFeedContents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitleView;
        public final TextView mDateTimeView;
        public final TextView mSummaryView;
        public final TextView mTagsView;
        public final ImageButton mEditButton;
        public final ImageButton mDeleteButton;
        public ToDoModel mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = (TextView) view.findViewById(R.id.text_view_title);
            mDateTimeView = (TextView) view.findViewById(R.id.text_view_date_time);
            mEditButton = (ImageButton) view.findViewById(R.id.image_button_edit);
            mDeleteButton = (ImageButton) view.findViewById(R.id.image_button_delete);
            mSummaryView = (TextView) view.findViewById(R.id.text_view_summary);
            mTagsView = (TextView) view.findViewById(R.id.text_view_tags);
        }

    }
}

