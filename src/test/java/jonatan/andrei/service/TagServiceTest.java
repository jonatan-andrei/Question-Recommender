package jonatan.andrei.service;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jonatan.andrei.dto.TagRequestDto;
import jonatan.andrei.exception.InconsistentIntegratedDataException;
import jonatan.andrei.exception.RequiredDataException;
import jonatan.andrei.model.Tag;
import jonatan.andrei.repository.TagRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static java.util.Objects.nonNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
@TestTransaction
public class TagServiceTest {

    @Inject
    TagService tagService;

    @Inject
    TagRepository tagRepository;

    @Test
    public void saveOrUpdate_saveNewTag() {
        // Arrange
        TagRequestDto tagRequestDto = TagRequestDto.builder()
                .integrationTagId("1")
                .name("Java")
                .description("Programming language")
                .active(true)
                .build();

        // Act
        Tag result = tagService.saveOrUpdate(tagRequestDto);

        // Assert
        Assertions.assertTrue(nonNull(result.getTagId()));
        Assertions.assertEquals(tagRequestDto.getIntegrationTagId(), result.getIntegrationTagId());
        Assertions.assertEquals(tagRequestDto.getName(), result.getName());
        Assertions.assertEquals(tagRequestDto.getDescription(), result.getDescription());
        Assertions.assertEquals(tagRequestDto.isActive(), result.isActive());
    }

    @Test
    public void saveOrUpdate_updateTag() {
        // Arrange
        Tag existingTag = tagRepository.save(Tag.builder()
                .integrationTagId("1")
                .name("Java")
                .description("language")
                .active(true)
                .questionCount(0)
                .build());
        TagRequestDto tagRequestDto = TagRequestDto.builder()
                .integrationTagId(existingTag.getIntegrationTagId())
                .name(existingTag.getName())
                .description("Programming language")
                .active(false)
                .build();

        // Act
        Tag result = tagService.saveOrUpdate(tagRequestDto);

        // Assert
        Assertions.assertEquals(existingTag.getTagId(), result.getTagId());
        Assertions.assertEquals(tagRequestDto.getIntegrationTagId(), result.getIntegrationTagId());
        Assertions.assertEquals(tagRequestDto.getName(), result.getName());
        Assertions.assertEquals(tagRequestDto.getDescription(), result.getDescription());
        Assertions.assertEquals(tagRequestDto.isActive(), result.isActive());
    }

    @Test
    public void saveOrUpdate_updateTagWithIntegrationTagId() {
        // Arrange
        Tag existingTag = tagRepository.save(Tag.builder()
                .name("Java")
                .description("Programming language")
                .active(true)
                .questionCount(0)
                .build());
        TagRequestDto tagRequestDto = TagRequestDto.builder()
                .integrationTagId("1")
                .name(existingTag.getName())
                .description(existingTag.getDescription())
                .active(existingTag.isActive())
                .build();

        // Act
        Tag result = tagService.saveOrUpdate(tagRequestDto);

        // Assert
        Assertions.assertEquals(existingTag.getTagId(), result.getTagId());
        Assertions.assertEquals(tagRequestDto.getIntegrationTagId(), result.getIntegrationTagId());
        Assertions.assertEquals(tagRequestDto.getName(), result.getName());
        Assertions.assertEquals(tagRequestDto.getDescription(), result.getDescription());
        Assertions.assertEquals(tagRequestDto.isActive(), result.isActive());
    }

    @Test
    public void saveOrUpdate_validateNameRequired() {
        // Arrange
        TagRequestDto tagRequestDto = TagRequestDto.builder()
                .integrationTagId("1")
                .active(true)
                .build();

        // Assert
        Exception exception = assertThrows(RequiredDataException.class, () -> {
            // Act
            tagService.saveOrUpdate(tagRequestDto);
        });

        Assertions.assertEquals("Attribute 'name' is required", exception.getMessage());
    }

    @Test
    public void saveOrUpdate_validateIntegrationTagIdRequired() {
        // Arrange
        TagRequestDto tagRequestDto = TagRequestDto.builder()
                .name("Java")
                .active(true)
                .build();

        // Assert
        Exception exception = assertThrows(RequiredDataException.class, () -> {
            // Act
            tagService.saveOrUpdate(tagRequestDto);
        });

        Assertions.assertEquals("Attribute 'integrationTagId' is required", exception.getMessage());
    }

    @Test
    public void saveOrUpdate_validateExistingTagWithNameWithIntegrationTagId() {
        // Arrange
        Tag existingTagWithIntegrationTagId = tagRepository.save(Tag.builder()
                .integrationTagId("1")
                .name("PHP")
                .description("Programming language")
                .active(true)
                .questionCount(0)
                .build());
        Tag existingTagWithName = tagRepository.save(Tag.builder()
                .integrationTagId("2")
                .name("Java")
                .description("Programming language")
                .active(true)
                .questionCount(0)
                .build());
        TagRequestDto tagRequestDto = TagRequestDto.builder()
                .integrationTagId("1")
                .name("Java")
                .description("Programming language")
                .active(true)
                .build();

        // Assert
        Exception exception = assertThrows(InconsistentIntegratedDataException.class, () -> {
            // Act
            tagService.saveOrUpdate(tagRequestDto);
        });

        Assertions.assertEquals("There is already a tag registered for this name but with a different integrationTagId", exception.getMessage());
    }

    @Test
    public void saveOrUpdate_validateExistingTagWithName() {
        // Arrange
        Tag existingTag = tagRepository.save(Tag.builder()
                .integrationTagId("1")
                .name("Java")
                .description("Programming language")
                .active(true)
                .questionCount(0)
                .build());
        TagRequestDto tagRequestDto = TagRequestDto.builder()
                .integrationTagId("2")
                .name("Java")
                .description("Programming language")
                .active(true)
                .build();

        // Assert
        Exception exception = assertThrows(InconsistentIntegratedDataException.class, () -> {
            // Act
            tagService.saveOrUpdate(tagRequestDto);
        });

        Assertions.assertEquals("There is already a tag registered for this name but with a different integrationTagId", exception.getMessage());
    }
}
