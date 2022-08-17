package jonatan.andrei.service;

import jonatan.andrei.dto.TagRequestDto;
import jonatan.andrei.exception.RequiredDataException;
import jonatan.andrei.factory.TagFactory;
import jonatan.andrei.model.Tag;
import jonatan.andrei.repository.TagRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@ApplicationScoped
public class TagService {

    @Inject
    TagRepository tagRepository;

    @Transactional
    public Tag saveOrUpdate(TagRequestDto tagRequestDto) {
        validateRequiredData(tagRequestDto);
        Tag existingTag = tagRepository.findByName(tagRequestDto.getName());
        if (isNull(existingTag)) {
            return tagRepository.save(TagFactory.newTag(tagRequestDto));
        } else {
            return tagRepository.save(TagFactory.overwrite(existingTag, tagRequestDto));
        }

    }

    public void incrementQuestionCountByTagsIds(List<Long> tagsIds) {
        tagRepository.incrementQuestionCount(tagsIds);
    }

    public void decrementQuestionCountByTagsIds(List<Long> tagsIds) {
        tagRepository.decrementQuestionCount(tagsIds);
    }

    @Transactional
    public List<Tag> saveOrUpdate(List<TagRequestDto> tags) {
        return tags.stream().map(tag -> saveOrUpdate(tag)).collect(Collectors.toList());
    }

    public List<Tag> findOrCreateTags(List<String> tagsName) {
        List<Tag> tags = tagRepository.findByNameIn(tagsName);
        tagsName.forEach(tag -> {
            Optional<Tag> foundTag = tags.stream().filter(t -> t.getName().equals(tag))
                    .findFirst();
            if (foundTag.isEmpty()) {
                tags.add(tagRepository.save(TagFactory.newTag(tag)));
            }
        });
        return tags;
    }

    private void validateRequiredData(TagRequestDto tagRequestDto) {
        if (isNull(tagRequestDto.getName())) {
            throw new RequiredDataException("Attribute 'name' is required");
        }
    }

}
