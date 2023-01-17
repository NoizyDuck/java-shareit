package ru.practicum.shareit.user;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exceptions.DuplicatedEmailException;
import ru.practicum.shareit.exceptions.IncorrectParameterException;
import ru.practicum.shareit.exceptions.NotFoundException;

import java.util.*;

@Component
public class UserRepositoryImpl implements UserRepository {

    private final Map<Integer, User> users = new HashMap<>();
    private Integer id = 0;

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User get(Integer id) {
        validateId(id);
        return users.get(id);
    }

    @Override
    public void remove(Integer id) {
        validateId(id);
        users.remove(id);
    }

    @Override
    public User update(User user) {
        validateId(user.getId());
        validateEmail(user);
//        users.put(user.getId(), user);
//        return user;
        User patchedUser = users.get(user.getId());
        if (user.getName() != null && !user.getName().isEmpty()) {
            patchedUser.setName(user.getName());
        }
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            patchedUser.setEmail(user.getEmail());
        }
        users.put(patchedUser.getId(), patchedUser);
        return users.get(patchedUser.getId());
    }


    @Override
    public User add(User user) {
        validateEmail(user);
        user.setId(++id);
        users.put(user.getId(), user);
        return user;
    }

    private void validateId(Integer id) {
        if (id != 0 && !users.containsKey(id)) {
            throw new NotFoundException("User with id " + id + " not registered");
        }
    }

    private void validateEmail(User user) {
        if (user.getEmail().isEmpty() || user.getEmail() == null){
            throw new IncorrectParameterException("Invalid email");
        }
        if (users.values()
                .stream()
                .anyMatch(
                        stored -> stored.getEmail().equalsIgnoreCase(user.getEmail())
                                && stored.getId() != user.getId()
                )
        ) {
            throw new DuplicatedEmailException("User with email " +
                    user.getEmail() + " already exist");
        }
    }

}
