package com.softuni.pcstore.service;

import com.softuni.pcstore.common.Constants;
import com.softuni.pcstore.domain.entities.Log;
import com.softuni.pcstore.domain.entities.User;
import com.softuni.pcstore.domain.models.service.LogServiceModel;
import com.softuni.pcstore.domain.models.service.UserServiceModel;
import com.softuni.pcstore.repository.LogRepository;
import com.softuni.pcstore.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogServiceImpl implements LogService {
    
    private final LogRepository logRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public LogServiceImpl(LogRepository logRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.logRepository = logRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createLog(LogServiceModel logServiceModel, String username) {
        Log logEntity = this.modelMapper
                .map(logServiceModel, Log.class);

        User userEntity = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException(Constants.EXCEPTION_NOT_FOUND));

        logEntity.setUser(userEntity);
        LocalDateTime date = LocalDateTime.now();
        logEntity.setDateTime(date);
        
        
        this.logRepository.save(logEntity);
    }

    @Override
    public List<LogServiceModel> findAllLogs() {
        List<LogServiceModel> logs = this.logRepository.findAll().stream()
                .map( log -> {
                    LogServiceModel logServiceModel = this.modelMapper.map(log, LogServiceModel.class);
                    logServiceModel.setUser(log.getUser().getUsername());
                    
                    return logServiceModel;
                })
                .collect(Collectors.toList());
        
        
        return logs; 
    }

    @Override
    public void deleteAllLogs() {
        this.logRepository.deleteAll();
    }
}
