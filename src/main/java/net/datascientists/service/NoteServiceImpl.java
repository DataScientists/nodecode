package net.datascientists.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.datascientists.dao.NoteDao;
import net.datascientists.entity.Note;
import net.datascientists.mapper.NoteMapper;
import net.datascientists.vo.NoteVO;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class NoteServiceImpl implements NoteService{

	@Autowired
	private NoteDao dao;
	
	@Autowired
	private NoteMapper mapper;

	@Override
	public List<NoteVO> list() {
		List<NoteVO> retValue = new ArrayList<NoteVO>();
		List <Note> notes = dao.list();
		retValue = mapper.convertToVOList(notes);
		return retValue;
	}

	@Override
	public List<NoteVO> findById(Long id) {
		Note note = dao.findById(id);
		NoteVO noteVO = mapper.convertToVO(note);
		List<NoteVO> list = new ArrayList<NoteVO>();
		list.add(noteVO);
		return list;
	}

	@Override
	public NoteVO save(NoteVO o) {
		Note noteEntity = dao.save(mapper.convertToEntity(o));
		return mapper.convertToVO(noteEntity);
	}

	@Override
	public NoteVO update(NoteVO vo) {
		Note note = mapper.convertToEntity(vo);
		dao.update(note);
		return vo;
	}

	@Override
	public void deleteSoft(NoteVO vo) {
		dao.deleteSoft(mapper.convertToEntity(vo));
	}

    @Override
    public void deleteHard(NoteVO entity)
    {
        // TODO Auto-generated method stub
    }

    @Override
    public List<? extends Object> listDeleted()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<NoteVO> getListByInterview(long interviewId)
    {
        // TODO Auto-generated method stub
        return null;
    }
}
