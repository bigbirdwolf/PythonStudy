package com.yltx.oil.partner.injections.modules;

import android.support.v4.app.Fragment;
import com.yltx.oil.partner.injections.components.ActivityScope;
import com.yltx.oil.partner.modules.oiltrade.fragment.StoredValueCardFragment;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;

@Module(subcomponents = {StoredValueCardFragmentSubcomponent.class})
/* loaded from: classes.dex */
public abstract class BuildersModule_StoredValueCardFragment {

    @ActivityScope
    @Subcomponent
    /* loaded from: classes.dex */
    public interface StoredValueCardFragmentSubcomponent extends AndroidInjector<StoredValueCardFragment> {

        @Subcomponent.Builder
        /* loaded from: classes.dex */
        public static abstract class Builder extends AndroidInjector.Builder<StoredValueCardFragment> {
        }
    }

    @FragmentKey(StoredValueCardFragment.class)
    @Binds
    @IntoMap
    abstract AndroidInjector.Factory<? extends Fragment> bindAndroidInjectorFactory(StoredValueCardFragmentSubcomponent.Builder builder);

    private BuildersModule_StoredValueCardFragment() {
    }
}