package com.example.the6;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CancelAdapter extends RecyclerView.Adapter<CancelAdapter.ViewHolder> {
    private List<OrderLog> mOrderList;
    private String Productname;
    private onItemClickListner mListner;

    public interface onItemClickListner{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(onItemClickListner listner){
        mListner = listner;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mProductNameField;
        public TextView mOrderNumberField;

        public ViewHolder(View itemView, onItemClickListner listner) {
            super(itemView);
            mProductNameField = itemView.findViewById(R.id.textviewCancelOrderProductName);
            mOrderNumberField = itemView.findViewById(R.id.textViewOrderNumber);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listner != null){
                        int position = getAbsoluteAdapterPosition();

                        if(position != RecyclerView.NO_POSITION){
                            listner.onItemClick(position);
                        }
                    }
                }
            });

        }
    }

    public CancelAdapter(List<OrderLog> mOrderList){
        this.mOrderList = mOrderList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cancel_order_item, parent, false);
        ViewHolder vh = new ViewHolder(view, mListner);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OrderLog currentOrder = mOrderList.get(position);

        Integer orderint = currentOrder.getOrdernumber();
        String OrderNumberString = orderint.toString();

        holder.mProductNameField.setText(currentOrder.getProductname());
        holder.mOrderNumberField.setText("Order #" + OrderNumberString);


    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }
}
