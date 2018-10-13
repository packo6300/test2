package com.packo.iptv.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import com.packo.iptv.R;
import com.packo.iptv.beans.ItemBean;

import java.util.List;

public class CanalesAdapter extends BaseAdapter {
    private Context context;
    Bitmap bmp = null;
    private int layout;
    private List<ItemBean> names;

    public CanalesAdapter(Context context, int layout, List<ItemBean> names){
        this.context=context;
        this.layout=layout;
        this.names=names;
    }

    @Override
    public int getCount() {
        return this.names.size();
    }

    @Override
    public Object getItem(int position) {
        return this.names.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            LayoutInflater layoutInflater= LayoutInflater.from(this.context);
            convertView=layoutInflater.inflate(this.layout,null);
            holder = new ViewHolder();
            holder.tituloTextView = convertView.findViewById(R.id.nombre_item);
            holder.imgImageView = convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        }
        else {
            holder=(ViewHolder) convertView.getTag();
        }
        ItemBean publicacion = names.get(position);
        holder.tituloTextView.setText(publicacion.getNombre());
        holder.setText(publicacion.getUrl());
        String imgUrl=publicacion.getImagen();
        Picasso.get().load(imgUrl)
                .resize(100, 100)
                .centerCrop().into(holder.imgImageView);
        return convertView;
    }
    static class ViewHolder{
        private TextView tituloTextView;
        private String categotiaTextView;
        private ImageView imgImageView;

        public String getText() {
            return categotiaTextView;
        }

        public void setText(String categotiaTextView) {
            this.categotiaTextView = categotiaTextView;
        }
    }

}
