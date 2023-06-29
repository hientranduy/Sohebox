package com.hientran.sohebox.configuration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionConfig;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizePolicy;

@Configuration
@EnableCaching
public class HazelcastConfiguration {

	@Bean
	public Config hazelCastConfig() {
		Config config = new Config();
		config.setClusterName("hazelcast-SoheboxCluster");
		config.setInstanceName("hazelcast-SoheboxInstance");
		config.getNetworkConfig().setPortAutoIncrement(true);
		config.addMapConfig(createMapConfig("configCache"));
		config.addMapConfig(createMapConfig("typeCache"));
		config.addMapConfig(createMapConfig("englishTypeCache"));
		config.addMapConfig(createMapConfig("foodTypeCache"));
		config.addMapConfig(createMapConfig("mediaTypeCache"));
		return config;
	}

	private MapConfig createMapConfig(String cacheName) {
		MapConfig configCache = new MapConfig();
		configCache.setName(cacheName);
		configCache.setTimeToLiveSeconds(-1); // Never expired.
		configCache.setEvictionConfig(new EvictionConfig().setSize(300).setMaxSizePolicy(MaxSizePolicy.FREE_HEAP_SIZE)
				.setEvictionPolicy(EvictionPolicy.LFU));
		return configCache;
	}
}