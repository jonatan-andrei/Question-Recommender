package jonatan.haas.service;

import jonatan.haas.dto.TagRequestDto;
import jonatan.haas.factory.TagFactory;
import jonatan.haas.model.Tag;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class TagService {

    @Inject
    EntityManager entityManager; // TODO criar repositório

    @Transactional
    public Tag save(TagRequestDto tagRequestDto) {
        // TODO Verifica se Tag existe
        entityManager.persist(TagFactory.newTag(tagRequestDto));
        // TODO adicionar retorno de tag
        return null;
    }

}
