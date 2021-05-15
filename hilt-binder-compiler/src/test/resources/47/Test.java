import com.paulrybitskyi.hiltbinder.BindType;

@BindType(to = Testable.class)
public class Test implements Testable<
    Testable1<? super Integer, ? super Float, ? super String>,
    Testable2<? extends Integer, ? extends Float, ? extends String>,
    Testable3<?, ?, ?>
> {}