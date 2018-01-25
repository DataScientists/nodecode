package net.datascientists.service;

import net.datascientists.vo.NoteVO;

import java.util.List;

public interface NoteService extends BaseService<NoteVO>{
    
    List<NoteVO> findById(Long id);
    List<NoteVO> list();
    List<NoteVO> getListByInterview(long interviewId);
}
