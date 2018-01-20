package net.datascientists.service;

import net.datascientists.vo.NoteVO;

import java.util.List;

public interface NoteService extends BaseService<NoteVO>{
    List<NoteVO> getListByInterview(long interviewId);
}
