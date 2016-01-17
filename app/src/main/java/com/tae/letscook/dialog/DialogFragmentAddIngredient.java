package com.tae.letscook.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.EditText;

import com.tae.letscook.R;
import com.tae.letscook.Utils.KeyboardUtils;
import com.tae.letscook.listeners.OnIngredientAddedListener;
import com.tae.letscook.listeners.OnRecipeTitleListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Eduardo on 02/01/2016.
 */
public class DialogFragmentAddIngredient extends DialogFragment {

    private OnIngredientAddedListener ingredientAddedListener;
    @Bind(R.id.et_ingredient_name) protected EditText etIngredientName;
    @Bind(R.id.et_ingredient_amount) protected EditText etIngredientAmount;

    public static DialogFragmentAddIngredient newInstance () {
        return new DialogFragmentAddIngredient();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            ingredientAddedListener = (OnIngredientAddedListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement OnRecipeTitleListener \n " + e.getMessage());
        }
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_ingredient, null);
        ButterKnife.bind(this, view);
        builder.setView(view);
        KeyboardUtils.showKeyboard(getActivity());
        builder.setPositiveButton(getActivity().getResources().getString(R.string.dialog_add), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ingredientAddedListener.addIngredient(etIngredientName.getText().toString(), etIngredientAmount.getText().toString());
            }
        });
        builder.setNegativeButton(getActivity().getResources().getString(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        return builder.create();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
