package dutjava.tracnghiem.model.service;

import java.util.List;
import java.util.Optional;

public interface ICrudService<TModel> {
    List<TModel> getAll();
    Optional<TModel> getById(int id);
    Optional<TModel> save(TModel model);
    void delete(TModel model);
    void deleteById(int id);
}
