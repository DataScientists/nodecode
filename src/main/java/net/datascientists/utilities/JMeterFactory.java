package net.datascientists.utilities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.jmeter.config.Argument;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.ConfigTestElement;
import org.apache.jmeter.control.GenericController;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.extractor.json.jsonpath.JSONPostProcessor;
import org.apache.jmeter.protocol.http.control.CacheManager;
import org.apache.jmeter.protocol.http.control.CookieManager;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerBase;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;

public class JMeterFactory
{
    
    private HashTree hashTree;
    private org.apache.jmeter.threads.ThreadGroup threadGroup;
    private List<HTTPSamplerBase> httpSampler;
    private HeaderManager header;
    private GenericController controller;
    private TestPlan testPlan;
    private List<HashTree> childHashTree;
    private JSONPostProcessor jsonPostProcesser;

    
    
    public JMeterFactory(
        HashTree hashTree, ThreadGroup threadGroup, List<HTTPSamplerBase> httpSampler, HeaderManager header, GenericController controller, TestPlan testPlan,
        List<HashTree> childHashTree, JSONPostProcessor jsonPostProcesser)
    {
        super();
        this.hashTree = hashTree;
        this.threadGroup = threadGroup;
        this.httpSampler = httpSampler;
        this.header = header;
        this.controller = controller;
        this.testPlan = testPlan;
        this.childHashTree = childHashTree;
        this.jsonPostProcesser = jsonPostProcesser;
    }


    public HashTree getHashTree()
    {
        return hashTree;
    }


    public org.apache.jmeter.threads.ThreadGroup getThreadGroup()
    {
        return threadGroup;
    }


    public List<HTTPSamplerBase> getHttpSampler()
    {
        return httpSampler;
    }


    public HeaderManager getHeader()
    {
        return header;
    }


    public GenericController getController()
    {
        return controller;
    }


    public TestPlan getTestPlan()
    {
        return testPlan;
    }


    public List<HashTree> getChildHashTree()
    {
        return childHashTree;
    }


    public JSONPostProcessor getJsonPostProcesser()
    {
        return jsonPostProcesser;
    }


    public static class Builder
    {

        private HashTree hashTree = new HashTree();
        private org.apache.jmeter.threads.ThreadGroup threadGroup;
        private List<HTTPSamplerBase> httpSampler = new ArrayList<>();
        private HeaderManager header;
        private LoopController controller;
        private TestPlan testPlan;
        private List<HashTree> childHashTree = new ArrayList<>();
        private JSONPostProcessor jsonPostProcesser;


        public Builder addTestPlan(final TestPlan testPlan)
        {
            this.testPlan = testPlan;
            return this;
        }


        public Builder addChildHashTree(final HashTree hashTree)
        {
            this.childHashTree.add(hashTree);
            return this;
        }


        public Builder addHeader(final HeaderManager header)
        {
            this.header = header;
            return this;
        }


        public Builder addController(final LoopController controller)
        {
            this.controller = controller;
            return this;
        }


        public Builder addThreadGroup(final org.apache.jmeter.threads.ThreadGroup threadGroup)
        {
            this.threadGroup = threadGroup;
            return this;
        }


        public Builder addHttpSampler(final HTTPSamplerBase httpSampler)
        {
            this.httpSampler.add(httpSampler);
            return this;
        }
        
        public Builder addJSONPostProcessor(final JSONPostProcessor jsonPostProcessor)
        {
            this.jsonPostProcesser = jsonPostProcessor;
            return this;
        }


        public JMeterFactory build()
        {
            String jmeterHome = "C:\\Users\\lenovo\\Downloads\\apache-jmeter-3.3\\apache-jmeter-3.3"; 
            JMeterUtils.setJMeterHome(jmeterHome); 
            JMeterUtils.loadJMeterProperties(JMeterUtils.getJMeterBinDir() + "\\jmeter.properties"); 
            JMeterUtils.initLocale();
            if (controller != null)
            {
                setDefaultLoopControllerProperty(this.controller);
                this.hashTree.add("loopController", this.controller);
            }
            if (testPlan != null)
            {
                setDefaultPropertyForTestPlan(testPlan);
                this.hashTree.add(testPlan);
            }
            if (threadGroup != null)
            {
                setDefaultPropertyForThreadGroup(threadGroup);
                this.hashTree.add(threadGroup);
            }
            if (jsonPostProcesser != null)
            {
                setDefaultPropertyForJSONPostProcessor(jsonPostProcesser);
                this.hashTree.add(jsonPostProcesser);
            }
            if (!this.httpSampler.isEmpty())
            {
                setDefaultHeaderProperty(this.header);
                for (HTTPSamplerBase sampler : this.httpSampler)
                {
                    setDefaultHTTPSamplerProperty(sampler);
                    sampler.setHeaderManager(this.header);
                    this.hashTree.add("httpSampler", sampler);
                }
            }
            return new JMeterFactory(this.hashTree,threadGroup,httpSampler,header,controller,testPlan,childHashTree,jsonPostProcesser);
        }


        private void setDefaultHTTPSamplerProperty(HTTPSamplerBase sampler)
        {
            sampler.setProperty(TestElement.GUI_CLASS,sampler.GUI_CLASS);
            sampler.setProperty(TestElement.TEST_CLASS,sampler.TEST_CLASS);
            sampler.setProperty(TestElement.ENABLED,sampler.ENABLED);
        }


