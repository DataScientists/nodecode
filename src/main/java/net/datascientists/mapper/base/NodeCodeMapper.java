package net.datascientists.mapper.base;

import java.util.List;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NodeCodeMapper {

	Object convertToVO(Object entity);

	List<Object> convertToVOList(List<Object> entityList);

	Object convertToEntity(Object vo);
	
	List<Object> convertToEntityList(List<Object> voList);
	
}
