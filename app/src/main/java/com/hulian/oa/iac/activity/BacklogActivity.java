package com.hulian.oa.iac.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hulian.oa.R;
import com.hulian.oa.iac.activity_list.ApplyAllowListActivity;
import com.hulian.oa.iac.activity_list.CertificationListActivity;
import com.hulian.oa.iac.activity_list.InspectDeviceListActivity;
import com.hulian.oa.iac.activity_list.ManageStaffListActivity;
import com.hulian.oa.iac.activity_list.PramaryDeviceListActivity;
import com.hulian.oa.iac.activity_list.ProduceListActivity;
import com.hulian.oa.iac.activity_list.SelfCheckDeviceListActivity;
import com.hulian.oa.iac.activity_list.SubcontractListActivity;
import com.hulian.oa.iac.base.BaseActivity;
import com.hulian.oa.iac.bean.BacklogBean;
//import com.yanzhenjie.nohttp.NoHttp;
//import com.yanzhenjie.nohttp.RequestMethod;
//import com.yanzhenjie.nohttp.rest.Request;
//import com.yanzhenjie.nohttp.rest.Response;
//import com.zhhl.marketauthority.R;
//import com.zhhl.marketauthority.activity.backloglist.ApplyAllowListActivity;
//import com.zhhl.marketauthority.activity.backloglist.CertificationListActivity;
//import com.zhhl.marketauthority.activity.backloglist.InspectDeviceListActivity;
//import com.zhhl.marketauthority.activity.backloglist.ManageStaffListActivity;
//import com.zhhl.marketauthority.activity.backloglist.PramaryDeviceListActivity;
//import com.zhhl.marketauthority.activity.backloglist.ProduceListActivity;
//import com.zhhl.marketauthority.activity.backloglist.SelfCheckDeviceListActivity;
//import com.zhhl.marketauthority.activity.backloglist.SubcontractListActivity;
//import com.zhhl.marketauthority.bean.BacklogBean;
//import com.zhhl.marketauthority.config.UrlConfig;
//import com.zhhl.marketauthority.nohttp.listener.HttpListener;
//import com.zhhl.marketauthority.util.GsonUtil;
//import com.zhhl.marketauthority.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 陈泽宇 on 2019/12/6.
 * Describe:待办事项 详情
 */
public class BacklogActivity extends BaseActivity {


    @BindView(R.id.name)
    TextView name;//申请单位
    @BindView(R.id.legalPerson)
    TextView legalPerson;//法定代表人
    @BindView(R.id.industry)
    TextView industry;//所属行业
    @BindView(R.id.establishment)
    TextView establishment;//成立日期
    @BindView(R.id.assets)
    TextView assets;//固定资产
    @BindView(R.id.registeredCapital)
    TextView registeredCapital;//注册资金
    @BindView(R.id.address)
    TextView address;//单位地址
    @BindView(R.id.sort)
    TextView sort;//许可项目
    @BindView(R.id.children)
    TextView children;//许可子项
    @BindView(R.id.level)
    TextView level;//级别
    @BindView(R.id.type)
    TextView type;//参数
    @BindView(R.id.operating_2_state)
    ImageView operating2State;
    @BindView(R.id.equipment_1_state)
    ImageView equipment1State;
    @BindView(R.id.equipment_2_state)
    ImageView equipment2State;
    @BindView(R.id.equipment_3_state)
    ImageView equipment3State;
    @BindView(R.id.personnel_1_state)
    ImageView personnel1State;
    @BindView(R.id.personnel_2_state)
    ImageView personnel2State;
    @BindView(R.id.application_1_state)
    ImageView application1State;
    private String N_L_ID;
    private String N_B_ID;
    @Override
    protected int setContentView() {
        return R.layout.activity_backlog;
    }

    @Override
    protected void onCreate() {
        addBack();
        setTitleText("待办事项");
        getData();
        //模拟数据
        setData(null);
    }

    private void getData() {

        //Constants.URL_NOHTTP_POST
        Intent intent = getIntent();
//        Request<String> request = NoHttp.createStringRequest(UrlConfig.PATH_COMMON, RequestMethod.POST);
//        request.add("N_L_ID",intent.getStringExtra("N_L_ID"));
//        request.add("N_B_ID",intent.getStringExtra("N_B_ID"));
//        request.add("N_TYPE","0");
//        request(0,request,httpListener,true,true);

    }
//    private HttpListener<String> httpListener = new HttpListener<String>() {
//        @Override
//        public void onSucceed(int what, Response<String> response) {
//            BacklogBean backlogBean = GsonUtil.GsonToBean(response.get(), BacklogBean.class);
//            N_L_ID =  backlogBean.getObj().getN_L_ID();
//            N_B_ID = backlogBean.getObj().getN_B_ID();
//            setData(backlogBean);
//        }
//
//        @Override
//        public void onFailed(int what, Response<String> response) {
//            ToastUtils.show(BacklogActivity.this,"请求失败");
//        }
//    };

