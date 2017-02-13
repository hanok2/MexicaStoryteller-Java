package slant;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.*;
import mexica.CharacterName;
import mexica.MexicaRepository;
import mexica.core.Action;
import mexica.core.Position;
import mexica.story.DeadAvatarException;
import mexica.story.Story;
import mexica.story.filter.StoryFilterException;
import mexica.tools.InvalidCharacterException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 * Class to read the xml output generated by the Slant program
 * @author Ivan Guerrero
 */
public class SlantXMLReader implements SlantXML {    
    private Document doc;
    /**
     * Reads an XML file generated by Slant containing constraints for a Mexica story
     */
    public Story readXML(String path) {
        Story story = new Story();
        try {
            File fXmlFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(fXmlFile);
            //Obtains the story element
            Element storyNode = (Element)doc.getElementsByTagName(MEXICA_STORY).item(0);
            story = createStory(storyNode);
            Element slantNode = (Element)doc.getElementsByTagName(SLANT_STORY).item(0);
            readConstraints(slantNode, story);
        } catch (SAXException | IOException | ParserConfigurationException | NullPointerException ex) {
            Logger.getGlobal().log(Level.SEVERE, "Error reading XML file {0}", ex.getMessage());
            throw new Error("Error reading XML file " + ex.getMessage());
        } 
        
        return story;
    }
    
    private Story createStory(Element storyNode) {
        Story story = new Story();
        
        //Reads the default location for the story
        String defaultLocation = storyNode.getAttribute(DEFAULT_LOCATION);
        story.setDefaultPosition(Position.valueOf(defaultLocation));
        
        //Obtains the action list element
        Element actionsNode = (Element)storyNode.getElementsByTagName(MEXICA_ACTIONS).item(0);
        //Reads the action in the story and adds them to the story object
        NodeList actions = actionsNode.getElementsByTagName(MEXICA_ACTION);
        for (int i=0; i<actions.getLength(); i++) {
            CharacterName performer, receiver;
            //Reads the action elements
            Element actionElem = (Element)actions.item(i);
            String actionID = actionElem.getAttribute(ACTION_ID);
            String name = actionElem.getAttribute(ACTION_NAME);
            String perf = actionElem.getAttribute(ACTION_PERFORMER);
            String rec = actionElem.getAttribute(ACTION_RECEIVER);
            
            //Obtains the Mexica mappings for the read elements
            Action action = MexicaRepository.getInstance().getActions().getAction(name);
            performer = CharacterName.valueOf(perf);
            try {
                if (rec != null && !rec.isEmpty()) {
                    receiver = CharacterName.valueOf(rec);
                    story.addAction(action, performer, receiver);
                }
                else
                    story.addAction(action, performer);
            } catch (InvalidCharacterException | DeadAvatarException | StoryFilterException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Error parsing action {0}, {1}", 
                                                     new Object[] {actionID, ex.getMessage()});
            }
        }
        
        return story;
    }    

    private void readConstraints(Element storyNode, Story story) {
        try {
            Element constraints = (Element)storyNode.getElementsByTagName(PLOTTING).item(0);
            NodeList prohibits = constraints.getElementsByTagName(PROHIBIT_ELEMENT);
            NodeList requireds = constraints.getElementsByTagName(REQUIRED_ELEMENT);
            for (int i=0; i<prohibits.getLength(); i++) {
                Element p = (Element)prohibits.item(i);
                String character = p.getAttribute(PROHIBIT_CHARACTER);
                String tension = p.getAttribute(PROHIBIT_TENSION);
                SlantTension slantTension = SlantTension.valueFromXML(tension);
                SlantGuidelines guidelines = (SlantGuidelines)story.getGuidelines();
                guidelines.addSlantGuideline(CharacterName.valueOfName(character), SlantTensionConverter.convert(slantTension), true);
            }

            for (int i=0; i<requireds.getLength(); i++) {
                Element r = (Element)requireds.item(i);
                String character = r.getAttribute(REQUIRED_CHARACTER);
                String tension = r.getAttribute(REQUIRED_TENSION);
                SlantTension slantTension = SlantTension.valueFromXML(tension);
                SlantGuidelines guidelines = (SlantGuidelines)story.getGuidelines();
                guidelines.addSlantGuideline(CharacterName.valueOfName(character), SlantTensionConverter.convert(slantTension), false);
            }
        } catch (NullPointerException npe) {
            Logger.getGlobal().log(Level.INFO, "No constraints found in slant file");
        }
    }
    
    public Document getDocument() {
        return doc;
    }
}