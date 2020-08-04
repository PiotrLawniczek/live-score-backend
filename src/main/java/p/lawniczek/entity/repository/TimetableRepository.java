package p.lawniczek.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import p.lawniczek.entity.Table;

public interface TimetableRepository extends JpaRepository<Table, Long> {
}
