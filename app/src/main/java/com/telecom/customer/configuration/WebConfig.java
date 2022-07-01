package com.telecom.customer.configuration;

import com.telecom.customer.repository.converter.PhoneNumberEntityToPhoneNumberConverter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@NoArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(final FormatterRegistry registry) {
        registry.addConverter(new PhoneNumberEntityToPhoneNumberConverter());
    }
}
