package by.bsuir.aiprp.client;

import by.bsuir.aiprp.ws.AwesomeWebService;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServiceClient extends JFrame implements ActionListener {

    private static List<Object> FILE_NAMES;
    private static int PREVIOUS_LINE = 0;
    private static final TextArea TEXT_AREA = new TextArea();
    private static final String URL = "http://localhost:1986/wss/hello?wsdl";
    private static final String NAMESPACE_URI = "http://ws.aiprp.bsuir.by/";
    private static final String LOCAL_PART = "WebServiceImplService";

    public ServiceClient() {
        super("Laba 4");
        JPanel contents = new JPanel();
        final JList<Object> files = new JList<>(getFileNames());
        files.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        files.addListSelectionListener(new ListSelectionListener());
        TEXT_AREA.setBounds(20, 50, 300, 300);
        contents.add(new JScrollPane(files));
        contents.add(TEXT_AREA);
        setContentPane(contents);
        setSize(600, 300);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    static class ListSelectionListener implements javax.swing.event.ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting()) {
                if (e.getFirstIndex() == PREVIOUS_LINE) {
                    PREVIOUS_LINE = e.getLastIndex();
                } else {
                    PREVIOUS_LINE = e.getFirstIndex();
                }
                String text = getFileText((String) FILE_NAMES.get(PREVIOUS_LINE));
                TEXT_AREA.setText(text);
            }
        }
    }

    public static void main(String[] args) {
        new ServiceClient();
    }

    public static Object[] getFileNames() {
        FILE_NAMES = new ArrayList<>();
        try {
            URL url = new URL(URL);
            QName qname = new QName(NAMESPACE_URI, LOCAL_PART);
            Service service = Service.create(url, qname);
            AwesomeWebService awesomeWebService = service.getPort(AwesomeWebService.class);
            List<Object> files = Arrays.asList(awesomeWebService.getFiles());
            FILE_NAMES.addAll(files);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return FILE_NAMES.toArray();
    }

    public static String getFileText(String fileName) {
        try {
            URL url = new URL(URL);
            QName qname = new QName(NAMESPACE_URI, LOCAL_PART);
            Service service = Service.create(url, qname);
            AwesomeWebService awesomeWebService = service.getPort(AwesomeWebService.class);

            return awesomeWebService.getFile(fileName);

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "";
        }
    }
}
