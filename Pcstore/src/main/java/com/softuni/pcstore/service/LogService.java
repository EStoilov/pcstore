package com.softuni.pcstore.service;

import com.softuni.pcstore.domain.models.service.LogServiceModel;

import java.util.List;
public interface LogService {
    
    void createLog(LogServiceModel logServiceModel, String username);
    
    List<LogServiceModel> findAllLogs();
    
    void deleteAllLogs();
}
