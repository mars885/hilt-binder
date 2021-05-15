import com.paulrybitskyi.hiltbinder.BindType;

@CustomQualifier(
    boolValue = true,
    byteValue = 10,
    charValue = ',',
    shortValue = Short.MAX_VALUE,
    intValue = -10,
    longValue = 100L,
    floatValue = -15f,
    doubleValue = 50.0,
    stringValue = "string",
    enumValue = CustomQualifierType.ONE,
    annotationValue = @InnerAnno(
        intValue = 0,
        longValue = 500L,
        classValue = CustomQualifierClass.class
    ),
    classValue = CustomQualifierClass.class,
    boolArray = { true, false },
    byteArray = { 100, 105 },
    charArray = { 'A', 'B', 'C' },
    shortArray = { Short.MIN_VALUE, Short.MAX_VALUE },
    intArray = { 0, 10, 20 },
    longArray = { 10L, 20L, 30L },
    floatArray = { Float.MAX_VALUE, Float.MIN_VALUE },
    doubleArray = { 100.0, 200.0, 300.0 },
    stringArray = { "one", "two", "three" },
    enumArray = {
        CustomQualifierType.ONE,
        CustomQualifierType.TWO,
        CustomQualifierType.THREE
    },
    annotationArray = {
        @InnerAnno(
            intValue = 0,
            longValue = 1L,
            classValue = Integer.class
        ),
        @InnerAnno(
            intValue = 10,
            longValue = 100L,
            classValue = Float.class
        )
    },
    classArray = {
        Boolean.class,
        Integer.class,
        Double.class
    }
)
@BindType(withQualifier = true)
public class Test implements Testable { }