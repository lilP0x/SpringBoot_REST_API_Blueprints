package edu.eci.arsw.blueprints.test.persistence.impl.Impl;

import edu.eci.arsw.blueprints.filter.RedundancyFiltering;
import edu.eci.arsw.blueprints.filter.SubsamplingFiltering;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import static org.junit.Assert.*;
import org.junit.Test;

public class FilterTest {

    @Test
    public void testRedundancyFilter() {
        RedundancyFiltering filter = new RedundancyFiltering();
        Point[] points = {new Point(0, 0), new Point(0, 0), new Point(1, 1)};
        Blueprint bp = new Blueprint("author", "name", points);
        Blueprint filtered = filter.filter(bp);

        // Verificar que solo hay 2 puntos únicos
        assertEquals(2, filtered.getPoints().size());
    }

    @Test
    public void testSubsamplingFilter() {
        SubsamplingFiltering filter = new SubsamplingFiltering();
        Point[] points = {new Point(0, 0), new Point(1, 1), new Point(2, 2)};
        Blueprint bp = new Blueprint("author", "name", points);
        Blueprint filtered = filter.filter(bp);

        // Verificar que se submuestrean correctamente los puntos
        assertEquals(2, filtered.getPoints().size());
    }
}