        private void setDefaultPropertyForJSONPostProcessor(JSONPostProcessor jsonPostProcesser)
        {
            jsonPostProcesser.setProperty(TestElement.GUI_CLASS,jsonPostProcesser.GUI_CLASS);
            jsonPostProcesser.setProperty(TestElement.TEST_CLASS,jsonPostProcesser.TEST_CLASS);
            jsonPostProcesser.setProperty(TestElement.ENABLED,jsonPostProcesser.ENABLED);
            jsonPostProcesser.setRefNames("jsontoken");
            jsonPostProcesser.setJsonPathExpressions("$..*");
            jsonPostProcesser.setMatchNumbers("1");
        }


        private void setDefaultPropertyForThreadGroup(ThreadGroup threadGroup)
        {
            threadGroup.setProperty(TestElement.GUI_CLASS, threadGroup.GUI_CLASS);
            threadGroup.setProperty(TestElement.TEST_CLASS,threadGroup.TEST_CLASS);
            threadGroup.setProperty(TestElement.ENABLED,threadGroup.ENABLED);
            threadGroup.setNumThreads(1);
            LoopController controller = new LoopController();
            setDefaultLoopControllerProperty(controller);
            threadGroup.setSamplerController(controller);
            threadGroup.setRampUp(1);
            threadGroup.setStartTime(1363247040000L);
            threadGroup.setEndTime(1363247040000L);
            threadGroup.setScheduler(false);
            threadGroup.setDuration(0);
            threadGroup.setDelay(0);
        }
        
        


        private void setDefaultLoopControllerProperty(LoopController controller)
        {
            controller.setProperty(TestElement.GUI_CLASS, controller.GUI_CLASS);
            controller.setProperty(TestElement.TEST_CLASS,controller.TEST_CLASS);
            controller.setProperty(TestElement.ENABLED,controller.ENABLED);
            controller.setContinueForever(false);
            controller.setLoops(1);
        }


        private void setDefaultHeaderProperty(HeaderManager header)
        {
            header.setProperty(TestElement.GUI_CLASS, header.GUI_CLASS);
            header.setProperty(TestElement.TEST_CLASS, header.TEST_CLASS);
        }


        private void setDefaultPropertyForTestPlan(TestPlan testPlan)
        {
            testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName()); 
            testPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());
        }


        public String createTree(HashTree hashTree)
        {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            try
            {
                SaveService.saveTree(hashTree, os);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return os.toString();
        }


        public void setDefaultElements(HashTree hashTree)
        {
            addDefaultArguments(hashTree);
            addDefaultConfigTestElement(hashTree);
//            addDefaultDnsCache(hashTree);
            addDefaultCookieManager(hashTree);
            addCacheManager(hashTree);
        }


//        private void addDefaultDnsCache(HashTree hashTree)
//        {
//               //@TODO no DNSCacheManager in this API
//        }


        private void addCacheManager(HashTree hashTree)
        {
            CacheManager cacheManager = new CacheManager();
            cacheManager.setProperty(TestElement.GUI_CLASS,cacheManager.GUI_CLASS);
            cacheManager.setProperty(TestElement.TEST_CLASS,cacheManager.TEST_CLASS);
            cacheManager.setProperty(TestElement.ENABLED,cacheManager.ENABLED);
            hashTree.add(cacheManager);
        }


        private void addDefaultCookieManager(HashTree hashTree)
        {
            CookieManager cookieManager = new CookieManager();
            cookieManager.setProperty(TestElement.GUI_CLASS, cookieManager.GUI_CLASS);
            cookieManager.setProperty(TestElement.TEST_CLASS,cookieManager.TEST_CLASS);
            cookieManager.setProperty(TestElement.ENABLED,cookieManager.ENABLED);
            cookieManager.setClearEachIteration(true);
            hashTree.add(cookieManager);
        }


        private void addDefaultConfigTestElement(HashTree hashTree)
        {
            ConfigTestElement configTestElement = new ConfigTestElement();
            configTestElement.setProperty(TestElement.GUI_CLASS, configTestElement.GUI_CLASS);
            configTestElement.setProperty(TestElement.TEST_CLASS,configTestElement.TEST_CLASS);
            configTestElement.setProperty("HTTPSampler.image_parser", true);
            configTestElement.setProperty("HTTPSampler.concurrentDwn", true);
            configTestElement.setProperty("HTTPSampler.concurrentPool", "6");
            hashTree.add(configTestElement);
        }


        private void addDefaultArguments(HashTree hashTree)
        {
            Arguments arguments = new Arguments();
            arguments.setProperty(TestElement.GUI_CLASS,Argument.GUI_CLASS);
            arguments.setProperty(TestElement.TEST_CLASS,Argument.TEST_CLASS);
            arguments.setProperty(TestElement.ENABLED,Argument.ENABLED);
            Argument argument1 = new Argument();
            argument1.setProperty("BASE_URL_1", "localhost");
            argument1.setMetaData("=");
            arguments.addArgument(argument1);
            hashTree.add(arguments);
        }


    }

}
