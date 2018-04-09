package net.datascientists.service;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.gui.ArgumentsPanel;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.protocol.http.control.Header;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.protocol.http.control.gui.HttpTestSampleGui;
import org.apache.jmeter.protocol.http.gui.HeaderPanel;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jmeter.visualizers.ViewResultsFullVisualizer;
import org.apache.jorphan.collections.HashTree;
import org.apache.jorphan.collections.ListedHashTree;
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
        if (!list.isEmpty())
        {
            Collections.sort(list);
        }
        String jmeterHome = "C:\\Users\\lenovo\\Downloads\\apache-jmeter-3.3\\apache-jmeter-3.3";
        JMeterUtils.setJMeterHome(jmeterHome);
        JMeterUtils.loadJMeterProperties(JMeterUtils.getJMeterBinDir() + "\\jmeter.properties");
        JMeterUtils.initLocale();

        HashTree testPlanTree = new ListedHashTree();
        TestPlan testPlan = new TestPlan();
        testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
        testPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());
        testPlan.setProperty(TestElement.ENABLED,true);
        testPlan.setName("Default Test Plan for NodeCode");
        testPlan.setUserDefinedVariables((Arguments) new ArgumentsPanel().createTestElement());
        testPlanTree.add(testPlan, JMeterFactory.addDefaultHeaderManager());
        testPlanTree.add(testPlan, JMeterFactory.addDefaultArguments());
        testPlanTree.add(testPlan, JMeterFactory.addDefaultConfigTestElement());
        testPlanTree.add(testPlan, JMeterFactory.addDefaultCookieManager());
        testPlanTree.add(testPlan, JMeterFactory.addCacheManager());
        ThreadGroup defaultPropertyForThreadGroup = JMeterFactory.addDefaultPropertyForThreadGroup();
        HashTree threadGroupTree = testPlanTree.add(testPlan, defaultPropertyForThreadGroup);
        for (JMXLogVO jmxLogVO : list)
        {
            HTTPSamplerProxy httpSampler = createHTTPSampler(jmxLogVO);
            LoopController loopController = new LoopController();
            loopController.setLoops(1);
            loopController.addTestElement(httpSampler);
            loopController.setFirst(true);
            loopController.initialize();
            //            threadGroupTree.add(httpSampler);
            threadGroupTree.add(httpSampler, getHeaderManager(jmxLogVO));
            if (jmxLogVO.getFunction() != null && jmxLogVO.getFunction().contains("login"))
            {
                threadGroupTree.add(httpSampler, JMeterFactory.addDefaultPropertyForJSONPostProcessor());
            }
            else
            {
                threadGroupTree.add(httpSampler, JMeterFactory.addDefaultRegExtractor());
            }
            threadGroupTree.add(httpSampler, JMeterFactory.addDefaultResponseAssertion());
        }
        Summariser summer = null;
        String summariserName = JMeterUtils.getPropDefault("summariser.name", "summary");
        if (summariserName.length() > 0) {
            summer = new Summariser(summariserName);
        }
        String slash = System.getProperty("file.separator");
        String logFile = jmeterHome + slash + "example.jtl";
        ResultCollector logger = new ResultCollector(summer);
        logger.setProperty(TestElement.GUI_CLASS,ViewResultsFullVisualizer.class.getName());
        logger.setProperty(TestElement.TEST_CLASS,ResultCollector.class.getName());
        logger.setProperty(TestElement.ENABLED,true);
        logger.setName("View Results Tree");
        logger.setFilename(logFile);
        testPlanTree.add(testPlanTree.getArray()[0], logger);
        String result = JMeterFactory.createTree(testPlanTree);
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
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        bos.write(hashTree.getBytes("UTF-8"));
//        DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(fos));
//        outStream.writeUTF(hashTree);
        bos.close();
        return path;
    }


    private HTTPSamplerProxy createHTTPSampler(JMXLogVO vo)
    {
        HTTPSamplerProxy httpSampler = new HTTPSamplerProxy();
        httpSampler.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());
        httpSampler.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
        httpSampler.setName(vo.getFunction());
        httpSampler.setProperty(TestElement.ENABLED, true);
        httpSampler.setDomain("localhost");
        httpSampler.setPort(8080);
        httpSampler.setPath("nodecode"+vo.getFunction());
//      @TODO  httpSampler.addArgument(name, value);
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
        headerManager.setProperty(TestElement.GUI_CLASS, HeaderPanel.class.getName());
        headerManager.setProperty(TestElement.TEST_CLASS, HeaderManager.class.getName());
        headerManager.setProperty(TestElement.ENABLED, true);
        headerManager.setName("HTTP Header manager");
        if (vo.getFunction() != null && !(vo.getFunction().contains("login")))
        {
            Header headerAccept = new Header();
            headerAccept.setName("Accept");
            headerAccept.setValue("application/json, text/plain, */*");
            Header headerToken = new Header();
            headerToken.setName("X-Auth-Token");
            headerToken.setValue("${jsontoken}");
            headerManager.add(headerAccept);
            headerManager.add(headerToken);
        }
        for (HeaderVO headerVO : headers)
        {
            if(headerVO.getHeaderName().contains("x-auth-token") || headerVO.getHeaderName().contains("cookie")){
                continue;
            }
            Header headerInfo = new Header();
            headerInfo.setName(headerVO.getHeaderName());
            headerInfo.setValue(headerVO.getValue());
            headerManager.add(headerInfo);
        }
        return headerManager;
    }

}
