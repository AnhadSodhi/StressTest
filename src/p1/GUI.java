package p1;

import javax.swing.SwingUtilities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GUI extends JPanel {
    private JFrame frame;
    private final int frameWidth;
    private final int frameHeight;
    private JButton startButton;
    private JButton stopButton;
    
    private JLabel startedLabel;
    private JLabel cpuLabel;
    private JLabel cpuTemperatureLabel;
    private JLabel gpuLabel;
    private JLabel gpuTemperatureLabel;
    
    public GUI(int xSize, int ySize) {
        frame = new JFrame("Stress Test");
        frameWidth = xSize;
        frameHeight = ySize;
        
        createAndShowGUI();
    }
    
    public Dimension getPreferredSize() {
        return new Dimension(frameWidth, frameHeight);
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
    
    private void processClicks(MouseEvent e) {
        repaint(); // Redraw the panel
    }
    
    private void createAndShowGUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this); // Add the current instance of GUI to the frame
        frame.setLayout(null); // Set layout to null for absolute positioning
        
        // Create started status label
        startedLabel = new JLabel("Status: Not Running");
        startedLabel.setBounds(frameWidth-150, 10, 200, 30);
        frame.add(startedLabel);
        
        // Create and add start button
        startButton = createStartButton();
        frame.add(startButton);

        // Create and add stop button
        stopButton = createStopButton();
        frame.add(stopButton);

        // Create and add cpu label
        cpuLabel = new JLabel("CPU: " + Temps.getCurrentCPU());
        cpuLabel.setBounds(10, 10, 200, 30);
        frame.add(cpuLabel);

        // Create and add temperature label
        cpuTemperatureLabel = new JLabel("Temperature: ");
        cpuTemperatureLabel.setBounds(10, 50, 200, 30);
        frame.add(cpuTemperatureLabel);

        // Create and add cpu label
        gpuLabel = new JLabel("GPU: " + Temps.getCurrentGPU());
        gpuLabel.setBounds(10, 90, 200, 30);
        frame.add(gpuLabel);

        // Create and add temperature label
        gpuTemperatureLabel = new JLabel("Temperature: ");
        gpuTemperatureLabel.setBounds(10, 130, 200, 30);
        frame.add(gpuTemperatureLabel);

        frame.setSize(frameWidth, frameHeight); // Set the size of the frame to 500x500

        frame.setVisible(true);
        setBorder(BorderFactory.createLineBorder(Color.black));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                processClicks(e);
            }
        });
        
        updateTemperature(cpuTemperatureLabel, Temps.getCPUTemp());
        updateTemperature(gpuTemperatureLabel, Temps.getGPUTemp());
        updateStartedLabel();
        
        Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	updateTemperature(cpuTemperatureLabel, Temps.getCPUTemp());
                updateTemperature(gpuTemperatureLabel, Temps.getGPUTemp());
                updateStartedLabel();
            }
        });
        timer.start();
    }

    
    private JButton createStartButton() {
        JButton button = new JButton("Start");
        button.setBounds(frameWidth-150, 50, 100, 100); // Position and size
        button.setBackground(Color.GREEN); // Set background color to green
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Main.startTest(); // Call Main.start() when button clicked
            }
        });
        return button;
    }

    private JButton createStopButton() {
        JButton button = new JButton("Stop");
        button.setBounds(frameWidth-150, 50+100+50, 100, 100); // Position and size
        button.setBackground(Color.RED); // Set background color to red
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Main.stopTest(); // Call Main.stop() when button clicked
            }
        });
        return button;
    }
    
    // Function to update temperature label
    public void updateTemperature(JLabel label, double temperature) {
        label.setText("Temperature: " + temperature + " °C");
    }
    
    // Function to update started label
    public void updateStartedLabel() {
        startedLabel.setText((Main.running) ? "Status: Running..." : "Status: Not running");
    }
}
