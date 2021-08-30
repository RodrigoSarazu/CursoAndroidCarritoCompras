package com.front.ecommerceapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.front.ecommerceapp.R;
import com.front.ecommerceapp.entity.SliderItem;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterVH> {

    private Context context;
    private List<SliderItem>mSliderItems=new ArrayList<>();

    public SliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        //estamos trayendo al Layout image_slider_layout_item
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item,null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {

        SliderItem sliderItem=mSliderItems.get(position);
        viewHolder.textView.setText(sliderItem.getTitulo());
        viewHolder.textView.setTextSize(16);
        viewHolder.textView.setTextColor(Color.WHITE);
        Glide.with(viewHolder.itemView)
                .load(sliderItem.getImagen())
                .fitCenter()
                .into(viewHolder.imageView);

       /*Para traer un mensaje de la posición
       viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show();
            }
        });*/

    }

    @Override
    public int getCount() {
        return mSliderItems.size();
    }
    public void updateItem(List<SliderItem> lista) {
        mSliderItems.clear();
        mSliderItems.addAll(lista);
        this.notifyDataSetChanged();
    }


    //Holder trayendo los objetos del image_slider_layout_item
    protected class SliderAdapterVH extends SliderViewAdapter.ViewHolder{
        View itemView;
        ImageView imageView;
        TextView textView;
        public SliderAdapterVH(View itemView){
            super(itemView);
            imageView=itemView.findViewById(R.id.iv_auto_image_slider);
            textView=itemView.findViewById(R.id.tv_auto_image_slider);
            this.itemView=itemView;
        }
    }

}
