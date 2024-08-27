package es.bit.api.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(
                "components",
                "cpus",
                "gpus",
                "motherboards",
                "powerSupplies",
                "ramMemories",
                "storages",
                "cpuCoolers",
                "cases",
                "caseFans",
                "cables",
                "cableColors",
                "cableTypes",
                "caseFanSizes",
                "caseSizes",
                "componentTypes",
                "cpuSeries",
                "cpuSockets",
                "gpuChipsetSeries",
                "lightings",
                "manufacturers",
                "motherboardChipsets",
                "motherboardFormFactors",
                "multiGpuTypes",
                "powerSupplyFormFactors");
    }
}
