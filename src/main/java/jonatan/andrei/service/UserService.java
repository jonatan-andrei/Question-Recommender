package jonatan.andrei.service;

import jonatan.andrei.dto.CreateUserRequestDto;
import jonatan.andrei.dto.UpdateUserRequestDto;
import jonatan.andrei.dto.UserFollowerRequestDto;
import jonatan.andrei.model.User;
import jonatan.andrei.model.UserFollower;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class UserService {

    @Transactional
    public User save(CreateUserRequestDto createUserRequestDto) {
        return null;
    }

    @Transactional
    public User update(UpdateUserRequestDto updateUserRequestDto) {
        return null;
    }

    @Transactional
    public UserFollower registerFollower(UserFollowerRequestDto userFollowerRequestDto) {
        // Valida se ambos usuários existem
        // Valida se deve remover ou adicionar (e se usuários eram amigos em caso de remoção)
        return null;
    }
}
