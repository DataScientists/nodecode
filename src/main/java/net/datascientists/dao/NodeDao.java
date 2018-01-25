package net.datascientists.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.datascientists.dao.base.BaseDao;
import net.datascientists.entity.Node;

@Repository
public class NodeDao implements BaseDao<Node>
{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Node save(Node node){
        sessionFactory.getCurrentSession().saveOrUpdate(node);
        return node;
    }
    @Override
    public void deleteSoft(Node node){
        node.setDeleted(1);
        sessionFactory.getCurrentSession().saveOrUpdate(node);
    }
    @Override
    public void deleteHard(Node node){
        sessionFactory.getCurrentSession().delete(node);
    }
    @Override
    public Node findById(Long id){
        Node retValue = (Node) sessionFactory.getCurrentSession().get(Node.class, id);
        return retValue;
    }
    @Override
    public List<Node> list(){
        final Session session = sessionFactory.getCurrentSession();
        final Criteria crit = session.createCriteria(Node.class);
        crit.add(Restrictions.eq("deleted", 0));
        return crit.list();
    }

    @Override
    public List<Node> listDeleted(){
        final Session session = sessionFactory.getCurrentSession();
        final Criteria crit = session.createCriteria(Node.class);
        crit.add(Restrictions.eq("deleted", 1));
        return crit.list();
    }


}
