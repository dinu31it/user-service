package com.myservice.userservice.service;

import com.myservice.userservice.entity.User;
import com.myservice.userservice.model.Department;
import com.myservice.userservice.model.ResponseTemplateObject;
import com.myservice.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    public User saveUser(User user) {
        log.info("Inside saveUser of UserService");
        return userRepository.save(user);
    }

    public ResponseTemplateObject getUserWithDepartment(Long userId) {
        log.info("Inside saveUser of getUserWithDepartment");
        ResponseTemplateObject rto = new ResponseTemplateObject();
        User user = userRepository.findByUserId(userId);

        Department department = restTemplate
                .getForObject("http://DEPARTMENT-SERVICE/department/"+user.getDepartmentId(), Department.class);
        rto.setUser(user);
        rto.setDepartment(department);
        return rto;
    }
}
