package net.datascientists.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.datascientists.entity.Node;

@Repository
public class NodeDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void deleteSoft(Node node){
    	node.setDeleted(1);
        sessionFactory.getCurrentSession().saveOrUpdate(node);
    }
    public void deleteHard(Node node){
        sessionFactory.getCurrentSession().delete(node);
    }
	public Node findById(Long id){
		Node retValue = (Node) sessionFactory.getCurrentSession().get(Node.class, id);
		return retValue;
    }
	public Node save(Node node){
    	sessionFactory.getCurrentSession().saveOrUpdate(node);
    	return node;
    }
    @SuppressWarnings("unchecked")
	public List<Node> list() {
    	final Session session = sessionFactory.getCurrentSession();
    	final Criteria crit = session.createCriteria(Node.class);
    	crit.add(Restrictions.eq("deleted", 0));
    	return crit.list();
    }
    @SuppressWarnings("unchecked")
   	public List<Node> listDeleted() {
    	final Session session = sessionFactory.getCurrentSession();
        final Criteria crit = session.createCriteria(Node.class);
        crit.add(Restrictions.eq("deleted", 1));
        return crit.list();
    } 
}
