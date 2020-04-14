package com.hulian.oa.iac.config;

public class UrlConfig {

//    1企业信息
//    2申请许可类别
//    3取得相关认证
//    4试生产（制造）情况
//    5分包、外协情况
//    6自行校验仪器设备能力
//    7主要生产设备状况
//    8主要检验与试验仪器设备状况
//    9管理、专业、作业人员情况
//    10各部门人员组成
//    11  申请单位资源
    public static final String HOST = "http://124.234.143.68:";
    public static final String PORT = "7034/FH_O";
//    public static final String HOST = "http://192.168.0.112:";
//    public static final String PORT = "8080/FH_O";
    public static final String  PATH_COMMON = HOST+PORT+"/authenticate/ydlist.do";
    public static final String  PATH_BACKLOG = HOST+PORT+"/transaction/ydlist";
    //上传数据
    public static final String  PATH_UPLOAD_DATA = HOST+PORT+"/authenticate/scpstj";
    public static final String  PATH_UPLOAD_IMAGE = HOST+PORT+"/apply_data/uploadImg.do";
    public static final String  PAHT_SHOW_IMG = HOST+PORT+"/apply_data/download.do";
    //待办提交
//    public static final String  PAHT_DB_PJ = HOST+PORT+"/authenticate/ydAudit";
    public static final String  PAHT_DB_PJ = HOST+PORT+"/authenticate/audit1";
    //预览报告
    public static final String  PAHT_REPORT = HOST+PORT+"/sqstatic/111/11.html";
    //预览报告新地址
    public static final String  PAHT_PRE_REPORT = HOST+PORT+"/authenticate/psbg";
    //登录接口
    public static final String  PAHT_LOGIN = HOST+PORT+"/zjk/cx";
    //测试服务器
//    public static final String  PATH_COMMON = "http://124.234.143.68:7034/FH_O/authenticate/ydlist.do";
//    public static final String  PATH_BACKLOG = "http://124.234.143.68:7034/FH_O/transaction/ydlist";
//      public static final String  PATH_UPLOAD_DATA = "http://192.168.0.126:8080/FH_O/authenticate/scpstj.do";
    //1企业信息评审
//    public static final String  PATH_UPLOAD_DATA = "http://124.234.143.68:7034/FH_O/authenticate/scpstj.do";
//  public static final String  PATH_UPLOAD_DATA = "http://192.168.0.126:8080/FH_O/authenticate/scpstj.do";

    //上传图片接口

}
