package jonatan.haas.service;

import jonatan.haas.dto.TagRequestDto;
import jonatan.haas.factory.TagFactory;
import jonatan.haas.model.Tag;
import jonatan.haas.repository.TagRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Objects;

import static java.util.Objects.nonNull;

@ApplicationScoped
public class TagService {

    @Inject
    TagRepository tagRepository;

    @Transactional
    public Tag saveOrUpdate(TagRequestDto tagRequestDto) {
        // TODO validar informações obrigatórias
        Tag existingTagWithIntegrationTagId = tagRepository.findByIntegrationTagId(tagRequestDto.getIntegrationTagId());
        Tag existingTagWithName = tagRepository.findByName(tagRequestDto.getName());
        if (nonNull(existingTagWithIntegrationTagId)) {
            if (nonNull(existingTagWithName) && !Objects.equals(existingTagWithIntegrationTagId.getIntegrationTagId(), existingTagWithName.getIntegrationTagId())) {
                // TODO exception - tag de nome e id diferentes
            }
            return tagRepository.save(TagFactory.overwrite(existingTagWithIntegrationTagId, tagRequestDto));
        } else if (nonNull(existingTagWithName)) {
            if (nonNull(existingTagWithName.getIntegrationTagId()) && !existingTagWithName.getIntegrationTagId().equals(tagRequestDto.getIntegrationTagId())) {
                // TODO exception - tag de nome e id diferentes
            }
            return tagRepository.save(TagFactory.overwrite(existingTagWithName, tagRequestDto));
        } else {
            return tagRepository.save(TagFactory.newTag(tagRequestDto));
        }
    }

}
