package lamportrmi;

/**
 * Cet enum comporte les messages que les slients envoient s'ils veulent accéder à 
 * la section critique ou s'il sortent de la section critique
 * 
 * @author Nathan & Jimmy
 */
 class Message {
    
    
    Message(int from, long date, MessageType type) {
        this.from = from;
        this.date = date;
        this.type = type;
    }
    
    private final int from;
    private final long date;
    private final MessageType type;

    public int getFrom() {
        return from;
    }

    public long getDate() {
        return date;
    }

    public MessageType getType() {
        return type;
    }
}
