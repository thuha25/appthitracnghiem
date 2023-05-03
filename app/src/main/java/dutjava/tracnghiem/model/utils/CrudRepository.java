package dutjava.tracnghiem.model.utils;

import java.util.Collection;
import java.util.Optional;

public abstract class CrudRepository<TEntity, TPrimary> extends Repository<TEntity, TPrimary> {
    public CrudRepository(Class<TEntity> EntityClass, Class<TPrimary> PrimaryClass) {
        super(EntityClass, PrimaryClass);
    }

    public abstract Collection<TEntity> findAll();
    public abstract Optional<TEntity> findById(TPrimary id);
    public abstract void save(TEntity entity);
    public abstract void delete(TEntity entity);
    public abstract void deleteById(TPrimary id);
}
