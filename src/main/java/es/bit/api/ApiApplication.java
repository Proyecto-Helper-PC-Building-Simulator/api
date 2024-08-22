package es.bit.api;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableAutoConfiguration
@EnableCaching
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI() {
		String description = """
				# PC Building Simulator API
				The **PC Building Simulator API** is a tool designed to be used by an [app](https://github.com/Proyecto-Helper-PC-Building-Simulator/app_javafx) that
				allows players to **keep track of the requests** from the customers in the game,
				along with the parts needed.
				                                                                                          
				The component data was obtained from the [PC Building Simulator 1](https://www.pcbuildingsim.com/pc-building-simulator) game.
				                                                                                          
				## Example Usage
				                                                                                          
				Here is an example of how you might use the API:
				                                                                                          
				- `192.168.1.104:9090/pc/motherboards?priceMin=100&multiGpuType=SLI&frequencyMin=2000&lighting=Red&page=0&level=18`
				                                                                                          
				Where the each part represents:
				                                                                                          
				- 192.168.1.104:9090 🡢 **Host** (IP address and port)
				- pc 🡢 **Context path** (API endpoint root)
				- motherboards 🡢 **Resource** (Specific resource or collection within the API)
				- priceMin 🡢 **Query Key** (Filter or parameter key in the query string)
				- 100 🡢 **Query Value** (Value corresponding to the query key)
				- page=0 🡢 **Pagination** (Parameter for pagination control)
				- level=18 🡢 **Level Filter** (Specific parameter indicating the level where is unlocked)
				- **Separators** (Characters like `/`, `?`, `=` and `&`)
				                                                                                          
				## Common Filters
				                                                                                          
				The following filters are common to all components and are represented in the example above:
				                                                                                          
				- **name**: The name of the component.
				- **manufacturer**: The brand or manufacturer of the component.
				- **lighting**: Whether the component has lighting (e.g., RGB).
				- **priceMin**: The minimum price for the component.
				- **priceMax**: The maximum price for the component.
				- **level**: The level at which the component is unlocked in the game.
				                                                                                          
				## Component-Specific Filters
				                                                                                          
				In addition to the common filters, each type of component has specific filters that are relevant only to that component. Below is a breakdown of these filters by component type:
				                                                                                          
				### CPU
				                                                                                          
				- **serie**: The series of the CPU (e.g., Intel Core i9, AMD Ryzen 7).
				- **socket**: The CPU socket type (e.g., LGA1151, AM4).
				- **frequencyMin**: The minimum clock frequency (in MHz).
				- **frequencyMax**: The maximum clock frequency (in MHz).
				- **coresMin**: The minimum number of cores.
				- **coresMax**: The maximum number of cores.
				- **wattageMin**: The minimum power consumption (in watts).
				- **wattageMax**: The maximum power consumption (in watts).
				                                                                                          
				### CPU Cooler
				                                                                                          
				- **type**: The type of cooler (e.g., Air, Liquid).
				- **socket**: The supported CPU socket types.
				- **sizeMin**: The minimum size of the cooler (in mm).
				- **sizeMax**: The maximum size of the cooler (in mm).
				- **airFlowMin**: The minimum airflow (in CFM).
				- **airFlowMax**: The maximum airflow (in CFM).
				- **heightMin**: The minimum height of the cooler (in mm).
				- **heightMax**: The maximum height of the cooler (in mm).
				                                                                                          
				### Motherboard
				                                                                                          
				- **frequencyMin**: The minimum supported memory frequency (in MHz).
				- **frequencyMax**: The maximum supported memory frequency (in MHz).
				- **formFactor**: The form factor of the motherboard (e.g., ATX, Micro-ATX).
				- **chipset**: The chipset of the motherboard (e.g., Z370, B450).
				- **socket**: The CPU socket type supported by the motherboard.
				- **multiGpuType**: The type of multi-GPU support (e.g., SLI, CrossFire).
				                                                                                          
				### RAM Memory
				                                                                                          
				- **sizeMin**: The minimum size of the RAM module (in GB).
				- **sizeMax**: The maximum size of the RAM module (in GB).
				- **frequencyMin**: The minimum memory frequency (in MHz).
				- **frequencyMax**: The maximum memory frequency (in MHz).
				                                                                                          
				### GPU
				                                                                                          
				- **chipsetBrand**: The brand of the GPU chipset (e.g., NVIDIA, AMD).
				- **vramMin**: The minimum video memory (in GB).
				- **vramMax**: The maximum video memory (in GB).
				- **memoryFrequencyMin**: The minimum memory frequency (in MHz).
				- **memoryFrequencyMax**: The maximum memory frequency (in MHz).
				- **coreFrequencyMin**: The minimum core clock speed (in MHz).
				- **coreFrequencyMax**: The maximum core clock speed (in MHz).
				- **lengthMin**: The minimum length of the GPU (in mm).
				- **lengthMax**: The maximum length of the GPU (in mm).
				- **wattageMin**: The minimum power consumption (in watts).
				- **wattageMax**: The maximum power consumption (in watts).
				- **chipsetSerie**: The series of the GPU chipset (e.g., RTX 3080, RX 6800).
				- **multiGpuType**: The type of multi-GPU support.
				                                                                                          
				### Storage
				                                                                                          
				- **sizeMin**: The minimum storage size (in GB).
				- **sizeMax**: The maximum storage size (in GB).
				- **transferSpeedMin**: The minimum data transfer speed (in MB/s).
				- **transferSpeedMax**: The maximum data transfer speed (in MB/s).
				- **type**: The type of storage (e.g., HDD, SSD, M.2).
				                                                                                          
				### Power Supply
				                                                                                          
				- **wattageMin**: The minimum power output (in watts).
				- **wattageMax**: The maximum power output (in watts).
				- **lengthMin**: The minimum length of the PSU (in mm).
				- **lengthMax**: The maximum length of the PSU (in mm).
				- **type**: The type of PSU (e.g., Modular, Non-modular).
				- **formFactor**: The form factor of the PSU (e.g., ATX, SFX).
				                                                                                          
				### Cable
				                                                                                          
				- **type**: The type of cable (e.g., Ribbon, Plastic).
				- **color**: The color of the cable.
				                                                                                          
				### Case
				                                                                                          
				- **psuLengthMin**: The minimum PSU length supported (in mm).
				- **psuLengthMax**: The maximum PSU length supported (in mm).
				- **gpuLengthMin**: The minimum GPU length supported (in mm).
				- **gpuLengthMax**: The maximum GPU length supported (in mm).
				- **cpuFanHeightMin**: The minimum CPU cooler height supported (in mm).
				- **cpuFanHeightMax**: The maximum CPU cooler height supported (in mm).
				- **caseSize**: The size of the case (e.g., Mid Tower, Full Tower).
				- **fanSize**: The size of the case fans supported (in mm).
				- **motherboardFormFactor**: The motherboard form factors supported by the case.
				- **psuFormFactor**: The PSU form factors supported by the case.
				                                                                                          
				### Case Fan
				                                                                                          
				- **airFlowMin**: The minimum airflow provided by the fan (in CFM).
				- **airFlowMax**: The maximum airflow provided by the fan (in CFM).
				- **sizeMin**: The minimum size of the fan (in mm).
				- **sizeMax**: The maximum size of the fan (in mm).
				                                                                                          
				""";
		return new OpenAPI().info(new Info()
				.title("PC Simulator API")
				.version("1.1")
				.description(description)
		);
	}
}