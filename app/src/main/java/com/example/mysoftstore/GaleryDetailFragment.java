package com.example.mysoftstore;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class GaleryDetailFragment extends ListFragment {

    private long productId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        //Toast.makeText(getActivity(),productId+"************",Toast.LENGTH_SHORT).show();
        int[] images = {R.drawable.ph3, R.drawable.ph2, R.drawable.ph4};
        int[] images2 ={R.drawable.comp2, R.drawable.comp3, R.drawable.computer};
        int[] images3 ={R.drawable.c011, R.drawable.c021, R.drawable.c031};
        int[] images4 ={R.drawable.tv, R.drawable.tv1, R.drawable.tv3};

        List<HashMap<String, String>> detailImgList =new ArrayList<HashMap<String,String>>();;
        int i=0;
        while (i<images.length){
            int name = 0;
            if(productId==-1) {
                name = images2[i];
            }else if (productId==0){
                name = images3[i];
            }else if (productId==1){
                name = images[i];
            }else if (productId==2){
                name = images4[i];
            }
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("name", String.valueOf(name));
            detailImgList.add(hm);
            i++;
        }
        String[] from={"name"};
        int[] to={R.id.image_galery_detail};
        SimpleAdapter detailGaleryAdapter = new SimpleAdapter(getContext(),detailImgList,R.layout.fragment_galery_detail,from,to);
        setListAdapter(detailGaleryAdapter);
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }
}
