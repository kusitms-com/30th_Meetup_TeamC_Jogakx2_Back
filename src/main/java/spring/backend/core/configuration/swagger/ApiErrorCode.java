package spring.backend.core.configuration.swagger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import spring.backend.core.exception.error.BaseErrorCode;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiErrorCode {

  Class<? extends BaseErrorCode>[] value();
}