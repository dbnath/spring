/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.toolsrepo.zuul.simple;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link ZuulProxyApplication}.
 * 
 * @author Debasish Nath
 */
@RunWith(SpringRunner.class)
public class ZuulProxyApplicationTests {

	@Rule
	public OutputCapture outputCapture = new OutputCapture();

	private String profiles;

	ConfigurableApplicationContext context;

	@Before
	public void init() {
		this.profiles = System.getProperty("spring.profiles.active");
	}

	@After
	public void after() {
		SpringApplication.exit(context);
	}

	@Test
	public void testDefaultSettings() throws Exception {
		SpringApplication springApplication = new SpringApplicationBuilder()
				.sources(ZuulProxyApplication.class)
				.build();
		context = springApplication.run();
		String output = this.outputCapture.toString();
		assertTrue("Wrong output: " + output, output.contains("Hello Deb"));
	}



}
