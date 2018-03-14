package net.datascientists.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.datascientists.dao.base.BaseDao;
import net.datascientists.entity.Role;
import net.datascientists.mapper.RoleMapper;
import net.datascientists.service.base.BaseService;
import net.datascientists.vo.RoleVO;

@Transactional
@Service("RoleService")
public class RoleService implements BaseService<RoleVO> {

    @Autowired
    @Qualifier("RoleDao")
    private BaseDao<Role> dao;
    @Autowired
    private RoleMapper mapper;
    
    @Override
    public List<RoleVO> list(){
        List<Role> roles = dao.list();
        return mapper.convertToVOList(roles);
    }

    @Override
    public List<RoleVO> find(String searchName, Object searchVal){
        List<Role> roles = dao.find(searchName,searchVal);
        return mapper.convertToVOList(roles);
    }

    @Override
    public RoleVO save(RoleVO entity){
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteSoft(RoleVO entity){
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteHard(RoleVO entity){
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<RoleVO> listDeleted(){
        // TODO Auto-generated method stub
        return null;
    }


}
