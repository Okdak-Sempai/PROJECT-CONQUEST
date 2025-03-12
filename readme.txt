Informations:   SDK: Java17
		src contient fichiertesteColon.txt, necessaire aux tests unitaires.

# 🚀 Optimisation du Partage des Ressources dans une Colonie Spatiale

Ce projet a pour objectif de développer un logiciel en Java permettant d’optimiser la répartition des ressources critiques au sein d’une colonie spatiale. Après chaque mission de ravitaillement, le commandant doit attribuer ces ressources aux colons en respectant au mieux leurs préférences tout en minimisant les conflits interpersonnels.

L’algorithme mis en place devra :
- Modéliser la colonie sous forme de graphe représentant les relations entre colons.
- Prendre en compte les préférences individuelles des colons concernant les ressources.
- Calculer le coût d’une affectation en mesurant le niveau de jalousie entre colons.
- Trouver une répartition optimale minimisant ces conflits.

Ce projet s'inscrit dans le cadre d'un exercice académique de programmation avancée et vise à explorer des solutions algorithmiques efficaces pour la gestion des ressources en environnement contraint.





Informations sur le code:

-La classe main se trouve dans Conquest.Main

-Un algorithme plus efficace que le Naif fut implenté, il réside en Conquest.Expedition.meuilleurResultat()
Voici l'explication de ce qu'il fait :
L’algorithme amélioré repose sur une représentation du problème sous forme de graphe où les colons sont des nœuds et leurs relations détestables sont des arêtes pondérées.
Chaque arête est pondérée en fonction du degré de détestation entre deux colons.
Cela permet de modéliser le problème comme un problème de partitionnement de graphe, où l’objectif est de minimiser les conflits tout en respectant les contraintes des ressources.

Dans une première étape, une analyse préliminaire est effectuée pour évaluer les préférences des colons et leurs relations.
Une priorité est donnée aux colons ayant le plus grand impact potentiel sur les conflits globaux (combinaison de contraintes et de préférences).
Cette analyse permet d’éliminer les affectations non viables dès le départ et de limiter l’espace des solutions à explorer.

L’algorithme applique ensuite une méthode d’optimisation par Simulated Annealing.
Cette méthode démarre avec une affectation initiale aléatoire ou heuristique des ressources aux colons.
À chaque itération, une modification est apportée à l’affectation courante (par exemple, en échangeant les ressources entre deux colons).
Si cette modification réduit le coût global (conflits), elle est acceptée immédiatement. Sinon, elle peut être acceptée avec une probabilité décroissante au fil des itérations, ce qui permet d’éviter de rester bloqué dans un minimum local.

Enfin, pour améliorer encore l’affectation, l’algorithme utilise un processus d’optimisation finale par Tabu Search.
Ce processus explore systématiquement des échanges locaux entre colons, tout en maintenant une liste taboue des solutions récemment explorées pour éviter les cycles.
Cette étape garantit que l’algorithme affine la solution jusqu’à atteindre un minimum global ou une configuration très proche de l’optimal.

Ces étapes combinées permettent une exploration efficace de l’espace des solutions, avec une convergence rapide vers une configuration de faible coût global.
L’approche offre une meilleure performance par rapport à l’algorithme initial en termes de temps de calcul et de qualité des solutions trouvées.

-Toutes les fonctionalitées ont pu etre implementé. Un algorithme performant est present et des tests unitaires renforcent le code de manière generale. Une Javadoc complete et poussé fut proposé aussi.
Ce qu'il manque est la JavaFX qui au final n'a pas pu etre faite au vu de l'organisation du groupe.
