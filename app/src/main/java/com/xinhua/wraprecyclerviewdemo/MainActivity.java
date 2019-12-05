package com.xinhua.wraprecyclerviewdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xinhua.wraprecyclerviewdemo.recyclerview.MyRecyclerAdapter;
import com.xinhua.wraprecyclerviewdemo.recyclerview.WrapRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private WrapRecyclerView mRecyclerView;
    private Button mBtnAddHeaderTop, mBtnAddHeaderCenter, mBtnAddHeaderBottom;
    private Button mBtnAddFooterTop, mBtnAddFooterCenter, mBtnAddFooterBottom;
    private Button mBtnDelHeaderIndex, mBtnDelHeaderView, mBtnDelHeaderBottom;
    private Button mBtnDelFooterIndex, mBtnDelFooterView, mBtnDelFooterBottom;

    private TextView mHeadertexttView1, mHeadertexttView2, mHeadertexttView3, mHeadertexttView4;
    private TextView mFootertexttView1, mFootertexttView2, mFootertexttView3, mFootertexttView4;


    private List<String> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mBtnAddHeaderTop = findViewById(R.id.btn_addHeader_top);
        mBtnAddHeaderCenter = findViewById(R.id.btn_addHeader_center);
        mBtnAddHeaderBottom = findViewById(R.id.btn_addHeader_bottom);
        mBtnAddFooterTop = findViewById(R.id.btn_addFooter_top);
        mBtnAddFooterCenter = findViewById(R.id.btn_addFooter_center);
        mBtnAddFooterBottom = findViewById(R.id.btn_addFooter_bottom);

        mBtnDelHeaderIndex = findViewById(R.id.btn_deleteHeader_index);
        mBtnDelHeaderView = findViewById(R.id.btn_deleteHeader_view);
        mBtnDelHeaderBottom = findViewById(R.id.btn_deleteHeader_bottom);
        mBtnDelFooterIndex = findViewById(R.id.btn_deleteFooter_index);
        mBtnDelFooterView = findViewById(R.id.btn_deleteFooter_view);
        mBtnDelFooterBottom = findViewById(R.id.btn_deleteFooter_bottom);

        mBtnAddHeaderTop.setOnClickListener(this);
        mBtnAddHeaderCenter.setOnClickListener(this);
        mBtnAddHeaderBottom.setOnClickListener(this);
        mBtnAddFooterTop.setOnClickListener(this);
        mBtnAddFooterCenter.setOnClickListener(this);
        mBtnAddFooterBottom.setOnClickListener(this);
        mBtnDelHeaderIndex.setOnClickListener(this);
        mBtnDelHeaderView.setOnClickListener(this);
        mBtnDelHeaderBottom.setOnClickListener(this);
        mBtnDelFooterIndex.setOnClickListener(this);
        mBtnDelFooterView.setOnClickListener(this);
        mBtnDelFooterBottom.setOnClickListener(this);

        //新增头部
        mHeadertexttView1 = new TextView(this);
        mHeadertexttView1.setText("新增头部文字1");
        mHeadertexttView2 = new TextView(this);
        mHeadertexttView2.setText("新增头部文字2");
        mHeadertexttView3 = new TextView(this);
        mHeadertexttView3.setText("新增头部文字3");
        mHeadertexttView4 = new TextView(this);
        mHeadertexttView4.setText("新增头部文字4");

        //新增底部
        mFootertexttView1 = new TextView(this);
        mFootertexttView1.setText("新增底部文字1");
        mFootertexttView2 = new TextView(this);
        mFootertexttView2.setText("新增底部文字2");
        mFootertexttView3 = new TextView(this);
        mFootertexttView3.setText("新增底部文字3");
        mFootertexttView4 = new TextView(this);
        mFootertexttView4.setText("新增底部文字4");

        //添加
        mRecyclerView.addHeaderView(mHeadertexttView1);
        mRecyclerView.addHeaderView(mHeadertexttView2);
        mRecyclerView.addHeaderView(mHeadertexttView3);
        mRecyclerView.addHeaderView(mHeadertexttView4);

        mRecyclerView.addFooterView(mFootertexttView1);
        mRecyclerView.addFooterView(mFootertexttView2);
        mRecyclerView.addFooterView(mFootertexttView3);
        mRecyclerView.addFooterView(mFootertexttView4);

    }

    private void initData() {
        mList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mList.add("测试数据 -> " + i);
        }
        mRecyclerView.setAdapter(new MyRecyclerAdapter(this, mList));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_addHeader_top:
                mRecyclerView.addHeaderView(R.layout.layout_header_view1, 0);
                break;
            case R.id.btn_addHeader_center:
                View headerLayoutView = LayoutInflater.from(this).inflate(R.layout.layout_header_view2, null, false);
                mRecyclerView.addHeaderView(headerLayoutView, 2);
                break;
            case R.id.btn_addHeader_bottom:
                mRecyclerView.addHeaderView(R.layout.layout_header_view3);
                break;
            case R.id.btn_addFooter_top:
                View footerLayoutView = LayoutInflater.from(this).inflate(R.layout.layout_footer_view1, null, false);
                mRecyclerView.addFooterView(footerLayoutView, 0);
                break;
            case R.id.btn_addFooter_center:
                mRecyclerView.addFooterView(R.layout.layout_footer_view2, 2);
                break;
            case R.id.btn_addFooter_bottom:
                mRecyclerView.addFooterView(R.layout.layout_footer_view3);
                break;
            case R.id.btn_deleteHeader_index:
                mRecyclerView.removeHeaderViewWithIndex(1);
                break;
            case R.id.btn_deleteHeader_view:
                mRecyclerView.removeHeaderView(mHeadertexttView3);
                break;
            case R.id.btn_deleteHeader_bottom:
                mRecyclerView.removeHeaderViewWithIndex(mRecyclerView.getHeaderItemCount() - 1);
                break;
            case R.id.btn_deleteFooter_index:
                mRecyclerView.removeFooterViewWithIndex(1);
                break;
            case R.id.btn_deleteFooter_view:
                mRecyclerView.removeFooterView(mFootertexttView3);
                break;
            case R.id.btn_deleteFooter_bottom:
                mRecyclerView.removeFooterViewWithIndex(mRecyclerView.getFooterItemCount() - 1);
                break;
        }
    }
}
