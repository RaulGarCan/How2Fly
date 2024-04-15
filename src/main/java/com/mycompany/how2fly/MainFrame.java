/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.how2fly;

import com.google.gson.Gson;
import static com.mycompany.how2fly.Main.leerJSON;
import com.mycompany.how2fly.pojo.BestFlights;
import com.mycompany.how2fly.pojo.Flight;
import com.mycompany.how2fly.pojo.OtherFlights;
import com.mycompany.how2fly.pojo.PriceInsights;
import com.mycompany.how2fly.pojo.Response;
import com.mycompany.how2fly.pojo.frontend.AirportList;
import com.mycompany.how2fly.pojo.frontend.FlightDetails;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
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
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import com.mycompany.how2fly.pojo.filters.FlightFilters;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Alumno
 */
public class MainFrame extends javax.swing.JFrame {

    private ButtonGroup bgDayTime, bgLayovers;
    private JCheckBox cbApplyPrice;
    private int priceSelected;
    private JPanel filtersPanel, scrollInsidePanel;
    private ArrayList<FlightListElementPanel> scrollPanelList;
    private JScrollPane scrollPanel;
    private JPanel bottomPanel;
    private ArrayList<FlightFilters> activeFilters;
    private int maxPrice;
    private JSlider slPriceRange;
    private PriceInsights priceInsights;
    private static String priceLevel;
    private JLabel lbPriceLevel, lbPriceSelected;
    private ArrayList<FlightDetails> flights;
    private ArrayList<FlightDetails> filteredFlights;
    private JTextField tfGoing, tfReturn;
    private JComboBox cbPassenger, cbType, cbFrom, cbTo;
    public static Font defaultFontSize5Bold, defaultFontSize2, defaultFontSize5, defaultFontHeader, defaultFontHeaderBold;
    public static final String PATHEXAMPLE = "./src/main/java/com/mycompany/how2fly/data/example.json";
    public static final String PATHCACHE = "./src/main/java/com/mycompany/how2fly/cache/cache.json";
    public static final String PATHCACHEAIRPORT = "./src/main/java/com/mycompany/how2fly/cache/airports.json";
    private JPanel homePanel;
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        start();
    }

    private void start() {
        activeFilters = new ArrayList<>();

        this.setBounds(0, 0, 500, 500);

        slPriceRange = new JSlider();
        lbPriceSelected = new JLabel("0");
        filteredFlights = new ArrayList<>();

        bgDayTime = new ButtonGroup();
        bgLayovers = new ButtonGroup();

        createHomePanel();

        GridLayout frameLayout = new GridLayout(0, 1);
        this.setLayout(frameLayout);
        this.add(homePanel);

        this.setExtendedState(MAXIMIZED_BOTH);
    }

    private void updateCache() {
        LocalDateTime dateTime = FlightListElementPanel.parseDateTime(flights.getFirst().getFlights().getFirst().getDeparture_airport().getTime().split(" "));
        LocalDate now = LocalDate.now();
        if (!now.getMonth().equals(dateTime.getMonth())) {
            peticionAPI("CDG", "AUS", "", "", "EUR", PATHCACHE, priceLevel, PATHCACHE);
        }
    }

    private void fillAirports(JComboBox<String> cb) {
        Gson gson = new Gson();
        AirportList[] airports = gson.fromJson(leerJSON(MainFrame.PATHCACHEAIRPORT), AirportList[].class);
        for (AirportList a : airports) {
            cb.addItem(a.getIataCode());
        }
    }

    private boolean checkFields(JTextField[] tfs) {
        if (!fieldsFilled(tfs)) {
            return false;
        }
        if (!fieldsFromToDiff(cbFrom, cbTo)) {
            return false;
        }
        if (!checkDateFormat(tfGoing)) {
            tfGoing.setForeground(Color.red);
            return false;
        } else {
            tfGoing.setForeground(Color.black);
        }
        if (!checkDateFormat(tfReturn)) {
            tfReturn.setForeground(Color.red);
            return false;
        } else {
            tfReturn.setForeground(Color.black);
        }
        if (!returnDateHigerOrEqualToGoing(tfGoing, tfReturn)) {
            return false;
        }
        return true;
    }

    private boolean checkDateFormat(JTextField tfDate) {
        String[] dateParts = tfDate.getText().split("-");
        if (dateParts.length != 3) {
            return false;
        }
        if (dateParts[0].length() != 4) {
            return false;
        }
        if (dateParts[1].length() != 2 || dateParts[2].length() != 2) {
            return false;
        }
        try {
            LocalDate test = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
            System.out.println(test.toString());
        } catch (DateTimeException | NumberFormatException ex) {
            System.out.println(ex);
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

    private boolean fieldsFromToDiff(JComboBox cbFrom, JComboBox cbTo) {
        return !cbFrom.getSelectedItem().toString().equals(cbTo.getSelectedItem().toString());
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
            for (BestFlights f : bestFlights) {
                flightDetails.add(new FlightDetails(f));
            }
        }
        for (OtherFlights f : otherFlights) {
            flightDetails.add(new FlightDetails(f));
        }
        return flightDetails;
    }

    private void createHomePanel() {
        JLabel lbPriceLvl = new JLabel("Price Level: ");
        lbPriceLevel = new JLabel();
        lbPriceLevel.setFont(defaultFontHeaderBold);
        homePanel = new JPanel();
        homePanel.setBackground(Color.GREEN);

        GridBagLayout homeLayout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        homePanel.setLayout(homeLayout);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 3;
        constraints.gridheight = 1;
        constraints.weightx = 1;
        constraints.weighty = 0;
        constraints.fill = GridBagConstraints.BOTH;
        homePanel.add(setupTopPanel(), constraints);

        lbPriceLvl.setFont(defaultFontHeaderBold);
        lbPriceLevel.setFont(defaultFontHeaderBold);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridheight = 3;
        constraints.gridwidth = 3;
        constraints.weighty = 1;
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.BOTH;
        homePanel.add(setupBottomPanel(getScrollPanelElements(), false), constraints);

        for (FlightDetails f : flights) {
            filteredFlights.add(f);
        }

        constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 0);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridheight = 1;
        constraints.gridwidth = 1;
        homePanel.add(lbPriceLvl, constraints);

        constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 0, 10, 0);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridheight = 1;
        constraints.gridwidth = 1;
        homePanel.add(lbPriceLevel, constraints);
    }

    private JPanel setupTopPanel() {
        Dimension dim = new Dimension(this.getSize().width / 7, 25);
        JPanel topPanel = new JPanel();
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(Color.blue);
        topPanel.setLayout(new GridLayout(2, 7));

        // Top Row
        JLabel lbFrom = new JLabel("From:");

        defaultFontSize5Bold = new Font(lbFrom.getFont().getName(), Font.BOLD, lbFrom.getFont().getSize() + 5);
        defaultFontSize5 = new Font(lbFrom.getFont().getName(), lbFrom.getFont().getStyle(), lbFrom.getFont().getSize() + 5);
        defaultFontSize2 = new Font(lbFrom.getFont().getName(), lbFrom.getFont().getStyle(), lbFrom.getFont().getSize() + 2);
        defaultFontHeaderBold = new Font(lbFrom.getFont().getName(), Font.BOLD, lbFrom.getFont().getSize() + 10);
        defaultFontHeader = new Font(lbFrom.getFont().getName(), lbFrom.getFont().getStyle(), lbFrom.getFont().getSize() + 10);

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
        cbFrom = new JComboBox();
        fillAirports(cbFrom);
        cbFrom.setFont(defaultFontSize5);
        cbFrom.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        cbFrom.setToolTipText("Departure Airport");
        topPanel.add(cbFrom);

        cbTo = new JComboBox();
        fillAirports(cbTo);
        cbTo.setFont(defaultFontSize5);
        cbTo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        cbTo.setToolTipText("Arrival Airport");
        topPanel.add(cbTo);

        tfGoing = new JTextField();
        tfGoing.setFont(defaultFontSize5);
        tfGoing.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tfGoing.setToolTipText("Departure Date: yyyy-MM-dd");
        topPanel.add(tfGoing);

        tfReturn = new JTextField();
        tfReturn.setFont(defaultFontSize5);
        tfReturn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tfReturn.setToolTipText("Return Date: yyyy-MM-dd");
        topPanel.add(tfReturn);

        cbPassenger = new JComboBox<String>();
        cbPassenger.setFont(defaultFontSize5);
        cbPassenger.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        for (int i = 1; i <= 10; i++) {
            cbPassenger.addItem(i);
        }
        cbPassenger.setToolTipText("Passengers Number");
        topPanel.add(cbPassenger);

        cbType = new JComboBox();
        cbType.setFont(defaultFontSize5);
        cbType.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        cbType.setToolTipText("Trip Type");
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
                JTextField[] tfs = {tfGoing, tfReturn};
                if (checkFields(tfs)) {
                    String from = cbFrom.getSelectedItem().toString();
                    String to = cbTo.getSelectedItem().toString();
                    String going = tfGoing.getText();
                    String returnal = null;
                    if (tfReturn.isEnabled()) {
                        returnal = tfReturn.getText();
                    }
                    String passengers = cbPassenger.getSelectedItem().toString();
                    String type = cbType.getSelectedItem().toString();

                    homePanel.remove(bottomPanel);
                    ArrayList<FlightListElementPanel> scrollElements = getScrollPanelElements(from, to, going, returnal, type, passengers);
                    bottomPanel = MainFrame.this.setupBottomPanel(scrollElements, false);
                    GridBagConstraints c = new GridBagConstraints();
                    c.gridx = 0;
                    c.gridy = 2;
                    c.gridheight = 3;
                    c.gridwidth = 3;
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
        btnSearch.setToolTipText("Press to Search");
        Image searchImg = FlightListElementPanel.rescaleImage(FlightListElementPanel.createImageWithURL("https://cdn-icons-png.flaticon.com/512/751/751381.png"), 25, 25);
        btnSearch.setIcon(new ImageIcon(searchImg));
        topPanel.add(btnSearch);

        return topPanel;
    }

    private JPanel setupBottomPanel(ArrayList<FlightListElementPanel> scrollElements, boolean keepFilters) {
        bottomPanel = new JPanel();
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPanel.setBackground(Color.red);

        if (!keepFilters) {
            scrollPanel = createScrollPanel(scrollElements);
        }

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

        if (!keepFilters) {
            filtersPanel = new JPanel();
            filtersPanel.setBackground(Color.cyan);
            filtersPanel.setLayout(new GridLayout(1, 2));

            ArrayList<FlightFilters> filters = new ArrayList<>();
            for (FlightFilters f : FlightFilters.values()) {
                filters.add(f);
            }
            addFilters(filtersPanel, filters);
        }

        constraints.gridx = 3;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 0.15;
        constraints.weighty = 1;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.fill = GridBagConstraints.BOTH;
        bottomPanel.add(filtersPanel, constraints);

        return bottomPanel;
    }

    private JScrollPane createScrollPanel(ArrayList<FlightListElementPanel> scrollElements) {
        scrollPanelList = new ArrayList<>();
        scrollInsidePanel = new JPanel();
        scrollInsidePanel.setBackground(Color.white);
        scrollInsidePanel.setLayout(new BoxLayout(scrollInsidePanel, BoxLayout.Y_AXIS));

        for (FlightListElementPanel p : scrollElements) {
            p.setPreferredSize(new Dimension(1, 227));
            scrollInsidePanel.add(p);
            scrollPanelList.add(p);
        }

        scrollPanel = new JScrollPane(scrollInsidePanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPanel.setBackground(Color.yellow);

        JScrollBar verticalScroll = scrollPanel.getVerticalScrollBar();
        verticalScroll.setUnitIncrement(20);
        verticalScroll.setPreferredSize(new Dimension(verticalScroll.getPreferredSize().width - 5, verticalScroll.getPreferredSize().height));
        scrollPanel.setVerticalScrollBar(verticalScroll);
        return scrollPanel;
    }

    private void updateScrollPanel(ArrayList<FlightDetails> flights) {
        for (FlightListElementPanel p : scrollPanelList) {
            p.setVisible(false);
            for (FlightDetails f : flights) {
                if (p.getFlightDetails().equals(f)) {
                    p.setVisible(true);
                }
            }
        }
        MainFrame.this.revalidate();
        MainFrame.this.repaint();
    }

    private void changePriceLevelColor() {
        switch (priceLevel.toLowerCase()) {
            case "low":
                lbPriceLevel.setForeground(Color.GREEN);
                break;
            case "typical":
                lbPriceLevel.setForeground(Color.ORANGE);
                break;
            case "high":
                lbPriceLevel.setForeground(Color.RED);
                break;
        }
    }

    private ArrayList<FlightListElementPanel> getScrollPanelElements() {
        // Based on API response when searched returns a list of
        // panels matching the number of flights returned
        ArrayList<FlightListElementPanel> tmp = new ArrayList<>();

        Response r = parsearJSON(leerJSON(PATHCACHE));
        priceInsights = r.getPrice_insights();
        priceLevel = r.getPrice_insights().getPrice_level();
        lbPriceLevel.setText(priceLevel);
        changePriceLevelColor();

        System.out.println(r.getPrice_insights());

        flights = getFrontEndDetails(r.getBest_flights(), r.getOther_flights());

        getPriceRange();

        for (FlightDetails f : flights) {
            tmp.add(new FlightListElementPanel(this, homePanel, f));
        }

        return tmp;
    }

    private ArrayList<FlightListElementPanel> getScrollPanelElements(String from, String to, String going, String returnal, String type, String passengers) {
        // Based on API response when searched returns a list of
        // panels matching the number of flights returned
        ArrayList<FlightListElementPanel> tmp = new ArrayList<>();

        peticionAPI(from, to, going, returnal, "EUR", type, passengers, PATHEXAMPLE);
        Response r = parsearJSON(leerJSON(PATHEXAMPLE));
        priceLevel = r.getPrice_insights().getPrice_level();
        lbPriceLevel.setText("Price Level: " + priceLevel);
        changePriceLevelColor();

        flights = getFrontEndDetails(r.getBest_flights(), r.getOther_flights());

        getPriceRange();

        for (FlightDetails f : flights) {
            tmp.add(new FlightListElementPanel(this, homePanel, f));
        }

        return tmp;
    }

    private void addFilters(JPanel filtersPanel, ArrayList<FlightFilters> filters) {
        JPanel leftCol = new JPanel();
        leftCol.setLayout(new GridLayout(0, 1));
        leftCol.setBackground(Color.PINK);
        JPanel rightCol = new JPanel();
        rightCol.setLayout(new GridLayout(0, 1));
        rightCol.setBackground(Color.cyan);

        ArrayList<JRadioButton> rbs = new ArrayList<>();
        ArrayList<JCheckBox> cbs = new ArrayList<>();

        int counter = 0;
        JLabel lbLayover = new JLabel("Layover Filters");
        lbLayover.setFont(defaultFontSize5Bold);
        leftCol.add(lbLayover);
        for (int i = counter; i < 4; i++) {
            JRadioButton rbLayover = new JRadioButton(filters.get(i).getDisplayedName());
            rbLayover.setFont(defaultFontSize2);
            rbs.add(rbLayover);
            bgLayovers.add(rbLayover);
            leftCol.add(rbLayover);
        }

        counter = 4;
        JLabel lbDayTime = new JLabel("Day Time Filters");
        lbDayTime.setFont(defaultFontSize5Bold);
        leftCol.add(lbDayTime);
        for (int i = counter; i < 7; i++) {
            JRadioButton rbDayTime = new JRadioButton(filters.get(i).getDisplayedName());
            rbDayTime.setFont(defaultFontSize2);
            rbs.add(rbDayTime);
            leftCol.add(rbDayTime);
            bgDayTime.add(rbDayTime);
        }

        counter = 7;
        JLabel lbPriceRange = new JLabel(filters.get(counter).getDisplayedName());
        lbPriceRange.setFont(defaultFontSize5Bold);
        leftCol.add(lbPriceRange);
        lbPriceSelected.setFont(defaultFontSize2);
        int nDigits = String.valueOf(priceInsights.getLowest_price()).length();
        String s = "1";
        for (int i = 0; i < nDigits; i++) {
            s += "0";
        }

        JPanel panelSlider = new JPanel();
        panelSlider.setOpaque(false);
        GridBagLayout sliderLayout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        panelSlider.setLayout(sliderLayout);
        slPriceRange.setFont(defaultFontSize2);
        slPriceRange.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                lbPriceSelected.setText("Up to " + slPriceRange.getValue());
                priceSelected = slPriceRange.getValue();
                cbApplyPrice.setSelected(false);
                activeFilters.remove(FlightFilters.PRICERANGE);
            }
        });

        leftCol.add(lbPriceSelected);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.weightx = 1;
        c.fill = GridBagConstraints.BOTH;

        panelSlider.add(slPriceRange, c);

        c.gridx = 4;
        c.gridy = 0;
        c.weightx = 0.15;

        cbApplyPrice = new JCheckBox("Apply");
        cbApplyPrice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cbApplyPrice.isSelected()) {
                    activeFilters.add(FlightFilters.PRICERANGE);
                } else {
                    activeFilters.remove(FlightFilters.PRICERANGE);
                }
                loadFilters();

            }
        });
        panelSlider.add(cbApplyPrice, c);

        leftCol.add(panelSlider);

        counter++;

        JLabel lbOvernightAndDelayed = new JLabel("Overnights and Delays");
        lbOvernightAndDelayed.setFont(defaultFontSize5Bold);
        rightCol.add(lbOvernightAndDelayed);

        for (int i = counter; i < 10; i++) {
            JCheckBox cbOvernightAndDelayed = new JCheckBox(filters.get(i).getDisplayedName());
            cbOvernightAndDelayed.setFont(defaultFontSize2);
            cbs.add(cbOvernightAndDelayed);
            rightCol.add(cbOvernightAndDelayed);
        }
        for (int i = 0; i < 7; i++) {
            rightCol.add(new JLabel());
        }
        filtersListeners(rbs, cbs);

        JButton btnClearFilters = new JButton("Clear Filters");
        btnClearFilters.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bgDayTime.clearSelection();
                bgLayovers.clearSelection();

                activeFilters.clear();
                loadFilters();
                for (JCheckBox cb : cbs) {
                    cb.setSelected(false);
                }
                cbApplyPrice.setSelected(false);
            }
        });

        rightCol.add(btnClearFilters);

        filtersPanel.add(leftCol);
        filtersPanel.add(rightCol);
    }

    private void filtersListeners(ArrayList<JRadioButton> rbs, ArrayList<JCheckBox> cbs) {
        rbs.get(0).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activeFilters.remove(FlightFilters.LAYOVER2);
                activeFilters.remove(FlightFilters.LAYOVER3);
                activeFilters.remove(FlightFilters.LAYOVERS);
                activeFilters.add(FlightFilters.LAYOVER1);
                loadFilters();
            }
        });
        rbs.get(1).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activeFilters.remove(FlightFilters.LAYOVER1);
                activeFilters.remove(FlightFilters.LAYOVER3);
                activeFilters.remove(FlightFilters.LAYOVERS);
                activeFilters.add(FlightFilters.LAYOVER2);
                loadFilters();
            }
        });
        rbs.get(2).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activeFilters.remove(FlightFilters.LAYOVER1);
                activeFilters.remove(FlightFilters.LAYOVER2);
                activeFilters.remove(FlightFilters.LAYOVERS);
                activeFilters.add(FlightFilters.LAYOVER3);
                loadFilters();
            }
        });
        rbs.get(3).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activeFilters.remove(FlightFilters.LAYOVER1);
                activeFilters.remove(FlightFilters.LAYOVER2);
                activeFilters.remove(FlightFilters.LAYOVER3);
                activeFilters.add(FlightFilters.LAYOVERS);
                loadFilters();
            }
        });

        rbs.get(4).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activeFilters.remove(FlightFilters.MIDDURATION);
                activeFilters.remove(FlightFilters.LONGDURATION);
                activeFilters.add(FlightFilters.SHORTDURATION);
                loadFilters();
            }
        });
        rbs.get(5).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activeFilters.remove(FlightFilters.SHORTDURATION);
                activeFilters.remove(FlightFilters.LONGDURATION);
                activeFilters.add(FlightFilters.MIDDURATION);
                loadFilters();
            }
        });
        rbs.get(6).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activeFilters.remove(FlightFilters.SHORTDURATION);
                activeFilters.remove(FlightFilters.MIDDURATION);
                activeFilters.add(FlightFilters.LONGDURATION);
                loadFilters();
            }
        });
        cbs.get(0).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cbs.get(0).isSelected()) {
                    activeFilters.add(FlightFilters.OVERNIGHT);
                } else {
                    activeFilters.remove(FlightFilters.OVERNIGHT);
                }
                loadFilters();
            }
        });
        cbs.get(1).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cbs.get(1).isSelected()) {
                    activeFilters.add(FlightFilters.DELAYED);
                } else {
                    activeFilters.remove(FlightFilters.DELAYED);
                }
                loadFilters();
            }
        });
    }

    private void loadFilters() {
        filteredFlights = new ArrayList<>();
        for (FlightDetails f : flights) {
            filteredFlights.add(f);
        }
        System.out.println("Active Filters: " + activeFilters);
        if (!activeFilters.isEmpty()) {
            for (FlightFilters f : activeFilters) {
                ArrayList<FlightDetails> tmp = getFlightsWithFilter(f);
                System.out.println("tmp: " + tmp);
                filteredFlights.clear();
                filteredFlights.addAll(tmp);
                System.out.println("Filtered: " + filteredFlights);
            }
            System.out.println(activeFilters);
            // get the filtered flights sublist
            loadFilteredFlights();
        } else {
            updateScrollPanel(flights);
        }

    }

    private ArrayList<FlightDetails> getFlightsWithFilter(FlightFilters filter) {
        switch (filter) {
            case LAYOVER1:
                return filterLayover(1);
            case LAYOVER2:
                return filterLayover(2);
            case LAYOVER3:
                return filterLayover(3);
            case LAYOVERS:
                return filterLayover(4);
            case SHORTDURATION:
                return filterDuration(-1);
            case MIDDURATION:
                return filterDuration(0);
            case LONGDURATION:
                return filterDuration(1);
            case PRICERANGE:
                return filterPriceRange();
            case DELAYED:
                return filterDelayed();
            case OVERNIGHT:
                return filterOvernight();
            default:
                break;
        }
        return new ArrayList<>();
    }

    private ArrayList<FlightDetails> filterLayover(int nLayover) {
        ArrayList<FlightDetails> result = new ArrayList<>();
        if (nLayover != 4) {
            for (FlightDetails f : filteredFlights) {
                if (f.getLayovers().size() == nLayover) {
                    result.add(f);
                }
            }
        } else {
            for (FlightDetails f : filteredFlights) {
                if (f.getLayovers().size() >= nLayover) {
                    result.add(f);
                }
            }
        }
        return result;
    }

    private ArrayList<FlightDetails> filterDuration(int duration) {
        // -1 Short (6h)
        // 0 Mid (10h)
        // 1 Long (10h+)
        ArrayList<FlightDetails> result = new ArrayList<>();
        switch (duration) {
            case -1:
                for (FlightDetails f : filteredFlights) {
                    LocalDateTime time1 = FlightListElementPanel.parseDateTime(f.getFlights().getFirst().getDeparture_airport().getTime().split(" "));
                    LocalDateTime time2 = FlightListElementPanel.parseDateTime(f.getFlights().getLast().getArrival_airport().getTime().split(" "));
                    long flightDuration = Math.abs(time1.until(time2, ChronoUnit.HOURS));
                    if (flightDuration <= 6) {
                        result.add(f);
                    }
                }
                return result;
            case 0:
                for (FlightDetails f : filteredFlights) {
                    LocalDateTime time1 = FlightListElementPanel.parseDateTime(f.getFlights().getFirst().getDeparture_airport().getTime().split(" "));
                    LocalDateTime time2 = FlightListElementPanel.parseDateTime(f.getFlights().getLast().getArrival_airport().getTime().split(" "));
                    long flightDuration = Math.abs(time1.until(time2, ChronoUnit.HOURS));
                    if (flightDuration <= 10) {
                        result.add(f);
                    }
                }
                return result;
            case 1:
                for (FlightDetails f : filteredFlights) {
                    LocalDateTime time1 = FlightListElementPanel.parseDateTime(f.getFlights().getFirst().getDeparture_airport().getTime().split(" "));
                    LocalDateTime time2 = FlightListElementPanel.parseDateTime(f.getFlights().getLast().getArrival_airport().getTime().split(" "));
                    long flightDuration = Math.abs(time1.until(time2, ChronoUnit.HOURS));
                    if (flightDuration > 10) {
                        result.add(f);
                    }
                }
                return result;
            default:
                break;
        }
        return new ArrayList<>();
    }

    private ArrayList<FlightDetails> filterOvernight() {
        ArrayList<FlightDetails> result = new ArrayList<>();
        boolean isOvernight = false;
        for (FlightDetails f : filteredFlights) {
            for (Flight flight : f.getFlights()) {
                if (flight.isOvernight()) {
                    isOvernight = true;
                }
            }
            if (!isOvernight) {
                result.add(f);
            }
            isOvernight = false;
        }
        return result;
    }

    private ArrayList<FlightDetails> filterDelayed() {
        ArrayList<FlightDetails> result = new ArrayList<>();
        boolean isDelayed = false;
        for (FlightDetails f : filteredFlights) {
            for (Flight flight : f.getFlights()) {
                if (flight.isOften_delayed_by_over_30_min()) {
                    isDelayed = true;
                }
            }
            if (!isDelayed) {
                result.add(f);
            }
            isDelayed = false;
        }
        return result;
    }

    private ArrayList<FlightDetails> filterPriceRange() {
        ArrayList<FlightDetails> result = new ArrayList<>();
        System.out.println("Lowest Price: " + priceInsights.getLowest_price());
        System.out.println("Price Selected: " + priceSelected);
        for (FlightDetails f : filteredFlights) {
            if (f.getPrice() >= priceInsights.getLowest_price() && f.getPrice() <= priceSelected) {
                System.out.println(f.getPrice());
                result.add(f);
            }
        }
        System.out.println(result);
        return result;
    }

    private void loadFilteredFlights() {
        // load the filtered flights sublist
        updateScrollPanel(filteredFlights);
    }

    private void getPriceRange() {
        int max = flights.getFirst().getPrice();
        for (FlightDetails f : flights) {
            if (f.getPrice() > max) {
                max = f.getPrice();
            }
        }
        priceSelected = priceInsights.getLowest_price();
        lbPriceSelected.setText("Up to " + priceInsights.getLowest_price());
        slPriceRange.setMinimum(priceInsights.getLowest_price());
        slPriceRange.setMaximum(max);
        maxPrice = max;
    }

    public JPanel getHomePanel() {
        return homePanel;
    }

    private void peticionAPI(String departureId, String arrivalId, String departureDate, String returnDate, String currency, String type, String passengers, String savePath) {
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
            guardarJSON(result, savePath);
            conn.disconnect();
        } catch (Exception e) {
            System.out.println("Exception in NetClientGet:- " + e);
        }
    }

    private void guardarJSON(String data, String path) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
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

    private String leerJSON(String path) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
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
