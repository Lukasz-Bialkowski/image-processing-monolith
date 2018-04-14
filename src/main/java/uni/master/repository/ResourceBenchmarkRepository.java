package uni.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uni.master.entity.ResourceBenchmark;

public interface ResourceBenchmarkRepository extends JpaRepository<ResourceBenchmark, Long> {
}
