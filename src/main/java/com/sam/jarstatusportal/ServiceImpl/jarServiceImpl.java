package com.sam.jarstatusportal.ServiceImpl;

import com.sam.jarstatusportal.Entity.User;
import com.sam.jarstatusportal.Repository.JarRepo;
import com.sam.jarstatusportal.Service.JarService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
@Transactional
public class jarServiceImpl implements JarService {


    @Autowired
    private JarRepo jarRepo;

    @Value("${upload.directory}")
    public String jarUplaodDirectory;

    @Override
    public String uplaodingAllFormData(User user) throws IOException {

        MultipartFile jarFile = user.getJarFile();

        Path jarFilePath = Path.of(jarUplaodDirectory +"/"+ jarFile.getOriginalFilename());

        Files.createDirectories(jarFilePath.getParent());
        jarFile.transferTo(jarFilePath);
        user.setJarFilePath(String.valueOf(jarFilePath));


        jarRepo.save(user);
        return "data saved successfully";
    }

    @Override
    public List<User> findAllJarsData() {
        return jarRepo.findAll();
    }
}
