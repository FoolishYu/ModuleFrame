package com.module.data.model.bean;

/**
 * @author zhouchunjie
 * @date 2017/10/26
 */

public class ImageInfo {

    private long id;
    private String path;
    private long dateModified;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getDateModified() {
        return dateModified;
    }

    public void setDateModified(long dateModified) {
        this.dateModified = dateModified;
    }
}
