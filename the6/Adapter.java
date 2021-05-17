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

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> implements Filterable{
    private List<Products> mList;
    private List<Products> fullList;

    private onItemClickListener mListner;

   public interface onItemClickListener{
       void onItemClick(int position);
   }

   public void setOnItemClickListener(onItemClickListener listener){
       mListner = listener;
   }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView line1;

        public ViewHolder(View itemView, onItemClickListener listner) {
            super(itemView);
            line1 = itemView.findViewById(R.id.line1);

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

    public Adapter(List<Products> productList){
        this.mList = productList;
        fullList = new ArrayList<>(productList);
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchitems, parent, false);
        ViewHolder vh = new ViewHolder(view, mListner);

        return vh;
    }

    @Override
    public void onBindViewHolder(Adapter.ViewHolder holder, int position) {
        Products currentProduct = mList.get(position);

        holder.line1.setText(currentProduct.getProductName());


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public Filter getFilter() {
        return ListFilter;

    }

    private Filter ListFilter = new Filter() {
       @Override
       protected FilterResults performFiltering(CharSequence constraint) {
          List<Products> filteredList = new ArrayList<>();

          if(constraint == null || constraint.length() == 0){
              filteredList.addAll(fullList);
          }else{
              String filterPatern = constraint.toString().toLowerCase().trim();

              for (Products product: fullList) {
                  if(product.getProductName().toLowerCase().contains(filterPatern)){
                      filteredList.add(product);
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
