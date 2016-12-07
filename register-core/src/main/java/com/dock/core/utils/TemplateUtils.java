package com.dock.core.utils;

import com.google.common.collect.Maps;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;
import java.util.Map;

public class TemplateUtils {
	private static final Logger logger = LoggerFactory.getLogger(TemplateUtils.class);

	private static final String ATTRIBUTE_DATE = "date";
	private static final String TEMPLATE_NAME = "my_template";

	public static void main(String[] args) {
		String template = "验证码${code}，您正在登录，若非本人操作，请勿泄露。";
		Map<String, Object> data = Maps.newHashMap();
		data.put("code", "111");
		System.out.println(formatFreemarker(template, data));
	}

	public static String formatString(String template, Map<String, Object> data) {
		logger.debug("###### template is :{}, with data :{}", template, data);

		return formatFreemarker(template, data);
	}

	public static String formatFreemarker(String template, Map<String, Object> data) {
		String message = "";
		try {
			data.put(ATTRIBUTE_DATE, DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));

			Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
			StringTemplateLoader stringLoader = new StringTemplateLoader();
			stringLoader.putTemplate(TEMPLATE_NAME, template);
			cfg.setTemplateLoader(stringLoader);
			Template tpl = cfg.getTemplate(TEMPLATE_NAME);

			StringWriter writer = null;
			try {
				writer = new StringWriter();
				tpl.process(data, writer);

				message = writer.toString();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			} finally {
				if (writer != null) {
					writer.flush();
					writer.close();
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return message;
	}

	/**
	 * This method is very slowly, freemarker is 15 times faster than velocity.
	 * @param template
	 * @param data
	 * @return
	 */
	/*
	public static String formatVelocity(String template, Map<String, Object> data) {
		String message = "";
		try {
			VelocityEngine engine = new VelocityEngine();
			engine.init();

			VelocityContext context = new VelocityContext(data);
			context.put(ATTRIBUTE_DATE, DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));

			StringWriter writer = null;
			try {
				writer = new StringWriter();
				engine.evaluate(context, writer, TEMPLATE_NAME, template);

				message = writer.toString();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			} finally {
				if (writer != null) {
					writer.flush();
					writer.close();
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return message;
	}*/
}
