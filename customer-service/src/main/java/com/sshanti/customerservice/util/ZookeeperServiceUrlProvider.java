package com.sshanti.customerservice.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import javax.management.ServiceNotFoundException;
import java.util.List;

@Component
public class ZookeeperServiceUrlProvider {
    public static final String ACCOUNT_SERVICE = "account-service";
    public static final String CUSTOMER_SERVICE = "customer-service";

    @Autowired
    private DiscoveryClient discoveryClient;

    public String serviceUrl(String serviceName) throws ServiceNotFoundException {
        List<ServiceInstance> list = discoveryClient.getInstances(serviceName);
        if (list != null && !list.isEmpty()) {
            return list.get(0).getUri().toString();
        }
        throw new ServiceNotFoundException(String.format("Service [%s] not found!", serviceName));

    }
}
