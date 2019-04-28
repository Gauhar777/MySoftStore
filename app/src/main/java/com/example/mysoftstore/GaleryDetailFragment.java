package com.example.mysoftstore;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class GaleryDetailFragment extends ListFragment {
    int[] images={R.drawable.c011,R.drawable.c021,R.drawable.c031};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        List<HashMap<String, String>> detailImgList =new ArrayList<HashMap<String,String>>();;
        int i=0;
        while (i<images.length){
            int name=images[i];
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("name", String.valueOf(name));
            detailImgList.add(hm);
            i++;
        }
        String[] from={"name"};
        int[] to={R.id.image_galery_detail};
        SimpleAdapter detailGaleryAdapter = new SimpleAdapter(getContext(),detailImgList,R.layout.fragment_galery_detail,from,to);
        setListAdapter(detailGaleryAdapter);
        return super.onCreateView(inflater,container,savedInstanceState);
    }

}
