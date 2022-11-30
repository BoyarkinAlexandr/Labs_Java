package com.javalabs.lab3;

import java.util.Objects;

/**
 * This class represents a specific location in a 2D map.  Coordinates are
 * integer values.
 **/
public class Location {
    /**
     * X coordinate of this location.
     **/
    public int xCoord;

    /**
     * Y coordinate of this location.
     **/
    public int yCoord;


    /**
     * Creates a new location with the specified integer coordinates.
     **/
    public Location(int x, int y) {
        xCoord = x;
        yCoord = y;
    }

    /**
     * Creates a new location with coordinates (0, 0).
     **/
    public Location() {
        this(0, 0);
    }

    @Override
    public boolean equals(Object obj)
    {
        // Если объект Location?
        if (obj instanceof Location) {

            // Приведем другой объект к типу Location, затем сравниваем. Возвращаем true, если они равны.
            Location other = (Location) obj;
            if (xCoord == other.xCoord && yCoord == other.yCoord) {
                return true;
            }
        }

        // Если мы попали сюда, то они не равны.  Возвращаем false.
        return false;
    }

    //Предоставляет хэш-код для каждого местоположения.
    public int hashCode(){
        return Objects.hash(xCoord,yCoord);
    }
}
