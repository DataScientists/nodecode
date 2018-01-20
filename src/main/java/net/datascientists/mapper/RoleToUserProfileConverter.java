package net.datascientists.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import net.datascientists.entity.UserRole;
import net.datascientists.service.UserProfileService;

@Component
public class RoleToUserProfileConverter implements Converter<Object, UserRole>{
 
    @Autowired
    UserProfileService userProfileService;
 
    public UserRole convert(Object element) {
        Integer id = Integer.parseInt((String)element);
        UserRole profile= userProfileService.findById(id);
        System.out.println("Profile : "+profile);
        return profile;
    }
 
}
