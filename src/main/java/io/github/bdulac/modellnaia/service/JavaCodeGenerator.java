package io.github.bdulac.modellnaia.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import io.github.bdulac.modellnaia.entity.Attribute;
import io.github.bdulac.modellnaia.entity.Classifier;
import io.github.bdulac.modellnaia.entity.Method;
import io.github.bdulac.modellnaia.entity.Parameter;
import io.github.bdulac.modellnaia.enums.ClassifierType;
import io.github.bdulac.modellnaia.enums.CodeGenerationOption;
import io.github.bdulac.modellnaia.enums.Language;
import io.github.bdulac.modellnaia.enums.Visibility;
import lombok.extern.slf4j.Slf4j;

/**
 * Générateur de code Java.
 */
@Component
@Slf4j
public class JavaCodeGenerator implements CodeGenerator {

	private static final String CLASS_TEMPLATE = """
			package {{package}};

			{{#imports}}
			import {{.}};
			{{/imports}}

			{{#comments}}
			/**
			 * {{description}}
			 */
			{{/comments}}
			{{#annotations}}
			{{.}}
			{{/annotations}}
			public {{#abstract}}abstract {{/abstract}}class {{className}} {{#superclass}}extends {{.}} {{/superclass}}{{#interfaces}}implements {{#first}}{{.}}{{/first}}{{^first}}, {{.}}{{/first}}{{/interfaces}} {

			{{#attributes}}
			    {{#comments}}
			    /**
			     * {{description}}
			     */
			    {{/comments}}
			    {{visibility}} {{#static}}static {{/static}}{{#final}}final {{/final}}{{type}} {{name}}{{#defaultValue}} = {{.}}{{/defaultValue}};
			{{/attributes}}

			{{#methods}}
			    {{#comments}}
			    /**
			     * {{description}}
			     {{#parameters}}
			     * @param {{name}} {{description}}
			     {{/parameters}}
			     {{#returnType}}
			     * @return {{description}}
			     {{/returnType}}
			     */
			    {{/comments}}
			    {{#annotations}}
			    {{.}}
			    {{/annotations}}
			    {{visibility}} {{#static}}static {{/static}}{{#abstract}}abstract {{/abstract}}{{#final}}final {{/final}}{{#returnType}}{{.}}{{/returnType}}{{^returnType}}void{{/returnType}} {{name}}({{#parameters}}{{type}} {{name}}{{#defaultValue}} = {{.}}{{/defaultValue}}{{^last}}, {{/last}}{{/parameters}}){{#abstract}};{{/abstract}}{{^abstract}} {
			        {{#body}}
			        {{.}}
			        {{/body}}
			        {{^body}}
			        // TODO: Implémenter cette méthode
			        {{#returnType}}
			        return null;
			        {{/returnType}}
			        {{/body}}
			    }{{/abstract}}
			{{/methods}}
			}
			""";

	private static final String INTERFACE_TEMPLATE = """
			package {{package}};

			{{#imports}}
			import {{.}};
			{{/imports}}

			{{#comments}}
			/**
			 * {{description}}
			 */
			{{/comments}}
			{{#annotations}}
			{{.}}
			{{/annotations}}
			public interface {{interfaceName}} {{#superInterfaces}}extends {{#first}}{{.}}{{/first}}{{^first}}, {{.}}{{/first}}{{/superInterfaces}} {

			{{#methods}}
			    {{#comments}}
			    /**
			     * {{description}}
			     {{#parameters}}
			     * @param {{name}} {{description}}
			     {{/parameters}}
			     {{#returnType}}
			     * @return {{description}}
			     {{/returnType}}
			     */
			    {{/comments}}
			    {{#annotations}}
			    {{.}}
			    {{/annotations}}
			    {{#returnType}}{{.}}{{/returnType}}{{^returnType}}void{{/returnType}} {{name}}({{#parameters}}{{type}} {{name}}{{^last}}, {{/last}}{{/parameters}});
			{{/methods}}
			}
			""";

	@Override
	public String generate(CodeGenerationContext context) {
		if(context.getClassifier().getType().equals(ClassifierType.CLASS)) {
			return generateClass(context.getClassifier(), context);
		}
		else if(context.getClassifier().getType().equals(ClassifierType.INTERFACE)) {
			return generateInterface(context.getClassifier(), context);
		}
		return null;
	}

