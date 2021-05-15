import com.paulrybitskyi.hiltbinder.BindType;

@CustomQualifier(type = CustomQualifier.Type.ONE)
@BindType(withQualifier = true)
public class Test implements Testable {}