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
public class NoteServiceImpl implements NoteService {

	@Autowired
	private NoteDao dao;
	
	@Autowired
	private NoteMapper mapper;

	@Override
	public List<NoteVO> listAll() {
		return mapper.convertToNoteVOList(dao.getAllActive());
	}

	@Override
	public List<NoteVO> findById(Long id) {
		Note note = dao.get(id);
		NoteVO noteVO = mapper.convertToNoteVO(note);
		List<NoteVO> list = new ArrayList<NoteVO>();
		list.add(noteVO);
		return list;
	}

	@Override
	public NoteVO create(NoteVO o) {
		Note noteEntity = dao.save(mapper.convertToNote(o));
		return mapper.convertToNoteVO(noteEntity);
	}

	@Override
	public void update(NoteVO o) {
		dao.saveOrUpdate(mapper.convertToNote(o));
	}

	@Override
	public void delete(NoteVO o) {
		dao.delete(mapper.convertToNote(o));
	}

    @Override
    public List<NoteVO> getListByInterview(long interviewId) {
        List<Note> list = dao.getListByInterview(interviewId);
        return mapper.convertToNoteVOList(list);
    }
}
