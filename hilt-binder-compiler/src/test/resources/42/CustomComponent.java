import dagger.hilt.DefineComponent;
import dagger.hilt.components.SingletonComponent;

@CustomScope
@DefineComponent(parent = SingletonComponent.class)
public interface CustomComponent {}