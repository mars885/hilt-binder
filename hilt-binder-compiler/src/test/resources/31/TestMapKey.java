import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import dagger.MapKey;

@MapKey
@Retention(RetentionPolicy.RUNTIME)
public @interface TestMapKey {

    Type value();

    enum Type {

        ONE,
        TWO,
        THREE

    }

}