package Model;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(primaryKeys = {"siteID"}, foreignKeys = {@ForeignKey(entity = Task.class,
        parentColumns = "taskID",
        childColumns = "taskID",
        onDelete = ForeignKey.SET_NULL)
})
public class ConstructionSite {

    private int siteID;
    private String siteName;
    private String city;
    private String address;
    private String overseer;
    private int hours;
    private int taskID;
}
