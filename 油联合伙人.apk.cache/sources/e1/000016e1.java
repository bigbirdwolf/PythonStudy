package com.yltx.oil.partner.injections.modules;

import android.app.Activity;
import com.yltx.oil.partner.injections.components.ActivityScope;
import com.yltx.oil.partner.modules.mine.activity.HelpCenterActivity;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module(subcomponents = {HelpCenterActivitySubcomponent.class})
/* loaded from: classes.dex */
public abstract class BuildersModule_HelpCenterActivity {

    @ActivityScope
    @Subcomponent
    /* loaded from: classes.dex */
    public interface HelpCenterActivitySubcomponent extends AndroidInjector<HelpCenterActivity> {

        @Subcomponent.Builder
        /* loaded from: classes.dex */
        public static abstract class Builder extends AndroidInjector.Builder<HelpCenterActivity> {
        }
    }

    @ActivityKey(HelpCenterActivity.class)
    @Binds
    @IntoMap
    abstract AndroidInjector.Factory<? extends Activity> bindAndroidInjectorFactory(HelpCenterActivitySubcomponent.Builder builder);

    private BuildersModule_HelpCenterActivity() {
    }
}