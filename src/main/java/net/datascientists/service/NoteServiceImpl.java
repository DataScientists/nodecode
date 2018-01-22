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
public class NoteServiceImpl {

	@Autowired
	private NoteDao dao;
	
	@Autowired
	private NoteMapper mapper;

	
	public List<NoteVO> listAll() {
		List<NoteVO> retValue = new ArrayList<NoteVO>();
		List <Note> notes = dao.list();
		retValue = mapper.convertToVOList(notes);
		return retValue;
	}

	
	public List<NoteVO> findById(Long id) {
		Note note = dao.findById(id);
		NoteVO noteVO = mapper.convertToVO(note);
		List<NoteVO> list = new ArrayList<NoteVO>();
		list.add(noteVO);
		return list;
	}

	
	public NoteVO create(NoteVO o) {
		Note noteEntity = dao.save(mapper.convertToEntity(o));
		return mapper.convertToVO(noteEntity);
	}

	
	public void update(NoteVO vo) {
		Note note = mapper.convertToEntity(vo);
		dao.save(note);
	}

	
	public void delete(NoteVO vo) {
		dao.deleteSoft(mapper.convertToEntity(vo));
	}
}
