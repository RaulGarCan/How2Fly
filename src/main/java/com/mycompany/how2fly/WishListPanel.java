/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.how2fly;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author Alumno
 */
public class WishListPanel extends javax.swing.JPanel {

    private MainFrame parent;

    /**
     * Creates new form WishListPanel
     */
    public WishListPanel(MainFrame parent) {
        this.parent = parent;
        initComponents();
        start();
    }

    private void start() {
        this.setBackground(Color.green);
        GridBagLayout homeLayout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        this.setLayout(homeLayout);
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = 1;
        constraints.gridwidth = 1;
        this.add(btnBackHome(), constraints);
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridheight = 3;
        constraints.gridwidth = 1;
        constraints.weighty = 1;
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.BOTH;
        this.add(setupBottomPanel(), constraints);
    }

    private JButton btnBackHome() {
        JButton btnHome = new JButton("Home");
        btnHome.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                parent.remove(WishListPanel.this);
                parent.add(parent.getHomePanel());
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
        return btnHome;
    }

    private JPanel setupBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.red);
        JPanel scrollInsidePanel = new JPanel();
        scrollInsidePanel.setBackground(Color.white);
        scrollInsidePanel.setLayout(new BoxLayout(scrollInsidePanel, BoxLayout.Y_AXIS));

        ArrayList<JPanel> scrollElements = getScrollPanelElements();
        for (JPanel p : scrollElements) {
            p.setPreferredSize(new Dimension(1, 240));
            scrollInsidePanel.add(p);
        }

        JScrollPane scrollPanel = new JScrollPane(scrollInsidePanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPanel.setBackground(Color.yellow);
        scrollPanel.getVerticalScrollBar().setUnitIncrement(20);
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
        tmp.add(new FlightListElementPanel(parent, this));
        tmp.add(new FlightListElementPanel(parent, this));
        tmp.add(new FlightListElementPanel(parent, this));
        tmp.add(new FlightListElementPanel(parent, this));
        tmp.add(new FlightListElementPanel(parent, this));
        tmp.add(new FlightListElementPanel(parent, this));
        return tmp;
    }

    private void addFilters(JPanel filtersPanel, ArrayList<String> filters) {
        for (String f : filters) {
            filtersPanel.add(new JCheckBox(f));
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
