package Agents;

import Behaviors.ExtractionBehavior;
import jade.core.Agent;

/**
 * agent to extract services descriptions on the cloud
 */
public class AgentExtracteur extends Agent {

    long period = 5000;

    @Override
    protected void setup() {

        System.out.println("Agent Exracteur : " + getLocalName() + " lancé");

        ExtractionBehavior extractionBehavior = new ExtractionBehavior(this,period);
        addBehaviour(extractionBehavior);

    }
}
