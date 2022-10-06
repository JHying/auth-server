package tw.hyin.demo.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import tw.hyin.java.utils.JsonUtil;
import tw.hyin.java.utils.Log;


/**
 * JSON 轉換全域設定
 * @author H-yin
 *
 */
@Configuration
public class JacksonConfig {

	// JSON 序列化之全域配置
	@Bean
	@Primary
	@ConditionalOnMissingBean(ObjectMapper.class)
	public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
		ObjectMapper objectMapper = builder.createXmlMapper(false).build();
		// 忽略不存在的屬性
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// 允許出現單引號
		objectMapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
		Log.info("Initialized JacksonConfig complete.");
		//修改常用工具的 mapper 設定
		JsonUtil.setMapper(objectMapper);
		return objectMapper;
	}
}
