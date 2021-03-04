import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class FileHandler {
    XMLStreamWriter xsw;
    FileWriter fw;

    public void changeMap(int startX, int startY) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader br = new BufferedReader(new FileReader("games/example-game/example.xml"));

        while ((line = br.readLine()) != null) {
            if (line.contains(" x=\"")) {
                line = line.replaceAll("x=\"\\d+\"", "x=\"" + startX + "\"");
                line = line.replaceAll("y=\"\\d+\"", "y=\"" + startY + "\"");
            }
            sb.append(line).append("\n");
        }
        br.close();

        BufferedWriter bw = new BufferedWriter(new FileWriter("games/example-game/example.xml"));
        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    public void writeToFile(int startX, int startY, int[][] map) {
        String file = "games/example-game/P5.tmx";

        try {
            this.changeMap(startX, startY);
            this.fw = new FileWriter(file);
            this.xsw = XMLOutputFactory.newInstance().createXMLStreamWriter(this.fw);
            this.beginFile(map);
            this.writeLayerData(map);
            this.fw.write("</map>");
            this.xsw.writeEndDocument();
            this.xsw.flush();
            this.format(file);
        } catch (Exception e) {
            System.err.println("Unable to write to the file: " + e.getMessage());
        } finally {
            try {
                if (this.xsw != null) {
                    this.xsw.close();
                }
            } catch (Exception e) {
                System.err.println("Unable to close the file: " + e.getMessage());
            }
        }
    }

    public void beginFile(int[][] map) throws IOException {
        this.fw.write("<map version=\"1.0\" orientation=\"orthogonal\" width=\"" + map.length + "\" height=\"" +
                map[0].length + "\" tilewidth=\"32\" tileheight=\"32\">");
        this.fw.write("<properties>");
        this.fw.write("<property name=\"name\" value=\"Blackrock\"/>");
        this.fw.write("</properties>");
        this.fw.write("<tileset firstgid=\"1\" name=\"graphics\" tilewidth=\"32\" tileheight=\"32\">");
        this.fw.write("<image source=\"graphics.png\" width=\"320\" height=\"1184\"/>");
        this.fw.write("</tileset>");
    }

    public void writeLayerData(int[][] map) throws IOException {
        this.fw.write("<layer name=\"Tile Layer 1\" width=\"" + map.length + "\" height=\"" + map[0].length + "\">");
        this.fw.write("<data>");
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                this.fw.write("<tile gid=\"" + Objects.LAND_TILE.getValue() + "\"/>");
            }
        }
        this.fw.write("</data>");
        this.fw.write("</layer>");
        this.fw.write("<layer name=\"Tile Layer 2\" width=\"" + map.length + "\" height=\"" + map[0].length + "\">");
        this.fw.write("<data>");
        for (int[] row : map) {
            for (int val : row) {
                this.fw.write("<tile gid=\"" + val + "\"/>");
            }
        }
        this.fw.write("</data>");
        this.fw.write("</layer>");
    }

    public void format(String file) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new InputStreamReader(new FileInputStream(file))));

        Transformer xformer = TransformerFactory.newInstance().newTransformer();
        xformer.setOutputProperty(OutputKeys.METHOD, "xml");
        xformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        xformer.setOutputProperty(OutputKeys.INDENT, "yes");
        Source source = new DOMSource(document);
        Result result = new StreamResult(new File(file));
        xformer.transform(source, result);
    }
}
