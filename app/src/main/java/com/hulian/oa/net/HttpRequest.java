package com.hulian.oa.net;


import com.hulian.oa.bean.User;

import java.io.File;
import java.util.List;

/**
 * 作者：zb.
 * <p>
 * 时间：On 2019-05-05.
 * <p>
 * 描述：所有的请求接口
 */
public class HttpRequest {


    /**
     * @param params   入参
     * @param callback 回调接口
     */
    public static void getBannerApi(RequestParams params, ResponseCallback callback) {
        RequestMode.getRequest("https://www.wanandroid.com/banner/json", params, callback, null);
    }

    /**
     * @param params   入参
     * @param callback 回调接口
     */
    public static void postLoginApi(RequestParams params, ResponseCallback callback) {
//    RequestMode.postRequest("https://www.wanandroid.com/user/login", params, callback, UserInfo.class);
        RequestMode.postRequest(Urls.commUrls + "system/user/login", params, callback, User.class);
    }

    /**
     * 新闻列表
     *
     * @param params   入参
     * @param callback 回调接口
     */
    public static void postNesListApi(RequestParams params, ResponseCallback callback) {
//    RequestMode.postRequest("https://www.wanandroid.com/user/login", params, callback, UserInfo.class);
        RequestMode.postRequest(Urls.commUrls + "system/journalism/findJournalismList", params, callback, null);
    }

    /**
     * 新闻详情评论列表
     *
     * @param params   入参
     * @param callback 回调接口
     */
    public static void postNesInfoApi(RequestParams params, ResponseCallback callback) {
//    RequestMode.postRequest("https://www.wanandroid.com/user/login", params, callback, UserInfo.class);
        RequestMode.postRequest(Urls.commUrls + "system/journalism/findJournalismDetailByJournalismId ", params, callback, null);
    }

    /**
     * 新闻详情评论
     *
     * @param params   入参
     * @param callback 回调接口
     */
    public static void postNesSenCommApi(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/journalismComment/commentJournalism", params, callback, null);
    }
//  /**
//   * 收藏
//   * @param params 入参
//   * @param callback 回调接口
//   */
//  public static void postStoreSenCommApi(RequestParams params, ResponseCallback callback) {
//    RequestMode.postRequest(Urls.commUrls+"system/collectData/saveCollectInfo", params, callback, null);
//  }

    /**
     * 收藏qgl
     *
     * @param params   入参
     * @param callback 回调接口
     */
    public static void postStoreSenCommApi(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/collectData/collectXwOrGg", params, callback, null);
    }

    /**
     * 点赞
     *
     * @param params   入参
     * @param callback 回调接口
     */
    public static void postDianzanCommApi(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/journalismComment/praiseComment", params, callback, null);
    }

    /**
     * 通告列表
     *
     * @param params   入参
     * @param callback 回调接口
     */
    public static void postNoticeListApi(RequestParams params, ResponseCallback callback) {
//    RequestMode.postRequest("https://www.wanandroid.com/user/login", params, callback, UserInfo.class);
        RequestMode.postRequest(Urls.commUrls + "system/notice/findNoticeList ", params, callback, null);
    }

    /**
     * 通告详情
     *
     * @param params   入参
     * @param callback 回调接口
     */
    public static void postNoticeInfoApi(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/notice/findNoticeDetailByNoticeId", params, callback, null);

    }

//  /**
//   * 待办列表
//   * @param params 入参
//   * @param callback 回调接口
//   */
//  public static void postAgencyListApi(RequestParams params, ResponseCallback callback) {
//    RequestMode.postRequest(Urls.commUrls+"system/pendingCore/findUnfinishedListInfo", params, callback, null);
//
//  }

    /**
     * 待办列表
     *
     * @param params   入参
     * @param callback 回调接口
     */
    public static void postAgencyListApi(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/pendingCore/selectUnfinishedWork", params, callback, null);

    }

//  /**
//   * 已办列表
//   * @param params 入参
//   * @param callback 回调接口
//   */
//  public static void postAgencyFinishListApi(RequestParams params, ResponseCallback callback) {
//    RequestMode.postRequest(Urls.commUrls+"system/pendingCore/findFinishedListInfo", params, callback, null);
//
//  }

