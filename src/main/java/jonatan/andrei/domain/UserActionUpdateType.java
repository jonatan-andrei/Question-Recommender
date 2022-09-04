package jonatan.andrei.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public enum UserActionUpdateType {

    INCREASE(BigDecimal.valueOf(1)),
    DECREASE(BigDecimal.valueOf(-1));

    private BigDecimal value;

}
