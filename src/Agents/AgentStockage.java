package Agents;

import jade.core.Agent;

/**
 * agent chargé de stocker les donnees filtrée et categorisée dans la base de donnee de services
 */
public class AgentStockage extends Agent {

    protected void setup() {
        System.out.println("Agent Stockage : " + getLocalName() + " lancé");

    }
}
