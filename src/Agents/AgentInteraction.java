package Agents;

import jade.core.Agent;

/**
 * agent jouant le role d'interface entre le systeme crawler et le systeme externe
 */
public class AgentInterface extends Agent {

    protected void setup() {
        System.out.println("Agent Interface : " + getLocalName() + " lancé");

    }
}
