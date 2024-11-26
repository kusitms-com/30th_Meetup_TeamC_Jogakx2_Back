package spring.backend.core.infrastructure.persistence.interceptor;

import org.hibernate.annotations.IdGeneratorType;
import spring.backend.core.infrastructure.persistence.SequentialUUIDGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@IdGeneratorType(SequentialUUIDGenerator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SequentialUUID {
}
