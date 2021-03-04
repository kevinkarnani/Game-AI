package ch.idsia.agents.controllers.behavior;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class BehaviorHandler extends DefaultHandler {
    ArrayList<Composite> compList = new ArrayList<>();
    public Composite tmp;
    String taskName;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("sequence")) {
            this.tmp = new Sequence(null);
            this.compList.add(this.tmp);
        } else if (qName.equalsIgnoreCase("selector")) {
            this.tmp = new Selector(null);
            this.compList.add(this.tmp);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("sequence") || qName.equalsIgnoreCase("selector")) {
            if (this.compList.size() > 1) {
                this.compList.get(this.compList.size() - 2).addTask(this.tmp);
                this.compList.remove(this.compList.size() - 1);
                this.tmp = this.compList.get(this.compList.size() - 1);
            }
        } else if (qName.equalsIgnoreCase("action")) {
            this.tmp.addTask(new Action(this.taskName));
        } else {
            this.tmp.addTask(new Condition(this.taskName));
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        this.taskName = new String(ch, start, length);
    }
}
