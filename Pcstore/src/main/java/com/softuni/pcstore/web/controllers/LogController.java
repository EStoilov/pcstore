package com.softuni.pcstore.web.controllers;

import com.softuni.pcstore.domain.models.views.LogAllViewModel;
import com.softuni.pcstore.service.LogService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/logs")
public class LogController extends BaseController {
    private final LogService logService;
    private final ModelMapper modelMapper;

    @Autowired
    public LogController(LogService logService, ModelMapper modelMapper) {
        this.logService = logService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView allLogs(ModelAndView modelAndView){
        List<LogAllViewModel> logs = this.logService.findAllLogs().stream()
                .map(l -> this.modelMapper.map(l, LogAllViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("logs", logs);
        
        return view("logs/all-logs", modelAndView);
    }

    @PostMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView allLogsConfirm(){
        this.logService.deleteAllLogs();

        return redirect("/home");
    }
}
