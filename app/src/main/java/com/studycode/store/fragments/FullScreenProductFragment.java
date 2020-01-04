package com.studycode.store.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.studycode.store.R;
import com.studycode.store.customviews.ScalingImageView;
import com.studycode.store.models.Product;


public class FullScreenProductFragment extends Fragment {

    private static final String TAG = "FullScreenProductFragme";
    //widgets
    private ScalingImageView mImageView;
    public Product mProduct;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();

        if(bundle != null){
            mProduct = bundle.getParcelable(getString(R.string.intent_product));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_full_screen_product, container, false);
        mImageView = view.findViewById(R.id.scale_image);
        setProduct();
        return view;
    }

    private void setProduct() {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);
        Glide.with(getActivity())
                .setDefaultRequestOptions(requestOptions)
                .load(mProduct.getImage())
                .into(mImageView);
    }

}
