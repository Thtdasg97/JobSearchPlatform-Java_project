package com.example.springasm02.service;
import com.example.springasm02.dao.ApplyPostRepository;
import com.example.springasm02.dao.CvRepository;
import com.example.springasm02.entity.ApplyPost;
import com.example.springasm02.entity.Cv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplyPostServiceImpl implements ApplyPostService {
    private ApplyPostRepository applyPostRepository;
    private CvRepository cvRepository;

    @Autowired
    public ApplyPostServiceImpl(ApplyPostRepository applyPostRepository, CvRepository cvRepository) {
        this.applyPostRepository = applyPostRepository;
        this.cvRepository = cvRepository;
    }

    @Override
    public List<ApplyPost> findAllApplyPost() {
        return applyPostRepository.findAll();
    }

    @Override
    public int getTotalPages(int pageSize) {
        long totalApplyPosts = applyPostRepository.count();
        return (int) Math.ceil((double) totalApplyPosts / pageSize);
    }

    @Override
    public void saveApplyPost(ApplyPost applyPost) {
        applyPostRepository.save(applyPost);
    }

    @Override
    public void saveCv(Cv cv) {
        cvRepository.save(cv);
    }

}
