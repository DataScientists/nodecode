package net.datascientists.utilities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.jmeter.control.GenericController;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerBase;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jorphan.collections.HashTree;

public class JMeterFactory
{

    public static class Builder{
 
        private HashTree hashTree;
        private org.apache.jmeter.threads.ThreadGroup threadGroup;
        private HTTPSamplerBase httpSampler;
        private HeaderManager header; 
        private GenericController controller;
        
        public Builder addHeader(final HeaderManager header){
            this.header = header;
            return this;
        }
        
        public Builder addController(final GenericController controller){
            this.controller = controller;
            return this;
        }
        
        public Builder addThreadGroup(final org.apache.jmeter.threads.ThreadGroup threadGroup){
            this.threadGroup = threadGroup;
            return this;
        }
        
        public Builder addHttpSampler(final HTTPSamplerBase httpSampler){
            this.httpSampler = httpSampler;
            return this;
        }
        
        public String build(){
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            TestPlan testPlan = new TestPlan("Create JMeter Script From Java Code");
            hashTree = new HashTree();
            hashTree.add("testPlan", testPlan);
            hashTree.add("loopController", this.controller);
            hashTree.add("threadGroup", this.threadGroup);
            this.httpSampler.setHeaderManager(this.header);
            hashTree.add("httpSampler", this.httpSampler);
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
        
    }
    
}