	private String generateClass(Classifier classifier, CodeGenerationContext context) {
		Map<String, Object> templateData = new HashMap<>();

		templateData.put("package", determinePackage(classifier));
		templateData.put("className", classifier.getName());
		templateData.put("abstract", classifier.isAbstract());
		templateData.put("comments", context.hasOption(CodeGenerationOption.INCLUDE_COMMENTS));
		templateData.put("description", classifier.getDescription());

		// Imports
		Set<String> imports = new HashSet<>();
		if (context.hasOption(CodeGenerationOption.USE_ANNOTATIONS)) {
			imports.add("lombok.Data");
			imports.add("javax.persistence.*");
		}
		templateData.put("imports", imports);

		// Annotations
		List<String> annotations = new ArrayList<>();
		if (context.hasOption(CodeGenerationOption.USE_ANNOTATIONS)) {
			annotations.add("@Data");
			annotations.add("@Entity");
		}
		templateData.put("annotations", annotations);

		// Attributs
		List<Map<String, Object>> attributes = new ArrayList<>();
		if (classifier.getAttributes() != null) {
			for (Attribute attr : classifier.getAttributes()) {
				Map<String, Object> attrData = new HashMap<>();
				attrData.put("name", attr.getName());
				attrData.put("type", attr.getType());
				attrData.put("visibility", mapVisibility(attr.getVisibility()));
				attrData.put("static", attr.isStatic());
				attrData.put("final", attr.isFinal());
				attrData.put("defaultValue", attr.getDefaultValue());
				attrData.put("comments", context.hasOption(CodeGenerationOption.INCLUDE_COMMENTS));
				attrData.put("description", "Attribut " + attr.getName());
				attributes.add(attrData);
			}
		}
		templateData.put("attributes", attributes);

		// Méthodes
		List<Map<String, Object>> methods = new ArrayList<>();
		if (classifier.getMethods() != null) {
			for (Method method : classifier.getMethods()) {
				Map<String, Object> methodData = buildMethodData(method, context);
				methods.add(methodData);
			}
		}
		templateData.put("methods", methods);

		return new MustacheTemplateEngine().process(CLASS_TEMPLATE, templateData);
	}

	private String generateInterface(Classifier classifier, CodeGenerationContext context) {
		Map<String, Object> templateData = new HashMap<>();

		templateData.put("package", determinePackage(classifier));
		templateData.put("interfaceName", classifier.getName());
		templateData.put("comments", context.hasOption(CodeGenerationOption.INCLUDE_COMMENTS));
		templateData.put("description", classifier.getDescription());

		// Méthodes
		List<Map<String, Object>> methods = new ArrayList<>();
		if (classifier.getMethods() != null) {
			for (Method method : classifier.getMethods()) {
				Map<String, Object> methodData = buildMethodData(method, context);
				methods.add(methodData);
			}
		}
		templateData.put("methods", methods);

		return new MustacheTemplateEngine().process(INTERFACE_TEMPLATE, templateData);
	}

	private Map<String, Object> buildMethodData(Method method, CodeGenerationContext context) {
		Map<String, Object> methodData = new HashMap<>();
		methodData.put("name", method.getName());
		methodData.put("returnType", method.getReturnType());
		methodData.put("visibility", mapVisibility(method.getVisibility()));
		methodData.put("static", method.isStatic());
		methodData.put("abstract", method.isAbstract());
		methodData.put("final", method.isFinal());
		methodData.put("comments", context.hasOption(CodeGenerationOption.INCLUDE_COMMENTS));
		methodData.put("description", "Méthode " + method.getName());

		// Paramètres
		List<Map<String, Object>> parameters = new ArrayList<>();
		if (method.getParameters() != null) {
			for (int i = 0; i < method.getParameters().size(); i++) {
				Parameter param = method.getParameters().get(i);
				Map<String, Object> paramData = new HashMap<>();
				paramData.put("name", param.getName());
				paramData.put("type", param.getType());
				paramData.put("defaultValue", param.getDefaultValue());
				paramData.put("last", i == method.getParameters().size() - 1);
				paramData.put("description", "Paramètre " + param.getName());
				parameters.add(paramData);
			}
		}
		methodData.put("parameters", parameters);

		return methodData;
	}

	private String mapVisibility(Visibility visibility) {
		return switch (visibility) {
		case PUBLIC -> "public";
		case PRIVATE -> "private";
		case PROTECTED -> "protected";
		case PACKAGE_PRIVATE -> ""; // package-private
		};
	}

	private String determinePackage(Classifier classifier) {
		// Logique pour déterminer le package basé sur le diagramme ou autres critères
		if (classifier.getPackageElement() != null) {
			return classifier.getPackageElement().getName().toLowerCase().replaceAll("\\s+", "");
		}
		return "default";
	}

	@Override
	public Language getSupportedLanguage() {
		return Language.JAVA;
	}

	@Override
    public String getTemplate(Classifier classifier) {
        if(ClassifierType.CLASS.equals(classifier.getType())) {
        		return CLASS_TEMPLATE;
            }
        	else if(ClassifierType.INTERFACE.equals(classifier.getType())) {
        		return INTERFACE_TEMPLATE;
            }
        return "";
    }
}
