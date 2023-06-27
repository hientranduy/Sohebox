package com.hientran.sohebox.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.hientran.sohebox.constants.enums.ConfigTblEnum;
import com.hientran.sohebox.entity.ConfigTbl;
import com.hientran.sohebox.sco.ConfigSCO;

/**
 * @author hientran
 */
@Component
@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
public class ConfigSpecs extends GenericSpecs {
	public Specification<ConfigTbl> buildSpecification(ConfigSCO sco) {
		// Declare result
		Specification<ConfigTbl> specification = Specification.where(null);

		// Add criteria
		if (sco != null) {
			if (sco.getSearchOr() != null && sco.getSearchOr() == true) {
				specification = specification.or(buildSearchText(ConfigTblEnum.configKey.name(), sco.getConfigKey()));
				specification = specification
						.or(buildSearchText(ConfigTblEnum.configValue.name(), sco.getConfigValue()));
				specification = specification
						.or(buildSearchText(ConfigTblEnum.description.name(), sco.getDescription()));
				specification = specification
						.or(buildSearchBoolean(ConfigTblEnum.deleteFlag.name(), sco.getDeleteFlag()));
			} else {
				specification = specification.and(buildSearchText(ConfigTblEnum.configKey.name(), sco.getConfigKey()));
				specification = specification
						.and(buildSearchText(ConfigTblEnum.configValue.name(), sco.getConfigValue()));
				specification = specification
						.and(buildSearchText(ConfigTblEnum.description.name(), sco.getDescription()));
				specification = specification
						.and(buildSearchBoolean(ConfigTblEnum.deleteFlag.name(), sco.getDeleteFlag()));
			}
		}

		// Return result
		return specification;
	}
}