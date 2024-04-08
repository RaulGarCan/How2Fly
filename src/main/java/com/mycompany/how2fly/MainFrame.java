/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.how2fly;

import com.google.gson.Gson;
import com.mycompany.how2fly.pojo.BestFlights;
import com.mycompany.how2fly.pojo.OtherFlights;
import com.mycompany.how2fly.pojo.Response;
import com.mycompany.how2fly.pojo.frontend.FlightDetails;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author Alumno
 */
public class MainFrame extends javax.swing.JFrame {

    JPanel bottomPanel;
    private JTextField tfFrom, tfTo, tfGoing, tfReturn;
    private JComboBox cbPassenger, cbType;
    public static Font defaultFontSize5Bold, defaultFontSize2, defaultFontSize5;
    private final String rutaExample = "./src/main/java/com/mycompany/how2fly/data/example.json";
    private final String rutaCache = "./src/main/java/com/mycompany/how2fly/cache/cache.json";
    private JPanel homePanel;
    private FlightDetailsPanel flightDetailsPanel;
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        start();
    }

    private void start() {
        this.setBounds(0, 0, 500, 500);

        createHomePanel();

        GridLayout frameLayout = new GridLayout(0, 1);
        this.setLayout(frameLayout);
        this.add(homePanel);

        this.setExtendedState(MAXIMIZED_BOTH);
    }

    private boolean checkFields(JTextField[] tfs) {
        if (!fieldsFilled(tfs)) {
            return false;
        }
        if (!fieldsFromToDiff(tfFrom, tfTo)) {
            return false;
        }
        if (!returnDateHigerOrEqualToGoing(tfGoing, tfReturn)) {
            return false;
        }
        return true;
    }

    private boolean fieldsFilled(JTextField[] tfs) {
        for (JTextField tf : tfs) {
            if (tf.getText().isBlank()) {
                return false;
            }
        }
        return true;
    }

    private boolean fieldsFromToDiff(JTextField tfFrom, JTextField tfTo) {
        return !tfFrom.getText().equals(tfTo.getText());
    }

    private boolean returnDateHigerOrEqualToGoing(JTextField tfGoing, JTextField tfReturn) {
        LocalDate goingDate = parseDate(tfGoing.getText());
        LocalDate returnDate = parseDate(tfReturn.getText());
        if (goingDate.until(returnDate).isNegative()) {
            return false;
        }
        return true;
    }

    private LocalDate parseDate(String date) {
        String[] data = date.split("-");
        if (data.length == 0) {
            data = date.split("/");
        }
        if (data.length == 0) {
            return LocalDate.now();
        }
        return LocalDate.of(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]));
    }

    private ArrayList<FlightDetails> getFrontEndDetails(ArrayList<BestFlights> bestFlights, ArrayList<OtherFlights> otherFlights) {
        ArrayList<FlightDetails> flightDetails = new ArrayList<>();
        if (bestFlights != null) {
            for (BestFlights flights : bestFlights) {
                flightDetails.add(new FlightDetails(flights));
            }
        }
        for (OtherFlights flights : otherFlights) {
            flightDetails.add(new FlightDetails(flights));
        }
        return flightDetails;
    }

    private void createHomePanel() {
        homePanel = new JPanel();
        homePanel.setBackground(Color.GREEN);

        GridBagLayout homeLayout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        homePanel.setLayout(homeLayout);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 1;
        constraints.weighty = 0;
        constraints.fill = GridBagConstraints.BOTH;
        homePanel.add(setupTopPanel(), constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridheight = 3;
        constraints.gridwidth = 1;
        constraints.weighty = 1;
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.BOTH;
        homePanel.add(setupBottomPanel(getScrollPanelElements()), constraints);
    }

    private JPanel setupTopPanel() {
        Dimension dim = new Dimension(this.getSize().width / 8, 25);
        JPanel topPanel = new JPanel();
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(Color.blue);
        topPanel.setLayout(new GridLayout(2, 7));

        // Top Row
        JLabel lbFrom = new JLabel("From:");

        defaultFontSize5Bold = new Font(lbFrom.getFont().getName(), Font.BOLD, lbFrom.getFont().getSize() + 5);
        defaultFontSize5 = new Font(lbFrom.getFont().getName(), lbFrom.getFont().getStyle(), lbFrom.getFont().getSize() + 5);
        defaultFontSize2 = new Font(lbFrom.getFont().getName(), lbFrom.getFont().getStyle(), lbFrom.getFont().getSize() + 2);

        lbFrom.setFont(defaultFontSize5Bold);
        lbFrom.setSize(dim);
        lbFrom.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        lbFrom.setPreferredSize(dim);
        topPanel.add(lbFrom);

        JLabel lbTo = new JLabel("To:");
        lbTo.setFont(defaultFontSize5Bold);
        topPanel.add(lbTo);

        JLabel lbGoing = new JLabel("Going:");
        lbGoing.setFont(defaultFontSize5Bold);
        topPanel.add(lbGoing);

        JLabel lbReturn = new JLabel("Return:");
        lbReturn.setFont(defaultFontSize5Bold);
        topPanel.add(lbReturn);

        JLabel lbPassenger = new JLabel("Passenger:");
        lbPassenger.setFont(defaultFontSize5Bold);
        topPanel.add(lbPassenger);

        JLabel lbType = new JLabel("Type:");
        lbType.setFont(defaultFontSize5Bold);
        topPanel.add(lbType);

        topPanel.add(new JLabel());

        // Bottom Row
        tfFrom = new JTextField();
        tfFrom.setFont(defaultFontSize5);
        tfFrom.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tfFrom.setToolTipText("From");
        topPanel.add(tfFrom);

        tfTo = new JTextField();
        tfTo.setFont(defaultFontSize5);
        tfTo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tfTo.setToolTipText("To");
        topPanel.add(tfTo);

        tfGoing = new JTextField();
        tfGoing.setFont(defaultFontSize5);
        tfGoing.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tfGoing.setToolTipText("Going");
        topPanel.add(tfGoing);

        tfReturn = new JTextField();
        tfReturn.setFont(defaultFontSize5);
        tfReturn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tfReturn.setToolTipText("Return");
        topPanel.add(tfReturn);

        cbPassenger = new JComboBox<String>();
        cbPassenger.setFont(defaultFontSize5);
        cbPassenger.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        for (int i = 1; i <= 10; i++) {
            cbPassenger.addItem(i);
        }
        cbPassenger.setToolTipText("Passenger");
        topPanel.add(cbPassenger);

        cbType = new JComboBox();
        cbType.setFont(defaultFontSize5);
        cbType.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        cbType.setToolTipText("Type");
        cbType.addItem("Round Trip");
        cbType.addItem("One Way");
        cbType.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (cbType.getSelectedItem().equals("One Way")) {
                    tfReturn.setEnabled(false);
                } else {
                    if (!tfReturn.isEnabled()) {
                        tfReturn.setEnabled(true);
                    }
                }
            }
        });
        topPanel.add(cbType);

        JButton btnSearch = new JButton("Search");
        btnSearch.setFont(defaultFontSize5);
        btnSearch.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnSearch.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTextField[] tfs = {tfFrom, tfTo, tfGoing, tfReturn};
                if (checkFields(tfs)) {
                    String from = tfFrom.getText();
                    String to = tfTo.getText();
                    String going = tfGoing.getText();
                    String returnal = null;
                    if (tfReturn.isEnabled()) {
                        returnal = tfReturn.getText();
                    }
                    String passengers = cbPassenger.getSelectedItem().toString();
                    String type = cbType.getSelectedItem().toString();

                    homePanel.remove(bottomPanel);
                    ArrayList<JPanel> scrollElements = getScrollPanelElements(from, to, going, returnal, type, passengers);
                    bottomPanel = MainFrame.this.setupBottomPanel(scrollElements);
                    GridBagConstraints c = new GridBagConstraints();
                    c.gridx = 0;
                    c.gridy = 1;
                    c.gridheight = 3;
                    c.gridwidth = 1;
                    c.weighty = 1;
                    c.weightx = 1;
                    c.fill = GridBagConstraints.BOTH;
                    homePanel.add(bottomPanel, c);
                    
                    MainFrame.this.revalidate();
                    MainFrame.this.repaint();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        btnSearch.setToolTipText("Search");
        topPanel.add(btnSearch);

        return topPanel;
    }

    private JPanel setupBottomPanel(ArrayList<JPanel> scrollElements) {
        bottomPanel = new JPanel();
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPanel.setBackground(Color.red);
        JPanel scrollInsidePanel = new JPanel();
        scrollInsidePanel.setBackground(Color.white);
        scrollInsidePanel.setLayout(new BoxLayout(scrollInsidePanel, BoxLayout.Y_AXIS));

        for (JPanel p : scrollElements) {
            p.setPreferredSize(new Dimension(1, 227));
            scrollInsidePanel.add(p);
        }

        JScrollPane scrollPanel = new JScrollPane(scrollInsidePanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPanel.setBackground(Color.yellow);

        JScrollBar verticalScroll = scrollPanel.getVerticalScrollBar();
        verticalScroll.setUnitIncrement(20);
        verticalScroll.setPreferredSize(new Dimension(verticalScroll.getPreferredSize().width - 5, verticalScroll.getPreferredSize().height));
        scrollPanel.setVerticalScrollBar(verticalScroll);

        JPanel filtersPanel = new JPanel();
        filtersPanel.setBackground(Color.cyan);
        filtersPanel.setLayout(new GridLayout(0, 1));

        GridBagLayout panelLayout = new GridBagLayout();
        bottomPanel.setLayout(panelLayout);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 3;
        constraints.gridheight = 1;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        bottomPanel.add(scrollPanel, constraints);

        constraints.gridx = 3;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 0.5;
        constraints.weighty = 1;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.fill = GridBagConstraints.BOTH;
        bottomPanel.add(filtersPanel, constraints);

        ArrayList<String> filters = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            filters.add("Filter " + i);
        }
        addFilters(filtersPanel, filters);

        return bottomPanel;
    }

    private ArrayList<JPanel> getScrollPanelElements() {
        // Based on API response when searched returns a list of
        // panels matching the number of flights returned
        ArrayList<JPanel> tmp = new ArrayList<>();

        Response r = parsearJSON(leerJSON(rutaCache));

        ArrayList<FlightDetails> flightDetails = getFrontEndDetails(r.getBest_flights(), r.getOther_flights());

        for (FlightDetails f : flightDetails) {
            tmp.add(new FlightListElementPanel(this, homePanel, f));
        }

        return tmp;
    }

    private ArrayList<JPanel> getScrollPanelElements(String from, String to, String going, String returnal, String type, String passengers) {
        // Based on API response when searched returns a list of
        // panels matching the number of flights returned
        ArrayList<JPanel> tmp = new ArrayList<>();

        peticionAPI(from, to, going, returnal, "EUR", type, passengers);
        Response r = parsearJSON(leerJSON(rutaExample));

        ArrayList<FlightDetails> flightDetails = getFrontEndDetails(r.getBest_flights(), r.getOther_flights());

        for (FlightDetails f : flightDetails) {
            tmp.add(new FlightListElementPanel(this, homePanel, f));
        }

        return tmp;
    }

    private void addFilters(JPanel filtersPanel, ArrayList<String> filters) {
        for (String f : filters) {
            filtersPanel.add(new JCheckBox(f));
        }
    }

    public JPanel getHomePanel() {
        return homePanel;
    }

    private void peticionAPI(String departureId, String arrivalId, String departureDate, String returnDate, String currency, String type, String passengers) {
        if (type.equalsIgnoreCase("Round Trip")) {
            type = "1";
        } else {
            type = "2";
        }
        try {

            String engine = "google_flights";
            String api_key = "cb86689dbb1f68e6363ba7c5ecf4604177d0e21cd801037bd8e35ef77a43e271";

            String link = "https://serpapi.com/search.json?engine=" + engine + "&departure_id=" + departureId + "&arrival_id=" + arrivalId + "&gl=us&hl=en&adults=" + passengers;
            if (currency != null && !currency.isBlank()) {
                link += "&currency=" + currency;
            }
            link += "&type=" + type;
            if (departureDate != null && !departureDate.isBlank()) {
                link += "&outbound_date=" + departureDate;
            }
            if (returnDate != null && !returnDate.isBlank()) {
                link += "&return_date=" + returnDate;
            }
            link += "&api_key=" + api_key;

            System.out.println(link);
            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode());
            }
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String output;
            String result = "";
            while ((output = br.readLine()) != null) {
                result += output;
            }
            System.out.println(result);
            guardarJSON(result);
            conn.disconnect();
        } catch (Exception e) {
            System.out.println("Exception in NetClientGet:- " + e);
        }
    }

    private void guardarJSON(String data) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(rutaExample));
            writer.write(data);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Response parsearJSON(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Response.class);
    }

    private String leerJSON(String ruta) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(ruta));
            String line;
            String result = "";
            while ((line = reader.readLine()) != null) {
                result += line;
            }
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
