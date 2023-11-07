package com.example.jobsearchflatform.service;
import com.example.jobsearchflatform.entity.ApplyPost;
import com.example.jobsearchflatform.entity.Cv;
import java.util.List;

public interface ApplyPostService {
    List<ApplyPost> findAllApplyPost();
    int getTotalPages(int pageSize);
    void saveApplyPost(ApplyPost applyPost);

    void saveCv(Cv cv);

}
