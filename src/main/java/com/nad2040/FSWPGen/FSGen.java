package com.nad2040.FSWPGen;

import com.google.gson.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

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

    public static LinkedHashMap<String, JsonArray> FSRepo() throws IOException, IllegalArgumentException {
        URL url = new URL("https://raw.githubusercontent.com/Moulberry/NotEnoughUpdates-REPO/master/constants/fairy_souls.json");
        InputStreamReader rd = new InputStreamReader(url.openStream());

        JsonObject rootObj = JsonParser.parseReader(rd).getAsJsonObject();
        JsonArray hub = rootObj.getAsJsonArray("hub");
        JsonArray combat_1 = rootObj.getAsJsonArray("combat_1");
        JsonArray combat_2 = rootObj.getAsJsonArray("combat_2");
        JsonArray combat_3 = rootObj.getAsJsonArray("combat_3");
        JsonArray foraging_1 = rootObj.getAsJsonArray("foraging_1");
        JsonArray farming_1 = rootObj.getAsJsonArray("farming_1");
        JsonArray mining_1 = rootObj.getAsJsonArray("mining_1");
        JsonArray mining_2 = rootObj.getAsJsonArray("mining_2");
        JsonArray mining_3 = rootObj.getAsJsonArray("mining_3");
        JsonArray winter = rootObj.getAsJsonArray("winter");
        JsonArray dungeon_hub = rootObj.getAsJsonArray("dungeon_hub");

        LinkedHashMap<String, JsonArray> soulrepo = new LinkedHashMap<>();
        soulrepo.put("Hub", hub);
        soulrepo.put("Spider's Den", combat_1);
        soulrepo.put("Blazing Fortress", combat_2);
        soulrepo.put("End", combat_3);
        soulrepo.put("Park", foraging_1);
        soulrepo.put("Farming", farming_1);
        soulrepo.put("Gold Mine", mining_1);
        soulrepo.put("Deep Caverns", mining_2);
        soulrepo.put("Dwarven Mines", mining_3);
        soulrepo.put("Jerry's Workshop", winter);
        soulrepo.put("Dungeon Hub", dungeon_hub);

        return soulrepo;
    }
}