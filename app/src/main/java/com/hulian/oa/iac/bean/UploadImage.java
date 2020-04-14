package com.hulian.oa.iac.bean;

public class UploadImage{


    /**
     * flag : true
     * code : 200
     * filePath : 630647654f084cfbabf9119e64aa5ad1
     * zmwjFileId : fac235b6069145ea9e84333ab4f251cc
     */

    private boolean flag;
    private String code;
    private String filePath;
    private String zmwjFileId;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getZmwjFileId() {
        return zmwjFileId;
    }

    public void setZmwjFileId(String zmwjFileId) {
        this.zmwjFileId = zmwjFileId;
    }
}
