/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.how2fly;

import com.mycompany.how2fly.pojo.Flight;
import com.mycompany.how2fly.pojo.frontend.FlightDetails;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Alumno
 */
public class FlightDetailsPanel extends javax.swing.JPanel {

    private FlightDetails flightDetails;
    private MainFrame parent;
    private JPanel context;

    /**
     * Creates new form FlightDetailsPanel
     */
    public FlightDetailsPanel(MainFrame parent, JPanel context, FlightDetails flightDetails) {
        this.parent = parent;
        this.context = context;
        this.flightDetails = flightDetails;
        initComponents();
        start();
    }

    private void start() {
        JLabel lbHeader = new JLabel("From - To");
        lbHeader.setOpaque(true);
        lbHeader.setBackground(Color.green);
        JPanel detailsPanel = new JPanel();
        detailsPanel.setBackground(Color.red);
        JButton btnBack = new JButton("Back");

        setupDetailsPanel(detailsPanel);

        btnBack.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                goBack();
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

        JButton btnBook = new JButton("Book Now!");

        btnBook.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

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

        GridBagLayout detailsLayout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        this.setLayout(detailsLayout);

        c.anchor = GridBagConstraints.CENTER;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 1;
        this.add(lbHeader, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridheight = 4;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        this.add(detailsPanel, c);

        c.anchor = GridBagConstraints.WEST;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridy = 5;
        c.weightx = 0;
        c.weighty = 0;
        c.fill = GridBagConstraints.NONE;
        this.add(btnBack, c);

        c.anchor = GridBagConstraints.EAST;
        this.add(btnBook, c);

        loadFlightData();
    }

    private void setupDetailsPanel(JPanel detailsPanel) {
        GridLayout detailsLayout = new GridLayout(1, flightDetails.getFlights().size());
        detailsPanel.setLayout(detailsLayout);

        for (int i = 0; i < flightDetails.getFlights().size(); i++) {
            JPanel column = new JPanel();
            column.setBackground(Color.lightGray);
            column.setLayout(new GridLayout(10,1));
            
            Flight f = flightDetails.getFlights().get(i);
            JLabel lbFrom = new JLabel("From");
            lbFrom.setText("From: " + f.getDeparture_airport().getName() + " (" + f.getDeparture_airport().getId() + ")");

            JLabel lbGoingTime = new JLabel("99:99");
            lbGoingTime.setText("Going Time: "+f.getDeparture_airport().getTime());
            
            JLabel lbTo = new JLabel("To");
            lbTo.setText("To: " + f.getArrival_airport().getName()+"("+f.getArrival_airport().getId()+")");

            JLabel lbArriveTime = new JLabel("99:99");
            lbArriveTime.setText("Arrive Time: "+f.getArrival_airport().getTime());
            
            JLabel lbAirplane = new JLabel("Airplane");
            lbAirplane.setText("Airplane: "+f.getAirplane());

            JLabel lbAirline = new JLabel("Airline");
            lbAirline.setText("Airline: "+f.getAirline());
            Image airlineImg = FlightListElementPanel.createImageWithURL(f.getAirline_logo());
            ImageIcon airlineLogo = new ImageIcon(FlightListElementPanel.rescaleImage(airlineImg, 50, 50));
            lbAirline.setIcon(airlineLogo);

            JLabel lbTravelClass = new JLabel("Travel Class");
            lbTravelClass.setText("Travel Class: "+f.getTravel_class());
            
            JLabel lbDelayed = new JLabel("Delayed");
            lbDelayed.setText("Often Delayed: "+f.isOften_delayed_by_over_30_min());
            
            JLabel lbOvernight = new JLabel("Overnight");
            lbOvernight.setText("Overnight: "+f.isOvernight());
            
            JLabel lbLegRoom = new JLabel("Leg Room");
            lbLegRoom.setText("Leg Room: "+f.getLegroom());
            
            column.add(lbFrom);
            column.add(lbGoingTime);
            column.add(lbTo);
            column.add(lbArriveTime);
            column.add(lbAirplane);
            column.add(lbAirline);
            column.add(lbTravelClass);
            column.add(lbDelayed);
            column.add(lbOvernight);
            column.add(lbLegRoom);
            
            detailsPanel.add(column);
        }

    }

    private void loadFlightData() {

    }

    private void goBack() {
        parent.remove(this);
        parent.add(context);
        parent.revalidate();
        parent.repaint();
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
