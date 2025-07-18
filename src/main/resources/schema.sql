-- DDL Compatible H2, MySQL et PostgreSQL
-- Schéma pour application UML

-- =============================================================================
-- TABLES PRINCIPALES
-- =============================================================================

-- Table des utilisateurs
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20),
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    CONSTRAINT chk_user_status CHECK (status IN ('ACTIVE', 'INACTIVE', 'SUSPENDED'))
);

-- Table des projets
CREATE TABLE projects (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version BIGINT NOT NULL DEFAULT 0,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    git_repository_path VARCHAR(255)
);

CREATE TABLE code_generations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    project_id BIGINT NOT NULL,
    CONSTRAINT fk_code_genration_project FOREIGN KEY (project_id) REFERENCES projects(id)
);

-- Table des packages
CREATE TABLE packages (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version BIGINT NOT NULL DEFAULT 0,
    name VARCHAR(100) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    project_id BIGINT NOT NULL,
    parent_package_id BIGINT,
    CONSTRAINT fk_package_project FOREIGN KEY (project_id) REFERENCES projects(id),
    CONSTRAINT fk_package_parent FOREIGN KEY (parent_package_id) REFERENCES packages(id)
);

-- Table des diagrammes
CREATE TABLE diagrams (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version BIGINT NOT NULL DEFAULT 0,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL,
    description VARCHAR(500),
    project_id BIGINT NOT NULL,
    CONSTRAINT fk_diagram_project FOREIGN KEY (project_id) REFERENCES projects(id),
    CONSTRAINT chk_diagram_type CHECK (type IN ('CLASS_DIAGRAM', 'ACTIVITY_DIAGRAM'))
);

-- Table des classificateurs (classes, interfaces, etc.)
CREATE TABLE classifiers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version BIGINT NOT NULL DEFAULT 0,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL,
    visibility VARCHAR(20) NOT NULL DEFAULT 'PUBLIC',
    is_abstract BOOLEAN NOT NULL DEFAULT FALSE,
    is_final BOOLEAN NOT NULL DEFAULT FALSE,
    is_static BOOLEAN NOT NULL DEFAULT FALSE,
    description VARCHAR(500),
    position_x DOUBLE PRECISION,
    position_y DOUBLE PRECISION,
    diagram_id BIGINT NOT NULL,
    package_id BIGINT,
    CONSTRAINT fk_classifier_diagram FOREIGN KEY (diagram_id) REFERENCES diagrams(id),
    CONSTRAINT fk_classifier_package FOREIGN KEY (package_id) REFERENCES packages(id),
    CONSTRAINT chk_classifier_type CHECK (type IN ('CLASS', 'INTERFACE', 'ENUM', 'ABSTRACT_CLASS')),
    CONSTRAINT chk_classifier_visibility CHECK (visibility IN ('PUBLIC', 'PRIVATE', 'PROTECTED', 'PACKAGE_PRIVATE'))
);

-- Table des attributs
CREATE TABLE attributes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version BIGINT NOT NULL DEFAULT 0,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(100) NOT NULL,
    visibility VARCHAR(20) NOT NULL DEFAULT 'PRIVATE',
    is_static BOOLEAN NOT NULL DEFAULT FALSE,
    is_final BOOLEAN NOT NULL DEFAULT FALSE,
    default_value VARCHAR(255),
    description VARCHAR(500),
    classifier_id BIGINT NOT NULL,
    CONSTRAINT fk_attribute_classifier FOREIGN KEY (classifier_id) REFERENCES classifiers(id),
    CONSTRAINT chk_attribute_visibility CHECK (visibility IN ('PUBLIC', 'PRIVATE', 'PROTECTED', 'PACKAGE_PRIVATE'))
);

-- Table des méthodes
CREATE TABLE methods (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version BIGINT NOT NULL DEFAULT 0,
    name VARCHAR(100) NOT NULL,
    return_type VARCHAR(100) DEFAULT 'void',
    visibility VARCHAR(20) NOT NULL DEFAULT 'PUBLIC',
    is_static BOOLEAN NOT NULL DEFAULT FALSE,
    is_final BOOLEAN NOT NULL DEFAULT FALSE,
    is_abstract BOOLEAN NOT NULL DEFAULT FALSE,
    body VARCHAR(2000),
    description VARCHAR(500),
    classifier_id BIGINT NOT NULL,
    CONSTRAINT fk_method_classifier FOREIGN KEY (classifier_id) REFERENCES classifiers(id),
    CONSTRAINT chk_method_visibility CHECK (visibility IN ('PUBLIC', 'PRIVATE', 'PROTECTED', 'PACKAGE_PRIVATE'))
);

-- Table des paramètres
CREATE TABLE parameters (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version BIGINT NOT NULL DEFAULT 0,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(100) NOT NULL,
    parameter_order INTEGER,
    default_value VARCHAR(255),
    method_id BIGINT NOT NULL,
    CONSTRAINT fk_parameter_method FOREIGN KEY (method_id) REFERENCES methods(id)
);