    /**
     * 已办列表
     *
     * @param params   入参
     * @param callback 回调接口
     */
    public static void postAgencyFinishListApi(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/pendingCore/selectFinishedWork", params, callback, null);

    }

    /**
     * 发布通知
     *
     * @param params   入参
     * @param callback 回调接口
     */
    public static void postNoticeSendApi(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/notice/publishNoticeInfo", params, callback, null);
    }


//  /**
//   * 查询会议室
//   * @param params 入参
//   * @param callback 回调接口
//   */
//  public static void postMeetRoomeApi(RequestParams params, ResponseCallback callback) {
//    RequestMode.postRequest(Urls.commUrls+"system/meetingRoom/queryMeetingRoom", params, callback, null);
//  }

    /**
     * 查询会议室
     *
     * @param params   入参
     * @param callback 回调接口
     */
    public static void postMeetRoomeApi(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/meetingRoom/androidQueryMeetingRoom", params, callback, null);
    }


    /**
     * 发布会议
     *
     * @param params   入参
     * @param callback 回调接口
     */
    public static void postMeetLauncherApi(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/meeting/hb_add", params, callback, null);
    }
//  /**
//   * 发布公文
//   * @param params 入参
//   * @param callback 回调接口
//   */
//  public static void postDocumentSendApi(RequestParams params, List<File> files, ResponseCallback callback) {
//  //  RequestMode.postRequest(Urls.commUrls+"system/officialDocument/publish", params, callback, null);
//    RequestMode.postMultipart(Urls.commUrls+"system/officialDocument/publish", params, files, callback, null);
//  }

    /**
     * 发布公文
     *
     * @param params   入参
     * @param callback 回调接口
     */
    public static void postDocumentSendApi(RequestParams params, List<File> files, ResponseCallback callback) {
        //  RequestMode.postRequest(Urls.commUrls+"system/officialDocument/publish", params, callback, null);
        RequestMode.postMultipart_qgl(Urls.commUrls + "system/lotus/hb_add", params, files, callback, null);
    }


    /**
     * 任务详情（回复）
     *
     * @param params   入参
     * @param callback 回调接口
     */
    public static void post_workCoordinationReply_add(RequestParams params, List<File> files, ResponseCallback callback) {
        RequestMode.postMultipart(Urls.commUrls + "system/workCoordinationReply/hb_add", params, files, callback, null);
    }
//  /**
//   * 我发起的/我审批的
//   * @param params 入参
//   * @param callback 回调接口
//   */
//  public static void postDocumentListApi(RequestParams params,  ResponseCallback callback) {
//     RequestMode.postRequest(Urls.commUrls+"system/officialDocument/getlists", params, callback, null);
//  }

    /**
     * qgl
     * 我发起的/我审批的
     *
     * @param params   入参
     * @param callback 回调接口
     */
    public static void postDocumentListApi(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/lotus/lotusViceList", params, callback, null);
    }
//  /**
//   * 公文详情
//   * @param params 入参
//   * @param callback 回调接口
//   */
//  public static void postDocumentInfoApi(RequestParams params,  ResponseCallback callback) {
//    RequestMode.postRequest(Urls.commUrls+"system/officialDocument/getDetail", params, callback, null);
//  }

    /**
     * qgl
     * 公文详情
     *
     * @param params   入参
     * @param callback 回调接口
     */
    public static void postDocumentInfoApi(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "/system/lotus/details", params, callback, null);
    }
