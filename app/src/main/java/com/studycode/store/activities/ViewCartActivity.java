package com.studycode.store.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.studycode.store.R;
import com.studycode.store.adapters.CartRecyclerViewAdapter;
import com.studycode.store.models.Product;
import com.studycode.store.resources.ProductHeaders;
import com.studycode.store.utils.CartManger;

import java.math.BigDecimal;
import java.util.ArrayList;

public class ViewCartActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ViewCartActivity";

    //widgets
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFab;

    //vars
    CartRecyclerViewAdapter mAdapter;
    private ArrayList<Product> mProducts = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);
        mRecyclerView = findViewById(R.id.recycler_view);
        mFab = findViewById(R.id.fab);

        mFab.setOnClickListener(this);

        getProducts();
        initRecyclerView();
    }

    private void getProducts(){
        mProducts.add(new Product(ProductHeaders.HEADER_TITLES[0], 0, "", new BigDecimal(0), 0));
        mProducts.add(new Product(ProductHeaders.HEADER_TITLES[1], 0, "", new BigDecimal(0), 0));
        mProducts.add(new Product(ProductHeaders.HEADER_TITLES[2], 0, "", new BigDecimal(0), 0));

        CartManger cartManger = new CartManger(this);
        mProducts = cartManger.getCartItems();
    }

    private void initRecyclerView(){
        mAdapter = new CartRecyclerViewAdapter(this, mProducts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        //wait for the recyclerview to finish loading the views
        mRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.M){
                    mRecyclerView.setOnScrollListener(new CartScrollListener());
                }
                else{
                    mRecyclerView.addOnScrollListener(new CartScrollListener());
                }
            }
        });
    }


    private void setFABVisibility(boolean isVisible){
        Animation animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
        Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
        if(isVisible){
            mFab.setAnimation(animFadeIn);
            mFab.setVisibility(View.VISIBLE);
        }
        else{
            mFab.setAnimation(animFadeOut);
            mFab.setVisibility(View.INVISIBLE);
        }
    }

    public boolean isRecyclerScrollable() {
        return mRecyclerView.computeVerticalScrollRange() > mRecyclerView.getHeight();
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.fab){
            mRecyclerView.smoothScrollToPosition(0);
        }
    }

    class CartScrollListener extends RecyclerView.OnScrollListener{
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                Log.d(TAG, "onScrollStateChanged: stopped...");
            }
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                Log.d(TAG, "onScrollStateChanged: fling.");
            }
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                Log.d(TAG, "onScrollStateChanged: touched.");
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if(isRecyclerScrollable()){
                if(!recyclerView.canScrollVertically(1)){
                    setFABVisibility(true);
                }
                else{
                    setFABVisibility(false);
                }
            }
        }
    }

}
