/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.how2fly;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author Alumno
 */
public class MainFrame extends javax.swing.JFrame {

    private JPanel homePanel;
    private WishListPanel wishListPanel;
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

        homePanel = new JPanel();
        homePanel.setBackground(Color.GREEN);

        GridBagLayout homeLayout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        homePanel.setLayout(homeLayout);
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
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
        homePanel.add(setupBottomPanel(), constraints);

        GridLayout frameLayout = new GridLayout(1, 1);
        this.setLayout(frameLayout);
        this.add(homePanel);

        this.setExtendedState(MAXIMIZED_BOTH);
    }

    private void matchParentSize(JPanel panel, JFrame parent) {
        panel.setSize(parent.getSize());
        panel.setPreferredSize(parent.getPreferredSize());
    }

    private void matchParentSize(JPanel panel, JPanel parent) {
        panel.setSize(parent.getSize());
        panel.setPreferredSize(parent.getPreferredSize());
    }

    private void matchParentSize(JPanel panel, JScrollPane parent) {
        panel.setSize(parent.getSize());
        panel.setPreferredSize(parent.getPreferredSize());
    }

    private JPanel setupTopPanel() {
        Dimension dim = new Dimension(this.getSize().width / 8, 25);
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.blue);
        topPanel.setLayout(new GridLayout(2, 7));

        // Top Row
        JLabel lbFrom = new JLabel("From:");
        lbFrom.setSize(dim);
        lbFrom.setPreferredSize(dim);
        topPanel.add(lbFrom);

        JLabel lbTo = new JLabel("To:");
        topPanel.add(lbTo);

        JLabel lbGoing = new JLabel("Going:");
        topPanel.add(lbGoing);

        JLabel lbReturn = new JLabel("Return:");
        topPanel.add(lbReturn);

        JLabel lbPassenger = new JLabel("Passenger:");
        topPanel.add(lbPassenger);

        JLabel lbType = new JLabel("Type:");
        topPanel.add(lbType);

        topPanel.add(new JLabel());

        // Bottom Row
        JTextField tfFrom = new JTextField();
        tfFrom.setToolTipText("From");
        topPanel.add(tfFrom);

        JTextField tfTo = new JTextField();
        tfTo.setToolTipText("To");
        topPanel.add(tfTo);

        JTextField tfGoing = new JTextField();
        tfGoing.setToolTipText("Going");
        topPanel.add(tfGoing);

        JTextField tfReturn = new JTextField();
        tfReturn.setToolTipText("Return");
        topPanel.add(tfReturn);

        JTextField tfPassenger = new JTextField();
        tfPassenger.setToolTipText("Passenger");
        topPanel.add(tfPassenger);

        JTextField tfType = new JTextField();
        tfType.setToolTipText("Type");
        topPanel.add(tfType);

        JButton btnSearch = new JButton("Search");
        btnSearch.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void mousePressed(MouseEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void mouseExited(MouseEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
        tfFrom.setToolTipText("Search");
        topPanel.add(btnSearch);

        return topPanel;
    }

    private JPanel setupBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.red);
        JPanel scrollInsidePanel = new JPanel();
        scrollInsidePanel.setBackground(Color.white);
        scrollInsidePanel.setLayout(new BoxLayout(scrollInsidePanel, BoxLayout.Y_AXIS));

        ArrayList<JPanel> scrollElements = getScrollPanelElements();
        for (JPanel p : scrollElements) {
            p.setPreferredSize(new Dimension(1, 227));
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
        constraints.insets = new Insets(10,10,10,10);
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
        tmp.add(new FlightListElementPanel());
        tmp.add(new FlightListElementPanel());
        tmp.add(new FlightListElementPanel());
        tmp.add(new FlightListElementPanel());
        tmp.add(new FlightListElementPanel());
        tmp.add(new FlightListElementPanel());
        tmp.add(new FlightListElementPanel());
        tmp.add(new FlightListElementPanel());
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
