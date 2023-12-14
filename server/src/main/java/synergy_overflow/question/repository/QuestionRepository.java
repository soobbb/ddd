package synergy_overflow.question.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import synergy_overflow.question.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Page<Question> findDistinctAllByAnswersIsNotNullOrderByQuestionIdDesc(Pageable pageable);
}
