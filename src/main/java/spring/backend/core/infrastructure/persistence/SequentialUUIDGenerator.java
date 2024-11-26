package spring.backend.core.infrastructure.persistence;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public class SequentialUUIDGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        long mostSignificantBits = Instant.now().toEpochMilli();
        long leastSignificantBits = UUID.randomUUID().getLeastSignificantBits();
        return new UUID(mostSignificantBits, leastSignificantBits);
    }
}
