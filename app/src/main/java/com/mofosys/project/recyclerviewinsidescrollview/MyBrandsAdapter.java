package com.mofosys.project.recyclerviewinsidescrollview;

/**
 * Created by girish on 26/7/17.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by girish on 20/6/17.
 */

public class MyBrandsAdapter extends RecyclerView.Adapter<MyBrandsAdapter.MyViewHolder> {

    private ArrayList<UpdateBrandsModel> myBrandsModelList;

    public MyBrandsAdapter(ArrayList<UpdateBrandsModel> myBrandsModelList) {
        this.myBrandsModelList = myBrandsModelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_my_brands, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final UpdateBrandsModel my_brands = myBrandsModelList.get(position);
        holder.brand_name.setText(my_brands.getMy_brand_name());

    }

    @Override
    public int getItemCount() {
        return myBrandsModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView brand_name;

        public MyViewHolder(View itemView) {
            super(itemView);
            brand_name = (TextView) itemView.findViewById(R.id.brand_name_my_brands);
        }
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }


}
