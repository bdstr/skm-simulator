package pl.edu.pjwstk.skmapi.service;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pjwstk.skmapi.model.DbEntity;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class CrudService<T extends DbEntity> {
    JpaRepository<T, Long> repository;

    public CrudService(JpaRepository<T, Long> repository) {
        this.repository = repository;
    }

    public List<T> getAll() {
        Iterable<T> items = repository.findAll();
        var itemList = new ArrayList<T>();

        items.forEach(itemList::add);

        return itemList;
    }

    public T getById(Long id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public void delete(Long id) {
        Optional<T> item = repository.findById(id);

        if (item.isPresent()) {
            repository.delete(item.orElseThrow());
        } else {
            throw new EntityNotFoundException();
        }
    }

    public abstract T createOrUpdate(T updateEntity);
}
