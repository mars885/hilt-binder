import com.paulrybitskyi.hiltbinder.BindType;

@BindType(installIn = BindType.Component.CUSTOM)
public class Test implements Testable {}