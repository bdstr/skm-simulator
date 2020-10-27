package pl.edu.pjwstk.skmclient.model;

public enum TrainStation {
    GDYNIA_GLOWNA(0, "Gdynia Główna"),
    GDYNIA_WZGORZE_SW_MAKSYM(1, "Gdynia Wzgórze Św.Maksym."),
    GDYNIA_REDLOWO(2, "Gdynia Redłowo"),
    GDYNIA_ORLOWO(3, "Gdynia Orłowo"),
    SOPOT_KAMIENNY_POTOK(4, "Sopot Kamienny Potok"),
    SOPOT(5, "Sopot"),
    SOPOT_WYSCIGI(6, "Sopot Wyścigi"),
    GDANSK_ZABIANKA(7, "Gdańsk Żabianka - Awfis"),
    GDANSK_OLIWA(8, "Gdańsk Oliwa"),
    GDANSK_PRZYMORZE(9, "Gdańsk Przymorze-Uniwer."),
    GDANSK_ZASPA(10, "Gdańsk Zaspa"),
    GDANSK_WRZESZCZ(11, "Gdańsk Wrzeszcz"),
    GDANSK_POLITECHNIKA(12, "Gdańsk Politechnika"),
    GDANSK_STOCZNIA(13, "Gdańsk Stocznia"),
    GDANSK_GLOWNY(14, "Gdańsk Główny");

    private final int id;
    private final String name;

    TrainStation(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isLastStation() {
        return id == TrainStation.values().length - 1 || id == 0;
    }
}
