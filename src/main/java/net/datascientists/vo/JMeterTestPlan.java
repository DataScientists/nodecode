package net.datascientists.vo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.jorphan.collections.HashTree;

@XmlRootElement(name = "jmeterTestPlan")
public class JMeterTestPlan
{

    @XmlElement(name = "hashTree")
    private HashTree hashTree;

    public JMeterTestPlan()
    {
    }
    
    public JMeterTestPlan(HashTree hashTree)
    {
        super();
        this.hashTree = hashTree;
    }
    
    
}
