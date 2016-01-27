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
import android.widget.Spinner;
import android.widget.Toast;

import com.tae.letscook.R;
import com.tae.letscook.Utils.KeyboardUtils;
import com.tae.letscook.listeners.OnRecipeTitleListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;

/**
 * Created by Eduardo on 01/01/2016.
 */
public class DialogFragmentRecipeTitle extends DialogFragment {

    @Bind(R.id.et_recipe_title) protected EditText etTitle;
    @Bind(R.id.spinner_recipe_category) protected Spinner spinnerCategory;
    private OnRecipeTitleListener titleListener;
    private String category;

    public static DialogFragmentRecipeTitle newInstance () {
        return new DialogFragmentRecipeTitle();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            titleListener = (OnRecipeTitleListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement OnRecipeTitleListener \n " + e.getMessage());
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_recipe_title, null);
        builder.setView(view);
        ButterKnife.bind(this, view);
        KeyboardUtils.showKeyboard(getActivity());
        builder.setPositiveButton(getActivity().getResources().getString(R.string.dialog_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (etTitle.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Title cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    titleListener.launchAddRecipeFragment(etTitle.getText().toString(), category);
                }
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

    @OnItemSelected(R.id.spinner_recipe_category)
    protected void onItemSelected(int position) {
        category = spinnerCategory.getAdapter().getItem(position).toString();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
