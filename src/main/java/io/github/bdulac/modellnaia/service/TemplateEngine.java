package io.github.bdulac.modellnaia.service;

import java.util.Map;

/**
 * Interface pour le moteur de templates.
 */
public interface TemplateEngine {
    String process(String template, Map<String, Object> data);
}
