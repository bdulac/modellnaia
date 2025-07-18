// =============================================
// TYPES ET INTERFACES DU MODÈLE UML
// =============================================

export interface UMLElement {
  id: string;
  name: string;
  type: UMLElementType;
  position: Position;
  properties: Record<string, any>;
}

export interface Position {
  x: number;
  y: number;
}

export interface Size {
  width: number;
  height: number;
}

export enum UMLElementType {
  CLASS = 'class',
  INTERFACE = 'interface',
  ENUM = 'enum',
  PACKAGE = 'package',
  ASSOCIATION = 'association',
  INHERITANCE = 'inheritance',
  COMPOSITION = 'composition',
  AGGREGATION = 'aggregation'
}

export interface UMLClass extends UMLElement {
  type: UMLElementType.CLASS;
  attributes: UMLAttribute[];
  methods: UMLMethod[];
  stereotype?: string;
  isAbstract: boolean;
}

export interface UMLAttribute {
  id: string;
  name: string;
  type: string;
  visibility: Visibility;
  isStatic: boolean;
}

export interface UMLMethod {
  id: string;
  name: string;
  returnType: string;
  visibility: Visibility;
  isStatic: boolean;
  isAbstract: boolean;
  parameters: UMLParameter[];
}

export interface UMLParameter {
  name: string;
  type: string;
}

export enum Visibility {
  PRIVATE = 'private',
  PROTECTED = 'protected',
  PUBLIC = 'public',
  PACKAGE = 'package'
}

export interface UMLRelation extends UMLElement {
  type: UMLElementType.ASSOCIATION | UMLElementType.INHERITANCE | 
        UMLElementType.COMPOSITION | UMLElementType.AGGREGATION;
  sourceId: string;
  targetId: string;
  sourceMultiplicity?: string;
  targetMultiplicity?: string;
  label?: string;
}

export interface UMLDiagram {
  id: string;
  name: string;
  elements: UMLElement[];
  relations: UMLRelation[];
  metadata: DiagramMetadata;
}

export interface DiagramMetadata {
  version: string;
  lastModified: Date;
  author?: string;
  description?: string;
}

// =============================================
// COUCHE DE DONNÉES - REPOSITORY PATTERN
// =============================================

export interface DiagramRepository {
  findById(id: string): Promise<UMLDiagram | null>;
  save(diagram: UMLDiagram): Promise<UMLDiagram>;
  delete(id: string): Promise<void>;
  findAll(): Promise<UMLDiagram[]>;
}

export class RestDiagramRepository implements DiagramRepository {
  constructor(private baseUrl: string = '/api/diagrams') {}

  async findById(id: string): Promise<UMLDiagram | null> {
    try {
      const response = await fetch(`${this.baseUrl}/${id}`);
      if (!response.ok) {
        if (response.status === 404) return null;
        throw new Error(`HTTP ${response.status}: ${response.statusText}`);
      }
      return await response.json();
    } catch (error) {
      console.error('Error fetching diagram:', error);
      throw error;
    }
  }

  async save(diagram: UMLDiagram): Promise<UMLDiagram> {
    const method = diagram.id ? 'PUT' : 'POST';
    const url = diagram.id ? `${this.baseUrl}/${diagram.id}` : this.baseUrl;
    
    const response = await fetch(url, {
      method,
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(diagram)
    });

    if (!response.ok) {
      throw new Error(`HTTP ${response.status}: ${response.statusText}`);
    }
    
    return await response.json();
  }

  async delete(id: string): Promise<void> {
    const response = await fetch(`${this.baseUrl}/${id}`, {
      method: 'DELETE'
    });
    
    if (!response.ok) {
      throw new Error(`HTTP ${response.status}: ${response.statusText}`);
    }
  }

  async findAll(): Promise<UMLDiagram[]> {
    const response = await fetch(this.baseUrl);
    if (!response.ok) {
      throw new Error(`HTTP ${response.status}: ${response.statusText}`);
    }
    return await response.json();
  }
}

// =============================================
// COUCHE MÉTIER - SERVICES
// =============================================

export class DiagramService {
  constructor(private repository: DiagramRepository) {}

  async getDiagram(id: string): Promise<UMLDiagram | null> {
    return await this.repository.findById(id);
  }

  async saveDiagram(diagram: UMLDiagram): Promise<UMLDiagram> {
    diagram.metadata.lastModified = new Date();
    return await this.repository.save(diagram);
  }

  async createNewDiagram(name: string): Promise<UMLDiagram> {
    const diagram: UMLDiagram = {
      id: crypto.randomUUID(),
      name,
      elements: [],
      relations: [],
      metadata: {
        version: '1.0',
        lastModified: new Date()
      }
    };
    
    return await this.repository.save(diagram);
  }

