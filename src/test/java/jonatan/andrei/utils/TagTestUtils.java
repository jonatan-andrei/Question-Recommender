package jonatan.andrei.utils;

import jonatan.andrei.model.Tag;
import jonatan.andrei.repository.TagRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class TagTestUtils {

    @Inject
    TagRepository tagRepository;

    public Tag saveWithName(String name) {
        return tagRepository.save(Tag.builder()
                .name(name)
                .active(true)
                .questionCount(0)
                .build());
    }
}
