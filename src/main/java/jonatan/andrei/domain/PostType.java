package jonatan.andrei.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PostType {

    QUESTION(null),
    ANSWER(QUESTION),
    QUESTION_COMMENT(QUESTION),
    ANSWER_COMMENT(ANSWER);

    private PostType parentPostType;

    }