  async deleteDiagram(id: string): Promise<void> {
    await this.repository.delete(id);
  }

  async getAllDiagrams(): Promise<UMLDiagram[]> {
    return await this.repository.findAll();
  }
}

// =============================================
// GESTION DES COMMANDES - COMMAND PATTERN
// =============================================

export interface Command {
  execute(): void;
  undo(): void;
  getDescription(): string;
}

export class CommandHistory {
  private history: Command[] = [];
  private currentIndex = -1;

  execute(command: Command): void {
    // Supprimer les commandes après l'index actuel
    this.history = this.history.slice(0, this.currentIndex + 1);
    
    command.execute();
    this.history.push(command);
    this.currentIndex++;
  }

  undo(): boolean {
    if (this.currentIndex >= 0) {
      this.history[this.currentIndex].undo();
      this.currentIndex--;
      return true;
    }
    return false;
  }

  redo(): boolean {
    if (this.currentIndex < this.history.length - 1) {
      this.currentIndex++;
      this.history[this.currentIndex].execute();
      return true;
    }
    return false;
  }

  canUndo(): boolean {
    return this.currentIndex >= 0;
  }

  canRedo(): boolean {
    return this.currentIndex < this.history.length - 1;
  }
}

export class AddElementCommand implements Command {
  constructor(
    private diagram: UMLDiagram,
    private element: UMLElement
  ) {}

  execute(): void {
    this.diagram.elements.push(this.element);
  }

  undo(): void {
    const index = this.diagram.elements.findIndex(e => e.id === this.element.id);
    if (index !== -1) {
      this.diagram.elements.splice(index, 1);
    }
  }

  getDescription(): string {
    return `Add ${this.element.type}: ${this.element.name}`;
  }
}

export class RemoveElementCommand implements Command {
  private removedIndex: number = -1;

  constructor(
    private diagram: UMLDiagram,
    private elementId: string
  ) {}

  execute(): void {
    this.removedIndex = this.diagram.elements.findIndex(e => e.id === this.elementId);
    if (this.removedIndex !== -1) {
      this.diagram.elements.splice(this.removedIndex, 1);
    }
  }

  undo(): void {
    if (this.removedIndex !== -1) {
      const element = this.diagram.elements.find(e => e.id === this.elementId);
      if (element) {
        this.diagram.elements.splice(this.removedIndex, 0, element);
      }
    }
  }

  getDescription(): string {
    return `Remove element: ${this.elementId}`;
  }
}

// =============================================
// GESTIONNAIRE PRINCIPAL - FACADE PATTERN
// =============================================

export class UMLEditorManager {
  private diagram: UMLDiagram | null = null;
  private commandHistory = new CommandHistory();
  private eventTarget = new EventTarget();

  constructor(private diagramService: DiagramService) {}

  // Gestion du diagramme
  async loadDiagram(id: string): Promise<void> {
    this.diagram = await this.diagramService.getDiagram(id);
    this.dispatchEvent('diagramLoaded', { diagram: this.diagram });
  }

  async saveDiagram(): Promise<void> {
    if (!this.diagram) throw new Error('No diagram loaded');
    
    this.diagram = await this.diagramService.saveDiagram(this.diagram);
    this.dispatchEvent('diagramSaved', { diagram: this.diagram });
  }

  async createNewDiagram(name: string): Promise<void> {
    this.diagram = await this.diagramService.createNewDiagram(name);
    this.commandHistory = new CommandHistory(); // Reset history
    this.dispatchEvent('diagramCreated', { diagram: this.diagram });
  }

  getCurrentDiagram(): UMLDiagram | null {
    return this.diagram;
  }

  // Gestion des éléments
  addElement(element: UMLElement): void {
    if (!this.diagram) throw new Error('No diagram loaded');
    
    const command = new AddElementCommand(this.diagram, element);
    this.commandHistory.execute(command);
    this.dispatchEvent('elementAdded', { element });
  }

  removeElement(elementId: string): void {
    if (!this.diagram) throw new Error('No diagram loaded');
    
    const command = new RemoveElementCommand(this.diagram, elementId);
    this.commandHistory.execute(command);
    this.dispatchEvent('elementRemoved', { elementId });
  }

  updateElement(elementId: string, updates: Partial<UMLElement>): void {
    if (!this.diagram) throw new Error('No diagram loaded');
    
    const element = this.diagram.elements.find(e => e.id === elementId);
    if (element) {
      Object.assign(element, updates);
      this.dispatchEvent('elementUpdated', { element });
    }
  }

