package lamportrmi;

import java.io.Serializable;

/**
 * Classe qui gèrent les messages de communication entre les serveurs, avec le TYPE
 de message, l'id du serveur et l'heure d'envoi
 * 
 * @author Nathan & Jimmy
 */
 class Message implements Serializable {
    
    private final int FROM;
    private final MessageType TYPE;
    
    private long date;
    
    Message(int from, long date, MessageType type) {
        this.FROM = from;
        this.date = date/1000/3600;
        this.TYPE = type;
    }

    public int getFROM() {
        return FROM;
    }

    public long getDate() {
        return date;
    }

    public MessageType getTYPE() {
        return TYPE;
    }
    
    public void setDate(long newDate) {
        this.date = newDate;
    }
}
