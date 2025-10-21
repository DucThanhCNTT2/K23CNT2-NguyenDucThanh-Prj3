package pkg_annotation;

import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @GetMapping("/users")
    public String getUsers() {
        return "<h1>Get all users</h1>";
    }

    @PostMapping("/users")
    public String createUser() {
        return "<h1>User created</h1>";
    }

    @PutMapping("/users/{id}")
    public String updateUser(@PathVariable int id) {
        return "<h1>User with ID " + id + " updated</h1>";
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable int id) {
        return "<h1>User with ID " + id + " deleted</h1>";
    }
}

