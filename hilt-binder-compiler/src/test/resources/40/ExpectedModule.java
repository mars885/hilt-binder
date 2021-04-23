// Generated by @BindType. Do not modify!

import com.paulrybitskyi.hiltbinder.keys.MapClassKey;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import dagger.multibindings.IntoMap;

@Module
@InstallIn(SingletonComponent.class)
public interface HiltBinder_SingletonComponentModule {
    @Binds
    @IntoMap
    @MapClassKey(Test1.class)
    Testable<?, ?> bind_Test1(Test1 binding);

    @Binds
    @IntoMap
    @MapClassKey(Test2.class)
    Testable<?, ?> bind_Test2(Test2 binding);

    @Binds
    @IntoMap
    @MapClassKey(Test3.class)
    Testable<?, ?> bind_Test3(Test3 binding);
}