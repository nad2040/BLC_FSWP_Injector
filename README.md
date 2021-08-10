# BLC Fairy Soul Waypoint Injector for Hypixel Skyblock
Injects BLC Mod Profile with fairy soul waypoints automatically.

- It accesses the NotEnoughUpdates-REPO/constants/fairy_souls.json
- It reads src/main/resources/locations.csv
- It creates a linked hash map for each real island name and the JsonArray of coordinate strings held in NEU-REPO.
- It formats the BLC waypoint format with the coordinates and name, uses Gson to parse it into a JsonElement, and inserts it into the JsonArray.

That JsonArray is then inserted into your BLC Mod Profile.

#### Please make a Github issue if this jar does not work on Windows. It should in theory, but I am not 100% sure.

## How to Use?

Download the latest InjectBLCWithFSWP jar from the releases -->\
Make sure you have java 16 installed: https://adoptopenjdk.net/?variant=openjdk16 \
   (download the jre if you want the jre)\
Double click the jar.\
Select your BLC mod profile.\
Select if you want all waypoints to be active/inactive by default. (Just leave it inactive please)\
Click install.\
Then close the window.

## Credits
Credits to https://github.com/Moulberry/NotEnoughUpdates-REPO/blob/master/constants/fairy_souls.json
