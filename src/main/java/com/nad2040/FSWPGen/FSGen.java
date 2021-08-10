package com.nad2040.FSWPGen;

import com.google.gson.*;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class FSGen {
    private static final String blcFormat =
        """
        {
            "global": {
                "value": true
            },
            "dimensionSpecific": {
                "value": true
            },
            "minimapMod": {
                "value": false
            },
            "directionMod": {
                "value": false
            },
            "beamMarker": {
                "value": true
            },
            "textMarker": {
                "value": true
            },
            "enabled": {
                "value": %s
            },
            "fancyFont": {
                "value": false
            },
            "x": %s,
            "y": %s,
            "z": %s,
            "dimension": 0,
            "nameColor": {
                "enabled": true,
                "color": {
                    "red": -1,
                    "green": 0,
                    "blue": 0,
                    "alpha": -1
                },
                "breathingSpeed": 1,
                "mode": "STATIC",
                "up": true
            },
            "backgroundColor": {
                "enabled": true,
                "color": {
                    "red": 0,
                    "green": 0,
                    "blue": 0,
                    "alpha": 104
                },
                "breathingSpeed": 1,
                "mode": "STATIC",
                "up": true
            },
            "beamColor": {
                "enabled": true,
                "color": {
                    "red": 0,
                    "green": 0,
                    "blue": 0,
                    "alpha": 104
                },
                "breathingSpeed": 1,
                "mode": "STATIC",
                "up": true
            },
            "name": "%s",
            "server": "mc.hypixel.net:25565",
            "beamMode": "SINGLE",
            "toBedrock": {
                "value": false
            },
            "mapMarker": "DOT",
            "mapMarkerColor": {
                "enabled": true,
                "color": {
                    "red": 0,
                    "green": 0,
                    "blue": 0,
                    "alpha": -1
                },
                "breathingSpeed": 1,
                "mode": "STATIC",
                "up": true
            },
            "directionMarker": "DOT",
            "directionMarkerColor": {
                "enabled": true,
                "color": {
                    "red": 0,
                    "green": 0,
                    "blue": 0,
                    "alpha": -1
                },
                "breathingSpeed": 1,
                "mode": "STATIC",
                "up": true
            },
            "a": true,
            "b": "Name"
        }""";


    public static void BLCGen(boolean active) throws IOException, URISyntaxException {
        File output = new File(new File(FSGen.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParent() + "/fswp_gen_" + (active ? "" : "de") + "activated.json");
        FileWriter w = new FileWriter(output);

        String isActive = (active ? "true" : "false");
        boolean first = true;

        for (Map.Entry<String, JsonArray> l : FSRepo().entrySet()) {
            int i=1;
            for (JsonElement e : l.getValue()) {
                String[] coords = e.getAsString().split(",");
                if (!first) w.append(",\n");
                w.append(String.format(blcFormat, isActive, coords[0], coords[1], coords[2], l.getKey() + " #" + (i++)));
                first = false;
            }
        }

        w.flush();
        w.close();
    }

    public static JsonArray BLC_FSWP(boolean active) throws IOException {
        JsonArray array = new JsonArray();
        String isActive = (active ? "true" : "false");
        for (Map.Entry<String, JsonArray> l : FSRepo().entrySet()) {
            int i=1;
            for (JsonElement e : l.getValue()) {
                String[] coords = e.getAsString().split(",");
                array.add(JsonParser.parseString(String.format(blcFormat, isActive, coords[0], coords[1], coords[2], l.getKey().replace("'","\\'") + " #" + (i++))));
            }
        }
        return array;
    }

    public static void FyuGen() throws URISyntaxException, IOException {
        File output = new File(new File(FSGen.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParent() + "/waypoints.txt");
        FileWriter w = new FileWriter(output);

        String format = "%s #%d;Overworld;mc.hypixel.net;%s\n";

        for (Map.Entry<String, JsonArray> l : FSRepo().entrySet()) {
            int i=1;
            for (JsonElement e : l.getValue()) {
                w.append(String.format(format, l.getKey(), i++, e.getAsString().replace(',', ';')));
            }
        }

        w.flush();
        w.close();
    }

    public static void LunarGen(boolean active) throws URISyntaxException, IOException {
        final String path = new File(FSGen.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParent();
        FileWriter w;

        String format =
        """
        "%s #%d": {
            "location": {
                "x": %s,
                "y": %s,
                "z": %s
            },
            "visible": %s,
            "dimension": 0
        }""";

        String isActive = (active ? "true" : "false");
        for (Map.Entry<String, JsonArray> l : FSRepo().entrySet()) {
            w = new FileWriter(path + "/waypoints_" + l.getKey().replace(' ','_') + ".json");
            w.append("{\n\t\"mp:hypixel.net\": {\n\t\t\"\": {\n");
            boolean first = true;
            int i=1;
            for (JsonElement e : l.getValue()) {
                String[] coords = e.getAsString().split(",");
                if (!first) w.append(",\n");
                w.append(String.format(format, l.getKey(), i++, coords[0], coords[1], coords[2], isActive));
                first = false;
            }
            w.append("\n\t\t}\n\t}\n}");
            w.flush();
            w.close();
        }
    }

    public static LinkedHashMap<String, JsonArray> FSRepo() throws IOException, IllegalArgumentException {
        URL neu_repo = new URL("https://raw.githubusercontent.com/Moulberry/NotEnoughUpdates-REPO/master/constants/fairy_souls.json");
        InputStreamReader rd = new InputStreamReader(neu_repo.openStream());
        JsonObject rootObj = JsonParser.parseReader(rd).getAsJsonObject();
        LinkedHashMap<String, JsonArray> soulrepo = new LinkedHashMap<>();

        URL csv = new URL("https://raw.githubusercontent.com/nad2040/BLC_FSWP_Injector/main/src/main/resources/locations.csv");
        BufferedReader br = new BufferedReader(new InputStreamReader(csv.openStream(), StandardCharsets.UTF_8));

        String line;
        while ((line = br.readLine()) != null) {
            String[] location = line.split(",");
            soulrepo.put(location[0],rootObj.getAsJsonArray(location[1]));
        }

        return soulrepo;
    }
}