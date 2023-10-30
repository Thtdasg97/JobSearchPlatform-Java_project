package com.example.springasm02.service;
import com.example.springasm02.entity.ApplyPost;
import com.example.springasm02.entity.Cv;
import java.util.List;

public interface ApplyPostService {
    List<ApplyPost> findAllApplyPost();
    int getTotalPages(int pageSize);
    void saveApplyPost(ApplyPost applyPost);

    void saveCv(Cv cv);

}
