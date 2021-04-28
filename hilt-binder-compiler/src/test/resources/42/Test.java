import com.paulrybitskyi.hiltbinder.BindType;

@BindType(
    installIn = BindType.Component.CUSTOM,
    customComponent = CustomComponent.class
)
@CustomScope
public class Test implements Testable {}