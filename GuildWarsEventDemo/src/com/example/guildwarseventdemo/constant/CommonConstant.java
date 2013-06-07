package com.example.guildwarseventdemo.constant;

public class CommonConstant {
    /**
     * <h3>Dynamic Events API – BETA</h3></br>
     * Optional parameters: world_id, map_id, event_id

This API returns an object with an “events” array. Each element contains the world_id, map_id, event_id, and state of an event. The ids can be translated to strings using the APIs listed below. The optional parameters can be used to filter the results to a desired scope of events.

The possible event states are:</br>
<ul>
<li>Active – The event is running now
<li>Success – The event has succeeded
<li>Fail – The event has failed
<li>Warmup – The event is inactive, and will only become active once certain criteria are met
<li>Preparation – The criteria for the event to start have been met, but certain activities (such as an NPC dialogue) have not completed yet. After the activites have been completed, the event will become Active.
</ul>

     */
    public static final String url = "https://api.guildwars2.com/v1/";
   
    public static final String DANAMIC_EVENT = "events.json";
    
    /**
     * Optional parameters: lang</br>

Each of these APIs takes an optional querystring parameter “lang”. So for example, if you go to /v1/world_names.json?lang=de the results will be returned in german.

Currently supported languages: en, fr, de, es

These APIs return a JSON array of ids to strings. The ids correspond to ids returned in the events.json API.

The region a world is in can be determined by its world_id:
<ul><li>1xxx – North America
 <li>2xxx – Europe</ul>

     */
    public static final String EVENT_NAME = "event_names.json";
    public static final String MAP_NAME = "map_names.json";
    public static final String WORLD_NAME = "world_names.json";
    
    
    /**Attr in events.json.
     * 
     */
    public static final String TAG_ARRAY_EVENT = "events";
    public static final String TAG_WORDL_ID = "world_id";
    public static final String TAG_MAP_ID = "map_id";
    public static final String TAG_EVENT_ID = "event_id";
    public static final String TAG_STATE = "state";
    
    /**Attr in other json.
     * 
     */
    public static final String TAG_ID = "id";
    public static final String TAG_NAME = "name";
    
    public static final String SERVER_US = "US";
    public static final String SERVER_EU = "Europe";
    
    public static final String[] DRAG_EVENT = new String[] {
      "03BF176A-D59F-49CA-A311-39FC6F533F2F",
      "568A30CF-8512-462F-9D67-647D69BEFAED",
      "0464CB9E-1848-4AAA-BA31-4779A959DD71",
      "F7D9D427-5E54-4F12-977A-9809B23FBA99",
      "31CEBA08-E44D-472F-81B0-7143D73797F5",
      "33F76E9E-0BB6-46D0-A3A9-BE4CDFC4A3A4",
      "C5972F64-B894-45B4-BC31-2DEEA6B7C033",
      "C876757A-EF3E-4FBE-A484-07FF790D9B05",
      "9AA133DC-F630-4A0E-BB5D-EE34A2B306C2",
      "D35D7F3B-0A9B-41C6-BD87-7D7A0953F789",
      "57A8E394-092D-4877-90A5-C238E882C320",
      "0372874E-59B7-4A8F-B535-2CF57B8E67E4"
    };
    
    public static final String[] DRAGON_EVENT_NAME = new String[] {
        "Shatterer",
        "Tequatl the Sunless",
        "Claw of Jormag",
        "The Frozen Maw",
        "Shadow Behemoth",
        "Fire Elemental",
        "Great Jungle Wurm",
        "Megadestroyer",
        "Golem Mark II",
        "Temple of Balthazar",
        "Temple of Grenth",
        "Temple of Lyssa"
    };
    
    public static final int[] WORLD_ID_US = new int[] {
        1001,1020,1013,1003,1017,
        1016,1024,1008,1009,1014,
        1002,1019,1011,1021,1022,
        1015,1006,1012,1010,1007,
        1018,1004,1005,1023
    };
    
    public static final int[] WORLD_ID_EU = new int[] {
        2001,2205,2301,2206,2103,
        2003,2012,2007,2013,2202,
        2010,2203,2104,2102,2002,
        2004,2204,2207,2005,2101,
        2014,2006,2009,2201,2008,
        2105,2011
    };
    
    public static final int[] MAP_ID = new int[] {
        53,873,17,30,65,
        15,28,27,73,21,
        34,24,39,19,23,
        54,25,29,62,31,
        22,20,26,35,51,
        32
    };
    
    public static final String[] WORLD_NAME_US = new String[] {
        "Anvil Rock",           "Ferguson's Crossing",  "Sanctum of Rall",  "Yak's Bend",       "Tarnished Coast",
        "Sea of Sorrows",       "Eredon Terrace",       "Jade Quarry",      "Fort Aspenwood",   "Crystal Desert",
        "Borlis Pass",          "Blackgate",            "Stormbluff Isle",  "Dragonbrand",      "Kaineng",
        "Isle of Janthir",      "Sorrow's Furnace",     "Darkhaven",        "Ehmry Bay",        "Gate of Madness",
        "Northern Shiverpeaks", "Henge of Denravi",     "Maguuma",          "Devona's Rest"
    };
    
    public static final String[] WORLD_NAME_EU = new String[] {
        "Fissure of Woe",   "Drakkar Lake [DE]",    "Baruch Bay [SP]",      "Miller's Sound [DE]",  "Augury Rock [FR]",
        "Gandara",          "Piken Square",         "Far Shiverpeaks",      "Aurora Glade",         "Riverside [DE]",
        "Seafarer's Rest",  "Elona Reach [DE]",     "Vizunah Square [FR]",  "Fort Ranik [FR]",      "Desolation",
        "Blacktide",        "Abaddon's Mouth [DE]", "Dzagonur [DE]",        "Ring of Fire",         "Jade Sea [FR]",
        "Gunnar's Hold",    "Underworld",           "Ruins of Surmia",      "Kodash [DE]",          "Whiteside Ridge",
        "Arborstone [FR]",  "Vabbi"
    };
    
    public static final String[] MAP_NAMES = new String[] {
        "Sparkfly Fen",     "Southsun Cove",        "Harathi Hinterlands",  "Frostgorge Sound",     "Malchor's Leap",
        "Queensdale",       "Wayfarer Foothills",   "Lornar's Pass",        "Bloodtide Coast",      "Fields of Ruin",
        "Caledon Forest",   "Gendarran Fields",     "Mount Maelstrom",      "Plains of Ashford",    "Kessex Hills",
        "Brisban Wildlands","Iron Marches",         "Timberline Falls",     "Cursed Shore",         "Snowden Drifts",
        "Fireheart Rise",   "Dredgehaunt Cliffs",   "Blazeridge Steppes",   "Metrica Province",     "Straits of Devastation",
        "Diessa Plateau"
    };

}
