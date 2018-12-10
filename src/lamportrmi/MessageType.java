/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lamportrmi;

/**
 *
 * @author jimmy
 */
public enum MessageType {
    REQUEST,        // Requete pour dire que l'on veut accéder à la section critique
    RESPONSE,       // Réponse d'un client pour accepter l'accès d'un autre client
                    // à la section critique
    FREE;           // Message de libération de la section critique
}
