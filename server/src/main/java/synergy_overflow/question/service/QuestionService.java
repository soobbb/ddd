package synergy_overflow.question.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import synergy_overflow.exception.businessLogicException.BusinessLogicException;
import synergy_overflow.exception.businessLogicException.ExceptionCode;
import synergy_overflow.helper.loggedInChecker.LoggedInMemberUtils;
import synergy_overflow.helper.patchUtil.CustomBeanUtils;
import synergy_overflow.member.entity.Member;
import synergy_overflow.member.service.MemberService;
import synergy_overflow.question.entity.Question;
import synergy_overflow.question.repository.QuestionRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final CustomBeanUtils<Question> editUtil;
    private final MemberService memberService;

    public QuestionService(QuestionRepository questionRepository, CustomBeanUtils<Question> editUtil, MemberService memberService) {
        this.questionRepository = questionRepository;
        this.editUtil = editUtil;
        this.memberService = memberService;
    }

    public Question createQuestion(Question question) {
        Member member = memberService.findMemberByEmail(LoggedInMemberUtils.findLoggedInMember());

        member.setQuestions(question);

        return questionRepository.save(question);
    }

    public Question getQuestion(long questionId) {
        Question question = findExistsQuestion(questionId);
        question.setViews();

        return questionRepository.save(question);
    }

    public Page<Question> getQuestions(int page, int size, String sort) {
        return sortingQuestion(page, size, sort);
    }

    private Page<Question> sortingQuestion(int page, int size, String sort) {
        if (sort.equals("answered")) {
            return questionRepository.findDistinctAllByAnswersIsNotNullOrderByQuestionIdDesc(PageRequest.of(page, size));
        } else if (sort.equals("views")) {
            return questionRepository.findAll(
                    PageRequest.of(page, size, Sort.by(sort).descending()
                            .and(Sort.by("questionId").descending()))
            );
        } else if (sort.equals("new")) {
            return questionRepository.findAll(
                    PageRequest.of(page, size, Sort.by("questionId").descending())
            );
        } else {
            throw new BusinessLogicException(ExceptionCode.INVALID_SORT_PARAMETER);
        }
    }

    public Question editQuestion(Question question) {
        Question existQuestion = findExistsQuestion(question.getQuestionId());
        LoggedInMemberUtils.verifyMine(existQuestion.getWriter().getEmail());

        Question newQuestion = editUtil.copyNonNullProperties(question, existQuestion);

        return questionRepository.save(newQuestion);
    }

    public void removeQuestion(long questionId) {
        Question existQuestion = findExistsQuestion(questionId);

        LoggedInMemberUtils.verifyMine(existQuestion.getWriter().getEmail());
        questionRepository.deleteById(questionId);
    }

    public Question findExistsQuestion(long questionId) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        return optionalQuestion.orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
    }
}
