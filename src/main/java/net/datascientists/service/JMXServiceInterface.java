package net.datascientists.service;

import java.util.List;

import net.datascientists.service.base.BaseService;
import net.datascientists.vo.JMXLogVO;

public interface JMXServiceInterface extends BaseService<JMXLogVO>
{

    String createJMXFile(List<JMXLogVO> list);

}
