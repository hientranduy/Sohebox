package com.hientran.sohebox.configuration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionConfig;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizePolicy;

/**
 * Hazelcast Configuration
 *
 * @author hientran
 */
@Configuration
@EnableCaching
public class HazelcastConfiguration {

    @Bean
    public Config hazelCastConfig() {
        Config config = new Config();
        config.setInstanceName("hazelcast-instance");

        // Register configCache
        MapConfig configCache = new MapConfig();
        configCache.setName("configCache");
        configCache.setTimeToLiveSeconds(-1); // Never expired.
        configCache.setEvictionConfig(new EvictionConfig().setSize(300).setMaxSizePolicy(MaxSizePolicy.FREE_HEAP_SIZE)
                .setEvictionPolicy(EvictionPolicy.LFU));
        config.addMapConfig(configCache);

        // Register typeCache
        MapConfig typeCache = new MapConfig();
        typeCache.setName("typeCache");
        typeCache.setTimeToLiveSeconds(-1);
        configCache.setEvictionConfig(new EvictionConfig().setSize(300).setMaxSizePolicy(MaxSizePolicy.FREE_HEAP_SIZE)
                .setEvictionPolicy(EvictionPolicy.LFU));
        config.addMapConfig(typeCache);

        // Register englishTypeCache
        MapConfig englishTypeCache = new MapConfig();
        englishTypeCache.setName("englishTypeCache");
        englishTypeCache.setTimeToLiveSeconds(-1);
        configCache.setEvictionConfig(new EvictionConfig().setSize(300).setMaxSizePolicy(MaxSizePolicy.FREE_HEAP_SIZE)
                .setEvictionPolicy(EvictionPolicy.LFU));
        config.addMapConfig(englishTypeCache);

        // Register foodTypeCache
        MapConfig foodTypeCache = new MapConfig();
        foodTypeCache.setName("foodTypeCache");
        foodTypeCache.setTimeToLiveSeconds(-1);
        configCache.setEvictionConfig(new EvictionConfig().setSize(300).setMaxSizePolicy(MaxSizePolicy.FREE_HEAP_SIZE)
                .setEvictionPolicy(EvictionPolicy.LFU));
        config.addMapConfig(foodTypeCache);

        // Return
        return config;
    }
}