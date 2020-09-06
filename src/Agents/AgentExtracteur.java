package Agents;

import jade.core.Agent;

/**
 * agent to extract services descriptions on the cloud
 */
public class AgentExtracteur extends Agent {

    @Override
    protected void setup() {
        System.out.println("Agent Exracteur : " + getLocalName() + " lancé");

    }
}
