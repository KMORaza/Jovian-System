package jovian_system;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import static javafx.scene.paint.Color.rgb;

public class JovianSystemSimulation extends Application {

    /**
     Jupiter wird kreisförmig mit der Farbe RGB(255, 229, 204) in der Mitte der App dargestellt
     (Jupiter is represented in circular form with color RGB(255, 229, 204) at the center of the app)

     Galileische Monde: Io, Europa, Ganymed und Kallisto
     (Galilean moons: Io, Europa, Ganymede and Callisto)

     In der Simulation dargestellte nicht-galileische Monde: Metis, Adrastea, Amalthea, Thebe
     (Non-Galilean moons depicted in simulation: Metis, Adrastea, Amalthea, Thebe)

     Ringe des Jupiter: Halo, Hauptring, Gossamer Ringe
     (Rings of Jupiter: Halo, Main Ring, Gossamer Rings)
    **/

    // Skalierungsfaktor für Entfernungen (skaliert für Simulation)
    // Scale factor for distances (scaled for simulation)
    private static final double SCALE = 0.02; // Angepasster Skalierungsfaktor für bessere Passform (Adjusted scale factor for better fitting)

    // Umlaufzeit (in Sekunden, vereinfacht für die Simulation)
    // Orbital periods (in seconds, simplified for the simulation)
    private static final double ORBIT_PERIOD_IO = 1.8;         // 1,8 Tage (1.8 days)
    private static final double ORBIT_PERIOD_EUROPA = 3.5;     // 3,5 Tage (3.5 days)
    private static final double ORBIT_PERIOD_GANYMEDE = 7.1;   // 7,1 Tage (7.1 days)
    private static final double ORBIT_PERIOD_CALLISTO = 16.7;  // 16,7 Tage (16.7 days)
    private static final double ORBIT_PERIOD_METIS = 0.3;      // 0,3 Tage (0,3 days)
    private static final double ORBIT_PERIOD_ADRASTEA = 0.4;   // 0,4 Tage (0,4 days)
    private static final double ORBIT_PERIOD_AMALTHEA = 1.0;   // 1,0 Tage (1.0 days)
    private static final double ORBIT_PERIOD_THEBE = 1.8;      // 1,8 Tage (1.8 days)

    // Radien der Jupitermonde (für die Simulation skaliert)
    // Radii of the moons of Jupiter (scaled for simulation)
    private static final double RADIUS_IO = 100;        // Io
    private static final double RADIUS_EUROPA = 150;    // Europa
    private static final double RADIUS_GANYMEDE = 240;  // Ganymede
    private static final double RADIUS_CALLISTO = 290;  // Callisto
    private static final double RADIUS_METIS = 45;      // Metis
    private static final double RADIUS_ADRASTEA = 50;   // Adrastea
    private static final double RADIUS_AMALTHEA = 60;   // Amalthea
    private static final double RADIUS_THEBE = 70.5;    // Thebe

    // Bahnwinkel der Jupitermonde (Orbital angles of moons of Jupiter)
    private double angleIo = 0;
    private double angleEuropa = 0;
    private double angleGanymede = 0;
    private double angleCallisto = 0;
    private double angleMetis = 0;
    private double angleAdrastea = 0;
    private double angleAmalthea = 0;
    private double angleThebe = 0;

    // Verfolgen Sie den Status der Animation (angehalten oder abgespielt)
    // Track the state of the animation (paused or playing)
    private boolean isPaused = false;
    private AnimationTimer animationTimer;