//  /**
//   * 公文审批
//   * @param params 入参
//   * @param callback 回调接口
//   */
//  public static void postDocumentApproveApi(RequestParams params,  ResponseCallback callback) {
//    RequestMode.postRequest(Urls.commUrls+"system/officialDocument/approve", params, callback, null);
//  }

    /**
     * 公文审批 qgl
     *
     * @param params   入参
     * @param callback 回调接口
     */
    public static void postDocumentApproveApi(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/lotus/lotusApprove", params, callback, null);
    }

    /**
     * 公文审批 qgl2
     *
     * @param params   入参
     * @param callback 回调接口
     */
    public static void postDocumentApproveApi_two(RequestParams params, List<File> file, ResponseCallback callback) {
        RequestMode.postMultipart_qgl(Urls.commUrls + "system/lotus/lotusApprove", params, file, callback, null);
    }

    /**
     * 公文审批 PAD
     *
     * @param params   入参
     * @param callback 回调接口
     */
    public static void postDocumentApproveApi_PAD(RequestParams params, List<File> file, ResponseCallback callback) {
        RequestMode.postMultipart_qgl(Urls.commUrls + "system/lotus/lotusApprove", params, file, callback, null);
    }

    /**
     * 查询部门人员
     *
     * @param params   入参
     * @param callback 回调接口
     */
    public static void postUserInfoByDeptId(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/user/findUserInfoByDeptId ", params, callback, null);
    }

    /**
     * 部门列表
     *
     * @param params   入参
     * @param callback 回调接口
     */
    public static void postDepartmentListApi(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/dept/findDeptInfoAll", params, callback, null);
    }

    /**
     * 收藏 ： 新闻、通知通告查询
     *
     * @param params
     * @param callback
     */
    public static void postNews_inquiryListApi(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/collectData/findCollectInfo", params, callback, null);
    }

    /**
     * 收藏：新闻、通知删除
     *
     * @param params
     * @param callback
     */
    public static void potNews_romoveCollectInfo(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/collectData/romoveCollectInfo", params, callback, null);
    }

    /**
     * 根据日期查询日程
     *
     * @param params
     * @param callback
     */
    public static void postSche_schedule(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/schedule/schedule", params, callback, null);

    }

    /**
     * 增加提醒时间
     *
     * @param params
     * @param callback
     */
    public static void postSche_Tixing(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/schedule/edit", params, callback, null);
    }

    /**
     * 添加日程
     *
     * @param params
     * @param callback
     */
    public static void postSche_Tianjia(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/schedule/hb_add", params, callback, null);
    }

    /**
     * 修改日程
     *
     * @param params
     * @param callback
     */
    public static void postSche_Xiugai(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/schedule/androidEdit", params, callback, null);
    }

    /**
     * 删除日程
     *
     * @param params
     * @param callback
     */
    public static void postSche_Dlete(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/schedule/remove", params, callback, null);
    }

    /**
     * 查询有日程的日期
     *
     * @param params
     * @param callback
     */
    public static void postSche_selectDates(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/schedule/selectDates", params, callback, null);
    }

    /**
     * 添加反馈意见
     *
     * @param params
     * @param callback
     */
    public static void postSuggest(RequestParams params, List<File> file, ResponseCallback callback) {
        RequestMode.postMultipart_qgl(Urls.commUrls + "system/opinionManagement/hb_add", params, file, callback, null);
    }

    /**
     * 任务协同列表
     *
     * @param params
     * @param callback
     */
    public static void post_CoordinationRelease_list(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/workCoordinationRelease/list", params, callback, null);
    }

