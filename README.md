# Lancement
Pour lancer le programme, il faut ouvrir dans NetBeans le projet, faire un clean/build du projet, ouvrir le fichier `MainBot.java`, faire click droit dessus et utiliser l'option Run File. En faisant ceci, les serveurs se connecteront automatiquement entre eux.

Après la connexion, il faut aller dans le dossier `src/lamportrmi`, dans lequel vous trouverez des fichier `client.bat` numérotés. Il faut lancer ces fichiers en faisant double click sur chaque fichier (ou en sélectionnant tous les fichiers et en cliquant sur Enter pour les lancer tous à la fois).

Ceci ouvrira une ligne de commande par client dans laquelle vous aurez 3 options:
- En écrivant '1' -> Affichage de la variable globalle, pour obtenir sa valeur et l'afficher.
- En écrivant '2' -> Modification de la variable globalle, moment dans lequel on veut modifier la variable globalle, où l'on écrira le nouveau nombre que l'on veut pour la variable.
- En écrivant 'q' -> Quitter la ligne de commande.

### Fichiers bat
Cette classe nous sert à créer les fichier .bat que l'on pourra lancer pour connecter les serveurs (fait automatiquement) et lancer les clients qui se connecteront aux servers.
Le nom des fichier seront: `launch_server(id).bat`, pour lancer les serveurs, situés dans la racine du projet; et `client(id).bat` pour les clients, situés dans le dossier `src/lamportrmi`.

### Serveur
La classe DistributedServer est la classe principale du serveur. Elle possède donc un main qui doit être appelé avec des arguments:

- `l'adresse ip` du serveur qu'on monte actuellement et qui doit correspondre a une des adresse ip serveur déclaré dans le fichier `structure.txt`
- `le port` du serveur qu'on monte actuellement et qui doit correspondre a une des ports serveur déclaré dans le fichier `structure.txt`

La classe DistributerServer sera appelée autant de fois qu'il y a de lignes dans le fichier strucutre.txt (actuellement 5, modifiable), par le fichier `launch_server(id).bat`.

### Client
Pour le client, la classe principale est ClientOfGlobalRMIServer.

Ici on peut appeler un client se connectant à un des serveur précédement créé.

La classe sera appelé par les dossiers `client(id).bat` avec l'adresse ip et le port où on peut le trouver.

Se référer au fichier structure.txt pour être sûr des valeurs.

(Vous remarquerez que les ports ne sont pas les mêmes que pour le serveur. C'est parce que les ports de communication entre serveurs sont les ports 1099-1103 et les ports de communication entre clients et serveurs sont 1104-1109)