    @Override
    public void start(Stage primaryStage) {
        // Eine Gruppe zur Aufnahme unserer Objekte (Jupiter und Monde) wurde erstellt.
        // (Create a group to hold our objects (Jupiter and moons))
        Group root = new Group();

        // Erstellt ein Raster im Hintergrund
        // (Create a background grid)
        createGrid(root);

        // Simulation des Jupiters im Zentrum mit den Farben RGB(255, 229, 204)
        // Simulates Jupiter at the center with the color RGB(255, 229, 204)
        Circle jupiter = new Circle(400, 300, 27); // X, Y, Radius
        jupiter.setFill(rgb(255, 229, 204)); // RGB-Farbe für Jupiter (RGB color for Jupiter)
        root.getChildren().add(jupiter); // Fügt die Simulation des Jupiters hinzu (Adds the simulation of Jupiter)

        // Erzeugt die Simulation des innersten Ring-Halo (Created the simulation of the innermost ring Halo)
        createHalo(root);
        // Erstellt die Simulation des Hauptrings (Created the simulation of Main Ring)
        createMainRing(root);
        // Erstellt die Simulation des ersten Gossamer-Rings oder des Amalthea-Gossamer-Rings
        // (Created the simulation of first Gossamer ring or Amalthea Gossamer ring)
        createGossamerRing1(root);
        // Erstellt die Simulation des zweiten Gossamer-Rings oder Thebe-Gossamer-Rings
        // (Created the simulation of second Gossamer ring or Thebe Gossamer ring)
        createGossamerRing2(root);

        // Erstellt die Simulation von Umlaufringen für jeden Jupitermond
        // Created the simulation of orbital rings of each Jupiter's moon
        createOrbitRing(root, RADIUS_IO, Color.WHITE);         // Io
        createOrbitRing(root, RADIUS_EUROPA, Color.WHITE);     // Europa
        createOrbitRing(root, RADIUS_GANYMEDE, Color.WHITE);   // Ganymede
        createOrbitRing(root, RADIUS_CALLISTO, Color.WHITE);   // Callisto
        createOrbitRing(root, RADIUS_METIS, Color.WHITE);      // Metis
        createOrbitRing(root, RADIUS_ADRASTEA, Color.WHITE);   // Adrastea
        createOrbitRing(root, RADIUS_AMALTHEA, Color.WHITE);   // Amalthea
        createOrbitRing(root, RADIUS_THEBE, Color.WHITE);      // Thebe

        // Erstellt die Darstellung von Monden (Created the depiction of moons)
        Circle io = createMoon(0, 0, rgb(212, 198, 107), 10);         // Io
        Circle europa = createMoon(0, 0, rgb(222, 223, 227), 10);     // Europa
        Circle ganymede = createMoon(0, 0, rgb(143, 128, 113), 10);   // Ganymede
        Circle callisto = createMoon(0, 0, rgb(180, 162, 133), 10);   // Callisto
        Circle metis = createMoon(0, 0, rgb(255, 255, 255), 5);       // Metis
        Circle adrastea = createMoon(0, 0, rgb(255, 255, 255), 5);    // Adrastea
        Circle amalthea = createMoon(0, 0, rgb(255, 255, 255), 5.5);  // Amalthea
        Circle thebe = createMoon(0, 0, rgb(255, 255, 255), 5.5);     // Thebe

        // Beschriftungen für die Namen aller Monde (Static labels for names of each moon)
        Text ioLabel = createOrbitLabel("IO", RADIUS_IO + 12, 0);
        Text europaLabel = createOrbitLabel("EUROPA", RADIUS_EUROPA + 7, 0);
        Text ganymedeLabel = createOrbitLabel("GANYMEDE", RADIUS_GANYMEDE + 6, 0);
        Text callistoLabel = createOrbitLabel("CALLISTO", RADIUS_CALLISTO + 11, 0);
        Text metisLabel = createOrbitLabelForNonGalileanMoons("METIS", RADIUS_METIS + 9, 2);
        Text adrasteaLabel = createOrbitLabelForNonGalileanMoons("ADRASTEA", RADIUS_ADRASTEA + 9, 2);
        Text amaltheaLabel = createOrbitLabelForNonGalileanMoons("AMALTHEA", RADIUS_AMALTHEA + 9, 2);
        Text thebeLabel = createOrbitLabelForNonGalileanMoons("THEBE", RADIUS_THEBE + 9, 2);

        // Fügt die Simulation von Monden und ihren Beschriftungen hinzu
        // (Adds simulation of moons with their labels)
        root.getChildren().addAll(io, europa, ganymede, callisto, metis, adrastea, amalthea, thebe,
                ioLabel, europaLabel, ganymedeLabel, callistoLabel, metisLabel, adrasteaLabel,
                amaltheaLabel, thebeLabel);

        // Schaltfläche zum Anhalten (⏸) oder Abspielen (▶)
        // Toggle button to pause (⏸) or play (▶)
        Button toggleButton = new Button("⏸");
        toggleButton.setLayoutX(10);
        toggleButton.setLayoutY(10);
        toggleButton.setStyle("-fx-font-family: 'Segoe UI Emoji'; -fx-font-size: 33; -fx-text-fill: white; -fx-background-color: #000; " +
                "-fx-border-color: black; -fx-border-width: 0.5; -fx-border-radius: 5;");

        toggleButton.setOnAction(e -> {
            if (isPaused) {
                // Fortsetzen der Simulation (Resume the simulation)
                animationTimer.start();
                toggleButton.setText("⏸");
            } else {
                // Pausiert die Simulation (Pause the simulation)
                animationTimer.stop();
                toggleButton.setText("▶");
            }
            isPaused = !isPaused;
        });

        root.getChildren().add(toggleButton);

        // Legen Sie Größe, Titel und schwarzen Hintergrund fest
        // Set the size, title & black-colored background
        Scene scene = new Scene(root, 800, 600, Color.BLACK);
        primaryStage.setTitle("Jovian System Simulation");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false); // Ich habe die Maximieren-Schaltfläche deaktiviert oder 🗖 (I have disabled the maximize button or 🗖)
        primaryStage.show();

