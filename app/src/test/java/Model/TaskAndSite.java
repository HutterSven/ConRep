package Model;

import androidx.room.Embedded;

public class TaskAndSite {

    @Embedded
    ConstructionSite site;

    @Embedded
    Task task;

}
