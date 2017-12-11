package com.teksine.queryapplication.other;

/**
 * Created by abin on 11/12/2017.
 */

public class RowItem {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private int imageId;
    private String title;
    private String desc;

    public RowItem(int id,int imageId, String title, String desc) {
        this.id=id;
        this.imageId = imageId;
        this.title = title;
        this.desc = desc;
    }
    public int getImageId() {
        return imageId;
    }
    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    @Override
    public String toString() {
        return id+" "+title + "\n" + desc;
    }
}
