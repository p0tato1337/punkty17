package pl.example.spring.punkty.db;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StudentRepozytory extends CrudRepository<StudentRow,Long>
{
    Optional<StudentRow> findById(long studentId);
}
