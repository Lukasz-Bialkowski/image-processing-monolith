package uni.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uni.master.entity.ImageDAO;

public interface ImageRepository extends JpaRepository<ImageDAO, Long> {
}
