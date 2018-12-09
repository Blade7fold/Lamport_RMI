package lamportrmi;

/**
 * Cet enum comporte les messages que les slients envoient s'ils veulent accéder à 
 * la section critique ou s'il sortent de la section critique
 * 
 * @author Nathan & Jimmy
 */
public enum Message {
    REQUEST,        // Requete pour dire que l'on veut accéder à la section critique
    RESPONSE,       // Réponse d'un client pour accepter l'accès d'un autre client
                    // à la section critique
    FREE;           // Message de libération de la section critique
}
