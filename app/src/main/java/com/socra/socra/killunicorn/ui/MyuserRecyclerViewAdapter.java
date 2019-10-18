package com.socra.socra.killunicorn.ui;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.socra.socra.killunicorn.R;
import com.socra.socra.killunicorn.clases.usuarios;


import java.util.List;


public class MyuserRecyclerViewAdapter extends RecyclerView.Adapter<MyuserRecyclerViewAdapter.ViewHolder> {

    private final List<usuarios> mValues;


    public MyuserRecyclerViewAdapter(List<usuarios> items) {
        mValues = items;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        int pos = position +1;
        holder.textviewposition.setText(pos+ "º");
        holder.textviewpatos.setText(String.valueOf(mValues.get(position).getPatos()));
        holder.textViewnick.setText(mValues.get(position).getNick());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView textviewposition;
        public final TextView textviewpatos;
        public final TextView textViewnick;
        public usuarios mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            textviewposition = (TextView) view.findViewById(R.id.tvposicion);
            textviewpatos = (TextView) view.findViewById(R.id.tvpatos);
            textViewnick = (TextView) view.findViewById(R.id.tvusuario);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + textViewnick.getText() + "'";
        }
    }
}
