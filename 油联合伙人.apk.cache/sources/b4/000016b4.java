package com.yltx.oil.partner.injections.modules;

import android.app.Activity;
import com.yltx.oil.partner.injections.components.ActivityScope;
import com.yltx.oil.partner.modules.profit.activity.CollectionRecordActivity;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module(subcomponents = {CollectionRecordActivitySubcomponent.class})
/* loaded from: classes.dex */
public abstract class BuildersModule_CollectionRecordActivity {

    @ActivityScope
    @Subcomponent
    /* loaded from: classes.dex */
    public interface CollectionRecordActivitySubcomponent extends AndroidInjector<CollectionRecordActivity> {

        @Subcomponent.Builder
        /* loaded from: classes.dex */
        public static abstract class Builder extends AndroidInjector.Builder<CollectionRecordActivity> {
        }
    }

    @ActivityKey(CollectionRecordActivity.class)
    @Binds
    @IntoMap
    abstract AndroidInjector.Factory<? extends Activity> bindAndroidInjectorFactory(CollectionRecordActivitySubcomponent.Builder builder);

    private BuildersModule_CollectionRecordActivity() {
    }
}