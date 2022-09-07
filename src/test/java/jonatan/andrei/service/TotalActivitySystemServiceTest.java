package jonatan.andrei.service;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jonatan.andrei.domain.PostClassificationType;
import jonatan.andrei.domain.UserActionType;
import jonatan.andrei.factory.TotalActivitySystemFactory;
import jonatan.andrei.model.TotalActivitySystem;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@TestTransaction
public class TotalActivitySystemServiceTest extends AbstractServiceTest {

    @Inject
    TotalActivitySystemService totalActivitySystemService;

    @Test
    public void updateNumberByAction_save() {
        // Arrange
        PostClassificationType postClassificationType = PostClassificationType.CATEGORY;
        UserActionType userActionType = UserActionType.QUESTION_COMMENTED;
        BigDecimal value = BigDecimal.ONE;

        // Act
        totalActivitySystemService.updateNumberByAction(postClassificationType, userActionType, value);

        // Assert
        TotalActivitySystem totalActivitySystem = totalActivitySystemRepository.findByPostClassificationType(postClassificationType).get();
        assertEquals(BigDecimal.ONE, totalActivitySystem.getNumberQuestionsCommented().stripTrailingZeros());
    }

    @Test
    public void updateNumberByAction_update() {
        // Arrange
        PostClassificationType postClassificationType = PostClassificationType.CATEGORY;
        TotalActivitySystem totalActivitySystem = totalActivitySystemRepository.save(TotalActivitySystemFactory.newTotalActivitySystem(postClassificationType));
        UserActionType userActionType = UserActionType.QUESTION_ASKED;
        BigDecimal value = BigDecimal.ONE;

        // Act
        totalActivitySystemService.updateNumberByAction(postClassificationType, userActionType, value);

        // Assert
        totalActivitySystem = totalActivitySystemRepository.findById(totalActivitySystem.getTotalActivitySystemId()).get();
        assertEquals(BigDecimal.ONE, totalActivitySystem.getNumberQuestionsAsked().stripTrailingZeros());
    }
}
