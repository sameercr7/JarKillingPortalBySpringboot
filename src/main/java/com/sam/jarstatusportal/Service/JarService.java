package com.sam.jarstatusportal.Service;

import com.sam.jarstatusportal.Entity.User;

import java.io.IOException;
import java.util.List;

public interface JarService {
    String uplaodingAllFormData(User user) throws IOException;

    List<User> findAllJarsData();
}
