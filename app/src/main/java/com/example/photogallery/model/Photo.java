package com.example.photogallery.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "Photos")
public class Photo
{
    @PrimaryKey
    @NonNull
    private String id;
    private String owner;
    private String secret;
    private String server;
    private String title;
    private int is_public;
    private int is_friend;
    private int is_family;
    private int farm;

    @Ignore
    public Photo() {}

    public Photo(String id, String owner, String secret, String server, String title, int is_family, int is_friend, int is_public) {
        setId(id);
        setOwner(owner);
        setSecret(secret);
        setServer(server);
        setTitle(title);
        setIs_family(is_family);
        setIs_friend(is_friend);
        setIs_public(is_public);
    }

    public String getId() {
        return id;
    }

    @SerializedName("id")
    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    @SerializedName("owner")
    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSecret() {
        return secret;
    }

    @SerializedName("secret")
    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getServer() {
        return server;
    }

    @SerializedName("server")
    public void setServer(String server) {
        this.server = server;
    }

    public int getFarm() {
        return farm;
    }

    @SerializedName("farm")
    public void setFarm(int farm) {
        this.farm = farm;
    }

    public String getTitle() {
        return title;
    }

    @SerializedName("title")
    public void setTitle(String title) {
        this.title = title;
    }

    public int getIs_public() {
        return is_public;
    }

    @SerializedName("ispublic")
    public void setIs_public(int is_public) {
        this.is_public = is_public;
    }

    public int getIs_friend() {
        return is_friend;
    }

    @SerializedName("isfriend")
    public void setIs_friend(int is_friend) {
        this.is_friend = is_friend;
    }

    public int getIs_family() {
        return is_family;
    }

    @SerializedName("isfamily")
    public void setIs_family(int is_family) {
        this.is_family = is_family;
    }

    //Получить url изображения
    public String getPhotoUrl() {
        return String.format("https://live.staticflickr.com/%s/%s_%s_w.jpg", getServer(), getId(), getSecret());
    }
}
