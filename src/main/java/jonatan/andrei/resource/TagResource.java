package jonatan.andrei.resource;

import jonatan.andrei.dto.TagRequestDto;
import jonatan.andrei.model.Tag;
import jonatan.andrei.service.TagService;
import org.springframework.http.ResponseEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.List;

@Path("/tag")
@ApplicationScoped
public class TagResource {

    @Inject
    TagService tagService;

    @POST
    public ResponseEntity<Tag> saveOrUpdate(TagRequestDto tagRequestDto) {
        return ResponseEntity.ok(tagService.saveOrUpdate(tagRequestDto));
    }

    @POST
    @Path("/list")
    public ResponseEntity<List<Tag>> saveOrUpdate(List<TagRequestDto> tags) {
        return ResponseEntity.ok(tagService.saveOrUpdate(tags));
    }
}
