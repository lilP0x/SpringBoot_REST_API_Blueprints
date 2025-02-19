/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.test.persistence.impl.Impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author hcadavid
 */
public class InMemoryPersistenceTest {
    
    @Test
    public void saveNewAndLoadTest() throws BlueprintPersistenceException, BlueprintNotFoundException{
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();

        Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15)};
        Blueprint bp0=new Blueprint("mack", "mypaint",pts0);
        
        ibpp.saveBlueprint(bp0);
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        
        ibpp.saveBlueprint(bp);
        
        assertNotNull("Loading a previously stored blueprint returned null.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()));
        
        assertEquals("Loading a previously stored blueprint returned a different blueprint.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()), bp);
        
    }


    @Test
    public void saveExistingBpTest() throws BlueprintPersistenceException{
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        ibpp.saveBlueprint(bp);
       
        Point[] pts2=new Point[]{new Point(10, 10),new Point(20, 20)};
        Blueprint bp2=new Blueprint("john", "thepaint",pts2);

        try{
            ibpp.saveBlueprint(bp2);
            fail("An exception was expected after saving a second blueprint with the same name and autor");
        }
        catch (BlueprintPersistenceException ex){

        }        
    }

    @Test
    public void getBlueprintTest(){
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();

        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);

        try {
            ibpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }

        try {
            Blueprint blueprint = ibpp.getBlueprint("john","thepaint");
            assertEquals(blueprint, bp);

        } catch (BlueprintNotFoundException ex) {
            fail("Blueprint persistence failed search the blueprint.");
        }
    }

    @Test
    public void getNotFoundBlueprintTest(){
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
        try {
            Blueprint blueprint = ibpp.getBlueprint("john","thepaint");
        } catch (BlueprintNotFoundException ex) {
            assertEquals("The blueprint not found thepaint" , ex.getMessage());;
        }
    }

    @Test
    public void getBlueprintByAuthorTest() throws BlueprintPersistenceException {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();
    
        Point[] pts1 = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp1 = new Blueprint("john", "thepaint1", pts1);  
    
        Point[] pts2 = new Point[]{new Point(10, 10), new Point(20, 20)};
        Blueprint bp2 = new Blueprint("john", "thepaint2", pts2);  
    
        ibpp.saveBlueprint(bp1);
        ibpp.saveBlueprint(bp2);
    
        Set<Blueprint> expectedBlueprints = new HashSet<>();
        expectedBlueprints.add(bp1);
        expectedBlueprints.add(bp2);
    
        try {
            Set<Blueprint> blueprintAuthor = ibpp.getBlueprintsByAuthor("john"); 
            assertEquals(expectedBlueprints, blueprintAuthor);
        } catch (BlueprintNotFoundException ex) {
            fail("No blueprints found for author: john");
        }
    }

    @Test
    public void getBlueprintsByNonExistentAuthorTest() throws BlueprintPersistenceException {
    
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();
    
        Point[] pts1 = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp1 = new Blueprint("john", "thepaint1", pts1); 

        ibpp.saveBlueprint(bp1);
       
        try {
            Set<Blueprint> blueprintAuthor = ibpp.getBlueprintsByAuthor("Maria"); 
        } catch (BlueprintNotFoundException ex) {
            assertEquals("No blueprints found for author: Maria", ex.getMessage());;
        }
    }
}