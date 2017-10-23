package com.xxx.coinman;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.EnableTransactionManagement;


import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.xxx.coinman.repository", repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
public class AuditConfig {
	@Bean
	public AuditorAwareImpl auditorAwareImpl() {
		return new AuditorAwareImpl();

	}

	public class AuditorAwareImpl implements AuditorAware<String> {

		@Override
		public String getCurrentAuditor() {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (null == authentication || !authentication.isAuthenticated()) {
				return null;
			}

			if (!(authentication.getPrincipal() instanceof UserDetails)) {
				return null;
			}


			
			return ((User) authentication.getPrincipal()).getUsername();
		}

	}	
}