package jonatan.haas.resource;

import jonatan.haas.dto.TagRequestDto;
import jonatan.haas.service.TagService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/tag")
@ApplicationScoped
public class TagResource {

    @Inject
    TagService tagService;

    @POST
    public void saveOrUpdate(@Valid TagRequestDto tagRequestDto) {
        tagService.saveOrUpdate(tagRequestDto);
    }
}
