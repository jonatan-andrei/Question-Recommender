package jonatan.andrei.service;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jonatan.andrei.dto.TagRequestDto;
import jonatan.andrei.exception.RequiredDataException;
import jonatan.andrei.model.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static java.util.Objects.nonNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
@TestTransaction
public class TagServiceTest extends AbstractServiceTest {

    @Inject
    TagService tagService;

    @Test
    public void saveOrUpdate_saveNewTag() {
        // Arrange
        TagRequestDto tagRequestDto = TagRequestDto.builder()
                .name("Java")
                .description("Programming language")
                .active(true)
                .build();

        // Act
        Tag result = tagService.saveOrUpdate(tagRequestDto);

        // Assert
        Assertions.assertTrue(nonNull(result.getTagId()));
        Assertions.assertEquals(tagRequestDto.getName(), result.getName());
        Assertions.assertEquals(tagRequestDto.getDescription(), result.getDescription());
        Assertions.assertEquals(tagRequestDto.isActive(), result.isActive());
    }

    @Test
    public void saveOrUpdate_updateTag() {
        // Arrange
        Tag existingTag = tagRepository.save(Tag.builder()
                .name("Java")
                .description("language")
                .active(true)
                .build());
        TagRequestDto tagRequestDto = TagRequestDto.builder()
                .name(existingTag.getName())
                .description("Programming language")
                .active(false)
                .build();

        // Act
        Tag result = tagService.saveOrUpdate(tagRequestDto);

        // Assert
        Assertions.assertEquals(existingTag.getTagId(), result.getTagId());
        Assertions.assertEquals(tagRequestDto.getName(), result.getName());
        Assertions.assertEquals(tagRequestDto.getDescription(), result.getDescription());
        Assertions.assertEquals(tagRequestDto.isActive(), result.isActive());
    }

    @Test
    public void saveOrUpdate_validateNameRequired() {
        // Arrange
        TagRequestDto tagRequestDto = TagRequestDto.builder()
                .active(true)
                .build();

        // Assert
        Exception exception = assertThrows(RequiredDataException.class, () -> {
            // Act
            tagService.saveOrUpdate(tagRequestDto);
        });

        Assertions.assertEquals("Attribute 'name' is required", exception.getMessage());
    }
}
