package net.datascientists.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.datascientists.dao.base.BaseDao;
import net.datascientists.entity.Note;
import net.datascientists.mapper.NoteMapper;
import net.datascientists.service.base.BaseService;
import net.datascientists.vo.NoteVO;


@Service("NoteService")
@Transactional
public class NoteService implements BaseService<NoteVO>{

	@Autowired
	@Qualifier("NoteDao")
	private BaseDao<Note> dao;
	
	@Autowired
	private NoteMapper mapper;

	@Override
	public List<NoteVO> list() {
		List<NoteVO> retValue = new ArrayList<NoteVO>();
        List <Note> notes = (List<Note>) dao.list();
		retValue = mapper.convertToVOList(notes);
		return retValue;
	}

	@Override
	public List<NoteVO> find(String searchName, Object searchVal) {
		Note note = (Note) dao.find(searchName,searchVal);
		NoteVO noteVO = mapper.convertToVO(note);
		List<NoteVO> list = new ArrayList<NoteVO>();
		list.add(noteVO);
		return list;
	}

	@Override
	public NoteVO save(NoteVO o) {
		Note noteEntity = (Note) dao.save(mapper.convertToEntity(o));
		return mapper.convertToVO(noteEntity);
	}

	@Override
	public void deleteSoft(NoteVO vo) {
		dao.deleteSoft(mapper.convertToEntity(vo));
	}

    @Override
    public void deleteHard(NoteVO vo)
    {
        dao.deleteHard(mapper.convertToEntity(vo));
    }

    @Override
    public List<NoteVO> listDeleted()
    {
    	List<NoteVO> retValue = new ArrayList<NoteVO>();
        List <Note> Notes = (List<Note>) dao.listDeleted();
		retValue = mapper.convertToVOList(Notes);
		return retValue;
    }   
}
