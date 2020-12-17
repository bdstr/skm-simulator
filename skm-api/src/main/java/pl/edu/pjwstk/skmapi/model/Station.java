package pl.edu.pjwstk.skmapi.model;

public enum Station {
    GDYNIA_GLOWNA(0, "Gdynia Główna", 2),
    GDYNIA_WZGORZE_SW_MAKSYM(1, "Gdynia Wzgórze Św.Maksym.", 0),
    GDYNIA_REDLOWO(2, "Gdynia Redłowo",0),
    GDYNIA_ORLOWO(3, "Gdynia Orłowo",0),
    SOPOT_KAMIENNY_POTOK(4, "Sopot Kamienny Potok",0),
    SOPOT(5, "Sopot",0),
    SOPOT_WYSCIGI(6, "Sopot Wyścigi",0),
    GDANSK_ZABIANKA(7, "Gdańsk Żabianka - Awfis",0),
    GDANSK_OLIWA(8, "Gdańsk Oliwa",0),
    GDANSK_PRZYMORZE(9, "Gdańsk Przymorze-Uniwer.",0),
    GDANSK_ZASPA(10, "Gdańsk Zaspa",0),
    GDANSK_WRZESZCZ(11, "Gdańsk Wrzeszcz",0),
    GDANSK_POLITECHNIKA(12, "Gdańsk Politechnika",0),
    GDANSK_STOCZNIA(13, "Gdańsk Stocznia",0),
    GDANSK_GLOWNY(14, "Gdańsk Główny",2);

    private final int id;
    private final String name;
    private final int pauseTime;

    Station(int id, String name, int pauseTime) {
        this.id = id;
        this.name = name;
        this.pauseTime = pauseTime;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPauseTime() {
        return pauseTime;
    }

    public boolean isLastStation() {
        return id == Station.values().length - 1 || id == 0;
    }
}
