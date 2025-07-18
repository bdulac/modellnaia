package io.github.bdulac.modellnaia.service;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

/**
 * Implémentation simple du moteur de templates utilisant Mustache.
 */
@Component
public class MustacheTemplateEngine implements TemplateEngine {
    
    private final MustacheFactory mustacheFactory;
    
    public MustacheTemplateEngine() {
        this.mustacheFactory = new DefaultMustacheFactory();
    }
    
    @Override
    public String process(String template, Map<String, Object> data) {
        try {
            Mustache mustache = mustacheFactory.compile(new StringReader(template), "template");
            StringWriter writer = new StringWriter();
            mustache.execute(writer, data);
            return writer.toString();
        } catch (Exception e) {
            throw new TemplateProcessingException("Erreur lors du traitement du template", e);
        }
    } 
    public class CodeGenerationNotFoundException extends RuntimeException {
        private static final long serialVersionUID = 8938994524885734893L;

		public CodeGenerationNotFoundException(Long id) {
            super("Génération de code non trouvée avec l'ID: " + id);
        }
    }

    public class UnsupportedLanguageException extends RuntimeException {
        private static final long serialVersionUID = 6054277945189262962L;

		public UnsupportedLanguageException(String message) {
            super(message);
        }
    }
    public class TemplateProcessingException extends RuntimeException {
        private static final long serialVersionUID = -3134906349372384989L;

		public TemplateProcessingException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
