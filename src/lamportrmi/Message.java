package lamportrmi;

import java.io.Serializable;

/**
 * Classe qui gèrent les messages de communication entre les serveurs, avec le TYPE
 * de message, l'id du serveur et l'heure d'envoi
 * 
 * @author Nathan & Jimmy
 */
 class Message implements Serializable {
    
    private final int FROM;         // Qui envoie le message
    private final MessageType TYPE; // Type de message envoyé
    
    private long date;              // Date d'envoie du message
    
    /**
     * Constructeur de la classe Message
     * @param from Qui envoie le message
     * @param date Date d'envoie du message
     * @param type Type de message envoyé
     */
    Message(int from, long date, MessageType type) {
        this.FROM = from;
        this.date = date;
        this.TYPE = type;
    }

    /**
     * Métode pour obtenir qui envoie le message
     * @return L'id de qui envoie
     */
    public int getFrom() {
        return FROM;
    }

    /**
     * Métode pour obtenir la date d'envoie du message
     * @return La date de l'envoie
     */
    public long getDate() {
        return date;
    }

    /**
     * Obtient le type du message
     * @return Le type de message
     */
    public MessageType getType() {
        return TYPE;
    }
    
    /**
     * Modificateur de la date du message
     * @param newDate La nouvelle date du message
     */
    public void setDate(long newDate) {
        this.date = newDate;
    }
}
