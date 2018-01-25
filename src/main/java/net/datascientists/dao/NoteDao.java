package net.datascientists.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.datascientists.entity.Note;

@Repository
public class NoteDao implements BaseDao<Note>{

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void deleteSoft(Note note){
    	note.setDeleted(1);
        sessionFactory.getCurrentSession().saveOrUpdate(note);
    }
	
	@Override
    public void deleteHard(Note note){
        sessionFactory.getCurrentSession().delete(note);
    }
	
	@Override
	public Note findById(Long id){
		return (Note) sessionFactory.getCurrentSession().get(Note.class, id);
    }
	
	@Override
	public Note save(Note note){
    	sessionFactory.getCurrentSession().saveOrUpdate(note);
    	return note;
    }
	
	@Override
    @SuppressWarnings("unchecked")
	public List<Note> list() {
    	final Session session = sessionFactory.getCurrentSession();
    	final Criteria crit = session.createCriteria(Note.class);
    	crit.add(Restrictions.eq("deleted", 0));
    	return crit.list();
    }
	
	@Override
    @SuppressWarnings("unchecked")
   	public List<Note> listDeleted() {
    	final Session session = sessionFactory.getCurrentSession();
        final Criteria crit = session.createCriteria(Note.class);
        crit.add(Restrictions.eq("deleted", 1));
        return crit.list();
    }

    @Override
    public Object update(Note entity)
    {
        // TODO Auto-generated method stub
        return null;
    } 
}
