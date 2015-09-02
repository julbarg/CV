package com.claro.cv.service;

import org.springframework.stereotype.Service;

import com.claro.cv.dto.UserDTO;


@Service
public interface LoginService {

   public boolean authenticate(UserDTO user) throws Exception;

}
