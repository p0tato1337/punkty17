package pl.example.spring.punkty;
import io.vavr.collection.List;
import org.springframework.stereotype.Service;
import pl.example.spring.punkty.db.StudentRepozytory;
import pl.example.spring.punkty.db.StudentRow;
import pl.example.spring.punkty.db.ScoreRow;
import pl.example.spring.punkty.db.ScoreRepozytory;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.function.Function;

@Service
public class StudentService {

    private final StudentRepozytory studentRepozytory;
    private final ScoreRepozytory scoreRepozytory;

    public StudentService(StudentRepozytory studentRepository, ScoreRepozytory
            scoreRepository) {
        this.studentRepozytory = studentRepository;
        this.scoreRepozytory = scoreRepository;
    }

    List<Student> getStudents() {
        return List.ofAll(this.studentRepozytory.findAll())
                .map(StudentRow::toStudent);
    }

    Student addStudent(final NewStudent newStudent) {
        return this.studentRepozytory.save(new StudentRow(
                newStudent.name,
                newStudent.number,
                newStudent.group)).toStudent();
    }

    @Transactional
    public Optional<Student> changeNumber (long studentId, String newNumber) {
        final Optional<StudentRow> student = this.studentRepozytory.findById(studentId);
        return student.map(c -> {
            c.setNumber(newNumber);
            return c.toStudent();
        });
    }


    public Optional<Integer> addScore(final long studentId, final Score score) {
        final Optional<StudentRow> student =
                this.studentRepozytory.findById(studentId);
        return student.map(c->{
            int existingScore=List.ofAll(c.getScores())
                    .foldLeft(0,(p,s)->p+s.getScore());
            final ScoreRow newScore=new ScoreRow(score.score,score.comment,c);
            this.scoreRepozytory.save(newScore);
            return existingScore+score.score;});
    }
}
