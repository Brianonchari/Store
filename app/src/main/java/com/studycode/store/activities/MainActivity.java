package com.studycode.store.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.studycode.store.R;
import com.studycode.store.adapters.MainRecyclerViewAdapter;
import com.studycode.store.models.Product;
import com.studycode.store.resources.Products;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener
{

    private static final String TAG = "MainActivity";
    private static final int NUM_COLUMNS = 2;

    //vars
    MainRecyclerViewAdapter mAdapter;
    private ArrayList<Product> mProducts = new ArrayList<>();

    //widgets
    private RecyclerView mRecyclerView;
    private RelativeLayout mCart;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recycler_view);
//        mCart = findViewById(R.id.main_cart);
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        getProducts();
        initRecyclerView();
    }

    private void getProducts(){
        mProducts.addAll(Arrays.asList(Products.FEATURED_PRODUCTS));
    }

    private void initRecyclerView(){
        mAdapter = new MainRecyclerViewAdapter(this, mProducts);
        GridLayoutManager layoutManager = new GridLayoutManager(this, NUM_COLUMNS);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View view) {

//        switch(view.getId()){
////            case R.id.cart:{
////                //open Cart Activity
////                Intent intent = new Intent(this, ViewCartActivity.class);
////                startActivity(intent);
//                break;
//            }
        }


    @Override
    public void onRefresh() {
        Collections.shuffle(mProducts);
        onItemsLoadComplete();
    }

    void onItemsLoadComplete() {
        (mRecyclerView.getAdapter()).notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