//  /**
//   * 工作协同新增
//   * @param params
//   * @param callback
//   */
//  public static void post_CoordinationRelease_add(RequestParams params,ResponseCallback callback)
//  {
//    RequestMode.postRequest(Urls.commUrls+"system/workCoordinationRelease/hb_add",params,callback,null);
//  }

    /**
     * 工作协同新增
     *
     * @param params
     * @param callback
     */
    public static void post_CoordinationRelease_add(RequestParams params, List<File> file, ResponseCallback callback) {
        RequestMode.postMultipart_qgl(Urls.commUrls + "system/workCoordinationRelease/hb_add", params, file, callback, null);
    }

    /**
     * 工作完成
     *
     * @param params
     * @param callback
     */
    public static void post_CoordinationRelease_deit(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/workCoordinationInfo/edit", params, callback, null);
    }

    /**
     * 转交任务
     *
     * @param params
     * @param callback
     */
    public static void post_workCoordinationInfo_editExecutor(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/workCoordinationInfo/editExecutor", params, callback, null);
    }

    /**
     * 转交任务
     *
     * @param params
     * @param callback
     */
    public static void post_workCoordinationRelease_details(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/workCoordinationRelease/details", params, callback, null);
    }

    /**
     * 收件箱
     *
     * @param params
     * @param callback
     */
    public static void post_FindInboxInfo(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/mail/findInboxInfo", params, callback, null);
    }

    /**
     * 发件箱
     *
     * @param params
     * @param callback
     */
    public static void post_FindSentMailList(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/mail/findSentMailList", params, callback, null);
    }

    /**
     * 邮箱回复
     *
     * @param params
     * @param callback
     */
    public static void post_replyMail(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/mail/replyMail", params, callback, null);
    }

    /**
     * 发送邮箱
     *
     * @param params
     * @param callback
     */
    public static void post_sendMail(RequestParams params, List<File> files, ResponseCallback callback) {
        RequestMode.postMultipart(Urls.commUrls + "system/mail/sendMail", params, files, callback, null);
    }

    /**
     * 邮箱收件箱收藏
     *
     * @param params
     * @param callback
     */
    public static void post_mailCollect(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/mail/mailCollect", params, callback, null);
    }

    /**
     * 收件箱邮件删除
     *
     * @param params
     * @param callback
     */
    public static void post_DeleteCollect(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/mailInfo/remove", params, callback, null);
    }


    /**
     * 请假申请
     *
     * @param params
     * @param callback
     */
    public static void post_sendLeave(RequestParams params, List<File> files, ResponseCallback callback) {
        RequestMode.postMultipart(Urls.commUrls + "system/workLeave/addLeave", params, files, callback, null);
    }

    /**
     * 请假列表
     *
     * @param params
     * @param callback
     */
    public static void get_listWorkLeave(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/workLeave/listWorkLeave", params, callback, null);
    }

    /**
     * 请假详情
     *
     * @param params
     * @param callback
     */
    public static void get_WorkLeave(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/workLeave/selectWorkLeave", params, callback, null);
    }

    /**
     * 请假同意，拒绝，驳回
     *
     * @param params
     * @param callback
     */
    public static void get_Work_edit(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/workLeave/passOrRejection", params, callback, null);
    }

    /**
     * 申请报销
     *
     * @param params
     * @param callback
     */
    public static void post_sendExpense(RequestParams params, List<File> files, ResponseCallback callback) {
        RequestMode.postMultipart(Urls.commUrls + "system/workReimbursement/hb_add", params, files, callback, null);
    }

    /**
     * 报销列表
     *
     * @param params
     * @param callback
     */
    public static void get_listWorkExpense(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/workReimbursement/list", params, callback, null);
    }

    /**
     * 报销详情
     *
     * @param params
     * @param callback
     */
    public static void get_WorkExpense(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/workReimbursement/selectReimbursement", params, callback, null);
    }

    /**
     * 报销同意，拒绝，驳回
     *
     * @param params
     * @param callback
     */
    public static void get_WorkExpense_edit(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/workReimbursement/edit", params, callback, null);
    }

    /**
     * 下载图片 Get方式
     *
     * @param params   入参
     * @param imgPath  存储地址
     * @param callback 回调接口
     */
    public static void getImgApi(RequestParams params, String imgPath, ResponseByteCallback callback) {
        RequestMode.getLoadImg("http://p0.meituan.net/165.220/movie/7f32684e28253f39fe2002868a1f3c95373851.jpg", params, imgPath, callback);
    }

    /**
     * 下载图片 Post方式
     *
     * @param params   入参
     * @param imgPath  存储地址
     * @param callback 回调接口
     */
    public static void postImgApi(RequestParams params, String imgPath, ResponseByteCallback callback) {
        RequestMode.postLoadImg("url地址", params, imgPath, callback);
    }

    /**
     * 图文混合上传服务器
     *
     * @param params
     * @param files
     * @param callback
     */
    public static void postMultipartApi(RequestParams params, List<File> files, ResponseCallback callback) {
        RequestMode.postMultipart("url地址", params, files, callback, null);
    }

    /**
     * 指令下发
     *
     * @param params   入参
     * @param callback 回调接口
     */
    public static void postInstructSendApi(RequestParams params, List<File> files, ResponseCallback callback) {
        //  RequestMode.postRequest(Urls.commUrls+"system/officialDocument/publish", params, callback, null);
        RequestMode.postMultipart(Urls.commUrls + "system/instructions/addInstructions", params, files, callback, null);
    }

    /**
     * 指令安排列表
     *
     * @param params
     * @param callback
     */
    public static void postSecondInstructApi(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/instructions/list", params, callback, null);
    }

    /**
     * 指令详情
     *
     * @param params
     * @param callback
     */
    public static void postInstructDetailsApi(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/instructions/selectDetails", params, callback, null);
    }

    /**
     * 指令接受和反馈
     *
     * @param params
     * @param callback
     */
    public static void postInstructOperation(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/instructions/edit", params, callback, null);
    }

    /**
     * 会议安排-我发起的
     *
     * @param params
     * @param callback
     */
    public static void postMyCreateMeeting(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/meeting/getMeetingListByCreator", params, callback, null);
    }

    /**
     * 会议安排-我接收的
     *
     * @param params
     * @param callback
     */
    public static void postMyAttendMeeting(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/meeting/getMeetingListByParticipantId", params, callback, null);
    }

    /**
     * 会议信息
     *
     * @param params
     * @param callback
     */
    public static void postMeeting(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/meeting/getMeetingByid", params, callback, null);
    }

    /**
     * 会议签到
     *
     * @param params
     * @param callback
     */
    public static void postMeetingsignIn(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/meetingParticipant/signIn", params, callback, null);
    }

    /**
     * 新加的抄送人
     *
     * @param params
     * @param callback
     */
    public static void postChaosongren(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/lotus/copierList", params, callback, null);
    }

    /**
     * 新加的我发起的
     *
     * @param params
     * @param callback
     */
    public static void postWoFaqi(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/lotus/list", params, callback, null);
    }


    /**
     * 新加的任务取消
     *
     * @param params
     * @param callback
     */
    public static void postCancelTask(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/workCoordinationRelease/cancelTask", params, callback, null);
    }

    /**
     * 消息中心
     *
     * @param params
     * @param callback
     */
    public static void postNotice(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/viewMsg/android/list", params, callback, null);
    }

    /**
     * 消息中心详情
     *
     * @param params
     * @param callback
     */
    public static void postNotice_List(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/viewMsg/android/getListByTypeAndUserId", params, callback, null);
    }

    /**
     * 修改已读
     *
     * @param params
     * @param callback
     */
    public static void postNotice_staus(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/viewMsg/android/updatesUnreadMsgByType", params, callback, null);
    }

    /**
     * 邮件详情
     *
     * @param params
     * @param callback
     */
    public static void youjian_detelis(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/mailInfo/android/findMail", params, callback, null);
    }

    /**
     * 请假审批人，抄送人
     *
     * @param params
     * @param callback
     */
    public static void getPerson(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/workLeave/getUserInfo", params, callback, null);
    }

    public static void getrcYiDu(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/viewMsg/clearMsgByType", params, callback, null);
    }

    /**
     * 添加日程
     *
     * @param params
     * @param callback
     */
    public static void post_findLotusSignApprover(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/lotus/findLotusSignApprover", params, callback, null);
    }

    /**
     * 获取汇报接收人
     *
     * @param params
     * @param callback
     */
    public static void getRecipient(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/reportInfo/android/getReportDefaultPerson", params, callback, null);
    }

    /**
     * 添加汇报
     *
     * @param params
     * @param callback
     */
    public static void postReport(RequestParams params, List<File> files, ResponseCallback callback) {
        RequestMode.postMultipart(Urls.commUrls + "system/reportInfo/android/hb_add", params, files,callback, null);
    }

    /**
     * 获取汇报详情
     *
     * @param params
     * @param callback
     */
    public static void getGetWorkReportList(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/reportInfo/android/list", params, callback, null);
    }

    /**
     * 获取汇报详情
     *
     * @param params
     * @param callback
     */
    public static void getGetWorkReport(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/reportInfo/android/selectById", params, callback, null);
    }

    /**
     * 获取版本信息
     *
     * @param params
     * @param callback
     */
    public static void posVerson(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/apkVersion/android/apkInfo", params, callback, null);
    }

}
