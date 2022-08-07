package jonatan.andrei.resource;

import jonatan.andrei.dto.CategoryRequestDto;
import jonatan.andrei.dto.TagRequestDto;
import jonatan.andrei.model.Category;
import jonatan.andrei.model.Tag;
import jonatan.andrei.service.CategoryService;
import org.springframework.http.ResponseEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.List;

@Path("/category")
@ApplicationScoped
public class CategoryResource {

    @Inject
    CategoryService categoryService;

    @POST
    public ResponseEntity<Category> saveOrUpdate(CategoryRequestDto categoryRequestDto) {
        return ResponseEntity.ok(categoryService.saveOrUpdate(categoryRequestDto));
    }

    @POST
    @Path("/list")
    public ResponseEntity<List<Category>> saveOrUpdate(List<CategoryRequestDto> categories) {
        return ResponseEntity.ok(categoryService.saveOrUpdate(categories));
    }

}
