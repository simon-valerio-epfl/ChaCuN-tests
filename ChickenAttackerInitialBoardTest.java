package ch.epfl.chacun;

import ch.epfl.chacun.tile.Tiles;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class ChickenAttackerInitialBoardTest {

    Board getStandardBoard () {

        Tile tile56 = TileReader.readTileFromCSV(56);
        Tile tile27 = TileReader.readTileFromCSV(27);
        Tile tile42 = TileReader.readTileFromCSV(42);
        Tile tile60 = TileReader.readTileFromCSV(60);
        Tile tile94 = TileReader.readTileFromCSV(94);
        Tile tile49 = TileReader.readTileFromCSV(49);
        Tile tile61 = TileReader.readTileFromCSV(61);
        Tile tile62 = TileReader.readTileFromCSV(62);

        PlacedTile placedTile62 = new PlacedTile(tile62, PlayerColor.RED, Rotation.NONE, new Pos(0, 0));
        PlacedTile placedTile27 = new PlacedTile(tile27, PlayerColor.RED, Rotation.NONE, new Pos(0, 1));
        PlacedTile placedTile61 = new PlacedTile(tile61, PlayerColor.RED, Rotation.NONE, new Pos(1, 0));
        PlacedTile placedTile49 = new PlacedTile(tile49, PlayerColor.RED, Rotation.NONE, new Pos(1, 1));
        PlacedTile placedTile94 = new PlacedTile(tile94, PlayerColor.RED, Rotation.NONE, new Pos(2, 0));
        PlacedTile placedTile56 = new PlacedTile(tile56, PlayerColor.RED, Rotation.NONE, new Pos(2, 1));
        PlacedTile placedTile60 = new PlacedTile(tile60, PlayerColor.RED, Rotation.RIGHT, new Pos(3, 0));
        PlacedTile placedTile42 = new PlacedTile(tile42, PlayerColor.RED, Rotation.LEFT, new Pos(3, 1));

        Board board = Board.EMPTY
                .withNewTile(placedTile62)
                .withNewTile(placedTile27)
                .withNewTile(placedTile61)
                .withNewTile(placedTile49)
                .withNewTile(placedTile94)
                .withNewTile(placedTile56)
                .withNewTile(placedTile60)
                .withNewTile(placedTile42);

        return board;
    }

    @Test
    void testClassIsFinal() {
        assertTrue(Modifier.isFinal(Board.class.getModifiers()));
    }

    @Test
    void testWithNewTile() {
        Zone.River river = new Zone.River(563, 0, null);
        Zone.Meadow meadow = new Zone.Meadow(560, List.of(), null);
        Zone.Meadow meadow1 = new Zone.Meadow(562, List.of(), null);

        TileSide.River riverSide = new TileSide.River(meadow1, river, meadow);
        TileSide.Meadow meadowSide = new TileSide.Meadow(meadow);
        TileSide.Meadow meadowSide1 = new TileSide.Meadow(meadow1);

        Tile tile = new Tile(56, Tile.Kind.NORMAL, riverSide, meadowSide, meadowSide1, meadowSide1);
        PlacedTile placedTile = new PlacedTile(tile, PlayerColor.RED, Rotation.NONE, new Pos(0, 0));

        Board board = Board.EMPTY;

        assertEquals(56, board.withNewTile(placedTile).tileAt(new Pos(0, 0)).id());
    }

    @Test
    void testInsertionPositions() {
        Tile tile56 = TileReader.readTileFromCSV(56);

        PlacedTile placedTile56 = new PlacedTile(tile56, null, Rotation.NONE, new Pos(0, 0));
        Board board = Board.EMPTY.withNewTile(placedTile56);

        assertEquals(4, board.insertionPositions().size());
        assertEquals(Set.of(
                new Pos(0, 1),
                new Pos(1, 0),
                new Pos(0, -1),
                new Pos(-1, 0)
        ), board.insertionPositions());

        // try out of bounds insertion positions
        Tile tile10 = TileReader.readTileFromCSV(10);
        PlacedTile placedTile10 = new PlacedTile(tile10, null, Rotation.NONE, new Pos(-12, -12));
        Board newBoard = Board.EMPTY.withNewTile(placedTile10);

        assertEquals(2, newBoard.insertionPositions().size());
    }

    @Test
    void testCouldPlaceTile(){
        Tile tile29 = TileReader.readTileFromCSV(29);
        Tile tile62 = TileReader.readTileFromCSV(62);
        Tile tile17 = TileReader.readTileFromCSV(17);

        PlacedTile placedTile29 = new PlacedTile(tile29, null, Rotation.NONE, new Pos(0, 0));
        Board board = Board.EMPTY.withNewTile(placedTile29);

        assertFalse(board.couldPlaceTile(tile62));
        assertTrue(board.couldPlaceTile(tile17));
    }

    @Test
    void testAdjacentMeadow() {
        int meadowZone62 = 620;
        int meadowZone27 = 272;

        Tile tile56 = TileReader.readTileFromCSV(56);
        Tile tile27 = TileReader.readTileFromCSV(27);
        Tile tile42 = TileReader.readTileFromCSV(42);
        Tile tile60 = TileReader.readTileFromCSV(60);
        Tile tile94 = TileReader.readTileFromCSV(94);
        Tile tile49 = TileReader.readTileFromCSV(49);
        Tile tile61 = TileReader.readTileFromCSV(61);
        Tile tile62 = TileReader.readTileFromCSV(62);

        int shift = 9;

        PlacedTile placedTile62 = new PlacedTile(tile62, PlayerColor.RED, Rotation.NONE, new Pos(0 + shift, 0));
        PlacedTile placedTile27 = new PlacedTile(tile27, PlayerColor.RED, Rotation.NONE, new Pos(0 + shift, 1));
        PlacedTile placedTile61 = new PlacedTile(tile61, PlayerColor.RED, Rotation.NONE, new Pos(1 + shift, 0));
        PlacedTile placedTile49 = new PlacedTile(tile49, PlayerColor.RED, Rotation.NONE, new Pos(1 + shift, 1));
        PlacedTile placedTile94 = new PlacedTile(tile94, PlayerColor.RED, Rotation.NONE, new Pos(2 + shift, 0));
        PlacedTile placedTile56 = new PlacedTile(tile56, PlayerColor.RED, Rotation.NONE, new Pos(2 + shift, 1));
        PlacedTile placedTile60 = new PlacedTile(tile60, PlayerColor.RED, Rotation.RIGHT, new Pos(3 + shift, 0));
        PlacedTile placedTile42 = new PlacedTile(tile42, PlayerColor.RED, Rotation.LEFT, new Pos(3 + shift, 1));

        Board board = Board.EMPTY
                .withNewTile(placedTile62)
                .withOccupant(new Occupant(Occupant.Kind.PAWN, meadowZone62))
                .withNewTile(placedTile27)
                .withOccupant(new Occupant(Occupant.Kind.PAWN, meadowZone27))
                .withNewTile(placedTile61)
                .withNewTile(placedTile49)
                .withNewTile(placedTile94)
                .withNewTile(placedTile56)
                .withNewTile(placedTile60)
                .withNewTile(placedTile42);

        assertEquals(5, board.adjacentMeadow(new Pos(2 + shift, 0), new Zone.Meadow(941, List.of(), Zone.SpecialPower.HUNTING_TRAP)).tileIds().size());
        assertEquals(0, board.adjacentMeadow(new Pos(2 + shift, 0), new Zone.Meadow(941, List.of(), Zone.SpecialPower.HUNTING_TRAP)).openConnections());
        assertEquals(1, board.adjacentMeadow(new Pos(2 + shift, 0), new Zone.Meadow(941, List.of(), Zone.SpecialPower.HUNTING_TRAP)).occupants().size());

        assertEquals(2, board.occupantCount(PlayerColor.RED, Occupant.Kind.PAWN));

        assertEquals(Set.of(
                new Occupant(Occupant.Kind.PAWN, meadowZone27),
                new Occupant(Occupant.Kind.PAWN, meadowZone62)
        ), board.occupants());

        board = board.withoutOccupant(new Occupant(Occupant.Kind.PAWN, meadowZone62));
        assertEquals(1, board.occupantCount(PlayerColor.RED, Occupant.Kind.PAWN));

        assertEquals(10, board.insertionPositions().size());

        assertEquals(Set.of(
                new Occupant(Occupant.Kind.PAWN, 272)
        ), board.occupants());

    }

    private PlacedTile placeTile(Tile tile, Pos pos) {
        return new PlacedTile(tile, null, Rotation.NONE, pos);
    }

    @Test
    void testAdajcentMeadow2 () {
        PlacedTile tile94 = placeTile(Tiles.TILES.get(94), new Pos(11, 12));
        PlacedTile tile61 = placeTile(Tiles.TILES.get(61), new Pos(10, 12));
        PlacedTile tile49 = placeTile(Tiles.TILES.get(49), new Pos(10, 11));
        PlacedTile tile26 = placeTile(Tiles.TILES.get(26), new Pos(9, 11));
        PlacedTile tile56 = placeTile(Tiles.TILES.get(56), new Pos(11, 11));
        PlacedTile tile42 = new PlacedTile(Tiles.TILES.get(42), null, Rotation.LEFT, new Pos(12, 11));

        Board board = Board.EMPTY
                .withNewTile(tile94)
                .withNewTile(tile61)
                .withNewTile(tile49)
                .withNewTile(tile26)
                .withNewTile(tile56)
                .withNewTile(tile42);

        /*
        List<Integer> expectedArr = List.of(94, 61, 49, 26, 56, 42);
        for (int i = 0; i < board.orderedTileIndexes.length; i++) {
            assertEquals(expectedArr.get(i), board.placedTiles[board.orderedTileIndexes[i]].id());
        }
        */

        assertEquals(Set.of(94, 61, 49, 56), board.adjacentMeadow(new Pos(11, 12), new Zone.Meadow(941, List.of(), Zone.SpecialPower.HUNTING_TRAP)).tileIds().stream().collect(Collectors.toSet()));
    }

    @Test
    void testLastPlacedTileOnEmptyBoard() {
        assertNull(Board.EMPTY.lastPlacedTile());
    }

    @Test
    void testUnknownTileAt() {
        assertNull(Board.EMPTY.tileAt(new Pos(0, 0)));
        Board board = Board.EMPTY.withNewTile(new PlacedTile(TileReader.readTileFromCSV(56), PlayerColor.RED, Rotation.NONE, new Pos(0, 0)));
        assertNull(board.tileAt(new Pos(1, 0)));
        assertNull(board.tileAt(new Pos(-10000, 1000)));
    }

    @Test
    void testTileWithId() {
        assertThrows(IllegalArgumentException.class, () -> Board.EMPTY.tileWithId(56));
        Board board = Board.EMPTY.withNewTile(new PlacedTile(TileReader.readTileFromCSV(56), PlayerColor.RED, Rotation.NONE, new Pos(0, 0)));
        assertEquals(56, board.tileWithId(56).id());
        assertThrows(IllegalArgumentException.class, () -> board.tileWithId(100000));
        assertThrows(IllegalArgumentException.class, () -> board.tileWithId(625));
        assertDoesNotThrow(() -> board.tileWithId(56));
    }

    @Test
    void cancelledAnimals() {
        Board board = Board.EMPTY;
        board = board.withMoreCancelledAnimals(Set.of(new Animal(10, Animal.Kind.DEER)));
        assertEquals(1, board.cancelledAnimals().size());
        // test immutability
        try {
            board.cancelledAnimals().add(new Animal(10, Animal.Kind.MAMMOTH));
        } catch (UnsupportedOperationException e) {
            // expected
        }
        assertEquals(1, board.cancelledAnimals().size());
    }

    @Test
    void testForestAndMeadowArea() {
        Tile tile62 = TileReader.readTileFromCSV(62);
        Board board = getStandardBoard();
        assertEquals(7, board.meadowArea((Zone.Meadow) tile62.zones().stream().findFirst().get()).tileIds().size());
        assertThrows(IllegalArgumentException.class, () -> board.meadowArea(new Zone.Meadow(0, List.of(), null)));
    }

    @Test
    void testForestArea() {
        Tile tile60 = TileReader.readTileFromCSV(60);
        Board board = getStandardBoard();
        assertEquals(3, board.forestArea((Zone.Forest) tile60.zones().stream().filter(zone -> zone instanceof Zone.Forest).findFirst().get()).tileIds().size());
        assertThrows(IllegalArgumentException.class, () -> board.forestArea(new Zone.Forest(0, Zone.Forest.Kind.WITH_MENHIR)));
    }

    @Test
    void testRiverArea() {
        Tile tile56 = TileReader.readTileFromCSV(56);
        Board board = getStandardBoard();
        assertEquals(3, board.riverArea((Zone.River) tile56.zones().stream().filter(zone -> zone instanceof Zone.River).findFirst().get()).tileIds().size());
        assertThrows(IllegalArgumentException.class, () -> board.riverArea(new Zone.River(0, 0, null)));
    }

    @Test
    void testRiverSystemArea() {
        Tile tile56 = TileReader.readTileFromCSV(56);
        Board board = getStandardBoard();
        assertEquals(3, board.riverSystemArea((Zone.River) tile56.zones().stream().filter(zone -> zone instanceof Zone.River).findFirst().get()).tileIds().size());
        assertThrows(IllegalArgumentException.class, () -> board.riverSystemArea(new Zone.River(0, 0, null)));
    }

    @Test
    void testLastPlacedTile() {
        PlacedTile placedTile61 = new PlacedTile(Tiles.TILES.get(61), PlayerColor.RED, Rotation.LEFT, new Pos(3, 1));
        PlacedTile placedTile62 = new PlacedTile(Tiles.TILES.get(62), PlayerColor.RED, Rotation.NONE, new Pos(4, 1));

        Board board = Board.EMPTY.withNewTile(placedTile61);

        assertEquals(placedTile61, board.lastPlacedTile());
        assertEquals(placedTile62, board.withNewTile(placedTile62).lastPlacedTile());

        assertNull(Board.EMPTY.lastPlacedTile());
    }

    @Test
    void testOutOfBoundsPlaceTile() {
        Tile tile62 = TileReader.readTileFromCSV(62);
        PlacedTile placedTile62 = new PlacedTile(tile62, PlayerColor.RED, Rotation.LEFT, new Pos(0, -12));
        PlacedTile placedTile622 = new PlacedTile(tile62, PlayerColor.RED, Rotation.LEFT, new Pos(-13, 0));
        PlacedTile placedTile623 = new PlacedTile(tile62, PlayerColor.RED, Rotation.LEFT, new Pos(-13, 1));
        PlacedTile placedTile624 = new PlacedTile(tile62, PlayerColor.RED, Rotation.LEFT, new Pos(-12, 0));
        PlacedTile placedTile625 = new PlacedTile(tile62, PlayerColor.RED, Rotation.LEFT, new Pos(13, -13));
        PlacedTile placedTile626 = new PlacedTile(tile62, PlayerColor.RED, Rotation.LEFT, new Pos(12, -12));
        assertThrows(IllegalArgumentException.class, () -> Board.EMPTY.withNewTile(placedTile62).withNewTile(placedTile622));
        assertThrows(IllegalArgumentException.class, () -> Board.EMPTY.withNewTile(placedTile622).withNewTile(placedTile623));
        assertDoesNotThrow(() -> Board.EMPTY.withNewTile(placedTile622).withNewTile(placedTile624));
        assertThrows(IllegalArgumentException.class, () -> Board.EMPTY.withNewTile(placedTile625).withNewTile(placedTile626));
    }

    @Test
    void testCannotPlace() {
        Tile tile13 = TileReader.readTileFromCSV(13);
        PlacedTile placedTile13 = new PlacedTile(tile13, PlayerColor.RED, Rotation.NONE, new Pos(0, 0));
        Tile tile35 = TileReader.readTileFromCSV(35);
        PlacedTile placedTile35 = new PlacedTile(tile35, PlayerColor.RED, Rotation.NONE, new Pos(0, 1));
        assertThrows(IllegalArgumentException.class, () -> Board.EMPTY.withNewTile(placedTile13).withNewTile(placedTile35));
        Tile tile61 = TileReader.readTileFromCSV(61);
        PlacedTile placedTile61 = new PlacedTile(tile61, PlayerColor.RED, Rotation.NONE, new Pos(0, 1));
        assertThrows(IllegalArgumentException.class, () -> Board.EMPTY.withNewTile(placedTile13).withNewTile(placedTile61));

        Tile tile57 = Tiles.TILES.get(57);
        PlacedTile placedTile57 = new PlacedTile(tile57, PlayerColor.RED, Rotation.NONE, new Pos(0, 0));
        Tile tile68 = TileReader.readTileFromCSV(68);
        PlacedTile placedTile68 = new PlacedTile(tile68, PlayerColor.RED, Rotation.NONE, new Pos(0, 1));
        assertDoesNotThrow(() -> Board.EMPTY.withNewTile(placedTile57).withNewTile(placedTile68));
    }

    @Test
    void testBoardEquals() {
        Board board = getStandardBoard();
        Board board2 = getStandardBoard();
        assertEquals(board, board2);

        Board board3 = Board.EMPTY;
        assertNotEquals(board, board3);

        Tile tile42 = TileReader.readTileFromCSV(42);
        PlacedTile placedTile42 = new PlacedTile(tile42, PlayerColor.RED, Rotation.LEFT, new Pos(3, 1));

        Board board4 = Board.EMPTY
                .withNewTile(placedTile42);

        board3 = board3.withNewTile(placedTile42);

        assertEquals(board3, board4);

        board3 = board3.withMoreCancelledAnimals(Set.of(new Animal(10, Animal.Kind.DEER)));
        assertNotEquals(board3, board4);

        board4 = board4.withMoreCancelledAnimals(Set.of(new Animal(10, Animal.Kind.DEER)));
        assertEquals(board3, board4);

        board4 = board4.withOccupant(new Occupant(Occupant.Kind.PAWN, 421));
        assertNotEquals(board3, board4);

        board3 = board3.withOccupant(new Occupant(Occupant.Kind.PAWN, 421));
        assertEquals(board3, board4);

        Animal animal = new Animal(10, Animal.Kind.DEER);
        assertNotEquals(board3, animal);

        assertFalse(board3.equals(null));
    }

    @Test
    void testWithoutGatherersOrFishersIn() {
        int meadowZone62 = 620;
        int meadowZone27 = 272;

        Tile tile56 = TileReader.readTileFromCSV(56);
        Tile tile27 = TileReader.readTileFromCSV(27);
        Tile tile42 = TileReader.readTileFromCSV(42);
        Tile tile60 = TileReader.readTileFromCSV(60);
        Tile tile94 = TileReader.readTileFromCSV(94);
        Tile tile49 = TileReader.readTileFromCSV(49);
        Tile tile61 = TileReader.readTileFromCSV(61);
        Tile tile62 = TileReader.readTileFromCSV(62);

        PlacedTile placedTile62 = new PlacedTile(tile62, PlayerColor.RED, Rotation.NONE, new Pos(0, 0));
        PlacedTile placedTile27 = new PlacedTile(tile27, PlayerColor.RED, Rotation.NONE, new Pos(0, 1));
        PlacedTile placedTile61 = new PlacedTile(tile61, PlayerColor.RED, Rotation.NONE, new Pos(1, 0));
        PlacedTile placedTile49 = new PlacedTile(tile49, PlayerColor.RED, Rotation.NONE, new Pos(1, 1));
        PlacedTile placedTile94 = new PlacedTile(tile94, PlayerColor.RED, Rotation.NONE, new Pos(2, 0));
        PlacedTile placedTile56 = new PlacedTile(tile56, PlayerColor.RED, Rotation.NONE, new Pos(2, 1));
        PlacedTile placedTile60 = new PlacedTile(tile60, PlayerColor.RED, Rotation.RIGHT, new Pos(3, 0));
        PlacedTile placedTile42 = new PlacedTile(tile42, PlayerColor.RED, Rotation.LEFT, new Pos(3, 1));



        Board board = Board.EMPTY
                .withNewTile(placedTile62)
                .withOccupant(new Occupant(Occupant.Kind.PAWN, meadowZone62))
                .withNewTile(placedTile27)
                //.withOccupant(new Occupant(Occupant.Kind.PAWN, meadowZone27))
                .withNewTile(placedTile61)
                .withNewTile(placedTile49)
                .withNewTile(placedTile94)
                .withNewTile(placedTile56)
                .withNewTile(placedTile60)
                .withNewTile(placedTile42)
                .withOccupant(new Occupant(Occupant.Kind.PAWN, 421))
                .withOccupant(new Occupant(Occupant.Kind.PAWN, placedTile27.riverZones().stream().findFirst().get().id()));

        board = board.withoutGatherersOrFishersIn(
                Set.of(
                        board.forestArea(placedTile42.forestZones().stream().findFirst().get())
                ),
                Set.of(
                        board.riverArea(placedTile27.riverZones().stream().findFirst().get())
                )
        );

        assertEquals(2, board.occupantCount(PlayerColor.RED, Occupant.Kind.PAWN));
        assertEquals(0, board.forestArea(placedTile42.forestZones().stream().findFirst().get()).occupants().size());
    }

    @Test
    void testRiversClosedByLastTile() {

        Board board = getStandardBoard();

        Tile tile87 = TileReader.readTileFromCSV(87);
        PlacedTile placedTile87 = new PlacedTile(tile87, PlayerColor.RED, Rotation.RIGHT, new Pos(0, 2));

        assertEquals(0, board.riversClosedByLastTile().size());

        board = board.withNewTile(placedTile87);

        assertEquals(1, board.riversClosedByLastTile().size());
        assertEquals(board.riverArea(placedTile87.riverZones().stream().findFirst().get()), board.riversClosedByLastTile().stream().findFirst().get());

    }

    @Test
    void testForestsClosedByLastTile() {

        Board board = getStandardBoard();

        Tile tile73 = TileReader.readTileFromCSV(73);
        PlacedTile placedTile73 = new PlacedTile(tile73, PlayerColor.RED, Rotation.NONE, new Pos(-1, 1));

        assertEquals(0, board.forestsClosedByLastTile().size());

        board = board.withNewTile(placedTile73);

        assertEquals(1, board.forestsClosedByLastTile().size());
        assertEquals(board.forestArea(placedTile73.forestZones().stream().filter(zone -> zone.localId() == 3).findFirst().get()), board.forestsClosedByLastTile().stream().findFirst().get());

    }

}
