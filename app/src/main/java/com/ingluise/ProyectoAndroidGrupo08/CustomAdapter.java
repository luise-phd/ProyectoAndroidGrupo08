package com.ingluise.ProyectoAndroidGrupo08;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private static final String TAG = CustomAdapter.class.getSimpleName();

    private String[] mDataSet;

    public CustomAdapter(String[] dataSet) {
        mDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.i(TAG, "Elemento " + position + " seleccionado");
        viewHolder.getTextView().setText(mDataSet[position]);
    }

    @Override
    public int getItemCount() {
        return mDataSet.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv;
        private final ImageView iv;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i(TAG, "Elemento " + getAdapterPosition() + " seleccionado" );
                }
            });
            tv = view.findViewById(R.id.textView17);
            iv = view.findViewById(R.id.imageView6);
            iv.setImageResource(R.mipmap.ic_apple);
        }

        public TextView getTextView() {
            return tv;
        }

        public ImageView getImageView() {
            return iv;
        }
    }
}
