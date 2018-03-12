package net.datascientists.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.datascientists.dao.JMXDao;
import net.datascientists.mapper.JMXLogMapper;
import net.datascientists.service.base.BaseService;
import net.datascientists.vo.JMXLogVO;

@Service("JMXService")
@Transactional
public class JMXService implements BaseService<JMXLogVO>
{

    @Autowired
    private JMXDao dao;
    @Autowired
    private JMXLogMapper mapper;
    
    @Override
    public JMXLogVO save(JMXLogVO vo)
    {
        dao.save(mapper.convertToEntity(vo));
        return vo;
    }

    @Override
    public void deleteSoft(JMXLogVO vo)
    {
        dao.deleteSoft(mapper.convertToEntity(vo));
    }

    @Override
    public void deleteHard(JMXLogVO vo)
    {
        dao.deleteHard(mapper.convertToEntity(vo));
    }

    @Override
    public List<JMXLogVO> find(String searchName, Object searchVal)
    {
        return mapper.convertToVOList(dao.find(searchName, searchVal));
    }

    @Override
    public List<JMXLogVO> list()
    {
        return mapper.convertToVOList(dao.list());
    }

    @Override
    public List<JMXLogVO> listDeleted()
    {
        return mapper.convertToVOList(dao.listDeleted());
    }



}
