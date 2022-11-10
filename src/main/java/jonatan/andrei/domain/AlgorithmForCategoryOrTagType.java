package jonatan.andrei.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum AlgorithmForCategoryOrTagType {

    SUBTRACTION(1),
    PERCENTAGE(2);


    private Integer code;

    public static AlgorithmForCategoryOrTagType findByCode(BigDecimal code) {
        return Stream.of(values())
                .filter(a -> a.getCode().equals(code.intValue()))
                .findFirst().orElse(AlgorithmForCategoryOrTagType.SUBTRACTION);
    }
}
