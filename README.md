# BLC Fairy Soul Waypoint Injector for Hypixel Skyblock

### **This work is now obsolete because Badlion Client added a mod that allows you to find fairy souls without having to use the waypoints mod.**

Injects BLC Mod Profile with fairy soul waypoints automatically.

- It accesses the NotEnoughUpdates-REPO/constants/fairy_souls.json
- It reads src/main/resources/locations.csv
- It creates a linked hash map for each real island name and the JsonArray of coordinate strings held in NEU-REPO.
- It formats the BLC waypoint format with the coordinates and name, uses Gson to parse it into a JsonElement, and inserts it into the JsonArray.

That JsonArray is then inserted into your BLC Mod Profile.

**IT WILL DELETE ANY WAYPOINTS ALREADY INSIDE YOUR MOD PROFILE**

#### Please make a Github issue if this jar does not work on Windows. It should in theory, but I am not 100% sure.

## How to Use?

Download the latest InjectBLCWithFSWP jar from the releases -->\
Make sure you have Java 17 installed: https://adoptium.net/?variant=openjdk17&jvmVariant=hotspot \
Double click the jar.\
Select your BLC mod profile. .minecraft/BLClient-Mod-Profiles \
Select if you want all waypoints to be active/inactive by default. (I recommend inactive)\
Click the install button.\
Then close the window.

## Credits
Credits to https://github.com/Moulberry/NotEnoughUpdates-REPO/blob/master/constants/fairy_souls.json
