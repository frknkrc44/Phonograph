package com.kabouzeid.gramophone.views;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.kabouzeid.appthemehelper.ThemeStore;
import com.kabouzeid.gramophone.App;
import com.kabouzeid.gramophone.ui.activities.AboutActivity;
import com.kabouzeid.gramophone.ui.activities.MainActivity;
import com.kabouzeid.gramophone.ui.activities.SettingsActivity;

import org.frknkrc44.frigraph.R;

import java.lang.reflect.Constructor;

@SuppressLint("NonConstantResourceId")
public class BottomSheetMainActivity extends BottomSheetDialogFragment implements View.OnClickListener {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog ret = super.onCreateDialog(savedInstanceState);
        ret.setOnShowListener(d -> {
            FrameLayout bottomSheet = ret.findViewById(
                    com.google.android.material.R.id.design_bottom_sheet);
            TypedArray array = requireActivity().getTheme()
                    .obtainStyledAttributes(new int[]{android.R.attr.windowBackground});
            int color = array.getColor(0, ThemeStore.accentColor(requireContext()));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                array.close();
            }
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setColor(color);
            gradientDrawable.setCornerRadius(64);
            gradientDrawable.setStroke(32, 0);
            bottomSheet.setBackground(gradientDrawable);
        });
        return ret;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout view = new LinearLayout(inflater.getContext());
        int margin = inflater.getContext().getResources().getDimensionPixelSize(R.dimen.default_item_margin);
        view.setPadding(margin, margin, margin, margin);
        view.setOrientation(LinearLayout.VERTICAL);
        Menu menu;
        try {
            menu = newMenuInstance(inflater.getContext());
        } catch (Throwable t) {
            PopupMenu popup = new PopupMenu(inflater.getContext(), null);
            menu = popup.getMenu();
        }
        assert menu != null;
        MenuInflater inflater1 = new MenuInflater(inflater.getContext());
        inflater1.inflate(R.menu.menu_drawer, menu);
        for (int i = 0;i < menu.size();i++) {
            MenuItem item = menu.getItem(i);
            ViewGroup itemLayout = (ViewGroup) inflater.inflate(R.layout.bottomsheet_item, view, false);
            itemLayout.setId(item.getItemId());
            ImageView icon = itemLayout.findViewById(R.id.bottom_sheet_icon);
            icon.setImageDrawable(item.getIcon());
            TextView text = itemLayout.findViewById(R.id.bottom_sheet_text);
            text.setText(item.getTitle());
            icon.setImageTintList(text.getTextColors());
            itemLayout.setOnClickListener(this);
            view.addView(itemLayout);
        }
        return view;
    }

    @SuppressLint("PrivateApi")
    private Menu newMenuInstance(Context context) throws Throwable {
        Class<?> menuBuilderClass = Class.forName("com.android.internal.view.menu.MenuBuilder");
        Constructor<?> constructor = menuBuilderClass.getDeclaredConstructor(Context.class);
        return (Menu) constructor.newInstance(context);
    }

    @Override
    public void onClick(View v) {
        MainActivity activity = (MainActivity) requireActivity();
        dismiss();
        switch (v.getId()) {
            case R.id.nav_library:
                App.getMainHandler().postDelayed(() -> activity.setMusicChooser(MainActivity.LIBRARY), 200);
                break;
            case R.id.nav_folders:
                App.getMainHandler().postDelayed(() -> activity.setMusicChooser(MainActivity.FOLDERS), 200);
                break;
            case R.id.action_scan:
                App.getMainHandler().postDelayed(activity::showMediaScanner, 200);
                break;
            case R.id.nav_settings:
                startActivity(new Intent(activity, SettingsActivity.class));
                break;
            case R.id.nav_about:
                startActivity(new Intent(activity, AboutActivity.class));
                break;
        }
    }
}
