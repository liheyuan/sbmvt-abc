package com.coder4.sbmvt.abc.client.configuration;

import com.coder4.sbmvt.abc.client.SbmvtAbcEasyClientBuilder;
import com.coder4.sbmvt.abc.client.SbmvtAbcEurekaClientBuilder;
import com.coder4.sbmvt.abc.thrift.SbmvtAbcThrift;
import com.coder4.sbmvt.abc.thrift.SbmvtAbcThrift.Client;
import com.coder4.sbmvt.thrift.client.ThriftClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author coder4
 */
@Configuration
public class SbmvtAbcClientConfiguration {

    @Bean(name = "sbmvtAbcThriftClient")
    @ConditionalOnMissingBean(name = "sbmvtAbcThriftClient")
    @ConditionalOnProperty(name = {"sbmvtAbcThriftServer.host", "sbmvtAbcThriftServer.port"})
    public ThriftClient<Client> easyClient(
            @Value("${sbmvtAbcThriftServer.host}") String host,
            @Value("${sbmvtAbcThriftServer.port}") int port
    ) {
        // TODO LOG
        return SbmvtAbcEasyClientBuilder.buildClient(host, port);
    }

    @Bean(name = "sbmvtAbcThriftClient")
    @ConditionalOnMissingBean(name = "sbmvtAbcThriftClient")
    public ThriftClient<SbmvtAbcThrift.Client> eurekaClient() {
        // TODO LOG
        return SbmvtAbcEurekaClientBuilder.buildClient("sbmvt-abc-thrift-server");
    }

}
