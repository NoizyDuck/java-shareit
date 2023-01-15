package ru.practicum.shareit.user;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class UserRepositoryImpl implements UserRepository{

    private final List<User> users = new ArrayList<>();
    @Override
    public List<User> getAll() {
        return users;
    }

    @Override
    public User get(Integer id) {
        return users.get(id);
    }

    @Override
    public void remove(Integer id) {
    users.removeIf(user -> user.getId().equals(id));
    }

    @Override
    public User update(User user) {
        //TODO добавить эксепшн
        if(users.contains(user)) {
            users.remove(user.getId());
            users.add(user);
        }else {
            System.out.println("User not exist");
        }
        return user;
    }

    @Override
    public User add(User user) {
            if(!users.contains(user)){
                user.setId(getId());
                users.add(user);
            } else{
         System.out.println("User already exist");
            }
        return user;
    }
    private Integer getId() {
        Integer lastId = users.stream()
                .mapToInt(User::getId)
                .max()
                .orElse(0);
        return lastId + 1;
    }
}
