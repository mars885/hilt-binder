import dagger.hilt.DefineComponent;
import dagger.hilt.components.SingletonComponent;

@DefineComponent(parent = SingletonComponent.class)
public interface CustomComponent {}