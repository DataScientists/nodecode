package net.datascientists.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import net.datascientists.entity.Note;
import net.datascientists.mapper.base.BaseMapper;
import net.datascientists.vo.NoteVO;

@Component
public class NoteMapper implements BaseMapper<NoteVO,Note>{
 
    @Override
    public NoteVO convertToVO(Note note) {
        if (note == null) {
            return null;
        }
        NoteVO noteVO = new NoteVO();
        noteVO.setId(note.getId());
        noteVO.setDeleted(note.getDeleted());
        noteVO.setLastUpdated(note.getLastUpdated());
        		
        return noteVO;
    }
    
    @Override
	public Note convertToEntity(NoteVO noteVO) {
		if(noteVO == null){
			return null;
		}
		Note note = new Note();
		note.setId(noteVO.getId()); 
		note.setDeleted(noteVO.getDeleted());
        note.setLastUpdated(noteVO.getLastUpdated());
        
		return note;
	}	
    
    @Override
	public List<Note> convertToEntityList(List<NoteVO> noteVOs) {
		if (noteVOs == null) {
            return null;
        }
        List<Note> list = new ArrayList<Note>();
        for (NoteVO note : noteVOs) {       	
        	list.add(convertToEntity(note));       	
        }
        return list;
	}	
    
    @Override
	public List<NoteVO> convertToVOList(List<Note> entityList) {
		if (entityList == null) {
            return null;
        }
        List<NoteVO> list = new ArrayList<NoteVO>();
        for (Note entity : entityList) {
            list.add((NoteVO)convertToVO(entity));
        }
        return list;
	}		
}
