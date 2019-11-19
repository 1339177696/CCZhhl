package com.hulian.oa.pad;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Created by qgl on 2019/11/19 11:25.
 */
public class Padbean {
    private String url;
    private Bitmap bitmap;
    private File file;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }




    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

}
