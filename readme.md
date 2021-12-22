# SkySpy
## By Mohamed DIB & Dylan HEYRAUD


## FeedBack
Nous avons trouvé le projet tellement intéressant, car c'était la première fois que nous travaillions avec LiveData, coroutines...

Avant ce projet, nous avions l'habitude de récupérer les données de manière désordonnée, mais maintenant il est devenu facile de communiquer des données entre les modèles, afficher le modèle de manière asynchrone ou synchrone.

Mais encore, nous avons rencontré quelques difficultés :

## Difficultés rencontrées

- L'api ne répond pas tout le temps, ce qui fait perdre beaucoup du temps
- Pour utiliser google maps sur un fragment, nous devions utiliser MapView, mais il n'y avait aucune documentation sur l'ajout de carte sur un MapView
- Nous ne pouvons pas obtenir le trajet de l'avion à partir de l'API selon la documentation : ("The tracks endpoint is currently not functional")
- Nous étions pressés par le temps (examens, alternance)

## Améliorations possible
- Dans la page d'acceuil, remplacer le Spinner par un autocomplete input
- Filtrer les dates dans le Date Picker, pour afficher uniquement les dates avec des vols
- Améliorer le design de la page de la carte, afin qu'elle puisse ressembler à la maquette
- Pour obtenir un suivi en temps réel de l'avion, nous devons constamment envoyer une requete à l'API et mettre à jour la position sur la carte
- Il manque la partie "la liste des vols de l’avion sélectionné"