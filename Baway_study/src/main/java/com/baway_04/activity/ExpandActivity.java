package com.baway_04.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ExpandableListView;

import com.baway_04.R;
import com.baway_04.adapter.ExPandAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExpandActivity extends Activity {

    @BindView(R.id.expand_list)
    ExpandableListView expandableListView;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expand_activity);

        ButterKnife.bind(this);


        ExPandAdapter exPandAdapter = new ExPandAdapter(this);
        expandableListView.setAdapter(exPandAdapter);

        for (int x =0;x<exPandAdapter.getDataCount();x++){
            expandableListView.expandGroup(x);
        }

    }
}
