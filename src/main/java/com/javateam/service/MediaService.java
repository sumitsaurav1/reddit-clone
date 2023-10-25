package com.javateam.service;
import com.javateam.model.Media;
import com.javateam.repository.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MediaService {
    private MediaRepository mediaRepository;
    @Autowired
    public MediaService(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    public Media create(Media media) {
        return mediaRepository.save(media);
    }

    public Media update(Media media) {
        return mediaRepository.save(media);
    }
    public List<Media> viewAll() {
        return (List<Media>) mediaRepository.findAll();
    }


    public Media viewById(Integer id) {
        return mediaRepository.findById(id).get();
    }
}
