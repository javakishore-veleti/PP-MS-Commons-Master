package jk.pp.ms.commons.service.connect.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.client.RestTemplate;

import jk.pp.engg.foundations.common.domain.core.connect.ServiceConnectReq;
import jk.pp.engg.foundations.common.domain.core.connect.ServiceConnectResult;
import jk.pp.engg.foundations.common.service.core.connect.ServiceConnector;
import jk.pp.ms.commons.service.connect.config.Resilience4JCircuitBreakerConfig;

public abstract class BaseServiceConnector<T extends ServiceConnectReq>
		implements ServiceConnector<T, ServiceConnectResult> {

	@Autowired(required = false)
	@Qualifier(Resilience4JCircuitBreakerConfig.REST_TEMPLATE_BEAN_ID)
	private RestTemplate restTemplate;

	@SuppressWarnings("rawtypes")
	@Autowired(required = false)
	private CircuitBreakerFactory cbFactory;

	protected String commandUrl;

	public void setCommandUrl(String commandUrl) {
		this.commandUrl = commandUrl;
	}

	@Override
	public ServiceConnectResult command(T request) throws Exception {

		if (cbFactory != null) {

			return cbFactory.create(this.commandUrl).run(
					() -> restTemplate.getForObject(this.commandUrl, ServiceConnectResult.class),
					throwable -> new ServiceConnectResult());
		}

		if (this.restTemplate != null) {
			this.restTemplate.getForObject(this.commandUrl, request.getClass());
		}

		return null;
	}
}
