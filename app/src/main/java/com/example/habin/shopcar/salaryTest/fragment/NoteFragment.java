package com.example.habin.shopcar.salaryTest.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.habin.shopcar.R;
import com.example.habin.shopcar.salaryTest.Entity.recordDetailsEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NoteFragment extends Fragment {


    Unbinder unbinder;
    @BindView(R.id.tv_note_context)
    TextView mTvNoteContext;

    public static NoteFragment newInstance(recordDetailsEntity.RecordData data) {
        NoteFragment fragment = new NoteFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", data);
        fragment.setArguments(bundle);
        return fragment;
    }

    private View mView;

    private recordDetailsEntity.RecordData mData;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView != null) {
            ViewGroup parent = (ViewGroup) mView.getParent();
            if (parent != null) {
                parent.removeView(mView);
            }
            return mView;
        }
        mView = inflater.inflate(R.layout.fragment_note, container, false);

        unbinder = ButterKnife.bind(this, mView);
        Bundle bundle = this.getArguments();
        mData = (recordDetailsEntity.RecordData) bundle.getSerializable("data");
        initView();
        return mView;
    }

    private void initView() {
        recordDetailsEntity.RecordData.salaryRecordInfoEditVO data = mData.getSalaryRecordInfoEditVOList().get(0);

        mTvNoteContext.setText(data.getFieldValue());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
