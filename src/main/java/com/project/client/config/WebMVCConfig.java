package com.project.client.config;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.project.client.interceptor.MenuInterceptor;

@Configuration
//@EnableWebMvc
// @ComponentScan({ "com.client" })
/**
 * Java configuration file that is used for Spring MVC and Freemarker
 * configurations
 */
public class WebMVCConfig implements WebMvcConfigurer {

//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/assets/**").addResourceLocations("/assets/");
//		registry.addResourceHandler("/pages/**").addResourceLocations("/pages/");
//	}

//	@Bean
//	public FreeMarkerViewResolver freemarkerViewResolver() {
//		FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
//		resolver.setCache(false);
//		resolver.setPrefix("/WEB-INF/views/ftl/");
//		resolver.setSuffix(".ftl");
//		return resolver;
//	}


//	@Resource
//	@Qualifier("accessTokenRequest")
//	private AccessTokenRequest accessTokenRequest;
	
//	@Bean
//	public FreeMarkerConfigurer freemarkerConfig() {
//		FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
//		freeMarkerConfigurer.setTemplateLoaderPath("/WEB-INF/views/ftl/");
//		return freeMarkerConfigurer;
//	}

	@Bean
	@Description("Spring Message Resolver")
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setBasename("lang/messages");
		return messageSource;
	}

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(new Locale("en"));
		return localeResolver;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		return localeChangeInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
		registry.addInterceptor(new MenuInterceptor());
	}

	// @Override
	// public void addResourceHandlers(ResourceHandlerRegistry registry) {
	// registry.addResourceHandler("/resources/**", "/css/**")
	// .addResourceLocations("/WEB-INF/resources/", "/WEB-INF/css/");
	// }

	// @Override
	// @Description("Custom Conversion Service")
	// public void addFormatters(FormatterRegistry registry) {
	// registry.addFormatter(new NameFormatter());
	// }
}
