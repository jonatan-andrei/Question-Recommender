package jonatan.andrei.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserActionUpdateType {

    INCREASE(1),
    DECREASE(-1);

    private int value;

}
