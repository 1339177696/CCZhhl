package com.hulian.oa.bean;

import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.List;

/**
 * 作者：qgl 时间： 2020/7/16 16:40
 * Describe:
 */
public class urlbean {
    public String getLineMoney() {
        return lineMoney;
    }

    public void setLineMoney(String lineMoney) {
        this.lineMoney = lineMoney;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getApproveType() {
        return approveType;
    }

    public void setApproveType(String approveType) {
        this.approveType = approveType;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    private String lineMoney;
    private String img;
    private String approveType;
    private String cause;
    private List<LocalMedia> list_invoice;//发票说明;

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    private List<File>files;

    public List<LocalMedia> getList_invoice() {
        return list_invoice;
    }

    public void setList_invoice(List<LocalMedia> list_invoice) {
        this.list_invoice = list_invoice;
    }


    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    private String index;//索引id (id值为list的大小，添加多个空数据对象时移除时会导致移除最后一个，不按照位置移除)
}
