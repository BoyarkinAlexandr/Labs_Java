package com.javalabs.lab3;

import java.util.HashMap;

/**
 * This class stores the basic state necessary for the A* algorithm to compute a
 * path across a map.  This state includes a collection of "open waypoints" and
 * another collection of "closed waypoints."  In addition, this class provides
 * the basic operations that the A* pathfinding algorithm needs to perform its
 * processing.
 **/
public class AStarState {
    /**
     * This is a reference to the map that the A* algorithm is navigating.
     **/
    private Map2D map;

    private final HashMap<Location, Waypoint> closedWaypoints = new HashMap<>(); //открытые точки
    private final HashMap<Location, Waypoint> openedWaypoints = new HashMap<>(); //закрытые точки


    /**
     * Initialize a new state object for the A* pathfinding algorithm to use.
     **/
    public AStarState(Map2D map) {
        if (map == null)
            throw new NullPointerException("map cannot be null");

        this.map = map;
    }

    /**
     * Returns the map that the A* pathfinder is navigating.
     **/
    public Map2D getMap() {
        return map;
    }


    /**
     * Этот метод добавляет путевую точку к (или потенциально обновляет уже имеющуюся путевую точку
     * в) коллекции "открытые путевые точки".  Если еще нет открытой
     * путевой точки в местоположении новой путевой точки, тогда новая путевая точка просто
     * добавляется в коллекцию.  Однако, если в местоположении
     * новой путевой точки уже есть путевая точка, новая путевая точка заменяет только старую <em>
     * если</em> значение "предыдущей стоимости" новой путевой точки меньше текущего
     * значение "предыдущей стоимости" путевой точки.
     **/
    public boolean addOpenWaypoint(Waypoint newWP) {

        // Находит местонахождение нового waypoint.
        Location location = newWP.getLocation();
        // Проверяет, есть ли уже открытая путевая точка в новом
        // местоположении путевой точки.
        // Если в новой путевой точке уже есть открытая путевая точка
        // местоположения, проверяет, является ли у новой путевая точка предыдущее
        // значение "стоимости" меньше, чем предыдущее текущей путевой точки
        // стоимость їзначение.
        if (!openedWaypoints.containsKey(location) || newWP.getPreviousCost() < openedWaypoints.get(location).getPreviousCost()) {
            openedWaypoints.put(location, newWP);
            return true;       // Если значение "предыдущей стоимости" новой путевой точки меньше, чем
                             // значение "предыдущей стоимости" текущей путевой точки, новая путевая точка
                            // заменяет старую путевую точку и возвращает true.

        }
        // Если значение "предыдущей стоимости" новой путевой точки не меньше, чем
        // значение "предыдущей стоимости" текущей путевой точки, возврат false.
        return false;
    }

    // Возвращает текущее количество открытых путевых точек.
    public int numOpenWaypoints() {

        return openedWaypoints.size();
    }

    /**
     * Этот метод сканирует все открытые путевые точки и возвращает путевую точку
     * с минимальной общей стоимостью.  Если открытых путевых точек нет, этот метод
     * возвращает <code>null</code>.
     **/
    public Waypoint getMinOpenWaypoint() {

        Location minLocation = null;
        float minTotalCost = Float.MAX_VALUE;
        for (Location key : openedWaypoints.keySet()) {    // перебираем все открытые waypoints.
            float totalCost = openedWaypoints.get(key).getTotalCost(); // сохраняет общую стоимость текущего waypoint.

            // если общая стоимость для текущей waypoint  лучше (ниже)
            // чем сохраненная стоимость для сохраненной лучшей waypoint, то обменяем их
            if (totalCost < minTotalCost) {
                minTotalCost = totalCost;
                minLocation = key;
            }
        }
        // возвращает путевую точку с минимальной общей стоимостью.
        return openedWaypoints.get(minLocation);
    }


    /**
     * Этот метод перемещает путевую точку в указанном месте из открытого в список закрыто.
     **/
    public void closeWaypoint(Location loc) {
        Waypoint wpToClose = openedWaypoints.remove(loc);
        if (wpToClose != null) {
            closedWaypoints.put(loc, wpToClose);
        }
    }

    /**
     * Возвращает значение true, если коллекция закрытых путевых точек содержит путевую точку
     * для указанного местоположения.
     **/
    public boolean isLocationClosed(Location loc) {

        return closedWaypoints.containsKey(loc);
    }
}
