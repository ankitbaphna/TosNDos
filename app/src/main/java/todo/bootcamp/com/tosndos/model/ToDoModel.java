package todo.bootcamp.com.tosndos.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

/**
 * Created by baphna on 9/23/2016.
 */
@Table(name = "ToDoTable")
public class ToDoModel extends Model {

    @Column(name = "title")     String title;
    @Column(name = "endDate")   Date endDate;
    @Column(name = "tags")      String tags;
    @Column(name = "priority")  int priority; //0 = low, 1 = medium, 2 = high
    @Column(name = "completed") boolean completed;
    @Column(name = "items")     String details;

    public ToDoModel() {
        super();
    }

    public ToDoModel(String title, Date endDate, String tags, int priority, boolean completed, String details) {
        super();
        this.title = title;
        this.endDate = endDate;
        this.tags = tags;
        this.priority = priority;
        this.completed = completed;
        this.details = details;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
