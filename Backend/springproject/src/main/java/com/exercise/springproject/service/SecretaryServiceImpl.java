package com.exercise.springproject.service;


import com.exercise.springproject.api.SecretaryRepository;
import com.exercise.springproject.domain.secretary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecretaryServiceImpl implements  SecretaryService{
    @Autowired
    private SecretaryRepository secretaryRepository;

    @Override
    public secretary findSecretaryById(int id) {
        return secretaryRepository.findSecretaryById(id);
    }
}