  // Gestion de l'historique
  undo(): boolean {
    const result = this.commandHistory.undo();
    if (result) {
      this.dispatchEvent('undoExecuted', {});
    }
    return result;
  }

  redo(): boolean {
    const result = this.commandHistory.redo();
    if (result) {
      this.dispatchEvent('redoExecuted', {});
    }
    return result;
  }

  canUndo(): boolean {
    return this.commandHistory.canUndo();
  }

  canRedo(): boolean {
    return this.commandHistory.canRedo();
  }

  // Gestion des événements
  addEventListener(type: string, listener: EventListener): void {
    this.eventTarget.addEventListener(type, listener);
  }

  removeEventListener(type: string, listener: EventListener): void {
    this.eventTarget.removeEventListener(type, listener);
  }

  private dispatchEvent(type: string, detail: any): void {
    this.eventTarget.dispatchEvent(new CustomEvent(type, { detail }));
  }
}

// =============================================
// FACTORY POUR CRÉER DES ÉLÉMENTS UML
// =============================================

export class UMLElementFactory {
  static createClass(name: string, position: Position): UMLClass {
    return {
      id: crypto.randomUUID(),
      name,
      type: UMLElementType.CLASS,
      position,
      properties: {},
      attributes: [],
      methods: [],
      isAbstract: false
    };
  }

  static createInterface(name: string, position: Position): UMLElement {
    return {
      id: crypto.randomUUID(),
      name,
      type: UMLElementType.INTERFACE,
      position,
      properties: {
        methods: []
      }
    };
  }

  static createRelation(
    type: UMLElementType.ASSOCIATION | UMLElementType.INHERITANCE | 
          UMLElementType.COMPOSITION | UMLElementType.AGGREGATION,
    sourceId: string,
    targetId: string
  ): UMLRelation {
    return {
      id: crypto.randomUUID(),
      name: '',
      type,
      position: { x: 0, y: 0 },
      properties: {},
      sourceId,
      targetId
    };
  }
}

// =============================================
// EXEMPLE D'UTILISATION
// =============================================

export class UMLEditorApp {
  private editorManager: UMLEditorManager;

  constructor() {
    const repository = new RestDiagramRepository();
    const service = new DiagramService(repository);
    this.editorManager = new UMLEditorManager(service);

    this.setupEventListeners();
  }

  private setupEventListeners(): void {
    this.editorManager.addEventListener('diagramLoaded', (event: any) => {
      console.log('Diagram loaded:', event.detail.diagram);
      this.renderDiagram(event.detail.diagram);
    });

    this.editorManager.addEventListener('elementAdded', (event: any) => {
      console.log('Element added:', event.detail.element);
      this.renderElement(event.detail.element);
    });

    // Configuration des raccourcis clavier
    document.addEventListener('keydown', (event) => {
      if (event.ctrlKey || event.metaKey) {
        switch (event.key) {
          case 'z':
            event.preventDefault();
            if (event.shiftKey) {
              this.editorManager.redo();
            } else {
              this.editorManager.undo();
            }
            break;
          case 's':
            event.preventDefault();
            this.editorManager.saveDiagram();
            break;
        }
      }
    });
  }

  private renderDiagram(diagram: UMLDiagram): void {
    // Intégration avec vos web components Polymeria
    const diagramContainer = document.querySelector('#diagram-container');
    if (diagramContainer) {
      diagramContainer.innerHTML = '';
      
      diagram.elements.forEach(element => {
        this.renderElement(element);
      });
    }
  }

  private renderElement(element: UMLElement): void {
    // Création des web components selon le type d'élément
    let componentTag: string;
    
    switch (element.type) {
      case UMLElementType.CLASS:
        componentTag = 'uml-class';
        break;
      case UMLElementType.INTERFACE:
        componentTag = 'uml-interface';
        break;
      case UMLElementType.ENUM:
        componentTag = 'uml-enum';
        break;
      default:
        componentTag = 'uml-element';
    }

    const webComponent = document.createElement(componentTag);
    webComponent.setAttribute('element-id', element.id);
    webComponent.setAttribute('data', JSON.stringify(element));
    
    // Positionnement
    webComponent.style.position = 'absolute';
    webComponent.style.left = `${element.position.x}px`;
    webComponent.style.top = `${element.position.y}px`;

    document.querySelector('#diagram-container')?.appendChild(webComponent);
  }

  async initialize(): Promise<void> {
    // Initialisation de l'application
    await this.editorManager.createNewDiagram('New Diagram');
  }

  getEditorManager(): UMLEditorManager {
    return this.editorManager;
  }
}