/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.how2fly;

import com.mycompany.how2fly.pojo.Flight;
import com.mycompany.how2fly.pojo.frontend.FlightDetails;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Alumno
 */
public class FlightListElementPanel extends javax.swing.JPanel {

    private final FlightDetails flightDetails;
    private final MainFrame parent;
    private final JPanel context;
    private String lineImgURL = "https://cdn-icons-png.flaticon.com/512/59/59549.png";
    private String planeImgURL = "https://cdn-icons-png.flaticon.com/512/61/61212.png";

    /**
     * Creates new form FlightListElementPanel
     */
    public FlightListElementPanel(MainFrame parent, JPanel context, FlightDetails flightDetails) {
        this.parent = parent;
        this.context = context;
        this.flightDetails = flightDetails;
        initComponents();
        start();
    }

    public FlightDetails getFlightDetails() {
        return flightDetails;
    }
    
    private void start() {
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setBackground(Color.yellow);
        GridBagLayout mainLayout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        this.setLayout(mainLayout);
        JLabel lbPrice = new JLabel("100€");
        lbPrice.setToolTipText("Total Price");
        lbPrice.setFont(MainFrame.defaultFontSize2);
        JLabel lbAirline = new JLabel("Airline");
        lbAirline.setToolTipText("Airline");
        lbAirline.setFont(MainFrame.defaultFontSize2);
        
        JLabel lbOvernight = new JLabel();
        lbOvernight.setFont(MainFrame.defaultFontSize2);
        lbOvernight.setVisible(false);
        for (Flight f : flightDetails.getFlights()) {
            if (f.isOvernight()) {
                lbOvernight.setVisible(true);
            }
        }
        Image overnightImg = rescaleImage(createImageWithURL("https://cdn-icons-png.flaticon.com/512/581/581601.png"), 25, 25);
        lbOvernight.setIcon(new ImageIcon(overnightImg));
        lbOvernight.setToolTipText("This flight is overnight");

        JLabel lbDelayed = new JLabel();
        lbDelayed.setFont(MainFrame.defaultFontSize2);
        lbDelayed.setVisible(false);
        for (Flight f : flightDetails.getFlights()) {
            if (f.isOften_delayed_by_over_30_min()) {
                lbDelayed.setVisible(true);
            }
        }
        Image delayedImg = rescaleImage(createImageWithURL("https://cdn-icons-png.flaticon.com/512/2972/2972543.png"), 25, 25);
        lbDelayed.setIcon(new ImageIcon(delayedImg));
        lbDelayed.setToolTipText("This flight is often delayed");

        JPanel infoPanel = new JPanel();
        infoPanel.setToolTipText("Press for Details");
        infoPanel.setBackground(Color.MAGENTA);

        sendToDetailsListener(infoPanel);

        constraints.insets = new Insets(0, 0, 10, 0);
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;

        this.add(lbPrice, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 9;
        constraints.gridheight = 2;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.insets = new Insets(0, 0, 10, 10);
        constraints.fill = GridBagConstraints.BOTH;

        setupInfoPanelContent(infoPanel, lbPrice, lbAirline);

        this.add(infoPanel, constraints);

        constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 0, 0);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;

        this.add(lbAirline, constraints);

        constraints = new GridBagConstraints();
        constraints.insets = new Insets(0, 0, 5, 5);
        constraints.gridx = 10;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;

        this.add(lbOvernight, constraints);

        constraints = new GridBagConstraints();
        constraints.insets = new Insets(0, 0, 5, 5);
        constraints.gridx = 11;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;

        this.add(lbDelayed, constraints);
    }

