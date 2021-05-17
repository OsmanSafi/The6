package com.example.the6;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.the6.Database.SixDAO;

public class Dialog extends AppCompatDialogFragment {

    private EditText nameField;
    private EditText priceField;
    private EditText quantityField;

    private dialogListner listner;

    SixDAO mDao;

    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_product_dialog_layout, null);

        builder.setView(view).setTitle("Please fill in the blanks")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = nameField.getText().toString();
                String price = priceField.getText().toString();
                String quantity = quantityField.getText().toString();

                listner.applyText(name, price, quantity);
                startActivity(new Intent(getContext(), Admin.class));
            }
        });


        nameField = view.findViewById(R.id.addproductname);
        priceField = view.findViewById(R.id.addproductprice);
        quantityField = view.findViewById(R.id.addproductquantity);

        return builder.create();
    }

    public interface dialogListner{
        void applyText(String name, String price, String Quantity);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listner = (dialogListner) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }

    }



}