-- Table des relations entre classes
CREATE TABLE relationships (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version BIGINT NOT NULL DEFAULT 0,
    type VARCHAR(50) NOT NULL,
    name VARCHAR(100),
    source_multiplicity VARCHAR(20),
    target_multiplicity VARCHAR(20),
    source_role VARCHAR(100),
    target_role VARCHAR(100),
    is_navigable_source_to_target BOOLEAN NOT NULL DEFAULT TRUE,
    is_navigable_target_to_source BOOLEAN NOT NULL DEFAULT FALSE,
    description VARCHAR(500),
    diagram_id BIGINT NOT NULL,
    source_classifier_id BIGINT NOT NULL,
    target_classifier_id BIGINT NOT NULL,
    CONSTRAINT fk_relationship_diagram FOREIGN KEY (diagram_id) REFERENCES diagrams(id),
    CONSTRAINT fk_relationship_source FOREIGN KEY (source_classifier_id) REFERENCES classifiers(id),
    CONSTRAINT fk_relationship_target FOREIGN KEY (target_classifier_id) REFERENCES classifiers(id),
    CONSTRAINT chk_relationship_type CHECK (type IN ('ASSOCIATION', 'DEPENDENCY', 'SPECIALIZATION'))
);

-- Table des activités (pour diagrammes d'activités)
CREATE TABLE activities (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version BIGINT NOT NULL DEFAULT 0,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL,
    description VARCHAR(1000),
    code VARCHAR(2000),
    condition_expression VARCHAR(500),
    position_x DOUBLE PRECISION,
    position_y DOUBLE PRECISION,
    diagram_id BIGINT NOT NULL,
    CONSTRAINT fk_activity_diagram FOREIGN KEY (diagram_id) REFERENCES diagrams(id),
    CONSTRAINT chk_activity_type CHECK (type IN ('METHOD'))
);

-- Table des flux entre activités
CREATE TABLE activity_flows (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version BIGINT NOT NULL DEFAULT 0,
    name VARCHAR(100),
    guard_condition VARCHAR(500),
    description VARCHAR(500),
    diagram_id BIGINT NOT NULL,
    source_activity_id BIGINT NOT NULL,
    target_activity_id BIGINT NOT NULL,
    CONSTRAINT fk_flow_diagram FOREIGN KEY (diagram_id) REFERENCES diagrams(id),
    CONSTRAINT fk_flow_source FOREIGN KEY (source_activity_id) REFERENCES activities(id),
    CONSTRAINT fk_flow_target FOREIGN KEY (target_activity_id) REFERENCES activities(id)
);

-- =============================================================================
-- INDEX POUR OPTIMISER LES PERFORMANCES
-- =============================================================================

-- Index pour les recherches fréquentes
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_status ON users(status);

CREATE INDEX idx_projects_name ON projects(name);

CREATE INDEX idx_packages_project ON packages(project_id);
CREATE INDEX idx_packages_parent ON packages(parent_package_id);
CREATE INDEX idx_packages_full_name ON packages(full_name);

CREATE INDEX idx_diagrams_project ON diagrams(project_id);
CREATE INDEX idx_diagrams_type ON diagrams(type);

CREATE INDEX idx_classifiers_diagram ON classifiers(diagram_id);
CREATE INDEX idx_classifiers_package ON classifiers(package_id);
CREATE INDEX idx_classifiers_name ON classifiers(name);

CREATE INDEX idx_attributes_classifier ON attributes(classifier_id);
CREATE INDEX idx_methods_classifier ON methods(classifier_id);
CREATE INDEX idx_parameters_method ON parameters(method_id);

CREATE INDEX idx_relationships_diagram ON relationships(diagram_id);
CREATE INDEX idx_relationships_source ON relationships(source_classifier_id);
CREATE INDEX idx_relationships_target ON relationships(target_classifier_id);

CREATE INDEX idx_activities_diagram ON activities(diagram_id);
CREATE INDEX idx_activity_flows_diagram ON activity_flows(diagram_id);
CREATE INDEX idx_activity_flows_source ON activity_flows(source_activity_id);
CREATE INDEX idx_activity_flows_target ON activity_flows(target_activity_id);

-- =============================================================================
-- COMMENTAIRES POUR DOCUMENTER LE SCHÉMA
-- =============================================================================

-- Commentaires sur les tables principales
COMMENT ON TABLE users IS 'Utilisateurs de l''application';
COMMENT ON TABLE projects IS 'Projets UML (correspondant à des repositories Git)';
COMMENT ON TABLE packages IS 'Packages UML pour organiser les classes';
COMMENT ON TABLE diagrams IS 'Diagrammes UML (classe ou activité)';
COMMENT ON TABLE classifiers IS 'Éléments de classification (classes, interfaces, enum)';
COMMENT ON TABLE attributes IS 'Attributs des classes';
COMMENT ON TABLE methods IS 'Méthodes des classes';
COMMENT ON TABLE parameters IS 'Paramètres des méthodes';
COMMENT ON TABLE relationships IS 'Relations entre classes (héritage, association, etc.)';
COMMENT ON TABLE activities IS 'Activités dans les diagrammes d''activités';
COMMENT ON TABLE activity_flows IS 'Flux entre activités';

-- =============================================================================
-- DONNÉES DE TEST (OPTIONNEL)
-- =============================================================================

-- Exemple d'utilisateur
INSERT INTO users (first_name, last_name, email, status) 
VALUES ('John', 'Doe', 'john.doe@example.com', 'ACTIVE');

-- Exemple de projet
INSERT INTO projects (name, description) 
VALUES ('Mon Projet UML', 'Projet de démonstration pour l''application UML');

-- Exemple de diagramme de classes
INSERT INTO diagrams (name, type, description, project_id) 
VALUES ('Diagramme Principal', 'CLASS_DIAGRAM', 'Diagramme de classes principal', 1);