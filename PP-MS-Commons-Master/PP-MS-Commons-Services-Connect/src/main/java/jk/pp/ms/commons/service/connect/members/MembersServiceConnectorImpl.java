package jk.pp.ms.commons.service.connect.members;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import jk.pp.ms.commons.service.connect.common.BaseServiceConnector;
import jk.pp.ms.commons.service.connect.members.dto.MemberConnectReq;

@ConditionalOnProperty(name = "pp.ms.serviceconnect.members.enabled", havingValue = "true")
@Profile("pp.ms.serviceconnect.members.enabled")
@Service("MembersServiceConnectorImpl")
public class MembersServiceConnectorImpl extends BaseServiceConnector<MemberConnectReq>
		implements MembersServiceConnector {

	@Value("${pp.ms.serviceconnect.members.apiuri:undefined}")
	private String commandUrl;

	@PostConstruct
	public void setCommandUrl() {
		super.setCommandUrl(this.commandUrl);
	}

}
