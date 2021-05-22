package jk.pp.ms.commons.configs;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "jk.pp.engg.foundations.common" })
public class MSCommonGlobalConfig {

	public static final String[] COMMON_PROFILES = { "ms-common", "ms-db" };

	public static String[] buildAMSStartupProfiles(MSType msType) {

		String msProfiles[] = null;
		switch (msType) {
		case ENTITLEMENTS:
			msProfiles = new String[] { "entitlements" };
			break;
		case ECLAIMS:
			msProfiles = new String[] { "eclaims" };
			break;
		case PROVIDERS:
			msProfiles = new String[] { "providers" };
			break;
		case MEDPOLICY:
			msProfiles = new String[] { "medpolicy" };
			break;
		default:
			break;
		}

		return ArrayUtils.addAll(COMMON_PROFILES, msProfiles);
	}
}
