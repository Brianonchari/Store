package com.studycode.store.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.Display;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.studycode.store.R;
import com.studycode.store.adapters.ProductPagerAdapter;
import com.studycode.store.customviews.MyDragShadowBuilder;
import com.studycode.store.fragments.FullScreenProductFragment;
import com.studycode.store.fragments.ViewProductFragment;
import com.studycode.store.models.Product;
import com.studycode.store.resources.Products;
import com.studycode.store.utils.CartManger;

import java.util.ArrayList;

public class ViewProductActivity extends AppCompatActivity implements View.OnTouchListener,
        GestureDetector.OnGestureListener ,
        GestureDetector.OnDoubleTapListener,
        View.OnClickListener,
        View.OnDragListener {

    private static final String TAG = "ViewProductActivity";


    //Widgets
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private RelativeLayout mAddToCart, mCart;
    private ImageView mCartIcon, mPlusIcon;

    private ProductPagerAdapter productPagerAdapter;
    private Product product;
    private GestureDetector mGestureDetector;
    private Rect mCartPositionRectangle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        Log.d(TAG, "onCreate: Started");
        mTabLayout = findViewById(R.id.tabLayout);
        mViewPager = findViewById(R.id.product_container);
        mAddToCart = findViewById(R.id.add_to_cart);
        mCart = findViewById(R.id.cart);
        mPlusIcon = findViewById(R.id.plus_image);
        mCartIcon = findViewById(R.id.cart_image);

        mCart.setOnClickListener(this);
        mAddToCart.setOnClickListener(this);
        mViewPager.setOnTouchListener(this);
        mGestureDetector = new GestureDetector(this, this);
        getIncomingIntent();
        initPagerAdapter();
    }

    private void initPagerAdapter() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        Products products = new Products();
        Product[] selectedProduct = products.PRODUCT_MAP.get(product.getType());
        for (Product product : selectedProduct) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("intent_product", product);
            ViewProductFragment viewProductFragment = new ViewProductFragment();
            viewProductFragment.setArguments(bundle);
            fragments.add(viewProductFragment);
        }

        productPagerAdapter = new ProductPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(productPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager, true);
    }

    private void addCurrentItemToCart() {
        Product selectedProduct = ((ViewProductFragment) productPagerAdapter.getItem(mViewPager.getCurrentItem())).mProduct;
        CartManger cartManger = new CartManger(this);
        cartManger.addItemToCart(selectedProduct);
        Toast.makeText(this, "added to cart", Toast.LENGTH_SHORT).show();
    }

    private void getCartPosition(){
        mCartPositionRectangle = new Rect();
        mCart.getGlobalVisibleRect(mCartPositionRectangle);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        mCartPositionRectangle.left = mCartPositionRectangle.left - Math.round((int)(width * 0.18));
        mCartPositionRectangle.top = 0;
        mCartPositionRectangle.right = width;
        mCartPositionRectangle.bottom = mCartPositionRectangle.bottom - Math.round((int)(width * 0.03));
    }

    private void setDragMode(boolean isDragging){
        if(isDragging){
            mCartIcon.setVisibility(View.INVISIBLE);
            mPlusIcon.setVisibility(View.VISIBLE);
        }
        else{
            mCartIcon.setVisibility(View.VISIBLE);
            mPlusIcon.setVisibility(View.INVISIBLE);
        }
    }

    private void getIncomingIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra("intent_product")) {
            product = intent.getParcelableExtra("intent_product");
        }
    }

    private void inflateFullScreenProductFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        FullScreenProductFragment fragment = new FullScreenProductFragment();

        Bundle bundle = new Bundle();
        Product selectedProduct =((ViewProductFragment)productPagerAdapter.getItem(mViewPager.getCurrentItem())).mProduct;
        bundle.putParcelable(getString(R.string.intent_product), selectedProduct);
        fragment.setArguments(bundle);

        // Enter Transition for New Fragment
        Fade enterFade = new Fade();
        enterFade.setStartDelay(1);
        enterFade.setDuration(300);
        fragment.setEnterTransition(enterFade);

        transaction.addToBackStack(getString(R.string.fragment_full_screen_product));
        transaction.replace(R.id.full_screen_container, fragment, getString(R.string.fragment_full_screen_product));
        transaction.commit();
    }

    //    OnTouch Listener
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        getCartPosition();
        //Pass touch event action to gesture detector
        if (v.getId() == R.id.product_container) {
            mGestureDetector.onTouchEvent(event);
        }
        return false;
    }

    //    GestureDetector
    @Override
    public boolean onDown(MotionEvent e) {
        Log.d(TAG, "onDown: called");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.d(TAG, "onShowPress: called");

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d(TAG, "onSingleTapUp: called");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.d(TAG, "onScroll: Called");
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.d(TAG, "onLongPress: called");

        ViewProductFragment fragment = ((ViewProductFragment)productPagerAdapter.getItem(mViewPager.getCurrentItem()));
        // Instantiates the drag shadow builder.
        View.DragShadowBuilder myShadow = new MyDragShadowBuilder(fragment.imageView, fragment.mProduct.getImage());
        // Starts the drag
        fragment.imageView.startDrag(null, myShadow, null, 0);
        myShadow.getView().setOnDragListener(this);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d(TAG, "onFling: called");
        return false;
    }

    //Tap Listener
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Log.d(TAG, "onSingleTapConfirmed: called");
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.d(TAG, "onDoubleTap: called");
        inflateFullScreenProductFragment();
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        Log.d(TAG, "onDoubleTapEvent: called");
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cart: {
                Intent intent = new Intent(this, ViewCartActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.add_to_cart: {
                addCurrentItemToCart();
                break;
            }
        }
    }
    //OnDrag Listener
    @Override
    public boolean onDrag(View v, DragEvent event) {
        switch(event.getAction()) {

            case DragEvent.ACTION_DRAG_STARTED:
                Log.d(TAG, "onDrag: drag started.");
                setDragMode(true);
                return true;

            case DragEvent.ACTION_DRAG_ENTERED:
                return true;

            case DragEvent.ACTION_DRAG_LOCATION:
                Point currentPoint = new Point(Math.round(event.getX()), Math.round(event.getY()));
                if(mCartPositionRectangle.contains(currentPoint.x, currentPoint.y)){
                    mCart.setBackgroundColor(this.getResources().getColor(R.color.blue2));
                }
                else{
                    mCart.setBackgroundColor(this.getResources().getColor(R.color.blue1));
                }
                return true;

            case DragEvent.ACTION_DRAG_EXITED:
                return true;

            case DragEvent.ACTION_DROP:
                Log.d(TAG, "onDrag: dropped.");
                return true;

            case DragEvent.ACTION_DRAG_ENDED:
                Log.d(TAG, "onDrag: ended.");
                Drawable background = mCart.getBackground();
                if (background instanceof ColorDrawable) {
                    if (((ColorDrawable) background).getColor() == getResources().getColor(R.color.blue2)) {
                        addCurrentItemToCart();
                    }
                }
                mCart.setBackground(this.getResources().getDrawable(R.drawable.blue_onclick_dark));
                setDragMode(false);
                return true;
            default:
                Log.e(TAG,"Unknown action type received by OnStartDragListener.");
                break;
        }
        return false;
    }
}
