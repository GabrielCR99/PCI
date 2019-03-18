package com.studio.pci.providers;
import com.studio.pci.models.User;

public class UserDAO extends AbstractDAO<User> {
    public UserDAO() {
        super("users");
    }
}
