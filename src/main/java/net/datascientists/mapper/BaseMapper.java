package net.datascientists.mapper;

import java.util.List;

public interface BaseMapper<V,E>
{

    public V convertToVO(E entity);
    public E convertToEntity(V vo);
    public List<E> convertToEntityList(List<V> voList) ;
    public List<V> convertToVOList(List<E> entityList) ;
    
}
