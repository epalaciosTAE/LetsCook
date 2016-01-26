package com.tae.letscook.Utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.tae.letscook.R;
import com.tae.letscook.model.Item;
import com.tae.letscook.model.ItemRecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Eduardo on 07/12/2015.
 */
public class DrawableUtils {

    /**
     * Method to access drawable resources:
     * Ripple for Lollipop
     * Selector below Lollipop
     * @param context
     * @param id
     * @return
     */
    public static Drawable drawableFactory(Context context, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return ContextCompat.getDrawable(context, id);
        } else {
            return context.getResources().getDrawable(R.drawable.drawable_selector_item);
        }
    }

    public static Integer randomDrawable() {
        List<Integer> drawables = new ArrayList<>(10);
        drawables.add(R.drawable.img_avocado);
        drawables.add(R.drawable.img_chocolate);
        drawables.add(R.drawable.img_egg);
        drawables.add(R.drawable.img_fish_smoking);
        drawables.add(R.drawable.img_pasta);
        drawables.add(R.drawable.img_ribs);
        drawables.add(R.drawable.img_rice);
        drawables.add(R.drawable.img_species);
        drawables.add(R.drawable.img_steak);
        drawables.add(R.drawable.img_vegetables);
        return drawables.get(new Random().nextInt(drawables.size()));
    }

    /**
     * This set a view to use a custom ripple effect.
     * In the xml needs the property android:background="?android:attr/selectableItemBackground"
     * @param view
     * @param drawable
     */
    public static void setRipple(View view, Drawable drawable) {
        view.setBackground(drawable);
    }

    private static int[] getImagesResources(Context context, int arrayResource) {
        TypedArray mIconsTypeArray = context.getResources().obtainTypedArray(arrayResource);
        int[] mDrawerIcons = new int[mIconsTypeArray.length()];
        for (int i = 0; i < mDrawerIcons.length; i++) {
            mDrawerIcons[i] = mIconsTypeArray.getResourceId(i, 0);
        }
        mIconsTypeArray.recycle();
        return mDrawerIcons;
    }

    private static String[] getTitleResources(Context context, int arrayResource) {
        return context.getResources().getStringArray(arrayResource);
    }

//    public static List<ItemRecipe> itemsFromResourcesFactory(Context context, int arrayTitles, int arrayImages) {
//        String[] titles = getTitleResources(context, arrayTitles);
//        int[] images = getImagesResources(context, arrayImages);
//        List<ItemRecipe> items = new ArrayList<>(titles.length);
//        for (int i = 0; i < titles.length; i++) {
//            items.add(new ItemRecipe(titles[i], String.valueOf(images[i])));
//        }
//        return items;
//    }


    public static List<Item> itemsFromResourcesFactory(Context context, int arrayTitles, int arrayImages) {
        String[] titles = getTitleResources(context, arrayTitles);
        int[] images = getImagesResources(context, arrayImages);
        List<Item> items = new ArrayList<>(titles.length);
        for (int i = 0; i < titles.length; i++) {
            items.add(new Item(titles[i], images[i]));
        }
        return items;
    }

}
