import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface InnerAnno {

    int intValue();

    long longValue();

    Class<?> classValue();

}