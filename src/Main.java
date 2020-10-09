import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class Main {

    public static void main(String[] args){

        Runtime runtime = Runtime.instance();
        Profile profile = new ProfileImpl();

        profile.setParameter(Profile.MAIN_HOST,"localhost");
        profile.setParameter(Profile.GUI,"true");
        ContainerController containerController = runtime.createMainContainer(profile);
        AgentController agentControllerAE;
        AgentController agentControllerAF;
        AgentController agentControllerAI;
        AgentController agentControllerAS;
        try {
            agentControllerAE = containerController.createNewAgent("Agent Extracteur","Agents.AgentExtracteur",null);
            agentControllerAF = containerController.createNewAgent("Agent Filtreur","Agents.AgentFiltreur",null);
            agentControllerAI = containerController.createNewAgent("Agent Interaction","Agents.AgentInteraction",null);
            agentControllerAS = containerController.createNewAgent("Agent Stockage","Agents.AgentStockage",null);
            agentControllerAE.start();
            agentControllerAF.start();
            agentControllerAI.start();
            agentControllerAS.start();

        } catch (StaleProxyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
