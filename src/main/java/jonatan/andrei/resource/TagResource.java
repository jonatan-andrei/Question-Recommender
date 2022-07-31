package jonatan.andrei.resource;

import jonatan.andrei.dto.TagRequestDto;
import jonatan.andrei.service.TagService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/tag")
@ApplicationScoped
public class TagResource {

    @Inject
    TagService tagService;

    @POST
    public void saveOrUpdate(TagRequestDto tagRequestDto) {
        tagService.saveOrUpdate(tagRequestDto);
    }
}
