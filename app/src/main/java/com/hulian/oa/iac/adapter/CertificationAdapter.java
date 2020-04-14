package com.hulian.oa.iac.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hulian.oa.R;
import com.hulian.oa.iac.bean.Certification;
import com.hulian.oa.iac.listener.EditTextChangeListener;

import java.util.List;

/**
 * Created by 陈泽宇 on 2019/12/5.
 * Describe:
 */
public class CertificationAdapter extends BaseQuickAdapter<Certification, BaseViewHolder> {

    private EditTextChangeListener listener;
    private boolean toChange = false;

    public CertificationAdapter(List<Certification> data, EditTextChangeListener listener) {
        super(R.layout.item_certification_list, data);
        this.listener = listener;
    }

    @Override
    protected void convert(BaseViewHolder helper, Certification item) {
        helper.setText(R.id.name_text, item.getName());
        helper.setText(R.id.name_edit, item.getName());
        helper.setText(R.id.organization_text, item.getOrganization());
        helper.setText(R.id.organization_edit, item.getOrganization());
        helper.setText(R.id.data_text, item.getData());
        helper.setText(R.id.data_edit, item.getData());
        helper.setText(R.id.validity_text, item.getValidity());
        helper.setText(R.id.validity_edit, item.getValidity());

        int position = helper.getLayoutPosition();
        EditText nameEdit = helper.getView(R.id.name_edit);
        EditText organizationEdit = helper.getView(R.id.organization_edit);
        EditText dataEdit = helper.getView(R.id.data_edit);
        EditText validityEdit = helper.getView(R.id.validity_edit);

        nameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.setName(s.toString());
                listener.haveChange(item, position);
            }
        });

        organizationEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.setOrganization(s.toString());
                listener.haveChange(item, position);
            }
        });

        dataEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.setData(s.toString());
                listener.haveChange(item, position);
            }
        });

        validityEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.setValidity(s.toString());
                listener.haveChange(item, position);
            }
        });

        if (toChange) {
            helper.setVisible(R.id.name_text, false);
            helper.setVisible(R.id.name_edit, true);
            helper.setVisible(R.id.organization_text, false);
            helper.setVisible(R.id.organization_edit, true);
            helper.setVisible(R.id.data_text, false);
            helper.setVisible(R.id.data_edit, true);
            helper.setVisible(R.id.validity_text, false);
            helper.setVisible(R.id.validity_edit, true);
        } else {
            helper.setVisible(R.id.name_text, true);
            helper.setVisible(R.id.name_edit, false);
            helper.setVisible(R.id.organization_text, true);
            helper.setVisible(R.id.organization_edit, false);
            helper.setVisible(R.id.data_text, true);
            helper.setVisible(R.id.data_edit, false);
            helper.setVisible(R.id.validity_text, true);
            helper.setVisible(R.id.validity_edit, false);
        }

    }

    public void setToChange(boolean change){
        toChange = change;
        notifyDataSetChanged();
    }


}
