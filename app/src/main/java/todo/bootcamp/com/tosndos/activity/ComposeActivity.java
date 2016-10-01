package todo.bootcamp.com.tosndos.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import todo.bootcamp.com.tosndos.R;
import todo.bootcamp.com.tosndos.model.ToDoModel;

public class ComposeActivity extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    @Bind(R.id.edit_text_description)
    EditText editTextDescription;

    @Bind(R.id.edit_text_tags)
    EditText editTextTags;

    @Bind(R.id.edit_text_title)
    EditText editTextTitle;

    @Bind(R.id.edit_text_priority)
    EditText editTextPriority;

    @Bind(R.id.button_date_end)
    ImageView datePicker;

    @Bind(R.id.text_view_date_end)
    TextView textViewDateTime;

    @Bind(R.id.relative_layout_date_text)
    RelativeLayout relativeLayoutDate;

    boolean isEditing = false;
    private long editItemId = 0;
    private String TAG = ComposeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        ButterKnife.bind(this);

        editItemId = getIntent().getLongExtra("Id", -1 );
        if(editItemId != -1){
            isEditing = true;
            ToDoModel toDoModel = ToDoModel.load(ToDoModel.class, editItemId);
            editTextTitle.setText(toDoModel.getTitle());
            editTextTags.setText(toDoModel.getTags());
            editTextPriority.setText(toDoModel.getPriority()+"");
            editTextDescription.setText(toDoModel.getDetails());
            textViewDateTime.setText(toDoModel.getEndDate().toString());
        }

    }

    @OnClick({R.id.button_date_end, R.id.relative_layout_date_text})
    public void datePickerClicked(View view) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                ComposeActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_compose, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_discard) {
            finish();
        }else if (id == R.id.action_save) {
            String details = "";
            String title = "";
            String tags = "";
            int priority = 0;
            String priorityString = "0";
            Date endDate = new Date();

            if(textViewDateTime.getText() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd , hh:mm");

                try {
                    endDate = sdf.parse(textViewDateTime.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            if(editTextDescription.getText() != null){
                details = editTextDescription.getText().toString();
            }

            if(editTextTitle.getText() != null){
                title = editTextTitle.getText().toString();
            } else {
                Toast.makeText(this, "Please enter title", Toast.LENGTH_SHORT).show();
            }

            if(editTextTags.getText() != null){
                tags = editTextTags.getText().toString();
            }
            if (editTextPriority.getText() != null){
                try {
                    priority = Integer.parseInt(editTextPriority.getText().toString());
                } catch (NumberFormatException e){
                    Log.e(TAG, "Exception in priority " +e.getMessage());
                    priority = 0;
                }
            }

            if(isEditing ==  false) {
                ToDoModel toDoModel = new ToDoModel(title, endDate, tags, priority, false, details);
                toDoModel.save();
                EventBus.getDefault().post("");
            } else {
                ToDoModel toDoModel = ToDoModel.load(ToDoModel.class, editItemId);
                toDoModel.setTitle(title);
                toDoModel.setDetails(details);
                toDoModel.setTags(tags);
                toDoModel.setEndDate(endDate);
                toDoModel.setCompleted(false);
                toDoModel.setPriority(priority);
                toDoModel.save();
                EventBus.getDefault().post("");
            }
            
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd");
        Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
        String date = sdf.format(calendar.getTime());

        textViewDateTime.setText(date);

        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                ComposeActivity.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
        );
        tpd.show(getFragmentManager(), "");
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Calendar calendar = new GregorianCalendar(2016, 1, 28, hourOfDay, minute);
        String time = sdf.format(calendar.getTime());
        textViewDateTime.append(" , " + time);
    }
}
