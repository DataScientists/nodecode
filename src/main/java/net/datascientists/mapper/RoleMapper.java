package net.datascientists.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import net.datascientists.entity.Role;
import net.datascientists.mapper.base.BaseMapper;
import net.datascientists.vo.RoleVO;

@Component
public class RoleMapper implements BaseMapper<RoleVO,Role>{
 
    @Override
    public RoleVO convertToVO(Role Role) {
        if (Role == null) {
            return null;
        }
        RoleVO RoleVO = new RoleVO();
        RoleVO.setId(Role.getId());
        RoleVO.setDeleted(Role.getDeleted());
        RoleVO.setLastUpdated(Role.getLastUpdated());
        RoleVO.setName(Role.getName());
        RoleVO.setDescription(Role.getDescription());
        		
        return RoleVO;
    }
    
    @Override
	public Role convertToEntity(RoleVO RoleVO) {
		if(RoleVO == null){
			return null;
		}
		Role Role = new Role();
		Role.setId(RoleVO.getId()); 
		Role.setDeleted(RoleVO.getDeleted());
        Role.setLastUpdated(RoleVO.getLastUpdated());
        Role.setName(RoleVO.getName());
        Role.setDescription(RoleVO.getDescription());
        
		return Role;
	}	
    
    @Override
	public List<Role> convertToEntityList(List<RoleVO> RoleVOs) {
		if (RoleVOs == null) {
            return null;
        }
        List<Role> list = new ArrayList<Role>();
        for (RoleVO Role : RoleVOs) {       	
        	list.add(convertToEntity(Role));       	
        }
        return list;
	}	
    
    @Override
	public List<RoleVO> convertToVOList(List<Role> entityList) {
		if (entityList == null) {
            return null;
        }
        List<RoleVO> list = new ArrayList<RoleVO>();
        for (Role entity : entityList) {
            list.add((RoleVO)convertToVO(entity));
        }
        return list;
	}		
}
