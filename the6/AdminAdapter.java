package com.example.the6;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.Viewholder> implements Filterable {
    private List<String> mList;
    private List<String> mFullList;
    private onItemClickListner mListner;


    public interface onItemClickListner{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(onItemClickListner listner){
        mListner = listner;
    }

    public static class Viewholder extends RecyclerView.ViewHolder{
        public TextView nameOnLine;

        public Viewholder( View itemView, onItemClickListner listner) {
            super(itemView);
            nameOnLine = itemView.findViewById(R.id.line1);

            itemView.setOnClickListener(v -> {
                if(listner != null){
                    int position = getAbsoluteAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listner.onItemClick(position);
                    }

                }
            });

        }
    }

    public AdminAdapter(List<String> nameList){
        this.mList = nameList;
        mFullList = new ArrayList<>(nameList);
    }


    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchitems, parent, false);
        Viewholder vh = new Viewholder(view, mListner);

        return vh;
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {
        String currentname = mList.get(position);

        holder.nameOnLine.setText(currentname);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public Filter getFilter() {
        return Listfilter;
    }

    private Filter Listfilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<String> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(mFullList);
            }else{
                String filterPatern = constraint.toString().toLowerCase().trim();

                for(String string: mFullList){
                    if(string.toLowerCase().contains(filterPatern)){
                        filteredList.add(string);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mList.clear();
            mList.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };


}
