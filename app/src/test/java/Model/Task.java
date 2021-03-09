package Model;

import androidx.room.Entity;
import androidx.room.ForeignKey;

import java.util.Date;

@Entity(primaryKeys = {"taskID"}, foreignKeys = {@ForeignKey(entity = ConstructionSite.class,
        parentColumns = "siteID",
        childColumns = "siteID",
        onDelete = ForeignKey.SET_NULL)
})
public class Task {

    private int taskID;
    private String name;
    private String description;
    private Date deadline;
    private boolean status;
    private int siteID;

}
