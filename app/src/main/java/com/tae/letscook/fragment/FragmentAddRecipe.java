package com.tae.letscook.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tae.letscook.R;
import com.tae.letscook.Utils.PhotoUtils;
import com.tae.letscook.adapter.AdapterIngredients;
import com.tae.letscook.constants.Constants;
import com.tae.letscook.dialog.DialogFragmentAddIngredient;
import com.tae.letscook.listeners.OnIngredientAddedListener;
import com.tae.letscook.model.ItemIngredient;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

/**
 * Created by Eduardo on 30/12/2015.
 */
public class FragmentAddRecipe extends Fragment implements OnIngredientAddedListener {

    private static final String TAG = "FragmentAddRecipe";
    @Bind(R.id.tv_recipe_title) protected TextView tvRecipeTitle;
    @Bind(R.id.img_add_recipe) protected ImageView imgRecipe;
    @Bind(R.id.recycler_view) protected RecyclerView recyclerView;
    private String title, category;
    private AdapterIngredients adapter;
    private ItemTouchHelper.SimpleCallback simpleTouchCallback;
    private File tempImageFile;

    /**
     * This builder receives the params from DialogFragmentRecipeTitle
     * @param title
     * @param category
     * @return
     */
    public static FragmentAddRecipe newInstance (String title, String category) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARGS_RECIPE_TITLE, title);
        bundle.putString(Constants.ARGS_RECIPE_CATEGORY, category);
        FragmentAddRecipe fragmentAddRecipe = new FragmentAddRecipe();
        fragmentAddRecipe.setArguments(bundle);
        return fragmentAddRecipe;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        title = getTitleRecipe();
        category = getCategoryRecipe();
        setItemTouchSimpleCallback();


    }

    /**
     * Callback to handle drag and swipe
     * Only swipe implemented to delete items from the ingredients list
     */
    private void setItemTouchSimpleCallback() {
        simpleTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(dragFlags, swipeFlags) != 0;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.removeIngredient(viewHolder.getAdapterPosition());
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_recipe, container, false);
        ButterKnife.bind(this, view);
        tvRecipeTitle.setText(title);
        setRecyclerView();
        initItemTouch();
        return view;
    }

    /**
     * The ItemTouchHelper.SimpleCallback needs to be passed to ItemTouchHelper to make it work
     */
    private void initItemTouch() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void setRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new AdapterIngredients(getActivity());
        recyclerView.setAdapter(adapter);
    }

    private String getCategoryRecipe() {
        return getArguments().getString(Constants.ARGS_RECIPE_CATEGORY);
    }

    private String getTitleRecipe() {
        return Constants.RECIPE + getArguments().getString(Constants.ARGS_RECIPE_TITLE);
    }

    /**
     * Start a dialog to let the user add ingredients and amount
     */
    private void displayAddIngredientDialog() {
        DialogFragmentAddIngredient dialog = DialogFragmentAddIngredient.newInstance();
        dialog.setTargetFragment(this, 0); // communicate the dialog with the fragment setting the target
        dialog.show(getActivity().getSupportFragmentManager(), getResources().getString(R.string.dialog_add_ingredient));
    }

    @OnClick( R.id.fab_add_ingredient)
    protected void onFabClick(View view) {
        if (view.getId() == R.id.fab_add_ingredient) {
            Log.i("FragmentAddRecipe", "click");
            displayAddIngredientDialog();
        }
    }

    @OnItemSelected({R.id.spinner_budget, R.id.spinner_level, R.id.spinner_dinner_guest, R.id.spinner_time})
    protected void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinner_budget : // TODO get the data from the spinners to create the recipe object
                Log.i(TAG, "onItemSelected: " + parent.getAdapter().getItem(position));
                break;
            case R.id.spinner_level :
                Log.i(TAG, "onItemSelected: " + parent.getAdapter().getItem(position));
                break;
            case R.id.spinner_dinner_guest :
                Log.i(TAG, "onItemSelected: " + parent.getAdapter().getItem(position));
                break;
            case R.id.spinner_time :
                Log.i(TAG, "onItemSelected: " + parent.getAdapter().getItem(position));
                break;
        }

    }

    /**
     * Listener that gets the data from the DialogFragmentAddIngredient and update the list
     * in the adapter
     * @param ingredient
     * @param amount
     */
    @Override
    public void addIngredient(String ingredient, String amount) {
        Log.i(TAG, "ingredient: " + ingredient + " amount " + amount);
        adapter.addIngredient(new ItemIngredient(ingredient, amount));
    }

    /**
     * Check if user allow the camera permision
     * **Marshmallow**
     * @return
     */
    private boolean hasCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA},Constants.PERMISSIONS_REQUEST_CAMERA);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.PERMISSIONS_REQUEST_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launchCamera();
            }
        }
    }

    private void launchCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            tempImageFile = PhotoUtils.createTempImageFile();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempImageFile));
            startActivityForResult(intent, Constants.REQUEST_CAMERA_IMAGE);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == Constants.REQUEST_CAMERA_IMAGE) {
        // TODO pass the thumbnail to the recipe object to save it in the server
            Picasso.with(getActivity()).load(tempImageFile).resize(75,75).centerCrop().into(imgRecipe); // TODO we need a placeholder Image!!!
            Log.i(TAG, "onActivityResult: tem image bytes" + tempImageFile.getTotalSpace());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_add_recipe, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_add_image) {
            if (hasCameraPermission()) {
                launchCamera();
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