    private void setData(BacklogBean backlogBean) {
//        BacklogBean.ObjBean.ResBean bean = backlogBean.getObj().getRes();
//        BacklogBean.ObjBean.Res1Bean bean1 = backlogBean.getObj().getRes1().get(0);

        BacklogBean.ObjBean.ResBean bean = new BacklogBean.ObjBean.ResBean();
        bean.setV_C_NAME("吉林省智慧互联科技有限公司");
        bean.setV_LEGAL_PERSON("王俊杰");
        bean.setD_CREATE_DATE("2016-10-23");
        bean.setFIXED_ASSETS("500W");
        bean.setREGISTERED_CAPITAL("300W");
        bean.setV_ADDRESS("吉林省长春市前进大街2333号");
        bean.setV_TRADE("互联网/计算机");

        BacklogBean.ObjBean.Res1Bean bean1 = new BacklogBean.ObjBean.Res1Bean();
        bean1.setN_FIRST_ID("锅炉制造");
        bean1.setN_SECOND_ID("锅炉");
        bean1.setN_THIRD_ID("锅炉B");
        bean1.setV_PARA_ID("额定出口压力小于等于2.5MPa；有机热载体锅炉");

        //申请单位基本信息
        name.setText(bean.getV_C_NAME());
        legalPerson.setText(bean.getV_LEGAL_PERSON());
        industry.setText(bean.getV_TRADE());
        establishment.setText(bean.getD_CREATE_DATE());
        assets.setText(bean.getFIXED_ASSETS());
        registeredCapital.setText(bean.getREGISTERED_CAPITAL());
        address.setText(bean.getV_ADDRESS());
        //申请许可类别
        sort.setText(bean1.getN_FIRST_ID());
        children.setText(bean1.getN_SECOND_ID());
        level.setText(bean1.getN_THIRD_ID());
        type.setText(bean1.getV_PARA_ID());
    }

//    @OnClick(R.id.review)
//    public void onViewClicked() {
////        showToast("测试");
//        BottomDialogFr bottomDialogFr = new BottomDialogFr();
//        bottomDialogFr.setN_B_ID(N_B_ID);
//        bottomDialogFr.show(getSupportFragmentManager(), "BacklogActivity");
//    }

    @OnClick({R.id.review,R.id.company, R.id.apply_permission_sort, R.id.check, R.id.operating_rl_1, R.id.operating_rl_2, R.id.equipment_rl_1, R.id.equipment_rl_2, R.id.equipment_rl_3, R.id.personnel_rl_1, R.id.personnel_rl_2, R.id.application_rl_1})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        intent.putExtra("N_L_ID",N_L_ID);
        intent.putExtra("N_B_ID",N_B_ID);
        switch (view.getId()) {
            case R.id.company://申请单位基本信息
//                startActivity(new Intent(BacklogActivity.this, ApplyCompanyActivity.class));
                intent.setClass(mContext,ApplyCompanyActivity.class);
                startActivity(intent);
                break;
            case R.id.apply_permission_sort://申请许可类别
//                startActivity(new Intent(BacklogActivity.this, ApplyAllowListActivity.class));
                intent.setClass(mContext, ApplyAllowListActivity.class);
                startActivity(intent);
                break;
            case R.id.check://相关认证
                intent.setClass(mContext, CertificationListActivity.class);
                startActivity(intent);
                break;
            case R.id.operating_rl_1://试生产(制造)情况
                intent.setClass(mContext, ProduceListActivity.class);
                startActivity(intent);
                break;
            case R.id.operating_rl_2://分包、外协情况
                intent.setClass(mContext, SubcontractListActivity.class);
                startActivity(intent);
                break;
            case R.id.equipment_rl_1://自行校验仪器设备能力
                intent.setClass(mContext, SelfCheckDeviceListActivity.class);
                startActivity(intent);
                break;
            case R.id.equipment_rl_2://主要生产设备状况
                intent.setClass(mContext, PramaryDeviceListActivity.class);
                startActivity(intent);
                break;
            case R.id.equipment_rl_3://主要检验与试验仪器设备状况
                intent.setClass(mContext, InspectDeviceListActivity.class);
                startActivity(intent);
                break;
            case R.id.personnel_rl_1://管理、专业、作业人员情况
//                startActivity(new Intent(BacklogActivity.this, ManageStaffListActivity.class));
                intent.setClass(mContext, ManageStaffListActivity.class);
                startActivity(intent);
                break;
            case R.id.personnel_rl_2://各部门人员组成
//                startActivity(new Intent(BacklogActivity.this, DepartmentsActivity.class));
                intent.setClass(mContext,DepartmentsActivity.class);
                startActivity(intent);
                break;
            case R.id.application_rl_1://申请单位资源
//                startActivity(new Intent(BacklogActivity.this, ApplyUnitResouse.class));
                intent.setClass(mContext,ApplyUnitResouse.class);
                startActivity(intent);
                break;
            case R.id.review:
//                BottomDialogFr bottomDialogFr = new BottomDialogFr();
//                bottomDialogFr.setN_B_ID(N_B_ID);
//                bottomDialogFr.show(getSupportFragmentManager(), "BacklogActivity");

                intent.setClass(mContext,ReviewActvity.class);
//                intent.putExtra("N_L_ID",N_L_ID);
//                intent.putExtra("N_B_ID",N_B_ID);
                startActivity(intent);
                overridePendingTransition(R.anim.dialog_fr_in,R.anim.dialog_fr_out_hide);
                break;
        }
    }
}
