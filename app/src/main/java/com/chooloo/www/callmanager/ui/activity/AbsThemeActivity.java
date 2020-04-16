package com.chooloo.www.callmanager.ui.activity;

import android.content.res.Resources;
import android.widget.Toast;

import androidx.annotation.CallSuper;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatActivity;

import com.chooloo.www.callmanager.R;
import com.chooloo.www.callmanager.util.PreferenceUtils;
import com.chooloo.www.callmanager.util.ThemeUtils;

import timber.log.Timber;

import static com.chooloo.www.callmanager.util.ThemeUtils.ThemeType;

public abstract class AbsThemeActivity extends AppCompatActivity {

    private @StyleRes int mThemeStyle = -1;
    private @ThemeType int mThemeType;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    @CallSuper
    protected void onStart() {
        super.onStart();
        updateTheme();
    }

    protected void setThemeType(@ThemeType int type) {
        mThemeType = type;
        updateTheme();
    }

    protected void updateTheme() {
        String theme = PreferenceUtils.getInstance(this).getString(R.string.pref_app_theme_key);
        int newThemeStyle = ThemeUtils.themeFromId(theme, mThemeType);
        Timber.i("Theme updating to: " + theme);
        boolean isInOnCreate = mThemeStyle == -1;

        if (mThemeStyle != newThemeStyle) {
            mThemeStyle = newThemeStyle;
            setTheme(mThemeStyle);

            if (!isInOnCreate) {
                finish();
                startActivity(getIntent());
            }
        }
    }

    @Override
    public void setTheme(int resid) {
        Resources.Theme theme = super.getTheme();
        theme.applyStyle(resid, true);
    }
}
