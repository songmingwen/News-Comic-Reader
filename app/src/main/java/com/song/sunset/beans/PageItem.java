package com.song.sunset.beans;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.Keep;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

/**
 * @author songmingwen
 * @description
 * @since 2020/12/16
 */
@Keep
public class PageItem {

    private Class<? extends Fragment> fragmentClass;

    /**
     * title 对应的 resId
     */
    @StringRes
    private int titleRes;

    /**
     * title 的字符串值，如果此不为空，则应该优先使用该值
     */
    private CharSequence title;

    /**
     * title 的颜色值
     */
    @ColorRes
    private int titleColorRes;

    private ColorStateList titleColorDay;

    private ColorStateList titleColorNight;

    /**
     * icon 的资源
     */
    @DrawableRes
    private int iconRes;

    /**
     * icon 的图标，如果不为 null ，则应该优先使用 icon 而不是 iconRes
     */
    private Drawable icon;

    private Bundle arguments;

    public PageItem(Class<? extends Fragment> fragmentClass, CharSequence title) {
        this(fragmentClass, title, null);
    }

    public PageItem(Class<? extends Fragment> fragmentClass, @DrawableRes int icon, @StringRes int title, @ColorRes int titleColor, Bundle arguments) {
        this(fragmentClass, title, null);
    }

    public PageItem(Class<? extends Fragment> fragmentClass, CharSequence title, Bundle arguments) {
        this(fragmentClass, title, 0, arguments);
    }

    public PageItem(Class<? extends Fragment> fragmentClass, int iconResId) {
        this(fragmentClass, iconResId, null);
    }

    public PageItem(Class<? extends Fragment> fragmentClass, Drawable icon) {
        this(fragmentClass, icon, null);
    }

    public PageItem(Class<? extends Fragment> fragmentClass, int iconResId, Bundle arguments) {
        this(fragmentClass, null, iconResId, arguments);
    }

    public PageItem(Class<? extends Fragment> fragmentClass, Drawable icon, Bundle arguments) {
        this(fragmentClass, null, icon, arguments);
    }

    private PageItem(Class<? extends Fragment> fragmentClass, CharSequence title, int iconResId, Bundle arguments) {
        this.fragmentClass = fragmentClass;

        this.title = title;

        this.iconRes = iconResId;

        this.icon = null;

        this.arguments = arguments;
    }

    public PageItem(Class<? extends Fragment> fragmentClass, CharSequence title, Drawable icon, Bundle arguments) {
        this.fragmentClass = fragmentClass;

        this.title = title;

        this.icon = icon;

        this.iconRes = 0;

        this.arguments = arguments;
    }

    public PageItem(Class<? extends Fragment> fragmentClass, CharSequence title,
                     Drawable icon, ColorStateList titleColorDay, ColorStateList titleColorNight, Bundle arguments) {
        this(fragmentClass, title, icon, arguments);
        this.titleColorDay = titleColorDay;
        this.titleColorNight = titleColorNight;
    }

    private PageItem(Builder builder) {
        fragmentClass = builder.fragmentClass;
        titleColorDay = builder.titleColorDay;
        titleColorNight = builder.titleColorNight;
        titleColorRes = builder.titleColorRes;
        titleRes = builder.titleRes;
        setTitle(builder.title);
        iconRes = builder.iconRes;
        icon = builder.icon;
        arguments = builder.arguments;
    }

    public Class<? extends Fragment> getFragmentClass() {
        return this.fragmentClass;
    }

    public Bundle getArguments() {
        return this.arguments;
    }

    public CharSequence getTitle() {
        return this.title;
    }

    public void setTitle(CharSequence title) {
        this.title = title;
    }

    public int getIconResId() {
        return this.iconRes;
    }

    public Drawable getIcon() {
        return icon;
    }

    public ColorStateList getTitleColorDay() {
        return titleColorDay;
    }

    public ColorStateList getTitleColorNight() {
        return titleColorNight;
    }

    public void setIconView(ImageView imageView) {
        if (icon != null) {
            imageView.setImageDrawable(icon);
        } else if (iconRes != 0) {
            imageView.setImageResource(iconRes);
        }
    }

    public void setIconTab(TabLayout.Tab tab) {
        if (icon != null) {
            tab.setIcon(icon);
        } else if (iconRes != 0) {
            tab.setIcon(iconRes);
        }
    }

    public void setTitleView(TextView textView) {
        if (title != null) {
            textView.setText(title);
        } else if (titleRes != 0) {
            textView.setText(titleRes);
        }
        if (titleColorRes != 0) {
            textView.setTextColor(ContextCompat.getColorStateList(textView.getContext(), titleColorRes));
        }
    }

    public static final class Builder {
        private Class<? extends Fragment> fragmentClass;
        private ColorStateList titleColorDay;
        private ColorStateList titleColorNight;
        private int titleColorRes;
        private int titleRes;
        private CharSequence title;
        private int iconRes;
        private Drawable icon;
        private Bundle arguments;

        public Builder() {
        }

        public Builder fragmentClass(Class<? extends Fragment> val) {
            fragmentClass = val;
            return this;
        }

        public Builder titleColorDay(ColorStateList val) {
            titleColorDay = val;
            return this;
        }

        public Builder titleColorNight(ColorStateList val) {
            titleColorNight = val;
            return this;
        }

        public Builder titleColorRes(int val) {
            titleColorRes = val;
            return this;
        }

        public Builder titleRes(int val) {
            titleRes = val;
            return this;
        }

        public Builder title(CharSequence val) {
            title = val;
            return this;
        }

        public Builder iconRes(int val) {
            iconRes = val;
            return this;
        }

        public Builder icon(Drawable val) {
            icon = val;
            return this;
        }

        public Builder arguments(Bundle val) {
            arguments = val;
            return this;
        }

        public PageItem build() {
            return new PageItem(this);
        }
    }
}
