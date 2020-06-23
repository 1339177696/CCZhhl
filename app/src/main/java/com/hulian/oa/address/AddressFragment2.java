package com.hulian.oa.address;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.AddresFragmentDetelis;
import com.hulian.oa.R;
import com.hulian.oa.address.activity.OrganizationActivity;
import com.hulian.oa.adpter.SortAdapter;
import com.hulian.oa.bean.SortModel;
import com.hulian.oa.me.activity.MeActivity;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.CharacterParser;
import com.hulian.oa.utils.PinyinComparator;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.views.SideBar;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class AddressFragment2 extends Fragment {

    @BindView(R.id.tv_type)
    TextView tv_type;
    private SideBar sideBar; // 右边的引导
    private TextView dialog;
    private SortAdapter adapter; // 排序的适配器
    private CharacterParser characterParser;
    private List<SortModel> SourceDateList; // 数据
    private PinyinComparator pinyinComparator;
    private ListView sortListView;
    public AddressFragment2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Main1_home.
     */
    // TODO: Rename and change types and number of parameters
    public static AddressFragment2 newInstance(String requestJson) {
        AddressFragment2 fragment = new AddressFragment2();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_address2, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        iniTview();
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        sideBar = (SideBar) view.findViewById(R.id.sidrbar);
        dialog = (TextView) view.findViewById(R.id.dialog);
        sideBar.setTextView(dialog);
        /**
         * 为右边添加触摸事件
         */
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }
            }
        });

        sortListView = (ListView) view.findViewById(R.id.country_lvcountry);
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                }else if (position == 1){
                    startActivity(new Intent(getActivity(), OrganizationActivity.class));
                }
                else {
                    //Toast.makeText(getActivity(), ((SortModel) adapter.getItem(position)).getUserName(), Toast.LENGTH_SHORT).show();
                   // NimUIKitImpl.getContactEventListener().onItemClick(getActivity(), ((SortModel) adapter.getItem(position)).getLoginName());
                    Intent intent = new Intent(getActivity(), AddresFragmentDetelis.class);
                    intent.putExtra("userName",((SortModel) adapter.getItem(position)).getUserName());
                    intent.putExtra("email",((SortModel) adapter.getItem(position)).getEmail());
                    intent.putExtra("loginName",((SortModel) adapter.getItem(position)).getLoginName());
                    intent.putExtra("phone",((SortModel) adapter.getItem(position)).getPhonenumber());
                    startActivity(intent);
                }
            }

        });
        getData();
        return view;
    }

    public void iniTview(){
        tv_type.setText(SPUtils.get(getActivity(), "nickname", "").toString().substring(SPUtils.get(getActivity(), "nickname", "").toString().length()-2,SPUtils.get(getActivity(), "nickname", "").toString().length()));
    }

    public void onEventMainThread(AddressFragment2 event) {
        iniTview();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }



    @OnClick(R.id.tv_type)
    public void onViewClicked() {
        startActivity(new Intent(getActivity(), MeActivity.class));
    }


    private void getData() {
        RequestParams params = new RequestParams();
        params.put("loginName", SPUtils.get(getActivity(), "username", "").toString());
        HttpRequest.getAddress_list(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                //需要转化为实体对象
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    List<SortModel> alist = gson.fromJson(result.getJSONArray("data").toString(), new TypeToken<List<SortModel>>() {}.getType());
                    SourceDateList = filledData(alist);// 填充数据
                    Collections.sort(SourceDateList, pinyinComparator);
                    adapter = new SortAdapter(getActivity(), SourceDateList);
                    sortListView.setAdapter(adapter);

                    /**
                     * 设置滚动监听， 实时跟新悬浮的字母的值
                     */
                    sortListView.setOnScrollListener(new AbsListView.OnScrollListener() {

                        @Override
                        public void onScrollStateChanged(AbsListView view, int scrollState) {
                            // TODO Auto-generated method stub
                        }

                        @Override
                        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Toast.makeText(getActivity(), "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 填充数据
     *
     * @param date
     * @return
     */
    private List<SortModel> filledData(List<SortModel>date) {
        List<SortModel> mSortList = new ArrayList<SortModel>();
        for (int i = 0; i < date.size(); i++) {
            SortModel sortModel = new SortModel();
            sortModel.setUserName(date.get(i).getUserName());
            sortModel.setLoginName(date.get(i).getLoginName());
            sortModel.setEmail(date.get(i).getEmail());
            sortModel.setPhonenumber(date.get(i).getPhonenumber());
            String pinyin = characterParser.getSelling(date.get(i).getUserName());
            String sortString = pinyin.substring(0, 1).toUpperCase();
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }
            mSortList.add(sortModel);
        }
        return mSortList;

    }
}
