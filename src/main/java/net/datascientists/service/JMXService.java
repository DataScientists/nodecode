package net.datascientists.service;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.extractor.json.jsonpath.JSONPostProcessor;
import org.apache.jmeter.protocol.http.control.Header;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.testelement.TestPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.datascientists.dao.JMXDao;
import net.datascientists.mapper.JMXLogMapper;
import net.datascientists.utilities.JMeterFactory;
import net.datascientists.utilities.PropUtil;
import net.datascientists.vo.HeaderVO;
import net.datascientists.vo.JMXLogVO;

@Service("JMXService")
@Transactional
public class JMXService implements JMXServiceInterface
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


    @Override
    public String createJMXFile(List<JMXLogVO> list)
    {
        if(!list.isEmpty()){
            Collections.sort(list);
        }
        JMeterFactory.Builder builder = new JMeterFactory.Builder();
        TestPlan testplan = new TestPlan();
        testplan.setName("TestPlan");
        testplan.setComment("Test Plan Created in node code.");
        builder.addTestPlan(testplan);
        JMeterFactory factory = builder.build();
        for (JMXLogVO jmxLogVO : list)
        {
            JMeterFactory.Builder childbuilder = new JMeterFactory.Builder();
            HTTPSamplerProxy httpSampler = createHTTPSampler(jmxLogVO);
            LoopController loopController = new LoopController();
            loopController.setLoops(1);
            loopController.addTestElement(httpSampler);
            loopController.setFirst(true);
            loopController.initialize();

            org.apache.jmeter.threads.ThreadGroup threadGroup = new org.apache.jmeter.threads.ThreadGroup();
            threadGroup.setNumThreads(1);
            threadGroup.setRampUp(1);
            threadGroup.setSamplerController(loopController);

            if (jmxLogVO.getFunction() != null && !(jmxLogVO.getFunction().contains("login")))
            {
                JSONPostProcessor jsonPostProcessor = new JSONPostProcessor();
                jsonPostProcessor.setJsonPathExpressions("$..*");
                jsonPostProcessor.setRefNames("jsontoken");
                jsonPostProcessor.setMatchNumbers("1");
                childbuilder.addJSONPostProcessor(jsonPostProcessor);
            }

            childbuilder.addHeader(getHeaderManager(jmxLogVO));
            childbuilder.addController(loopController);
            childbuilder.addHttpSampler(httpSampler);
            childbuilder.addThreadGroup(threadGroup);

            factory.getHashTree().add(childbuilder.build().getHashTree());
        }
        String result = builder.createTree(factory.getHashTree());
        String filePath = "";
        try
        {
            filePath = writeToFile(result);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return filePath;
    }


    private String writeToFile(String hashTree) throws IOException
    {
        String path = PropUtil.getInstance().getProperty("jmx.path") + "test.jmx";
        FileOutputStream fos = new FileOutputStream(path);
        DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(fos));
        outStream.writeUTF(hashTree);
        outStream.close();
        return path;
    }


    private HTTPSamplerProxy createHTTPSampler(JMXLogVO vo)
    {
        HTTPSamplerProxy httpSampler = new HTTPSamplerProxy();
        httpSampler.setDomain("localhost");
        httpSampler.setPort(8080);
        httpSampler.setPath(vo.getFunction());
        if (vo.getGetParameters() != null)
        {
            httpSampler.setMethod("GET");
        }
        else
        {
            httpSampler.setMethod("POST");
        }

        return httpSampler;
    }


    private HeaderManager getHeaderManager(JMXLogVO vo)
    {
        String header = vo.getHeader();
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<HeaderVO>>()
        {}.getType();
        List<HeaderVO> headers = gson.fromJson(header, listType);
        HeaderManager headerManager = new HeaderManager();
        if (vo.getFunction() != null && !(vo.getFunction().contains("login")))
        {
            Header headerToken = new Header();
            headerToken.setProperty("Accept", "application/json, text/plain, */*");
            headerToken.setProperty("X-Auth-Token", "${jsontoken}");
            headerManager.add(headerToken);
        }
        for (HeaderVO headerVO : headers)
        {
            Header headerInfo = new Header();
            headerInfo.setName(headerVO.getHeaderName());
            headerInfo.setValue(headerVO.getValue());
            headerManager.add(headerInfo);
        }
        return headerManager;
    }

}