        // Animationsschleife zur Simulation einer Orbitalbewegung
        // Animation loop for simulation of orbital motion
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Aktualisieren Sie die Position (Update the positions)
                updateMoonPosition(io, RADIUS_IO, angleIo);
                updateMoonPosition(europa, RADIUS_EUROPA, angleEuropa);
                updateMoonPosition(ganymede, RADIUS_GANYMEDE, angleGanymede);
                updateMoonPosition(callisto, RADIUS_CALLISTO, angleCallisto);
                updateMoonPosition(metis, RADIUS_METIS, angleMetis);
                updateMoonPosition(adrastea, RADIUS_ADRASTEA, angleAdrastea);
                updateMoonPosition(amalthea, RADIUS_AMALTHEA, angleAmalthea);
                updateMoonPosition(thebe, RADIUS_THEBE, angleThebe);

                // Erhöht den Umlaufwinkel jedes Mondes (Increases orbital angle of every moon)
                angleIo += 0.05;
                angleEuropa += 0.03;
                angleGanymede += 0.02;
                angleCallisto += 0.01;
                angleMetis += 0.1;
                angleAdrastea += 0.1;
                angleAmalthea += 0.07;
                angleThebe += 0.06;
            }
        };
        animationTimer.start();
    }

    // Farbe und Radius des Mondes (Color and radius of moon)
    private Circle createMoon(double x, double y, Color color, double radius) {
        Circle moon = new Circle(0, 0, radius); // radius of moon
        moon.setFill(color);
        return moon;
    }

    // Textbeschriftung, die den Namen jedes Galileischen Mondes angibt
    // (Text label to indicate name of each Galilean moon)
    private Text createOrbitLabel(String moonName, double radius, double labelOffset) {
        Text label = new Text(moonName);
        label.setFill(Color.WHITE);
        label.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 14;");
        double x = 400 + radius * Math.cos(Math.toRadians(90));
        double y = 300 + radius * Math.sin(Math.toRadians(90));
        label.setX(x + labelOffset);
        label.setY(y - 10);
        return label;
    }

    // Textbeschriftung zur Angabe des Namens jedes nicht-galileischen Mondes
    // Text label to indicate name of each non-Galilean moon
    private Text createOrbitLabelForNonGalileanMoons(String moonName, double radius, double labelOffset) {
        Text label = new Text(moonName);
        label.setFill(Color.WHITE);
        label.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 10;");
        double x = 400 + radius * Math.cos(Math.toRadians(90));
        double y = 300 + radius * Math.sin(Math.toRadians(90));
        label.setX(x + labelOffset);
        label.setY(y - 10);
        return label;
    }

    // Aktualisiert die Position des Mondes basierend auf seinem Umlaufradius und -winkel
    // Updates position of moon as per its orbital radius and angle
    private void updateMoonPosition(Circle moon, double radius, double angle) {
        // Berechnet X- und Y-Koordinaten mithilfe der Trigonometrie
        // Calculation of X,Y coordinates by trigonometry
        double x = 400 + radius * Math.cos(angle); // Zentrum des Jupiter (Jupiter's center)
        double y = 300 + radius * Math.sin(angle); // Zentrum des Jupiter (Jupiter's center)
        moon.setCenterX(x);
        moon.setCenterY(y);
    }

    // Erstellt die Simulation eines Orbitalrings (Creates the simulation of orbital ring)
    private void createOrbitRing(Group root, double radius, Color color) {
        Circle orbitRing = new Circle(400, 300, radius);
        orbitRing.setStroke(color);
        orbitRing.setStrokeWidth(0.5);
        orbitRing.setFill(Color.TRANSPARENT);
        root.getChildren().add(orbitRing);
    }

    // Erstellt eine Simulation des Halo-Rings (das ist der innerste Ring im Jupitersystem).
    // Creates the simulation of Halo ring (innermost ring in Jovian system)
    private void createHalo(Group root) {
        Circle ringHalo = new Circle(400, 300, 38);
        ringHalo.setStroke(rgb(176,196,222, 0.7));
        ringHalo.setStrokeWidth(9);
        ringHalo.setFill(Color.TRANSPARENT);
        root.getChildren().add(ringHalo);
    }

    // Erstellt die Simulation des Hauptrings
    // Creates the simulation of Main Ring
    private void createMainRing(Group root) {
        Circle mainRing = new Circle(400, 300, 46);
        mainRing.setStroke(rgb(255,99,71, 0.7));
        mainRing.setStrokeWidth(7); // Reduced stroke width for thinner rings
        mainRing.setFill(Color.TRANSPARENT);
        root.getChildren().add(mainRing);
    }

    // Erstellt die Simulation des ersten Gossamer-Rings oder des Amalthea-Gossamer-Rings
    // (Creates the simulation of first Gossamer ring or Amalthea Gossamer ring)
    private void createGossamerRing1(Group root) {
        Circle GossamerRing1 = new Circle(400, 300, 55);
        GossamerRing1.setStroke(rgb(218,165,32, 0.7));
        GossamerRing1.setStrokeWidth(8.5);
        GossamerRing1.setFill(Color.TRANSPARENT);
        root.getChildren().add(GossamerRing1);
    }

    // Erstellt die Simulation des zweiten Gossamer-Rings oder Thebe-Gossamer-Rings
    // (Creates the simulation of second Gossamer ring or Thebe Gossamer ring)
    private void createGossamerRing2(Group root) {
        Circle GossamerRing2 = new Circle(400, 300, 65);
        GossamerRing2.setStroke(rgb(152,251,152, 0.7));
        GossamerRing2.setStrokeWidth(9);
        GossamerRing2.setFill(Color.TRANSPARENT);
        root.getChildren().add(GossamerRing2);
    }


    // Quadratisches Gitter im Hintergrund (squared grid in background)
    private void createGrid(Group root) {
        for (int i = 0; i < 800; i += 40) {
            Line line = new Line(i, 0, i, 800);
            line.setStroke(Color.GRAY);
            root.getChildren().add(line);
        }
        for (int i = 0; i < 800; i += 40) {
            Line line = new Line(0, i, 800, i);
            line.setStroke(Color.GRAY);
            root.getChildren().add(line);
        }
    }

    // Starten Sie die JavaFX-App (Launches the JavaFX app)
    public static void main(String[] args) {
        launch(args);
    }
}
