package simulator.personal;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by alex on 10/24/2016.
 */
public class StringConstants
{
    private static final String[] names = {
            "Subliminal App",
            "Dude App",
            "City App",
            "Environ App",
            "Envision App",
            "Variety App",
            "Pickup App",
            "Voltage App",
            "Spice App",
            "Heart App",
            "Exchange App",
            "Table App",
            "Scion App",
            "BlueMoon App",
            "Salmon App",
            "Navajo App",
            "Treehouse App",
            "Junkie App",
            "Proteus App",
            "Gun App",
            "Freeway App",
            "Neuro App",
            "Fest App",
            "Jasmine App",
            "Sea App",
            "Window App",
            "Media App",
            "Cupcake App",
            "Solution App",
            "Addict App",
            "Ways App",
            "My App",
            "Baby App",
            "Engineered App",
            "Map App",
            "Acumen App",
            "BigIdea App",
            "Freelance App",
            "Lens App",
            "NewImage App",
            "Broadway App",
            "FirstPlace App",
            "Marketplace App",
            "Exact App",
            "War App",
            "PageOne App",
            "Angus App",
            "tastic App",
            "Bravo App",
            "Skills App",
            "Quant App",
            "Lifestyle App",
            "Embedded App",
            "ThreeSixty App",
            "Sunny App",
            "Search App",
            "Goodwin App",
            "Centaur App",
            "Charisma App",
            "Empress App",
            "Hotrod App",
            "Downtown App",
            "Masterpiece App",
            "Surge App",
            "BlueSky App",
            "Lord App",
            "HawkEye App",
            "Premium App",
            "Ultimate App",
            "Storm App",
            "Download App",
            "Jarvis App"
    };

    public static String getName()
    {
        return names[ThreadLocalRandom.current().nextInt(names.length)];
    }

    private StringConstants() throws InstantiationException
    {
        throw new InstantiationException("Class cannot be instantiated");
    }
}
