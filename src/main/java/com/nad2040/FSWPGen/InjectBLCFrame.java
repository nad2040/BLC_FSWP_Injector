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
    private final JLabel success;
    private File modprofile;

    public InjectBLCFrame() {
        final int width = 600, height = 300;
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
        install.setBounds(width/2 - 50,140,100,20);
        install.setEnabled(false);
        install.addActionListener(this);
        panel.add(install);

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
        if (e.getSource() == install) {
            try {
                jsonInject(modprofile,false);
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
        waypoints.remove("waypoints");

        JsonArray array = FSGen.BLC_FSWP(visible);
        waypoints.add("waypoints", array);

        FileWriter w = new FileWriter(file.getAbsolutePath());
        gson.toJson(rootObj, w);
        w.flush();
        w.close();
    }

    public static void main(String[] args) throws IllegalArgumentException, IOException, URISyntaxException {
        InjectBLCFrame frame = new InjectBLCFrame();

//        jsonInject(new File("/Users/danliu/Desktop/test.json"), true);

//        FSGen.BLCGen(true);
//        FSGen.BLCGen(false);
//        FSGen.FyuGen();
    }
}