package uni.master;

import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<Long, ImageDAO> {
}
