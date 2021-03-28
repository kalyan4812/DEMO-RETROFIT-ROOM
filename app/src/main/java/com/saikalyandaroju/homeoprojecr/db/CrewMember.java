package com.saikalyandaroju.homeoprojecr.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.saikalyandaroju.homeoprojecr.POJOS.CrewResponse;

import java.util.List;

@Entity(tableName = "CrewMembers")
public class CrewMember {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    private String id;
    private String name ,agency, status, imageurl, wikiurl;

    public CrewMember() {

    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public CrewMember(String id, String name, String agency, String status, String imageurl, String wikiurl) {
        this.name = name;
        this.agency = agency;
        this.status = status;
        this.imageurl = imageurl;
        this.wikiurl = wikiurl;
        this.id=id;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getWikiurl() {
        return wikiurl;
    }

    public void setWikiurl(String wikiurl) {
        this.wikiurl = wikiurl;
    }
}
