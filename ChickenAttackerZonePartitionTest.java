package ch.epfl.chacun;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ChickenAttackerZonePartitionTest {

    @Test
    public void testAreaContaing() {
        ZonePartition<Zone.Forest> zp = new ZonePartition<>(new HashSet<>());
        zp.areaContaining(new Zone.Forest(0, Zone.Forest.Kind.PLAIN));
    }

}