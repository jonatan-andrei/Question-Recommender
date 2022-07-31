package jonatan.andrei.service;

import jonatan.andrei.dto.TagRequestDto;
import jonatan.andrei.exception.InconsistentIntegratedDataException;
import jonatan.andrei.exception.RequiredDataException;
import jonatan.andrei.factory.TagFactory;
import jonatan.andrei.model.Tag;
import jonatan.andrei.repository.TagRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Objects;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@ApplicationScoped
public class TagService {

    @Inject
    TagRepository tagRepository;

    @Transactional
    public Tag saveOrUpdate(TagRequestDto tagRequestDto) {
        validateRequiredData(tagRequestDto);
        Tag existingTagWithIntegrationTagId = tagRepository.findByIntegrationTagId(tagRequestDto.getIntegrationTagId());
        Tag existingTagWithName = tagRepository.findByName(tagRequestDto.getName());
        if (nonNull(existingTagWithIntegrationTagId)) {
            validateExistingTagWithNameWithIntegrationTagId(existingTagWithIntegrationTagId, existingTagWithName);
            return tagRepository.save(TagFactory.overwrite(existingTagWithIntegrationTagId, tagRequestDto));
        } else if (nonNull(existingTagWithName)) {
            validateExistingTagWithName(existingTagWithName, tagRequestDto);
            return tagRepository.save(TagFactory.overwrite(existingTagWithName, tagRequestDto));
        } else {
            return tagRepository.save(TagFactory.newTag(tagRequestDto));
        }
    }

    private void validateExistingTagWithNameWithIntegrationTagId(Tag existingTagWithIntegrationTagId, Tag existingTagWithName) {
        if (nonNull(existingTagWithName) && !Objects.equals(existingTagWithIntegrationTagId.getIntegrationTagId(), existingTagWithName.getIntegrationTagId())) {
            throw new InconsistentIntegratedDataException("There is already a tag registered for this name but with a different integrationTagId");
        }
    }

    private void validateExistingTagWithName(Tag existingTagWithName, TagRequestDto tagRequestDto) {
        if (nonNull(existingTagWithName.getIntegrationTagId()) && !existingTagWithName.getIntegrationTagId().equals(tagRequestDto.getIntegrationTagId())) {
            throw new InconsistentIntegratedDataException("There is already a tag registered for this name but with a different integrationTagId");
        }
    }

    private void validateRequiredData(TagRequestDto tagRequestDto) {
        if (isNull(tagRequestDto.getIntegrationTagId())) {
            throw new RequiredDataException("Attribute 'integrationTagId' is required");
        }
        if (isNull(tagRequestDto.getName())) {
            throw new RequiredDataException("Attribute 'name' is required");
        }
    }

}
