import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomQualifier {

    boolean boolValue();

    char charValue();

    byte byteValue();

    short shortValue();

    int intValue();

    long longValue();

    float floatValue();

    double doubleValue();

    String stringValue();

    CustomQualifierType enumValue();

    InnerAnno annotationValue();

    Class<?> classValue();

    boolean[] boolArray();

    byte[] byteArray();

    char[] charArray();

    short[] shortArray();

    int[] intArray();

    long[] longArray();

    float[] floatArray();

    double[] doubleArray();

    String[] stringArray();

    CustomQualifierType[] enumArray();

    InnerAnno[] annotationArray();

    Class<?>[] classArray();

}