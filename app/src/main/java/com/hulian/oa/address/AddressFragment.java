package com.hulian.oa.address;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hulian.oa.MainActivity;
import com.hulian.oa.R;
import com.hulian.oa.address.adapter.PersonAdapter;
import com.hulian.oa.address.bean.People;
import com.hulian.oa.address.viewholder.FuncViewHolder;
import com.hulian.oa.me.MeActivity;
import com.hulian.oa.utils.SPUtils;
import com.netease.nim.uikit.api.model.contact.ContactsCustomization;
import com.netease.nim.uikit.business.contact.ContactsFragment;
import com.netease.nim.uikit.business.contact.core.item.AbsContactItem;
import com.netease.nim.uikit.business.contact.core.viewholder.AbsContactViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class AddressFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
//    @BindView(R.id.iv_mine)
//    ImageView ivMine;
    @BindView(R.id.contact_fragment)
    LinearLayout contactFragment;
    private PersonAdapter mListAdapter;
    private List<People> sourceList;
    private List<People> newList;

    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.listview)
    ListView listview;

    @BindView(R.id.tv_type)
    TextView tv_type;
    @BindView(R.id.iv_image)
    FrameLayout iv_image;

    private ContactsFragment fragment;

    public AddressFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Main1_home.
     */
    // TODO: Rename and change types and number of parameters
    public static AddressFragment newInstance(String requestJson) {
        AddressFragment fragment = new AddressFragment();
        Bundle args = new Bundle();
//        args.putString("requestJson", requestJson);
//        args.putString("gid", gid);
//        args.putString("idno", idno);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            gid = getArguments().getString("gid");
//            idno=getArguments().getString("idno");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_address, container, false);
        ButterKnife.bind(this, view);

        FragmentManager fragment1 = getFragmentManager();
        //2,碎片的显示需要使用FragmentTransaction类操作
        FragmentTransaction transacction = fragment1.beginTransaction();
        fragment = new ContactsFragment();
//        fragment.setContainerId(R.id.contact_fragment);
        transacction.replace(R.id.contact_fragment, fragment);
        //使用FragmentTransaction必须要commit
        transacction.commit();
        // 功能项定制
        fragment.setContactsCustomization(new ContactsCustomization() {
            @Override
            public Class<? extends AbsContactViewHolder<? extends AbsContactItem>> onGetFuncViewHolderClass() {
                return FuncViewHolder.class;
            }

            @Override
            public List<AbsContactItem> onGetFuncItems() {
                return FuncViewHolder.FuncItem.provide();
            }

            @Override
            public void onFuncItemClick(AbsContactItem item) {
                FuncViewHolder.FuncItem.handle(getActivity(), item);
            }
        });

//
        mListAdapter = new PersonAdapter(getActivity(), initData());
        listview.setAdapter(mListAdapter);
    /*    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(getActivity(), ActivityPerson.class));
            }
        });*/
        mListAdapter.notifyDataSetChanged();
        //meditText监听文本变化
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //告诉adapter 文本有变化了
                changeText(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        tv_type.setText(SPUtils.get(getActivity(), "nickname", "").toString().substring(SPUtils.get(getActivity(), "nickname", "").toString().length()-2,SPUtils.get(getActivity(), "nickname", "").toString().length()));
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FuncViewHolder.unRegisterUnreadNumChangedCallback();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private List<People> initData() {
        sourceList = new ArrayList<>();
        People people = new People();
        people.setName("王俊杰");
        People people2 = new People();
        people2.setName("段云龙");
        sourceList.add(people);
        sourceList.add(people2);
        return sourceList;
    }

    //这个方法很重要，editText监听文本变化需要用到
    public void changeText(String textStr) {
        newList = new ArrayList<>();
        int length = sourceList.size();
        for (int i = 0; i < length; ++i) {
            if (sourceList.get(i).getName().contains(textStr)) {
                People item = sourceList.get(i);
                newList.add(item);
            }
        }
        mListAdapter = new PersonAdapter(getActivity(), newList);
        listview.setAdapter(mListAdapter);
    }

    @OnClick(R.id.iv_image)
    public void onViewClicked() {
//        startActivity(new Intent(getActivity(), MeActivity.class));
        EventBus.getDefault().post(new MainActivity());

    }


}
