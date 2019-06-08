package ru.konverdev.parallax.model;

import java.util.ArrayList;
import java.util.Date;

import ru.konverdev.parallax.model.classes.Direction;
import ru.konverdev.parallax.model.classes.Station;
import ru.konverdev.parallax.model.yandex_api.Stations;

public class Route {
    public static final String NO_DIRECTION = "Выберите направления";
    public static final String NO_DATE = "Выберите дату отправления";
    public static final String NO_ALL = "Выберите дату отправления";
    public static final String ERROR_YANDEX = "Не удалось получить список станций";
    public static final String ERROR_PARALLAX_API = "Не удалось получить список направлений";
    public static final String ERROR_NO_STATIONS = "Станции не найдены";

    Direction direction;
    Date date;
    Stations stations;

    public Route() {
    }

    public Route(Direction direction, Date date) {
        this.direction = direction;
        this.date = date;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setStations(Stations stations) {
        this.stations = stations;
    }

    public ArrayList<Station> getStations() {
        return stations.GetStations();
    }

    public boolean isValid() {
        if (date == null || direction == null) {
            return false;
        } else {
            return true;
        }
    }
}