    private void setupInfoPanelContent(JPanel infoPanel, JLabel lbPrice, JLabel lbAirline) {
        JLabel lbFrom = new JLabel("From");
        lbFrom.setFont(MainFrame.defaultFontSize2);
        JLabel lbTo = new JLabel("To");
        lbTo.setFont(MainFrame.defaultFontSize2);
        JLabel lbDuration = new JLabel("10:10");
        lbDuration.setFont(MainFrame.defaultFontSize2);
        JLabel lbLayovers = new JLabel("10");
        lbLayovers.setFont(MainFrame.defaultFontSize2);
        JLabel lbGoingTime = new JLabel("10:10");
        lbGoingTime.setFont(MainFrame.defaultFontSize2);
        JLabel lbArriveTime = new JLabel("10:10");
        lbArriveTime.setFont(MainFrame.defaultFontSize2);

        loadFlightData(lbFrom, lbTo, lbPrice, lbDuration, lbLayovers, lbGoingTime, lbArriveTime, lbAirline);

        GridBagLayout layoutInfoPanel = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        infoPanel.setLayout(layoutInfoPanel);

        c.ipadx = 2;
        c.ipady = 8;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 1;
        c.gridy = 0;
        infoPanel.add(lbDuration, c);
        c.gridx = 0;
        c.gridy = 1;
        infoPanel.add(lbGoingTime, c);
        c.gridx = 5;
        c.gridy = 1;
        infoPanel.add(lbArriveTime, c);
        c.gridx = 0;
        c.gridwidth = 2;
        c.gridy = 2;
        JLabel lbLine = new JLabel();
        Image lbLineImg = createImageWithURL(lineImgURL);
        lbLineImg = rescaleImage(lbLineImg, 50, 25);
        lbLine.setIcon(new ImageIcon(lbLineImg));
        infoPanel.add(lbLine, c);
        c.gridx = 2;
        JLabel lbPlane = new JLabel();
        Image lbPlaneImg = createImageWithURL(planeImgURL);
        lbPlaneImg = rescaleImage(lbPlaneImg, 25, 25);
        lbPlane.setIcon(new ImageIcon(lbPlaneImg));
        infoPanel.add(lbPlane, c);
        c.ipadx = 2;
        c.ipady = 8;
        c.gridwidth = 1;
        c.gridy = 3;
        c.gridx = 0;
        infoPanel.add(lbFrom, c);
        c.gridy = 3;
        c.gridx = 5;
        infoPanel.add(lbTo, c);
        c.gridy = 4;
        c.gridx = 1;
        infoPanel.add(lbLayovers, c);
    }

    private void loadFlightData(JLabel lbFrom, JLabel lbTo, JLabel lbPrice, JLabel lbDuration, JLabel lbLayovers, JLabel lbGoingTime, JLabel lbArriveTime, JLabel lbAirline) {
        int layovers = flightDetails.getLayovers().size();
        lbFrom.setText(flightDetails.getFlights().getFirst().getDeparture_airport().getId());
        lbTo.setText(flightDetails.getFlights().getLast().getArrival_airport().getId());
        int euros = flightDetails.getPrice();
        lbPrice.setText(euros + "€");
        LocalDateTime goingTime = parseDateTime(flightDetails.getFlights().getFirst().getDeparture_airport().getTime().split(" "));
        LocalDateTime arriveTime = parseDateTime(flightDetails.getFlights().getLast().getArrival_airport().getTime().split(" "));
        long hours = goingTime.until(arriveTime, ChronoUnit.MINUTES) / 60;
        long minutes = goingTime.until(arriveTime, ChronoUnit.MINUTES) % 60;
        String duration = "";
        if (hours != 0) {
            duration += hours + "h ";
        }
        if (minutes != 0) {
            if (minutes < 10) {
                duration += "0";
            }
            duration += minutes + "m";
        }
        lbDuration.setText(duration);
        lbLayovers.setText("Layovers: " + layovers);
        String lessThanTenMinutes = "";
        if (goingTime.getMinute() < 10) {
            lessThanTenMinutes = "0";
        }
        lbGoingTime.setText("" + goingTime.getHour() + ":" + lessThanTenMinutes + goingTime.getMinute());
        lessThanTenMinutes = "";
        if (arriveTime.getMinute() < 10) {
            lessThanTenMinutes = "0";
        }
        lbArriveTime.setText("" + arriveTime.getHour() + ":" + lessThanTenMinutes + arriveTime.getMinute());
        lbAirline.setText(flightDetails.getFlights().getFirst().getAirline());

        Image airlineIcon = createImageWithURL(flightDetails.getFlights().getFirst().getAirline_logo());
        airlineIcon = rescaleImage(airlineIcon, 50, 50);
        lbAirline.setIcon(new ImageIcon(airlineIcon));

    }

    public static Image rescaleImage(Image image, int width, int height) {
        return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    public static Image createImageWithURL(String url) {
        try {
            return ImageIO.read(new URL(url));
        } catch (IOException ex) {
            Logger.getLogger(FlightListElementPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private LocalTime parseTime(String time) {
        String[] data = time.split(":");
        int hours = Integer.parseInt(data[0]);
        int minutes = Integer.parseInt(data[1]);
        return LocalTime.of(hours, minutes);
    }

    public static LocalDateTime parseDateTime(String[] data) {
        String date = data[0];
        String time = data[1];
        String[] dataTime = time.split(":");
        int hours = Integer.parseInt(dataTime[0]);
        int minutes = Integer.parseInt(dataTime[1]);
        String[] dataDate = date.split("-");
        int years = Integer.parseInt(dataDate[0]);
        int months = Integer.parseInt(dataDate[1]);
        int days = Integer.parseInt(dataDate[2]);
        return LocalDateTime.of(years, months, days, hours, minutes);
    }

    private void sendToDetailsListener(JPanel panel) {
        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                parent.remove(context);
                parent.add(new FlightDetailsPanel(parent, context, flightDetails));
                parent.revalidate();
                parent.repaint();
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
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
