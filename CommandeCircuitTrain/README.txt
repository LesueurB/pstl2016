2013-12-17

TODO
-------------------------

TODO : nothing, all is perfect (non j'deconne)
	- externaliser les modes simulateur, le port de comm de l'arduino, l'adresse et le port de WiThrottle
	- créer un editeur de circuit

DONE : 
	- ajouter des scenarios � trois cantons plutot que 6 
DOC
---------------------

Librairie necessaire � la compilation : rxtxcomm.jar, elle doit �tre dans le build path, une version se trouve dans le repertoire complement

Pour faire fonctionner la bibliotheque serie il faut la suite de logiciel rxtx
(cf http://rxtx.qbang.org/wiki/index.php/Download#Pre-Built_Binaries)
une copie du zip se trouve dans "complements"

Les scenarii en XML se trouve dans le repertoire complements, pour fonctionner, il faut que les fichiers xml soit dans le meme repertoire que le moniteur (monitor.jar)

Les adresses des trains doivent etre des nombres entiers (pour le circuit exemple, les adresses des trains sont 2 pour la loco verte et 4 pour la loco rouge)

Lorsqu'on branche l'arduino, il faut modifier "a la main" le port dans la classe 
upmc.train.model.server.MessageDecoder (ligne 90)

Pour choisir entre un mode simulateur ou circuit reel, il faut egalement changer un booleen "a la main"
dans la classe 
upmc.train.model.server.NetMonitorDecoder, ligne 41

La communication avec le circuit se fait par WiThrottle, par d�faut :

port : 55555
adresse : localhost

Pour les modifier il faut les changer "� la main" dans la classe :

upmc.train.model.communication.NetMonitor
lignes 18 et 19

PAR DEFAUT LE MODE EST CIRCUIT REEL


ARDUINO 
-----------------------
Dans la version actuel du .ino (programme de la carte arduino) 
les capteurs peuvent �tre au maximum au nombre de 6
Les capteurs sont num�rot�s 1,2,3,4,5,6

1,2,3 (pins 2,3,4 ) sont d�di�es � des cantons
4,5,6 (pins 5,6,7) sont d�di�es � des stations

Les capteurs sont num�rot�s 1, 2,3,4,5,6

Les feux peuvent �tre au maximum au nombre de 3
pins A0, A1 (feu 1 rouge, feu 1 vert)
pins A2, A3 (feu 2 rouge, feu 2 vert)
pins A4, A5 (feu 3 rouge, feu 3 vert)

Consequences : seuls les scenarii � trois cantons peuvent fonctionner avec ce programme, l'extension � 6 cantons
est simple � envisager



