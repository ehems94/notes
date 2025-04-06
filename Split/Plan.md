- Organisation des repos
	- Monorepo C
	- Monorepo librairies
	- Monorepo M
- Sprint review à la fin des sprints - 30min par bloc
	- Division par "blocs" : split m/c, sujet Mat, sujet Pie...
	- Support : JIRA + **page confluence à remplir en amont ?**
	- Structuration
		- Intro - rappel des objectifs du sprint / livrables
		- Démo ? - présentation des livrables, difficultés, parties non finies ou partiellement livrées
		- Discussion critique
			- ce qui a marché : flow travail, interactions, outils
			- ce qui a bloqué / pris du temps : arbitrages, dépendances, qualité du code partagé
			- décisions à prendre : architecture, priorités à revoir, ajustements de périmètre
		- Préparation sprint suivant - ajustement roadmap, réaffecter tâches, confirmer objectifs prochain sprint
## Sprints

### Sprint 1 - Démarrage
- Identification des composants à extraire dans des librairies
	- Git / GitLab
	- Application
	- Helm
	- Result ?
- Bootstrap des librairies
	- Accent sur les tests unitaires
	- Monorepo, setup Gradle
	- HelmService (+ KubeProperties ?)
- Bootstrap projet 2
	- Spécifications
	- Monorepo, Spring Boot
- **Livrables du sprint**
	- Plan détaillé des composants / modules à extraire
		- Documenté / reviewé par les 3 SE (Ticket JIRA ? Issue GitLab ? Confluence ? ...)
	- Repo créés
### Sprint 2 - Premières extractions modules & intégrations
- Projet 1
	- Début de refactoring & intégration de modules, si possible
	- Màj build system pour utiliser les libs
- Projet 2
	- CI/CD basique (déploiement)
	- Intégration modules
	- Features de base sans sécurité ?
- Libs
	- CI/CD
	- Continuer les modules + tests
- Livrables
	- Premières versions des lib publiées / peuvent être publiées
	- CI/CD fonctionnelles pour lib + projet 2
### Sprint 3 - Stabilisation des libs
- Projet 1
	- Intégration des modules
- Projet 2
	- Interconnections DH, K...
	- Authentification
- Libs
	- Continuer modules
	- Documentation, tests, packaging
### Sprint 4 - Consolidation
- Projet 1
	- Finalisation refactoring
- Projet 2
	- Revue projet
- Libs
	- Peaufinages
### Sprint 5
## Components
### Projet 1
### Libs
- Bootstrapper la librairie
	- Structure gradle / dossiers
- Module Helm
- 
### Projet 2
- Spring Boot
	- A terme, lib d'observabilité pour DH, M, C ?