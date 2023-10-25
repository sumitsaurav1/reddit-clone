package com.javateam.service;

import com.javateam.repository.SubredditRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

@Transactional
public class SubredditService {
    private SubredditRepository subredditRepository;

    @Autowired
    public SubredditService(SubredditRepository subredditRepository){
        this.subredditRepository = subredditRepository;
    }

}
