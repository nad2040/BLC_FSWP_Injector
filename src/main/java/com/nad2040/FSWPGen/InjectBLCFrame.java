package com.nad2040.FSWPGen;

import com.google.gson.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

public class InjectBLCFrame extends JFrame implements ActionListener {

    private final JFrame frame;
    private final JTextField filelabel;
    private final JButton findFile;
    private final JButton install;
    private final JCheckBox active;
    private final JLabel success;
    private File modprofile;

    public InjectBLCFrame() {
        final int width = 600, height = 250;
        JPanel panel = new JPanel();
        frame = new JFrame();
        frame.setSize(width,height);
        frame.setLocationRelativeTo(null);
        frame.setTitle("InjectBLCModProfileWithFSWP");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        panel.setLayout(null);

        JLabel title = new JLabel("Locate your BLC Mod Profile");
        title.setBounds(width/2 - 100,20,200,20);
        title.setHorizontalAlignment(JLabel.CENTER);
        panel.add(title);

        findFile = new JButton("Find your profile");
        findFile.setBounds(width/2 - 100,60,200,20);
        findFile.addActionListener(this);
        panel.add(findFile);

        filelabel = new JTextField("");
        filelabel.setBounds(width/2 - 250,100,500,20);
        filelabel.setEditable(false);
        panel.add(filelabel);

        install = new JButton("Install");
        install.setBounds(width/2 + 50,140,125,20);
        install.setEnabled(false);
        install.addActionListener(this);
        panel.add(install);

        active = new JCheckBox("All Hidden (Recommended)");
        active.setBounds(width/2 - 200,140,220,20);
        active.addActionListener(this);
        panel.add(active);

        success = new JLabel("");
        success.setBounds(width/2 - 200,170,400,20);
        success.setHorizontalAlignment(JLabel.CENTER);

        panel.add(success);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == findFile) {
            FileDialog fd = new FileDialog(frame,"",FileDialog.LOAD);
            fd.setDirectory(System.getProperty("user.home"));
            fd.setLocationRelativeTo(frame);
            fd.setVisible(true);
            if (fd.getDirectory() != null) {
                String directory = fd.getDirectory();
                String filename = fd.getFile();
                filelabel.setText(directory + filename);
                modprofile = new File(directory + filename);
                if (filename.split("\\.")[1].equals("json")) install.setEnabled(true);
            }
        }
        if (e.getSource() == active) {
            active.setText(active.isSelected() ? "All Active (Just no)": "All Hidden (Recommended)");
        }
        if (e.getSource() == install) {
            try {
                jsonInject(modprofile,active.isSelected());
                success.setText("Success! You may close the window now.");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public static void jsonInject(File file, boolean visible) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

        InputStreamReader rd = new InputStreamReader(file.toURI().toURL().openStream());
        JsonObject rootObj = JsonParser.parseReader(rd).getAsJsonObject();
        rd.close();

        JsonObject waypoints = rootObj.getAsJsonObject("waypoints");
        JsonArray array = FSGen.BLC_FSWP(visible);
        waypoints.add("waypoints", array);

        FileWriter w = new FileWriter(file.getAbsolutePath());
        gson.toJson(rootObj, w);
        w.flush();
        w.close();
    }

    public static void main(String[] args) throws IllegalArgumentException, URISyntaxException, IOException {
//        InjectBLCFrame frame = new InjectBLCFrame();

//        When running the functions below inside IntelliJ IDEA, the files will be generated in /build/classes/java
//        These are functions only useful to me.
//        FSGen.BLCGen(false);
//        FSGen.BLCGen(true);
        FSGen.LunarGen(false);
//        FSGen.LunarGen(true);
//        FSGen.FyuGen();
    }
}
