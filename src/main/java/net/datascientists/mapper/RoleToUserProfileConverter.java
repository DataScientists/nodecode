package net.datascientists.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import net.datascientists.entity.Roles;
import net.datascientists.service.UserRoleService;

@Component
public class RoleToUserProfileConverter implements Converter<Object, Roles>{
 
    @Autowired
    UserRoleService userProfileService;
 
    public Roles convert(Object element) {
        Integer id = Integer.parseInt((String)element);
        Roles profile= userProfileService.findById(id);
        System.out.println("Profile : "+profile);
        return profile;
    }
 
}
