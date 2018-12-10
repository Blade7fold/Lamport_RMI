###SERVEUR
La classe DistributedServer est la classe principale du serveur. Elle possède donc un main qui doit être appelé avec des arguments:

- l'adresse ip du serveur qu'on monte actuellement et qui doit correspondre a une des adresse ip serveur déclaré dans le fichier structure.txt
- le port du serveur qu'on monte actuellement et qui doit correspondre a une des ports serveur déclaré dans le fichier structure.txt
Les deux arguments doivent correspondre à la même ligne du fichier structure.txt

Il faut donc appeler la classe DistributerServer autant de fois qu'il y a de ligne dans le fichier strucutre.txt (actuellement 3)

Exemple :
//TODO

Pendant la phase de DEBUG on peut utiliser la méthode brut et commenter/décommenter un par un les lignes du main :

int own_port = 1099;
//int own_port = 1100;
//int own_port = 1101;

ps. Vous avez une minute pour faire cette manipulation sinon les serveurs considéreront qu'ils n'ont pas pu se connecter entre eux et donc le pool de machine ne sera pas complet. À la fin de la minute un message "Tous les serveurs sont connectés" s'affiche (que le pool soit complet ou non).

===============================================================================================
###CLIENT
Pour le client, la classe principale est ClientOfGlobalRMIServer.

Ici on peut appeler un client se connectant à un des serveur précédement créé.

Il faut donc appelé la classe en lui spécifiant l'adresse ip et le port où on peut le trouver.

se référer au fichier structure.txt pour être sûr des valeurs

Pour la phase de DEBUG on peut aussi commenter/décommenter les trois lignes du main suivante:
int port = 1102;
//int port = 1103;
//int port = 1104;

Vous remarquerez que les ports ne sont pas les mêmes que pour le serveur. C'est parce que pour l'instant les port de communication entre serveur sont les port 1099-1101 et les ports de communication entre client et serveur sont 1102-1104