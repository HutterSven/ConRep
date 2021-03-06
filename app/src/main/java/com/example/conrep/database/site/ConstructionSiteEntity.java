package com.example.conrep.database.site;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class ConstructionSiteEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int siteID;
    private String siteName;
    private String city;
    private String address;
    private String overseer;
    private int hours;

    public int getSiteID() {
        return siteID;
    }

    public void setSiteID(int siteID) {
        this.siteID = siteID;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOverseer() {
        return overseer;
    }

    public void setOverseer(String overseer) {
        this.overseer = overseer;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

}
