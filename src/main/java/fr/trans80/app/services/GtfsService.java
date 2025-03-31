package fr.trans80.app.services;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.ZipFile;
import org.onebusaway.csv_entities.EntityHandler;
import org.onebusaway.gtfs.impl.GtfsDaoImpl;
import org.onebusaway.gtfs.model.Agency;
import org.onebusaway.gtfs.model.ServiceCalendar;
import org.onebusaway.gtfs.model.Route;
import org.onebusaway.gtfs.model.Stop;
import org.onebusaway.gtfs.model.Trip;
import org.onebusaway.gtfs.serialization.GtfsReader;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class GtfsService {
    private static final String GTFS_URL = "https://sig.hautsdefrance.fr/ext/opendata/Transport/GTFS/80/RHDF_GTFS_COM_80.zip";
    private static final String TEMP_DIR = System.getProperty("java.io.tmpdir");

    public List<Agency> agencyCache = new ArrayList<>();
    public List<ServiceCalendar> calendarCache = new ArrayList<>();
    public List<Route> routeCache = new ArrayList<>();
    public List<Stop> stopCache = new ArrayList<>();
    public List<Trip> tripCache = new ArrayList<>();

    @PostConstruct
    private void init()  throws Exception {
        this.processGtfsData();
    }

    public void processGtfsData() throws Exception {
        ZipFile file = new ZipFile(downloadGtfsFile());
        file.extractAll(TEMP_DIR);
        readGtfsData(new File(TEMP_DIR));
    }

    private File downloadGtfsFile() throws IOException {
        File zipFile = new File(TEMP_DIR, "gtfs.zip");
        try (
                InputStream inputStream = new URL(GTFS_URL).openStream();
                OutputStream outputStream = new FileOutputStream(zipFile)
        ) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
        return zipFile;
    }

    private void readGtfsData(File extractedDir) throws IOException {
        GtfsReader reader = new GtfsReader();
        reader.setInputLocation(extractedDir);

        reader.addEntityHandler(new GtfsEntityHandler());

        GtfsDaoImpl store = new GtfsDaoImpl();
        reader.setEntityStore(store);

        reader.run();

        this.agencyCache = store.getAllAgencies().stream().toList();
        this.calendarCache = store.getAllCalendars().stream().toList();
        this.routeCache = store.getAllRoutes().stream().toList();
        this.stopCache = store.getAllStops().stream().toList();
        this.tripCache = store.getAllTrips().stream().toList();
    }

    private static class GtfsEntityHandler implements EntityHandler {
        public void handleEntity(Object bean) {
        }
    }
}