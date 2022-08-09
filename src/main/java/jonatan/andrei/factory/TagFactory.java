package jonatan.andrei.factory;

import jonatan.andrei.dto.TagRequestDto;
import jonatan.andrei.model.Tag;

public class TagFactory {

    public static Tag newTag(TagRequestDto tagRequestDto){
        return Tag.builder()
                .name(tagRequestDto.getName())
                .description(tagRequestDto.getDescription())
                .active(tagRequestDto.isActive())
                .questionCount(0)
                .build();
    }

    public static Tag overwrite (Tag existingTag, TagRequestDto tagRequestDto){
        existingTag.setActive(tagRequestDto.isActive());
        existingTag.setName(tagRequestDto.getName());
        existingTag.setDescription(tagRequestDto.getDescription());
        return existingTag;
    }
}
