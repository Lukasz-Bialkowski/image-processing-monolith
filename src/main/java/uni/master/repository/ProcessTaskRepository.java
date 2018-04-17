package uni.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uni.master.entity.ProcessTask;

import java.util.List;

public interface ProcessTaskRepository extends JpaRepository<ProcessTask, Long> {

    @Query("SELECT r FROM ProcessTask r WHERE "
            + "r.imageId LIKE '%' || :searchString || '%' "
            + "OR r.id LIKE '%' || :searchString || '%' "
            + "OR r.host LIKE '%' || :searchString || '%' "
            + "OR r.ipAddress LIKE '%' || :searchString || '%' ")
    List<ProcessTask> findtags(@Param("searchString")String searchString);

}
