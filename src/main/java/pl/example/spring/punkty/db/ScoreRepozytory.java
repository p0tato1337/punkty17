package pl.example.spring.punkty.db;
import org.springframework.data.repository.CrudRepository;

public interface ScoreRepozytory extends CrudRepository<ScoreRow, Long>
{

}